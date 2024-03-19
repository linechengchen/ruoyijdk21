package cn.iocoder.yudao.module.erp.controller.admin.production;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.module.erp.controller.admin.product.vo.product.ErpProductRespVO;
import cn.iocoder.yudao.module.erp.dal.dataobject.product.ErpProductDO;
import cn.iocoder.yudao.module.erp.dal.dataobject.product.ErpProductUnitDO;
import cn.iocoder.yudao.module.erp.service.product.ErpProductService;
import cn.iocoder.yudao.module.erp.service.product.ErpProductUnitService;
import cn.iocoder.yudao.module.erp.service.sale.ErpCustomerService;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.*;
import jakarta.servlet.http.*;

import java.util.*;
import java.io.IOException;
import java.util.function.Function;
import java.util.stream.Collectors;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;

import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;

import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertSet;
import static cn.iocoder.yudao.framework.operatelog.core.enums.OperateTypeEnum.*;

import cn.iocoder.yudao.module.erp.controller.admin.production.vo.*;
import cn.iocoder.yudao.module.erp.dal.dataobject.production.ProductionDO;
import cn.iocoder.yudao.module.erp.service.production.ProductionService;

@Tag(name = "管理后台 - ERP 生产")
@RestController
@RequestMapping("/erp/production")
@Validated
public class ProductionController {

    @Resource
    private ProductionService productionService;
    @Resource
    private ErpProductService productService;
    @Resource
    private ErpProductUnitService productUnitService;
    @Resource
    private ErpCustomerService customerService;

    @PostMapping("/create")
    @Operation(summary = "创建ERP 生产")
    @PreAuthorize("@ss.hasPermission('erp:production:create')")
    public CommonResult<Long> createProduction(@Valid @RequestBody ProductionSaveReqVO createReqVO) {
        return success(productionService.createProduction(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新ERP 生产")
    @PreAuthorize("@ss.hasPermission('erp:production:update')")
    public CommonResult<Boolean> updateProduction(@Valid @RequestBody ProductionSaveReqVO updateReqVO) {
        productionService.updateProduction(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除ERP 生产")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('erp:production:delete')")
    public CommonResult<Boolean> deleteProduction(@RequestParam("id") Long id) {
        productionService.deleteProduction(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得ERP 生产")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('erp:production:query')")
    public CommonResult<ProductionRespVO> getProduction(@RequestParam("id") Long id) {
        ProductionDO production = productionService.getProduction(id);
        return success(BeanUtils.toBean(production, ProductionRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得ERP 生产分页")
    @PreAuthorize("@ss.hasPermission('erp:production:query')")
    public CommonResult<PageResult<ProductionRespVO>> getProductionPage(@Valid ProductionPageReqVO pageReqVO) {
        PageResult<ProductionDO> pageResult = productionService.getProductionPage(pageReqVO);
        return success(buildProductionVOPageResult(pageResult));
    }

    private PageResult<ProductionRespVO> buildProductionVOPageResult(PageResult<ProductionDO> pageResult) {
        if (CollUtil.isEmpty(pageResult.getList())) {
            return PageResult.empty(pageResult.getTotal());
        }
        List<ErpProductDO> productList = productService.getProductListByIds(
                convertSet(pageResult.getList(), ProductionDO::getProductId));

        Map<Long, ErpProductRespVO> productMap = productService.getProductVOMap(
                convertSet(productList, ErpProductDO::getId));

        return BeanUtils.toBean(pageResult, ProductionRespVO.class, production -> {
            ErpProductRespVO product = productMap.get(production.getProductId());
            System.out.println("测试控制器打印");
            System.out.println(productUnitService.getProductUnit(product.getUnitId()).getName());
            production.setProductName(product.getName());
            if (product.getUnitId() != null) {
                production.setProductUnitName(productUnitService.getProductUnit(product.getUnitId()).getName());

            }
            if (production.getCustomerId() != null) {
                production.setCustomerName(customerService.getCustomer(production.getCustomerId()).getName());
            }
        });
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出ERP 生产 Excel")
    @PreAuthorize("@ss.hasPermission('erp:production:export')")
    @OperateLog(type = EXPORT)
    public void exportProductionExcel(@Valid ProductionPageReqVO pageReqVO,
                                      HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ProductionDO> list = productionService.getProductionPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "ERP 生产.xls", "数据", ProductionRespVO.class,
                BeanUtils.toBean(list, ProductionRespVO.class));
    }

}