CREATE TABLE IF NOT EXISTS "erp_production" (
                                                "id" bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY,
                                                "serial_number" varchar,
                                                "product_id" bigint NOT NULL,
                                                "customer_id" bigint,
                                                "status" int,
                                                "production_supervisor_id" bigint,
                                                "commissioning_supervisor_id" bigint,
                                                "product_unit_id" bigint,
                                                "creator" varchar DEFAULT '',
                                                "create_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                                "updater" varchar DEFAULT '',
                                                "update_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                                "deleted" bit NOT NULL DEFAULT FALSE,
                                                "tenant_id" bigint NOT NULL,
                                                PRIMARY KEY ("id")
    ) COMMENT 'ERP 生产表';