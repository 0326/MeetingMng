/*
Navicat MySQL Data Transfer

Source Server         : MeetingMngDBConn
Source Server Version : 50541
Source Host           : localhost:3306
Source Database       : meetingmngdatabase

Target Server Type    : MYSQL
Target Server Version : 50541
File Encoding         : 65001

Date: 2015-04-01 17:03:00
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for activate
-- ----------------------------
DROP TABLE IF EXISTS `activate`;
CREATE TABLE `activate` (
  `activateAddr` varchar(80) NOT NULL,
  `activateMode` tinyint(1) NOT NULL,
  `activateInfo` varchar(100) NOT NULL,
  PRIMARY KEY (`activateAddr`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of activate
-- ----------------------------

-- ----------------------------
-- Table structure for company_and_company_admin
-- ----------------------------
DROP TABLE IF EXISTS `company_and_company_admin`;
CREATE TABLE `company_and_company_admin` (
  `companyName` varchar(50) NOT NULL,
  `username` varchar(80) NOT NULL,
  `password` varchar(15) NOT NULL,
  `registerTime` datetime NOT NULL,
  `location` char(9) NOT NULL,
  `type` char(6) NOT NULL,
  `isLogin` tinyint(1) NOT NULL,
  `email` varchar(80) DEFAULT NULL,
  `name` varchar(20) NOT NULL,
  `sex` tinyint(1) NOT NULL,
  `officePhone` varchar(20) DEFAULT NULL,
  `avatarUrl` varchar(80) NOT NULL,
  `officeLocation` varchar(80) DEFAULT NULL,
  `cellphone` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`companyName`),
  KEY `FK_company_province_cityCode` (`location`),
  KEY `FK_company_industry_industryCode` (`type`),
  CONSTRAINT `FK_company_industry_industryCode` FOREIGN KEY (`type`) REFERENCES `industry` (`industryCode`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `FK_company_province_cityCode` FOREIGN KEY (`location`) REFERENCES `province_and_city` (`cityCode`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of company_and_company_admin
-- ----------------------------

-- ----------------------------
-- Table structure for industry
-- ----------------------------
DROP TABLE IF EXISTS `industry`;
CREATE TABLE `industry` (
  `industryCode` char(6) NOT NULL,
  `industryName` varchar(20) NOT NULL,
  `category` varchar(20) NOT NULL,
  PRIMARY KEY (`industryCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of industry
-- ----------------------------

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

-- ----------------------------
-- Table structure for province_and_city
-- ----------------------------
DROP TABLE IF EXISTS `province_and_city`;
CREATE TABLE `province_and_city` (
  `cityCode` char(9) NOT NULL,
  `cityName` varchar(20) NOT NULL,
  `provinceName` varchar(15) NOT NULL,
  PRIMARY KEY (`cityCode`),
  KEY `provinceAndCity` (`provinceName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of province_and_city
-- ----------------------------
