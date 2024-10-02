-- MySQL dump 10.13  Distrib 8.0.38, for macos14 (arm64)
--
-- Host: 127.0.0.1    Database: library_db
-- ------------------------------------------------------
-- Server version	8.0.39

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
-- Table structure for table `books`
--

DROP TABLE IF EXISTS `books`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `books` (
  `documentId` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `authors` text,
  `publisher` varchar(255) DEFAULT NULL,
  `publishedDate` date DEFAULT NULL,
  `description` text,
  `isbn` varchar(20) DEFAULT NULL,
  `pageCount` int DEFAULT NULL,
  `categories` text,
  `averageRating` decimal(3,2) DEFAULT NULL,
  `ratingsCount` int DEFAULT NULL,
  `imageLinks` text,
  `language` varchar(10) DEFAULT NULL,
  `maturityRating` varchar(50) DEFAULT NULL,
  `printType` varchar(50) DEFAULT NULL,
  `availableCopies` int DEFAULT NULL,
  PRIMARY KEY (`documentId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `books`
--

LOCK TABLES `books` WRITE;
/*!40000 ALTER TABLE `books` DISABLE KEYS */;
INSERT INTO `books` VALUES (1,'The Great Gatsby','F. Scott Fitzgerald','Scribner','1925-04-10','A novel about the American dream.','9780743273565',180,'Fiction, Classic',3.91,4000000,'https://example.com/gatsby.jpg','en','NOT_MATURE','BOOK',5),(2,'1984','George Orwell','Secker & Warburg','1949-06-08','Dystopian novel set in a totalitarian society.','9780451524935',328,'Fiction, Dystopia',4.17,3000000,'https://example.com/1984.jpg','en','NOT_MATURE','BOOK',3),(3,'To Kill a Mockingbird','Harper Lee','J.B. Lippincott & Co.','1960-07-11','A novel about racial injustice in the Deep South.','9780061120084',281,'Fiction, Classic',4.28,4500000,'https://example.com/mockingbird.jpg','en','NOT_MATURE','BOOK',2);
/*!40000 ALTER TABLE `books` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loandetails`
--

DROP TABLE IF EXISTS `loandetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `loandetails` (
  `loanId` int NOT NULL,
  `documentId` int NOT NULL,
  `documentType` enum('book','thesis') NOT NULL,
  KEY `fk_loandetails_loans` (`loanId`),
  KEY `fk_loandetails_theses` (`documentId`),
  CONSTRAINT `fk_loandetails_books` FOREIGN KEY (`documentId`) REFERENCES `books` (`documentId`),
  CONSTRAINT `fk_loandetails_loans` FOREIGN KEY (`loanId`) REFERENCES `loans` (`loanId`),
  CONSTRAINT `fk_loandetails_theses` FOREIGN KEY (`documentId`) REFERENCES `theses` (`documentId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loandetails`
--

LOCK TABLES `loandetails` WRITE;
/*!40000 ALTER TABLE `loandetails` DISABLE KEYS */;
INSERT INTO `loandetails` VALUES (1,1,'book'),(2,2,'book'),(3,3,'thesis'),(1,2,'thesis');
/*!40000 ALTER TABLE `loandetails` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loans`
--

DROP TABLE IF EXISTS `loans`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `loans` (
  `loanId` int NOT NULL AUTO_INCREMENT,
  `loanDate` date NOT NULL,
  `dueDate` date NOT NULL,
  `returnDate` date DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `comments` text,
  `userId` int NOT NULL,
  PRIMARY KEY (`loanId`),
  KEY `fk_loans_users` (`userId`),
  CONSTRAINT `fk_loans_users` FOREIGN KEY (`userId`) REFERENCES `users` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loans`
--

LOCK TABLES `loans` WRITE;
/*!40000 ALTER TABLE `loans` DISABLE KEYS */;
INSERT INTO `loans` VALUES (1,'2024-09-01','2024-09-15',NULL,'Active','First-time borrower.',1),(2,'2024-09-05','2024-09-20','2024-09-19','Returned','No issues.',2),(3,'2024-09-10','2024-09-25',NULL,'Active','Late return expected.',3);
/*!40000 ALTER TABLE `loans` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `theses`
--

DROP TABLE IF EXISTS `theses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `theses` (
  `documentId` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `authors` text,
  `publishedDate` date DEFAULT NULL,
  `institution` varchar(255) DEFAULT NULL,
  `degree` varchar(50) DEFAULT NULL,
  `description` text,
  `categories` text,
  `language` varchar(10) DEFAULT NULL,
  `citationCount` int DEFAULT NULL,
  `availableCopies` int DEFAULT NULL,
  PRIMARY KEY (`documentId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `theses`
--

LOCK TABLES `theses` WRITE;
/*!40000 ALTER TABLE `theses` DISABLE KEYS */;
INSERT INTO `theses` VALUES (1,'A Study on Quantum Computing','Alice Smith','2023-05-15','Harvard University','PhD','Explores the potential of quantum computing in various fields.','Computer Science, Quantum Physics','en',15,1),(2,'The Impact of Social Media on Youth','John Doe','2022-08-20','Stanford University','Master','Analyzes how social media influences the behavior of young people.','Sociology, Media Studies','en',25,2),(3,'Renewable Energy Sources and Sustainability','Emily Johnson','2021-11-10','MIT','Master','Discusses the importance of renewable energy for sustainable development.','Environmental Science, Energy','en',10,0);
/*!40000 ALTER TABLE `theses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `userId` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `phoneNumber` varchar(20) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `address` text,
  `membershipStatus` varchar(50) DEFAULT NULL,
  `privileges` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Alice Johnson','123-456-7890','alice.johnson@example.com','123 Main St, Anytown, USA','Active','Standard'),(2,'Bob Smith','098-765-4321','bob.smith@example.com','456 Elm St, Othertown, USA','Inactive','Standard'),(3,'Charlie Brown','555-555-5555','charlie.brown@example.com','789 Pine St, Sometown, USA','Active','Admin');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-10-02 16:19:45
