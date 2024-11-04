package cn.iocoder.yudao.module.system.framework.sms.core.client.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.crypto.digest.HmacAlgorithm;
import cn.hutool.http.HttpUtil;
import cn.iocoder.yudao.framework.common.core.KeyValue;
import cn.iocoder.yudao.framework.common.util.collection.MapUtils;
import cn.iocoder.yudao.framework.common.util.json.JsonUtils;
import cn.iocoder.yudao.module.system.enums.common.DingTalkPriorityEnum;
import cn.iocoder.yudao.module.system.enums.sms.SmsTemplateTypeEnum;
import cn.iocoder.yudao.module.system.framework.sms.core.client.dto.SmsReceiveRespDTO;
import cn.iocoder.yudao.module.system.framework.sms.core.client.dto.SmsSendRespDTO;
import cn.iocoder.yudao.module.system.framework.sms.core.client.dto.SmsTemplateRespDTO;
import cn.iocoder.yudao.module.system.framework.sms.core.enums.SmsTemplateAuditStatusEnum;
import cn.iocoder.yudao.module.system.framework.sms.core.property.SmsChannelProperties;
import cn.iocoder.yudao.module.system.util.dd.DingTalkUtils;
import cn.iocoder.yudao.module.system.util.dd.vo.CreateDingTodoReqVO;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.*;

/**
 * 基于钉钉 WebHook 实现的调试的短信客户端实现类
 * <p>
 * 考虑到省钱，我们使用钉钉 WebHook 模拟发送短信，方便调试。
 *
 * @author 芋道源码
 */
@Slf4j
public class DingTalkSmsClient extends AbstractSmsClient {

    private final DingTalkUtils dingTalkUtils;


    public DingTalkSmsClient(SmsChannelProperties properties, DingTalkUtils dingTalkUtils) {
        super(properties);
        this.dingTalkUtils = dingTalkUtils;
        Assert.notEmpty(properties.getApiKey(), "apiKey 不能为空");
        Assert.notEmpty(properties.getApiSecret(), "apiSecret 不能为空");
    }

    @Override
    protected void doInit() {
    }

    @Override
    public SmsSendRespDTO sendSms(Long sendLogId, String mobile,
                                  String apiTemplateId, List<KeyValue<String, Object>> templateParams) throws Throwable {
        // 构建请求
        String url = buildUrl("robot/send");
        Map<String, Object> params = new HashMap<>();
        params.put("msgtype", "text");
//        String content = String.format("【模拟短信】\n手机号：%s\n短信日志编号：%d\n模板参数：%s",
//                mobile, sendLogId, MapUtils.convertMap(templateParams));
        Map<String, Object> stringObjectMap = MapUtils.convertMap(templateParams);
//        SmsTemplateDO smsTemplate = templateService.getSmsTemplate(Long.valueOf(apiTemplateId));
//        log.info("短信模板：{}",smsTemplate.getContent());
//        String content = smsTemplate.getContent();
        String content = "";
        log.info("模板内容：{}", content);
        for (String s : stringObjectMap.keySet()) {
            if (content.contains("{" + s + "}")) {
                content = content.replace("{" + s + "}", stringObjectMap.get(s).toString());
            }
        }
        ;
        log.info("拼接后的参数：{}", content);
        //获取短信模板
        params.put("text", MapUtil.builder().put("content", content).build());
        // 执行请求
        String responseText = HttpUtil.post(url, JsonUtils.toJsonString(params));
        // 解析结果
        Map<?, ?> responseObj = JsonUtils.parseObject(responseText, Map.class);
        String errorCode = MapUtil.getStr(responseObj, "errcode");
        return new SmsSendRespDTO().setSuccess(Objects.equals(errorCode, "0")).setSerialNo(StrUtil.uuid())
                .setApiCode(errorCode).setApiMsg(MapUtil.getStr(responseObj, "errorMsg"));
    }

    public SmsSendRespDTO sendSms(String content) throws Throwable {
        // 构建请求
        String url = buildUrl("robot/send");
        Map<String, Object> params = new HashMap<>();
        params.put("msgtype", "text");
        //获取短信模板
        params.put("text", MapUtil.builder().put("content", content).build());
        // 执行请求
        String responseText = HttpUtil.post(url, JsonUtils.toJsonString(params));
        // 解析结果
        Map<?, ?> responseObj = JsonUtils.parseObject(responseText, Map.class);
        String errorCode = MapUtil.getStr(responseObj, "errcode");
        return new SmsSendRespDTO().setSuccess(Objects.equals(errorCode, "0")).setSerialNo(StrUtil.uuid())
                .setApiCode(errorCode).setApiMsg(MapUtil.getStr(responseObj, "errorMsg"));
    }

