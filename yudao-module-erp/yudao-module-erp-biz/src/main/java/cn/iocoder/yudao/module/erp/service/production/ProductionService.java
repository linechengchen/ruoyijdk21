package cn.iocoder.yudao.module.erp.service.production;

import java.util.*;

import cn.iocoder.yudao.module.erp.dal.dataobject.product.ErpProductDO;
import cn.iocoder.yudao.module.erp.dal.dataobject.purchase.ErpPurchaseOrderItemDO;
import jakarta.validation.*;
import cn.iocoder.yudao.module.erp.controller.admin.production.vo.*;
import cn.iocoder.yudao.module.erp.dal.dataobject.production.ProductionDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * ERP 生产 Service 接口
 *
 * @author 成行
 */
public interface ProductionService {

    /**
     * 创建ERP 生产
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createProduction(@Valid ProductionSaveReqVO createReqVO);

    /**
     * 更新ERP 生产
     *
     * @param updateReqVO 更新信息
     */
    void updateProduction(@Valid ProductionSaveReqVO updateReqVO);

    /**
     * 删除ERP 生产
     *
     * @param id 编号
     */
    void deleteProduction(Long id);

    /**
     * 获得ERP 生产
     *
     * @param id 编号
     * @return ERP 生产
     */
    ProductionDO getProduction(Long id);

    /**
     * 获得ERP 生产分页
     *
     * @param pageReqVO 分页查询
     * @return ERP 生产分页
     */
    PageResult<ProductionDO> getProductionPage(ProductionPageReqVO pageReqVO);

    /**
     * 获得产品列表
     *
     * @param longs 编号
     * @return 产品列表
     */
    List<ProductionDO> getProductListByIds(Collection<Long> ids);
}