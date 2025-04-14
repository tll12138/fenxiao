package cn.iocoder.yudao.module.fx.service.ec2jstorder;

import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.fx.controller.admin.ec2jstorder.vo.Ec2jstOrderPageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.ec2jstorder.vo.Ec2jstOrderSaveReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.ec2jstorder.Ec2jstOrderDO;
import cn.iocoder.yudao.module.fx.dal.dataobject.ec2jstorder.OrderItem;
import cn.iocoder.yudao.module.fx.dal.dataobject.ec2jstorder.OrderUploadReq;
import cn.iocoder.yudao.module.fx.dal.dataobject.ec2jstorder.PaymentInfo;
import cn.iocoder.yudao.module.fx.dal.dataobject.ec2jstorderitem.Ec2jstOrderitemDO;
import cn.iocoder.yudao.module.fx.dal.mysql.ec2jstorder.Ec2jstOrderMapper;
import cn.iocoder.yudao.module.fx.service.ec2jstorderitem.Ec2jstOrderitemService;
import cn.iocoder.yudao.module.fx.service.jushuitanapi.JuShuiTanApiService;
import cn.iocoder.yudao.module.fx.service.ordersinfo.OrdersInfoService;
import cn.iocoder.yudao.module.fx.utils.ObjectUtils;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants.EC2JST_ORDER_NOT_EXISTS;

