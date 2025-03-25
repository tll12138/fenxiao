package cn.iocoder.yudao.module.system.util.dd;

import cn.hutool.json.JSONUtil;
import cn.iocoder.yudao.module.system.dal.redis.dd.DingTalkAccessTokenRedisDAO;
import cn.iocoder.yudao.module.system.dal.redis.dd.DingTalkUserUnionRedisDAO;
import cn.iocoder.yudao.module.system.framework.sms.core.client.dto.SmsSendRespDTO;
import cn.iocoder.yudao.module.system.util.dd.config.DingTalkProperties;
import cn.iocoder.yudao.module.system.util.dd.vo.CreateDingTodoReqVO;
import com.aliyun.dingtalkoauth2_1_0.models.GetAccessTokenRequest;
import com.aliyun.dingtalkoauth2_1_0.models.GetAccessTokenResponse;
import com.aliyun.dingtalktodo_1_0.Client;
import com.aliyun.dingtalktodo_1_0.models.CreateTodoTaskHeaders;
import com.aliyun.dingtalktodo_1_0.models.CreateTodoTaskRequest;
import com.aliyun.dingtalktodo_1_0.models.CreateTodoTaskResponse;
import com.aliyun.dingtalktodo_1_0.models.UpdateTodoTaskHeaders;
import com.aliyun.dingtalktodo_1_0.models.UpdateTodoTaskRequest;
import com.aliyun.tea.TeaException;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.request.OapiV2UserGetRequest;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.dingtalk.api.response.OapiV2UserGetResponse;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.DING_TALK_GET_TOKEN_ERROR;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.DING_TALK_GET_USERID_ERROR;
import static cn.iocoder.yudao.module.system.enums.UrlConstant.DINGTALK_GET_USER_DETAIL_URL;
import static cn.iocoder.yudao.module.system.enums.UrlConstant.DINGTALK_SEND_NOTIFY_MESSAGE_URL;

/**
 * @author zrl
 * @date 2024/10/31
 */
@Service
@Slf4j
public class DingTalkUtils {

    @Resource
    private DingTalkProperties dingTalkProperties;

    @Resource
    private DingTalkAccessTokenRedisDAO dingTalkRedisDAO;

    @Resource
    private DingTalkUserUnionRedisDAO userUnionRedisDAO;


    // ==================================== 钉钉Token 相关API ===========================

    /**
     * 使用 Token 初始化账号Client
     *
     * @return Client
     * @throws Exception
     */
    public static com.aliyun.dingtalkoauth2_1_0.Client createTokenClient() throws Exception {
        com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config();
        config.protocol = "https";
        config.regionId = "central";
        return new com.aliyun.dingtalkoauth2_1_0.Client(config);
    }

