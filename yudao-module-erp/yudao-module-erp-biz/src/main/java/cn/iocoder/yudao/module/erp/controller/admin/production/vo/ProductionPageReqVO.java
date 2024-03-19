package cn.iocoder.yudao.module.erp.controller.admin.production.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - ERP 生产分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProductionPageReqVO extends PageParam {

    @Schema(description = "生产编号")
    private String serialNumber;

    @Schema(description = "产品编号", example = "10234")
    private Long productId;

    // ========== 产品信息 ==========

    @Schema(description = "产品名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "苹果")
    @ExcelProperty("产品名称")
    private String productName;

    @Schema(description = "产品分类", requiredMode = Schema.RequiredMode.REQUIRED, example = "水果")
    @ExcelProperty("产品分类")
    private String categoryName;

    @Schema(description = "单位", requiredMode = Schema.RequiredMode.REQUIRED, example = "个")
    @ExcelProperty("单位")
    private String unitName;


    @Schema(description = "客户编号", example = "24935")
    private Long customerId;

    @Schema(description = "生产状态", example = "1")
    private Integer status;

    @Schema(description = "生产主管", example = "3039")
    private Long productionSupervisorId;

    @Schema(description = "调试主管", example = "20168")
    private Long commissioningSupervisorId;

    @Schema(description = "产品单位单位", example = "31387")
    private Long productUnitId;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}