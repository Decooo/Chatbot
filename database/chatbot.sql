-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: chatbot
-- ------------------------------------------------------
-- Server version	5.7.20-log

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
-- Table structure for table `movie`
--

DROP TABLE IF EXISTS `movie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `movie` (
  `id_movie` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(225) NOT NULL,
  `director` varchar(45) NOT NULL,
  `production_year` int(11) NOT NULL,
  PRIMARY KEY (`id_movie`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movie`
--

LOCK TABLES `movie` WRITE;
/*!40000 ALTER TABLE `movie` DISABLE KEYS */;
INSERT INTO `movie` VALUES (1,'Najlepszy','Łukasz Palkowski',2017),(2,'Pianista','Roman Polański',2002),(3,'Wołyń','Wojciech Smarzowski',2016),(4,'Jak rozpętałem drugą wojnę światową','Tadeusz Chmielowski',1969),(5,'Chłopaki nie płaczą','Olaf Lubaszenko',2000),(6,'Dzień świra','Marek Koterski',2002),(7,'Dług','Krzysztof Krauze',1999),(8,'Kiler','Juliusz Machulski',1997),(9,'Kobiety mafii','Patryk Vega',2018),(10,'Wesele','Wojciech Smarzowski',2004);
/*!40000 ALTER TABLE `movie` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rating`
--

DROP TABLE IF EXISTS `rating`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rating` (
  `id_rating` int(11) NOT NULL AUTO_INCREMENT,
  `id_movie` int(11) NOT NULL,
  `rating` double DEFAULT NULL,
  `content_rating` varchar(5000) DEFAULT NULL,
  PRIMARY KEY (`id_rating`),
  KEY `movie_key_idx` (`id_movie`),
  CONSTRAINT `movie_key` FOREIGN KEY (`id_movie`) REFERENCES `movie` (`id_movie`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rating`
--

LOCK TABLES `rating` WRITE;
/*!40000 ALTER TABLE `rating` DISABLE KEYS */;
INSERT INTO `rating` VALUES (23,1,10,'Znakomicie odegrała swoją rolę Danuta Stenka wcielając się w George Sand. Pokazała jej prawdziwe oblicza – kobiety namiętnej, kochającej, opiekuńczej a także silnej, nieugiętej emancypantki. \r\nGra Stenki budzi we mnie wiele pozytywnych emocji. Na uznanie zasługuje także Piotr Adamczyk,. Twórcom jakoś udało się tak poprowadzić fabułę, by widz nie dał rady wszystkiego przewidzieć. Akcja rozwija się dynamicznie i jest całkiem emocjonująca, a gdy już na naszych oczach rozgrywa się drugi punkt zwrotny, ma się wrażenie, że wspomniany brak przewidywalności jest tak samo zaletą, co wadą.. Muzyka fantastycznie wpasowała się w całą produkcję. Świetnie dobrane efekty specjalne znacząco wzbogaciły efekt końcowy..'),(24,1,10,'Grę aktorską oceniam na raczej średnio udaną. Dobrze dobrano obsade do ról Pigwy,Spója,Spodka,Dudy,Ryjka i Zdechlaka. \r\nUważam ,że te komiczne role aktorzy odegrali naprawdę świetnie,rozbawiając widownie do łez .. Osoby, które czytały książkę, docenią ogrom pracy włożonej w utworzenie z \"Dwóch Wież\" historii ciekawszej dla widza, a za razem wciąż spójnej i inteligentnej. Wprowadzono wiele zmian w fabule, mając na celu głębsze ukazanie charakterów postaci i zmian, jakie się w nich dokonały na przestrzeni kilku miesięcy akcji książki. Doświadczymy ponadto wielu umiejętnie przedstawionych scen akcji (niektórych nie obejmuje treść \"Władcy Pierścieni\"), nieograniczających się tylko do następujących kolejno bitew.. Wszystkie obrazy w filmie, wielkie i małe, kameralne sceny zostały opatrzone świetną muzyką H. Shore\'a. Muzyka idealnie współgra z obrazem..'),(25,1,7.5,'Fabuła może wydawać się prosta i banalna, ale baśniowej historii trudno jest się oprzeć. Niesamowicie wciągają przygody nie tylko Froda, ale i innych członków Drużyny. Film jest wielowątkowy, ale nie mam wrażenia, jakoby miało to przytłaczać widza.. Grę aktorską oceniam na raczej średnio udaną. Dobrze dobrano obsade do ról Pigwy,Spója,Spodka,Dudy,Ryjka i Zdechlaka. \r\nUważam ,że te komiczne role aktorzy odegrali naprawdę świetnie,rozbawiając widownie do łez . \r\nJednak pozostała obsada aktorska nie wyróżniała się według mnie niczym szczególnym.. Wprawdzie niektóre dialogi i monologi są nieco zbyt przerysowane, ale za to z pasją ogląda się zwłaszcza sceny batalistyczne. Zachwyca świat Śródziemia. Błędy scenografów, operatorów i montażystów można policzyć na palcach jednej ręki. Wszystko wygląda niesamowicie realistycznie: zarówno przedmioty codziennego użytku, meble i kostiumy, jak i broń oraz zbroje..'),(28,1,6.7,'Fabuła może wydawać się prosta i banalna, ale baśniowej historii trudno jest się oprzeć. Niesamowicie wciągają przygody nie tylko Froda, ale i innych członków Drużyny. Film jest wielowątkowy, ale nie mam wrażenia, jakoby miało to przytłaczać widza..'),(31,1,5.5,'Znakomicie odegrała swoją rolę Danuta Stenka wcielając się w George Sand. Pokazała jej prawdziwe oblicza – kobiety namiętnej, kochającej, opiekuńczej a także silnej, nieugiętej emancypantki. \r\nGra Stenki budzi we mnie wiele pozytywnych emocji. Na uznanie zasługuje także Piotr Adamczyk, \r\nchoć nie mógł on w pełni wykorzystać swoich możliwości aktorskich ( jego rola jest nieefektowna, bezbarwna, człowiek, którego gra jest cichy,\r\n zagubiony, niezaradny, nie wydaje się on być pianistą światowej sławy)..'),(39,1,8.9,'Fabuła może wydawać się prosta i banalna, ale baśniowej historii trudno jest się oprzeć. Niesamowicie wciągają przygody nie tylko Froda, ale i innych członków Drużyny. Film jest wielowątkowy, ale nie mam wrażenia, jakoby miało to przytłaczać widza.. Znakomicie odegrała swoją rolę Danuta Stenka wcielając się w George Sand. Pokazała jej prawdziwe oblicza – kobiety namiętnej, kochającej, opiekuńczej a także silnej, nieugiętej emancypantki. \r\nGra Stenki budzi we mnie wiele pozytywnych emocji. Na uznanie zasługuje także Piotr Adamczyk,. Wszystkie obrazy w filmie, wielkie i małe, kameralne sceny zostały opatrzone świetną muzyką H. Shore\'a. Muzyka idealnie współgra z obrazem..'),(43,1,8.3,'Fabuła była interesująca. Częste zwroty akcji znacząco zwiększały zainteresowanie widza.. Zakończenie historii pozostawiało wiele do życzenia.. Gra aktorska stała na bardzo niskim poziomie. Główny bohater fatalnie odegrał swoją rolę. Najbardziej pozytywnie zaskoczył mnie występ Tomasza Karolaka.. Scenografia była niedopasowana do akcji na ekranie. Efekty wizualne były zrobione bardzo profesjonalnie. ścieżka dźwiękowa idealnie oddaje nastrój filmu.'),(44,1,5.7,'gra aktorska była na wspaniała. Znakomicie odegrała swoją rolę Danuta Stenka wcielając się w George Sand. Pokazała jej prawdziwe oblicza – kobiety namiętnej, kochającej, opiekuńczej a także silnej, nieugiętej emancypantki. \r\nGra Stenki budzi we mnie wiele pozytywnych emocji. Na uznanie zasługuje także Piotr Adamczyk, \r\nchoć nie mógł on w pełni wykorzystać swoich możliwości aktorskich ( jego rola jest nieefektowna, bezbarwna, człowiek, którego gra jest cichy,\r\n zagubiony, niezaradny, nie wydaje się on być pianistą światowej sławy).. Fabuła może wydawać się prosta i banalna, ale baśniowej historii trudno jest się oprzeć. Niesamowicie wciągają przygody nie tylko Froda, ale i innych członków Drużyny. Film jest wielowątkowy, ale nie mam wrażenia, jakoby miało to przytłaczać widza.. Wprawdzie niektóre dialogi i monologi są nieco zbyt przerysowane, ale za to z pasją ogląda się zwłaszcza sceny batalistyczne. Zachwyca świat Śródziemia. Błędy scenografów, operatorów i montażystów można policzyć na palcach jednej ręki. Wszystko wygląda niesamowicie realistycznie: zarówno przedmioty codziennego użytku, meble i kostiumy, jak i broń oraz zbroje..');
/*!40000 ALTER TABLE `rating` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'chatbot'
--

--
-- Dumping routines for database 'chatbot'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-12-27 23:11:49
