-- ----------------------------
-- Create Database
-- ----------------------------
DROP DATABASE `bear_log` ;
CREATE DATABASE `bear_log`CHARACTER SET utf8mb4 ;
USE `bear_log`;

-- ----------------------------
-- Create Table
-- ----------------------------
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_log
-- ----------------------------
DROP TABLE IF EXISTS `t_log`;
CREATE TABLE `t_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `module` varchar(100) NOT NULL COMMENT '模块名',
  `params` text COMMENT '方法参数',
  `remark` text COMMENT '备注',
  `flag` tinyint(1) NOT NULL COMMENT '是否成功(1成功，0失败)',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `username` (`username`),
  KEY `createTime` (`createTime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='日志表';
