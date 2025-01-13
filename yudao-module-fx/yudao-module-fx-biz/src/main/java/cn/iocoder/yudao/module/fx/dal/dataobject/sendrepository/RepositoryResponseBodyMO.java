package cn.iocoder.yudao.module.fx.dal.dataobject.sendrepository;

import lombok.Data;

@Data
public class RepositoryResponseBodyMO {
    private String msg;
    private String code;
    private RepositoryResponseBodyDataMO data;
}
