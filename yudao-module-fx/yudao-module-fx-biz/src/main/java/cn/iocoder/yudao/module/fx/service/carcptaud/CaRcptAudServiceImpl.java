package cn.iocoder.yudao.module.fx.service.carcptaud;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.bpm.api.task.BpmProcessInstanceApi;
import cn.iocoder.yudao.module.bpm.api.task.dto.BpmProcessInstanceCreateReqDTO;
import cn.iocoder.yudao.module.fx.controller.admin.carcptaud.vo.CaRcptAudPageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.carcptaud.vo.CaRcptAudSaveReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.fromaccount.vo.FromAccountSaveReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.carcptaud.CaRcptAudDO;
import cn.iocoder.yudao.module.fx.dal.dataobject.fromaccount.FromAccountDO;
import cn.iocoder.yudao.module.fx.dal.dataobject.holidays.HolidaysDO;
import cn.iocoder.yudao.module.fx.dal.mysql.carcptaud.CaRcptAudMapper;
import cn.iocoder.yudao.module.fx.dal.mysql.holidays.HolidaysMapper;
import cn.iocoder.yudao.module.fx.service.fromaccount.FromAccountService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Map;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants.CA_RCPT_AUD_ID_NOT_EXISTS;
import static cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants.CA_RCPT_AUD_NOT_EXISTS;

/**
 * 客商账户收款审核 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class CaRcptAudServiceImpl implements CaRcptAudService {

    @Resource
    private CaRcptAudMapper caRcptAudMapper;
    @Resource
    private BpmProcessInstanceApi processInstanceApi;
    @Resource
    private HolidaysMapper holidaysMapper;
    @Resource
    private FromAccountService fromAccountService;
    /**
     * 收款单对应的流程定义 KEY
     */
    public static final String PROCESS_KEY = "account_recharge";

    @Override
    public Integer createCaRcptAud(CaRcptAudSaveReqVO createReqVO) {
        // 插入
        CaRcptAudDO caRcptAud = BeanUtils.toBean(createReqVO, CaRcptAudDO.class);
        caRcptAudMapper.insert(caRcptAud);
        // 返回
        return caRcptAud.getId();
    }

    @Override
    public void updateCaRcptAud(CaRcptAudSaveReqVO updateReqVO) {
        // 校验存在
        validateCaRcptAudExists(updateReqVO.getId());
        // 更新
        CaRcptAudDO updateObj = BeanUtils.toBean(updateReqVO, CaRcptAudDO.class);
        caRcptAudMapper.updateById(updateObj);
    }

    @Override
    public void deleteCaRcptAud(Integer id) {
        // 校验存在
        validateCaRcptAudExists(id);
        // 删除
        caRcptAudMapper.deleteById(id);
    }

    private void validateCaRcptAudExists(Integer id) {
        if (caRcptAudMapper.selectById(id) == null) {
            throw exception(CA_RCPT_AUD_NOT_EXISTS);
        }
    }

    @Override
    public CaRcptAudDO getCaRcptAud(Integer id) {
        return caRcptAudMapper.selectById(id);
    }

    /**
     * 获得客商账户收款审核
     *
     * @param processInstanceId 编号
     * @return 客商账户收款审核
     */
    @Override
    public CaRcptAudDO getByProcessInstanceId(String processInstanceId) {
        if (processInstanceId == null) {
            throw exception(CA_RCPT_AUD_ID_NOT_EXISTS);
        }
        return caRcptAudMapper.selectOne(new LambdaQueryWrapper<CaRcptAudDO>().eq(CaRcptAudDO::getProcessInstanceId, processInstanceId));
    }

    @Override
    public PageResult<CaRcptAudDO> getCaRcptAudPage(CaRcptAudPageReqVO pageReqVO) {
        return caRcptAudMapper.selectPage(pageReqVO);
    }

    /**
     * 用户创建流程实例
     *
     * @param loginUserId
     */
    @Override
    public void startProcessInstance(Long loginUserId, CaRcptAudSaveReqVO createReqVO) {
        // 先保存
        CaRcptAudDO caRcptAud = BeanUtils.toBean(createReqVO, CaRcptAudDO.class);
        Long count = holidaysMapper.selectCount(HolidaysDO::getHoliday, DateUtil.format(DateUtil.date(), "yyyy-MM-dd"));
        caRcptAud.setIsWeek(count > 0 ? "1" : "0");
        caRcptAud.setSubmiter(loginUserId.toString());
        caRcptAudMapper.insert(caRcptAud);
        // 返回id
        Integer id = caRcptAud.getId();
        // 发起 BPM 流程
        Map<String, Object> processInstanceVariables = BeanUtil.beanToMap(createReqVO);
        String processInstanceId = processInstanceApi.createProcessInstance(loginUserId,
                new BpmProcessInstanceCreateReqDTO().setProcessDefinitionKey(PROCESS_KEY)
                        .setVariables(processInstanceVariables).setBusinessKey(String.valueOf(id)));

        // 将工作流的编号，更新到审核单中
        caRcptAudMapper.updateById(
                new CaRcptAudDO()
                        .setId(id)
                        .setProcessInstanceId(processInstanceId));
    }

    /**
     * 根据流程号获取审批单信息
     *
     * @param processInstanceId
     */
    @Override
    public CaRcptAudDO getInfoByPIId(String processInstanceId) {
        return caRcptAudMapper.selectOne(new LambdaQueryWrapperX<CaRcptAudDO>().eq(CaRcptAudDO::getProcessInstanceId, processInstanceId));
    }

    /**
     * 校验打款账户是否存在，不存在新增，存在就更新打款账号累计次数与金额
     */
    @Override
    public void updateOrInsertPayAcc(CaRcptAudDO caRcptAudDO) {
        FromAccountDO fromAcc = fromAccountService.getFromAccountByCusAndPayAccName(caRcptAudDO.getCustomer(), caRcptAudDO.getPaymentAccountName());
        if (fromAcc != null) {
            fromAccountService.updateFromAccount(new FromAccountSaveReqVO()
                    .setId(fromAcc.getId())
                    .setTotalNum(fromAcc.getTotalNum() + 1)
                    .setTotalAmt(fromAcc.getTotalAmt().add(caRcptAudDO.getReceive())));
        } else {
            fromAccountService.createFromAccount(new FromAccountSaveReqVO()
                    .setCustomerId(caRcptAudDO.getCustomer())
                    .setCustomerName(caRcptAudDO.getCustomerName())
                    .setTotalAmt(caRcptAudDO.getReceive())
                    .setTotalNum(1)
                    .setIsActive("1")
                    .setAccount(caRcptAudDO.getAccount())
                    .setAccountName(caRcptAudDO.getAccountName())
                    .setRemark(DateUtil.now() + "自动新增"));
        }
    }

}