package cn.iocoder.yudao.module.fx.service.jstorderout;

import cn.iocoder.yudao.module.fx.controller.admin.jstorderout.vo.JstOrderOutSaveReqVO;
import cn.iocoder.yudao.module.fx.dal.dataobject.jstorderout.JstOrderOutDO;

import javax.validation.Valid;

/**
 * 聚水潭发货回传中间表 Service 接口
 *
 * @author 管理员
 */
public interface JstOrderOutService {

    /**
     * 创建聚水潭发货回传中间表
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createJstOrderOut(@Valid JstOrderOutSaveReqVO createReqVO);

    /**
     * 更新聚水潭发货回传中间表
     *
     * @param updateReqVO 更新信息
     */
    void updateJstOrderOut(@Valid JstOrderOutSaveReqVO updateReqVO);

    /**
     * 聚水潭订单出库调用
     *
     * @param soId        销售单号
     * @param expressName 快递公司名称
     * @param express     快递单号
     * @param expressCode 快递编码
     */
    void handleCallSaleOrderOut(String soId, String expressName, String express, String expressCode);

    /**
     * 根据内部单号创建或更新聚水潭发货回传中间表
     *
     * @param jstOrderOutDO
     * @return
     */
    int mergeOrderOut(JstOrderOutDO jstOrderOutDO);

    /**
     * 定时任务自动发货和钉钉通知
     */
    void autoSendAndDingTalk();

}