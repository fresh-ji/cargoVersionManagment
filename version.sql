/*
Navicat MySQL Data Transfer

Source Server         : link
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : version

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2018-01-08 16:58:23
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for affiliation
-- ----------------------------
DROP TABLE IF EXISTS `affiliation`;
CREATE TABLE `affiliation` (
  `id` bigint(5) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL,
  `child_id` bigint(20) DEFAULT NULL,
  `parent_type` int(5) DEFAULT NULL,
  `child_type` int(5) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of affiliation
-- ----------------------------
INSERT INTO `affiliation` VALUES ('7', '2018001', '200', '1', '2');
INSERT INTO `affiliation` VALUES ('8', '200', '3627562825', '2', '3');

-- ----------------------------
-- Table structure for general_node
-- ----------------------------
DROP TABLE IF EXISTS `general_node`;
CREATE TABLE `general_node` (
  `id` bigint(5) NOT NULL AUTO_INCREMENT,
  `node_id` bigint(20) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `type` int(5) DEFAULT NULL,
  `version` int(5) DEFAULT NULL,
  `repo_path` varchar(100) DEFAULT NULL,
  `user` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of general_node
-- ----------------------------
INSERT INTO `general_node` VALUES ('6', '2018001', '2018001', '1', '1', 'F:/mySpace/project/2018001', 'jihang');
INSERT INTO `general_node` VALUES ('9', '200', '200', '2', '1', 'F:\\mySpace\\task\\200', 'xiaozhai');
INSERT INTO `general_node` VALUES ('10', '3627562825', 'refs/heads/xiaodu@FmySpacetask200', '3', '1', 'F:\\mySpace\\task\\200\\.git', 'xiaodu');

-- ----------------------------
-- Table structure for node_snapshot
-- ----------------------------
DROP TABLE IF EXISTS `node_snapshot`;
CREATE TABLE `node_snapshot` (
  `id` bigint(5) NOT NULL AUTO_INCREMENT,
  `node_id` bigint(20) DEFAULT NULL,
  `edition` int(5) DEFAULT NULL,
  `version_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of node_snapshot
-- ----------------------------
INSERT INTO `node_snapshot` VALUES ('6', '2018001', '1', '401e9eeaeb256bd67ca40ff2efa8714859b00ced');
INSERT INTO `node_snapshot` VALUES ('9', '200', '1', '4e491774d40207446cb6565c47dcb87fcf25fac2');
INSERT INTO `node_snapshot` VALUES ('10', '200', '1', '831f0edf006948f006bd4b6c917cfc73d36a2288');

-- ----------------------------
-- Table structure for push_inform
-- ----------------------------
DROP TABLE IF EXISTS `push_inform`;
CREATE TABLE `push_inform` (
  `id` bigint(5) NOT NULL AUTO_INCREMENT,
  `node_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of push_inform
-- ----------------------------
INSERT INTO `push_inform` VALUES ('2', '3627562825');
INSERT INTO `push_inform` VALUES ('3', '3627562825');
