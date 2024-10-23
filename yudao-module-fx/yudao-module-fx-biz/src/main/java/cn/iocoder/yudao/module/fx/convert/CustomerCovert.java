package cn.iocoder.yudao.module.fx.convert;

import cn.hutool.core.util.ArrayUtil;
import cn.iocoder.yudao.module.fx.controller.admin.customeraccount.vo.CustomerAccountRespVO;
import cn.iocoder.yudao.module.fx.controller.admin.customeraddress.vo.CustomerAddressDetailRespVO;
import cn.iocoder.yudao.module.fx.controller.admin.customerinfo.vo.CustomerInfoDetailRespVO;
import cn.iocoder.yudao.module.fx.controller.admin.customerinfo.vo.CustomerInfoSaveReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.subcompanyinfo.vo.SubCompanyInfoRespVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.customeraccount.CustomerAccountDO;
import cn.iocoder.yudao.module.fx.dal.dataobject.customeraddress.CustomerAddressDO;
import cn.iocoder.yudao.module.fx.dal.dataobject.customerinfo.CustomerInfoDO;
import cn.iocoder.yudao.module.fx.dal.dataobject.subcompanyinfo.SubCompanyInfoDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.List;

/**
 * @author zrl
 * @date 2024/7/17
 */
@Mapper
public interface CustomerCovert {

    CustomerCovert INSTANCE = Mappers.getMapper(CustomerCovert.class);

    SubCompanyInfoRespVO convert(SubCompanyInfoDO subCompanyInfoDO);

    List<SubCompanyInfoRespVO> convertSubCompanyList(List<SubCompanyInfoDO> subCompanyInfoDOList);

    CustomerInfoDO convert(CustomerInfoSaveReqVO customerInfoSaveReqVO);

    CustomerInfoDetailRespVO convert2Detail(CustomerInfoDO customerInfoDO);

    CustomerAddressDetailRespVO convert2AddressDetail(CustomerAddressDO addressDO);

    List<CustomerAddressDetailRespVO> convert2AddressDetailList(List<CustomerAddressDO> addressDOList);

    List<CustomerAccountRespVO> convertAccount(List<CustomerAccountDO> customerAccountDO);

}
