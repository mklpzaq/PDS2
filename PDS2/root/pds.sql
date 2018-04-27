-- --------------------------------------------------------
-- 호스트:                          127.0.0.1
-- 서버 버전:                        5.5.32 - MySQL Community Server (GPL)
-- 서버 OS:                        Win32
-- HeidiSQL 버전:                  8.0.0.4396
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- pds 의 데이터베이스 구조 덤핑
CREATE DATABASE IF NOT EXISTS `pds` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `pds`;


-- 테이블 pds의 구조를 덤프합니다. article
CREATE TABLE IF NOT EXISTS `article` (
  `article_id` int(10) NOT NULL AUTO_INCREMENT,
  `article_title` varchar(50) NOT NULL,
  `article_content` varchar(50) NOT NULL,
  PRIMARY KEY (`article_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 내보낼 데이터가 선택되어 있지 않습니다.


-- 테이블 pds의 구조를 덤프합니다. article_file
CREATE TABLE IF NOT EXISTS `article_file` (
  `article_file_id` int(10) NOT NULL AUTO_INCREMENT,
  `article_id` int(10) NOT NULL DEFAULT '0',
  `article_file_name` varchar(500) NOT NULL,
  `article_file_ext` varchar(50) NOT NULL,
  `article_file_type` varchar(500) NOT NULL,
  `article_file_size` int(10) NOT NULL,
  PRIMARY KEY (`article_file_id`),
  KEY `FK_article_file_article` (`article_id`),
  CONSTRAINT `FK_article_file_article` FOREIGN KEY (`article_id`) REFERENCES `article` (`article_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 내보낼 데이터가 선택되어 있지 않습니다.


-- 테이블 pds의 구조를 덤프합니다. board
CREATE TABLE IF NOT EXISTS `board` (
  `board_id` int(10) NOT NULL AUTO_INCREMENT,
  `board_title` varchar(50) DEFAULT NULL,
  `board_content` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`board_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 내보낼 데이터가 선택되어 있지 않습니다.


-- 테이블 pds의 구조를 덤프합니다. board_file
CREATE TABLE IF NOT EXISTS `board_file` (
  `board_file_id` int(10) NOT NULL AUTO_INCREMENT,
  `board_id` int(10) DEFAULT NULL,
  `board_file_name` varchar(500) DEFAULT NULL,
  `board_file_ext` varchar(50) DEFAULT NULL,
  `board_file_type` varchar(500) DEFAULT NULL,
  `board_file_size` int(10) DEFAULT NULL,
  PRIMARY KEY (`board_file_id`),
  KEY `FK_board_file_board` (`board_id`),
  CONSTRAINT `FK_board_file_board` FOREIGN KEY (`board_id`) REFERENCES `board` (`board_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 내보낼 데이터가 선택되어 있지 않습니다.


-- 테이블 pds의 구조를 덤프합니다. gallery
CREATE TABLE IF NOT EXISTS `gallery` (
  `gallery_id` int(10) NOT NULL AUTO_INCREMENT,
  `gallery_title` varchar(50) DEFAULT NULL,
  `gallery_content` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`gallery_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 내보낼 데이터가 선택되어 있지 않습니다.


-- 테이블 pds의 구조를 덤프합니다. gallery_file
CREATE TABLE IF NOT EXISTS `gallery_file` (
  `gallery_file_id` int(10) NOT NULL AUTO_INCREMENT,
  `gallery_id` int(10) DEFAULT NULL,
  `gallery_file_name` varchar(500) DEFAULT NULL,
  `gallery_file_ext` varchar(50) DEFAULT NULL,
  `gallery_file_type` varchar(500) DEFAULT NULL,
  `gallery_file_size` int(10) DEFAULT NULL,
  PRIMARY KEY (`gallery_file_id`),
  KEY `FK_gallery_file_gallery` (`gallery_id`),
  CONSTRAINT `FK_gallery_file_gallery` FOREIGN KEY (`gallery_id`) REFERENCES `gallery` (`gallery_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 내보낼 데이터가 선택되어 있지 않습니다.


-- 테이블 pds의 구조를 덤프합니다. notice
CREATE TABLE IF NOT EXISTS `notice` (
  `notice_id` int(10) NOT NULL AUTO_INCREMENT,
  `notice_title` varchar(50) DEFAULT NULL,
  `notice_content` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`notice_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 내보낼 데이터가 선택되어 있지 않습니다.


-- 테이블 pds의 구조를 덤프합니다. notice_file
CREATE TABLE IF NOT EXISTS `notice_file` (
  `notice_file_id` int(10) NOT NULL AUTO_INCREMENT,
  `notice_id` int(50) DEFAULT NULL,
  `notice_file_name` varchar(500) DEFAULT NULL,
  `notice_file_ext` varchar(50) DEFAULT NULL,
  `notice_file_type` varchar(500) DEFAULT NULL,
  `notice_file_size` int(10) DEFAULT NULL,
  PRIMARY KEY (`notice_file_id`),
  KEY `FK_notice_file_notice` (`notice_id`),
  CONSTRAINT `FK_notice_file_notice` FOREIGN KEY (`notice_id`) REFERENCES `notice` (`notice_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 내보낼 데이터가 선택되어 있지 않습니다.


-- 테이블 pds의 구조를 덤프합니다. resume
CREATE TABLE IF NOT EXISTS `resume` (
  `resume_id` int(10) NOT NULL AUTO_INCREMENT,
  `resume_title` varchar(50) DEFAULT NULL,
  `resume_content` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`resume_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 내보낼 데이터가 선택되어 있지 않습니다.


-- 테이블 pds의 구조를 덤프합니다. resume_file
CREATE TABLE IF NOT EXISTS `resume_file` (
  `resume_file_id` int(10) NOT NULL AUTO_INCREMENT,
  `resume_id` int(50) DEFAULT NULL,
  `resume_file_name` varchar(500) DEFAULT NULL,
  `resume_file_ext` varchar(50) DEFAULT NULL,
  `resume_file_type` varchar(500) DEFAULT NULL,
  `resume_file_size` int(10) DEFAULT NULL,
  PRIMARY KEY (`resume_file_id`),
  KEY `FK_resume_file_resume` (`resume_id`),
  CONSTRAINT `FK_resume_file_resume` FOREIGN KEY (`resume_id`) REFERENCES `resume` (`resume_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 내보낼 데이터가 선택되어 있지 않습니다.
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
