package cn.iocoder.yudao.module.fx.service.goodsarchives;

import cn.hutool.core.bean.BeanUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.fx.controller.admin.goodsarchives.vo.GoodsArchivesPageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.goodsarchives.vo.GoodsArchivesSaveReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.goodsarchives.GoodsArchivesDO;
import cn.iocoder.yudao.module.fx.dal.dataobject.goodsarchives.GoodsArchivesVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.goodsarchives.GoodsResponseBodyMO;
import cn.iocoder.yudao.module.fx.dal.mysql.goodsarchives.GoodsArchivesMapper;
import cn.iocoder.yudao.module.fx.utils.CollectionUtil;
import cn.iocoder.yudao.module.fx.utils.MapUtils;
import cn.iocoder.yudao.module.system.service.dict.DictDataService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jushuitan.api.ApiClient;
import com.jushuitan.api.ApiRequest;
import com.jushuitan.api.ApiResponse;
import com.jushuitan.api.DefaultApiClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants.GOODS_ARCHIVES_NOT_EXISTS;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.DICT_TYPE_NOT_EXISTS;

/**
 * 分销商品资料 Service 实现类
 *
 * @author 管理员
 */
@Slf4j
@Service
@Validated
public class GoodsArchivesServiceImpl implements GoodsArchivesService {

    @Resource
    private GoodsArchivesMapper goodsArchivesMapper;
    @Resource
    private DictDataService dictDataService;

    @Override
    public Integer createGoodsArchives(GoodsArchivesSaveReqVO createReqVO) {
        // 插入
        GoodsArchivesDO goodsArchives = BeanUtils.toBean(createReqVO, GoodsArchivesDO.class);
        goodsArchivesMapper.insert(goodsArchives);
        // 返回
        return goodsArchives.getId();
    }

    @Override
    public void updateGoodsArchives(GoodsArchivesSaveReqVO updateReqVO) {
        // 校验存在
        validateGoodsArchivesExists(updateReqVO.getId());
        // 更新
        GoodsArchivesDO updateObj = BeanUtils.toBean(updateReqVO, GoodsArchivesDO.class);
        goodsArchivesMapper.updateById(updateObj);
    }

    @Override
    public void deleteGoodsArchives(Integer id) {
        // 校验存在
        validateGoodsArchivesExists(id);
        // 删除
        goodsArchivesMapper.deleteById(id);
    }

    private void validateGoodsArchivesExists(Integer id) {
        if (goodsArchivesMapper.selectById(id) == null) {
            throw exception(GOODS_ARCHIVES_NOT_EXISTS);
        }
    }

    @Override
    public GoodsArchivesDO getGoodsArchives(Integer id) {
        return goodsArchivesMapper.selectById(id);
    }

    @Override
    public PageResult<GoodsArchivesDO> getGoodsArchivesPage(GoodsArchivesPageReqVO pageReqVO) {
        return goodsArchivesMapper.selectPage(pageReqVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void syncGoodsArchives(boolean ifAll) {
        //从数据字典获取接口数据
        Map<String, String> apiInfo = dictDataService.getDictDataMapByDictType("fx_jushuitan_API_info");
        if (MapUtils.isEmpty(apiInfo)) {
            throw exception(DICT_TYPE_NOT_EXISTS);
        }
        String goodUrl = apiInfo.get("goodsSyncUrl");
        String groupUrl = apiInfo.get("goodsGroupSyncUrl");
        String appKey = apiInfo.get("appKey");
        String appSecret = apiInfo.get("appSecret");
        String accessToken = apiInfo.get("accessToken");
        LocalDateTime now = LocalDateTime.now();

        executeSync(1, now.minusDays(ifAll ? 7 : 2), now, goodUrl, appKey, appSecret, accessToken, ifAll, "goods");
        executeSync(1, now.minusDays(ifAll ? 7 : 2), now, groupUrl, appKey, appSecret, accessToken, ifAll, "groupGoods");
    }

    private void executeSync(int pageNum, LocalDateTime modifiedBegin, LocalDateTime modifiedEnd, String url, String appKey, String appSecret, String accessToken, boolean ifAll, String type) {
        //修改时间比聚水潭商品最早的修改还早就退出递归
        if (modifiedEnd.isBefore(LocalDateTime.parse("2021-08-19T09:00:06", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))) && ifAll && "goods".equals(type)) {
            return;
        }
        //修改时间比聚水潭组合商品最早的修改还早就退出递归
        if (modifiedEnd.isBefore(LocalDateTime.parse("2022-06-27T14:01:39", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))) && ifAll && "groupGoods".equals(type)) {
            return;
        }

        ApiClient client = new DefaultApiClient();
        String biz = String.format("{\"page_index\":\"%s\",\"page_size\":\"50\",\"modified_begin\":\"%s\",\"modified_end\":\"%s\"}", pageNum, modifiedBegin.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), modifiedEnd.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        ApiRequest request = new ApiRequest.Builder(url, appKey, appSecret).biz(biz).build();
        log.info("request: {}", request.getBiz());

        try {
            ApiResponse response = client.execute(request, accessToken);
            log.info("response: {}", response);
            String body = response.getBody();
            GoodsResponseBodyMO bodyMO = JSONObject.parseObject(body, GoodsResponseBodyMO.class);
            List<GoodsArchivesVO> datas = bodyMO.getData().getDatas();
            if (CollectionUtil.isNotEmpty(datas)) {
                List<GoodsArchivesDO> list = new ArrayList<>(datas.size());
                Set<String> goodsArchivesNums = new HashSet<>(datas.size());
                for (GoodsArchivesVO vo : datas) {
                    goodsArchivesNums.add(vo.getSkuId());
                }
                List<GoodsArchivesDO> existingInfos = goodsArchivesMapper.selectList(new LambdaQueryWrapper<GoodsArchivesDO>().in(GoodsArchivesDO::getSkuId, goodsArchivesNums));
                Map<String, GoodsArchivesDO> existingInfoMap = existingInfos.stream().collect(Collectors.toMap(GoodsArchivesDO::getSkuId, Function.identity()));
                for (GoodsArchivesVO vo : datas) {
                    GoodsArchivesDO one = existingInfoMap.get(vo.getSkuId());
                    if (one == null) {
                        one = new GoodsArchivesDO();
                        existingInfoMap.put(vo.getSkuId(), one);
                    }
                    BeanUtil.copyProperties(vo, one);
                    one.setIsGroup("groupGoods".equals(type) ? "1" : "0");
                    list.add(one);
                }
                goodsArchivesMapper.insertOrUpdateBatch(list);
            }
            if (bodyMO.getData().isHasNext()) {
                executeSync(++pageNum, modifiedBegin, modifiedEnd, url, appKey, appSecret, accessToken, ifAll, type);
            } else if (ifAll) {
                TimeUnit.MILLISECONDS.sleep(1000);
                executeSync(1, modifiedBegin.minusDays(7), modifiedEnd.minusDays(7), url, appKey, appSecret, accessToken, true, type);
            }
        } catch (Exception e) {
            log.error("Error executing sync for {}: {}", type, e.getMessage(), e);
        }
    }

}