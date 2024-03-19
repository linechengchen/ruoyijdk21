package cn.iocoder.yudao.module.erp.service.production;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import jakarta.annotation.Resource;

import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;

import cn.iocoder.yudao.module.erp.controller.admin.production.vo.*;
import cn.iocoder.yudao.module.erp.dal.dataobject.production.ProductionDO;
import cn.iocoder.yudao.module.erp.dal.mysql.production.ProductionMapper;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import jakarta.annotation.Resource;
import org.springframework.context.annotation.Import;
import java.util.*;
import java.time.LocalDateTime;

import static cn.hutool.core.util.RandomUtil.*;
import static cn.iocoder.yudao.module.erp.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.framework.test.core.util.AssertUtils.*;
import static cn.iocoder.yudao.framework.test.core.util.RandomUtils.*;
import static cn.iocoder.yudao.framework.common.util.date.LocalDateTimeUtils.*;
import static cn.iocoder.yudao.framework.common.util.object.ObjectUtils.*;
import static cn.iocoder.yudao.framework.common.util.date.DateUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * {@link ProductionServiceImpl} 的单元测试类
 *
 * @author 成行
 */
@Import(ProductionServiceImpl.class)
public class ProductionServiceImplTest extends BaseDbUnitTest {

    @Resource
    private ProductionServiceImpl productionService;

    @Resource
    private ProductionMapper productionMapper;

    @Test
    public void testCreateProduction_success() {
        // 准备参数
        ProductionSaveReqVO createReqVO = randomPojo(ProductionSaveReqVO.class).setId(null);

        // 调用
        Long productionId = productionService.createProduction(createReqVO);
        // 断言
        assertNotNull(productionId);
        // 校验记录的属性是否正确
        ProductionDO production = productionMapper.selectById(productionId);
        assertPojoEquals(createReqVO, production, "id");
    }

    @Test
    public void testUpdateProduction_success() {
        // mock 数据
        ProductionDO dbProduction = randomPojo(ProductionDO.class);
        productionMapper.insert(dbProduction);// @Sql: 先插入出一条存在的数据
        // 准备参数
        ProductionSaveReqVO updateReqVO = randomPojo(ProductionSaveReqVO.class, o -> {
            o.setId(dbProduction.getId()); // 设置更新的 ID
        });

        // 调用
        productionService.updateProduction(updateReqVO);
        // 校验是否更新正确
        ProductionDO production = productionMapper.selectById(updateReqVO.getId()); // 获取最新的
        assertPojoEquals(updateReqVO, production);
    }

    @Test
    public void testUpdateProduction_notExists() {
        // 准备参数
        ProductionSaveReqVO updateReqVO = randomPojo(ProductionSaveReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> productionService.updateProduction(updateReqVO), PRODUCTION_NOT_EXISTS);
    }

    @Test
    public void testDeleteProduction_success() {
        // mock 数据
        ProductionDO dbProduction = randomPojo(ProductionDO.class);
        productionMapper.insert(dbProduction);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbProduction.getId();

        // 调用
        productionService.deleteProduction(id);
       // 校验数据不存在了
       assertNull(productionMapper.selectById(id));
    }

    @Test
    public void testDeleteProduction_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> productionService.deleteProduction(id), PRODUCTION_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetProductionPage() {
       // mock 数据
       ProductionDO dbProduction = randomPojo(ProductionDO.class, o -> { // 等会查询到
           o.setSerialNumber(null);
           o.setProductId(null);
           o.setCustomerId(null);
           o.setStatus(null);
           o.setProductionSupervisorId(null);
           o.setCommissioningSupervisorId(null);
           o.setProductUnitId(null);
           o.setCreateTime(null);
       });
       productionMapper.insert(dbProduction);
       // 测试 serialNumber 不匹配
       productionMapper.insert(cloneIgnoreId(dbProduction, o -> o.setSerialNumber(null)));
       // 测试 productId 不匹配
       productionMapper.insert(cloneIgnoreId(dbProduction, o -> o.setProductId(null)));
       // 测试 customerId 不匹配
       productionMapper.insert(cloneIgnoreId(dbProduction, o -> o.setCustomerId(null)));
       // 测试 status 不匹配
       productionMapper.insert(cloneIgnoreId(dbProduction, o -> o.setStatus(null)));
       // 测试 productionSupervisorId 不匹配
       productionMapper.insert(cloneIgnoreId(dbProduction, o -> o.setProductionSupervisorId(null)));
       // 测试 commissioningSupervisorId 不匹配
       productionMapper.insert(cloneIgnoreId(dbProduction, o -> o.setCommissioningSupervisorId(null)));
       // 测试 productUnitId 不匹配
       productionMapper.insert(cloneIgnoreId(dbProduction, o -> o.setProductUnitId(null)));
       // 测试 createTime 不匹配
       productionMapper.insert(cloneIgnoreId(dbProduction, o -> o.setCreateTime(null)));
       // 准备参数
       ProductionPageReqVO reqVO = new ProductionPageReqVO();
       reqVO.setSerialNumber(null);
       reqVO.setProductId(null);
       reqVO.setCustomerId(null);
       reqVO.setStatus(null);
       reqVO.setProductionSupervisorId(null);
       reqVO.setCommissioningSupervisorId(null);
       reqVO.setProductUnitId(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       PageResult<ProductionDO> pageResult = productionService.getProductionPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbProduction, pageResult.getList().get(0));
    }

}