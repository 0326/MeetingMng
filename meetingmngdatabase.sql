/*
Navicat MySQL Data Transfer

Source Server         : MeetingMngDBConn
Source Server Version : 50541
Source Host           : localhost:3306
Source Database       : meetingmngdatabase

Target Server Type    : MYSQL
Target Server Version : 50541
File Encoding         : 65001

Date: 2015-04-08 08:55:17
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
  `sendTime` datetime NOT NULL,
  `username` varchar(50) NOT NULL,
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
  `isLogin` tinyint(1) DEFAULT NULL,
  `email` varchar(80) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `sex` tinyint(1) DEFAULT NULL,
  `officePhone` varchar(20) DEFAULT NULL,
  `avatarUrl` varchar(80) DEFAULT NULL,
  `officeLocation` varchar(80) DEFAULT NULL,
  `cellphone` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`username`),
  KEY `FK_company_province_cityCode` (`location`),
  KEY `FK_company_industry_industryCode` (`type`),
  CONSTRAINT `FK_company_industry_industryCode` FOREIGN KEY (`type`) REFERENCES `industry` (`industryCode`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `FK_company_province_cityCode` FOREIGN KEY (`location`) REFERENCES `province_and_city` (`cityCode`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of company_and_company_admin
-- ----------------------------
INSERT INTO `company_and_company_admin` VALUES ('11111', '1833559609@qq.com', '111111', '2015-04-07 09:10:46', '101200301', '12289', '0', null, null, '0', null, null, null, null);

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
INSERT INTO `industry` VALUES ('100001', '酒店', '服务业');
INSERT INTO `industry` VALUES ('100002', '餐饮', '服务业');
INSERT INTO `industry` VALUES ('100003', '旅游', '服务业');
INSERT INTO `industry` VALUES ('100004', '休闲/娱乐/健身', '服务业');
INSERT INTO `industry` VALUES ('100005', '私人/家政服务', '服务业');
INSERT INTO `industry` VALUES ('10008', '电子/半导体', 'IT行业');
INSERT INTO `industry` VALUES ('110001', '环境', '其他');
INSERT INTO `industry` VALUES ('110002', '农/林/牧/渔', '其他');
INSERT INTO `industry` VALUES ('110003', '研究所/研究院', '其他');
INSERT INTO `industry` VALUES ('110004', '公共事业', '其他');
INSERT INTO `industry` VALUES ('110005', '非营利组织', '其他');
INSERT INTO `industry` VALUES ('110006', '政府部门', '其他');
INSERT INTO `industry` VALUES ('110007', '其他', '其他');
INSERT INTO `industry` VALUES ('12289', '会计/审计', '专业服务');
INSERT INTO `industry` VALUES ('12290', '人力资源', '专业服务');
INSERT INTO `industry` VALUES ('12291', '管理咨询', '专业服务');
INSERT INTO `industry` VALUES ('12292', '法律', '专业服务');
INSERT INTO `industry` VALUES ('12293', '检测/认证', '专业服务');
INSERT INTO `industry` VALUES ('12294', '翻译', '专业服务');
INSERT INTO `industry` VALUES ('16385', '高等教育', '教育培训行业');
INSERT INTO `industry` VALUES ('16386', '初中等教育', '教育培训行业');
INSERT INTO `industry` VALUES ('16387', '培训', '教育培训行业');
INSERT INTO `industry` VALUES ('20481', '高等教育', '教育培训行业');
INSERT INTO `industry` VALUES ('20482', '初中等教育', '教育培训行业');
INSERT INTO `industry` VALUES ('20483', '培训', '教育培训行业');
INSERT INTO `industry` VALUES ('24577', '日用品/化妆品', '消费品行业');
INSERT INTO `industry` VALUES ('24578', '食品/饮料', '消费品行业');
INSERT INTO `industry` VALUES ('24579', '服装/纺织', '消费品行业');
INSERT INTO `industry` VALUES ('24580', '家电/数码产品', '消费品行业');
INSERT INTO `industry` VALUES ('24581', '奢侈品/珠宝', '消费品行业');
INSERT INTO `industry` VALUES ('24582', '酒品', '消费品行业');
INSERT INTO `industry` VALUES ('24583', '烟草业', '消费品行业');
INSERT INTO `industry` VALUES ('28673', '广告/公关/会展', '文化传媒行业');
INSERT INTO `industry` VALUES ('28674', '报纸/杂志', '文化传媒行业');
INSERT INTO `industry` VALUES ('28675', '广播', '文化传媒行业');
INSERT INTO `industry` VALUES ('28676', '影视', '文化传媒行业');
INSERT INTO `industry` VALUES ('28677', '出版', '文化传媒行业');
INSERT INTO `industry` VALUES ('28678', '艺术/工艺', '文化传媒行业');
INSERT INTO `industry` VALUES ('28679', '体育', '文化传媒行业');
INSERT INTO `industry` VALUES ('4097', '计算机软件', 'IT行业');
INSERT INTO `industry` VALUES ('4098', '计算机硬件', 'IT行业');
INSERT INTO `industry` VALUES ('4099', 'IT服务', 'IT行业');
INSERT INTO `industry` VALUES ('4100', '互联网', 'IT行业');
INSERT INTO `industry` VALUES ('4101', '电子商务', 'IT行业');
INSERT INTO `industry` VALUES ('4102', '游戏', 'IT行业');
INSERT INTO `industry` VALUES ('4103', '通信', 'IT行业');
INSERT INTO `industry` VALUES ('60008', '办公用品', '消费品行业');
INSERT INTO `industry` VALUES ('70008', '动漫', '文化传媒行业');
INSERT INTO `industry` VALUES ('80001', '建筑设计/规划', '建筑/房地产行业');
INSERT INTO `industry` VALUES ('80002', '土木工程', '建筑/房地产行业');
INSERT INTO `industry` VALUES ('80003', '房地产', '建筑/房地产行业');
INSERT INTO `industry` VALUES ('80004', '物业管理', '建筑/房地产行业');
INSERT INTO `industry` VALUES ('80005', '建材', '建筑/房地产行业');
INSERT INTO `industry` VALUES ('80006', '装修装潢', '建筑/房地产行业');
INSERT INTO `industry` VALUES ('8193', '银行', '金融行业');
INSERT INTO `industry` VALUES ('8194', '保险', '金融行业');
INSERT INTO `industry` VALUES ('8195', '证券/基金/期货', '金融行业');
INSERT INTO `industry` VALUES ('8196', '投资', '金融行业');
INSERT INTO `industry` VALUES ('90001', '进出口', '贸易物流行业');
INSERT INTO `industry` VALUES ('90002', '批发/零售', '贸易物流行业');
INSERT INTO `industry` VALUES ('90003', '商店/超市', '贸易物流行业');
INSERT INTO `industry` VALUES ('90004', '物流/仓储', '贸易物流行业');
INSERT INTO `industry` VALUES ('90005', '运输/铁路/航空', '贸易物流行业');

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
INSERT INTO `province_and_city` VALUES ('101010100', '北京', '北京');
INSERT INTO `province_and_city` VALUES ('101010200', '海淀', '北京');
INSERT INTO `province_and_city` VALUES ('101010300', '朝阳', '北京');
INSERT INTO `province_and_city` VALUES ('101010400', '顺义', '北京');
INSERT INTO `province_and_city` VALUES ('101010500', '怀柔', '北京');
INSERT INTO `province_and_city` VALUES ('101010600', '通州', '北京');
INSERT INTO `province_and_city` VALUES ('101010700', '昌平', '北京');
INSERT INTO `province_and_city` VALUES ('101010800', '延庆', '北京');
INSERT INTO `province_and_city` VALUES ('101010900', '丰台', '北京');
INSERT INTO `province_and_city` VALUES ('101011000', '石景山', '北京');
INSERT INTO `province_and_city` VALUES ('101011100', '大兴', '北京');
INSERT INTO `province_and_city` VALUES ('101011200', '房山', '北京');
INSERT INTO `province_and_city` VALUES ('101011300', '密云', '北京');
INSERT INTO `province_and_city` VALUES ('101011400', '门头沟', '北京');
INSERT INTO `province_and_city` VALUES ('101011500', '平谷', '北京');
INSERT INTO `province_and_city` VALUES ('101011600', '八达岭', '北京');
INSERT INTO `province_and_city` VALUES ('101011700', '佛爷顶', '北京');
INSERT INTO `province_and_city` VALUES ('101011800', '汤河口', '北京');
INSERT INTO `province_and_city` VALUES ('101011900', '密云上甸子', '北京');
INSERT INTO `province_and_city` VALUES ('101012000', '斋堂', '北京');
INSERT INTO `province_and_city` VALUES ('101012100', '霞云岭', '北京');
INSERT INTO `province_and_city` VALUES ('101012200', '北京城区', '北京');
INSERT INTO `province_and_city` VALUES ('101020100', '上海', '上海');
INSERT INTO `province_and_city` VALUES ('101020200', '闵行', '上海');
INSERT INTO `province_and_city` VALUES ('101020300', '宝山', '上海');
INSERT INTO `province_and_city` VALUES ('101020500', '嘉定', '上海');
INSERT INTO `province_and_city` VALUES ('101020600', '南汇', '上海');
INSERT INTO `province_and_city` VALUES ('101020700', '金山', '上海');
INSERT INTO `province_and_city` VALUES ('101020800', '青浦', '上海');
INSERT INTO `province_and_city` VALUES ('101020900', '松江', '上海');
INSERT INTO `province_and_city` VALUES ('101021000', '奉贤', '上海');
INSERT INTO `province_and_city` VALUES ('101021100', '崇明', '上海');
INSERT INTO `province_and_city` VALUES ('101021200', '徐家汇', '上海');
INSERT INTO `province_and_city` VALUES ('101021300', '浦东', '上海');
INSERT INTO `province_and_city` VALUES ('101030100', '天津', '天津');
INSERT INTO `province_and_city` VALUES ('101030200', '武清', '天津');
INSERT INTO `province_and_city` VALUES ('101030300', '宝坻', '天津');
INSERT INTO `province_and_city` VALUES ('101030400', '东丽', '天津');
INSERT INTO `province_and_city` VALUES ('101030500', '西青', '天津');
INSERT INTO `province_and_city` VALUES ('101030600', '北辰', '天津');
INSERT INTO `province_and_city` VALUES ('101030700', '宁河', '天津');
INSERT INTO `province_and_city` VALUES ('101030800', '汉沽', '天津');
INSERT INTO `province_and_city` VALUES ('101030900', '静海', '天津');
INSERT INTO `province_and_city` VALUES ('101031000', '津南', '天津');
INSERT INTO `province_and_city` VALUES ('101031100', '塘沽', '天津');
INSERT INTO `province_and_city` VALUES ('101031200', '大港', '天津');
INSERT INTO `province_and_city` VALUES ('101031400', '蓟县', '天津');
INSERT INTO `province_and_city` VALUES ('101040100', '重庆', '重庆');
INSERT INTO `province_and_city` VALUES ('101040200', '永川', '重庆');
INSERT INTO `province_and_city` VALUES ('101040300', '合川', '重庆');
INSERT INTO `province_and_city` VALUES ('101040400', '南川', '重庆');
INSERT INTO `province_and_city` VALUES ('101040500', '江津', '重庆');
INSERT INTO `province_and_city` VALUES ('101040600', '万盛', '重庆');
INSERT INTO `province_and_city` VALUES ('101040700', '渝北', '重庆');
INSERT INTO `province_and_city` VALUES ('101040800', '北碚', '重庆');
INSERT INTO `province_and_city` VALUES ('101040900', '巴南', '重庆');
INSERT INTO `province_and_city` VALUES ('101041000', '长寿', '重庆');
INSERT INTO `province_and_city` VALUES ('101041100', '黔江', '重庆');
INSERT INTO `province_and_city` VALUES ('101041200', '万州天城', '重庆');
INSERT INTO `province_and_city` VALUES ('101041300', '万州龙宝', '重庆');
INSERT INTO `province_and_city` VALUES ('101041400', '涪陵', '重庆');
INSERT INTO `province_and_city` VALUES ('101041500', '开县', '重庆');
INSERT INTO `province_and_city` VALUES ('101041600', '城口', '重庆');
INSERT INTO `province_and_city` VALUES ('101041700', '云阳', '重庆');
INSERT INTO `province_and_city` VALUES ('101041800', '巫溪', '重庆');
INSERT INTO `province_and_city` VALUES ('101041900', '奉节', '重庆');
INSERT INTO `province_and_city` VALUES ('101042000', '巫山', '重庆');
INSERT INTO `province_and_city` VALUES ('101042100', '潼南', '重庆');
INSERT INTO `province_and_city` VALUES ('101042200', '垫江', '重庆');
INSERT INTO `province_and_city` VALUES ('101042300', '梁平', '重庆');
INSERT INTO `province_and_city` VALUES ('101042400', '忠县', '重庆');
INSERT INTO `province_and_city` VALUES ('101042500', '石柱', '重庆');
INSERT INTO `province_and_city` VALUES ('101042600', '大足', '重庆');
INSERT INTO `province_and_city` VALUES ('101042700', '荣昌', '重庆');
INSERT INTO `province_and_city` VALUES ('101042800', '铜梁', '重庆');
INSERT INTO `province_and_city` VALUES ('101042900', '璧山', '重庆');
INSERT INTO `province_and_city` VALUES ('101043000', '丰都', '重庆');
INSERT INTO `province_and_city` VALUES ('101043100', '武隆', '重庆');
INSERT INTO `province_and_city` VALUES ('101043200', '彭水', '重庆');
INSERT INTO `province_and_city` VALUES ('101043300', '綦江', '重庆');
INSERT INTO `province_and_city` VALUES ('101043400', '酉阳', '重庆');
INSERT INTO `province_and_city` VALUES ('101043600', '秀山', '重庆');
INSERT INTO `province_and_city` VALUES ('101043700', '沙坪坝', '重庆');
INSERT INTO `province_and_city` VALUES ('101050101', '哈尔滨', '黑龙江');
INSERT INTO `province_and_city` VALUES ('101050201', '齐齐哈尔', '黑龙江');
INSERT INTO `province_and_city` VALUES ('101050301', '牡丹江', '黑龙江');
INSERT INTO `province_and_city` VALUES ('101050401', '佳木斯', '黑龙江');
INSERT INTO `province_and_city` VALUES ('101050501', '绥化', '黑龙江');
INSERT INTO `province_and_city` VALUES ('101050601', '黑河', '黑龙江');
INSERT INTO `province_and_city` VALUES ('101050701', '大兴安岭', '黑龙江');
INSERT INTO `province_and_city` VALUES ('101050801', '伊春', '黑龙江');
INSERT INTO `province_and_city` VALUES ('101050901', '大庆', '黑龙江');
INSERT INTO `province_and_city` VALUES ('101051002', '七台河', '黑龙江');
INSERT INTO `province_and_city` VALUES ('101051101', '鸡西', '黑龙江');
INSERT INTO `province_and_city` VALUES ('101051201', '鹤岗', '黑龙江');
INSERT INTO `province_and_city` VALUES ('101051301', '双鸭山', '黑龙江');
INSERT INTO `province_and_city` VALUES ('101060101', '长春', '吉林');
INSERT INTO `province_and_city` VALUES ('101060201', '吉林', '吉林');
INSERT INTO `province_and_city` VALUES ('101060301', '延吉', '吉林');
INSERT INTO `province_and_city` VALUES ('101060401', '四平', '吉林');
INSERT INTO `province_and_city` VALUES ('101060501', '通化', '吉林');
INSERT INTO `province_and_city` VALUES ('101060601', '白城', '吉林');
INSERT INTO `province_and_city` VALUES ('101060701', '辽源', '吉林');
INSERT INTO `province_and_city` VALUES ('101060801', '松原', '吉林');
INSERT INTO `province_and_city` VALUES ('101060901', '白山', '吉林');
INSERT INTO `province_and_city` VALUES ('101070101', '沈阳', '辽宁');
INSERT INTO `province_and_city` VALUES ('101070201', '大连', '辽宁');
INSERT INTO `province_and_city` VALUES ('101070301', '鞍山', '辽宁');
INSERT INTO `province_and_city` VALUES ('101070401', '抚顺', '辽宁');
INSERT INTO `province_and_city` VALUES ('101070501', '本溪', '辽宁');
INSERT INTO `province_and_city` VALUES ('101070601', '丹东', '辽宁');
INSERT INTO `province_and_city` VALUES ('101070701', '锦州', '辽宁');
INSERT INTO `province_and_city` VALUES ('101070801', '营口', '辽宁');
INSERT INTO `province_and_city` VALUES ('101070901', '阜新', '辽宁');
INSERT INTO `province_and_city` VALUES ('101071001', '辽阳', '辽宁');
INSERT INTO `province_and_city` VALUES ('101071101', '铁岭', '辽宁');
INSERT INTO `province_and_city` VALUES ('101071201', '朝阳', '辽宁');
INSERT INTO `province_and_city` VALUES ('101071301', '盘锦', '辽宁');
INSERT INTO `province_and_city` VALUES ('101071401', '葫芦岛', '辽宁');
INSERT INTO `province_and_city` VALUES ('101080101', '呼和浩特', '内蒙古');
INSERT INTO `province_and_city` VALUES ('101080201', '包头', '内蒙古');
INSERT INTO `province_and_city` VALUES ('101080301', '乌海', '内蒙古');
INSERT INTO `province_and_city` VALUES ('101080401', '集宁', '内蒙古');
INSERT INTO `province_and_city` VALUES ('101080501', '通辽', '内蒙古');
INSERT INTO `province_and_city` VALUES ('101080601', '赤峰', '内蒙古');
INSERT INTO `province_and_city` VALUES ('101080701', '鄂尔多斯', '内蒙古');
INSERT INTO `province_and_city` VALUES ('101080801', '临河', '内蒙古');
INSERT INTO `province_and_city` VALUES ('101080901', '锡林浩特', '内蒙古');
INSERT INTO `province_and_city` VALUES ('101081000', '呼伦贝尔', '内蒙古');
INSERT INTO `province_and_city` VALUES ('101081101', '乌兰浩特', '内蒙古');
INSERT INTO `province_and_city` VALUES ('101081201', '阿拉善左旗', '内蒙古');
INSERT INTO `province_and_city` VALUES ('101090101', '石家庄', '河北');
INSERT INTO `province_and_city` VALUES ('101090201', '保定', '河北');
INSERT INTO `province_and_city` VALUES ('101090301', '张家口', '河北');
INSERT INTO `province_and_city` VALUES ('101090402', '承德', '河北');
INSERT INTO `province_and_city` VALUES ('101090501', '唐山', '河北');
INSERT INTO `province_and_city` VALUES ('101090601', '廊坊', '河北');
INSERT INTO `province_and_city` VALUES ('101090701', '沧州', '河北');
INSERT INTO `province_and_city` VALUES ('101090801', '衡水', '河北');
INSERT INTO `province_and_city` VALUES ('101090901', '邢台', '河北');
INSERT INTO `province_and_city` VALUES ('101091001', '邯郸', '河北');
INSERT INTO `province_and_city` VALUES ('101091101', '秦皇岛', '河北');
INSERT INTO `province_and_city` VALUES ('101100101', '太原', '山西');
INSERT INTO `province_and_city` VALUES ('101100201', '大同', '山西');
INSERT INTO `province_and_city` VALUES ('101100301', '阳泉', '山西');
INSERT INTO `province_and_city` VALUES ('101100401', '晋中', '山西');
INSERT INTO `province_and_city` VALUES ('101100501', '长治', '山西');
INSERT INTO `province_and_city` VALUES ('101100601', '晋城', '山西');
INSERT INTO `province_and_city` VALUES ('101100701', '临汾', '山西');
INSERT INTO `province_and_city` VALUES ('101100801', '运城', '山西');
INSERT INTO `province_and_city` VALUES ('101100901', '朔州', '山西');
INSERT INTO `province_and_city` VALUES ('101101001', '忻州', '山西');
INSERT INTO `province_and_city` VALUES ('101101100', '吕梁', '山西');
INSERT INTO `province_and_city` VALUES ('101110101', '西安', '陕西');
INSERT INTO `province_and_city` VALUES ('101110200', '咸阳', '陕西');
INSERT INTO `province_and_city` VALUES ('101110300', '延安', '陕西');
INSERT INTO `province_and_city` VALUES ('101110401', '榆林', '陕西');
INSERT INTO `province_and_city` VALUES ('101110501', '渭南', '陕西');
INSERT INTO `province_and_city` VALUES ('101110601', '商洛', '陕西');
INSERT INTO `province_and_city` VALUES ('101110701', '安康', '陕西');
INSERT INTO `province_and_city` VALUES ('101110801', '汉中', '陕西');
INSERT INTO `province_and_city` VALUES ('101110901', '宝鸡', '陕西');
INSERT INTO `province_and_city` VALUES ('101111001', '铜川', '陕西');
INSERT INTO `province_and_city` VALUES ('101120101', '济南', '山东');
INSERT INTO `province_and_city` VALUES ('101120201', '青岛', '山东');
INSERT INTO `province_and_city` VALUES ('101120301', '淄博', '山东');
INSERT INTO `province_and_city` VALUES ('101120401', '德州', '山东');
INSERT INTO `province_and_city` VALUES ('101120501', '烟台', '山东');
INSERT INTO `province_and_city` VALUES ('101120601', '潍坊', '山东');
INSERT INTO `province_and_city` VALUES ('101120701', '济宁', '山东');
INSERT INTO `province_and_city` VALUES ('101120801', '泰安', '山东');
INSERT INTO `province_and_city` VALUES ('101120901', '临沂', '山东');
INSERT INTO `province_and_city` VALUES ('101121001', '菏泽', '山东');
INSERT INTO `province_and_city` VALUES ('101121101', '滨州', '山东');
INSERT INTO `province_and_city` VALUES ('101121201', '东营', '山东');
INSERT INTO `province_and_city` VALUES ('101121301', '威海', '山东');
INSERT INTO `province_and_city` VALUES ('101121401', '枣庄', '山东');
INSERT INTO `province_and_city` VALUES ('101121501', '日照', '山东');
INSERT INTO `province_and_city` VALUES ('101121601', '莱芜', '山东');
INSERT INTO `province_and_city` VALUES ('101121701', '聊城', '山东');
INSERT INTO `province_and_city` VALUES ('101130101', '乌鲁木齐', '新疆');
INSERT INTO `province_and_city` VALUES ('101130201', '克拉玛依', '新疆');
INSERT INTO `province_and_city` VALUES ('101130301', '石河子', '新疆');
INSERT INTO `province_and_city` VALUES ('101130401', '昌吉', '新疆');
INSERT INTO `province_and_city` VALUES ('101130501', '吐鲁番', '新疆');
INSERT INTO `province_and_city` VALUES ('101130601', '库尔勒', '新疆');
INSERT INTO `province_and_city` VALUES ('101130701', '阿拉尔', '新疆');
INSERT INTO `province_and_city` VALUES ('101130801', '阿克苏', '新疆');
INSERT INTO `province_and_city` VALUES ('101130901', '喀什', '新疆');
INSERT INTO `province_and_city` VALUES ('101131001', '伊宁', '新疆');
INSERT INTO `province_and_city` VALUES ('101131101', '塔城', '新疆');
INSERT INTO `province_and_city` VALUES ('101131201', '哈密', '新疆');
INSERT INTO `province_and_city` VALUES ('101131301', '和田', '新疆');
INSERT INTO `province_and_city` VALUES ('101131401', '阿勒泰', '新疆');
INSERT INTO `province_and_city` VALUES ('101131501', '阿图什', '新疆');
INSERT INTO `province_and_city` VALUES ('101131601', '博乐', '新疆');
INSERT INTO `province_and_city` VALUES ('101140101', '拉萨', '西藏');
INSERT INTO `province_and_city` VALUES ('101140201', '日喀则', '西藏');
INSERT INTO `province_and_city` VALUES ('101140301', '山南', '西藏');
INSERT INTO `province_and_city` VALUES ('101140401', '林芝', '西藏');
INSERT INTO `province_and_city` VALUES ('101140501', '昌都', '西藏');
INSERT INTO `province_and_city` VALUES ('101140601', '那曲', '西藏');
INSERT INTO `province_and_city` VALUES ('101140701', '阿里', '西藏');
INSERT INTO `province_and_city` VALUES ('101150101', '西宁', '青海');
INSERT INTO `province_and_city` VALUES ('101150201', '海东', '青海');
INSERT INTO `province_and_city` VALUES ('101150301', '黄南', '青海');
INSERT INTO `province_and_city` VALUES ('101150401', '海南', '青海');
INSERT INTO `province_and_city` VALUES ('101150501', '果洛', '青海');
INSERT INTO `province_and_city` VALUES ('101150601', '玉树', '青海');
INSERT INTO `province_and_city` VALUES ('101150701', '海西', '青海');
INSERT INTO `province_and_city` VALUES ('101150801', '海北', '青海');
INSERT INTO `province_and_city` VALUES ('101160101', '兰州', '甘肃');
INSERT INTO `province_and_city` VALUES ('101160201', '定西', '甘肃');
INSERT INTO `province_and_city` VALUES ('101160301', '平凉', '甘肃');
INSERT INTO `province_and_city` VALUES ('101160401', '庆阳', '甘肃');
INSERT INTO `province_and_city` VALUES ('101160501', '武威', '甘肃');
INSERT INTO `province_and_city` VALUES ('101160601', '金昌', '甘肃');
INSERT INTO `province_and_city` VALUES ('101160701', '张掖', '甘肃');
INSERT INTO `province_and_city` VALUES ('101160801', '酒泉', '甘肃');
INSERT INTO `province_and_city` VALUES ('101160901', '天水', '甘肃');
INSERT INTO `province_and_city` VALUES ('101161001', '武都', '甘肃');
INSERT INTO `province_and_city` VALUES ('101161101', '临夏', '甘肃');
INSERT INTO `province_and_city` VALUES ('101161201', '合作', '甘肃');
INSERT INTO `province_and_city` VALUES ('101161301', '白银', '甘肃');
INSERT INTO `province_and_city` VALUES ('101161401', '嘉峪关', '甘肃');
INSERT INTO `province_and_city` VALUES ('101170101', '银川', '宁夏');
INSERT INTO `province_and_city` VALUES ('101170201', '石嘴山', '宁夏');
INSERT INTO `province_and_city` VALUES ('101170301', '吴忠', '宁夏');
INSERT INTO `province_and_city` VALUES ('101170401', '固原', '宁夏');
INSERT INTO `province_and_city` VALUES ('101170501', '中卫', '宁夏');
INSERT INTO `province_and_city` VALUES ('101180101', '郑州', '河南');
INSERT INTO `province_and_city` VALUES ('101180201', '安阳', '河南');
INSERT INTO `province_and_city` VALUES ('101180301', '新乡', '河南');
INSERT INTO `province_and_city` VALUES ('101180401', '许昌', '河南');
INSERT INTO `province_and_city` VALUES ('101180501', '平顶山', '河南');
INSERT INTO `province_and_city` VALUES ('101180601', '信阳', '河南');
INSERT INTO `province_and_city` VALUES ('101180701', '南阳', '河南');
INSERT INTO `province_and_city` VALUES ('101180801', '开封', '河南');
INSERT INTO `province_and_city` VALUES ('101180901', '洛阳', '河南');
INSERT INTO `province_and_city` VALUES ('101181001', '商丘', '河南');
INSERT INTO `province_and_city` VALUES ('101181101', '焦作', '河南');
INSERT INTO `province_and_city` VALUES ('101181201', '鹤壁', '河南');
INSERT INTO `province_and_city` VALUES ('101181301', '濮阳', '河南');
INSERT INTO `province_and_city` VALUES ('101181401', '周口', '河南');
INSERT INTO `province_and_city` VALUES ('101181501', '漯河', '河南');
INSERT INTO `province_and_city` VALUES ('101181601', '驻马店', '河南');
INSERT INTO `province_and_city` VALUES ('101181701', '三门峡', '河南');
INSERT INTO `province_and_city` VALUES ('101181801', '济源', '河南');
INSERT INTO `province_and_city` VALUES ('101190101', '南京', '江苏');
INSERT INTO `province_and_city` VALUES ('101190201', '无锡', '江苏');
INSERT INTO `province_and_city` VALUES ('101190301', '镇江', '江苏');
INSERT INTO `province_and_city` VALUES ('101190401', '苏州', '江苏');
INSERT INTO `province_and_city` VALUES ('101190501', '南通', '江苏');
INSERT INTO `province_and_city` VALUES ('101190601', '扬州', '江苏');
INSERT INTO `province_and_city` VALUES ('101190701', '盐城', '江苏');
INSERT INTO `province_and_city` VALUES ('101190801', '徐州', '江苏');
INSERT INTO `province_and_city` VALUES ('101190901', '淮安', '江苏');
INSERT INTO `province_and_city` VALUES ('101191001', '连云港', '江苏');
INSERT INTO `province_and_city` VALUES ('101191101', '常州', '江苏');
INSERT INTO `province_and_city` VALUES ('101191201', '泰州', '江苏');
INSERT INTO `province_and_city` VALUES ('101191301', '宿迁', '江苏');
INSERT INTO `province_and_city` VALUES ('101200101', '武汉', '湖北');
INSERT INTO `province_and_city` VALUES ('101200201', '襄樊', '湖北');
INSERT INTO `province_and_city` VALUES ('101200301', '鄂州', '湖北');
INSERT INTO `province_and_city` VALUES ('101200401', '孝感', '湖北');
INSERT INTO `province_and_city` VALUES ('101200501', '黄冈', '湖北');
INSERT INTO `province_and_city` VALUES ('101200601', '黄石', '湖北');
INSERT INTO `province_and_city` VALUES ('101200701', '咸宁', '湖北');
INSERT INTO `province_and_city` VALUES ('101200801', '荆州', '湖北');
INSERT INTO `province_and_city` VALUES ('101200901', '宜昌', '湖北');
INSERT INTO `province_and_city` VALUES ('101201001', '恩施', '湖北');
INSERT INTO `province_and_city` VALUES ('101201101', '十堰', '湖北');
INSERT INTO `province_and_city` VALUES ('101201201', '神农架', '湖北');
INSERT INTO `province_and_city` VALUES ('101201301', '随州', '湖北');
INSERT INTO `province_and_city` VALUES ('101201401', '荆门', '湖北');
INSERT INTO `province_and_city` VALUES ('101201501', '天门', '湖北');
INSERT INTO `province_and_city` VALUES ('101201601', '仙桃', '湖北');
INSERT INTO `province_and_city` VALUES ('101201701', '潜江', '湖北');
INSERT INTO `province_and_city` VALUES ('101210101', '杭州', '浙江');
INSERT INTO `province_and_city` VALUES ('101210201', '湖州', '浙江');
INSERT INTO `province_and_city` VALUES ('101210301', '嘉兴', '浙江');
INSERT INTO `province_and_city` VALUES ('101210401', '宁波', '浙江');
INSERT INTO `province_and_city` VALUES ('101210501', '绍兴', '浙江');
INSERT INTO `province_and_city` VALUES ('101210601', '台州', '浙江');
INSERT INTO `province_and_city` VALUES ('101210701', '温州', '浙江');
INSERT INTO `province_and_city` VALUES ('101210801', '丽水', '浙江');
INSERT INTO `province_and_city` VALUES ('101210901', '金华', '浙江');
INSERT INTO `province_and_city` VALUES ('101211001', '衢州', '浙江');
INSERT INTO `province_and_city` VALUES ('101211101', '舟山', '浙江');
INSERT INTO `province_and_city` VALUES ('101220101', '合肥', '安徽');
INSERT INTO `province_and_city` VALUES ('101220201', '蚌埠', '安徽');
INSERT INTO `province_and_city` VALUES ('101220301', '芜湖', '安徽');
INSERT INTO `province_and_city` VALUES ('101220401', '淮南', '安徽');
INSERT INTO `province_and_city` VALUES ('101220501', '马鞍山', '安徽');
INSERT INTO `province_and_city` VALUES ('101220601', '安庆', '安徽');
INSERT INTO `province_and_city` VALUES ('101220701', '宿州', '安徽');
INSERT INTO `province_and_city` VALUES ('101220801', '阜阳', '安徽');
INSERT INTO `province_and_city` VALUES ('101220901', '亳州', '安徽');
INSERT INTO `province_and_city` VALUES ('101221001', '黄山', '安徽');
INSERT INTO `province_and_city` VALUES ('101221101', '滁州', '安徽');
INSERT INTO `province_and_city` VALUES ('101221201', '淮北', '安徽');
INSERT INTO `province_and_city` VALUES ('101221301', '铜陵', '安徽');
INSERT INTO `province_and_city` VALUES ('101221401', '宣城', '安徽');
INSERT INTO `province_and_city` VALUES ('101221501', '六安', '安徽');
INSERT INTO `province_and_city` VALUES ('101221601', '巢湖', '安徽');
INSERT INTO `province_and_city` VALUES ('101221701', '池州', '安徽');
INSERT INTO `province_and_city` VALUES ('101230101', '福州', '福建');
INSERT INTO `province_and_city` VALUES ('101230201', '厦门', '福建');
INSERT INTO `province_and_city` VALUES ('101230301', '宁德', '福建');
INSERT INTO `province_and_city` VALUES ('101230401', '莆田', '福建');
INSERT INTO `province_and_city` VALUES ('101230501', '泉州', '福建');
INSERT INTO `province_and_city` VALUES ('101230509', '晋江', '福建');
INSERT INTO `province_and_city` VALUES ('101230601', '漳州', '福建');
INSERT INTO `province_and_city` VALUES ('101230701', '龙岩', '福建');
INSERT INTO `province_and_city` VALUES ('101230801', '三明', '福建');
INSERT INTO `province_and_city` VALUES ('101230901', '南平', '福建');
INSERT INTO `province_and_city` VALUES ('101240101', '南昌', '江西');
INSERT INTO `province_and_city` VALUES ('101240201', '九江', '江西');
INSERT INTO `province_and_city` VALUES ('101240301', '上饶', '江西');
INSERT INTO `province_and_city` VALUES ('101240401', '抚州', '江西');
INSERT INTO `province_and_city` VALUES ('101240501', '宜春', '江西');
INSERT INTO `province_and_city` VALUES ('101240601', '吉安', '江西');
INSERT INTO `province_and_city` VALUES ('101240701', '赣州', '江西');
INSERT INTO `province_and_city` VALUES ('101240801', '景德镇', '江西');
INSERT INTO `province_and_city` VALUES ('101240901', '萍乡', '江西');
INSERT INTO `province_and_city` VALUES ('101241001', '新余', '江西');
INSERT INTO `province_and_city` VALUES ('101241101', '鹰潭', '江西');
INSERT INTO `province_and_city` VALUES ('101250101', '长沙', '湖南');
INSERT INTO `province_and_city` VALUES ('101250201', '湘潭', '湖南');
INSERT INTO `province_and_city` VALUES ('101250301', '株洲', '湖南');
INSERT INTO `province_and_city` VALUES ('101250401', '衡阳', '湖南');
INSERT INTO `province_and_city` VALUES ('101250501', '郴州', '湖南');
INSERT INTO `province_and_city` VALUES ('101250601', '常德', '湖南');
INSERT INTO `province_and_city` VALUES ('101250700', '益阳', '湖南');
INSERT INTO `province_and_city` VALUES ('101250801', '娄底', '湖南');
INSERT INTO `province_and_city` VALUES ('101250901', '邵阳', '湖南');
INSERT INTO `province_and_city` VALUES ('101251001', '岳阳', '湖南');
INSERT INTO `province_and_city` VALUES ('101251101', '张家界', '湖南');
INSERT INTO `province_and_city` VALUES ('101251201', '怀化', '湖南');
INSERT INTO `province_and_city` VALUES ('101251301', '黔阳', '湖南');
INSERT INTO `province_and_city` VALUES ('101251401', '永州', '湖南');
INSERT INTO `province_and_city` VALUES ('101251501', '吉首', '湖南');
INSERT INTO `province_and_city` VALUES ('101260101', '贵阳', '贵州');
INSERT INTO `province_and_city` VALUES ('101260201', '遵义', '贵州');
INSERT INTO `province_and_city` VALUES ('101260301', '安顺', '贵州');
INSERT INTO `province_and_city` VALUES ('101260401', '都匀', '贵州');
INSERT INTO `province_and_city` VALUES ('101260501', '凯里', '贵州');
INSERT INTO `province_and_city` VALUES ('101260601', '铜仁', '贵州');
INSERT INTO `province_and_city` VALUES ('101260701', '毕节', '贵州');
INSERT INTO `province_and_city` VALUES ('101260801', '六盘水', '贵州');
INSERT INTO `province_and_city` VALUES ('101260906', '兴义', '贵州');
INSERT INTO `province_and_city` VALUES ('101270101', '成都', '四川');
INSERT INTO `province_and_city` VALUES ('101270201', '攀枝花', '四川');
INSERT INTO `province_and_city` VALUES ('101270301', '自贡', '四川');
INSERT INTO `province_and_city` VALUES ('101270401', '绵阳', '四川');
INSERT INTO `province_and_city` VALUES ('101270501', '南充', '四川');
INSERT INTO `province_and_city` VALUES ('101270601', '达州', '四川');
INSERT INTO `province_and_city` VALUES ('101270701', '遂宁', '四川');
INSERT INTO `province_and_city` VALUES ('101270801', '广安', '四川');
INSERT INTO `province_and_city` VALUES ('101270901', '巴中', '四川');
INSERT INTO `province_and_city` VALUES ('101271001', '泸州', '四川');
INSERT INTO `province_and_city` VALUES ('101271101', '宜宾', '四川');
INSERT INTO `province_and_city` VALUES ('101271201', '内江', '四川');
INSERT INTO `province_and_city` VALUES ('101271301', '资阳', '四川');
INSERT INTO `province_and_city` VALUES ('101271401', '乐山', '四川');
INSERT INTO `province_and_city` VALUES ('101271501', '眉山', '四川');
INSERT INTO `province_and_city` VALUES ('101271601', '凉山', '四川');
INSERT INTO `province_and_city` VALUES ('101271701', '雅安', '四川');
INSERT INTO `province_and_city` VALUES ('101271801', '甘孜', '四川');
INSERT INTO `province_and_city` VALUES ('101271901', '阿坝', '四川');
INSERT INTO `province_and_city` VALUES ('101272001', '德阳', '四川');
INSERT INTO `province_and_city` VALUES ('101272101', '广元', '四川');
INSERT INTO `province_and_city` VALUES ('101280101', '广州', '广东');
INSERT INTO `province_and_city` VALUES ('101280201', '韶关', '广东');
INSERT INTO `province_and_city` VALUES ('101280301', '惠州', '广东');
INSERT INTO `province_and_city` VALUES ('101280401', '梅州', '广东');
INSERT INTO `province_and_city` VALUES ('101280501', '汕头', '广东');
INSERT INTO `province_and_city` VALUES ('101280601', '深圳', '广东');
INSERT INTO `province_and_city` VALUES ('101280701', '珠海', '广东');
INSERT INTO `province_and_city` VALUES ('101280800', '佛山', '广东');
INSERT INTO `province_and_city` VALUES ('101280901', '肇庆', '广东');
INSERT INTO `province_and_city` VALUES ('101281001', '湛江', '广东');
INSERT INTO `province_and_city` VALUES ('101281101', '江门', '广东');
INSERT INTO `province_and_city` VALUES ('101281201', '河源', '广东');
INSERT INTO `province_and_city` VALUES ('101281301', '清远', '广东');
INSERT INTO `province_and_city` VALUES ('101281401', '云浮', '广东');
INSERT INTO `province_and_city` VALUES ('101281501', '潮州', '广东');
INSERT INTO `province_and_city` VALUES ('101281601', '东莞', '广东');
INSERT INTO `province_and_city` VALUES ('101281701', '中山', '广东');
INSERT INTO `province_and_city` VALUES ('101281801', '阳江', '广东');
INSERT INTO `province_and_city` VALUES ('101281901', '揭阳', '广东');
INSERT INTO `province_and_city` VALUES ('101282001', '茂名', '广东');
INSERT INTO `province_and_city` VALUES ('101282101', '汕尾', '广东');
INSERT INTO `province_and_city` VALUES ('101290101', '昆明', '云南');
INSERT INTO `province_and_city` VALUES ('101290201', '大理', '云南');
INSERT INTO `province_and_city` VALUES ('101290301', '红河', '云南');
INSERT INTO `province_and_city` VALUES ('101290401', '曲靖', '云南');
INSERT INTO `province_and_city` VALUES ('101290501', '保山', '云南');
INSERT INTO `province_and_city` VALUES ('101290601', '文山', '云南');
INSERT INTO `province_and_city` VALUES ('101290701', '玉溪', '云南');
INSERT INTO `province_and_city` VALUES ('101290801', '楚雄', '云南');
INSERT INTO `province_and_city` VALUES ('101290901', '普洱', '云南');
INSERT INTO `province_and_city` VALUES ('101291001', '昭通', '云南');
INSERT INTO `province_and_city` VALUES ('101291101', '临沧', '云南');
INSERT INTO `province_and_city` VALUES ('101291201', '怒江', '云南');
INSERT INTO `province_and_city` VALUES ('101291301', '香格里拉', '云南');
INSERT INTO `province_and_city` VALUES ('101291401', '丽江', '云南');
INSERT INTO `province_and_city` VALUES ('101291501', '德宏', '云南');
INSERT INTO `province_and_city` VALUES ('101291601', '景洪', '云南');
INSERT INTO `province_and_city` VALUES ('101300101', '南宁', '广西');
INSERT INTO `province_and_city` VALUES ('101300201', '崇左', '广西');
INSERT INTO `province_and_city` VALUES ('101300301', '柳州', '广西');
INSERT INTO `province_and_city` VALUES ('101300401', '来宾', '广西');
INSERT INTO `province_and_city` VALUES ('101300501', '桂林', '广西');
INSERT INTO `province_and_city` VALUES ('101300601', '梧州', '广西');
INSERT INTO `province_and_city` VALUES ('101300701', '贺州', '广西');
INSERT INTO `province_and_city` VALUES ('101300801', '贵港', '广西');
INSERT INTO `province_and_city` VALUES ('101300901', '玉林', '广西');
INSERT INTO `province_and_city` VALUES ('101301001', '百色', '广西');
INSERT INTO `province_and_city` VALUES ('101301101', '钦州', '广西');
INSERT INTO `province_and_city` VALUES ('101301201', '河池', '广西');
INSERT INTO `province_and_city` VALUES ('101301301', '北海', '广西');
INSERT INTO `province_and_city` VALUES ('101301401', '防城港', '广西');
INSERT INTO `province_and_city` VALUES ('101310101', '海口', '海南');
INSERT INTO `province_and_city` VALUES ('101310102', '琼山', '海南');
INSERT INTO `province_and_city` VALUES ('101310201', '三亚', '海南');
INSERT INTO `province_and_city` VALUES ('101310202', '东方', '海南');
INSERT INTO `province_and_city` VALUES ('101310203', '临高', '海南');
INSERT INTO `province_and_city` VALUES ('101310204', '澄迈', '海南');
INSERT INTO `province_and_city` VALUES ('101310205', '儋州', '海南');
INSERT INTO `province_and_city` VALUES ('101310206', '昌江', '海南');
INSERT INTO `province_and_city` VALUES ('101310207', '白沙', '海南');
INSERT INTO `province_and_city` VALUES ('101310208', '琼中', '海南');
INSERT INTO `province_and_city` VALUES ('101310209', '定安', '海南');
INSERT INTO `province_and_city` VALUES ('101310210', '屯昌', '海南');
INSERT INTO `province_and_city` VALUES ('101310211', '琼海', '海南');
INSERT INTO `province_and_city` VALUES ('101310212', '文昌', '海南');
INSERT INTO `province_and_city` VALUES ('101310214', '保亭', '海南');
INSERT INTO `province_and_city` VALUES ('101310215', '万宁', '海南');
INSERT INTO `province_and_city` VALUES ('101310216', '陵水', '海南');
INSERT INTO `province_and_city` VALUES ('101310217', '西沙', '海南');
INSERT INTO `province_and_city` VALUES ('101310220', '南沙岛', '海南');
INSERT INTO `province_and_city` VALUES ('101310221', '乐东', '海南');
INSERT INTO `province_and_city` VALUES ('101310222', '五指山', '海南');
INSERT INTO `province_and_city` VALUES ('101340101', '台北县', '台湾');
INSERT INTO `province_and_city` VALUES ('101340201', '高雄', '台湾');
INSERT INTO `province_and_city` VALUES ('101340401', '台中', '台湾');

-- ----------------------------
-- Table structure for temp_company_and_company_admin
-- ----------------------------
DROP TABLE IF EXISTS `temp_company_and_company_admin`;
CREATE TABLE `temp_company_and_company_admin` (
  `companyName` varchar(50) NOT NULL,
  `username` varchar(80) NOT NULL,
  `password` varchar(15) NOT NULL,
  `location` char(9) NOT NULL,
  `type` char(6) NOT NULL,
  PRIMARY KEY (`username`),
  KEY `FK_temp_company_industry_industryCode` (`type`),
  KEY `FK_temp_company_province_cityCode` (`location`),
  CONSTRAINT `FK_temp_company_industry_industryCode` FOREIGN KEY (`type`) REFERENCES `industry` (`industryCode`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `FK_temp_company_province_cityCode` FOREIGN KEY (`location`) REFERENCES `province_and_city` (`cityCode`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of temp_company_and_company_admin
-- ----------------------------
