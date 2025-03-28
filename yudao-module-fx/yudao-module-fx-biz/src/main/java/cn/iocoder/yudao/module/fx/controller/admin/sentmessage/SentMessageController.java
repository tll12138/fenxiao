package cn.iocoder.yudao.module.fx.controller.admin.sentmessage;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.fx.controller.admin.sentmessage.vo.SentMessagePageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.sentmessage.vo.SentMessageRespVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.sentmessage.SentMessageDO;
import cn.iocoder.yudao.module.fx.service.sentmessage.SentMessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 分销发货要求消息")
@RestController
@RequestMapping("/fx/sent-message")
@Validated
public class SentMessageController {

    @Resource
    private SentMessageService sentMessageService;

    @GetMapping("/get")
    @Operation(summary = "获得分销发货要求消息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('fx:sent-message:query')")
    public CommonResult<SentMessageRespVO> getSentMessage(@RequestParam("id") Long id) {
        SentMessageDO sentMessage = sentMessageService.getSentMessage(id);
        return success(BeanUtils.toBean(sentMessage, SentMessageRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得分销发货要求消息分页")
    @PreAuthorize("@ss.hasPermission('fx:sent-message:query')")
    public CommonResult<PageResult<SentMessageRespVO>> getSentMessagePage(@Valid SentMessagePageReqVO pageReqVO) {
        PageResult<SentMessageDO> pageResult = sentMessageService.getSentMessagePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, SentMessageRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出分销发货要求消息 Excel")
    @PreAuthorize("@ss.hasPermission('fx:sent-message:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportSentMessageExcel(@Valid SentMessagePageReqVO pageReqVO,
                                       HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<SentMessageDO> list = sentMessageService.getSentMessagePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "分销发货要求消息.xls", "数据", SentMessageRespVO.class,
                BeanUtils.toBean(list, SentMessageRespVO.class));
    }

    @GetMapping("/push")
    @Operation(summary = "发送消息提醒")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('fx:sent-message:query')")
    public CommonResult<Boolean> executeSendMsg(@RequestParam("id") Long id) {
        sentMessageService.executeSendMsg(id);
        return success(true);
    }

}