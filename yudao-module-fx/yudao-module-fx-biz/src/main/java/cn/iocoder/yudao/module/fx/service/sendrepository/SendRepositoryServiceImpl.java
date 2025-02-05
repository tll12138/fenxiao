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
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants.SEND_REPOSITORY_NOT_EXISTS;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.DICT_TYPE_NOT_EXISTS;

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
    private DictDataService dictDataService;

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
        //从数据字典获取接口数据
        Map<String, String> apiInfo = dictDataService.getDictDataMapByDictType("fx_jushuitan_API_info");
        if (MapUtils.isEmpty(apiInfo)) {
            throw exception(DICT_TYPE_NOT_EXISTS);
        }
        String url = apiInfo.get("SendRepositorySyncUrl");
        String appKey = apiInfo.get("appKey");
        String appSecret = apiInfo.get("appSecret");
        String accessToken = apiInfo.get("accessToken");
        //递归获取所有发货仓库信息
        executeSendRepository(1, url, appKey, appSecret, accessToken);
    }

    @Override
    public List<SendRepositoryDO> getSendRepositoryList() {
        return sendRepositoryMapper.selectList(SendRepositoryDO::getIsUsed, 1, SendRepositoryDO::getIsInside, 0);
    }

    private void executeSendRepository(int pageNum, String url, String appKey, String appSecret, String accessToken) {
        // 实例化client
        ApiClient client = new DefaultApiClient();
        String biz = String.format("{\"page_num\":\"%s\",\"page_size\":\"100\"}", pageNum);
        // 构建请求对象
        ApiRequest request = new ApiRequest.Builder(url, appKey, appSecret)
                .biz(biz).build();
        // 执行接口调用
        try {
            ApiResponse response = client.execute(request, accessToken);
            String body = response.getBody();
            RepositoryResponseBodyMO bodyMO = JSONObject.parseObject(body, RepositoryResponseBodyMO.class);
            List<SendRepositoryVO> datas = bodyMO.getData().getDatas();
            if (CollectionUtil.isNotEmpty(datas)) {
                // 把发货仓库信息存库
                List<SendRepositoryDO> list = new ArrayList<>(datas.size());
                Set<Integer> codes = new HashSet<>(datas.size());
                for (SendRepositoryVO data : datas) {
                    codes.add(data.getCode());
                }
                List<SendRepositoryDO> existingInfos = sendRepositoryMapper.selectList(new LambdaQueryWrapper<SendRepositoryDO>().in(SendRepositoryDO::getCode, codes));
                Map<String, SendRepositoryDO> existingInfoMap = existingInfos.stream().collect(Collectors.toMap(SendRepositoryDO::getCode, Function.identity()));
                for (SendRepositoryVO data : datas) {
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
                executeSendRepository(pageNum + 1, url, appKey, appSecret, accessToken);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}