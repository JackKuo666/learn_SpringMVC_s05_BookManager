/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 80028
Source Host           : localhost:3306
Source Database       : study

Target Server Type    : MYSQL
Target Server Version : 80028
File Encoding         : 65001

Date: 2022-08-16 15:46:51
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
  `bid` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `desc` varchar(255) DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`bid`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES ('2', '倚天屠龙记', '描了个述', '10.22');
INSERT INTO `book` VALUES ('5', 'ss', 'ss', '11.00');
INSERT INTO `book` VALUES ('6', 'dd', 'ss', '11.00');
INSERT INTO `book` VALUES ('7', '实施', '水水水水水', '112.00');

-- ----------------------------
-- Table structure for borrow
-- ----------------------------
DROP TABLE IF EXISTS `borrow`;
CREATE TABLE `borrow` (
  `id` int NOT NULL AUTO_INCREMENT,
  `bid` int DEFAULT NULL,
  `sid` int DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of borrow
-- ----------------------------
INSERT INTO `borrow` VALUES ('4', '2', '8', '2022-08-16 14:53:01');
INSERT INTO `borrow` VALUES ('5', '5', '8', '2022-08-16 14:53:05');

-- ----------------------------
-- Table structure for persistent_logins
-- ----------------------------
DROP TABLE IF EXISTS `persistent_logins`;
CREATE TABLE `persistent_logins` (
  `username` varchar(64) NOT NULL,
  `series` varchar(64) NOT NULL,
  `token` varchar(64) NOT NULL,
  `last_used` timestamp NOT NULL,
  PRIMARY KEY (`series`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of persistent_logins
-- ----------------------------

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `sid` int NOT NULL AUTO_INCREMENT,
  `uid` int DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `sex` enum('女','男') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '男',
  `grade` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('8', '9', '张三', '男', '2022');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `password` char(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('1', 'test', '$2a$10$37g6hYb9dqtNw89WjweVfuICuMJ.cQAWScxspCcao/UmGkQKS6sqe', 'user');
INSERT INTO `users` VALUES ('2', 'admin', '$2a$10$37g6hYb9dqtNw89WjweVfuICuMJ.cQAWScxspCcao/UmGkQKS6sqe', 'admin');
INSERT INTO `users` VALUES ('9', '张三', '$2a$10$QokyaWFjRpRXC9w2Sqrbl.dM1uIasQGYQtNE5zNMkZSKmSieW6VaK', 'user');
DROP TRIGGER IF EXISTS `del_book`;
DELIMITER ;;
CREATE TRIGGER `del_book` BEFORE DELETE ON `book` FOR EACH ROW DELETE FROM borrow where bid = old.bid
;;
DELIMITER ;
