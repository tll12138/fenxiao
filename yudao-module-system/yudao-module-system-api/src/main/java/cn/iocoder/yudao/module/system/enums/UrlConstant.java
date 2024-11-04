package cn.iocoder.yudao.module.system.enums;

/**
 * @author zrl
 * @date 2024/10/31
 */
public interface UrlConstant {

    /**
     * 钉钉API URL
     */

    String DINGTALK_GET_USER_DETAIL_URL = "https://oapi.dingtalk.com/topapi/v2/user/get";
    String DINGTALK_SEND_NOTIFY_MESSAGE_URL = "https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2";
}
