package cn.iocoder.yudao.module.erp.dal.mysql.production;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.MPJLambdaWrapperX;
import cn.iocoder.yudao.module.erp.dal.dataobject.product.ErpProductDO;
import cn.iocoder.yudao.module.erp.dal.dataobject.production.ProductionDO;
import cn.iocoder.yudao.module.erp.dal.dataobject.stock.ErpStockCheckDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.erp.controller.admin.production.vo.*;

/**
 * ERP 生产 Mapper
 *
 * @author 成行
 */
@Mapper
public interface ProductionMapper extends BaseMapperX<ProductionDO> {
    //查询的
    default PageResult<ProductionDO> selectPage(ProductionPageReqVO reqVO) {
        MPJLambdaWrapperX<ProductionDO> query = new MPJLambdaWrapperX<ProductionDO>()
                .likeIfPresent(ProductionDO::getSerialNumber, reqVO.getSerialNumber())
                .eqIfPresent(ProductionDO::getProductId, reqVO.getProductId())
                .eqIfPresent(ProductionDO::getCustomerId, reqVO.getCustomerId())
                .eqIfPresent(ProductionDO::getStatus, reqVO.getStatus())
                .eqIfPresent(ProductionDO::getProductionSupervisorId, reqVO.getProductionSupervisorId())
                .eqIfPresent(ProductionDO::getCommissioningSupervisorId, reqVO.getCommissioningSupervisorId())
                .eqIfPresent(ProductionDO::getProductUnitId, reqVO.getProductUnitId())
                .betweenIfPresent(ProductionDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ProductionDO::getId);

        query.leftJoin(ErpProductDO.class,
                        ErpProductDO::getId,
                        ProductionDO::getProductId)

                .eq(ErpProductDO::getId, ProductionDO::getProductId)
                .groupBy(ErpStockCheckDO::getId);
        //在控制台打印查询的sql语句
        System.out.println("sql打印");
        System.out.println("sql打印2");
        System.out.println(query.getSqlSegment());
        return selectJoinPage(reqVO, ProductionDO.class, query);
    }
}


