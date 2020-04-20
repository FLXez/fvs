-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Erstellungszeit: 11. Mrz 2020 um 10:47
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
-- Tabellenstruktur für Tabelle `ablauf`
--

CREATE TABLE `ablauf` (
  `a_id` int(11) NOT NULL,
  `f_id` int(11) NOT NULL,
  `v_id` int(11) NOT NULL,
  `position` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `ablauf`
--

INSERT INTO `ablauf` (`a_id`, `f_id`, `v_id`, `position`) VALUES
(1, 1, 1, 1);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `buslinie`
--

CREATE TABLE `buslinie` (
  `b_id` int(11) NOT NULL,
  `nummer` int(11) NOT NULL,
  `richtung` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `buslinie`
--

INSERT INTO `buslinie` (`b_id`, `nummer`, `richtung`) VALUES
(1, 42, 'Hannover Hauptbahnhof');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `fahrplan`
--

CREATE TABLE `fahrplan` (
  `f_id` int(11) NOT NULL,
  `b_id` int(11) DEFAULT NULL,
  `wochentag` varchar(20) NOT NULL,
  `uhrzeit` time NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `fahrplan`
--

INSERT INTO `fahrplan` (`f_id`, `b_id`, `wochentag`, `uhrzeit`) VALUES
(1, 1, 'mo,di,mi,do,fr', '10:30:00');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `haltestelle`
--

CREATE TABLE `haltestelle` (
  `h_id` int(11) NOT NULL,
  `bezeichnung` varchar(255) NOT NULL,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `haltestelle`
--

INSERT INTO `haltestelle` (`h_id`, `bezeichnung`, `latitude`, `longitude`) VALUES
(1, 'Hannover, Kröpcke Uhr', 52.374427, 9.738857),
(2, 'Hannover, Hauptbahnhof', 52.375688, 9.74116);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `user`
--

CREATE TABLE `user` (
  `u_id` int(11) NOT NULL,
  `vorname` varchar(25) NOT NULL,
  `name` varchar(25) NOT NULL,
  `email` varchar(320) NOT NULL,
  `passwort` varchar(255) NOT NULL,
  `privilegien` enum('Mitarbeiter','Manager') NOT NULL DEFAULT 'Mitarbeiter'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `user`
--

INSERT INTO `user` (`u_id`, `vorname`, `name`, `email`, `passwort`, `privilegien`) VALUES
(1, 'Admin', 'Admin', 'admin@admin.it', '', 'Manager');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `verbindung`
--

CREATE TABLE `verbindung` (
  `v_id` int(11) NOT NULL,
  `h_id_start` int(11) NOT NULL,
  `h_id_ende` int(11) NOT NULL,
  `dauer` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `verbindung`
--

INSERT INTO `verbindung` (`v_id`, `h_id_start`, `h_id_ende`, `dauer`) VALUES
(1, 1, 2, 10);

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `ablauf`
--
ALTER TABLE `ablauf`
  ADD PRIMARY KEY (`a_id`),
  ADD KEY `fkey_fid` (`f_id`),
  ADD KEY `fkey_vid` (`v_id`);

--
-- Indizes für die Tabelle `buslinie`
--
ALTER TABLE `buslinie`
  ADD PRIMARY KEY (`b_id`);

--
-- Indizes für die Tabelle `fahrplan`
--
ALTER TABLE `fahrplan`
  ADD PRIMARY KEY (`f_id`),
  ADD KEY `fkey_bid` (`b_id`);

--
-- Indizes für die Tabelle `haltestelle`
--
ALTER TABLE `haltestelle`
  ADD PRIMARY KEY (`h_id`);

--
-- Indizes für die Tabelle `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`u_id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indizes für die Tabelle `verbindung`
--
ALTER TABLE `verbindung`
  ADD PRIMARY KEY (`v_id`),
  ADD KEY `fkey_hid_start` (`h_id_start`),
  ADD KEY `fkey_hid_ende` (`h_id_ende`);

--
-- AUTO_INCREMENT für exportierte Tabellen
--

--
-- AUTO_INCREMENT für Tabelle `ablauf`
--
ALTER TABLE `ablauf`
  MODIFY `a_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT für Tabelle `buslinie`
--
ALTER TABLE `buslinie`
  MODIFY `b_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT für Tabelle `fahrplan`
--
ALTER TABLE `fahrplan`
  MODIFY `f_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT für Tabelle `haltestelle`
--
ALTER TABLE `haltestelle`
  MODIFY `h_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT für Tabelle `user`
--
ALTER TABLE `user`
  MODIFY `u_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT für Tabelle `verbindung`
--
ALTER TABLE `verbindung`
  MODIFY `v_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- Constraints der exportierten Tabellen
--

--
-- Constraints der Tabelle `ablauf`
--
ALTER TABLE `ablauf`
  ADD CONSTRAINT `fkey_fid` FOREIGN KEY (`f_id`) REFERENCES `fahrplan` (`f_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `fkey_vid` FOREIGN KEY (`v_id`) REFERENCES `verbindung` (`v_id`);

--
-- Constraints der Tabelle `fahrplan`
--
ALTER TABLE `fahrplan`
  ADD CONSTRAINT `fkey_bid` FOREIGN KEY (`b_id`) REFERENCES `buslinie` (`b_id`) ON DELETE SET NULL;

--
-- Constraints der Tabelle `verbindung`
--
ALTER TABLE `verbindung`
  ADD CONSTRAINT `fkey_hid_ende` FOREIGN KEY (`h_id_ende`) REFERENCES `haltestelle` (`h_id`),
  ADD CONSTRAINT `fkey_hid_start` FOREIGN KEY (`h_id_start`) REFERENCES `haltestelle` (`h_id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
