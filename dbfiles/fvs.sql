-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Erstellungszeit: 16. Mai 2020 um 02:31
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
(6, 41, 'R');

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
(5, 'test');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `linienabfolge`
--

CREATE TABLE `linienabfolge` (
  `lid` int(11) NOT NULL,
  `bid` int(11) NOT NULL,
  `vid` int(11) NOT NULL,
  `position` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

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
  `hidE` int(11) NOT NULL,
  `dauer` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `verbindung`
--

INSERT INTO `verbindung` (`vid`, `hidS`, `hidE`, `dauer`) VALUES
(3, 1, 3, 54),
(4, 3, 2, 2);

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
  ADD KEY `fkey_l_fid` (`bid`),
  ADD KEY `fkey_l_vid` (`vid`);

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
  MODIFY `bid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT für Tabelle `fahrt`
--
ALTER TABLE `fahrt`
  MODIFY `fid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT für Tabelle `haltestelle`
--
ALTER TABLE `haltestelle`
  MODIFY `hid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT für Tabelle `linienabfolge`
--
ALTER TABLE `linienabfolge`
  MODIFY `lid` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT für Tabelle `user`
--
ALTER TABLE `user`
  MODIFY `uid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
--
-- AUTO_INCREMENT für Tabelle `verbindung`
--
ALTER TABLE `verbindung`
  MODIFY `vid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
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
  ADD CONSTRAINT `fkey_l_bid` FOREIGN KEY (`bid`) REFERENCES `buslinie` (`bid`),
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
