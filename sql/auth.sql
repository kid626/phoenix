--
-- Table structure for table `auth_user`
--

DROP TABLE IF EXISTS `auth_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `auth_user`
(
    `id`          bigint                                                        NOT NULL COMMENT '主键',
    `username`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '账号',
    `salt`        varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '盐值',
    `password`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
    `is_enable`   char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci     DEFAULT NULL COMMENT '是否启用',
    `is_delete`   char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci     DEFAULT NULL COMMENT '是否删除',
    `create_time` datetime                                                     DEFAULT NULL COMMENT '创建时间',
    `create_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
    `update_time` datetime                                                     DEFAULT NULL COMMENT '更新时间',
    `update_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='用户';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auth_user`
--

LOCK
TABLES `auth_user` WRITE;
/*!40000 ALTER TABLE `auth_user` DISABLE KEYS */;
INSERT INTO `auth_user`
VALUES (1, 'admin', 'kPpSDM', 'QHeXQaVCq0UYn83NwwKtIQ==', 'Y', 'N', '2021-12-27 16:47:35', 'admin',
        '2021-12-27 16:47:35', 'admin'),
       (2, 'user', 'vSPaYk', 'w9pVTSFdIhr5G6eozo2osA==', 'Y', 'N', '2021-12-27 16:48:35', 'admin',
        '2021-12-27 16:48:35', 'admin');
/*!40000 ALTER TABLE `auth_user` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `auth_role_resource`
--

DROP TABLE IF EXISTS `auth_role_resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `auth_role_resource`
(
    `id`          bigint NOT NULL COMMENT '主键',
    `role_id`     bigint NOT NULL COMMENT '角色主键',
    `resource_id` bigint NOT NULL COMMENT '资源主键',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='角色-资源关系';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auth_role_resource`
--

LOCK
TABLES `auth_role_resource` WRITE;
/*!40000 ALTER TABLE `auth_role_resource` DISABLE KEYS */;
INSERT INTO `auth_role_resource`
VALUES (1, 1, 972459692830756864),
       (2, 1, 972459692843339776),
       (3, 1, 972459692843339777),
       (4, 1, 972459692847534080),
       (5, 1, 972459692851728384),
       (6, 1, 972459692851728385),
       (7, 1, 972459692851728386),
       (8, 1, 972459692855922688),
       (9, 1, 972459692855922689);
/*!40000 ALTER TABLE `auth_role_resource` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `auth_resource`
--

DROP TABLE IF EXISTS `auth_resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `auth_resource`
(
    `id`          bigint NOT NULL COMMENT '主键',
    `code`        varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '资源编码',
    `name`        varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '资源名称',
    `parent_code` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '父编码',
    `method`      tinyint                                                       DEFAULT NULL COMMENT '请求方法(0:全匹配,1:POST,2:DELETE,3:PUT,4:GET)',
    `url`         varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '资源路径',
    `icon`        varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '图标',
    `order_num`   int                                                           DEFAULT NULL COMMENT '排序',
    `type`        int                                                           DEFAULT NULL COMMENT '类型标记(0:菜单,1:按钮)',
    `is_enable`   char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci      DEFAULT NULL COMMENT '是否启用',
    `create_user` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
    `create_time` datetime                                                      DEFAULT NULL COMMENT '创建时间',
    `update_user` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人',
    `update_time` datetime                                                      DEFAULT NULL COMMENT '更新时间',
    `is_delete`   char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci      DEFAULT NULL COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='资源';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auth_resource`
--

LOCK
TABLES `auth_resource` WRITE;
/*!40000 ALTER TABLE `auth_resource` DISABLE KEYS */;
INSERT INTO `auth_resource`
VALUES (972459692830756864, 'user:center:menu', '用户中心', '', 4, '/userCenter', '', 1, 0, 'Y', 'admin',
        '2024-03-07 11:28:11', 'admin', '2024-03-07 11:28:11', 'N'),
       (972459692843339776, 'user:manage:menu', '用户管理', 'user:center:menu', 4, '/userCenter/userManage', '', 1, 0,
        'Y', 'admin', '2024-03-07 11:28:11', 'admin', '2024-03-07 11:28:11', 'N'),
       (972459692843339777, 'role:manage:menu', '角色管理', 'user:center:menu', 4, '/userCenter/roleManage', '', 2, 0,
        'Y', 'admin', '2024-03-07 11:28:11', 'admin', '2024-03-07 11:28:11', 'N'),
       (972459692847534080, 'resource:manage:menu', '菜单管理', 'user:center:menu', 4, '/userCenter/resourceManage', '',
        3, 0, 'Y', 'admin', '2024-03-07 11:28:11', 'admin', '2024-03-07 11:28:11', 'N'),
       (972459692851728384, 'user:detail', '查询详情', 'user:manage:menu', 4, '/user/v1/detail/*', '', 1, 1, 'Y',
        'admin', '2024-03-07 11:28:11', 'admin', '2024-03-07 11:28:11', 'N'),
       (972459692851728385, 'user:page', '分页查询', 'user:manage:menu', 4, '/user/v1/page', '', 1, 1, 'Y', 'admin',
        '2024-03-07 11:28:11', 'admin', '2024-03-07 11:28:11', 'N'),
       (972459692851728386, 'user:remove', '删除', 'user:manage:menu', 1, '/user/v1/remove/*', '', 1, 1, 'Y', 'admin',
        '2024-03-07 11:28:11', 'admin', '2024-03-07 11:28:11', 'N'),
       (972459692855922688, 'user:update', '更新', 'user:manage:menu', 1, '/user/v1/update', '', 1, 1, 'Y', 'admin',
        '2024-03-07 11:28:11', 'admin', '2024-03-07 11:28:11', 'N'),
       (972459692855922689, 'user:save', '新增', 'user:manage:menu', 1, '/user/v1/save', '', 1, 1, 'Y', 'admin',
        '2024-03-07 11:28:11', 'admin', '2024-03-07 11:28:11', 'N');
