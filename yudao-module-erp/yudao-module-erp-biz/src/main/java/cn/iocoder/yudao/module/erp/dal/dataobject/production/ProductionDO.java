package cn.iocoder.yudao.module.erp.dal.dataobject.production;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * ERP 生产 DO
 *
 * @author 成行
 */
@TableName("erp_production")
@KeySequence("erp_production_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductionDO extends BaseDO {

    /**
     * 产品编号
     */
    @TableId
    private Long id;
    /**
     * 生产编号
     */
    private String serialNumber;
    /**
     * 产品编号
     */
    private Long productId;
    /**
     * 客户编号
     */
    private Long customerId;
    /**
     * 生产状态
     */
    private Integer status;
    /**
     *  生产主管
     */
    private Long productionSupervisorId;
    /**
     * 调试主管id
     */
    private Long commissioningSupervisorId;
    /**
     * 产品单位单位
     */
    private Long productUnitId;

}