package cn.iocoder.yudao.module.fx.dal.dataobject.imageupload;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Blob;

/**
 * @author tll
 * @date 2025-07-18 13:49:50
 */
@TableName("fx_image_upload")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageUploadDO extends BaseDO {

    @TableId
    private Long id;

    private String fileName;

    private String filePath;

    private Blob fileContent;

}
