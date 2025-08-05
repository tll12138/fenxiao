package cn.iocoder.yudao.module.fx.controller.admin.imageupload;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.fx.service.imageupload.ImageUploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.error;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

/**
 * @author tll
 * @date 2025-07-18 13:47:52
 */
@RestController
@RequestMapping("/fx/image_upload")
@Validated
@Slf4j
public class ImageUploadController {

    @Resource
    private ImageUploadService imageUploadService;

    @PostMapping("/import")
    public CommonResult<ResponseEntity<?>> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            Integer imageUpload = imageUploadService.createImageUpload(file);
            return success(ResponseEntity.ok().body("{\"code\": 0, \"data\": {\"fileId\": " + imageUpload + "}}"));
        } catch (Exception e) {
            return error(-1, e.getMessage());
        }
    }

    @GetMapping("/getPayProof/{fileId}")
    public CommonResult<ResponseEntity<?>> getPayProof(@PathVariable Long fileId) {
        ResponseEntity<?> image = imageUploadService.getImageById(fileId);
        return success(image);
    }
}
