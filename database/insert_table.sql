-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: qlmaytinh
-- ------------------------------------------------------
-- Server version	8.0.44

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
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `productid` bigint NOT NULL,
  `userid` bigint NOT NULL,
  `quantity` int NOT NULL,
  `orderdate` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `status` varchar(50) NOT NULL,
  `totalamount` double NOT NULL,
  PRIMARY KEY (`id`),
  KEY `productid` (`productid`),
  KEY `userid` (`userid`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`productid`) REFERENCES `product` (`id`),
  CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`userid`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=127 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (101,28,20,1,'2026-02-27 12:48:38','PENDING',23740500),(102,28,21,1,'2026-02-27 05:49:33','PENDING',24990000),(103,28,21,2,'2026-02-27 05:49:33','PAID',49980000),(104,28,21,3,'2026-02-27 05:49:33','PAID',74970000),(105,28,20,1,'2026-02-27 05:49:33','PENDING',24990000),(106,28,20,2,'2026-02-27 05:49:33','PAID',49980000),(107,28,20,3,'2026-02-26 05:49:33','PAID',74970000),(108,28,19,1,'2026-02-27 05:49:33','PAID',24990000),(109,28,19,2,'2026-02-27 05:49:33','PAID',49980000),(110,28,19,3,'2026-02-27 05:49:33','PAID',74970000),(111,28,18,1,'2026-02-27 05:49:33','PAID',24990000),(112,28,18,1,'2026-02-27 05:49:33','PAID',23740500),(113,28,18,3,'2026-02-27 05:49:33','PAID',74970000),(114,28,17,1,'2026-02-27 05:49:33','PAID',24990000),(115,28,17,2,'2026-02-27 05:49:33','PENDING',49980000),(116,28,17,3,'2026-02-27 05:49:33','PAID',74970000),(117,29,21,1,'2026-02-27 05:49:33','PAID',42990000),(118,29,21,2,'2026-02-27 05:49:33','PAID',85980000),(119,29,21,3,'2026-02-27 05:49:33','PENDING',128970000),(120,29,20,1,'2026-02-27 05:49:33','PENDING',42990000),(121,29,20,2,'2026-02-27 05:49:33','PENDING',85980000),(122,42,18,1,'2026-03-01 20:24:47','PAID',28820699.999999996),(123,44,18,1,'2026-03-01 20:24:59','PAID',32290500),(124,42,18,1,'2026-03-01 20:25:45','PAID',28820699.999999996),(125,42,18,1,'2026-03-05 13:45:05','PENDING',28820699.999999996),(126,37,18,1,'2026-03-31 12:16:38','PENDING',29361100);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `brand` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `description` text,
  `price` double NOT NULL,
  `quantity` int NOT NULL,
  `warrantytime` int NOT NULL,
  `image` varchar(225) NOT NULL,
  `discount` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (28,'MacBook Air M2 13','Apple','CPU: M2 | RAM: 8GB | SSD: 256GB | SCREEN: 11-13.9',24990000,10,12,'https://images.unsplash.com/photo-1517336714731-489689fd1ca8',0.05),(29,'MacBook Pro M3 14','Apple','CPU: M3 | RAM: 16GB | SSD: 512GB | SCREEN: 11-13.9',42990000,8,12,'https://images.unsplash.com/photo-1496181133206-80ce9b88a853',0.04),(30,'Dell XPS 13 Plus','Dell','CPU: M3 | RAM: 16GB | SSD: 512GB | SCREEN: 11-13.9',31990000,6,24,'https://images.unsplash.com/photo-1593642634367-d91a135587b5',0.1),(31,'Dell XPS 15','Dell','CPU: M4 | RAM: 24GB | SSD: 512GB | SCREEN: 15-15.9',35990000,5,24,'https://images.unsplash.com/photo-1587202372775-e229f172b9d7',0.12),(32,'HP Spectre x360 14','HP','CPU: M2 | RAM: 16GB | SSD: 512GB | SCREEN: 11-13.9',28990000,7,24,'https://images.unsplash.com/photo-1587829741301-dc798b83add3',0.08),(33,'HP Envy 16','HP','CPU: M3 | RAM: 16GB | SSD: 512GB | SCREEN: 15-15.9',27990000,9,24,'https://images.unsplash.com/photo-1611186871348-b1ce696e52c9',0.07),(34,'Asus ROG Zephyrus G14','Asus','CPU: M4 | RAM: 24GB | SSD: 512GB | SCREEN: 11-13.9',34990000,6,24,'https://images.unsplash.com/photo-1611078489935-0cb964de46d6',0.1),(35,'Asus Vivobook 15','Asus','CPU: M2 | RAM: 8GB | SSD: 256GB | SCREEN: 15-15.9',15990000,12,24,'https://images.unsplash.com/photo-1518770660439-4636190af475',0.15),(36,'Lenovo ThinkPad X13','Lenovo','CPU: M3 | RAM: 16GB | SSD: 512GB | SCREEN: 11-13.9',25990000,10,24,'https://images.unsplash.com/photo-1587614382346-4ec70e388b28',0.09),(37,'Lenovo Legion 5 Pro','Lenovo','CPU: M4 | RAM: 24GB | SSD: 512GB | SCREEN: 15-15.9',32990000,4,24,'https://images.unsplash.com/photo-1603302576837-37561b2e2302',0.11),(38,'Acer Swift 3','Acer','CPU: M2 | RAM: 8GB | SSD: 256GB | SCREEN: 11-13.9',13990000,14,24,'https://images.unsplash.com/photo-1511467687858-23d96c32e4ae',0.18),(39,'Acer Predator Helios Neo 16A','Acer','CPU: M4 | RAM: 24GB | SSD: 512GB | SCREEN: 15-15.9',33990000,6,24,'https://images.unsplash.com/photo-1618761714954-0b8cd0026356',0.1),(40,'MSI Modern 14','MSI','CPU: M3 | RAM: 16GB | SSD: 256GB | SCREEN: 11-13.9',18990000,9,24,'https://images.unsplash.com/photo-1496181133206-80ce9b88a853',0.12),(41,'MSI Katana 15','MSI','CPU: M4 | RAM: 24GB | SSD: 512GB | SCREEN: 15-15.9',29990000,7,24,'https://images.unsplash.com/photo-1593642634367-d91a135587b5',0.09),(42,'Samsung Galaxy Book3 Pro','Samsung','CPU: M3 | RAM: 16GB | SSD: 512GB | SCREEN: 15-15.9',30990000,2,24,'https://images.unsplash.com/photo-1587829741301-dc798b83add3',0.07),(43,'Huawei MateBook X Pro','Huawei','CPU: M3 | RAM: 16GB | SSD: 512GB | SCREEN: 11-13.9',28990000,8,24,'https://images.unsplash.com/photo-1517336714731-489689fd1ca8',0.06),(44,'LG Gram 16','LG','CPU: M3 | RAM: 16GB | SSD: 512GB | SCREEN: 15-15.9',33990000,4,24,'https://images.unsplash.com/photo-1611186871348-b1ce696e52c9',0.05),(45,'Razer Blade 14','Razer','CPU: M4 | RAM: 24GB | SSD: 512GB | SCREEN: 11-13.9',42990000,4,24,'https://images.unsplash.com/photo-1611078489935-0cb964de46d6',0.04),(46,'Gigabyte Aero 16','Gigabyte','CPU: M4 | RAM: 24GB | SSD: 512GB | SCREEN: 15-15.9',36990000,3,24,'https://images.unsplash.com/photo-1603302576837-37561b2e2302',0.06);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `code` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'ADMIN','ADMIN'),(2,'USER','USER');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `fullname` varchar(150) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `customercitizenid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `email` varchar(150) DEFAULT NULL,
  `roleid` bigint NOT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `roleid` (`roleid`),
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`roleid`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (17,'Nguyen Van A','nguyenvan4','$2a$10$xJnr./iqWezSIpquPe7J9OK0uUn9mpmH1rv5vbJD7KCrekoacBzVO','0777','09090006','nguyenvana@example.com',1,'image.img',''),(18,'Nguyen Van B','nguyenvan1','$2a$10$ynhKZ.JZXwAPmZrYmLRXE.puaTav2xQ39IpFzogDgGfHUhM23ACfS','0777','09090006','nguyenvana@example.com',2,'https://tse2.mm.bing.net/th/id/OIP.finRb_UVSWmskGdxhKRZnAAAAA?pid=Api&P=0&h=180','adadadad'),(19,'Nguyen Van C','nguyenvan2','$2a$10$Pm4obcxkal/iHbMMpkUrn.snDnGT5ylbPcS3v38JGJvzgqrOG6SC.','0777','09090006','nguyenvana@example.com',2,'image.img',''),(20,'Nguyen Van D','nguyenvan3','$2a$10$OBu6NWmdfzuglOUYiVaFTe/FXfMw4x.hrTf7YTbec26HXSIu5RCEW','0777','09090006','nguyenvana@example.com',2,'image.img',''),(21,'Nguyễn minh quốc','nguyenquoc123','$2a$10$FqHaHqybZNHE76o60kpu0ua7Kt2gyLqVH4Pmyzj.VYrH508BNIiiO',NULL,NULL,NULL,2,NULL,NULL),(23,'Nguyen Van H','nguyen van G','$2a$10$ZYyqfIBYvL2w7q1a8HYo0uBFj9F6csYId2t2HJA/5oDZ6EYhH.xIK','96523897465132','09090006','gacongnghiep2006@gmail.com',1,'dâdadadadd','qưertyuiopasfghjkkl');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'qlmaytinh'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-04-27 12:47:49
