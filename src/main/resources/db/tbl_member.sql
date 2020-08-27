/*
Navicat MySQL Data Transfer

Source Server         : 本机
Source Server Version : 80019
Source Host           : localhost:3306
Source Database       : cool-card

Target Server Type    : MYSQL
Target Server Version : 80019
File Encoding         : 65001

Date: 2020-08-27 14:44:06
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tbl_member
-- ----------------------------
DROP TABLE IF EXISTS `tbl_member`;
CREATE TABLE `tbl_member` (
  `id` int NOT NULL AUTO_INCREMENT,
  `member_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `age` int DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `gmt_create` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  `avatar` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_member
-- ----------------------------
INSERT INTO `tbl_member` VALUES ('1', '港区流机数量及作业量统计', '12', 'MALE', '2020-08-27 14:38:37', null, '/images/1598510317609login-bg2.jpg');

-- ----------------------------
-- Table structure for tbl_shiro_admin
-- ----------------------------
DROP TABLE IF EXISTS `tbl_shiro_admin`;
CREATE TABLE `tbl_shiro_admin` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '名称',
  `role_id` int DEFAULT NULL COMMENT '角色',
  `phone_no` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '手机号码',
  `login_pwd` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '登录密码',
  `salt` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '盐值',
  `status` tinyint DEFAULT NULL COMMENT '状态',
  `is_super_admin` tinyint DEFAULT '0' COMMENT '是否为超级管理员',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  `cipher_authorized` tinyint(1) DEFAULT '0',
  `export_authorized` tinyint(1) DEFAULT '0',
  `sort_authorized` tinyint(1) DEFAULT '0' COMMENT '排序权限',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=153 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='平台管理员';

-- ----------------------------
-- Records of tbl_shiro_admin
-- ----------------------------
INSERT INTO `tbl_shiro_admin` VALUES ('152', '唐全成', '23', 'tangquancheng', '8DE5D2BCE0C527E17988234DAB660582', '7043aee1', '1', '1', '2020-08-27 14:09:14', null, '0', '0', '0');

-- ----------------------------
-- Table structure for tbl_shiro_navigation
-- ----------------------------
DROP TABLE IF EXISTS `tbl_shiro_navigation`;
CREATE TABLE `tbl_shiro_navigation` (
  `id` int NOT NULL AUTO_INCREMENT,
  `function_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '功能名称',
  `parent_id` int DEFAULT NULL COMMENT '父级菜单',
  `icon` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '标签类',
  `sort` int DEFAULT NULL COMMENT '排序',
  `url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '功能地址',
  `type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '类型',
  `comment` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `is_available` tinyint DEFAULT NULL COMMENT '是否可用',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=259 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='平台导航';

-- ----------------------------
-- Records of tbl_shiro_navigation
-- ----------------------------
INSERT INTO `tbl_shiro_navigation` VALUES ('253', '系统管理', null, 'fa fa-cog', '10', '', null, '', '1', '2020-08-27 14:13:52', null);
INSERT INTO `tbl_shiro_navigation` VALUES ('254', '菜单管理', '253', '', '1', '/navigation', null, '', '1', '2020-08-27 14:14:23', null);
INSERT INTO `tbl_shiro_navigation` VALUES ('255', '用户管理', '253', '', '2', '/admin', null, '', '1', '2020-08-27 14:14:42', null);
INSERT INTO `tbl_shiro_navigation` VALUES ('256', '角色管理', '253', '', '3', '/role', null, '', '1', '2020-08-27 14:14:59', null);
INSERT INTO `tbl_shiro_navigation` VALUES ('257', '会员管理', null, 'fa fa-users', '9', '', null, '', '1', '2020-08-27 14:33:28', null);
INSERT INTO `tbl_shiro_navigation` VALUES ('258', '会员列表', '257', '', '1', '/member', null, '', '1', '2020-08-27 14:33:51', null);

-- ----------------------------
-- Table structure for tbl_shiro_role
-- ----------------------------
DROP TABLE IF EXISTS `tbl_shiro_role`;
CREATE TABLE `tbl_shiro_role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '角色名称',
  `code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '角色代码',
  `is_available` tinyint DEFAULT NULL COMMENT '是否可用',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  `comment` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `creator_id` int DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='平台角色';

-- ----------------------------
-- Records of tbl_shiro_role
-- ----------------------------
INSERT INTO `tbl_shiro_role` VALUES ('23', '经理', 'jingli', '1', '2020-08-27 14:15:22', null, null, null);

-- ----------------------------
-- Table structure for tbl_shiro_role_nav
-- ----------------------------
DROP TABLE IF EXISTS `tbl_shiro_role_nav`;
CREATE TABLE `tbl_shiro_role_nav` (
  `id` int NOT NULL AUTO_INCREMENT,
  `role_id` int DEFAULT NULL COMMENT '角色',
  `navigation_id` int DEFAULT NULL COMMENT '功能菜单',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `role_id` (`role_id`,`navigation_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2446 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='导航与角色关系';

-- ----------------------------
-- Records of tbl_shiro_role_nav
-- ----------------------------
INSERT INTO `tbl_shiro_role_nav` VALUES ('2442', '23', '253', '2020-08-27 14:15:32');
INSERT INTO `tbl_shiro_role_nav` VALUES ('2443', '23', '254', '2020-08-27 14:15:32');
INSERT INTO `tbl_shiro_role_nav` VALUES ('2444', '23', '255', '2020-08-27 14:15:32');
INSERT INTO `tbl_shiro_role_nav` VALUES ('2445', '23', '256', '2020-08-27 14:15:32');
