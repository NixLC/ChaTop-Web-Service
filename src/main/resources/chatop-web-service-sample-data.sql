-- Sample data for chatop-web-service
-- Images URLs might be unreachable
USE rentals;

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
-- Dumping data for table `messages`
--
LOCK TABLES `messages` WRITE;
/*!40000 ALTER TABLE `messages` DISABLE KEYS */;
INSERT INTO `messages` VALUES (21,3,1,'Bonjour, cette location est-elle toujours disponible ?','2024-07-31 09:19:10','2024-07-31 09:19:10');
/*!40000 ALTER TABLE `messages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `rentals`
--
LOCK TABLES `rentals` WRITE;
/*!40000 ALTER TABLE `rentals` DISABLE KEYS */;
INSERT INTO `rentals` VALUES (2,'VILLA PLAIN PIED AVEC GRAND JARDIN, PROCHE DES PLAGES !!!',150,250,'https://cdn.pixabay.com/photo/2012/09/20/19/33/castle-57254_1280.jpg','Maison moderne à ossature bois lovée dans un jardin de 2 000 m² dans un cadre paisible à quelques minutes des plages de sable fin et des dunes de Keremma. Villa de plain pied composée d\'une pièce de vie spacieuse et lumineuse avec cuisine équipée ouverte sur un séjour, coin bureau et grand salon, 2 chambres avec lits doubles dont 1 à étage réservé aux enfants, salle de bain avec douche italienne, 2 wc, lingerie et cellier. Le plus : Grande terrasse en bois équipée d\'un salon de jardin, barbecue à disposition - Cabane enfants dans un arbre\r\n         Location juillet/août à la quinzaine minimum - Tarif préférentiel pour une location au delà de 2 semaines',1,'2024-03-07 13:08:41','2024-07-21 11:16:34'),(3,'MAISONNETTE POUR 2 PERSONNES',50,50,'https://cdn.pixabay.com/photo/2013/03/25/06/44/water-96591_1280.jpg','Petite maisonnette de pêcheur entièrement restaurée amenagée sur deux étages, avec terrasse extérieure et salon jardin.\n         située a 600 m de belles plages et à proximité des commerces dans un charmant petit village. chars à voile,surf, pêche à pied,randonnées etc..',3,'2024-05-10 11:09:11','2024-07-02 05:12:24'),(59,'Maison en bord de lac pas cher',50,60,'https://cdn.pixabay.com/photo/2017/07/15/18/24/bretagne-2507358_1280.jpg','Logement vue sur mer et citadelle Port Louis situé dans une impasse privée avec accès direct plage.\n\nLogement calme et bien exposé\n\nEmbarcadère Port Louis et Lorient à proximité. Presqu\'île classée Natura 2000.\n\nNombreuses activités possibles dont pêche à pied très réputé\n\nTous commerces accessible à pied, notamment restaurants, crêperie, pizza à emporter, pharmacie\n\nBelles balades à pied avec vue sur entrée rade de Lorient et île de Groix\n\nMusée de la Compagnie des Indes de Port Louis et Cité de la Voile de Lorient à proximité',1,'2024-07-31 08:46:55','2024-07-31 09:15:55');
/*!40000 ALTER TABLE `rentals` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `users`
--
LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'test@test.com','Adam Floyd','$2a$10$ZRTniKzLGHUuUdjod7r8q.ZbPnnVtpA3lnHpRmr8Byq3SrX8eiC3m','2022-07-01 22:00:00','2024-07-26 13:05:02'),(3,'john.smith@bigmail.com','John Smith','$2a$12$pboKBRhTN6JwIpAHTxigIuyRDBlvpE0pppis5E.U2Aed7a9h4HfO6','2024-07-01 07:34:22','2024-07-08 14:06:30');
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
