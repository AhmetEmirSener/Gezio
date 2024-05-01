-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Anamakine: 127.0.0.1
-- Üretim Zamanı: 29 Ara 2023, 20:33:27
-- Sunucu sürümü: 10.4.32-MariaDB
-- PHP Sürümü: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Veritabanı: `a3`
--

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `adminler`
--

CREATE TABLE `adminler` (
  `id` int(11) NOT NULL,
  `username` varchar(16) NOT NULL,
  `pass` varchar(16) NOT NULL,
  `son` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `adminler`
--

INSERT INTO `adminler` (`id`, `username`, `pass`, `son`) VALUES
(1, 'admin', '123', 0),
(2, 'ahmet', '123', 0),
(3, 'mehmet', '123', 0);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `biletler`
--

CREATE TABLE `biletler` (
  `biletid` int(11) NOT NULL,
  `bilettype` varchar(22) NOT NULL,
  `biletprice` double NOT NULL,
  `turid` int(11) NOT NULL,
  `musteriid` int(11) NOT NULL,
  `tasitid` int(11) NOT NULL,
  `personelid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `biletler`
--

INSERT INTO `biletler` (`biletid`, `bilettype`, `biletprice`, `turid`, `musteriid`, `tasitid`, `personelid`) VALUES
(3, 'Eco', 150.5, 1, 1, 1, 1),
(4, 'VIP', 150.5, 1, 1, 1, 1),
(5, 'Eco', 999.9, 1, 1, 1, 1),
(6, 'VIP', 11111, 1, 1, 1, 1),
(7, 'Eco', 11111, 1, 1, 1, 1),
(8, 'VIP', 11111, 1, 1, 1, 1);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `musteriler`
--

CREATE TABLE `musteriler` (
  `musteriid` int(11) NOT NULL,
  `musteriname` varchar(50) NOT NULL,
  `musterisurname` varchar(50) NOT NULL,
  `musterination` varchar(16) NOT NULL,
  `musteriemail` varchar(50) NOT NULL,
  `musteripass` varchar(16) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `musteriler`
--

INSERT INTO `musteriler` (`musteriid`, `musteriname`, `musterisurname`, `musterination`, `musteriemail`, `musteripass`) VALUES
(1, 'Yusuf', 'Şeker', 'Türk', 'ysf13@gmail.com', '123yyssff123'),
(3, 'Rıdvan', 'Tekke', 'Türk', 'tek61an@hotmail.com', '123456789'),
(4, 'Bayram', 'Akteke', 'Türk', 'tek61an@hotmail.com', '123456789');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `personeller`
--

CREATE TABLE `personeller` (
  `personelid` int(11) NOT NULL,
  `personelname` varchar(50) NOT NULL,
  `personelsurname` varchar(50) NOT NULL,
  `personelsalary` int(11) NOT NULL,
  `personelbirthdate` date NOT NULL,
  `personeljoindate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `personeller`
--

INSERT INTO `personeller` (`personelid`, `personelname`, `personelsurname`, `personelsalary`, `personelbirthdate`, `personeljoindate`) VALUES
(1, 'Afkan', 'Özdemir', 10000, '2003-12-29', '2020-12-05'),
(5, 'Mert', 'Calik', 5000, '2003-12-29', '2020-12-05'),
(6, 'Ahmet Mert', 'Calik', 5000, '2003-12-29', '2020-12-05');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `tasitlar`
--

CREATE TABLE `tasitlar` (
  `tasitid` int(11) NOT NULL,
  `tasittype` varchar(16) NOT NULL,
  `tasitplate` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `tasitlar`
--

INSERT INTO `tasitlar` (`tasitid`, `tasittype`, `tasitplate`) VALUES
(1, 'Car', '61ABC444'),
(2, 'Bus', 'dasas'),
(3, 'Car', 'dasas'),
(4, 'Bus', 'dgfshbsdg'),
(5, 'Car', 'dgfshbsdg'),
(6, 'Bus', '77HJH692'),
(7, 'Car', '34MRD687'),
(8, 'Bus', '81DSA692'),
(9, 'Bus', '81DSA692'),
(10, 'Bus', '44DSA692'),
(11, 'Bus', '44DSA692'),
(12, 'Car', '44DSA692'),
(13, 'Car', '44DSA692'),
(14, 'Bus', '44DSA692'),
(15, 'Bus', '44DSA692');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `turlar`
--

CREATE TABLE `turlar` (
  `turid` int(11) NOT NULL,
  `tasitid` int(11) NOT NULL,
  `harakettime` date NOT NULL,
  `destination` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `turlar`
--

INSERT INTO `turlar` (`turid`, `tasitid`, `harakettime`, `destination`) VALUES
(1, 2, '2024-01-23', 'Yalova'),
(2, 2, '2024-01-23', 'Kars'),
(3, 6, '2024-06-01', 'Samsun'),
(4, 3, '2023-12-30', 'Kars'),
(5, 2, '2024-01-23', 'Kars'),
(6, 2, '2024-01-23', 'Kars'),
(16, 4, '2024-01-23', 'Kars'),
(17, 4, '2024-01-23', 'Kars'),
(18, 4, '2024-01-23', 'Kars');

--
-- Dökümü yapılmış tablolar için indeksler
--

--
-- Tablo için indeksler `adminler`
--
ALTER TABLE `adminler`
  ADD PRIMARY KEY (`id`);

--
-- Tablo için indeksler `biletler`
--
ALTER TABLE `biletler`
  ADD PRIMARY KEY (`biletid`),
  ADD KEY `MusteriForeign` (`musteriid`) USING BTREE,
  ADD KEY `PersonelIdForeign` (`personelid`),
  ADD KEY `TasitIdForeign` (`tasitid`);

--
-- Tablo için indeksler `musteriler`
--
ALTER TABLE `musteriler`
  ADD PRIMARY KEY (`musteriid`);

--
-- Tablo için indeksler `personeller`
--
ALTER TABLE `personeller`
  ADD PRIMARY KEY (`personelid`);

--
-- Tablo için indeksler `tasitlar`
--
ALTER TABLE `tasitlar`
  ADD PRIMARY KEY (`tasitid`);

--
-- Tablo için indeksler `turlar`
--
ALTER TABLE `turlar`
  ADD PRIMARY KEY (`turid`);

--
-- Dökümü yapılmış tablolar için AUTO_INCREMENT değeri
--

--
-- Tablo için AUTO_INCREMENT değeri `adminler`
--
ALTER TABLE `adminler`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Tablo için AUTO_INCREMENT değeri `biletler`
--
ALTER TABLE `biletler`
  MODIFY `biletid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- Tablo için AUTO_INCREMENT değeri `musteriler`
--
ALTER TABLE `musteriler`
  MODIFY `musteriid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Tablo için AUTO_INCREMENT değeri `personeller`
--
ALTER TABLE `personeller`
  MODIFY `personelid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=59;

--
-- Tablo için AUTO_INCREMENT değeri `tasitlar`
--
ALTER TABLE `tasitlar`
  MODIFY `tasitid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- Tablo için AUTO_INCREMENT değeri `turlar`
--
ALTER TABLE `turlar`
  MODIFY `turid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- Dökümü yapılmış tablolar için kısıtlamalar
--

--
-- Tablo kısıtlamaları `biletler`
--
ALTER TABLE `biletler`
  ADD CONSTRAINT `PersonelIdForeign` FOREIGN KEY (`personelid`) REFERENCES `personeller` (`personelid`),
  ADD CONSTRAINT `TasitIdForeign` FOREIGN KEY (`tasitid`) REFERENCES `tasitlar` (`tasitid`),
  ADD CONSTRAINT `test` FOREIGN KEY (`musteriid`) REFERENCES `musteriler` (`musteriid`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
