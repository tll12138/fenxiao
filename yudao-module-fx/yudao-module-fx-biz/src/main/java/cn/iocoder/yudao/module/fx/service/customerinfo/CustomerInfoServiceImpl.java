package cn.iocoder.yudao.module.fx.service.customerinfo;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.fx.controller.admin.customeraccount.vo.CustomerAccountRespVO;
import cn.iocoder.yudao.module.fx.controller.admin.customerinfo.vo.CustomerInfoDetailPageRespVO;
import cn.iocoder.yudao.module.fx.controller.admin.customerinfo.vo.CustomerInfoDetailRespVO;
import cn.iocoder.yudao.module.fx.controller.admin.customerinfo.vo.CustomerInfoPageReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.customerinfo.vo.CustomerInfoSaveReqVO;
import cn.iocoder.yudao.module.fx.controller.admin.customerinfo.vo.CustomerInfoSyncVO;
import cn.iocoder.yudao.module.fx.convert.CustomerCovert;
import cn.iocoder.yudao.module.fx.dal.dataobject.customeraccount.CustomerAccountDO;
import cn.iocoder.yudao.module.fx.dal.dataobject.customeraddress.CustomerAddressDO;
import cn.iocoder.yudao.module.fx.dal.dataobject.customerinfo.ChannelVo;
import cn.iocoder.yudao.module.fx.dal.dataobject.customerinfo.CustomerInfoDO;
import cn.iocoder.yudao.module.fx.dal.dataobject.customerinfo.CustomerResponseBodyMO;
import cn.iocoder.yudao.module.fx.dal.dataobject.subcompanyinfo.SubCompanyInfoDO;
import cn.iocoder.yudao.module.fx.dal.mysql.customeraccount.CustomerAccountMapper;
import cn.iocoder.yudao.module.fx.dal.mysql.customeraddress.CustomerAddressMapper;
import cn.iocoder.yudao.module.fx.dal.mysql.customerinfo.CustomerInfoMapper;
import cn.iocoder.yudao.module.fx.dal.mysql.subcompanyinfo.SubCompanyInfoMapper;
import cn.iocoder.yudao.module.fx.service.jushuitanapi.JuShuiTanApiService;
import cn.iocoder.yudao.module.fx.utils.CollectionUtil;
import cn.iocoder.yudao.module.system.controller.admin.user.vo.user.UserSaveReqVO;
import cn.iocoder.yudao.module.system.service.permission.PermissionService;
import cn.iocoder.yudao.module.system.service.user.AdminUserService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.google.common.collect.Sets;
import com.jushuitan.api.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.fx.constant.FieldConstant.FX_CUSTOMER_PASSWORD;
import static cn.iocoder.yudao.module.fx.constant.FieldConstant.FX_CUSTOMER_ROLE_ID;
import static cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants.CUSTOMER_ACCOUNT_CREATE_FAIL;
import static cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants.CUSTOMER_ADDRESS_UPDATE_FAIL;
import static cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants.CUSTOMER_INFO_DATE_PARSE_ERROR;
import static cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants.CUSTOMER_INFO_ID_NULL;
import static cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants.CUSTOMER_INFO_NOT_EXISTS;
import static cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants.CUSTOMER_INFO_SYNC_EMPTY;
import static cn.iocoder.yudao.module.fx.enums.ErrorCodeConstants.CUSTOMER_INFO_SYNC_ERROR;

/**
 * 分销商基础信息 Service 实现类
 *
 * @author 管理员
 */
@Slf4j
@Service
@Validated
public class CustomerInfoServiceImpl implements CustomerInfoService {

