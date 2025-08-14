package cn.iocoder.yudao.module.fx.service.billapply;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.fx.controller.admin.billapply.vo.BillApplyPageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.billapply.vo.BillApplySaveReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.billapply.BillApplyDO;
import cn.iocoder.yudao.module.fx.dal.mysql.billapply.BillApplyMapper;
import cn.iocoder.yudao.module.fx.utils.ZipUtils;
import cn.iocoder.yudao.module.infra.api.file.FileApi;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants.BILL_APPLY_NOT_EXISTS;

/**
 * 发票申请 Service 实现类
 *
 * @author 管理员
 */
@Slf4j
@Service
@Validated
public class BillApplyServiceImpl implements BillApplyService {

    @Resource
    private BillApplyMapper billApplyMapper;
    @Resource
    private FileApi fileApi;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer createBillApply(BillApplySaveReqVO createReqVO) {
        // 插入
        BillApplyDO billApply = BeanUtils.toBean(createReqVO, BillApplyDO.class);
        billApplyMapper.insert(billApply);
        // 返回
        return billApply.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBillApply(BillApplySaveReqVO updateReqVO) {
        // 校验存在
        validateBillApplyExists(updateReqVO.getId());
        // 更新
        BillApplyDO updateObj = BeanUtils.toBean(updateReqVO, BillApplyDO.class);
        billApplyMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBillApply(Integer id) {
        // 校验存在
        validateBillApplyExists(id);
        // 删除
        billApplyMapper.deleteById(id);
    }

    private BillApplyDO validateBillApplyExists(Integer id) {
        BillApplyDO billApplyDO = billApplyMapper.selectById(id);
        if (billApplyDO == null) {
            throw exception(BILL_APPLY_NOT_EXISTS);
        }
        return billApplyDO;
    }

    @Override
    public BillApplyDO getBillApply(Integer id) {
        return billApplyMapper.selectById(id);
    }

    @Override
    public PageResult<BillApplyDO> getBillApplyPage(BillApplyPageReqVO pageReqVO) {
        return billApplyMapper.selectPage(pageReqVO);
    }

    /**
     * 推送发票申请
     *
     * @param id 编号
     */
    @Override
    public void pushBillApply(Integer id) {
        // 校验存在
        BillApplyDO billApply = validateBillApplyExists(id);
        // 推送
        billApply.setApplyDate(DateUtil.today());
        billApplyMapper.updateById(billApply);
    }

    /**
     * 处理发票申请回调
     */
    @Override
    public void handleBillApplyCallback(Integer id, String soId, MultipartFile[] files) {
        String info = "发票申请回调处理";
        List<String> fileUrls = Lists.newArrayList();

        try {
            // 校验发票申请是否存在
            BillApplyDO billApply = validateBillApplyExists(id);
            billApply.setRid(soId);
            billApply.setBillDate(DateUtil.today());

            // 处理每个上传的文件
            for (MultipartFile file : files) {
                if (file.isEmpty()) {
                    log.warn("跳过空文件: {}", file.getOriginalFilename());
                    continue;
                }

                // 获取文件名和创建临时目录
                String originalFileName = file.getOriginalFilename();
                String baseTempPath = "D:/filerealpath/" + billApply.getId() + "/" + System.currentTimeMillis() + "/";
                String originalFileDir = baseTempPath + "original/";
                String extractDir = baseTempPath + "extracted/";

                File originalDir = new File(originalFileDir);
                File extractDirectory = new File(extractDir);
                if (!originalDir.exists()) originalDir.mkdirs();
                if (!extractDirectory.exists()) extractDirectory.mkdirs();

                try {
                    // 保存原文件
                    File originalFile = new File(originalFileDir + originalFileName);
                    file.transferTo(originalFile);
                    log.info("原文件保存成功: {}, 大小: {} bytes", originalFile.getAbsolutePath(), originalFile.length());

                    // 执行解压操作
                    String sourcePath = originalFile.getAbsolutePath().replace("/", "//");
                    String targetDir = extractDir.replace("/", "//");
                    // 注意：第三个参数应该是解压后的文件路径，而非原文件名
                    String targetFilePath = targetDir + FilenameUtils.getBaseName(originalFileName);

                    log.info("开始解压: 源文件={}, 目标目录={}", sourcePath, targetDir);
                    boolean unzipResult = ZipUtils.unzip1(sourcePath, targetDir, targetFilePath);
                    log.info("解压操作返回结果: {}", unzipResult);

                    // 验证解压目录是否有文件
                    File[] extractedFiles = extractDirectory.listFiles();
                    boolean hasExtractedFiles = extractedFiles != null && extractedFiles.length > 0;
                    log.info("解压目录文件数量: {}", hasExtractedFiles ? extractedFiles.length : 0);

                    // 收集待上传文件
                    List<File> filesToUpload = new ArrayList<>();
                    if (unzipResult && hasExtractedFiles) {
                        // 解压成功且有文件，收集解压目录中的文件
                        collectFiles(extractDirectory, filesToUpload);
                        log.info("成功收集到 {} 个解压后的文件", filesToUpload.size());
                    } else {
                        // 解压失败或无文件，使用原文件
                        log.warn("解压失败或无文件，将上传原文件: {}", originalFileName);
                        filesToUpload.add(originalFile);
                    }

                    // 上传文件
                    for (File fileToUpload : filesToUpload) {
                        if (!fileToUpload.exists()) {
                            log.error("文件不存在，跳过上传: {}", fileToUpload.getAbsolutePath());
                            continue;
                        }
                        if (fileToUpload.length() == 0) {
                            log.error("空文件，跳过上传: {}", fileToUpload.getAbsolutePath());
                            continue;
                        }

                        String fileUrl = fileApi.createFile(
                                fileToUpload.getName(),
                                null,
                                FileUtil.readBytes(fileToUpload));
                        fileUrls.add(fileUrl);
                        log.info("文件上传成功: {} ({} bytes)", fileToUpload.getName(), fileToUpload.length());
                    }

                } finally {
                    // 清理临时文件
                    log.info("清理临时目录: {}", baseTempPath);
                    deleteFolders(baseTempPath);
                }
            }

            // 更新记录
            billApply.setDocument(fileUrls.toString());
            log.info("处理完成，共上传 {} 个文件", fileUrls.size());
            billApplyMapper.updateById(billApply);

        } catch (Exception e) {
            log.error("{}处理失败", info, e);
            throw new RuntimeException(info + "处理失败: " + e.getMessage(), e);
        }
    }

    /**
     * 递归收集目录下的所有文件（增加日志输出）
     */
    private void collectFiles(File dir, List<File> fileList) {
        log.info("开始收集文件: {}", dir != null ? dir.getAbsolutePath() : "null");

        if (dir == null || !dir.exists()) {
            log.warn("目录不存在或为空: {}", dir != null ? dir.getAbsolutePath() : "null");
            return;
        }

        if (!dir.isDirectory()) {
            log.warn("不是目录: {}", dir.getAbsolutePath());
            return;
        }

        File[] files = dir.listFiles();
        if (files == null) {
            log.warn("无法获取目录文件列表: {}", dir.getAbsolutePath());
            return;
        }

        log.info("目录 {} 包含 {} 个文件/目录", dir.getAbsolutePath(), files.length);

        for (File file : files) {
            if (file.isDirectory()) {
                log.info("进入子目录: {}", file.getAbsolutePath());
                collectFiles(file, fileList);
            } else {
                log.info("添加文件到上传列表: {} ({} bytes)", file.getAbsolutePath(), file.length());
                fileList.add(file);
            }
        }
    }

    /**
     * 递归删除文件夹及内容
     */
    private void deleteFolders(String folderPath) {
        File folder = new File(folderPath);
        if (!folder.exists()) {
            return;
        }

        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteFolders(file.getAbsolutePath());
                } else {
                    file.delete();
                }
            }
        }
        folder.delete();
    }


}