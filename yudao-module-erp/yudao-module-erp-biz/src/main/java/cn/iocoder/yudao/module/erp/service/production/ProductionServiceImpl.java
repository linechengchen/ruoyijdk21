package cn.iocoder.yudao.module.erp.service.production;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.erp.controller.admin.production.vo.*;
import cn.iocoder.yudao.module.erp.dal.dataobject.production.ProductionDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.erp.dal.mysql.production.ProductionMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.erp.enums.ErrorCodeConstants.*;

/**
 * ERP 生产 Service 实现类
 *
 * @author 成行
 */
@Service
@Validated
public class ProductionServiceImpl implements ProductionService {

    @Resource
    private ProductionMapper productionMapper;

    @Override
    public Long createProduction(ProductionSaveReqVO createReqVO) {
        // 插入
        ProductionDO production = BeanUtils.toBean(createReqVO, ProductionDO.class);
        productionMapper.insert(production);
        // 返回
        return production.getId();
    }

    @Override
    public void updateProduction(ProductionSaveReqVO updateReqVO) {
        // 校验存在
        validateProductionExists(updateReqVO.getId());
        // 更新
        ProductionDO updateObj = BeanUtils.toBean(updateReqVO, ProductionDO.class);
        productionMapper.updateById(updateObj);
    }

    @Override
    public void deleteProduction(Long id) {
        // 校验存在
        validateProductionExists(id);
        // 删除
        productionMapper.deleteById(id);
    }

    private void validateProductionExists(Long id) {
        if (productionMapper.selectById(id) == null) {
            throw exception(PRODUCTION_NOT_EXISTS);
        }
    }

    @Override
    public ProductionDO getProduction(Long id) {
        return productionMapper.selectById(id);
    }

    @Override
    public PageResult<ProductionDO> getProductionPage(ProductionPageReqVO pageReqVO) {
        return productionMapper.selectPage(pageReqVO);
    }
    @Override
    public List<ProductionDO> getProductListByIds(Collection<Long> ids) {
        // 此处实现根据 ID 集合查询产品列表的逻辑
        // 以下为示例代码，您需要根据实际情况调整
        if (ids == null || ids.isEmpty()) {
            return Collections.emptyList();
        }
        return productionMapper.selectBatchIds(ids);
    }
}