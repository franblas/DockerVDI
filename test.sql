-- phpMyAdmin SQL Dump
-- version 4.1.13
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Nov 11, 2014 at 09:35 AM
-- Server version: 5.5.35-0ubuntu0.12.04.2
-- PHP Version: 5.3.10-1ubuntu3.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `test`
--

-- --------------------------------------------------------

--
-- Table structure for table `application`
--

CREATE TABLE IF NOT EXISTS `application` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `appliname` text NOT NULL,
  `display` tinyint(1) NOT NULL,
  `sound` tinyint(1) NOT NULL,
  `graph` tinyint(1) NOT NULL,
  `desktopgrp` int(11) NOT NULL,
  `gamegrp` int(11) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `application`
--

INSERT INTO `application` (`ID`, `appliname`, `display`, `sound`, `graph`, `desktopgrp`, `gamegrp`) VALUES
(1, 'supertux2', 1, 1, 1, 0, 1),
(2, 'stellarium', 1, 0, 0, 1, 0),
(3, 'geogebra', 1, 0, 0, 1, 0),
(4, 'libreoffice', 1, 0, 0, 1, 0);

-- --------------------------------------------------------

--
-- Table structure for table `usergroup`
--

CREATE TABLE IF NOT EXISTS `usergroup` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `user` text NOT NULL,
  `group` text NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `usergroup`
--

INSERT INTO `usergroup` (`ID`, `user`, `group`) VALUES
(1, 'paco', 'desktopgrp'),
(2, 'paco', 'gamegrp'),
(3, 'paul', 'desktopgrp');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `user` text NOT NULL,
  `password` text NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`ID`, `user`, `password`) VALUES
(1, 'paco', 'pacopaco'),
(2, 'paul', 'paulpaul');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;