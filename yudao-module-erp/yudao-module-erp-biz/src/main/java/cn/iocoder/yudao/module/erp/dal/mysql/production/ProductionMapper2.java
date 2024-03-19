//package cn.iocoder.yudao.module.erp.dal.mysql.production;
//
//import cn.iocoder.yudao.framework.common.pojo.PageResult;
//import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
//import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
//import cn.iocoder.yudao.framework.mybatis.core.query.MPJLambdaWrapperX;
//import cn.iocoder.yudao.module.erp.controller.admin.production.vo.ProductionPageReqVO;
//import cn.iocoder.yudao.module.erp.dal.dataobject.product.ErpProductDO;
//import cn.iocoder.yudao.module.erp.dal.dataobject.production.ProductionAndProductDO;
//import cn.iocoder.yudao.module.erp.dal.dataobject.production.ProductionDO;
//import org.apache.ibatis.annotations.Mapper;
//
///**
// * ERP 生产 Mapper
// *
// * @author 成行
// */
//
//@Mapper
//public interface ProductionMapper2 extends BaseMapperX<ProductionAndProductDO> {
//
//    default PageResult<ProductionAndProductDO> selectPageWithProduct(ProductionPageReqVO reqVO) {
//        return selectJoinPage(ProductionAndProductDO.class, new MPJLambdaWrapperX<ProductionDO>()
//                .selectAll(ProductionDO.class)
//                .selectAssociation(ErpProductDO.class, ProductionAndProductDO::getPd) // 查询 ErpProductDO 表的字段，映射到 pd 字段
//                .likeIfPresent(ProductionDO::getSerialNumber, reqVO.getSerialNumber())
//                .eqIfPresent(ProductionDO::getProductId, reqVO.getProductId())
//                .eqIfPresent(ProductionDO::getCustomerId, reqVO.getCustomerId())
//                .eqIfPresent(ProductionDO::getStatus, reqVO.getStatus())
//                .eqIfPresent(ProductionDO::getProductionSupervisorId, reqVO.getProductionSupervisorId())
//                .eqIfPresent(ProductionDO::getCommissioningSupervisorId, reqVO.getCommissioningSupervisorId())
//                .eqIfPresent(ProductionDO::getProductUnitId, reqVO.getProductUnitId())
//                .betweenIfPresent(ProductionDO::getCreateTime, reqVO.getCreateTime())
//                .orderByDesc(ProductionDO::getId));
//    }
//}