    /**
     * 封装请求获取钉钉AccessToken
     *
     * @return token
     * @throws Exception 异常信息
     */
    public String getAccessToken() throws Exception {
        String token = dingTalkRedisDAO.get();
        if (token != null) {
            return token;
        }
        com.aliyun.dingtalkoauth2_1_0.Client tokenClient = createTokenClient();
        GetAccessTokenRequest getAccessTokenRequest = new GetAccessTokenRequest()
                .setAppKey(dingTalkProperties.getClientId())
                .setAppSecret(dingTalkProperties.getClientSecret());
        try {
            GetAccessTokenResponse accessToken = tokenClient.getAccessToken(getAccessTokenRequest);
            if (!accessToken.statusCode.equals(200) || com.aliyun.teautil.Common.empty(accessToken.body.accessToken)) {
                log.error("[getAccessToken][获取钉钉 AccessToken 失败]");
            }
            //加入REDIS 缓存中
            log.info("[getAccessToken][获取钉钉 AccessToken 成功]-{}", accessToken.body.accessToken);
            dingTalkRedisDAO.set(accessToken.body.accessToken, accessToken.body.expireIn);
            return accessToken.body.accessToken;
        } catch (TeaException err) {
            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
                // err 中含有 code 和 message 属性，可帮助开发定位问题
                log.error("[getAccessToken][获取钉钉 AccessToken 失败],错误信息为：{}", err.message);
            }
        } catch (Exception _err) {
            TeaException err = new TeaException(_err.getMessage(), _err);
            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
                // err 中含有 code 和 message 属性，可帮助开发定位问题
                log.error("[getAccessToken][获取钉钉 AccessToken 失败],错误信息为：{}", err.message);
            }
        }
        throw exception(DING_TALK_GET_TOKEN_ERROR);
    }

    // ==================================== 钉钉用户 相关API ===========================


    /**
     * 通过userId 获取用户的 unionId
     *
     * @param userId 用户的userId
     * @return 用户的 unionId
     * @throws Exception 错误信息
     */
    public String getUnionId(String userId) throws Exception {
        String unionId = userUnionRedisDAO.get(userId);
        if (unionId != null) {
            return unionId;
        }
        try {
            DingTalkClient client = new DefaultDingTalkClient(DINGTALK_GET_USER_DETAIL_URL);
            OapiV2UserGetRequest req = new OapiV2UserGetRequest();
            req.setUserid(userId);
            req.setLanguage("zh_CN");
            OapiV2UserGetResponse rsp = client.execute(req, getAccessToken());
            if (!rsp.isSuccess() || rsp.getResult() == null
                    || com.aliyun.teautil.Common.empty(rsp.getResult().getUnionid())) {
                log.error("[getUserInfo][获取用户信息失败],用户ID为{}", userId);
                throw exception(DING_TALK_GET_USERID_ERROR);
            }
            userUnionRedisDAO.set(userId, rsp.getResult().getUnionid());
            return rsp.getResult().getUnionid();
        } catch (ApiException err) {
            if (!com.aliyun.teautil.Common.empty(err.getErrCode()) && !com.aliyun.teautil.Common.empty(err.getErrMsg())) {
                log.error("[getUserInfo][获取用户信息失败],错误信息为：{}，用户ID为{}", err.getErrMsg(), userId);
            }
        }
        throw exception(DING_TALK_GET_USERID_ERROR);
    }


    // ==================================== 钉钉消息通知 相关API ===========================


    /**
     * 发送钉钉通知消息（单个按钮）
     *
     * @param userId      用户 userID
     * @param title       标题 发送内容的标题
     * @param description 描述 发送内容的描述，会跟title 进行拼接，结果为markdown模式
     * @param url         处理链接地址
     * @param actionTitle 通知的来源 显示在钉钉通知中的来源
     * @param buttonText  按钮文案
     * @return 发送结果
     * @throws Exception 异常信息
     */
    public SmsSendRespDTO sendNotifyMessage(String userId, String title,
                                            String description, String url, String actionTitle, String buttonText) throws Exception {
        DingTalkClient client = new DefaultDingTalkClient(DINGTALK_SEND_NOTIFY_MESSAGE_URL);
        OapiMessageCorpconversationAsyncsendV2Request request =
                new OapiMessageCorpconversationAsyncsendV2Request();
        request.setAgentId(Long.valueOf(dingTalkProperties.getAgentId()));
        request.setUseridList(userId);
        request.setToAllUser(false);
        OapiMessageCorpconversationAsyncsendV2Request.Msg msg =
                new OapiMessageCorpconversationAsyncsendV2Request.Msg();

        msg.setMsgtype("action_card");
        msg.setActionCard(new OapiMessageCorpconversationAsyncsendV2Request.ActionCard());
        msg.getActionCard().setTitle(actionTitle);
        String descriptionMarkdown = "## " + title + "  \n  " + description;
        msg.getActionCard().setMarkdown(descriptionMarkdown);
        msg.getActionCard().setSingleTitle(buttonText);
        msg.getActionCard().setSingleUrl(url);
        request.setMsg(msg);
        try {
            String accessToken = getAccessToken();
            log.info("[sendNotifyMessage][发送钉钉通知开始][accessToken:{},userId:{},title:{},description:{},url:{},actionTitle:{},buttonText:{}]]",
                    accessToken, userId, title, description, url, actionTitle, buttonText);
            OapiMessageCorpconversationAsyncsendV2Response rsp = client.execute(request, accessToken);
            log.info("[sendNotifyMessage][发送钉钉通知成功，消息ID为:{}]", rsp.getTaskId());
            return new SmsSendRespDTO().setSuccess(rsp.getErrcode() == 0)
                    .setSerialNo(null)
                    .setApiRequestId(rsp.getTaskId().toString())
                    .setApiMsg(rsp.getErrmsg())
                    .setApiCode(rsp.getErrorCode());
        } catch (ApiException err) {
            if (!com.aliyun.teautil.Common.empty(err.getErrCode()) && !com.aliyun.teautil.Common.empty(err.getErrMsg())) {
                log.error("[getUserInfo][发送钉钉通知失败],错误信息为：{}", err.getErrMsg());
                return new SmsSendRespDTO().setSuccess(false)
                        .setSerialNo(null)
                        .setApiMsg(err.getErrMsg())
                        .setApiCode(err.getErrCode());
            }
        }
        return new SmsSendRespDTO().setSuccess(false)
                .setSerialNo(null)
                .setApiMsg("发送钉钉通知失败")
                .setApiCode("1");
    }


    // ==================================== 钉钉代办 相关API ===========================

    /**
     * 使用 Token 初始化账号Client
     *
     * @return Client
     * @throws Exception
     */
    public static Client createTaskClient() throws Exception {
        Config config = new Config();
        config.protocol = "https";
        config.regionId = "central";
        return new Client(config);
    }

    /**
     * 创建钉钉代办
     */
    public SmsSendRespDTO createTodoTask(CreateDingTodoReqVO reqVO) throws Exception {

        Client client = createTaskClient();
        CreateTodoTaskHeaders createTodoTaskHeaders = new CreateTodoTaskHeaders();
        createTodoTaskHeaders.xAcsDingtalkAccessToken = this.getAccessToken();

        CreateTodoTaskRequest.CreateTodoTaskRequestNotifyConfigs notifyConfigs =
                new CreateTodoTaskRequest.CreateTodoTaskRequestNotifyConfigs()
                        .setDingNotify("1"); // 默认为1，官方原话：DING通知配置，目前仅支持取值为1，表示应用内DING。
        CreateTodoTaskRequest.CreateTodoTaskRequestContentFieldList contentFieldList0 =
                new CreateTodoTaskRequest.CreateTodoTaskRequestContentFieldList()
                        .setFieldKey("内容")
                        .setFieldValue(reqVO.getDescription());
        CreateTodoTaskRequest.CreateTodoTaskRequestContentFieldList contentFieldList1 =
                new CreateTodoTaskRequest.CreateTodoTaskRequestContentFieldList()
                        .setFieldKey("发起人")
                        .setFieldValue(reqVO.getCreateUserName());
        CreateTodoTaskRequest.CreateTodoTaskRequestDetailUrl detailUrl =
                new CreateTodoTaskRequest.CreateTodoTaskRequestDetailUrl()
                        .setAppUrl(reqVO.getAppUrl())
                        .setPcUrl(reqVO.getAppUrl());
        CreateTodoTaskRequest createTodoTaskRequest = new CreateTodoTaskRequest()
                .setOperatorId(reqVO.getCreator()) // 创建者的unionId ，即流程发起者的钉钉unionId
                .setSourceId(reqVO.getSourceId()) // 自定义唯一
                .setSubject(reqVO.getTitle()) // 待办标题，最大长度1024。
                .setCreatorId(reqVO.getCreator())  // 创建者的unionId ，即流程发起者的钉钉unionId
                .setDetailUrl(detailUrl)
                .setExecutorIds(reqVO.getExecutor()) //执行者，即审批人的钉钉unionId
                .setParticipantIds(reqVO.getParticipantIds()) //参与者
                .setIsOnlyShowExecutor(reqVO.getIsOnlyShowExecutor())
                .setContentFieldList(Arrays.asList(contentFieldList0, contentFieldList1))
                .setPriority(reqVO.getPriority())
                .setDueTime(reqVO.getDueTime())
                .setNotifyConfigs(notifyConfigs);
        log.info("[createTask][创建钉钉代办开始]，{}", JSONUtil.toJsonStr(createTodoTaskRequest));
        try {
            CreateTodoTaskResponse response = client.createTodoTaskWithOptions(reqVO.getCreator(), createTodoTaskRequest,
                    createTodoTaskHeaders, new RuntimeOptions());
            log.info("[createTask][创建钉钉代办成功]，{}", JSONUtil.toJsonStr(response));
            return new SmsSendRespDTO().setSuccess(response.statusCode == 200)
                    .setSerialNo(response.body.id) //taskID
                    .setApiMsg("创建钉钉代办成功")
                    .setApiRequestId(response.body.requestId)
                    .setApiCode(response.statusCode.toString());
        } catch (Exception _err) {
            TeaException err = new TeaException(_err.getMessage(), _err);
            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
                // err 中含有 code 和 message 属性，可帮助开发定位问题
                log.error("[createTask][创建钉钉代办失败],错误信息为：{}", err.message);
                return new SmsSendRespDTO().setSuccess(false)
                        .setSerialNo(null)
                        .setApiMsg(err.message)
                        .setApiCode(err.code);
            }
        }
        return new SmsSendRespDTO().setSuccess(false)
                .setSerialNo(null)
                .setApiMsg("创建钉钉代办失败")
                .setApiCode("1");
    }


    /**
     * 更新钉钉代办
     *
     * @param unionId 操作者unionId
     * @param taskId  代办ID
     * @throws Exception 异常信息
     */
    public void updateTodoTask(String unionId, String taskId) throws Exception {
        Client client = createTaskClient();
        UpdateTodoTaskHeaders updateTodoTaskHeaders = new UpdateTodoTaskHeaders();
        updateTodoTaskHeaders.xAcsDingtalkAccessToken = getAccessToken();
        UpdateTodoTaskRequest updateTodoTaskRequest = new UpdateTodoTaskRequest()
                .setOperatorId(unionId)
                .setDone(true);
        try {
            client.updateTodoTaskWithOptions(unionId, taskId, updateTodoTaskRequest, updateTodoTaskHeaders, new RuntimeOptions());
        } catch (TeaException err) {
            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
                // err 中含有 code 和 message 属性，可帮助开发定位问题
                log.error("[updateTodoTask][更新钉钉代办失败],错误信息为：{}", err.message);
            }

        } catch (Exception _err) {
            TeaException err = new TeaException(_err.getMessage(), _err);
            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
                // err 中含有 code 和 message 属性，可帮助开发定位问题
                log.error("[updateTodoTask][更新钉钉代办失败],错误信息为：{}", err.message);
            }
        }
    }

}
