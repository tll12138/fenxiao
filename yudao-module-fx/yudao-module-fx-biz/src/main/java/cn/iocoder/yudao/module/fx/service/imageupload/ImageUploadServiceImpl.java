package cn.iocoder.yudao.module.fx.service.imageupload;

import cn.iocoder.yudao.module.fx.dal.dataobject.imageupload.ImageUploadDO;
import cn.iocoder.yudao.module.fx.dal.mysql.imageupload.ImageUploadMapper;
import cn.iocoder.yudao.module.fx.utils.ObjectUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

/**
 * @author 管理员
 */
@Service
@Validated
public class ImageUploadServiceImpl implements ImageUploadService {
    @Resource
    private ImageUploadMapper imageUploadMapper;

    /**
     * 图片上传
     *
     * @param file
     */
    @Override
    public Integer createImageUpload(MultipartFile file) {
        try {
            ImageUploadDO imageUpload = new ImageUploadDO();
            imageUpload.setFileName(file.getOriginalFilename());
            imageUpload.setFilePath("data/uploads/pay-warrants"); // 实际使用时需设置正确路径
            Blob blob = new SerialBlob(file.getBytes());
            imageUpload.setFileContent(blob);

            return imageUploadMapper.insert(imageUpload);
        } catch (IOException | SQLException e) {
            return -1;
        }
    }

    /**
     * 根据id查询图片
     *
     * @param id
     */
    @Override
    public ResponseEntity<?> getImageById(Long id) {
        ImageUploadDO imageUploadDO = imageUploadMapper.selectById(id);
        if (!ObjectUtils.isBlankOrNull(imageUploadDO)) {
            try {
                Blob blob = imageUploadDO.getFileContent();
                byte[] content = blob.getBytes(1, (int) blob.length());
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_JPEG); // 根据实际图片类型调整
                headers.setContentDispositionFormData("attachment", imageUploadDO.getFileName());
                return new ResponseEntity<>(content, headers, HttpStatus.OK);
            } catch (SQLException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"msg\": \"获取支付证明失败\"}");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"msg\": \"未找到支付证明\"}");
        }
    }
}