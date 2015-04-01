/*
Navicat MySQL Data Transfer

Source Server         : MeetingMngDBConn
Source Server Version : 50541
Source Host           : localhost:3306
Source Database       : meetingmngdatabase

Target Server Type    : MYSQL
Target Server Version : 50541
File Encoding         : 65001

Date: 2015-03-30 15:10:48
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ordinary_user
-- ----------------------------
DROP TABLE IF EXISTS `ordinary_user`;
CREATE TABLE `ordinary_user` (
  `cellphone` varchar(15) NOT NULL,
  `isCellphoneHide` tinyint(1) NOT NULL,
  `name` varchar(20) NOT NULL,
  `companyName` varchar(50) NOT NULL,
  `departmentNo` char(30) NOT NULL,
  `password` varchar(15) NOT NULL,
  `isRegister` tinyint(1) NOT NULL,
  `isLogin` tinyint(1) NOT NULL,
  `registerTime` datetime NOT NULL,
  `email` varchar(80) DEFAULT NULL,
  `isBindEmail` tinyint(1) NOT NULL,
  `sex` tinyint(1) NOT NULL,
  `officePhone` varchar(20) DEFAULT NULL,
  `position` varchar(30) DEFAULT NULL,
  `avatarUrl` varchar(80) NOT NULL,
  `officeLocation` varchar(80) DEFAULT NULL,
  `workNo` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`cellphone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ordinary_user
-- ----------------------------
