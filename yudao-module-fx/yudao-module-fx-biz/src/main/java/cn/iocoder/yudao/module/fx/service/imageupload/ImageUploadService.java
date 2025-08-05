package cn.iocoder.yudao.module.fx.service.imageupload;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 管理员
 */
public interface ImageUploadService {

    /**
     * 图片上传
     */
    Integer createImageUpload(MultipartFile file);

    /**
     * 根据id查询图片
     */
    ResponseEntity<?> getImageById(Long id);
}