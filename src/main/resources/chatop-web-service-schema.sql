--
-- Database schema creation for chatop-web-service
--
CREATE DATABASE  IF NOT EXISTS `rentals` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci */;

USE `rentals`;

--
-- Table structure for table `users`
--
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `users_index` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_ci;

--
-- Table structure for table `rentals`
--
DROP TABLE IF EXISTS `rentals`;
CREATE TABLE `rentals` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `surface` decimal(10,0) DEFAULT NULL,
  `price` decimal(10,0) DEFAULT NULL,
  `picture` varchar(255) DEFAULT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `owner_id` int(11) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_rental_owner` (`owner_id`),
  CONSTRAINT `fk_rental_owner` FOREIGN KEY (`owner_id`) REFERENCES `users` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_ci;


--
-- Table structure for table `messages`
--
DROP TABLE IF EXISTS `messages`;
CREATE TABLE `messages` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rental_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `message` varchar(2000) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_message_rental` (`rental_id`),
  KEY `fk_message_user` (`user_id`),
  CONSTRAINT `fk_message_rental` FOREIGN KEY (`rental_id`) REFERENCES `rentals` (`id`) ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT `fk_message_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_ci;

