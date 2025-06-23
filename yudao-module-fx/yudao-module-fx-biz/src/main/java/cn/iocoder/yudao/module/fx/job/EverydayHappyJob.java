package cn.iocoder.yudao.module.fx.job;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.iocoder.yudao.framework.quartz.core.handler.JobHandler;
import cn.iocoder.yudao.framework.tenant.core.aop.TenantIgnore;
import cn.iocoder.yudao.module.system.util.dd.DingTalkUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 每日废话任务
 *
 * @author tll
 */
@Slf4j
@Component("EverydayHappyJob")
public class EverydayHappyJob implements JobHandler {

    @Resource
    private DingTalkUtils dingTalkUtils;

    @Override
    @TenantIgnore
    public String execute(String param) throws Exception {
//        String url1 = "https://apis.tianapi.com/caihongpi/index?key=e9c423b9f86e828b9e048a79d6e63278";
//        String response1 = HttpUtil.get(url1);
//        // 新增JSON解析逻辑
//        JSONObject json1 = JSONUtil.parseObj(response1);
//        if (json1.getInt("code") != 200) {
//            log.error("接口调用失败：{}", json1.getStr("msg"));
//            return "失败";
//        }
//        String content = json1.getJSONObject("result").getStr("content");
//        content = "# 彩虹屁 \n### " + content;
//        dingTalkUtils.sendNotifyMarkdown("15967343191", "每日彩虹屁", content);

//        // 调用第二个接口
        String url2 = "https://apis.tianapi.com/one/index?key=e9c423b9f86e828b9e048a79d6e63278&rand=1";
        String response2 = HttpUtil.get(url2);
        JSONObject json2 = JSONUtil.parseObj(response2);
        if (json2.getInt("code") != 200) {
            log.error("接口调用失败：{}", json2.getStr("msg"));
            return "失败";
        }
        JSONObject result3 = json2.getJSONObject("result");
        String oneMsg = "# " + result3.getStr("word");
        Thread.sleep(1000);
        dingTalkUtils.sendNotifyMarkdown("15967343191", "每日一句", oneMsg);

        // 调用第三个接口
        String url3 = "https://apis.tianapi.com/tianqi/index?key=e9c423b9f86e828b9e048a79d6e63278&city=330481&type=1";
        String response3 = HttpUtil.get(url3);
        JSONObject json3 = JSONUtil.parseObj(response3);
        if (json3.getInt("code") != 200) {
            log.error("接口调用失败：{}", json3.getStr("msg"));
            return "失败";
        }
        JSONObject result1 = json3.getJSONObject("result");
        StringBuilder weatherMsg = new StringBuilder();

        // 基础天气信息
        weatherMsg.append(String.format("# %s%s今日天气\n", result1.getStr("province"), result1.getStr("area")))
                .append(String.format("### %s %s\n", result1.getStr("date"), result1.getStr("week")))
                .append(String.format("- 天气状况：%s\n", result1.getStr("weather")))
                .append(String.format("- 温度：%s (低温%s/高温%s)\n",
                        result1.getStr("real"),
                        result1.getStr("lowest"),
                        result1.getStr("highest")))
                .append(String.format("- 风力：%s%s\n", result1.getStr("wind"), result1.getStr("windsc")))
                .append(String.format("- 日出日落：%s/%s\n", result1.getStr("sunrise"), result1.getStr("sunset")));

        // 预警信息处理
        if (result1.containsKey("alarmlist") && !result1.getJSONArray("alarmlist").isEmpty()) {
            weatherMsg.append("\n⚠️ **天气预警**\n");
            result1.getJSONArray("alarmlist").forEach(alarm -> {
                JSONObject alarmObj = (JSONObject) alarm;
                weatherMsg.append(String.format("- %s%s%s预警：%s\n",
                        alarmObj.getStr("province"),
                        alarmObj.getStr("city"),
                        alarmObj.getStr("level"),
                        alarmObj.getStr("content")));
            });
        }

        // 生活建议
        weatherMsg.append("\n💡 小贴士：").append(result1.getStr("tips"));
        Thread.sleep(1000);
        dingTalkUtils.sendNotifyMarkdown("15967343191", "海宁今日天气预报", weatherMsg.toString());

        // 调用第四个接口
        String url4 = "https://apis.tianapi.com/star/index?key=e9c423b9f86e828b9e048a79d6e63278&astro=capricorn";
        String response4 = HttpUtil.get(url4);
        StringBuilder fortuneMsg = new StringBuilder("# 摩羯座今日运势报告 \n### ");
        JSONObject json4 = JSONUtil.parseObj(response4);
        if (json4.getInt("code") != 200) {
            log.error("接口调用失败：{}", json4.getStr("msg"));
            return "失败";
        }
        JSONObject result2 = json4.getJSONObject("result");
        result2.getJSONArray("list").forEach(item -> {
            JSONObject fortuneItem = (JSONObject) item;
            String type = fortuneItem.getStr("type");
            String content3 = fortuneItem.getStr("content");

            // 特殊处理概述类目
            if ("今日概述".equals(type)) {
                fortuneMsg.append("\n### ").append(type).append("\n### ").append(content3);
            } else {
                fortuneMsg.append(String.format(" \n### - %s：%s", type, content3));
            }
        });
        Thread.sleep(1000);
        dingTalkUtils.sendNotifyMarkdown("15967343191", "摩羯座每日运势", fortuneMsg.toString());

        // 调用第五个接口
        String url5 = "https://api.kuleu.com/api/MP4_xiaojiejie?type=json";
        String response5 = HttpUtil.get(url5);
        JSONObject json5 = JSONUtil.parseObj(response5);
        if (json5.getInt("code") != 200) {
            log.error("接口调用失败：{}", json5.getStr("msg"));
            return "失败";
        }
        String mp4Video = json5.getStr("mp4_video");

        Thread.sleep(1000);
        dingTalkUtils.sendNotifyMarkdown("15967343191", "每日视频", mp4Video);

        log.info("[execute][EverydayHappyJob]");
        return "EverydayHappyJob";
    }

}
