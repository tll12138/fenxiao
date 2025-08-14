package cn.iocoder.yudao.module.fx.dal.dataobject.billapply;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author tll
 * @date 2025-08-14 14:14:13
 */
@Data
public class BillApplyCallbackMO {

    // 发票申请id
    private Integer id;

    //发票附件
    private List<MultipartFile> files;

    //oa流程id
    private String soId;
}
