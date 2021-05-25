/*
Navicat MySQL Data Transfer

Source Server         : ssm
Source Server Version : 50717
Source Host           : 112.74.160.211:3306
Source Database       : mms

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2019-03-11 11:07:48
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for manager
-- ----------------------------
DROP TABLE IF EXISTS `manager`;
CREATE TABLE `manager` (
  `Manager_Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `Manager_Account` varchar(100) NOT NULL,
  `Manager_Name` varchar(100) NOT NULL,
  `Manager_Password` varchar(10000) NOT NULL,
  `Salt` varchar(100) NOT NULL,
  `Manager_Tel` varchar(20) DEFAULT NULL,
  `Popedom_Id` int(100) NOT NULL,
  PRIMARY KEY (`Manager_Id`,`Manager_Account`),
  KEY `Popedom_Id` (`Popedom_Id`),
  KEY `Manager_Id` (`Manager_Id`),
  CONSTRAINT `Popedom_Ids` FOREIGN KEY (`Popedom_Id`) REFERENCES `popedom` (`Popedom_Id`)
) ENGINE=InnoDB AUTO_INCREMENT=1028 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of manager
-- ----------------------------
INSERT INTO `manager` VALUES ('1000', 'admin', '超级管理员', 'admin123', 'heheda', '123123', '1');
INSERT INTO `manager` VALUES ('1001', 'admin1', 'admin', 'dd', 'heheda', '18888888888', '1');
INSERT INTO `manager` VALUES ('1002', 'Alice', 'Alice', 'asdasd', 'heheda', '13599999971', '2');
INSERT INTO `manager` VALUES ('1003', 'Bunny啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊阿啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊2啊啊啊', 'Bunny', 'Bunny', 'heheda', '', '1');
INSERT INTO `manager` VALUES ('1004', 'Belle', 'Belle', 'Belle', 'heheda', '123', '1');
INSERT INTO `manager` VALUES ('1005', '123', '艾米', '123', '123', null, '2');
INSERT INTO `manager` VALUES ('1006', '1234', '爱丽丝', '1234', '1', null, '2');
INSERT INTO `manager` VALUES ('1007', '12345', '艾莉森', '12345', '2', null, '2');
INSERT INTO `manager` VALUES ('1008', '123456', 'Amanda', '123456', '2', '14785236978', '2');
INSERT INTO `manager` VALUES ('1009', 'Amy', 'Amy', 'aa', 'heheda', null, '2');
INSERT INTO `manager` VALUES ('1010', 'Carmen', 'Carmen', 'Carmen', '3', null, '2');
INSERT INTO `manager` VALUES ('1011', '1234567', '凯莉', '1234567', '3', null, '3');
INSERT INTO `manager` VALUES ('1012', 'Clement', '克莱门特', 'Clement', 'Clement', null, '2');
INSERT INTO `manager` VALUES ('1013', 'Deborah', 'Deborah', 'Deborah', 'Deborah', null, '2');
INSERT INTO `manager` VALUES ('1014', 'Doris', 'Doris', 'Doris', 'Doris', null, '2');
INSERT INTO `manager` VALUES ('1015', 'Frieda', 'Frieda', 'Frieda', 'Frieda', null, '2');
INSERT INTO `manager` VALUES ('1016', 'Iris', '艾丽丝', 'Iris', '2', null, '2');
INSERT INTO `manager` VALUES ('1017', 'Jamie', 'Jamie', 'Jamie', 'Jamie', null, '2');
INSERT INTO `manager` VALUES ('1018', 'Joy', 'Joy', '123', 'heheda', null, '2');
INSERT INTO `manager` VALUES ('1019', 'Arthur', 'Arthur', 'Arthur', 'Arthur', null, '2');
INSERT INTO `manager` VALUES ('1024', 'dxh', 'dxh', 'aa', 'heheda', '123123', '1');
INSERT INTO `manager` VALUES ('1026', '123', 'aaaa', 'aaa', 'heheda', '123123', '2');
INSERT INTO `manager` VALUES ('1027', '哈哈哈哈', 'admin12345', 'admin12345', 'heheda', '18897454532', '2');

-- ----------------------------
-- Table structure for manager_permission
-- ----------------------------
DROP TABLE IF EXISTS `manager_permission`;
CREATE TABLE `manager_permission` (
  `Permission_Id` int(11) NOT NULL,
  `Permission_Name` varchar(100) DEFAULT NULL,
  `Permission_Type` varchar(50) DEFAULT NULL,
  `Permisson_url` varchar(200) DEFAULT NULL,
  `Parent_Id` int(11) DEFAULT NULL,
  `Parent_Ids` varchar(100) DEFAULT NULL,
  `Permission` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`Permission_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of manager_permission
-- ----------------------------
INSERT INTO `manager_permission` VALUES ('1', '资源', 'menu', null, '0', '0/', null);
INSERT INTO `manager_permission` VALUES ('2', '药品', null, null, null, null, null);
INSERT INTO `manager_permission` VALUES ('11', '用户管理', 'menu', '/UserManage/user.html', '1', '0/1/', '*.manager');
INSERT INTO `manager_permission` VALUES ('12', '用户添加', 'button', '/add.manager', '11', '0/1/11', 'add.manager');
INSERT INTO `manager_permission` VALUES ('13', '用户修改', 'button', '/edit.manager', '11', '0/1/11', 'edit.manager');
INSERT INTO `manager_permission` VALUES ('14', '用户删除', 'button', '/remove.manager', '11', '0/1/11', 'remove.manager');
INSERT INTO `manager_permission` VALUES ('15', '用户查询', 'button', '/list.manager', '11', '0/1/11', 'list.manager');
INSERT INTO `manager_permission` VALUES ('21', '药品管理', 'menu', 'DrugManage/drug.html', '1', '0/1/', '*.drug');
INSERT INTO `manager_permission` VALUES ('22', '药品添加', 'button', null, null, null, null);

-- ----------------------------
-- Table structure for medicine
-- ----------------------------
DROP TABLE IF EXISTS `medicine`;
CREATE TABLE `medicine` (
  `Medicine_Id` int(11) NOT NULL AUTO_INCREMENT,
  `Medicine_GB` varchar(15) NOT NULL,
  `Medicine_Name` varchar(100) NOT NULL,
  `Medicine_Price` double(16,2) NOT NULL,
  `Medicine_Firm` varchar(50) NOT NULL,
  `Medicine_Type` int(11) NOT NULL,
  PRIMARY KEY (`Medicine_Id`),
  KEY `Medicine_Type` (`Medicine_Type`),
  CONSTRAINT `Medicine_Type` FOREIGN KEY (`Medicine_Type`) REFERENCES `medicine_type` (`Medicine_Type`)
) ENGINE=InnoDB AUTO_INCREMENT=1029 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of medicine
-- ----------------------------
INSERT INTO `medicine` VALUES ('1000', 'H20054276', 'Transfer Factor Oral Solution', '29.90', '大连百利天华制药有限公司', '1014');
INSERT INTO `medicine` VALUES ('1001', 'Z51021834', '康复新液', '41.50', '四川好医生攀西药业有限责任公司', '1017');
INSERT INTO `medicine` VALUES ('1002', 'H20120425', '三乙醇胺乳膏', '210.00', 'JANSSEN CILAG', '1012');
INSERT INTO `medicine` VALUES ('1003', 'Z10920053', '双黄连口服液', '16.00', '哈药集团三精制药股份有限公司', '1000');
INSERT INTO `medicine` VALUES ('1004', 'Z65020142', '罗补甫克比日丸', '20.00', '和田维吾尔药业有限责任公司', '1006');
INSERT INTO `medicine` VALUES ('1005', 'H20020190', '米诺地尔搽剂', '128.00', '山西振东安特生物制药有限公司', '1006');
INSERT INTO `medicine` VALUES ('1006', 'H31022491', '复方苦参水杨酸散', '9.90', '上海运佳黄浦制药有限公司', '1008');
INSERT INTO `medicine` VALUES ('1007', 'Z10980140', '冰黄肤乐软膏', '15.60', '西藏芝芝药业有限公司', '1008');
INSERT INTO `medicine` VALUES ('1008', 'H19990155', '布洛芬乳膏', '15.80', '中美天津史克制药有限公司', '1012');
INSERT INTO `medicine` VALUES ('1009', 'H20060189', '双氯芬酸钾胶囊', '7.60', '丽珠集团丽珠制药厂', '1012');
INSERT INTO `medicine` VALUES ('1010', 'H20020190', '达霏欣', '88.50', '山西振东安特生物制药有限公司', '1004');
INSERT INTO `medicine` VALUES ('1011', 'H10950026', '善存', '47.00', '惠氏制药有限公司', '1004');
INSERT INTO `medicine` VALUES ('1012', 'H34020909', 'Furosemide Tablets', '5.49', '山西云鹏制药有限公司', '1007');
INSERT INTO `medicine` VALUES ('1013', 'H20044694', '格列齐特缓释片', '36.66', '施维雅(天津)制药有限公司', '1007');
INSERT INTO `medicine` VALUES ('1014', 'H20059107', 'Roxithromycin Capsules', '2.00', '哈药集团三精制药诺捷有限责任公司', '1009');
INSERT INTO `medicine` VALUES ('1015', 'H20040317', '氨溴特罗口服溶液', '26.48', '北京韩美药品有限公司', '1009');
INSERT INTO `medicine` VALUES ('1016', 'Z54020113', '奇正', '18.00', '西藏奇正藏药股份有限公司', '1010');
INSERT INTO `medicine` VALUES ('1017', 'Z13020898', '颈复康', '20.00', '颈复康药业集团有限公司', '1010');
INSERT INTO `medicine` VALUES ('1018', 'H10950224', '络活喜', '39.90', '辉瑞制药有限公司', '1011');
INSERT INTO `medicine` VALUES ('1019', 'H10980322', '安达', '10.52', '扬州一洋制药有限公司', '1003');
INSERT INTO `medicine` VALUES ('1020', 'H20013360', '金花', '9.80', '金花企业', '1014');
INSERT INTO `medicine` VALUES ('1026', 'H20083420 ', '阿莫西林', '8.00', '海口市制药厂有限公司', '1001');
INSERT INTO `medicine` VALUES ('1027', 'ZC20160006', '京都念慈菴 蜜炼川贝枇杷膏 300ml', '35.00', '京都念慈菴总厂有限公司', '1002');
INSERT INTO `medicine` VALUES ('1028', 'J20150044', '呵呵哒呵呵哒aaaaaaaaaaaaaaaa', '128.00', '阿斯利康制药有限公司', '1005');

-- ----------------------------
-- Table structure for medicine_log
-- ----------------------------
DROP TABLE IF EXISTS `medicine_log`;
CREATE TABLE `medicine_log` (
  `Medicine_Log_Id` int(11) NOT NULL AUTO_INCREMENT,
  `Sale_Date` varchar(50) NOT NULL,
  `Medicine_Id` int(11) NOT NULL,
  `Sale_Number` int(11) NOT NULL,
  `Sale_Price` double(16,2) NOT NULL,
  `User_Id` int(11) NOT NULL,
  PRIMARY KEY (`Medicine_Log_Id`),
  KEY `Medicine_Id` (`Medicine_Id`),
  KEY `User_Id` (`User_Id`),
  CONSTRAINT `Manager_Id` FOREIGN KEY (`User_Id`) REFERENCES `manager` (`Manager_Id`),
  CONSTRAINT `Medicine_Id` FOREIGN KEY (`Medicine_Id`) REFERENCES `medicine` (`Medicine_Id`)
) ENGINE=InnoDB AUTO_INCREMENT=1077 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of medicine_log
-- ----------------------------
INSERT INTO `medicine_log` VALUES ('1000', '1530189909101', '1000', '100', '50.00', '1000');
INSERT INTO `medicine_log` VALUES ('1001', '1530409281513', '1001', '120', '41.50', '1000');
INSERT INTO `medicine_log` VALUES ('1002', '1530322881513', '1002', '500', '210.00', '1002');
INSERT INTO `medicine_log` VALUES ('1003', '1530189909101', '1003', '500', '16.00', '1003');
INSERT INTO `medicine_log` VALUES ('1004', '1530322881513', '1004', '120', '20.00', '1004');
INSERT INTO `medicine_log` VALUES ('1005', '1530189909101', '1005', '120', '128.00', '1005');
INSERT INTO `medicine_log` VALUES ('1006', '1530189909101', '1006', '120', '9.90', '1006');
INSERT INTO `medicine_log` VALUES ('1007', '1530322881513', '1007', '120', '15.60', '1007');
INSERT INTO `medicine_log` VALUES ('1008', '1530409281513', '1008', '120', '15.80', '1008');
INSERT INTO `medicine_log` VALUES ('1009', '1530189909101', '1009', '120', '7.60', '1009');
INSERT INTO `medicine_log` VALUES ('1010', '1529977281513', '1010', '50', '88.50', '1010');
INSERT INTO `medicine_log` VALUES ('1011', '1530189909101', '1011', '120', '47.00', '1011');
INSERT INTO `medicine_log` VALUES ('1012', '1529977281513', '1012', '120', '5.49', '1012');
INSERT INTO `medicine_log` VALUES ('1013', '1530189909101', '1013', '20', '36.66', '1013');
INSERT INTO `medicine_log` VALUES ('1014', '1530409281513', '1014', '70', '2.00', '1014');
INSERT INTO `medicine_log` VALUES ('1015', '1530189909101', '1000', '120', '50.00', '1000');
INSERT INTO `medicine_log` VALUES ('1016', '1530495681513', '1003', '100', '16.00', '1000');
INSERT INTO `medicine_log` VALUES ('1017', '1530409281513', '1003', '80', '16.00', '1000');
INSERT INTO `medicine_log` VALUES ('1018', '1530322881513', '1003', '20', '16.00', '1000');
INSERT INTO `medicine_log` VALUES ('1019', '1530236481513', '1003', '100', '16.00', '1000');
INSERT INTO `medicine_log` VALUES ('1020', '1530150081513', '1003', '60', '16.00', '1000');
INSERT INTO `medicine_log` VALUES ('1021', '1530063681513', '1003', '140', '16.00', '1000');
INSERT INTO `medicine_log` VALUES ('1022', '1529977281513', '1003', '150', '16.00', '1000');
INSERT INTO `medicine_log` VALUES ('1023', '1530495681513', '1026', '100', '8.00', '1000');
INSERT INTO `medicine_log` VALUES ('1024', '1530409281513', '1026', '60', '8.00', '1000');
INSERT INTO `medicine_log` VALUES ('1025', '1530322881513', '1026', '70', '8.00', '1000');
INSERT INTO `medicine_log` VALUES ('1026', '1530236481513', '1026', '80', '8.00', '1000');
INSERT INTO `medicine_log` VALUES ('1027', '1530150081513', '1026', '40', '8.00', '1000');
INSERT INTO `medicine_log` VALUES ('1028', '1530063681513', '1026', '80', '8.00', '1000');
INSERT INTO `medicine_log` VALUES ('1029', '1529977281513', '1026', '120', '8.00', '1000');
INSERT INTO `medicine_log` VALUES ('1030', '1530495681513', '1026', '150', '28.00', '1000');
INSERT INTO `medicine_log` VALUES ('1031', '1530409281513', '1026', '160', '28.00', '1000');
INSERT INTO `medicine_log` VALUES ('1032', '1530322881513', '1026', '182', '28.00', '1000');
INSERT INTO `medicine_log` VALUES ('1033', '1530236481513', '1026', '12', '28.00', '1000');
INSERT INTO `medicine_log` VALUES ('1034', '1530150081513', '1026', '124', '28.00', '1000');
INSERT INTO `medicine_log` VALUES ('1035', '1530063681513', '1026', '120', '28.00', '1000');
INSERT INTO `medicine_log` VALUES ('1036', '1529977281513', '1026', '42', '28.00', '1000');
INSERT INTO `medicine_log` VALUES ('1037', '1530495681513', '1019', '85', '10.52', '1000');
INSERT INTO `medicine_log` VALUES ('1038', '1530409281513', '1019', '99', '10.52', '1000');
INSERT INTO `medicine_log` VALUES ('1039', '1530322881513', '1019', '56', '10.52', '1000');
INSERT INTO `medicine_log` VALUES ('1040', '1530236481513', '1019', '78', '10.52', '1000');
INSERT INTO `medicine_log` VALUES ('1041', '1530150081513', '1019', '46', '10.52', '1000');
INSERT INTO `medicine_log` VALUES ('1042', '1530063681513', '1019', '89', '10.52', '1000');
INSERT INTO `medicine_log` VALUES ('1043', '1529977281513', '1019', '21', '10.52', '1000');
INSERT INTO `medicine_log` VALUES ('1044', '1530495681513', '1011', '16', '47.00', '1000');
INSERT INTO `medicine_log` VALUES ('1045', '1530409281513', '1011', '12', '47.00', '1000');
INSERT INTO `medicine_log` VALUES ('1046', '1530322881513', '1011', '78', '47.00', '1000');
INSERT INTO `medicine_log` VALUES ('1047', '1530236481513', '1011', '123', '47.00', '1000');
INSERT INTO `medicine_log` VALUES ('1048', '1530150081513', '1011', '82', '47.00', '1000');
INSERT INTO `medicine_log` VALUES ('1049', '1530063681513', '1011', '85', '47.00', '1000');
INSERT INTO `medicine_log` VALUES ('1050', '1529977281513', '1011', '73', '47.00', '1000');
INSERT INTO `medicine_log` VALUES ('1066', '1530663169858', '1026', '3000', '8.00', '1024');
INSERT INTO `medicine_log` VALUES ('1067', '1530672081308', '1020', '50000', '9.80', '1001');
INSERT INTO `medicine_log` VALUES ('1068', '1530688989643', '1028', '233', '128.00', '1000');
INSERT INTO `medicine_log` VALUES ('1069', '1530749545662', '1026', '2', '8.00', '1000');
INSERT INTO `medicine_log` VALUES ('1070', '1530751702949', '1028', '2', '128.00', '1009');
INSERT INTO `medicine_log` VALUES ('1071', '1530752360641', '1028', '6', '128.00', '1009');
INSERT INTO `medicine_log` VALUES ('1072', '1530753717117', '1019', '1', '10.52', '1024');
INSERT INTO `medicine_log` VALUES ('1073', '1530760470480', '1028', '0', '128.00', '1000');
INSERT INTO `medicine_log` VALUES ('1074', '1530760520120', '1028', '0', '128.00', '1024');
INSERT INTO `medicine_log` VALUES ('1075', '1530760901514', '1028', '1', '128.00', '1009');
INSERT INTO `medicine_log` VALUES ('1076', '1530762231087', '1027', '20', '35.00', '1009');

-- ----------------------------
-- Table structure for medicine_stock
-- ----------------------------
DROP TABLE IF EXISTS `medicine_stock`;
CREATE TABLE `medicine_stock` (
  `Medicine_Id` int(11) NOT NULL,
  `Medicine_Stock_Number` int(11) NOT NULL,
  `Medicine_PD` varchar(50) NOT NULL,
  PRIMARY KEY (`Medicine_Id`,`Medicine_PD`),
  CONSTRAINT `M_Id` FOREIGN KEY (`Medicine_Id`) REFERENCES `medicine` (`Medicine_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of medicine_stock
-- ----------------------------
INSERT INTO `medicine_stock` VALUES ('1000', '1000', '1530189909101');
INSERT INTO `medicine_stock` VALUES ('1001', '1000', '1530189909101');
INSERT INTO `medicine_stock` VALUES ('1002', '1000', '1530189909101');
INSERT INTO `medicine_stock` VALUES ('1003', '1000', '1530189909101');
INSERT INTO `medicine_stock` VALUES ('1004', '1000', '1530189909101');
INSERT INTO `medicine_stock` VALUES ('1005', '1000', '1530189909101');
INSERT INTO `medicine_stock` VALUES ('1006', '1000', '1530189909101');
INSERT INTO `medicine_stock` VALUES ('1007', '1000', '1530189909101');
INSERT INTO `medicine_stock` VALUES ('1008', '1000', '1530189909101');
INSERT INTO `medicine_stock` VALUES ('1009', '1000', '1530189909101');
INSERT INTO `medicine_stock` VALUES ('1010', '1000', '1530189909101');
INSERT INTO `medicine_stock` VALUES ('1011', '1000', '1530189909101');
INSERT INTO `medicine_stock` VALUES ('1012', '1000', '1530189909101');
INSERT INTO `medicine_stock` VALUES ('1013', '1000', '1530189909101');
INSERT INTO `medicine_stock` VALUES ('1014', '1000', '1530189909101');
INSERT INTO `medicine_stock` VALUES ('1015', '1000', '1530189909101');
INSERT INTO `medicine_stock` VALUES ('1016', '1000', '1530189909101');
INSERT INTO `medicine_stock` VALUES ('1017', '1000', '1530189909101');
INSERT INTO `medicine_stock` VALUES ('1018', '1000', '1530189909101');
INSERT INTO `medicine_stock` VALUES ('1019', '999', '1530115200000');
INSERT INTO `medicine_stock` VALUES ('1020', '50000', '1530115200000');
INSERT INTO `medicine_stock` VALUES ('1026', '495', '1530189909101');
INSERT INTO `medicine_stock` VALUES ('1027', '480', '1530189909101');
INSERT INTO `medicine_stock` VALUES ('1028', '258', '1530115200000');

-- ----------------------------
-- Table structure for medicine_type
-- ----------------------------
DROP TABLE IF EXISTS `medicine_type`;
CREATE TABLE `medicine_type` (
  `Medicine_Type` int(11) NOT NULL AUTO_INCREMENT,
  `Medicine_Type_Name` varchar(50) NOT NULL,
  `Medicine_Type_Date` varchar(50) NOT NULL,
  `Medicine_Type_De` varchar(200) NOT NULL,
  PRIMARY KEY (`Medicine_Type`,`Medicine_Type_Name`),
  KEY `Medicine_Type` (`Medicine_Type`)
) ENGINE=InnoDB AUTO_INCREMENT=1019 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of medicine_type
-- ----------------------------
INSERT INTO `medicine_type` VALUES ('1000', '清热解毒类', '1530189909101', '可以清热解毒');
INSERT INTO `medicine_type` VALUES ('1001', '抗菌消炎类', '1530189909101', '可以抗菌消炎');
INSERT INTO `medicine_type` VALUES ('1002', '止咳平喘类', '1530189909101', '可以止咳平喘');
INSERT INTO `medicine_type` VALUES ('1003', '胃肠道类', '1530189909101', '专治胃肠道疾病');
INSERT INTO `medicine_type` VALUES ('1004', '维生素类', '1530189909101', '可以补充维生素');
INSERT INTO `medicine_type` VALUES ('1005', '心脑血管类', '1530189909101', '保护心脑血管');
INSERT INTO `medicine_type` VALUES ('1006', '五官外用类', '1530189909101', '治疗五官科疾病');
INSERT INTO `medicine_type` VALUES ('1007', '抗糖尿病类', '1530189909101', '治疗糖尿病');
INSERT INTO `medicine_type` VALUES ('1008', '皮肤用药', '1530189909101', '治疗皮肤疾病');
INSERT INTO `medicine_type` VALUES ('1009', '呼吸系统类', '1530189909101', '治疗呼吸系统类');
INSERT INTO `medicine_type` VALUES ('1010', '风湿免疫科', '1530189909101', '风湿免疫');
INSERT INTO `medicine_type` VALUES ('1011', '心脑血管', '1530189909101', '治疗心脑血管');
INSERT INTO `medicine_type` VALUES ('1012', '家庭常备', '1530189909101', '家庭常备药品');
INSERT INTO `medicine_type` VALUES ('1013', '肿瘤科', '1530189909101', '肿瘤用药');
INSERT INTO `medicine_type` VALUES ('1014', '止泻类', '1530189909101', '止泻用药');
INSERT INTO `medicine_type` VALUES ('1015', '助消化', '1530189909101', '助消化用药');
INSERT INTO `medicine_type` VALUES ('1016', '营养类', '1530189909101', '营养药');
INSERT INTO `medicine_type` VALUES ('1017', '溃疡类', '1530189909101', '治溃疡');

-- ----------------------------
-- Table structure for popedom
-- ----------------------------
DROP TABLE IF EXISTS `popedom`;
CREATE TABLE `popedom` (
  `Popedom_Id` int(11) NOT NULL,
  `Popedom_Name` varchar(100) NOT NULL,
  `Remark` varchar(1000) DEFAULT NULL,
  `Permission_Id` int(11) DEFAULT NULL,
  PRIMARY KEY (`Popedom_Id`),
  KEY `Permission_Id` (`Permission_Id`),
  CONSTRAINT `Permission_Id` FOREIGN KEY (`Permission_Id`) REFERENCES `manager_permission` (`Permission_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of popedom
-- ----------------------------
INSERT INTO `popedom` VALUES ('1', 'admin', '超级管理员', '11');
INSERT INTO `popedom` VALUES ('2', 'common', '普通用户', null);
INSERT INTO `popedom` VALUES ('3', 'common', '普通用户', null);