/*!40000 ALTER TABLE `auth_resource` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `auth_role`
--

DROP TABLE IF EXISTS `auth_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `auth_role`
(
    `id`          bigint                                                        NOT NULL COMMENT '主键',
    `name`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名',
    `code`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '角色编码',
    `note`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '角色备注',
    `is_enable`   char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci      DEFAULT NULL COMMENT '是否启用',
    `create_user` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
    `create_time` datetime                                                      DEFAULT NULL COMMENT '创建时间',
    `update_user` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人',
    `update_time` datetime                                                      DEFAULT NULL COMMENT '更新时间',
    `is_delete`   char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci      DEFAULT NULL COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='角色';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auth_role`
--

LOCK
TABLES `auth_role` WRITE;
/*!40000 ALTER TABLE `auth_role` DISABLE KEYS */;
INSERT INTO `auth_role`
VALUES (1, '管理员', 'admin', '超级管理员', 'Y', 'sys', '2024-03-07 12:30:16', 'sys', '2024-03-07 12:30:16', 'N'),
       (2, '普通用户', 'user', NULL, 'Y', 'sys', '2024-03-07 12:30:16', 'sys', '2024-03-07 12:30:16', 'N');
/*!40000 ALTER TABLE `auth_role` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `auth_user_role`
--

DROP TABLE IF EXISTS `auth_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `auth_user_role`
(
    `id`      bigint NOT NULL COMMENT '主键',
    `user_id` bigint NOT NULL COMMENT '用户主键',
    `role_id` bigint NOT NULL COMMENT '角色主键',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='用户-角色关系';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auth_user_role`
--

LOCK
TABLES `auth_user_role` WRITE;
/*!40000 ALTER TABLE `auth_user_role` DISABLE KEYS */;
INSERT INTO `auth_user_role`
VALUES (1, 1, 1),
       (2, 2, 2);
/*!40000 ALTER TABLE `auth_user_role` ENABLE KEYS */;
UNLOCK
TABLES;

DROP TABLE IF EXISTS `auth_org`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `auth_org`
(
    `id`          bigint NOT NULL COMMENT '主键',
    `name`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT '' COMMENT '组织名称',
    `code`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT '' COMMENT '组织编号',
    `parent_id`   bigint                                                        DEFAULT NULL COMMENT '上级组织id',
    `parent_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT '' COMMENT '上级组织编码',
    `sort_order`  int                                                           DEFAULT NULL COMMENT '组织排序码',
    `note`        varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '备注',
    `is_enable`   char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci      DEFAULT 'Y' COMMENT '是否启用',
    `create_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT '' COMMENT '创建人',
    `create_time` datetime                                                      DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT '' COMMENT '修改人',
    `update_time` datetime                                                      DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `is_delete`   char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci      DEFAULT 'N' COMMENT '是否删除'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='组织';


DROP TABLE IF EXISTS `auth_user_org`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `auth_user_org`
(
    `id`         bigint                                 DEFAULT NULL COMMENT '主键',
    `user_id`    bigint                                 DEFAULT NULL COMMENT '用户主键',
    `org_code`   varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '组织编码',
    `sort_order` int                                    DEFAULT NULL COMMENT '排序'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户-组织关系';
