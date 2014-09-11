# MySQL schema

CREATE TABLE `role` (
  `authority` varchar(40) NOT NULL,
  PRIMARY KEY (`authority`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `user` (
  `username` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `verify_code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`username`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `mobile` (`mobile`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `user_role` (
  `user_username` varchar(255) NOT NULL,
  `roles_authority` varchar(40) NOT NULL,
  PRIMARY KEY (`user_username`,`roles_authority`),
  KEY (`user_username`),
  KEY (`roles_authority`),
  CONSTRAINT FOREIGN KEY (`roles_authority`) REFERENCES `role` (`authority`),
  CONSTRAINT FOREIGN KEY (`user_username`) REFERENCES `user` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `location` (
  `location_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) NOT NULL,
  `city` varchar(255) NOT NULL,
  `country` varchar(255) NOT NULL,
  `currency` varchar(3) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `price` decimal(4,2) DEFAULT NULL,
  `state` varchar(255) NOT NULL,
  `zip` varchar(255) NOT NULL,
  PRIMARY KEY (`location_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `payment_card` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `county` varchar(255) DEFAULT NULL,
  `expiry_month` int(11) NOT NULL,
  `expiry_year` int(11) NOT NULL,
  `number` varchar(255) DEFAULT NULL,
  `postal_code` varchar(255) DEFAULT NULL,
  `preferred` bit(1) NOT NULL,
  `state` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `user` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY (`user`),
  CONSTRAINT FOREIGN KEY (`user`) REFERENCES `user` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `vehicle` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `brand` varchar(255) NOT NULL,
  `color` varchar(255) NOT NULL,
  `license` varchar(255) DEFAULT NULL,
  `preferred` bit(1) NOT NULL,
  `user` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY (`user`),
  CONSTRAINT FOREIGN KEY (`user`) REFERENCES `user` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `booking` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `confirmation` bit(1) NOT NULL,
  `end_date` datetime DEFAULT NULL,
  `formatted_id` varchar(255) DEFAULT NULL,
  `reminder` bit(1) NOT NULL,
  `start_date` datetime DEFAULT NULL,
  `card` bigint(20) DEFAULT NULL,
  `location` bigint(20) DEFAULT NULL,
  `user` varchar(255) DEFAULT NULL,
  `vehicle` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY (`vehicle`),
  KEY (`location`),
  KEY (`card`),
  KEY (`user`),
  CONSTRAINT FOREIGN KEY (`user`) REFERENCES `user` (`username`),
  CONSTRAINT FOREIGN KEY (`location`) REFERENCES `location` (`location_id`),
  CONSTRAINT FOREIGN KEY (`vehicle`) REFERENCES `vehicle` (`id`),
  CONSTRAINT FOREIGN KEY (`card`) REFERENCES `payment_card` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `news` (
  `news_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `active` bit(1) NOT NULL,
  `content` varchar(255) NOT NULL,
  `end_date` datetime DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `title` varchar(255) NOT NULL,
  PRIMARY KEY (`news_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
