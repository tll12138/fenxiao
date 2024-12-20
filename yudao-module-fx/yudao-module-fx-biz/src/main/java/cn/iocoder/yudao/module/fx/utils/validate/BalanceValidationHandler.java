package cn.iocoder.yudao.module.fx.utils.validate;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;
import cn.iocoder.yudao.module.fx.dal.dataobject.customeraccount.CustomerAccountDO;
import cn.iocoder.yudao.module.fx.dal.dataobject.ordersinfo.OrdersInfoDO;
import cn.iocoder.yudao.module.fx.dal.mysql.customeraccount.CustomerAccountMapper;
import cn.iocoder.yudao.module.fx.utils.orderinfo.OrderProcessingContext;
import cn.iocoder.yudao.module.fx.utils.template.BaseProcessingContext;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants.CUSTOMER_ACCOUNT_NOT_EXISTS;
import static cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants.ORDERS_ACCOUNT_BALANCES_NOT_ENOUGH;


/**
 * 余额校验器
 */
@Service
@Slf4j
public class BalanceValidationHandler extends ValidationHandler{


    @Resource
    private CustomerAccountMapper customerAccountMapper;

    @Override
    public void handle(BaseProcessingContext context) {
        OrderProcessingContext orderContext = (OrderProcessingContext) context;
        OrdersInfoDO orderInfo = orderContext.getOrderInfo();
        Long distributorId = orderInfo.getDistributorId();
        Long supplierId = orderInfo.getSupplierId();

        // 使用静态方法 查询
        CustomerAccountDO customerAccountDO =
                Db.getOne(Wrappers.lambdaQuery(CustomerAccountDO.class)
                .eq(CustomerAccountDO::getDistributorId, distributorId)
                .eq(CustomerAccountDO::getCompany, supplierId));

        if (customerAccountDO == null){
            throw exception(CUSTOMER_ACCOUNT_NOT_EXISTS);
        }
        // 判断余额是否够用
        BigDecimal balance = customerAccountDO.getBalance();
        // 订单金额
        BigDecimal salesAmount = orderInfo.getSalesAmount();
        if (balance.compareTo(salesAmount) < 0){
            Integer code = ORDERS_ACCOUNT_BALANCES_NOT_ENOUGH.getCode();
            String message = String.format(ORDERS_ACCOUNT_BALANCES_NOT_ENOUGH.getMsg(), balance);
            throw exception(new ErrorCode(code, message));
        }
        // 更新账户余额
        customerAccountDO.setBalance(balance.subtract(salesAmount));
        Db.updateById(customerAccountDO);

        log.info("[BalanceValidationHandler] 余额校验通过...");
        nextHandle(context);
    }


}