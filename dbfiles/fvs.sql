-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Erstellungszeit: 17. Mai 2020 um 13:57
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
(8, 55, 'R');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `fahrt`
--

CREATE TABLE `fahrt` (
  `fid` int(11) NOT NULL,
  `bid` int(11) NOT NULL,
  `hidS` int(11) NOT NULL,
  `hidE` int(11) NOT NULL,
  `uhrzeit` time NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

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
(1, 'Hannover, KrÃ¶pcke Uhr'),
(2, 'Hannover, Hauptbahnhof'),
(3, 'Hannover, Silas Home'),
(4, 'tbahnhof'),
(5, 'test'),
(6, 'Irgendwo'),
(7, 'Ich brauch noch eine für fix');

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
(34, 1, 2, 74, 0),
(35, 1, 2, 75, 1),
(36, 1, 2, 76, -1),
(37, 3, 4, 77, 0),
(38, 3, 4, 78, 1),
(39, 3, 4, 79, 2),
(40, 3, 4, 80, 3),
(41, 5, 6, 77, 0),
(42, 5, 6, 81, -1),
(43, 5, 6, 82, -2),
(44, 5, 6, 83, -3),
(45, 5, 6, 84, -4),
(46, 5, 6, 85, -5);

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
(74, 1, 5, 8),
(75, 5, 7, 7),
(76, 6, 1, 10),
(77, 1, 4, 5),
(78, 4, 2, 5),
(79, 2, 7, 5),
(80, 7, 6, 5),
(81, 3, 1, 5),
(82, 4, 3, 5),
(83, 6, 4, 5),
(84, 4, 6, 5),
(85, 7, 4, 5);

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
  MODIFY `bid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
--
-- AUTO_INCREMENT für Tabelle `fahrt`
--
ALTER TABLE `fahrt`
  MODIFY `fid` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT für Tabelle `haltestelle`
--
ALTER TABLE `haltestelle`
  MODIFY `hid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT für Tabelle `linienabfolge`
--
ALTER TABLE `linienabfolge`
  MODIFY `lid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=47;
--
-- AUTO_INCREMENT für Tabelle `user`
--
ALTER TABLE `user`
  MODIFY `uid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT für Tabelle `verbindung`
--
ALTER TABLE `verbindung`
  MODIFY `vid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=86;
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
