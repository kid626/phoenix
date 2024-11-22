CREATE TABLE `oss_file_info`
(
    `id`               bigint NOT NULL COMMENT '主键',
    `file_size`        bigint                                                        DEFAULT NULL COMMENT '文件大小',
    `identifier`       varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT 'md5唯一标识',
    `origin`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '来源',
    `path`             varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文件路径',
    `extend_name`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT '' COMMENT '扩展名',
    `origin_file_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '原始文件名',
    `bucket_name`      varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '桶名称',
    `is_public`        char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci      DEFAULT NULL COMMENT '是否公有桶',
    `file_type_code`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '文件类型（图片，文档，视频，音频，其他）',
    `file_type_name`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '文件类型名称',
    `create_time`      datetime                                                      DEFAULT NULL COMMENT '创建时间',
    `create_user`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '创建人',
    `update_time`      datetime                                                      DEFAULT NULL COMMENT '更新时间',
    `update_user`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '更新人',
    `is_delete`        char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci      DEFAULT NULL COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

DROP TABLE IF EXISTS `sys_dict`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_dict`
(
    `id`          bigint NOT NULL COMMENT '主键',
    `p_id`        bigint                                                       DEFAULT NULL COMMENT '父主键',
    `dict_code`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '字典编码',
    `p_code`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '父编码',
    `dict_name`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '字典名称',
    `dict_value`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '字典值',
    `sort_no`     int                                                          DEFAULT NULL COMMENT '排序',
    `is_default`  char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci     DEFAULT NULL COMMENT '是否默认',
    `is_enable`   char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci     DEFAULT NULL COMMENT '是否启用',
    `create_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建用户',
    `create_time` datetime                                                     DEFAULT NULL COMMENT '创建时间',
    `update_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新用户',
    `update_time` datetime                                                     DEFAULT NULL COMMENT '更新时间',
    `is_deleted`  char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci     DEFAULT NULL COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='系统数据字典';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dict`
--

LOCK
TABLES `sys_dict` WRITE;
/*!40000 ALTER TABLE `sys_dict` DISABLE KEYS */;
INSERT INTO `sys_dict`
VALUES (1, -1, 'file_type', '', '文件类型', '文件类型', 1, 'Y', 'Y', 'sys', '2023-04-17 09:42:04', 'sys',
        '2023-04-17 09:42:06', 'N'),
       (2, 1, 'image', 'file_type', '图片', '图片', 1, 'Y', 'Y', 'sys', '2023-04-17 09:43:34', 'sys',
        '2023-04-17 09:43:38', 'N'),
       (3, 1, 'video', 'file_type', '视频', '视频', 2, 'Y', 'Y', 'sys', '2023-04-17 09:43:34', 'sys',
        '2023-04-17 09:43:38', 'N'),
       (4, 1, 'music', 'file_type', '音频', '音频', 3, 'Y', 'Y', 'sys', '2023-04-17 09:43:34', 'sys',
        '2023-04-17 09:43:38', 'N'),
       (5, 1, 'doc', 'file_type', '文档', '文档', 4, 'Y', 'Y', 'sys', '2023-04-17 09:43:34', 'sys',
        '2023-04-17 09:43:38', 'N'),
       (6, 2, 'bmp', 'image', 'bmp', 'bmp', 0, 'Y', 'Y', 'sys', '2023-04-17 14:34:42', 'sys', '2023-04-17 14:34:42',
        'N'),
       (7, 2, 'jpg', 'image', 'jpg', 'jpg', 0, 'Y', 'Y', 'sys', '2023-04-17 14:34:42', 'sys', '2023-04-17 14:34:42',
        'N'),
       (8, 2, 'png', 'image', 'png', 'png', 0, 'Y', 'Y', 'sys', '2023-04-17 14:34:42', 'sys', '2023-04-17 14:34:42',
        'N'),
       (9, 2, 'tif', 'image', 'tif', 'tif', 0, 'Y', 'Y', 'sys', '2023-04-17 14:34:42', 'sys', '2023-04-17 14:34:42',
        'N'),
       (10, 2, 'gif', 'image', 'gif', 'gif', 0, 'Y', 'Y', 'sys', '2023-04-17 14:34:42', 'sys', '2023-04-17 14:34:42',
        'N'),
       (11, 2, 'jpeg', 'image', 'jpeg', 'jpeg', 0, 'Y', 'Y', 'sys', '2023-04-17 14:34:42', 'sys', '2023-04-17 14:34:42',
        'N'),
       (12, 5, 'doc', 'doc', 'doc', 'doc', 0, 'Y', 'Y', 'sys', '2023-04-17 14:35:27', 'sys', '2023-04-17 14:35:27',
        'N'),
       (13, 5, 'docx', 'doc', 'docx', 'docx', 0, 'Y', 'Y', 'sys', '2023-04-17 14:35:27', 'sys', '2023-04-17 14:35:27',
        'N'),
       (14, 5, 'docm', 'doc', 'docm', 'docm', 0, 'Y', 'Y', 'sys', '2023-04-17 14:35:27', 'sys', '2023-04-17 14:35:27',
        'N'),
       (15, 5, 'dot', 'doc', 'dot', 'dot', 0, 'Y', 'Y', 'sys', '2023-04-17 14:35:27', 'sys', '2023-04-17 14:35:27',
        'N'),
       (16, 5, 'dotx', 'doc', 'dotx', 'dotx', 0, 'Y', 'Y', 'sys', '2023-04-17 14:35:27', 'sys', '2023-04-17 14:35:27',
        'N'),
       (17, 5, 'dotm', 'doc', 'dotm', 'dotm', 0, 'Y', 'Y', 'sys', '2023-04-17 14:35:27', 'sys', '2023-04-17 14:35:27',
        'N'),
       (18, 5, 'odt', 'doc', 'odt', 'odt', 0, 'Y', 'Y', 'sys', '2023-04-17 14:35:27', 'sys', '2023-04-17 14:35:27',
        'N'),
       (19, 5, 'fodt', 'doc', 'fodt', 'fodt', 0, 'Y', 'Y', 'sys', '2023-04-17 14:35:27', 'sys', '2023-04-17 14:35:27',
        'N'),
       (20, 5, 'ott', 'doc', 'ott', 'ott', 0, 'Y', 'Y', 'sys', '2023-04-17 14:35:27', 'sys', '2023-04-17 14:35:27',
        'N'),
       (21, 5, 'rtf', 'doc', 'rtf', 'rtf', 0, 'Y', 'Y', 'sys', '2023-04-17 14:35:27', 'sys', '2023-04-17 14:35:27',
        'N'),
       (22, 5, 'txt', 'doc', 'txt', 'txt', 0, 'Y', 'Y', 'sys', '2023-04-17 14:35:27', 'sys', '2023-04-17 14:35:27',
        'N'),
       (23, 5, 'html', 'doc', 'html', 'html', 0, 'Y', 'Y', 'sys', '2023-04-17 14:35:27', 'sys', '2023-04-17 14:35:27',
        'N'),
       (24, 5, 'htm', 'doc', 'htm', 'htm', 0, 'Y', 'Y', 'sys', '2023-04-17 14:35:27', 'sys', '2023-04-17 14:35:27',
        'N'),
       (25, 5, 'mht', 'doc', 'mht', 'mht', 0, 'Y', 'Y', 'sys', '2023-04-17 14:35:27', 'sys', '2023-04-17 14:35:27',
        'N'),
       (26, 5, 'xml', 'doc', 'xml', 'xml', 0, 'Y', 'Y', 'sys', '2023-04-17 14:35:27', 'sys', '2023-04-17 14:35:27',
        'N'),
       (27, 5, 'pdf', 'doc', 'pdf', 'pdf', 0, 'Y', 'Y', 'sys', '2023-04-17 14:35:27', 'sys', '2023-04-17 14:35:27',
        'N'),
       (28, 5, 'djvu', 'doc', 'djvu', 'djvu', 0, 'Y', 'Y', 'sys', '2023-04-17 14:35:27', 'sys', '2023-04-17 14:35:27',
        'N'),
       (29, 5, 'fb2', 'doc', 'fb2', 'fb2', 0, 'Y', 'Y', 'sys', '2023-04-17 14:35:27', 'sys', '2023-04-17 14:35:27',
        'N'),
       (30, 5, 'epub', 'doc', 'epub', 'epub', 0, 'Y', 'Y', 'sys', '2023-04-17 14:35:27', 'sys', '2023-04-17 14:35:27',
        'N'),
       (31, 5, 'xps', 'doc', 'xps', 'xps', 0, 'Y', 'Y', 'sys', '2023-04-17 14:35:27', 'sys', '2023-04-17 14:35:27',
        'N'),
       (32, 5, 'xls', 'doc', 'xls', 'xls', 0, 'Y', 'Y', 'sys', '2023-04-17 14:35:27', 'sys', '2023-04-17 14:35:27',
        'N'),
       (33, 5, 'xlsx', 'doc', 'xlsx', 'xlsx', 0, 'Y', 'Y', 'sys', '2023-04-17 14:35:27', 'sys', '2023-04-17 14:35:27',
        'N'),
       (34, 5, 'xlsm', 'doc', 'xlsm', 'xlsm', 0, 'Y', 'Y', 'sys', '2023-04-17 14:35:27', 'sys', '2023-04-17 14:35:27',
        'N'),
       (35, 5, 'xlt', 'doc', 'xlt', 'xlt', 0, 'Y', 'Y', 'sys', '2023-04-17 14:35:27', 'sys', '2023-04-17 14:35:27',
        'N'),
       (36, 5, 'xltx', 'doc', 'xltx', 'xltx', 0, 'Y', 'Y', 'sys', '2023-04-17 14:35:27', 'sys', '2023-04-17 14:35:27',
        'N'),
       (37, 5, 'xltm', 'doc', 'xltm', 'xltm', 0, 'Y', 'Y', 'sys', '2023-04-17 14:35:27', 'sys', '2023-04-17 14:35:27',
        'N'),
       (38, 5, 'ods', 'doc', 'ods', 'ods', 0, 'Y', 'Y', 'sys', '2023-04-17 14:35:27', 'sys', '2023-04-17 14:35:27',
        'N'),
       (39, 5, 'fods', 'doc', 'fods', 'fods', 0, 'Y', 'Y', 'sys', '2023-04-17 14:35:27', 'sys', '2023-04-17 14:35:27',
        'N'),
       (40, 5, 'ots', 'doc', 'ots', 'ots', 0, 'Y', 'Y', 'sys', '2023-04-17 14:35:27', 'sys', '2023-04-17 14:35:27',
        'N'),
       (41, 5, 'csv', 'doc', 'csv', 'csv', 0, 'Y', 'Y', 'sys', '2023-04-17 14:35:27', 'sys', '2023-04-17 14:35:27',
        'N'),
       (42, 5, 'pps', 'doc', 'pps', 'pps', 0, 'Y', 'Y', 'sys', '2023-04-17 14:35:27', 'sys', '2023-04-17 14:35:27',
        'N'),
       (43, 5, 'ppsx', 'doc', 'ppsx', 'ppsx', 0, 'Y', 'Y', 'sys', '2023-04-17 14:35:27', 'sys', '2023-04-17 14:35:27',
        'N'),
       (44, 5, 'ppsm', 'doc', 'ppsm', 'ppsm', 0, 'Y', 'Y', 'sys', '2023-04-17 14:35:27', 'sys', '2023-04-17 14:35:27',
        'N'),
       (45, 5, 'ppt', 'doc', 'ppt', 'ppt', 0, 'Y', 'Y', 'sys', '2023-04-17 14:35:27', 'sys', '2023-04-17 14:35:27',
        'N'),
       (46, 5, 'pptx', 'doc', 'pptx', 'pptx', 0, 'Y', 'Y', 'sys', '2023-04-17 14:35:27', 'sys', '2023-04-17 14:35:27',
        'N'),
       (47, 5, 'pptm', 'doc', 'pptm', 'pptm', 0, 'Y', 'Y', 'sys', '2023-04-17 14:35:27', 'sys', '2023-04-17 14:35:27',
        'N'),
       (48, 5, 'pot', 'doc', 'pot', 'pot', 0, 'Y', 'Y', 'sys', '2023-04-17 14:35:27', 'sys', '2023-04-17 14:35:27',
        'N'),
       (49, 5, 'potx', 'doc', 'potx', 'potx', 0, 'Y', 'Y', 'sys', '2023-04-17 14:35:27', 'sys', '2023-04-17 14:35:27',
        'N'),
       (50, 5, 'potm', 'doc', 'potm', 'potm', 0, 'Y', 'Y', 'sys', '2023-04-17 14:35:27', 'sys', '2023-04-17 14:35:27',
        'N'),
       (51, 5, 'odp', 'doc', 'odp', 'odp', 0, 'Y', 'Y', 'sys', '2023-04-17 14:35:27', 'sys', '2023-04-17 14:35:27',
        'N'),
       (52, 5, 'fodp', 'doc', 'fodp', 'fodp', 0, 'Y', 'Y', 'sys', '2023-04-17 14:35:27', 'sys', '2023-04-17 14:35:27',
        'N'),
       (53, 5, 'otp', 'doc', 'otp', 'otp', 0, 'Y', 'Y', 'sys', '2023-04-17 14:35:27', 'sys', '2023-04-17 14:35:27',
        'N'),
       (54, 5, 'hlp', 'doc', 'hlp', 'hlp', 0, 'Y', 'Y', 'sys', '2023-04-17 14:35:27', 'sys', '2023-04-17 14:35:27',
        'N'),
       (55, 5, 'wps', 'doc', 'wps', 'wps', 0, 'Y', 'Y', 'sys', '2023-04-17 14:35:27', 'sys', '2023-04-17 14:35:27',
        'N'),
       (56, 5, 'java', 'doc', 'java', 'java', 0, 'Y', 'Y', 'sys', '2023-04-17 14:35:27', 'sys', '2023-04-17 14:35:27',
        'N'),
       (57, 5, 'js', 'doc', 'js', 'js', 0, 'Y', 'Y', 'sys', '2023-04-17 14:35:27', 'sys', '2023-04-17 14:35:27', 'N'),
       (58, 5, 'css', 'doc', 'css', 'css', 0, 'Y', 'Y', 'sys', '2023-04-17 14:35:27', 'sys', '2023-04-17 14:35:27',
        'N'),
       (59, 5, 'json', 'doc', 'json', 'json', 0, 'Y', 'Y', 'sys', '2023-04-17 14:35:27', 'sys', '2023-04-17 14:35:27',
        'N'),
       (60, 3, 'avi', 'video', 'avi', 'avi', 0, 'Y', 'Y', 'sys', '2023-04-17 14:36:16', 'sys', '2023-04-17 14:36:16',
        'N'),
       (61, 3, 'mp4', 'video', 'mp4', 'mp4', 0, 'Y', 'Y', 'sys', '2023-04-17 14:36:16', 'sys', '2023-04-17 14:36:16',
        'N'),
       (62, 3, 'mpg', 'video', 'mpg', 'mpg', 0, 'Y', 'Y', 'sys', '2023-04-17 14:36:16', 'sys', '2023-04-17 14:36:16',
        'N'),
       (63, 3, 'mov', 'video', 'mov', 'mov', 0, 'Y', 'Y', 'sys', '2023-04-17 14:36:16', 'sys', '2023-04-17 14:36:16',
        'N'),
       (64, 3, 'swf', 'video', 'swf', 'swf', 0, 'Y', 'Y', 'sys', '2023-04-17 14:36:16', 'sys', '2023-04-17 14:36:16',
        'N'),
       (65, 4, 'wav', 'music', 'wav', 'wav', 0, 'Y', 'Y', 'sys', '2023-04-17 14:36:51', 'sys', '2023-04-17 14:36:51',
        'N'),
       (66, 4, 'aif', 'music', 'aif', 'aif', 0, 'Y', 'Y', 'sys', '2023-04-17 14:36:51', 'sys', '2023-04-17 14:36:51',
        'N'),
       (67, 4, 'au', 'music', 'au', 'au', 0, 'Y', 'Y', 'sys', '2023-04-17 14:36:51', 'sys', '2023-04-17 14:36:51', 'N'),
       (68, 4, 'mp3', 'music', 'mp3', 'mp3', 0, 'Y', 'Y', 'sys', '2023-04-17 14:36:51', 'sys', '2023-04-17 14:36:51',
        'N'),
       (69, 4, 'ram', 'music', 'ram', 'ram', 0, 'Y', 'Y', 'sys', '2023-04-17 14:36:51', 'sys', '2023-04-17 14:36:51',
        'N'),
       (70, 4, 'wma', 'music', 'wma', 'wma', 0, 'Y', 'Y', 'sys', '2023-04-17 14:36:51', 'sys', '2023-04-17 14:36:51',
        'N'),
       (71, 4, 'mmf', 'music', 'mmf', 'mmf', 0, 'Y', 'Y', 'sys', '2023-04-17 14:36:51', 'sys', '2023-04-17 14:36:51',
        'N'),
       (72, 4, 'amr', 'music', 'amr', 'amr', 0, 'Y', 'Y', 'sys', '2023-04-17 14:36:51', 'sys', '2023-04-17 14:36:51',
        'N'),
       (73, 4, 'aac', 'music', 'aac', 'aac', 0, 'Y', 'Y', 'sys', '2023-04-17 14:36:51', 'sys', '2023-04-17 14:36:51',
        'N'),
       (74, 4, 'flac', 'music', 'flac', 'flac', 0, 'Y', 'Y', 'sys', '2023-04-17 14:36:51', 'sys', '2023-04-17 14:36:51',
        'N'),
       (75, 5, 'md', 'doc', 'md', 'md', 0, 'Y', 'Y', 'sys', '2023-04-17 14:35:27', 'sys', '2023-04-17 14:35:27', 'N'),
       (76, 5, 'markdown', 'doc', 'markdown', 'markdown', 0, 'Y', 'Y', 'sys', '2023-04-17 14:35:27', 'sys',
        '2023-04-17 14:35:27', 'N');
/*!40000 ALTER TABLE `sys_dict` ENABLE KEYS */;
UNLOCK
TABLES;