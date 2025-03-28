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
import cn.iocoder.yudao.module.fx.service.jushuitanapi.JuShuiTanApiService;
import cn.iocoder.yudao.module.fx.utils.CollectionUtil;
import cn.iocoder.yudao.module.system.service.dict.DictDataService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jushuitan.api.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants.GOODS_ARCHIVES_NOT_EXISTS;

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
    private JuShuiTanApiService juShuiTanApiService;
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

    /**
     * 获得分销商品资料
     *
     * @param skuId
     * @return 分销商品资料
     */
    @Override
    public GoodsArchivesDO getGoodsArchivesBySkuId(String skuId) {
        return goodsArchivesMapper.selectOne(new LambdaQueryWrapper<GoodsArchivesDO>().eq(GoodsArchivesDO::getSkuId, skuId).last("limit 1"));
    }

    /**
     * 获得分销商品skuId和品牌的映射关系
     * 用于查询商品的品牌
     */
    @Override
    public Map<String, String> getGoodsArchivesBrandsMap() {
        // 查询所有商品的skuId和品牌字段
        List<GoodsArchivesDO> goodsArchivesList = goodsArchivesMapper.selectList(
                new LambdaQueryWrapper<GoodsArchivesDO>()
                        .select(GoodsArchivesDO::getSkuId, GoodsArchivesDO::getBrand));

        // 转换为 skuId -> brand 的映射
        return goodsArchivesList.stream()
                .collect(Collectors.toMap(
                        GoodsArchivesDO::getSkuId,
                        g -> Optional.ofNullable(g.getBrand()).orElse(""),
                        (existing, replacement) -> replacement)); // 处理重复key的情况
    }

    @Override
    public PageResult<GoodsArchivesDO> getGoodsArchivesPage(GoodsArchivesPageReqVO pageReqVO) {
        return goodsArchivesMapper.selectPage(pageReqVO);
    }

    @Override
    public PageResult<GoodsArchivesDO> getGoodsArchivesPageByWarehouseCode(GoodsArchivesPageReqVO pageReqVO) {
        Page page = new Page(pageReqVO.getPageNo(), pageReqVO.getPageSize());
        Page<GoodsArchivesDO> archivesPageByWarehouseCode = goodsArchivesMapper.getGoodsArchivesPageByWarehouseCode(page, pageReqVO);
        log.info("archivesPageByWarehouseCode: {}", archivesPageByWarehouseCode.getRecords());
        return new PageResult<>(archivesPageByWarehouseCode.getRecords(), archivesPageByWarehouseCode.getTotal());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void syncGoodsArchives(boolean ifAll) {
        String goodUrl = "goodsSyncUrl";
        String groupUrl = "goodsGroupSyncUrl";
        LocalDateTime now = LocalDateTime.now();
        //查询品牌字典
        final Map<String, String> fxBrand = dictDataService.getDictDataMapByDictType("fx_brand");

        executeSync(1, now.minusDays(ifAll ? 7 : 2), now, goodUrl, ifAll, "goods", fxBrand);
        executeSync(1, now.minusDays(ifAll ? 7 : 2), now, groupUrl, ifAll, "groupGoods", fxBrand);
    }

    private void executeSync(int pageNum, LocalDateTime modifiedBegin, LocalDateTime modifiedEnd, String url, boolean ifAll, String type, Map<String, String> fxBrand) {
        //修改时间比聚水潭商品最早的修改还早就退出递归
        if (modifiedEnd.isBefore(LocalDateTime.parse("2021-08-19T09:00:06", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))) && ifAll && "goods".equals(type)) {
            return;
        }
        //修改时间比聚水潭组合商品最早的修改还早就退出递归
        if (modifiedEnd.isBefore(LocalDateTime.parse("2022-06-27T14:01:39", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))) && ifAll && "groupGoods".equals(type)) {
            return;
        }
        String biz = String.format("{\"page_index\":\"%s\",\"page_size\":\"50\",\"modified_begin\":\"%s\",\"modified_end\":\"%s\"}", pageNum, modifiedBegin.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), modifiedEnd.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        try {
            ApiResponse response = juShuiTanApiService.execute(url, biz);
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
                    one.setBrand(fxBrand.containsKey(vo.getBrand()) ? fxBrand.get(vo.getBrand()) : vo.getBrand());
                    list.add(one);
                }
                goodsArchivesMapper.insertOrUpdateBatch(list);
            }
            if (bodyMO.getData().isHasNext()) {
                executeSync(++pageNum, modifiedBegin, modifiedEnd, url, ifAll, type, fxBrand);
            } else if (ifAll) {
                TimeUnit.MILLISECONDS.sleep(1000);
                executeSync(1, modifiedBegin.minusDays(7), modifiedEnd.minusDays(7), url, true, type, fxBrand);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

}