package cn.iocoder.yudao.module.fx.controller.admin.carcptaud.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author tll
 * @date 2025-06-25 11:22:46
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileVO {
    private String fileId;   // 文件唯一标识（关键）
    private String fileName; // 原始文件名
    private String filePath; // 文件存储路径（可选）
}
