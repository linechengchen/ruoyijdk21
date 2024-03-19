package cn.iocoder.yudao.module.erp.controller.admin.production.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - ERP 生产 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ProductionRespVO {

    @Schema(description = "产品编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "17610")
    @ExcelProperty("产品编号")
    private Long id;

    @Schema(description = "产品信息", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("产品信息")
    private String productName;


    @Schema(description = "生产编号")
    @ExcelProperty("生产编号")
    private String serialNumber;

    @Schema(description = "产品编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "10234")
    @ExcelProperty("产品编号")
    private Long productId;

    @Schema(description = "客户编号", example = "24935")
    @ExcelProperty("客户编号")
    private Long customerId;

    @Schema(description = "客户名称", example = "小李子")
    @ExcelProperty("客户名称")
    private String customerName;

    @Schema(description = "生产状态", example = "1")
    @ExcelProperty("生产状态")
    private Integer status;

    @Schema(description = " 生产主管", example = "3039")
    @ExcelProperty(" 生产主管")
    private Long productionSupervisorId;

    @Schema(description = "调试主管id", example = "20168")
    @ExcelProperty("调试主管id")
    private Long commissioningSupervisorId;

    @Schema(description = "产品单位", example = "31387")
    @ExcelProperty("产品单位")

    private Long productUnitId;
    @Schema(description = "产品单位名称", example = "31387")

    @ExcelProperty("产品单位")
    private String  productUnitName;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}