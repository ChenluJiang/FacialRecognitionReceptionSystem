/*
 Navicat Premium Data Transfer

 Source Server         : Javagroup
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : localhost:3306
 Source Schema         : student_basic

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 16/11/2019 19:34:24
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for alert
-- ----------------------------
DROP TABLE IF EXISTS `alert`;
CREATE TABLE `alert` (
  `id` int(5) NOT NULL,
  `introduction` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of alert
-- ----------------------------
BEGIN;
INSERT INTO `alert` VALUES (1, 'Tuition is due');
INSERT INTO `alert` VALUES (2, 'Please return your stuff in time');
INSERT INTO `alert` VALUES (3, 'Java Assignment is due');
INSERT INTO `alert` VALUES (4, 'Telecom Assignment is due');
INSERT INTO `alert` VALUES (5, 'ODI Assignment is due');
COMMIT;

-- ----------------------------
-- Table structure for announcement
-- ----------------------------
DROP TABLE IF EXISTS `announcement`;
CREATE TABLE `announcement` (
  `id` int(5) NOT NULL,
  `announce` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of announcement
-- ----------------------------
BEGIN;
INSERT INTO `announcement` VALUES (1, 'Todays\' TA session has been adjusted to 2pm - 4pm');
INSERT INTO `announcement` VALUES (2, 'Please check the following table for you allocated seats in  Classroom 2 & Classroom 5. ');
INSERT INTO `announcement` VALUES (3, 'Midterm Exam Review & JAVA DB Update : Wednesday 7 - 9 pm');
INSERT INTO `announcement` VALUES (4, 'Please come to my TA  session between 6:45pm to 8:45pm in Project Room 4');
COMMIT;

-- ----------------------------
-- Table structure for appointment
-- ----------------------------
DROP TABLE IF EXISTS `appointment`;
CREATE TABLE `appointment` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `appointtime` date DEFAULT NULL,
  `stu_id` int(11) DEFAULT NULL,
  `teacher` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `stu_id2` (`stu_id`),
  CONSTRAINT `stu_id2` FOREIGN KEY (`stu_id`) REFERENCES `student_basic` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of appointment
-- ----------------------------
BEGIN;
INSERT INTO `appointment` VALUES (1, '2019-12-12', 4, '0');
COMMIT;

-- ----------------------------
-- Table structure for locker
-- ----------------------------
DROP TABLE IF EXISTS `locker`;
CREATE TABLE `locker` (
  `id` int(5) NOT NULL,
  `stu_id` int(5) DEFAULT NULL,
  `status` int(6) DEFAULT NULL COMMENT '0-未分配\n1-已分配',
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `stu_id1` (`stu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of locker
-- ----------------------------
BEGIN;
INSERT INTO `locker` VALUES (1, NULL, 0, '23-47-34');
INSERT INTO `locker` VALUES (2, NULL, 0, '22-46-33');
INSERT INTO `locker` VALUES (3, NULL, 0, '21-45-32');
INSERT INTO `locker` VALUES (4, NULL, 0, '20-44-31');
INSERT INTO `locker` VALUES (5, NULL, 0, '19-43-30');
INSERT INTO `locker` VALUES (6, NULL, 0, '18-42-29');
INSERT INTO `locker` VALUES (7, NULL, 0, '17-41-28');
INSERT INTO `locker` VALUES (8, NULL, 0, '16-40-27');
INSERT INTO `locker` VALUES (9, NULL, 0, '15-39-26');
INSERT INTO `locker` VALUES (10, NULL, 0, '14-38-25');
INSERT INTO `locker` VALUES (11, NULL, 0, '13-37-24');
INSERT INTO `locker` VALUES (12, NULL, 0, '12-36-23');
INSERT INTO `locker` VALUES (13, NULL, 0, '11-35-22');
INSERT INTO `locker` VALUES (14, NULL, 0, '10-34-21');
INSERT INTO `locker` VALUES (15, NULL, 0, '9-33-20');
INSERT INTO `locker` VALUES (16, NULL, 0, '8-32-19');
INSERT INTO `locker` VALUES (17, NULL, 0, '7-31-18');
INSERT INTO `locker` VALUES (18, NULL, 0, '6-30-17');
INSERT INTO `locker` VALUES (19, NULL, 0, '5-29-16');
INSERT INTO `locker` VALUES (20, NULL, 0, '4-28-15');
INSERT INTO `locker` VALUES (21, NULL, 0, '3-27-14');
INSERT INTO `locker` VALUES (22, NULL, 0, '2-26-13');
INSERT INTO `locker` VALUES (23, NULL, 0, '1-25-12');
INSERT INTO `locker` VALUES (24, NULL, 0, '0-24-11');
INSERT INTO `locker` VALUES (25, NULL, 0, '76-23-30');
INSERT INTO `locker` VALUES (26, NULL, 0, '75-22-29');
INSERT INTO `locker` VALUES (27, NULL, 0, '74-21-28');
INSERT INTO `locker` VALUES (28, NULL, 0, '73-20-27');
INSERT INTO `locker` VALUES (29, NULL, 0, '72-19-26');
INSERT INTO `locker` VALUES (30, NULL, 0, '71-18-25');
INSERT INTO `locker` VALUES (31, NULL, 0, '70-17-24');
INSERT INTO `locker` VALUES (32, NULL, 0, '69-16-23');
INSERT INTO `locker` VALUES (33, NULL, 0, '68-15-22');
INSERT INTO `locker` VALUES (34, NULL, 0, '67-14-21');
INSERT INTO `locker` VALUES (35, NULL, 0, '66-13-20');
INSERT INTO `locker` VALUES (36, NULL, 0, '65-12-19');
INSERT INTO `locker` VALUES (37, NULL, 0, '64-11-18');
INSERT INTO `locker` VALUES (38, NULL, 0, '63-10-17');
INSERT INTO `locker` VALUES (39, NULL, 0, '62-9-16');
INSERT INTO `locker` VALUES (40, NULL, 0, '61-8-15');
INSERT INTO `locker` VALUES (41, NULL, 0, '60-7-14');
INSERT INTO `locker` VALUES (42, NULL, 0, '59-6-13');
INSERT INTO `locker` VALUES (43, NULL, 0, '58-5-12');
INSERT INTO `locker` VALUES (44, NULL, 0, '57-4-11');
INSERT INTO `locker` VALUES (45, NULL, 0, '56-3-10');
INSERT INTO `locker` VALUES (46, NULL, 0, '55-2-9');
INSERT INTO `locker` VALUES (47, NULL, 0, '54-1-8');
INSERT INTO `locker` VALUES (48, NULL, 0, '53-0-7');
INSERT INTO `locker` VALUES (49, NULL, 0, '52-2-6');
COMMIT;

-- ----------------------------
-- Table structure for student_basic
-- ----------------------------
DROP TABLE IF EXISTS `student_basic`;
CREATE TABLE `student_basic` (
  `id` int(5) NOT NULL,
  `name` varchar(255) NOT NULL,
  `gender` varchar(255) NOT NULL COMMENT '0-female\n1-male\n',
  `program` varchar(255) NOT NULL,
  `birthday` date DEFAULT NULL,
  `andrewid` varchar(10) NOT NULL,
  `photo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of student_basic
-- ----------------------------
BEGIN;
INSERT INTO `student_basic` VALUES (1, 'Xuwen Ren', '0', 'MISM', '1997-07-31', 'xuwenr', NULL);
INSERT INTO `student_basic` VALUES (2, 'Jiayuan Zhang', '0', 'MISM', '1997-01-11', 'jiayuanz', NULL);
INSERT INTO `student_basic` VALUES (3, 'Gaoming Tang', '1', 'MISM', '1994-10-18', 'gaomingt', NULL);
INSERT INTO `student_basic` VALUES (4, 'Zimo Yang', '1', 'MISM', '1997-02-01', 'zimoyang', NULL);
INSERT INTO `student_basic` VALUES (5, 'Chenlu Jiang', '0', 'MISM', '1994-10-18', 'chenluj', NULL);
COMMIT;

-- ----------------------------
-- Table structure for visit_record
-- ----------------------------
DROP TABLE IF EXISTS `visit_record`;
CREATE TABLE `visit_record` (
  `id` int(5) NOT NULL AUTO_INCREMENT,
  `stu_id` int(11) DEFAULT NULL,
  `opetime` datetime DEFAULT NULL,
  `operation` int(10) NOT NULL COMMENT '0-Making appointment\n1-Rent locker\n2-Submit assignments\n3-Report lost\n4-Pay tuition\n5-Complaint\n6-Borrow stuff ',
  `opedetails` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `stu_id` (`stu_id`),
  CONSTRAINT `stu_id` FOREIGN KEY (`stu_id`) REFERENCES `student_basic` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of visit_record
-- ----------------------------
BEGIN;
INSERT INTO `visit_record` VALUES (1, 1, '2019-11-04 16:38:27', 1, 'bjkk');
INSERT INTO `visit_record` VALUES (2, 1, '2019-11-03 16:38:42', 2, NULL);
INSERT INTO `visit_record` VALUES (3, 1, '2019-11-01 16:39:00', 2, NULL);
INSERT INTO `visit_record` VALUES (4, 2, '2018-12-01 16:39:00', 4, '$5000');
INSERT INTO `visit_record` VALUES (5, 2, '2019-11-06 18:09:17', 4, '$5000');
INSERT INTO `visit_record` VALUES (6, 2, '2019-11-09 19:57:29', 4, '$5000');
INSERT INTO `visit_record` VALUES (7, 2, '2019-11-04 22:52:26', 2, NULL);
INSERT INTO `visit_record` VALUES (8, 1, '2019-11-13 15:47:02', 1, 'sgd');
INSERT INTO `visit_record` VALUES (9, 1, '2019-11-13 15:47:36', 1, 'sgd');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