    @Resource
    private CustomerInfoMapper customerInfoMapper;
    @Resource
    private CustomerAccountMapper customerAccountMapper;
    @Resource
    private CustomerAddressMapper customerAddressMapper;
    @Resource
    private SubCompanyInfoMapper companyInfoMapper;
    @Resource
    private JuShuiTanApiService juShuiTanApiService;
    @Resource
    private AdminUserService adminUserService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private PermissionService permissionService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createCustomerInfo(CustomerInfoSaveReqVO createReqVO) {
        // 插入分销商基础信息
        CustomerInfoDO customerInfo = CustomerCovert.INSTANCE.convert(createReqVO);
        customerInfoMapper.insert(customerInfo);

        // 插入地址信息
        createCustomerAddressList(customerInfo.getId(), createReqVO.getCustomerAddressList());

        // 插入账号信息
        createCustomerAccountList(customerInfo);

        // 返回
        return customerInfo.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCustomerInfo(CustomerInfoSaveReqVO updateReqVO) {
        // 校验存在
        validateCustomerInfoExists(updateReqVO.getId());

        // 更新
        CustomerInfoDO updateObj = BeanUtils.toBean(updateReqVO, CustomerInfoDO.class);
        customerInfoMapper.updateById(updateObj);

        // 更新子表
        updateCustomerAddressList(updateReqVO.getId(), updateReqVO.getCustomerAddressList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCustomerInfo(Long id) {
        // 校验存在
        validateCustomerInfoExists(id);
        // 删除
        customerInfoMapper.deleteById(id);

        // 删除子表
        deleteCustomerAccountById(id);
    }

    private void validateCustomerInfoExists(Long id) {
        if (customerInfoMapper.selectById(id) == null) {
            throw exception(CUSTOMER_INFO_NOT_EXISTS);
        }
    }

    @Override
    public CustomerInfoDO getCustomerInfo(Long id) {
        return customerInfoMapper.selectById(id);
    }

    /**
     * 获得分销商基础信息
     *
     * @param customerName 分销商名称
     * @return 分销商基础信息
     */
    @Override
    public CustomerInfoDO getCustomerInfoByName(String customerName) {
        return customerInfoMapper.selectOne(new LambdaQueryWrapper<CustomerInfoDO>().eq(CustomerInfoDO::getDistributorName, customerName).last("limit 1"));
    }

    @Override
    public CustomerInfoDetailRespVO getCustomerInfoDetail(Long id) {
        //获取分销商基础信息
        CustomerInfoDO customerInfoDO = customerInfoMapper.selectById(id);

        //将基础信息转换为详细返回信息对象
        CustomerInfoDetailRespVO customerInfoDetailRespVO =
                CustomerCovert.INSTANCE.convert2Detail(customerInfoDO);

        //获取分销商地址信息
        LambdaQueryWrapper<CustomerAddressDO> addressQueryWrapper = new LambdaQueryWrapper<>();
        addressQueryWrapper.eq(CustomerAddressDO::getDistributorId, id);
        List<CustomerAddressDO> customerAddressDOS = customerAddressMapper.selectList(addressQueryWrapper);
        customerInfoDetailRespVO.setCustomerAddressList(CustomerCovert.INSTANCE.convert2AddressDetailList(customerAddressDOS));

        //获取分销商账号信息
        LambdaQueryWrapper<CustomerAccountDO> accountQueryWrapper = new LambdaQueryWrapper<>();
        accountQueryWrapper.eq(CustomerAccountDO::getDistributorId, customerInfoDO.getId());
        List<CustomerAccountDO> accountDOList = customerAccountMapper.selectList(accountQueryWrapper);
        List<CustomerAccountRespVO> customerAccountRespVOS = CustomerCovert.INSTANCE.convertAccount(accountDOList);
        customerInfoDetailRespVO.setCustomerAccounts(customerAccountRespVOS);

        return customerInfoDetailRespVO;
    }

    @Override
    public List<CustomerInfoDO> getAllCustomerInfo() {
        return customerInfoMapper.selectList();
    }

    @Override
    public PageResult<CustomerInfoDO> getCustomerInfoPage(CustomerInfoPageReqVO pageReqVO) {
        return customerInfoMapper.selectPage(pageReqVO);
    }

    @Override
    public PageResult<CustomerInfoDetailPageRespVO> getCustomerInfoDetailPage(CustomerInfoPageReqVO pageReqVO) {
        PageResult<CustomerInfoDO> customerInfoDOPageResult = customerInfoMapper.selectPage(pageReqVO);
        List<Long> idList = customerInfoDOPageResult.getList().stream().map(CustomerInfoDO::getId).collect(Collectors.toList());
        if (idList.isEmpty()) {
            return new PageResult<>();
        }
        MPJLambdaWrapper<CustomerInfoDO> in = new MPJLambdaWrapper<CustomerInfoDO>()
                .selectAll(CustomerInfoDO.class)
                .selectCollection(CustomerAccountDO.class, CustomerInfoDetailPageRespVO::getCustomerAddressList)
                .leftJoin(CustomerAccountDO.class, CustomerAccountDO::getDistributorId, CustomerInfoDO::getId)
                .in(CustomerAccountDO::getDistributorId, idList);
        List<CustomerInfoDetailPageRespVO> customerInfoDetailPageRespVOS = customerInfoMapper.selectJoinList(CustomerInfoDetailPageRespVO.class, in);
        return new PageResult<>(customerInfoDetailPageRespVOS, customerInfoDOPageResult.getTotal());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void syncCustomers() {
        //递归获取所有分销商信息
        executeCustomers(1);
        List<CustomerInfoDO> allCustomerInfo = getAllCustomerInfo();
        // 新增：生成分销商映射并缓存
        Map<Long, String> distributorMap = allCustomerInfo.stream()
                .collect(Collectors.toMap(
                        CustomerInfoDO::getId,
                        CustomerInfoDO::getDistributorName,
                        (oldVal, newVal) -> newVal)); // 处理重复key的情况
        String redisKey = "customer:info:distributor-mapping";
        stringRedisTemplate.opsForValue().set(
                redisKey,
                JSONUtil.toJsonStr(distributorMap),
                30, // 保留30天
                TimeUnit.DAYS
        );
    }


    // ==================== 子表（分销商账号） ====================

    @Override
    public List<CustomerAccountDO> getCustomerAccountListById(Long id) {
        return customerAccountMapper.selectListById(id);
    }

    private void createCustomerAccountList(CustomerInfoDO customerInfoDO) {
        LambdaQueryWrapper<SubCompanyInfoDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SubCompanyInfoDO::getIsInitCompany, 1);
        List<SubCompanyInfoDO> subCompanyInfoDOS = companyInfoMapper.selectList(queryWrapper);
        List<CustomerAccountDO> accountDOList = subCompanyInfoDOS.stream().map((item) -> {
            CustomerAccountDO customerAccountDO = new CustomerAccountDO();
            customerAccountDO.setDistributorId(customerInfoDO.getId());//设置分销商ID为已插入的基础信息返回的ID
            customerAccountDO.setCompany(Math.toIntExact(item.getId()));
            customerAccountDO.setBalance(new BigDecimal(0));
            int index = subCompanyInfoDOS.indexOf(item);
            customerAccountDO.setAccountId(customerInfoDO.getDistributorNum() + "-" + index);
            return customerAccountDO;
        }).collect(Collectors.toList());

        Boolean b = customerAccountMapper.insertBatch(accountDOList);
        if (!b) {
            throw exception(CUSTOMER_ACCOUNT_CREATE_FAIL);
        }
    }


    private void deleteCustomerAccountById(Long id) {
        customerAccountMapper.delete(
                new LambdaQueryWrapper<CustomerAccountDO>()
                        .eq(CustomerAccountDO::getDistributorId, id)
        );
    }

    // ==================== 子表（分销商地址） ====================

    @Override
    public List<CustomerAddressDO> getCustomerAddressListById(Long id) {
        return customerAddressMapper.selectListById(id);
    }

    /**
     * 获得无账号分销商地址列表
     */
    @Override
    public List<CustomerInfoDO> getCustomerInfoByNoAccount() {
        return customerInfoMapper.getCustomerInfoByNoAccount();
    }

    /**
     * 同步OA客商信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class) // 事务注解，确保同步操作原子性
    public void syncOaCustomers(List<CustomerInfoSyncVO> syncVOList) {
        // 入参校验
        if (CollectionUtils.isEmpty(syncVOList)) {
            log.warn("同步OA客商信息：入参列表为空");
            throw exception(CUSTOMER_INFO_SYNC_EMPTY);
        }

        log.info("开始同步OA客商信息，待处理数量：{}", syncVOList.size());

        try {
            List<Long> allIds = syncVOList.stream()
                    .map(CustomerInfoSyncVO::getId)
                    .peek(id -> {
                        if (id == null) { // 校验ID不能为空
                            throw exception(CUSTOMER_INFO_ID_NULL);
                        }
                    })
                    .collect(Collectors.toList());

            // 批量查询已存在的客户信息
            Map<Long, CustomerInfoDO> existCustomerMap = customerInfoMapper.selectBatchIds(allIds).stream()
                    .collect(Collectors.toMap(CustomerInfoDO::getId, customer -> customer));

            // 2. 区分新增和更新列表
            List<CustomerInfoDO> insertList = new ArrayList<>();
            List<CustomerInfoDO> updateList = new ArrayList<>();

            for (CustomerInfoSyncVO syncVO : syncVOList) {
                CustomerInfoDO existDO = existCustomerMap.get(syncVO.getId());

                // 转换VO到DO（统一处理日期解析）
                CustomerInfoDO customerDO = convertToDO(syncVO, existDO);

                if (existDO == null) {
                    insertList.add(customerDO);
                } else {
                    updateList.add(customerDO);
                }
            }

            // 3. 批量执行数据库操作
            if (!insertList.isEmpty()) {
                customerInfoMapper.insertBatch(insertList);
                //获取要新增的分销商ID
                List<String> newCustomerIds = insertList.stream()
                        .map(CustomerInfoDO::getDistributorNum)
                        .collect(Collectors.toList());
                //获取已存在的分销商账号
                Set<String> existingCustomerIdsWithAccount = adminUserService.getCustomerIdsWithExistingAccount(newCustomerIds);
                //新增分销商账号
                for (CustomerInfoDO customerInfoDO : insertList) {
                    String account = customerInfoDO.getDistributorNum();
                    if (!existingCustomerIdsWithAccount.contains(account)) { // 不存在账号才创建
                        UserSaveReqVO userSaveReqVO = new UserSaveReqVO();
                        userSaveReqVO.setUsername(customerInfoDO.getDistributorNum());
                        userSaveReqVO.setNickname(customerInfoDO.getDistributorName());
                        userSaveReqVO.setPassword(FX_CUSTOMER_PASSWORD);
                        userSaveReqVO.setCustomerId(customerInfoDO.getId());
                        Long userId = adminUserService.createUser(userSaveReqVO);
                        permissionService.assignUserRole(userId, Sets.newHashSet(FX_CUSTOMER_ROLE_ID));
                        log.info("为分销商[ID:{}]创建账号成功", account);
                    } else {
                        log.info("分销商[ID:{}]已存在账号，无需重复创建", account);
                    }
                }
                log.info("OA客商信息同步：新增成功{}条", insertList.size());
            }
            if (!updateList.isEmpty()) {
                customerInfoMapper.updateBatch(updateList);
                log.info("OA客商信息同步：更新成功{}条", updateList.size());
            }
            log.info("OA客商信息同步完成，总处理{}条", syncVOList.size());

        } catch (Exception e) {
            log.error("OA客商信息同步失败，原因：{}", e.getMessage(), e);
            throw exception(CUSTOMER_INFO_SYNC_ERROR, e.getMessage());
        }
    }

    /**
     * 转换VO到DO，处理日期解析
     */
    private CustomerInfoDO convertToDO(CustomerInfoSyncVO syncVO, CustomerInfoDO existDO) {
        CustomerInfoDO customerDO = existDO != null ? existDO : new CustomerInfoDO();

        BeanUtil.copyProperties(syncVO, customerDO, "latestOrderDate");

        try {
            customerDO.setLatestOrderDate(StrUtil.isNotBlank(syncVO.getLatestOrderDate()) ? DateUtil.parseDate(syncVO.getLatestOrderDate()) : null);
        } catch (Exception e) {
            log.error("解析最新订单日期失败，ID:{}，日期字符串:{}",
                    syncVO.getId(), syncVO.getLatestOrderDate(), e);
            throw exception(CUSTOMER_INFO_DATE_PARSE_ERROR, syncVO.getLatestOrderDate());
        }

        return customerDO;
    }

    private void createCustomerAddressList(Long id, List<CustomerAddressDO> list) {
        list.forEach(o -> o.setDistributorId(id));
        customerAddressMapper.insertBatch(list);
    }

    private void updateCustomerAddressList(Long id, List<CustomerAddressDO> list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        list.forEach(o -> o.setDistributorId(id));
        Boolean b = customerAddressMapper.insertOrUpdateBatch(list);
        if (!b) {
            throw exception(CUSTOMER_ADDRESS_UPDATE_FAIL);
        }
    }

    private void executeCustomers(int pageNum) {
        String biz = String.format("{\"page_num\":\"%s\",\"page_size\":\"100\"}", pageNum);
        // 执行接口调用
        try {
            ApiResponse response = juShuiTanApiService.execute("customerSyncUrl", biz);
            String body = response.getBody();
            CustomerResponseBodyMO bodyMO = JSONObject.parseObject(body, CustomerResponseBodyMO.class);
            List<ChannelVo> channelVos = bodyMO.getData().getChannelVos();
            if (CollectionUtil.isNotEmpty(channelVos)) {
                // 把分销商信息存库
                List<CustomerInfoDO> list = new ArrayList<>(channelVos.size());
                Set<String> distributorNums = new HashSet<>(channelVos.size());
                for (ChannelVo channel : channelVos) {
                    distributorNums.add(channel.getDistributorNum());
                }
                List<CustomerInfoDO> existingInfos = customerInfoMapper.selectList(new LambdaQueryWrapper<CustomerInfoDO>().in(CustomerInfoDO::getDistributorNum, distributorNums));
                Map<String, CustomerInfoDO> existingInfoMap = existingInfos.stream().collect(Collectors.toMap(CustomerInfoDO::getDistributorNum, Function.identity()));
                for (ChannelVo channel : channelVos) {
                    CustomerInfoDO one = existingInfoMap.get(channel.getDistributorNum());
                    if (one == null) {
                        one = new CustomerInfoDO();
                        existingInfoMap.put(channel.getDistributorNum(), one);
                    }
                    one.setUpdateTime(DateUtil.parseLocalDateTime(DateUtil.now()));
                    one.setCreateTime(DateUtil.parseLocalDateTime(DateUtil.now()));
                    BeanUtil.copyProperties(channel, one);
                    list.add(one);
                }
                customerInfoMapper.insertOrUpdateBatch(list);
            }
            Integer total = bodyMO.getData().getTotal();
            int totalPage = (total / 100) + 1;
            if (total > 0 && (totalPage > pageNum)) {
                executeCustomers(pageNum + 1);
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}