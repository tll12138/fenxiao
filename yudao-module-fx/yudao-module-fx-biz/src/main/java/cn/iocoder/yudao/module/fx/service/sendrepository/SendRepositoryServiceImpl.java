package cn.iocoder.yudao.module.fx.service.sendrepository;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.fx.controller.admin.sendrepository.vo.SendRepositoryPageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.sendrepository.vo.SendRepositorySaveReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.sendrepository.RepositoryResponseBodyMO;
import cn.iocoder.yudao.module.fx.dal.dataobject.sendrepository.SendRepositoryDO;
import cn.iocoder.yudao.module.fx.dal.dataobject.sendrepository.SendRepositoryVO;
import cn.iocoder.yudao.module.fx.dal.mysql.sendrepository.SendRepositoryMapper;
import cn.iocoder.yudao.module.fx.service.jushuitanapi.JuShuiTanApiService;
import cn.iocoder.yudao.module.fx.utils.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jushuitan.api.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants.SEND_REPOSITORY_NOT_EXISTS;

/**
 * FX 发货仓库 Service 实现类
 *
 * @author 管理员
 */
@Slf4j
@Service
@Validated
public class SendRepositoryServiceImpl implements SendRepositoryService {

    @Resource
    private SendRepositoryMapper sendRepositoryMapper;
    @Resource
    private JuShuiTanApiService juShuiTanApiService;

    @Override
    public Integer createSendRepository(SendRepositorySaveReqVO createReqVO) {
        // 插入
        SendRepositoryDO sendRepository = BeanUtils.toBean(createReqVO, SendRepositoryDO.class);
        sendRepositoryMapper.insert(sendRepository);
        // 返回
        return sendRepository.getId();
    }

    @Override
    public void updateSendRepository(SendRepositorySaveReqVO updateReqVO) {
        // 校验存在
        validateSendRepositoryExists(updateReqVO.getId());
        // 更新
        SendRepositoryDO updateObj = BeanUtils.toBean(updateReqVO, SendRepositoryDO.class);
        sendRepositoryMapper.updateById(updateObj);
    }

    @Override
    public void deleteSendRepository(Integer id) {
        // 校验存在
        validateSendRepositoryExists(id);
        // 删除
        sendRepositoryMapper.deleteById(id);
    }

    private void validateSendRepositoryExists(Integer id) {
        if (sendRepositoryMapper.selectById(id) == null) {
            throw exception(SEND_REPOSITORY_NOT_EXISTS);
        }
    }

    @Override
    public SendRepositoryDO getSendRepository(Integer id) {
        return sendRepositoryMapper.selectById(id);
    }

    @Override
    public PageResult<SendRepositoryDO> getSendRepositoryPage(SendRepositoryPageReqVO pageReqVO) {
        return sendRepositoryMapper.selectPage(pageReqVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void syncSendRepository() {
        //递归获取所有发货仓库信息
        executeSendRepository(1);
    }

    @Override
    public List<SendRepositoryDO> getSendRepositoryList() {
        return sendRepositoryMapper.selectList(SendRepositoryDO::getIsUsed, 1, SendRepositoryDO::getIsInside, 0);
    }

    private void executeSendRepository(int pageNum) {
        String biz = String.format("{\"page_num\":\"%s\",\"page_size\":\"100\"}", pageNum);
        // 执行接口调用
        try {
            ApiResponse response = juShuiTanApiService.execute("SendRepositorySyncUrl", biz);
            String body = response.getBody();
            RepositoryResponseBodyMO bodyMO = JSONObject.parseObject(body, RepositoryResponseBodyMO.class);
            List<SendRepositoryVO> dataList = bodyMO.getData().getDatas();
            if (CollectionUtil.isNotEmpty(dataList)) {
                // 把发货仓库信息存库
                List<SendRepositoryDO> list = new ArrayList<>(dataList.size());
                Set<Integer> codes = new HashSet<>(dataList.size());
                for (SendRepositoryVO data : dataList) {
                    codes.add(data.getCode());
                }
                List<SendRepositoryDO> existingInfos = sendRepositoryMapper.selectList(new LambdaQueryWrapper<SendRepositoryDO>().in(SendRepositoryDO::getCode, codes));
                Map<String, SendRepositoryDO> existingInfoMap = existingInfos.stream().collect(Collectors.toMap(SendRepositoryDO::getCode, Function.identity()));
                for (SendRepositoryVO data : dataList) {
                    SendRepositoryDO one = existingInfoMap.get(data.getCode().toString());
                    if (one == null) {
                        one = new SendRepositoryDO();
                        existingInfoMap.put(String.valueOf(data.getCode()), one);
                    }
                    one.setUpdateTime(DateUtil.parseLocalDateTime(DateUtil.now()));
                    one.setCreateTime(DateUtil.parseLocalDateTime(DateUtil.now()));
                    BeanUtil.copyProperties(data, one);
                    one.setIsUsed("生效".equals(data.getStatus()) ? 1 : 0);
                    list.add(one);
                }
                sendRepositoryMapper.insertOrUpdateBatch(list);
            }
            ;
            if (bodyMO.getData().isHasNext()) {
                executeSendRepository(pageNum + 1);
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

}