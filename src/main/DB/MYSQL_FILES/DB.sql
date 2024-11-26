CREATE DATABASE  IF NOT EXISTS `user` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `user`;
-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: user
-- ------------------------------------------------------
-- Server version	8.0.40

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin` (
  `id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `contact_no` varchar(15) NOT NULL,
  `dob` date NOT NULL,
  `address` varchar(255) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES (1,'dummy','admin','30103','2024-11-22','addy','username','password'),(3,'ADMIN','COOL','03331111111','1997-11-07','cool town','admin','password');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `appointment`
--

DROP TABLE IF EXISTS `appointment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `appointment` (
  `appointmentID` int NOT NULL AUTO_INCREMENT,
  `status` varchar(255) NOT NULL,
  `date` date NOT NULL,
  `time` varchar(255) NOT NULL,
  `doctor_id` int NOT NULL,
  `patient_id` int NOT NULL,
  PRIMARY KEY (`appointmentID`),
  KEY `doctor_id` (`doctor_id`),
  KEY `patient_id` (`patient_id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointment`
--

LOCK TABLES `appointment` WRITE;
/*!40000 ALTER TABLE `appointment` DISABLE KEYS */;
INSERT INTO `appointment` VALUES (9,'confirmed','2024-11-30','03:00 PM - 04:00 PM',3,9),(10,'confirmed','2024-11-27','11:00 AM - 12:00 PM',3,9),(11,'confirmed','2024-11-29','03:00 PM - 04:00 PM',8,15),(14,'confirmed','2024-12-07','04:00 PM - 05:00 PM',12,9),(16,'Pending','2024-12-06','11:00 AM - 12:00 PM',7,16),(17,'confirmed','2024-11-25','09:00 AM - 10:00 AM',8,9),(21,'Pending','2024-11-27','09:00 AM - 10:00 AM',8,9),(22,'confirmed','2025-11-13','09:00 AM - 10:00 AM',8,9);
/*!40000 ALTER TABLE `appointment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `doctors`
--

DROP TABLE IF EXISTS `doctors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `doctors` (
  `DoctorID` int NOT NULL AUTO_INCREMENT,
  `Name` varchar(100) NOT NULL,
  `DOB` date NOT NULL,
  `Hospital` varchar(100) NOT NULL,
  `Specialty` varchar(100) NOT NULL,
  `Contact` varchar(15) NOT NULL,
  `Address` text NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`DoctorID`),
  UNIQUE KEY `Username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doctors`
--

LOCK TABLES `doctors` WRITE;
/*!40000 ALTER TABLE `doctors` DISABLE KEYS */;
INSERT INTO `doctors` VALUES (7,'Moeez','1998-07-09','CMH','Medical Specialist','03099717098','RaceCourse Apartments','moeez','password'),(8,'Shabbir ','1967-03-15','CMH','Paeds','03009113908','RaceCourse Apartments','baba','password'),(10,'Mama','1974-09-05','Shifa','Dentist','03335555555','RaceCourse Apartments','mama','password'),(12,'Maaz Hussain','2024-11-08','Shifa','Neurologist','11111111111','Westridge','username','password');
/*!40000 ALTER TABLE `doctors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `emergency`
--

