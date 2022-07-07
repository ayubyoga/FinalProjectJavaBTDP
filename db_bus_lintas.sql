-- Adminer 4.8.1 MySQL 5.5.5-10.4.24-MariaDB dump

SET NAMES utf8;
SET time_zone = '+00:00';
SET foreign_key_checks = 0;
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

SET NAMES utf8mb4;

DROP TABLE IF EXISTS `agency`;
CREATE TABLE `agency` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL,
  `details` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `owner_user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8y56hykf78k5u3wmutny52wcf` (`owner_user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

INSERT INTO `agency` (`id`, `code`, `details`, `name`, `owner_user_id`) VALUES
(1,	'HYT',	'PO Haryanto',	'Bus Lintas Jawa Tengah',	1),
(2,	'KDJ',	'PO Kramat Djati',	'Bus Lintas Jawa Barat',	2),
(3,	'RFI',	'Rans-J99',	'Double Decker J99',	3),
(4,	'ALS',	'PO Antar Lintas Sumatera',	'Bus Lintas Sumatera',	4),
(5,	'001',	'admin one',	'admin one',	1),
(6,	'002',	'admin two',	'admin two',	2),
(7,	'002',	'admin two',	'admin two',	3),
(8,	'usr1',	'user one',	'user one',	4),
(9,	'07',	'mon tiss',	'-',	5);

DROP TABLE IF EXISTS `bus`;
CREATE TABLE `bus` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `capacity` int(11) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `make` varchar(255) DEFAULT NULL,
  `agency_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK64nil2xxuhqde813s57dp1n9t` (`agency_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

INSERT INTO `bus` (`id`, `capacity`, `code`, `make`, `agency_id`) VALUES
(1,	40,	'HYT01',	'40',	1),
(2,	45,	'KDJ02',	'43',	2);

DROP TABLE IF EXISTS `flyway_schema_history`;
CREATE TABLE `flyway_schema_history` (
  `installed_rank` int(11) NOT NULL,
  `version` varchar(50) DEFAULT NULL,
  `description` varchar(200) NOT NULL,
  `type` varchar(20) NOT NULL,
  `script` varchar(1000) NOT NULL,
  `checksum` int(11) DEFAULT NULL,
  `installed_by` varchar(100) NOT NULL,
  `installed_on` timestamp NOT NULL DEFAULT current_timestamp(),
  `execution_time` int(11) NOT NULL,
  `success` tinyint(1) NOT NULL,
  PRIMARY KEY (`installed_rank`),
  KEY `flyway_schema_history_s_idx` (`success`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `flyway_schema_history` (`installed_rank`, `version`, `description`, `type`, `script`, `checksum`, `installed_by`, `installed_on`, `execution_time`, `success`) VALUES
(1,	'1',	'<< Flyway Baseline >>',	'BASELINE',	'<< Flyway Baseline >>',	NULL,	'root',	'2022-07-05 22:29:42',	0,	1),
(2,	'1.1',	'insert role',	'SQL',	'V1.1__insert_role.sql',	-555065487,	'root',	'2022-07-05 22:29:42',	5,	1),
(3,	'1.2',	'insert agency',	'SQL',	'V1.2__insert_agency.sql',	594219619,	'root',	'2022-07-05 22:29:42',	3,	1),
(4,	'1.3',	'insert bus',	'SQL',	'V1.3__insert_bus.sql',	868028641,	'root',	'2022-07-05 22:29:42',	2,	1),
(5,	'1.4',	'insert stop',	'SQL',	'V1.4__insert_stop.sql',	-1966689752,	'root',	'2022-07-05 22:29:42',	3,	1),
(6,	'1.5',	'insert trip',	'SQL',	'V1.5__insert_trip.sql',	0,	'root',	'2022-07-05 22:29:42',	1,	1);

DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

INSERT INTO `roles` (`id`, `name`) VALUES
(1,	'ROLE_USER'),
(2,	'ROLE_ADMIN');

DROP TABLE IF EXISTS `stop`;
CREATE TABLE `stop` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL,
  `detail` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

INSERT INTO `stop` (`id`, `code`, `detail`, `name`) VALUES
(1,	'1-2',	'Teluk',	'Karang'),
(2,	'1-8',	'Cimeng',	'Sukaraja 1-8'),
(3,	'1-3',	'Raja BasaI',	'Kedaton 1-3'),
(4,	'1-2',	'Sukabumi',	'Sukarame 1-2'),
(5,	'1-10',	'Tanjung Senang',	'Jati Agung 1-10');

DROP TABLE IF EXISTS `ticket`;
CREATE TABLE `ticket` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cancellable` bit(1) DEFAULT NULL,
  `journey_date` varchar(255) DEFAULT NULL,
  `seat_number` int(11) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `trip_schedule_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKdvt57mcco3ogsosi97odw563o` (`user_id`),
  KEY `FKfhudhsxbslgtmbrbkd3uak4ha` (`trip_schedule_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;


DROP TABLE IF EXISTS `trip`;
CREATE TABLE `trip` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fare` int(11) NOT NULL,
  `journey_time` int(11) NOT NULL,
  `agency_id` bigint(20) DEFAULT NULL,
  `bus_id` bigint(20) DEFAULT NULL,
  `dest_stop_id` bigint(20) DEFAULT NULL,
  `source_stop_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKab03gksoo5t3lo12ga3ixnykk` (`agency_id`),
  KEY `FKptvi61dd1hao1yig3in0gvcjs` (`bus_id`),
  KEY `FK1evbxrekvflflkfscj2i0fwv0` (`dest_stop_id`),
  KEY `FK5ln8w8n974euslk976dh6x7k5` (`source_stop_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

INSERT INTO `trip` (`id`, `fare`, `journey_time`, `agency_id`, `bus_id`, `dest_stop_id`, `source_stop_id`) VALUES
(1,	300000,	1,	1,	2,	1,	2),
(2,	300000,	12,	1,	2,	1,	2),
(3,	300000,	12,	1,	1,	1,	2),
(4,	300000,	1,	1,	1,	1,	2),
(5,	300000,	1,	1,	1,	2,	1);

DROP TABLE IF EXISTS `trip_schedule`;
CREATE TABLE `trip_schedule` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `available_seats` int(11) NOT NULL,
  `trip_date` varchar(255) DEFAULT NULL,
  `trip_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKaqjflucdvoypakmjxtb7n2mmm` (`trip_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

INSERT INTO `trip_schedule` (`id`, `available_seats`, `trip_date`, `trip_id`) VALUES
(4,	20,	'12 Desember',	2),
(3,	20,	'12 Desember',	2);

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(50) DEFAULT NULL,
  `first_name` varchar(120) DEFAULT NULL,
  `last_name` varchar(120) DEFAULT NULL,
  `mobile_number` varchar(120) DEFAULT NULL,
  `password` varchar(120) DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKsb8bbouer5wak8vyiiy4pf2bx` (`username`),
  UNIQUE KEY `UKob8kqyqqgmefl0aco34akdtpe` (`email`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

INSERT INTO `user` (`id`, `email`, `first_name`, `last_name`, `mobile_number`, `password`, `username`) VALUES
(3,	'admintwo@gmail.com',	'adminn',	'one',	'081112344444',	'$2a$10$KqpmMvdisubwDQwpRggU5.va0Gx6JtmZZ4.5t5oJ03TnVcaNBUiuG',	'admintwo');

DROP TABLE IF EXISTS `user_roles`;
CREATE TABLE `user_roles` (
  `user_id` bigint(20) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKh8ciramu9cc9q3qcqiv4ue8a6` (`role_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

INSERT INTO `user_roles` (`user_id`, `role_id`) VALUES
(1,	1),
(2,	1),
(3,	2);

-- 2022-07-07 10:42:47
