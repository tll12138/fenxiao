package cn.iocoder.yudao.module.fx.service.billapply;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpRequest;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.fx.controller.admin.billapply.vo.BillApplyPageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.billapply.vo.BillApplySaveReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.billapply.BillApplyDO;
import cn.iocoder.yudao.module.fx.dal.dataobject.billapply.BillApplyDetailDO;
import cn.iocoder.yudao.module.fx.dal.mysql.billapply.BillApplyDetailMapper;
import cn.iocoder.yudao.module.fx.dal.mysql.billapply.BillApplyMapper;
import cn.iocoder.yudao.module.fx.utils.RSAUtil;
import cn.iocoder.yudao.module.fx.utils.ZipUtils;
import cn.iocoder.yudao.module.infra.api.file.FileApi;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.diboot.core.exception.BusinessException;
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

    private static final String appid = "e0ab4a73-e9c6-4ae5-9dac-f21a7807991a";
    private static final String head = "https://www.puqiportal.com/";

    @Resource
    private BillApplyMapper billApplyMapper;
    @Resource
    private FileApi fileApi;
    @Resource
    private BillApplyDetailMapper billApplyDetailMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer createBillApply(BillApplySaveReqVO createReqVO) {
        // 插入
        BillApplyDO billApply = BeanUtils.toBean(createReqVO, BillApplyDO.class);
        billApplyMapper.insert(billApply);
        // 插入子表
        createBillApplyDetailList(billApply.getId(), createReqVO.getBillApplyDetails());
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
        // 更新子表
        updateBillApplyDetailList(updateReqVO.getId(), updateReqVO.getBillApplyDetails());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBillApply(Integer id) {
        // 校验存在
        validateBillApplyExists(id);
        // 删除
        billApplyMapper.deleteById(id);
        // 删除子表
        billApplyDetailMapper.deleteByMainId(id);
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
        // 1. 校验发票申请存在性
        BillApplyDO billApply = validateBillApplyExists(id);

        // 2. 更新申请日期
        updateBillApplyDate(billApply);
        log.info("准备推送发票申请，id：{}，申请信息：{}", id, billApply);

        try {
            // 3. 获取spk和secret
            JSONObject registResult = callRegistApi();
            String spk = registResult.getString("spk");
            String secret = registResult.getString("secret");

            // 4. 加密secret
            String rsaSecret = encryptSecret(secret, spk);

            // 5. 获取token
            String token = getToken(rsaSecret);

            // 6. 构建请求数据
            JSONObject mainData = buildMainData(billApply, spk);

            // 7. 调用创建请求接口
            callCreateRequestApi(mainData, token, spk);

        } catch (Exception e) {
            log.error("推送发票申请失败，id：{}", id, e);
            throw new BusinessException("推送发票申请失败：" + e.getMessage(), e);
        }
    }

    /**
     * 更新发票申请日期
     */
    private void updateBillApplyDate(BillApplyDO billApply) {
        billApply.setApplyDate(DateUtil.today());
        billApplyMapper.updateById(billApply);
    }

    /**
     * 调用注册接口获取spk和secret
     */
    private JSONObject callRegistApi() {
        String url = head + "api/ec/dev/auth/regist";
        log.info("调用注册接口，url：{}", url);

        String response = HttpRequest.post(url)
                .header("Content-Type", "application/json")
                .header("appid", appid)
                .timeout(20000)
                .execute()
                .body();

        log.info("注册接口响应：{}", response);
        return parseApiResponse(response, "获取spk和secret");
    }

    /**
     * 加密secret
     */
    private String encryptSecret(String secret, String spk) {
        try {
            String rsaSecret = RSAUtil.getRSA(secret, spk);
            log.info("RSA加密后的secret：{}", rsaSecret);
            return rsaSecret;
        } catch (Exception e) {
            throw new BusinessException("RSA加密secret失败", e);
        }
    }

    /**
     * 获取token
     */
    private String getToken(String rsaSecret) {
        String url = head + "api/ec/dev/auth/applytoken";
        log.info("调用获取token接口，url：{}", url);

        String response = HttpRequest.post(url)
                .header("Content-Type", "application/json")
                .header("appid", appid)
                .header("secret", rsaSecret)
                .timeout(20000)
                .execute()
                .body();

        log.info("token接口响应：{}", response);
        JSONObject result = parseApiResponse(response, "获取token");
        return result.getString("token");
    }

    /**
     * 构建请求数据
     */
    private JSONObject buildMainData(BillApplyDO billApply, String spk) {
        JSONArray fieldList = new JSONArray();

        // 添加字段到列表
        addField(fieldList, "re_name", billApply.getApplyMan());
        addField(fieldList, "fplx", billApply.getBillType());
        addField(fieldList, "saleorder", billApply.getSaleOrder());
        addField(fieldList, "ahead", billApply.getBillHead());
        addField(fieldList, "maker", "1101");
        addField(fieldList, "email", billApply.getEmail());
        addField(fieldList, "gfmc", billApply.getPurchaserName());
        addField(fieldList, "taxno", billApply.getTaxNo());
        addField(fieldList, "bankno", billApply.getBankNo());
        addField(fieldList, "address", billApply.getAddress());
        addField(fieldList, "amount", billApply.getTotalAmount());
        addField(fieldList, "jehjdx", billApply.getTotalAmount());
        addField(fieldList, "if_remote", "1");

        JSONObject mainData = new JSONObject();
        mainData.put("mainData", fieldList);
        mainData.put("requestName", buildRequestName(billApply));
        mainData.put("workflowId", 47038);

        return mainData;
    }

    /**
     * 构建请求名称
     */
    private String buildRequestName(BillApplyDO billApply) {
        return String.format("XS07-开票申请-系统管理员-%s-(%s)-%s",
                DateUtil.now(),
                billApply.getApplyMan(),
                billApply.getTotalAmount());
    }

    /**
     * 向JSONArray添加字段
     */
    private void addField(JSONArray array, String fieldName, Object fieldValue) {
        JSONObject fieldObj = new JSONObject();
        fieldObj.put("fieldName", fieldName);
        fieldObj.put("fieldValue", fieldValue);
        array.add(fieldObj);
    }

    /**
     * 调用创建请求接口
     */
    private void callCreateRequestApi(JSONObject mainData, String token, String spk) throws Exception {
        String url = head + "/api/workflow/paService/doCreateRequest";
        log.info("调用创建请求接口，url：{}，请求数据：{}", url, mainData);

        String encryptedUserId = RSAUtil.getRSA("1", spk);
        String response = HttpRequest.post(url)
                .header("Content-Type", "application/json")
                .header("appid", appid)
                .header("token", token)
                .header("userid", encryptedUserId)
                .body(mainData.toJSONString())
                .timeout(20000)
                .execute()
                .body();

        log.info("创建请求接口响应：{}", response);
    }

    /**
     * 解析API响应通用方法
     */
    private JSONObject parseApiResponse(String response, String operation) {
        if (response == null || response.isEmpty()) {
            throw new BusinessException(operation + "响应为空");
        }

        try {
            JSONObject json = JSONObject.parseObject(response);
            if (json == null) {
                throw new BusinessException(operation + "响应解析失败");
            }
            return json;
        } catch (Exception e) {
            throw new BusinessException(operation + "响应解析异常: " + response, e);
        }
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
     * 获得发票申请详情列表
     *
     * @param mainId 主表id
     * @return 发票申请详情列表
     */
    @Override
    public List<BillApplyDetailDO> getBillApplyDetailListByMainId(Integer mainId) {
        return billApplyDetailMapper.selectListByMainId(mainId);
    }

    private void createBillApplyDetailList(Integer mainId, List<BillApplyDetailDO> list) {
        list.forEach(o -> o.setMainId(mainId));
        billApplyDetailMapper.insertBatch(list);
    }

    private void updateBillApplyDetailList(Integer mainId, List<BillApplyDetailDO> list) {
        deleteBillApplyDetailByMainId(mainId);
        list.forEach(o -> o.setId(null).setUpdater(null).setUpdateTime(null));
        createBillApplyDetailList(mainId, list);
    }

    private void deleteBillApplyDetailByMainId(Integer mainId) {
        billApplyDetailMapper.deleteByMainId(mainId);
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