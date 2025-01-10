package cn.iocoder.yudao.module.fx.dal.dataobject.goodsarchives;

import lombok.Data;

@Data
public class GoodsResponseBodyMO {
    private String msg;
    private String code;
    private GoodsResponseBodyDataMO data;
}
