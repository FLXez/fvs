-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Erstellungszeit: 22. Mai 2020 um 13:37
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
(1, 50, 'H'),
(2, 50, 'R'),
(3, 20, 'H'),
(4, 20, 'R'),
(5, 80, 'H'),
(6, 80, 'R'),
(7, 100, 'H'),
(8, 100, 'R'),
(9, 64, 'H'),
(10, 64, 'R'),
(11, 72, 'H'),
(12, 72, 'R'),
(13, 90, 'H'),
(14, 90, 'R'),
(15, 30, 'H'),
(16, 30, 'R');

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
(1, 1, 1, 10, '12:20'),
(2, 1, 2, 8, '10:10'),
(3, 1, 8, 10, '15:30'),
(4, 2, 10, 1, '15:30'),
(5, 2, 7, 3, '12:20'),
(6, 3, 6, 7, '07:00'),
(7, 3, 4, 6, '12:00'),
(8, 3, 3, 7, '10:10'),
(9, 4, 7, 4, '18:20'),
(10, 4, 7, 3, '13:00'),
(11, 4, 6, 4, '12:10'),
(12, 4, 5, 4, '11:11'),
(13, 5, 10, 3, '16:44'),
(14, 5, 10, 7, '12:21'),
(15, 5, 9, 3, '14:59'),
(16, 6, 3, 1, '12:30'),
(17, 6, 3, 7, '14:10'),
(18, 6, 2, 1, '23:50'),
(19, 6, 7, 2, '12:10');

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
(1, 'Hannover, Hauptbahnhof'),
(2, 'Hannover, Steintor'),
(3, 'Hameln, Bahnhof'),
(4, 'Münster, Hauptbahnhof'),
(5, 'Münster, Nienberge'),
(6, 'Münster, Häger'),
(7, 'Hameln, HSW'),
(8, 'Hameln, Stadtgalerie'),
(9, 'Voldagsen, Bahnhof'),
(10, 'Elze, Bahnhof');

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
(1, 1, 2, 1, 0),
(2, 1, 2, 2, 1),
(3, 1, 2, 3, 2),
(4, 1, 2, 4, 3),
(5, 1, 2, 5, 4),
(6, 1, 2, 6, 5),
(7, 3, 4, 7, 0),
(8, 3, 4, 8, 1),
(9, 3, 4, 9, 2),
(10, 3, 4, 10, 3),
(11, 3, 4, 11, 4),
(12, 3, 4, 12, 5),
(13, 5, 6, 1, 0),
(14, 5, 6, 13, 1),
(15, 5, 6, 14, 2),
(16, 5, 6, 15, 3),
(17, 5, 6, 16, 4),
(18, 5, 6, 17, 5);

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
(1, 1, 2, 5),
(2, 2, 3, 30),
(3, 3, 7, 7),
(4, 7, 8, 5),
(5, 8, 9, 40),
(6, 9, 10, 20),
(7, 4, 5, 6),
(8, 5, 6, 13),
(9, 6, 3, 55),
(10, 3, 1, 2),
(11, 1, 8, 6),
(12, 8, 7, 3),
(13, 2, 10, 65),
(14, 10, 9, 32),
(15, 9, 7, 6),
(16, 7, 5, 1),
(17, 5, 3, 50);

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
  MODIFY `bid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;
--
-- AUTO_INCREMENT für Tabelle `fahrt`
--
ALTER TABLE `fahrt`
  MODIFY `fid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;
--
-- AUTO_INCREMENT für Tabelle `haltestelle`
--
ALTER TABLE `haltestelle`
  MODIFY `hid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
--
-- AUTO_INCREMENT für Tabelle `linienabfolge`
--
ALTER TABLE `linienabfolge`
  MODIFY `lid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;
--
-- AUTO_INCREMENT für Tabelle `user`
--
ALTER TABLE `user`
  MODIFY `uid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT für Tabelle `verbindung`
--
ALTER TABLE `verbindung`
  MODIFY `vid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;
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
