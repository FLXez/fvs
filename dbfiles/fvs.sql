-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Erstellungszeit: 21. Mai 2020 um 19:05
-- Server-Version: 10.1.21-MariaDB
-- PHP-Version: 7.1.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Datenbank: `fvs`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `buslinie`
--

CREATE TABLE `buslinie` (
  `bid` int(11) NOT NULL,
  `nummer` int(11) NOT NULL,
  `Richtung` enum('H','R') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `buslinie`
--

INSERT INTO `buslinie` (`bid`, `nummer`, `Richtung`) VALUES
(1, 12, 'H'),
(2, 12, 'R'),
(3, 14, 'H'),
(4, 14, 'R'),
(5, 41, 'H'),
(6, 41, 'R'),
(7, 55, 'H'),
(8, 55, 'R'),
(9, 20, 'H'),
(10, 20, 'R'),
(11, 666, 'H'),
(12, 666, 'R'),
(13, 25, 'H'),
(14, 25, 'R'),
(15, 15, 'H'),
(16, 15, 'R'),
(17, 11, 'H'),
(18, 11, 'R'),
(19, 56, 'H'),
(20, 56, 'R');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `fahrt`
--

CREATE TABLE `fahrt` (
  `fid` int(11) NOT NULL,
  `bid` int(11) NOT NULL,
  `hidS` int(11) NOT NULL,
  `hidE` int(11) NOT NULL,
  `uhrzeit` varchar(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `fahrt`
--

INSERT INTO `fahrt` (`fid`, `bid`, `hidS`, `hidE`, `uhrzeit`) VALUES
(11, 1, 7, 5, '12:20'),
(12, 2, 5, 7, '15:30'),
(13, 1, 4, 9, '15:30'),
(14, 1, 7, 6, '10:10'),
(15, 1, 7, 9, '12:20'),
(16, 1, 7, 9, '13:00'),
(17, 1, 6, 2, '10:10'),
(18, 11, 10, 3, '15:30');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `haltestelle`
--

CREATE TABLE `haltestelle` (
  `hid` int(11) NOT NULL,
  `bezeichnung` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `haltestelle`
--

INSERT INTO `haltestelle` (`hid`, `bezeichnung`) VALUES
(1, 'Hannover, Steintor'),
(2, 'Hannover, Hauptbahnhof'),
(3, 'Hannover, Silas Home'),
(4, 'tbahnhof'),
(5, 'test'),
(6, 'Irgendwo'),
(7, 'Ich brauch noch eine für fix'),
(8, 'Julian'),
(9, 'tester'),
(10, 'test12'),
(11, 'test34');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `linienabfolge`
--

CREATE TABLE `linienabfolge` (
  `lid` int(11) NOT NULL,
  `bidh` int(11) NOT NULL,
  `bidr` int(11) NOT NULL,
  `vid` int(11) NOT NULL,
  `position` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `linienabfolge`
--

INSERT INTO `linienabfolge` (`lid`, `bidh`, `bidr`, `vid`, `position`) VALUES
(56, 1, 2, 92, 0),
(57, 1, 2, 93, 1),
(58, 1, 2, 94, 2),
(59, 1, 2, 95, 3),
(60, 1, 2, 96, -1),
(61, 1, 2, 97, -2),
(62, 1, 2, 98, 4),
(63, 1, 2, 99, 5),
(65, 5, 6, 104, 0),
(66, 5, 6, 105, 1),
(67, 5, 6, 106, 2),
(68, 5, 6, 107, 3),
(69, 11, 12, 108, 0),
(70, 11, 12, 109, 1),
(71, 11, 12, 93, 2);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `user`
--

CREATE TABLE `user` (
  `uid` int(11) NOT NULL,
  `vorname` varchar(25) NOT NULL,
  `name` varchar(25) NOT NULL,
  `email` varchar(320) NOT NULL,
  `passwort` varchar(255) NOT NULL,
  `privilegien` enum('Mitarbeiter','Manager','Admin') NOT NULL DEFAULT 'Mitarbeiter'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `user`
--

INSERT INTO `user` (`uid`, `vorname`, `name`, `email`, `passwort`, `privilegien`) VALUES
(1, 'Admin', 'Admin', 'admin@admin.it', '', 'Admin'),
(2, 'Manager', 'Test', 'manager@test.fvs', 'manager', 'Manager'),
(3, 'Mitarbeiter', 'Test', 'mitarbeiter@test.fvs', 'mitarbeiter', 'Mitarbeiter');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `verbindung`
--

CREATE TABLE `verbindung` (
  `vid` int(11) NOT NULL,
  `hidS` int(11) NOT NULL,
  `hidE` int(11) DEFAULT NULL,
  `dauer` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `verbindung`
--

INSERT INTO `verbindung` (`vid`, `hidS`, `hidE`, `dauer`) VALUES
(92, 1, 2, 2),
(93, 2, 3, 9),
(94, 3, 4, 5),
(95, 4, 5, 30),
(96, 6, 1, 3),
(97, 7, 6, 3),
(98, 5, 8, 11),
(99, 8, 9, 5),
(100, 5, 2, 3),
(101, 3, 2, 8),
(102, 2, 1, 0),
(104, 1, 6, 0),
(105, 6, 2, 0),
(106, 2, 8, 0),
(107, 8, 5, 0),
(108, 10, 11, 0),
(109, 11, 2, 0);

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `buslinie`
--
ALTER TABLE `buslinie`
  ADD PRIMARY KEY (`bid`);

--
-- Indizes für die Tabelle `fahrt`
--
ALTER TABLE `fahrt`
  ADD PRIMARY KEY (`fid`),
  ADD KEY `fkey_f_hids` (`hidS`),
  ADD KEY `fkey_f_bid` (`bid`),
  ADD KEY `fkey_f_hide` (`hidE`);

--
-- Indizes für die Tabelle `haltestelle`
--
ALTER TABLE `haltestelle`
  ADD PRIMARY KEY (`hid`);

--
-- Indizes für die Tabelle `linienabfolge`
--
ALTER TABLE `linienabfolge`
  ADD PRIMARY KEY (`lid`),
  ADD KEY `fkey_l_fid` (`bidh`),
  ADD KEY `fkey_l_vid` (`vid`),
  ADD KEY `fkey_l_bidr` (`bidr`);

--
-- Indizes für die Tabelle `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`uid`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indizes für die Tabelle `verbindung`
--
ALTER TABLE `verbindung`
  ADD PRIMARY KEY (`vid`),
  ADD KEY `fkey_v_hids` (`hidS`),
  ADD KEY `fkey_v_hide` (`hidE`);

--
-- AUTO_INCREMENT für exportierte Tabellen
--

--
-- AUTO_INCREMENT für Tabelle `buslinie`
--
ALTER TABLE `buslinie`
  MODIFY `bid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;
--
-- AUTO_INCREMENT für Tabelle `fahrt`
--
ALTER TABLE `fahrt`
  MODIFY `fid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;
--
-- AUTO_INCREMENT für Tabelle `haltestelle`
--
ALTER TABLE `haltestelle`
  MODIFY `hid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;
--
-- AUTO_INCREMENT für Tabelle `linienabfolge`
--
ALTER TABLE `linienabfolge`
  MODIFY `lid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=72;
--
-- AUTO_INCREMENT für Tabelle `user`
--
ALTER TABLE `user`
  MODIFY `uid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT für Tabelle `verbindung`
--
ALTER TABLE `verbindung`
  MODIFY `vid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=110;
--
-- Constraints der exportierten Tabellen
--

--
-- Constraints der Tabelle `fahrt`
--
ALTER TABLE `fahrt`
  ADD CONSTRAINT `fkey_f_bid` FOREIGN KEY (`bid`) REFERENCES `buslinie` (`bid`),
  ADD CONSTRAINT `fkey_f_hide` FOREIGN KEY (`hidE`) REFERENCES `haltestelle` (`hid`),
  ADD CONSTRAINT `fkey_f_hids` FOREIGN KEY (`hidS`) REFERENCES `haltestelle` (`hid`);

--
-- Constraints der Tabelle `linienabfolge`
--
ALTER TABLE `linienabfolge`
  ADD CONSTRAINT `fkey_l_bidh` FOREIGN KEY (`bidh`) REFERENCES `buslinie` (`bid`),
  ADD CONSTRAINT `fkey_l_bidr` FOREIGN KEY (`bidr`) REFERENCES `buslinie` (`bid`),
  ADD CONSTRAINT `fkey_l_vid` FOREIGN KEY (`vid`) REFERENCES `verbindung` (`vid`);

--
-- Constraints der Tabelle `verbindung`
--
ALTER TABLE `verbindung`
  ADD CONSTRAINT `fkey_v_hide` FOREIGN KEY (`hidE`) REFERENCES `haltestelle` (`hid`),
  ADD CONSTRAINT `fkey_v_hids` FOREIGN KEY (`hidS`) REFERENCES `haltestelle` (`hid`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
