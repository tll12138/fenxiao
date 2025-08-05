package cn.iocoder.yudao.module.fx.controller.admin.carcptaud;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.fx.controller.admin.carcptaud.vo.CaRcptAudPageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.carcptaud.vo.CaRcptAudRespVO;
import cn.iocoder.yudao.module.fx.controller.admin.carcptaud.vo.CaRcptAudSaveReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.carcptaud.vo.FileVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.carcptaud.CaRcptAudDO;
import cn.iocoder.yudao.module.fx.service.carcptaud.CaRcptAudService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.error;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "管理后台 - 客商账户收款审核")
@RestController
@RequestMapping("/fx/ca-rcpt-aud")
@Validated
@Slf4j
public class CaRcptAudController {

    @Resource
    private CaRcptAudService caRcptAudService;

    @PostMapping("/create")
    @Operation(summary = "创建客商账户收款审核")
    @PreAuthorize("@ss.hasPermission('fx:ca-rcpt-aud:create')")
    public CommonResult<Integer> createCaRcptAud(@Valid @RequestBody CaRcptAudSaveReqVO createReqVO) {
        return success(caRcptAudService.createCaRcptAud(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新客商账户收款审核")
    @PreAuthorize("@ss.hasPermission('fx:ca-rcpt-aud:update')")
    public CommonResult<Boolean> updateCaRcptAud(@Valid @RequestBody CaRcptAudSaveReqVO updateReqVO) {
        caRcptAudService.updateCaRcptAud(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除客商账户收款审核")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('fx:ca-rcpt-aud:delete')")
    public CommonResult<Boolean> deleteCaRcptAud(@RequestParam("id") Integer id) {
        caRcptAudService.deleteCaRcptAud(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得客商账户收款审核")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('fx:ca-rcpt-aud:query')")
    public CommonResult<CaRcptAudRespVO> getCaRcptAud(@RequestParam("id") Integer id) {
        CaRcptAudDO caRcptAud = caRcptAudService.getCaRcptAud(id);
        return success(BeanUtils.toBean(caRcptAud, CaRcptAudRespVO.class));
    }

    @GetMapping("/getByProcessInstanceId")
    @Operation(summary = "根据流程号获取客商账户收款信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('fx:ca-rcpt-aud:query')")
    public CommonResult<CaRcptAudRespVO> getByProcessInstanceId(@RequestParam("processInstanceId") String processInstanceId) {
        CaRcptAudDO caRcptAud = caRcptAudService.getByProcessInstanceId(processInstanceId);
        return success(BeanUtils.toBean(caRcptAud, CaRcptAudRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得客商账户收款审核分页")
    @PreAuthorize("@ss.hasPermission('fx:ca-rcpt-aud:query')")
    public CommonResult<PageResult<CaRcptAudRespVO>> getCaRcptAudPage(@Valid CaRcptAudPageReqVO pageReqVO) {
        PageResult<CaRcptAudDO> pageResult = caRcptAudService.getCaRcptAudPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, CaRcptAudRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出客商账户收款审核 Excel")
    @PreAuthorize("@ss.hasPermission('fx:ca-rcpt-aud:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportCaRcptAudExcel(@Valid CaRcptAudPageReqVO pageReqVO,
                                     HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<CaRcptAudDO> list = caRcptAudService.getCaRcptAudPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "客商账户收款审核.xls", "数据", CaRcptAudRespVO.class,
                BeanUtils.toBean(list, CaRcptAudRespVO.class));
    }

    @PostMapping("/start-by-start-user")
    @Operation(summary = "用户创建流程实例", description = "发起流程")
    @PreAuthorize("@ss.hasPermission('fx:ca-rcpt-aud:update')")
    public CommonResult<Boolean> startProcessInstance(@Valid @RequestBody CaRcptAudSaveReqVO createReqVO) {
        caRcptAudService.startProcessInstance(getLoginUserId(), createReqVO);
        return success(true);
    }

    @PostMapping("/import")
    public CommonResult<FileVO> uploadPayWarrant(@RequestParam("file") MultipartFile file) {
        // 1. 校验文件类型（仅允许图片/PDF）
        String contentType = file.getContentType();
        if (!isValidFileType(contentType)) {
            return error(-1, "仅支持JPG/PNG/PDF文件");
        }

        // 2. 生成文件唯一ID（如UUID）
        String fileId = UUID.randomUUID().toString();
        String originalFileName = file.getOriginalFilename();

        // 3. 保存文件到存储服务（示例：本地存储）
        String filePath = saveFileToStorage(file, fileId, originalFileName);

        // 4. 返回文件元信息（前端需要fileId关联业务单据）
        return success(new FileVO(fileId, originalFileName, filePath));
    }

    // 校验文件类型
    private boolean isValidFileType(String contentType) {
        return contentType != null && (
                contentType.equals("image/jpeg") ||
                        contentType.equals("image/png") ||
                        contentType.equals("application/pdf")
        );
    }

    // 保存文件到本地（生产环境建议用OSS/MinIO）
    private String saveFileToStorage(MultipartFile file, String fileId, String originalFileName) {
        try {
            // 确保文件名不包含非法字符
            String safeOriginalFileName = sanitizeFileName(originalFileName);
            String ext = safeOriginalFileName.substring(safeOriginalFileName.lastIndexOf("."));
            String basePath = "/data/uploads/pay-warrants/"; // 存储根路径
            Path destDir = Paths.get(basePath, LocalDate.now().toString());

            // 确保目标文件夹存在，如果不存在则创建
            Files.createDirectories(destDir);
            log.info("目标文件夹已创建或存在: {}", destDir);

            // 构建完整文件路径
            Path destFilePath = destDir.resolve(fileId + ext);
            log.info("尝试保存文件到: {}", destFilePath);

            // 检查目录权限
            if (!Files.isWritable(destDir)) {
                throw new IOException("目录不可写: " + destDir);
            }

            // 使用NIO API保存文件，提供更详细的异常信息
            try (var inputStream = file.getInputStream()) {
                Files.copy(inputStream, destFilePath, StandardCopyOption.REPLACE_EXISTING);
                log.info("文件保存成功: {}", destFilePath);
                return destFilePath.toString();
            }
        } catch (IOException e) {
            // 记录详细的异常信息
            log.error("文件保存失败: {}", e.getMessage(), e);
            throw new RuntimeException("文件保存失败: " + e.getMessage(), e);
        } catch (Exception e) {
            // 捕获其他异常
            log.error("处理文件时发生意外错误: {}", e.getMessage(), e);
            throw new RuntimeException("处理文件时发生意外错误: " + e.getMessage(), e);
        }
    }

    // 清理文件名，移除非法字符
    private String sanitizeFileName(String fileName) {
        // 移除或替换Windows和Unix系统中的非法字符
        return fileName.replaceAll("[\\\\/:*?\"<>|]", "_");
    }

}