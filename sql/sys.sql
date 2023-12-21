/*
 Navicat Premium Data Transfer

 Source Server         : kid626
 Source Server Type    : MySQL
 Source Server Version : 80025
 Source Host           : localhost:3306
 Source Schema         : demo

 Target Server Type    : MySQL
 Target Server Version : 80025
 File Encoding         : 65001

 Date: 20/06/2022 15:27:12
*/

SET NAMES utf8mb4;
SET
FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config`
(
    `id`           bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
    `config_code`  varchar(32) COLLATE utf8mb4_general_ci                   DEFAULT NULL COMMENT '配置唯一编码',
    `config_name`  varchar(255) COLLATE utf8mb4_general_ci                  DEFAULT NULL COMMENT '配置名称',
    `config_value` text COLLATE utf8mb4_general_ci COMMENT '配置内容',
    `is_default`   char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '是否系统内置',
    `is_enable`    char(1) COLLATE utf8mb4_general_ci                       DEFAULT NULL COMMENT '是否启用',
    `create_user`  varchar(32) COLLATE utf8mb4_general_ci                   DEFAULT NULL COMMENT '创建用户',
    `create_time`  datetime                                                 DEFAULT NULL COMMENT '创建时间',
    `update_user`  varchar(32) COLLATE utf8mb4_general_ci                   DEFAULT NULL COMMENT '更新用户',
    `update_time`  datetime                                                 DEFAULT NULL COMMENT '更新时间',
    `is_deleted`   char(1) COLLATE utf8mb4_general_ci                       DEFAULT NULL COMMENT '是否删除',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT='系统配置表';
-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`
(
    `id`          bigint(0) NOT NULL COMMENT '主键',
    `p_id`        bigint(0) NULL DEFAULT NULL COMMENT '父主键',
    `dict_code`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典编码',
    `p_code`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '父编码',
    `dict_name`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典名称',
    `dict_value`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典值',
    `sort_no`     int(0) NULL DEFAULT NULL COMMENT '排序',
    `is_default`  char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否默认',
    `is_enable`   char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否启用',
    `create_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建用户',
    `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `update_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新用户',
    `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `is_deleted`  char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统数据字典' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`
(
    `id`             bigint(0) NOT NULL COMMENT '主键',
    `module`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作模块',
    `business_type`  int(0) NULL DEFAULT NULL COMMENT '业务类型（0查询 1新增 2修改 3删除 4其他）',
    `method`         varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求方法',
    `request_method` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求方式（0GET 1POST 2PUT 3DELETE）',
    `operator_type`  int(0) NULL DEFAULT NULL COMMENT '操作类别（0其它 1后台用户 2手机端用户）',
    `oper_url`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求URL',
    `oper_ip`        varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '主机地址',
    `oper_location`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作地点',
    `oper_param`     text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '请求参数',
    `oper_result`    text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '返回参数',
    `status`         int(0) NULL DEFAULT NULL COMMENT '操作状态（0正常 1异常）',
    `error_msg`      text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '错误消息',
    `create_user`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求用户',
    `create_time`    datetime(0) NULL DEFAULT NULL COMMENT '请求时间',
    `is_deleted`     char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统日志记录' ROW_FORMAT = Dynamic;

SET
FOREIGN_KEY_CHECKS = 1;
