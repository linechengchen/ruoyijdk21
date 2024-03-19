package cn.iocoder.yudao.module.erp.controller.admin.production.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - ERP 生产新增/修改 Request VO")
@Data
public class ProductionSaveReqVO {

    @Schema(description = "产品编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "17610")
    private Long id;

    @Schema(description = "生产编号")
    private String serialNumber;

    @Schema(description = "产品编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "10234")
    @NotNull(message = "产品编号不能为空")
    private Long productId;

    @Schema(description = "客户编号", example = "24935")
    private Long customerId;

    @Schema(description = "生产状态", example = "1")
    private Integer status;

    @Schema(description = " 生产主管", example = "3039")
    private Long productionSupervisorId;

    @Schema(description = "调试主管id", example = "20168")
    private Long commissioningSupervisorId;

    @Schema(description = "产品单位单位", example = "31387")
    private Long productUnitId;

}