package cn.iocoder.yudao.module.fx.service.ec2jstorderitem;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.fx.controller.admin.ec2jstorderitem.vo.Ec2jstOrderitemPageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.ec2jstorderitem.vo.Ec2jstOrderitemSaveReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.ec2jstorderitem.Ec2jstOrderitemDO;
import cn.iocoder.yudao.module.fx.dal.mysql.ec2jstorderitem.Ec2jstOrderitemMapper;
import cn.iocoder.yudao.module.fx.utils.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants.EC2JST_ORDERITEM_NOT_EXISTS;

/**
 * 分销订单上传详情中间 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class Ec2jstOrderitemServiceImpl implements Ec2jstOrderitemService {

    @Resource
    private Ec2jstOrderitemMapper ec2jstOrderitemMapper;

    @Override
    public void createEc2jstOrderitem(Ec2jstOrderitemSaveReqVO createReqVO) {
        // 插入
        Ec2jstOrderitemDO ec2jstOrderitem = BeanUtils.toBean(createReqVO, Ec2jstOrderitemDO.class);
        ec2jstOrderitemMapper.insert(ec2jstOrderitem);
        // 返回
    }

    private void validateEc2jstOrderitemExists(String id) {
        if (ec2jstOrderitemMapper.selectById(id) == null) {
            throw exception(EC2JST_ORDERITEM_NOT_EXISTS);
        }
    }

    @Override
    public Ec2jstOrderitemDO getEc2jstOrderitem(String id) {
        return ec2jstOrderitemMapper.selectById(id);
    }

    @Override
    public PageResult<Ec2jstOrderitemDO> getEc2jstOrderitemPage(Ec2jstOrderitemPageReqVO pageReqVO) {
        return ec2jstOrderitemMapper.selectPage(pageReqVO);
    }

    @Override
    public List<Ec2jstOrderitemDO> getEc2jstOrderItemListByMainIds(List<Integer> mainIds) {
        if (CollectionUtil.isEmpty(mainIds)) {
            return Collections.emptyList();
        }
        return ec2jstOrderitemMapper.selectList(new LambdaQueryWrapper<Ec2jstOrderitemDO>().in(Ec2jstOrderitemDO::getMainid, mainIds));
    }


}