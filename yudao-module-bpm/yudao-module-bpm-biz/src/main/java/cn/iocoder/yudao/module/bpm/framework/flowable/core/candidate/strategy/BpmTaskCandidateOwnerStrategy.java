package cn.iocoder.yudao.module.bpm.framework.flowable.core.candidate.strategy;

import cn.iocoder.yudao.framework.common.util.string.StrUtils;
import cn.iocoder.yudao.module.bpm.framework.flowable.core.candidate.BpmTaskCandidateStrategy;
import cn.iocoder.yudao.module.bpm.framework.flowable.core.enums.BpmTaskCandidateStrategyEnum;
import org.flowable.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Set;

import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

/**
 * 发起人自己 {@link BpmTaskCandidateStrategy} 实现类
 *
 * @author tll
 */
@Component
public class BpmTaskCandidateOwnerStrategy implements BpmTaskCandidateStrategy {

    @Override
    public BpmTaskCandidateStrategyEnum getStrategy() {
        return BpmTaskCandidateStrategyEnum.OWNER;
    }

    @Override
    public void validateParam(String param) {

    }

    @Override
    public Set<Long> calculateUsers(DelegateExecution execution, String param) {
        return StrUtils.splitToLongSet(Objects.requireNonNull(getLoginUserId()).toString());
    }

    /**
     * 是否一定要输入参数
     *
     * @return 是否
     */
    @Override
    public boolean isParamRequired() {
        return false;
    }

}