DROP TABLE IF EXISTS `emergency`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `emergency` (
  `emergency_id` int NOT NULL AUTO_INCREMENT,
  `patient_id` int NOT NULL,
  `hospital_id` int NOT NULL,
  `type` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  PRIMARY KEY (`emergency_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `emergency`
--

LOCK TABLES `emergency` WRITE;
/*!40000 ALTER TABLE `emergency` DISABLE KEYS */;
INSERT INTO `emergency` VALUES (1,9,2,'Heart Attack','Pending','i am dead'),(2,9,4,'Heart Attack','ALLOCATED','oi'),(3,16,2,'Broken Bone','ALLOCATED','oho saad haddi toot gayi'),(4,9,4,'Heart Attack','ALLOCATED','efwhoiwd'),(5,9,4,'Heart Attack','ALLOCATED','fuck'),(6,9,2,'Heart Attack','ALLOCATED','fuck'),(7,9,4,'Accident','ALLOCATED',','),(8,9,4,'Heart Attack','ALLOCATED',','),(9,9,4,'Accident','ALLOCATED','l'),(10,9,4,'Accident','ALLOCATED','yes');
/*!40000 ALTER TABLE `emergency` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `faq`
--

DROP TABLE IF EXISTS `faq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `faq` (
  `patientID` int NOT NULL,
  `doctorID` int NOT NULL,
  `Question` varchar(500) NOT NULL,
  `Answer` varchar(500) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `faq`
--

LOCK TABLES `faq` WRITE;
/*!40000 ALTER TABLE `faq` DISABLE KEYS */;
INSERT INTO `faq` VALUES (9,3,'geyy','true'),(0,8,'why so cool','yoyo'),(15,8,'hello','hi'),(0,10,'yo mama whats up','im good'),(9,10,'hi mama','hi beta jee'),(0,9,'is ur chaddi paseena full','yes'),(9,8,'jfbejksd','jtyj');
/*!40000 ALTER TABLE `faq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `feedback`
--

DROP TABLE IF EXISTS `feedback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `feedback` (
  `id` int NOT NULL AUTO_INCREMENT,
  `patient_name` varchar(255) NOT NULL,
  `doctor_name` varchar(255) NOT NULL,
  `hospital_name` varchar(255) NOT NULL,
  `experience_rating` varchar(255) DEFAULT NULL,
  `recommendations` text,
  `feedback_comments` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feedback`
--

LOCK TABLES `feedback` WRITE;
/*!40000 ALTER TABLE `feedback` DISABLE KEYS */;
INSERT INTO `feedback` VALUES (2,'Fakhir','munhim','CMH','0','very cool','not cool'),(9,'Fakhir','Mama','Shifa','0','very cool mama','whatsup maa'),(15,'Afrah','Shabbir ','CMH','0','everything good','perfect'),(16,'Fakhir','Shabbir ','CMH','0','very cool','awesome'),(17,'Inam','Shabbir ','CMH','Excellent','hihi','yoyoy'),(18,'Fakhir','Shabbir ','CMH','Excellent','ljnl','lkn');
/*!40000 ALTER TABLE `feedback` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `healthcare_packages`
--

DROP TABLE IF EXISTS `healthcare_packages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `healthcare_packages` (
  `package_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `hospital_name` varchar(255) NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `price` double NOT NULL,
  `description` text,
  PRIMARY KEY (`package_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `healthcare_packages`
--

LOCK TABLES `healthcare_packages` WRITE;
/*!40000 ALTER TABLE `healthcare_packages` DISABLE KEYS */;
INSERT INTO `healthcare_packages` VALUES (9,'package1','CMH','2024-11-28','2024-12-07',2500,'good package');
/*!40000 ALTER TABLE `healthcare_packages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hospitals`
--

DROP TABLE IF EXISTS `hospitals`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hospitals` (
  `id` int NOT NULL AUTO_INCREMENT,
  `Name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `Name_UNIQUE` (`Name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hospitals`
--

LOCK TABLES `hospitals` WRITE;
/*!40000 ALTER TABLE `hospitals` DISABLE KEYS */;
INSERT INTO `hospitals` VALUES (2,'CMH'),(3,'Reliance'),(4,'Safari Hospital'),(5,'Shifa');
/*!40000 ALTER TABLE `hospitals` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inventory`
--

DROP TABLE IF EXISTS `inventory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inventory` (
  `inventoryID` int NOT NULL AUTO_INCREMENT,
  `hospital` varchar(255) DEFAULT NULL,
  `medQuantity` int DEFAULT NULL,
  `stockQuantity` int DEFAULT NULL,
  `miscellaniousQuantity` int DEFAULT NULL,
  PRIMARY KEY (`inventoryID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inventory`
--

LOCK TABLES `inventory` WRITE;
/*!40000 ALTER TABLE `inventory` DISABLE KEYS */;
INSERT INTO `inventory` VALUES (1,NULL,204,325,172),(2,NULL,301,541,622),(3,NULL,462,397,590),(4,NULL,618,775,743),(5,'Safari Hospital',100,100,100),(6,'Safari Hospital',253,869,100);
/*!40000 ALTER TABLE `inventory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notification`
--

DROP TABLE IF EXISTS `notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notification` (
  `NotificationID` int NOT NULL AUTO_INCREMENT,
  `patient_id` int DEFAULT NULL,
  `doctor_id` int DEFAULT NULL,
  `staff_id` int DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `isRead` tinyint DEFAULT '0',
  PRIMARY KEY (`NotificationID`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notification`
--

LOCK TABLES `notification` WRITE;
/*!40000 ALTER TABLE `notification` DISABLE KEYS */;
INSERT INTO `notification` VALUES (8,0,NULL,NULL,'An emergency has been reported: Heart Attack. Please attend to the patient. Description: fuck',0),(9,0,NULL,NULL,'An emergency has been reported: Heart Attack. Please attend to the patient. Description: fuck',0),(10,0,NULL,NULL,'An emergency has been reported: Heart Attack. Please attend to the patient. Description: fuck',0),(11,0,NULL,NULL,'An emergency has been reported: Accident. Please attend to the patient. Description: ,',0),(12,0,NULL,NULL,'An emergency has been reported: Heart Attack. Please attend to the patient. Description: ,',0),(14,9,NULL,NULL,'Video consultation concluded. Symptoms: yes, Diagnosis: yes, Treatment: yes',1),(15,NULL,8,NULL,'You have a new Booked Appointment from Fakhir. Please review the details and respond promptly.',1),(16,NULL,8,NULL,'You have a new video consultation request from Fakhir. Please review the details and respond promptly.',1),(17,NULL,NULL,13,'An emergency has been reported: Accident. Please attend to the patient. Description: yes',1),(18,9,NULL,NULL,'Your appointment with Dr. Shabbir  has been cancelled due to the unavailability of the doctor. Kindly reschedule.',1),(21,9,NULL,NULL,'Your appointment with Dr. Shabbir  is confirmed. Please make sure to be prepared for the consultation.',1),(22,NULL,8,NULL,'You have a new video consultation request from Fakhir. Please review the details and respond promptly.',1);
/*!40000 ALTER TABLE `notification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patients`
--

DROP TABLE IF EXISTS `patients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `patients` (
  `id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `contact_no` varchar(255) NOT NULL,
  `dob` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patients`
--

LOCK TABLES `patients` WRITE;
/*!40000 ALTER TABLE `patients` DISABLE KEYS */;
INSERT INTO `patients` VALUES (9,'Fakhir','Ali','03335080003','2002-10-05','Bahria Town','username','password'),(13,'hamza','Aslam','99999999999','2024-10-27','fdsfsd','dwcscs','abcdefgh'),(16,'Inam','habibi','03001111111','2024-10-27','i8 islamabad','yoyo','password'),(17,'hello','lastname','03335080003','2024-11-26','kjn','user','password');
/*!40000 ALTER TABLE `patients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment` (
  `paymentID` int NOT NULL AUTO_INCREMENT,
  `paitentID` int NOT NULL,
  `Description` varchar(500) NOT NULL,
  `Amount` int NOT NULL,
  `Status` varchar(255) NOT NULL,
  PRIMARY KEY (`paymentID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment`
--

LOCK TABLES `payment` WRITE;
/*!40000 ALTER TABLE `payment` DISABLE KEYS */;
INSERT INTO `payment` VALUES (1,0,'HealthCare Package: package1',2500,'Paid'),(2,0,'HealthCare Package: package1',2500,'Paid'),(3,0,'HealthCare Package: package1',2500,'Paid'),(4,9,'HealthCare Package: package1',2500,'Paid'),(5,9,'HealthCare Package: package1',2500,'Paid'),(6,9,'Medical Consultation Fee for Video Consultation with Dr. Shabbir ',5000,'Paid'),(7,9,'HealthCare Package: package1',2500,'Unpaid');
/*!40000 ALTER TABLE `payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `staff`
--

DROP TABLE IF EXISTS `staff`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `staff` (
  `id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `contact_no` varchar(255) NOT NULL,
  `dob` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `Hospital` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `staff`
--

LOCK TABLES `staff` WRITE;
/*!40000 ALTER TABLE `staff` DISABLE KEYS */;
INSERT INTO `staff` VALUES (12,'habibi','dubai','03002222222','2000-11-23','dubai','habibi','password','Shifa'),(13,'zayyam','wallahi','03002323232','2000-08-23','Media Town Rawalpindi','zayyam','password','Safari Hospital'),(14,'Rayyan','Sheikh','03001010101','2003-07-02','I-10 Islamabad','rayyan','password','Shifa'),(15,'Umer','Farooq','03340000000','2001-06-26','your home','umer','password','CMH'),(16,'ayna','sulaiman','03210000000','1990-01-15','DHA 2 Islamabad','ayna','password','CMH');
/*!40000 ALTER TABLE `staff` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `videoconsulations`
--

DROP TABLE IF EXISTS `videoconsulations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `videoconsulations` (
  `historyID` int NOT NULL AUTO_INCREMENT,
  `symptoms` varchar(500) NOT NULL,
  `Diagnosis` varchar(500) DEFAULT NULL,
  `Treatment` varchar(500) DEFAULT NULL,
  `Date` date DEFAULT NULL,
  `isUpdated` tinyint(1) NOT NULL DEFAULT '0',
  `patientID` int NOT NULL,
  `doctorID` int NOT NULL,
  PRIMARY KEY (`historyID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `videoconsulations`
--

LOCK TABLES `videoconsulations` WRITE;
/*!40000 ALTER TABLE `videoconsulations` DISABLE KEYS */;
INSERT INTO `videoconsulations` VALUES (1,'yes','iws','is','2024-11-26',1,9,8),(2,'yes','yes','yes','2024-11-26',1,9,8),(3,'yes','yes','yes','2024-11-26',1,9,8),(4,'headache','chutiya','lun khao','2024-11-26',1,9,8),(5,'yes',NULL,NULL,NULL,0,9,8);
/*!40000 ALTER TABLE `videoconsulations` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-11-27  2:55:13