/**
 * 分销订单上传中间 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
@Slf4j
public class Ec2jstOrderServiceImpl implements Ec2jstOrderService {

    @Resource
    private Ec2jstOrderMapper ec2jstOrderMapper;
    @Resource
    private Ec2jstOrderitemService ec2jstOrderitemService;
    @Resource
    private JuShuiTanApiService juShuiTanApiService;
    @Resource
    private OrdersInfoService ordersInfoService;

    @Override
    public Integer createEc2jstOrder(Ec2jstOrderSaveReqVO createReqVO) {
        // 插入
        Ec2jstOrderDO ec2jstOrder = BeanUtils.toBean(createReqVO, Ec2jstOrderDO.class);
        ec2jstOrderMapper.insert(ec2jstOrder);
        // 返回
        return ec2jstOrder.getId();
    }

    @Override
    public void updateEc2jstOrder(Ec2jstOrderSaveReqVO updateReqVO) {
        // 校验存在
        validateEc2jstOrderExists(updateReqVO.getId());
        // 更新
        Ec2jstOrderDO updateObj = BeanUtils.toBean(updateReqVO, Ec2jstOrderDO.class);
        ec2jstOrderMapper.updateById(updateObj);
    }

    @Override
    public void deleteEc2jstOrder(Integer id) {
        // 校验存在
        validateEc2jstOrderExists(id);
        // 删除
        ec2jstOrderMapper.deleteById(id);
    }

    private void validateEc2jstOrderExists(Integer id) {
        if (ec2jstOrderMapper.selectById(id) == null) {
            throw exception(EC2JST_ORDER_NOT_EXISTS);
        }
    }

    @Override
    public Ec2jstOrderDO getEc2jstOrder(Integer id) {
        return ec2jstOrderMapper.selectById(id);
    }

    @Override
    public PageResult<Ec2jstOrderDO> getEc2jstOrderPage(Ec2jstOrderPageReqVO pageReqVO) {
        return ec2jstOrderMapper.selectPage(pageReqVO);
    }

    @Override
    public void uploadOrders() {
        // 获取未上传的订单
        List<Ec2jstOrderDO> ec2jstOrderDOS = ec2jstOrderMapper.selectList(new LambdaQueryWrapper<Ec2jstOrderDO>().eq(Ec2jstOrderDO::getErpStatus, "N"));
        if (ec2jstOrderDOS.isEmpty()) {
            return;
        }

        // 批量获取所有订单的明细
        List<Integer> mainIds = ec2jstOrderDOS.stream().map(Ec2jstOrderDO::getId).collect(Collectors.toList());
        List<Ec2jstOrderitemDO> allItems = ec2jstOrderitemService.getEc2jstOrderItemListByMainIds(mainIds);
        Map<Integer, List<Ec2jstOrderitemDO>> itemsByMainId = allItems.stream()
                .collect(Collectors.groupingBy(Ec2jstOrderitemDO::getMainid));
        Map<String, Ec2jstOrderDO> ec2jstOrderByOrderNo = new HashMap<>();
        // 分批次处理，每批最多50条
        List<List<Ec2jstOrderDO>> batches = Lists.partition(ec2jstOrderDOS, 50);
        try {
            for (List<Ec2jstOrderDO> batch : batches) {
                List<OrderUploadReq> reqList = new ArrayList<>();

                // 构建当前批次的请求列表
                for (Ec2jstOrderDO orderDO : batch) {
                    Integer mainId = orderDO.getId();
                    List<Ec2jstOrderitemDO> itemList = itemsByMainId.getOrDefault(mainId, Collections.emptyList());

                    List<OrderItem> items = itemList.stream().map(item -> {
                        OrderItem orderItem = ObjectUtils.copyProperties(item, OrderItem.class);
                        orderItem.setAmount(item.getAmount().doubleValue())
                                .setBasePrice(item.getBasePrice().doubleValue())
                                .setQty(item.getQty().intValue());
                        return orderItem;
                    }).collect(Collectors.toList());

                    PaymentInfo paymentInfo = new PaymentInfo()
                            .setAmount(orderDO.getPayAmount().doubleValue())
                            .setOuterPayId(orderDO.getOrderNo())
                            .setPayDate(orderDO.getOrderDate())
                            .setPayment("线下支付")
                            .setSellerAccount(StrUtil.EMPTY)
                            .setBuyerAccount(StrUtil.EMPTY);

                    OrderUploadReq req = ObjectUtils.copyProperties(orderDO, OrderUploadReq.class);
                    req.setItems(items);
                    req.setPay(paymentInfo);

                    reqList.add(req);
                    ec2jstOrderByOrderNo.put(orderDO.getOrderNo(), orderDO);
                }

                // 批量调用接口
                String biz = JSONObject.toJSONString(reqList);
                log.info(biz);
//                ApiResponse response = juShuiTanApiService.execute("ordersUploadUrl", biz);
//                String body = response.getBody();
//                OrderUploadRes bodyMO = JSONObject.parseObject(body, OrderUploadRes.class);

//                if (bodyMO.getCode() == 0) {
//                    List<ResultData> datas = bodyMO.getData().getDatas();
//                    List<Ec2jstOrderDO> toUpdate = new ArrayList<>();
//
//                    // 处理批量响应
//                    for (ResultData data : datas) {
//                        String soId = data.getSoId();
//                        Ec2jstOrderDO orderDO = ec2jstOrderByOrderNo.get(soId);
//
//                        if (data.getIsSuccess()) {
//                            orderDO.setErpStatus("Y");
//                            orderDO.setToErpTime(DateUtil.format(DateUtil.date(), DatePattern.NORM_DATETIME_PATTERN));
//                            orderDO.setOId(data.getOId());
//                            toUpdate.add(orderDO);
//                        } else {
//                            log.error("订单上传失败，订单号：{}，原因：{}", orderDO.getOrderNo(), data.getMsg());
//                        }
//                    }
//
//                    // 批量更新状态
//                    if (!toUpdate.isEmpty()) {
//                        ec2jstOrderMapper.updateBatchById(toUpdate);
//                    }
//                } else {
//                    log.error("批量订单上传失败：{}", bodyMO.getMsg());
//                    throw new RuntimeException("批量上传失败：" + bodyMO.getMsg());
//                }

                TimeUnit.MILLISECONDS.sleep(1000);
            }
        } catch (Exception e) {
            throw new RuntimeException("订单上传处理异常", e);
        }
    }

}