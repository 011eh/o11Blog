-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: mysql    Database: service_demo
-- ------------------------------------------------------
-- Server version	8.0.22

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `back_admin`
--

DROP TABLE IF EXISTS `back_admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `back_admin` (
  `id` char(19) NOT NULL COMMENT 'Id',
  `username` varchar(40) NOT NULL COMMENT '用户名',
  `nick_name` varchar(40) DEFAULT NULL COMMENT '昵称',
  `status` tinyint DEFAULT '1',
  `role_id` char(19) DEFAULT NULL COMMENT '角色Id',
  `avatar` varchar(128) DEFAULT NULL COMMENT '头像',
  `password` varchar(128) NOT NULL DEFAULT '123456' COMMENT '密码',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `back_admin_username_uindex` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='管理员';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `back_admin`
--

LOCK TABLES `back_admin` WRITE;
/*!40000 ALTER TABLE `back_admin` DISABLE KEYS */;
INSERT INTO `back_admin` VALUES ('1496756141639294978','admin1','管理员1',1,'1497825987499515915','https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif','26595aac3b1aa02c1f176dd651e3f2a9','2022-02-24 15:57:27','2022-05-06 13:52:23');
INSERT INTO `back_admin` VALUES ('1496756153844719617','admin2','管理员2',1,'1513430650854297601','https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif','b2f78c2327d2bda5136a1408a41e8a39','2022-02-24 15:57:30','2022-05-05 16:29:31');
INSERT INTO `back_admin` VALUES ('1515171134605787138','admin3','列表查询',1,'1515169656457560066','https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif','3be602dab2f0616146d5875250a46736','2022-04-16 11:32:04','2022-05-12 15:34:53');
INSERT INTO `back_admin` VALUES ('1522133730198286338','仅路由','仅路由',1,'1515169528258658306','https://011eh.oss-cn-guangzhou.aliyuncs.com/admin/avatar/e22b989f395244a591ad1edce000a0a9.jpg','a859fb6ce83468bfdf4c446018bc36bc','2022-05-05 16:38:56','2022-05-12 20:32:27');
/*!40000 ALTER TABLE `back_admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `back_permission`
--

DROP TABLE IF EXISTS `back_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `back_permission` (
  `id` char(19) NOT NULL COMMENT 'Id',
  `name` varchar(40) NOT NULL COMMENT '名称',
  `permission_key` varchar(40) DEFAULT NULL COMMENT '权限键值',
  `status` tinyint DEFAULT '1',
  `parent_id` char(19) DEFAULT NULL COMMENT '父级资源Id',
  `sort` tinyint DEFAULT '100' COMMENT '列表排序',
  `resource_type` tinyint DEFAULT NULL COMMENT '资源类型',
  `router_info` json DEFAULT NULL COMMENT 'Vue路由',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `back_permission`
--

LOCK TABLES `back_permission` WRITE;
/*!40000 ALTER TABLE `back_permission` DISABLE KEYS */;
INSERT INTO `back_permission` VALUES ('1497811057370992642','用户管理','admin:router',1,'1497825987499515915',4,1,'{\"meta\": {\"icon\": \"el-icon-user-solid\", \"title\": \"用户管理\", \"noCache\": true, \"breadcrumb\": true}, \"path\": \"user\", \"redirect\": \"\", \"component\": \"user\"}','2022-03-24 15:19:37','2022-04-08 15:18:46');
INSERT INTO `back_permission` VALUES ('1497811074932543489','用户添加','admin:create',1,'1497811057370992642',100,2,NULL,'2022-03-24 15:19:37','2022-04-08 15:28:56');
INSERT INTO `back_permission` VALUES ('1497811107790721025','用户更新','admin:update',1,'1497811057370992642',100,2,NULL,'2022-03-24 15:19:37','2022-03-24 15:19:37');
INSERT INTO `back_permission` VALUES ('1497825987499515906','用户删除','admin:delete',1,'1497811057370992642',100,2,NULL,'2022-03-24 15:19:37','2022-03-24 15:19:37');
INSERT INTO `back_permission` VALUES ('1497825987499515907','角色管理','role:router',1,'1497825987499515915',3,1,'{\"meta\": {\"icon\": \"peoples\", \"title\": \"角色管理\", \"noCache\": true, \"breadcrumb\": true}, \"path\": \"role\", \"component\": \"role\"}','2022-03-24 15:19:38','2022-04-08 15:22:31');
INSERT INTO `back_permission` VALUES ('1497825987499515908','角色添加','role:create',1,'1497825987499515907',100,2,NULL,'2022-03-24 15:19:38','2022-04-08 15:28:47');
INSERT INTO `back_permission` VALUES ('1497825987499515909','角色更新','role:update',1,'1497825987499515907',100,2,NULL,'2022-03-24 15:19:38','2022-03-24 15:19:38');
INSERT INTO `back_permission` VALUES ('1497825987499515910','角色删除','role:delete',1,'1497825987499515907',100,2,NULL,'2022-03-24 15:19:38','2022-03-24 15:19:38');
INSERT INTO `back_permission` VALUES ('1497825987499515911','权限管理','permission:router',1,'1497825987499515915',1,1,'{\"meta\": {\"icon\": \"list\", \"title\": \"权限管理\", \"noCache\": true, \"breadcrumb\": true}, \"path\": \"permission\", \"component\": \"permission\"}','2022-03-24 15:19:38','2022-04-09 17:46:46');
INSERT INTO `back_permission` VALUES ('1497825987499515912','权限添加','permission:create',1,'1497825987499515911',100,2,NULL,'2022-03-24 15:19:38','2022-04-08 15:28:39');
INSERT INTO `back_permission` VALUES ('1497825987499515913','权限更新','permission:update',1,'1497825987499515911',100,2,NULL,'2022-03-24 15:19:38','2022-03-24 15:19:38');
INSERT INTO `back_permission` VALUES ('1497825987499515914','权限删除','permission:delete',1,'1497825987499515911',100,2,NULL,'2022-03-24 15:19:38','2022-03-24 15:19:38');
INSERT INTO `back_permission` VALUES ('1497825987499515915','资源管理','resource:router',1,NULL,1,0,'{\"meta\": {\"icon\": \"lock\", \"title\": \"资源管理\", \"noCache\": true, \"breadcrumb\": true}, \"path\": \"/resource\", \"redirect\": \"/resource/permission\", \"component\": \"layout\"}','2022-03-24 15:19:38','2022-04-15 13:31:35');
INSERT INTO `back_permission` VALUES ('1512328763396460545','权限列表','permission:list',1,'1497825987499515911',1,2,'{}','2022-04-08 15:17:30','2022-04-16 16:11:18');
INSERT INTO `back_permission` VALUES ('1515162854164692993','角色列表','role:list',1,'1497825987499515907',100,2,NULL,'2022-04-16 10:59:09','2022-04-16 10:59:09');
INSERT INTO `back_permission` VALUES ('1515162951845838849','用户列表','admin:list',1,'1497811057370992642',100,2,'{}','2022-04-16 10:59:33','2022-04-16 10:59:53');
/*!40000 ALTER TABLE `back_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `back_role`
--

