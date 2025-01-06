package cn.iocoder.yudao.module.fx.dal.dataobject.customerinfo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Date;


@Data
public class ChannelVo {
    //供应商业务员名称
    @JSONField(name = "supplier_salesman_name")
    private String salesman;

    //联系人名称
    @JSONField(name = "contact_name")
    private String contact;

    //分销商等级
    @JSONField(name = "dis_level")
    private String distributorLevel;

    //联系人手机号
    @JSONField(name = "contact_phone")
    private String phone;

    //供应商备注
    @JSONField(name = "supplier_remark")
    private String remark;

    //申请时间
    @JSONField(name = "apply_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date applyTime;

    //开单名称
    @JSONField(name = "bill_name")
    private String displayName;

    @JSONField(name = "originator")
    private String originator;

    //分销商编号
    @JSONField(name = "channel_co_id")
    private String distributorNum;

    //合作时间
    @JSONField(name = "confirm_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date confirmTime;

    //分销商商公司名
    @JSONField(name = "co_name")
    private String distributorName;

    //合作状态-0:待授权；1:待审核；2:合作中;3:已拒绝；4:已终止
    @JSONField(name = "status")
    private String status;
}
