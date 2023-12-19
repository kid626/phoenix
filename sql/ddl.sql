-- mybatis_plus.demo_user definition

CREATE TABLE `demo_user`
(
    `id`          bigint NOT NULL COMMENT '主键',
    `dept_id`     bigint                                                        DEFAULT NULL COMMENT '部门id',
    `name`        varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '名称',
    `age`         int                                                           DEFAULT NULL COMMENT '年龄',
    `grade`       int                                                           DEFAULT NULL COMMENT '性别',
    `email`       varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '邮箱',
    `date_column` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '序列',
    `tenant_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '租户号',
    `create_time` datetime                                                      DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime                                                      DEFAULT NULL COMMENT '更新时间',
    `is_delete`   char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci      DEFAULT NULL COMMENT '是否删除',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
