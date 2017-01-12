-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: paike_v_5.0
-- ------------------------------------------------------
-- Server version	5.7.16-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `course`
--

DROP TABLE IF EXISTS `course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `course` (
  `idcourse` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `courseName` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idcourse`),
  UNIQUE KEY `idcourse_UNIQUE` (`idcourse`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course`
--

LOCK TABLES `course` WRITE;
/*!40000 ALTER TABLE `course` DISABLE KEYS */;
INSERT INTO `course` VALUES (8,'计算机网络'),(9,'毛概'),(10,'军事理论'),(11,'通信原理'),(12,'实验课'),(13,'英语'),(14,'微积分');
/*!40000 ALTER TABLE `course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group`
--

DROP TABLE IF EXISTS `group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `group` (
  `idgroup` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `groupName` varchar(45) DEFAULT NULL,
  `groupScheduled` int(10) unsigned DEFAULT '0',
  PRIMARY KEY (`idgroup`),
  UNIQUE KEY `idgroup_UNIQUE` (`idgroup`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group`
--

LOCK TABLES `group` WRITE;
/*!40000 ALTER TABLE `group` DISABLE KEYS */;
INSERT INTO `group` VALUES (3,'通信2班',8),(4,'通信3班',6),(5,'通信4班',13),(6,'通信5班',7),(7,'通信1班',10);
/*!40000 ALTER TABLE `group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room`
--

DROP TABLE IF EXISTS `room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `room` (
  `idroom` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `roomName` varchar(45) DEFAULT NULL,
  `roomScheduled` int(10) unsigned DEFAULT '0',
  PRIMARY KEY (`idroom`),
  UNIQUE KEY `idroom_UNIQUE` (`idroom`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room`
--

LOCK TABLES `room` WRITE;
/*!40000 ALTER TABLE `room` DISABLE KEYS */;
INSERT INTO `room` VALUES (1,'东九101',6),(2,'东九102',5),(3,'东九103',7),(4,'东九104',6),(6,'东九202',8),(7,'东九203',7),(8,'东九204',5),(9,'东12',0);
/*!40000 ALTER TABLE `room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `schedule`
--

DROP TABLE IF EXISTS `schedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `schedule` (
  `idschedule` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `idWork` int(10) unsigned DEFAULT '0',
  `idGroup` int(10) unsigned DEFAULT '0',
  `idCourse` int(10) unsigned DEFAULT '0',
  `idTeacher` int(10) unsigned DEFAULT '0',
  `idRoom` int(10) unsigned DEFAULT '0',
  `time` int(10) unsigned DEFAULT '0',
  PRIMARY KEY (`idschedule`),
  UNIQUE KEY `idcourse_UNIQUE` (`idschedule`)
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schedule`
--

LOCK TABLES `schedule` WRITE;
/*!40000 ALTER TABLE `schedule` DISABLE KEYS */;
INSERT INTO `schedule` VALUES (7,4,5,8,2,6,12),(8,4,5,8,2,6,5),(9,4,5,8,2,3,17),(10,5,6,9,3,3,14),(11,5,6,9,3,1,13),(14,8,4,9,3,3,6),(15,9,3,10,4,3,15),(16,10,5,10,4,7,6),(17,10,5,10,4,4,18),(21,12,3,11,5,3,20),(22,12,3,11,5,1,4),(23,13,4,11,6,3,13),(24,13,4,11,6,4,1),(25,14,5,11,7,7,1),(26,14,5,11,7,7,20),(34,18,6,12,8,8,5),(35,18,6,12,8,2,1),(39,20,5,13,13,2,10),(40,20,5,13,13,6,11),(41,20,5,13,13,2,14),(44,22,3,14,15,7,14),(45,22,3,14,15,6,1),(46,23,4,14,14,6,14),(47,23,4,14,14,7,5),(48,23,4,14,14,8,18),(49,24,5,14,14,6,8),(50,24,5,14,14,1,19),(51,24,5,14,14,7,13),(52,25,6,14,15,1,7),(53,25,6,14,15,2,2),(54,25,6,14,15,7,10),(55,26,7,8,2,8,10),(56,26,7,8,2,1,3),(66,31,7,13,13,4,8),(67,31,7,13,13,2,17),(68,32,7,14,14,6,16),(69,32,7,14,14,6,2),(70,33,7,11,5,4,13),(71,33,7,11,5,8,12),(72,33,7,11,5,4,15),(73,34,7,10,4,8,20),(74,35,3,8,1,1,8),(75,35,3,8,1,3,11),(76,35,3,8,1,4,3);
/*!40000 ALTER TABLE `schedule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teacher`
--

DROP TABLE IF EXISTS `teacher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teacher` (
  `idteacher` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `teacherName` varchar(45) DEFAULT NULL,
  `idCourse` int(10) unsigned DEFAULT NULL,
  `teacherScheduled` int(10) unsigned DEFAULT '0',
  PRIMARY KEY (`idteacher`),
  UNIQUE KEY `idteacher_UNIQUE` (`idteacher`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teacher`
--

LOCK TABLES `teacher` WRITE;
/*!40000 ALTER TABLE `teacher` DISABLE KEYS */;
INSERT INTO `teacher` VALUES (1,'曾龙龙',8,3),(2,'毛血旺',8,5),(3,'杨晓宇',9,3),(4,'余江',10,4),(5,'徐龙',11,5),(6,'徐远志',11,2),(7,'高于',11,2),(8,'刘希',12,2),(9,'张长风',12,0),(11,'徐鑫',13,0),(13,'曾永敏',13,5),(14,'张德军',14,8),(15,'郑向东',14,5);
/*!40000 ALTER TABLE `teacher` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `iduser` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `userName` varchar(45) DEFAULT NULL,
  `userPassword` varchar(45) DEFAULT NULL,
  `userType` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`iduser`),
  UNIQUE KEY `iduser_UNIQUE` (`iduser`),
  UNIQUE KEY `userName_UNIQUE` (`userName`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (10,'admin','111',1),(11,'111','111',0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `work`
--

DROP TABLE IF EXISTS `work`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `work` (
  `idwork` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `idGroup` int(10) unsigned DEFAULT '0',
  `idCourse` int(10) unsigned DEFAULT '0',
  `size` int(10) unsigned DEFAULT '0',
  PRIMARY KEY (`idwork`),
  UNIQUE KEY `idwork_UNIQUE` (`idwork`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `work`
--

LOCK TABLES `work` WRITE;
/*!40000 ALTER TABLE `work` DISABLE KEYS */;
INSERT INTO `work` VALUES (4,5,8,3),(5,6,9,2),(8,4,9,1),(9,3,10,1),(10,5,10,2),(12,3,11,2),(13,4,11,2),(14,5,11,2),(18,6,12,2),(20,5,13,3),(22,3,14,2),(23,4,14,3),(24,5,14,3),(25,6,14,3),(26,7,8,2),(31,7,13,2),(32,7,14,2),(33,7,11,3),(34,7,10,1),(35,3,8,3);
/*!40000 ALTER TABLE `work` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-01-12 13:13:47