    /**
     * 构建请求地址
     * <p>
     * 参见 <a href="https://developers.dingtalk.com/document/app/custom-robot-access/title-nfv-794-g71">文档</a>
     *
     * @param path 请求路径
     * @return 请求地址
     */
    @SuppressWarnings("SameParameterValue")
    private String buildUrl(String path) {
        // 生成 timestamp
        long timestamp = System.currentTimeMillis();
        // 生成 sign
        String secret = properties.getApiSecret();
        String stringToSign = timestamp + "\n" + secret;
        byte[] signData = DigestUtil.hmac(HmacAlgorithm.HmacSHA256, StrUtil.bytes(secret)).digest(stringToSign);
        String sign = Base64.encode(signData);
        // 构建最终 URL
        return String.format("https://oapi.dingtalk.com/%s?access_token=%s&timestamp=%d&sign=%s",
                path, properties.getApiKey(), timestamp, sign);
    }

    @Override
    public List<SmsReceiveRespDTO> parseSmsReceiveStatus(String text) {
        throw new UnsupportedOperationException("模拟短信客户端，暂时无需解析回调");
    }

    @Override
    public SmsTemplateRespDTO getSmsTemplate(String apiTemplateId) {
        return new SmsTemplateRespDTO().setId(apiTemplateId).setContent("")
                .setAuditStatus(SmsTemplateAuditStatusEnum.SUCCESS.getStatus()).setAuditReason("");
    }

    public SmsSendRespDTO sendSms(Integer type, String title, String description,
                                  Map<String, Object> params) throws Exception {

        //获取用户的userId
        if (!params.containsKey("creator")) {
            throw exception(SMS_TEMPLATE_CREATOR_ID_NOT_EXISTS);
        }
        if (!params.containsKey("detailUrl")) {
            throw exception(SMS_TEMPLATE_URL_NOT_EXISTS);
        }

        String creatorId = params.get("creator").toString();
        String detailUrl = params.get("detailUrl").toString();

        //判断通知的类型
        if (type.equals(SmsTemplateTypeEnum.NOTICE.getType())) {  //发送审批结果
            String actionTitle = "CRM系统-流程审批结果通知";
            String buttonText = "点击查看审批详情";
            return dingTalkUtils.sendNotifyMessage(creatorId, title, description, detailUrl,actionTitle,buttonText);
        } else if (type.equals(SmsTemplateTypeEnum.TODO.getType())) {  //发送待办
            if (!params.containsKey("executor")) {
                throw exception(SMS_TEMPLATE_EXECUTOR_ID_NOT_EXISTS);
            }
            if (!params.containsKey("startUserNickname")) {
                throw exception(SMS_TEMPLATE_CREATOR_ID_NOT_EXISTS);
            }
            String startUserNickname = params.get("startUserNickname").toString();

            String executorId = params.get("executor").toString();
            // 获取用户的unionId
            String creatorUnionId = dingTalkUtils.getUnionId(creatorId);
            String executorUnionId = dingTalkUtils.getUnionId(executorId);
            log.info("创建待办，创建人unionId：{}, 执行人unionId：{}", creatorUnionId, executorUnionId);
            CreateDingTodoReqVO todoReqVO = CreateDingTodoReqVO.builder()
                    .title(title)
                    .description(description)
                    .priority(DingTalkPriorityEnum.URGENT.getType()) // 紧急
                    .executor(Collections.singletonList(executorUnionId))
                    .participantIds(Arrays.asList(creatorUnionId, executorUnionId))
                    .sourceId(StrUtil.uuid())
                    .pcUrl(detailUrl)
                    .appUrl(detailUrl)
                    .creator(creatorUnionId)
                    .isOnlyShowExecutor(true)
                    .dueTime(null)
                    .createUserName(startUserNickname)
                    .build();
            return dingTalkUtils.createTodoTask(todoReqVO);
        }
        throw exception(SMS_TEMPLATE_TYPE_UN_SUPPORT);
    }
}
