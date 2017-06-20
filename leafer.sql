CREATE TABLE `user` (
  `id` bigint(64) unsigned NOT NULL,
  `username` varchar(20) NOT NULL,
  `password` varchar(64) NOT NULL,
  `is_enabled` tinyint(1) NOT NULL DEFAULT '1',
  `is_male` tinyint(1) DEFAULT NULL,
  `email` varchar(32) DEFAULT NULL,
  `bio` varchar(140) DEFAULT NULL,
  `blog` varchar(32) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `birthday` datetime DEFAULT NULL,
  `reset_code` varchar(6) DEFAULT NULL,
  `reset_empire_time` varchar(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;


CREATE TABLE `article` (
  `id` bigint(64) unsigned NOT NULL,
  `title` varchar(100) DEFAULT NULL,
  `body` mediumtext,
  `created_time` datetime DEFAULT NULL,
  `modified_time` datetime DEFAULT NULL,
  `username` varchar(20) DEFAULT NULL,
  `is_published` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_username` (`username`),
  CONSTRAINT `fk_article_user_username` FOREIGN KEY (`username`) REFERENCES `user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tag` (
  `id` bigint(64) unsigned NOT NULL,
  `name` varchar(20) NOT NULL,
  `description` varchar(32) DEFAULT NULL,
  `username` varchar(20) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `modified_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_username` (`username`),
  CONSTRAINT `fk_tag_user_username` FOREIGN KEY (`username`) REFERENCES `user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `article_tag` (
  `article_id` bigint(64) unsigned NOT NULL,
  `tag_id` bigint(64) unsigned NOT NULL,
  PRIMARY KEY (`article_id`,`tag_id`),
  KEY `fk_article_tag_tag_tag_id` (`tag_id`),
  CONSTRAINT `fk_article_tag_article_article_id` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_article_tag_tag_tag_id` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=ascii;

CREATE TABLE `role` (
  `id` bigint(64) unsigned NOT NULL,
  `username` varchar(20) NOT NULL,
  `role` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`),
  CONSTRAINT `fk_user_role_user_username` FOREIGN KEY (`username`) REFERENCES `user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `persistent_logins` (
  `username` varchar(64) NOT NULL,
  `series` varchar(64) NOT NULL,
  `token` varchar(64) NOT NULL,
  `last_used` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`series`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO role(id, username, role) values(1, 'admin', 'ROLE_ADMIN');
INSERT INTO user(id, username, password) VALUES(1, 'admin', '$2a$10$MDSRJE0SGzRWWMRdfZVq2.yfYOBQ8Y/6UMgONnmrzKMJbcB/Wi7i6');
