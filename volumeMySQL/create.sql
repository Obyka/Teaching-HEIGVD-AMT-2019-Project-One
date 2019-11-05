CREATE DATABASE amtprojectone;
USE amtprojectone;


DROP TABLE IF EXISTS `User`;
CREATE TABLE `User` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Username` varchar(255) NOT NULL,
  `Password` varchar(255) NOT NULL,
  PRIMARY KEY (`ID`)
);

DROP TABLE IF EXISTS `Serie`;
CREATE TABLE Serie (
    ID int NOT NULL PRIMARY KEY AUTO_INCREMENT,    
    Title varchar(255),
    Producer varchar(255),
    Synopsis TEXT,
    Genre varchar(255),
	AgeRestriction INT,
	OwnerID INT,
	FOREIGN KEY (OwnerID) REFERENCES User(ID)
);

DROP TABLE IF EXISTS `Viewer`;
CREATE TABLE Viewer (
    ID int NOT NULL PRIMARY KEY AUTO_INCREMENT,    
    Firstname varchar(255) NOT NULL,
    Lastname varchar(255) NOT NULL,
    Username varchar(255) NOT NULL UNIQUE,
    Genre varchar(255),
    Birthdate DATE,
	OwnerID INT,
	FOREIGN KEY (OwnerID) REFERENCES User(ID)
	ON DELETE CASCADE
);

DROP TABLE IF EXISTS `WatchingInfo`;
CREATE TABLE WatchingInfo (
    IDSerie int NOT NULL,    
    IDViewer int NOT NULL,
    TimeSpent int NOT NULL,
    BeginningDate DATE,
	OwnerID INT,
    PRIMARY KEY (IDSerie, IDViewer),
	FOREIGN KEY (OwnerID) REFERENCES User(ID),
    FOREIGN KEY (IDSerie) REFERENCES Serie(ID) ON DELETE CASCADE,
    FOREIGN KEY (IDViewer) REFERENCES Viewer(ID) ON DELETE CASCADE
);


SET NAMES utf8;
SET time_zone = '+00:00';
SET foreign_key_checks = 0;
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';


INSERT INTO `User` (`ID`, `Username`, `Password`) VALUES
(1,	'Obyka',	'10000:45b9a33974cd7c95db8979abc625941cef4cf1db3d38a97e:4799f8dce2b2164542a9c981b5e28940e62bc92f78a837d5'),
(2,	'JoLaBanane98',	'10000:45b9a33974cd7c95db8979abc625941cef4cf1db3d38a97e:4799f8dce2b2164542a9c981b5e28940e62bc92f78a837d5');