DROP TABLE IF EXISTS `back_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `back_role` (
  `id` char(19) NOT NULL COMMENT 'Id',
  `name` varchar(255) NOT NULL COMMENT '角色名',
  `summary` varchar(256) DEFAULT NULL COMMENT '简介',
  `status` tinyint DEFAULT '1',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `back_role`
--

LOCK TABLES `back_role` WRITE;
/*!40000 ALTER TABLE `back_role` DISABLE KEYS */;
INSERT INTO `back_role` VALUES ('1497825987499515915','超级管理员','拥有更多权限',1,'2022-04-11 01:31:05','2022-05-05 15:42:24');
INSERT INTO `back_role` VALUES ('1513430650854297601','普通管理员','普通权限',1,'2022-04-11 16:16:00','2022-05-05 14:56:40');
INSERT INTO `back_role` VALUES ('1515169528258658306','仅路由','只有路由菜单权限',1,'2022-04-16 11:25:41','2022-05-05 14:58:21');
INSERT INTO `back_role` VALUES ('1515169656457560066','仅查询','只有列表查询权限',1,'2022-04-16 11:26:11','2022-05-05 14:57:43');
/*!40000 ALTER TABLE `back_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `back_role_permission`
--

DROP TABLE IF EXISTS `back_role_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `back_role_permission` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `role_id` char(19) NOT NULL COMMENT '角色Id',
  `permission_id` varchar(40) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=890 DEFAULT CHARSET=utf8 COMMENT='管理员';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `back_role_permission`
--

LOCK TABLES `back_role_permission` WRITE;
/*!40000 ALTER TABLE `back_role_permission` DISABLE KEYS */;
INSERT INTO `back_role_permission` VALUES (776,'1513430650854297601','1497811057370992642','2022-05-05 06:56:42');
INSERT INTO `back_role_permission` VALUES (777,'1513430650854297601','1497811074932543489','2022-05-05 06:56:42');
INSERT INTO `back_role_permission` VALUES (778,'1513430650854297601','1497825987499515907','2022-05-05 06:56:42');
INSERT INTO `back_role_permission` VALUES (779,'1513430650854297601','1497825987499515911','2022-05-05 06:56:42');
INSERT INTO `back_role_permission` VALUES (780,'1513430650854297601','1497825987499515915','2022-05-05 06:56:42');
INSERT INTO `back_role_permission` VALUES (781,'1513430650854297601','1512328763396460545','2022-05-05 06:56:42');
INSERT INTO `back_role_permission` VALUES (782,'1513430650854297601','1515162854164692993','2022-05-05 06:56:42');
INSERT INTO `back_role_permission` VALUES (783,'1513430650854297601','1515162951845838849','2022-05-05 06:56:42');
INSERT INTO `back_role_permission` VALUES (787,'1515169656457560066','1497811057370992642','2022-05-05 06:57:42');
INSERT INTO `back_role_permission` VALUES (788,'1515169656457560066','1497825987499515907','2022-05-05 06:57:42');
INSERT INTO `back_role_permission` VALUES (789,'1515169656457560066','1497825987499515911','2022-05-05 06:57:42');
INSERT INTO `back_role_permission` VALUES (790,'1515169656457560066','1497825987499515915','2022-05-05 06:57:42');
INSERT INTO `back_role_permission` VALUES (791,'1515169656457560066','1512328763396460545','2022-05-05 06:57:42');
INSERT INTO `back_role_permission` VALUES (792,'1515169656457560066','1515162854164692993','2022-05-05 06:57:42');
INSERT INTO `back_role_permission` VALUES (793,'1515169656457560066','1515162951845838849','2022-05-05 06:57:42');
INSERT INTO `back_role_permission` VALUES (794,'1515169528258658306','1497811057370992642','2022-05-05 06:58:20');
INSERT INTO `back_role_permission` VALUES (795,'1515169528258658306','1497825987499515907','2022-05-05 06:58:20');
INSERT INTO `back_role_permission` VALUES (796,'1515169528258658306','1497825987499515911','2022-05-05 06:58:20');
INSERT INTO `back_role_permission` VALUES (797,'1515169528258658306','1497825987499515915','2022-05-05 06:58:20');
INSERT INTO `back_role_permission` VALUES (798,'1515169528258658306','1512344969314701313','2022-05-05 06:58:20');
INSERT INTO `back_role_permission` VALUES (799,'1515169528258658306','1515242200535572482','2022-05-05 06:58:20');
INSERT INTO `back_role_permission` VALUES (818,'1497825987499515915','1497811057370992642','2022-05-05 07:42:23');
INSERT INTO `back_role_permission` VALUES (819,'1497825987499515915','1497811074932543489','2022-05-05 07:42:23');
INSERT INTO `back_role_permission` VALUES (820,'1497825987499515915','1497811107790721025','2022-05-05 07:42:23');
INSERT INTO `back_role_permission` VALUES (821,'1497825987499515915','1497825987499515906','2022-05-05 07:42:23');
INSERT INTO `back_role_permission` VALUES (822,'1497825987499515915','1497825987499515907','2022-05-05 07:42:23');
INSERT INTO `back_role_permission` VALUES (823,'1497825987499515915','1497825987499515908','2022-05-05 07:42:23');
INSERT INTO `back_role_permission` VALUES (824,'1497825987499515915','1497825987499515909','2022-05-05 07:42:23');
INSERT INTO `back_role_permission` VALUES (825,'1497825987499515915','1497825987499515910','2022-05-05 07:42:23');
INSERT INTO `back_role_permission` VALUES (826,'1497825987499515915','1497825987499515911','2022-05-05 07:42:23');
INSERT INTO `back_role_permission` VALUES (827,'1497825987499515915','1497825987499515912','2022-05-05 07:42:23');
INSERT INTO `back_role_permission` VALUES (828,'1497825987499515915','1497825987499515913','2022-05-05 07:42:23');
INSERT INTO `back_role_permission` VALUES (829,'1497825987499515915','1497825987499515914','2022-05-05 07:42:23');
INSERT INTO `back_role_permission` VALUES (830,'1497825987499515915','1497825987499515915','2022-05-05 07:42:23');
INSERT INTO `back_role_permission` VALUES (831,'1497825987499515915','1512328763396460545','2022-05-05 07:42:23');
INSERT INTO `back_role_permission` VALUES (832,'1497825987499515915','1512344969314701313','2022-05-05 07:42:23');
INSERT INTO `back_role_permission` VALUES (833,'1497825987499515915','1515162854164692993','2022-05-05 07:42:23');
INSERT INTO `back_role_permission` VALUES (834,'1497825987499515915','1515162951845838849','2022-05-05 07:42:23');
INSERT INTO `back_role_permission` VALUES (835,'1497825987499515915','1515242200535572482','2022-05-05 07:42:24');
/*!40000 ALTER TABLE `back_role_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_log`
--

DROP TABLE IF EXISTS `sys_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_log` (
  `id` char(19) NOT NULL,
  `userId` char(19) DEFAULT NULL,
  `ip` varchar(50) DEFAULT NULL,
  `operation` varchar(50) DEFAULT NULL,
  `time_cost` int DEFAULT NULL,
  `method` varchar(20) DEFAULT NULL,
  `params` json DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_log`
--

LOCK TABLES `sys_log` WRITE;
/*!40000 ALTER TABLE `sys_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_log` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-05-12 20:48:56
