-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 20-02-2024 a las 21:49:51
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `prueba_tecnica`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `client`
--

CREATE TABLE `client` (
  `dni` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `client`
--

INSERT INTO `client` (`dni`, `email`, `last_name`, `name`) VALUES
('11223344X', 'lm10@gmail.com', 'Messi', 'Lionel'),
('22334455B', 'ia10@gmail.com', 'Aspas', 'Iago'),
('33445566C', 'km7@gmail.com', 'Mbappe', 'Killian'),
('44556677D', 'zi20@gmail.com', 'Ibrahimovic', 'Zlatan'),
('55667788E', 'zizou10@gmail.com', 'Zidane', 'Zinedine');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `flight`
--

CREATE TABLE `flight` (
  `code` varchar(255) NOT NULL,
  `departure_date` date DEFAULT NULL,
  `destination` varchar(255) DEFAULT NULL,
  `origin` varchar(255) DEFAULT NULL,
  `price_per_seat` double DEFAULT NULL,
  `seat_type` varchar(255) DEFAULT NULL,
  `total_seats` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `flight`
--

INSERT INTO `flight` (`code`, `departure_date`, `destination`, `origin`, `price_per_seat`, `seat_type`, `total_seats`) VALUES
('BAMA-3311', '2024-03-18', 'Madrid', 'Barcelona', 100, 'Economy', 57),
('BAVI-3344', '2024-03-27', 'Vigo', 'Barcelona', 220, 'Business', 56),
('MABA-1133', '2024-03-22', 'Barcelona', 'Madrid', 100, 'Economy', 59),
('MADU-1188', '2024-04-10', 'Dublin', 'Madrid', 440, 'Business', 50),
('MAME-1122', '2024-03-20', 'Mexico', 'Madrid', 400, 'Business', 50),
('MAPA-1177', '2024-04-05', 'Paris', 'Madrid', 400, 'Economy', 58),
('MEMA-2211', '2024-03-25', 'Madrid', 'Mexico', 500, 'Business', 50),
('PAMA-7711', '2024-04-01', 'Madrid', 'Paris', 600, 'Business', 64),
('PASA-7755', '2024-04-05', 'Santiago', 'Paris', 500, 'Business', 68),
('SAPA-5577', '2024-04-02', 'Paris', 'Santiago', 450, 'Business', 68),
('VIBA-4433', '2024-03-23', 'Barcelona', 'Vigo', 140, 'Economy', 50);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `flight_booking`
--

CREATE TABLE `flight_booking` (
  `id` bigint(20) NOT NULL,
  `reserve_date` date DEFAULT NULL,
  `flight_code` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `flight_booking`
--

INSERT INTO `flight_booking` (`id`, `reserve_date`, `flight_code`) VALUES
(1, '2024-02-20', 'BAMA-3311'),
(2, '2024-02-20', 'MABA-1133'),
(3, '2024-02-20', 'MAPA-1177'),
(4, '2024-02-20', 'PAMA-7711'),
(5, '2024-02-20', 'SAPA-5577');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `flight_reserve_client`
--

CREATE TABLE `flight_reserve_client` (
  `flight_reserve_id` bigint(20) NOT NULL,
  `client_dni` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `flight_reserve_client`
--

INSERT INTO `flight_reserve_client` (`flight_reserve_id`, `client_dni`) VALUES
(1, '11223344X'),
(2, '11223344X'),
(3, '11223344X'),
(3, '22334455B'),
(4, '11223344X'),
(4, '22334455B'),
(5, '44556677D'),
(5, '55667788E');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `hotel`
--

CREATE TABLE `hotel` (
  `code` varchar(255) NOT NULL,
  `city` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `hotel`
--

INSERT INTO `hotel` (`code`, `city`, `name`) VALUES
('AR0002', 'Miami', 'Atlantis Resort'),
('AR0003', 'Miami', 'Atlantis Resort 2'),
('GH0002', 'Madrid', 'Grand Hyatt'),
('GH0003', 'Buenos Aires', 'Grand Hyatt 2'),
('HL0001', 'Barcelona', 'Hilton'),
('HL0002', 'Barcelona', 'Hilton 2'),
('SH0004', 'Madrid', 'Sheraton'),
('SH0005', 'Santiago', 'Sheraton 2');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `room`
--

CREATE TABLE `room` (
  `code` bigint(20) NOT NULL,
  `is_available` bit(1) NOT NULL,
  `price_per_night` double DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `hotel_code` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `room`
--

INSERT INTO `room` (`code`, `is_available`, `price_per_night`, `type`, `hotel_code`) VALUES
(1, b'1', 300, 'Doble', 'AR0002'),
(2, b'1', 350, 'Triple', 'AR0002'),
(3, b'1', 400, 'Doble', 'AR0003'),
(4, b'1', 450, 'Triple', 'AR0003'),
(5, b'1', 200, 'Simple', 'GH0002'),
(6, b'1', 250, 'Doble', 'GH0002'),
(7, b'1', 150, 'Simple', 'GH0003'),
(8, b'1', 200, 'Doble', 'GH0003'),
(9, b'1', 150, 'Simple', 'HL0001'),
(10, b'1', 200, 'Doble', 'HL0001'),
(11, b'1', 250, 'Triple', 'HL0001'),
(15, b'1', 180, 'Simple', 'SH0004'),
(16, b'1', 210, 'Doble', 'SH0004'),
(17, b'1', 160, 'Simple', 'SH0005'),
(18, b'1', 200, 'Doble', 'SH0005'),
(19, b'1', 300, 'Doble', 'AR0002'),
(20, b'1', 350, 'Triple', 'AR0002'),
(21, b'1', 300, 'Doble', 'AR0002'),
(22, b'1', 350, 'Triple', 'AR0002'),
(23, b'1', 300, 'Doble', 'AR0002'),
(24, b'1', 350, 'Triple', 'AR0002'),
(25, b'1', 350, 'Doble', 'AR0002'),
(26, b'1', 220, 'Single', 'AR0002');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `room_booking`
--

CREATE TABLE `room_booking` (
  `id` bigint(20) NOT NULL,
  `date_from` date DEFAULT NULL,
  `date_to` date DEFAULT NULL,
  `room_code` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `room_booking`
--

INSERT INTO `room_booking` (`id`, `date_from`, `date_to`, `room_code`) VALUES
(2, '2024-04-01', '2024-04-07', 3),
(3, '2024-04-01', '2024-04-07', 4),
(4, '2024-04-10', '2024-04-17', 4);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `room_reserve_client`
--

CREATE TABLE `room_reserve_client` (
  `room_reserve_id` bigint(20) NOT NULL,
  `client_dni` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `room_reserve_client`
--

INSERT INTO `room_reserve_client` (`room_reserve_id`, `client_dni`) VALUES
(2, '33445566C'),
(3, '44556677D'),
(4, '55667788E');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `client`
--
ALTER TABLE `client`
  ADD PRIMARY KEY (`dni`);

--
-- Indices de la tabla `flight`
--
ALTER TABLE `flight`
  ADD PRIMARY KEY (`code`);

--
-- Indices de la tabla `flight_booking`
--
ALTER TABLE `flight_booking`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK8bcont35346oho228l9wvoht0` (`flight_code`);

--
-- Indices de la tabla `flight_reserve_client`
--
ALTER TABLE `flight_reserve_client`
  ADD KEY `FKs7cag9fkh0c44n0dalok7kjk8` (`client_dni`),
  ADD KEY `FKeor77oyvgn0wd3b29qfe1gl6k` (`flight_reserve_id`);

--
-- Indices de la tabla `hotel`
--
ALTER TABLE `hotel`
  ADD PRIMARY KEY (`code`);

--
-- Indices de la tabla `room`
--
ALTER TABLE `room`
  ADD PRIMARY KEY (`code`),
  ADD KEY `FK52stjyn379yiqc4od3rie9f9a` (`hotel_code`);

--
-- Indices de la tabla `room_booking`
--
ALTER TABLE `room_booking`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKbp1u91ch9m66g3wy41j66h4dk` (`room_code`);

--
-- Indices de la tabla `room_reserve_client`
--
ALTER TABLE `room_reserve_client`
  ADD KEY `FK9eiwl3oluswvy2bouft7xn04r` (`client_dni`),
  ADD KEY `FKbe0oyea1ivtkxbg20tosorpvg` (`room_reserve_id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `flight_booking`
--
ALTER TABLE `flight_booking`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de la tabla `room`
--
ALTER TABLE `room`
  MODIFY `code` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- AUTO_INCREMENT de la tabla `room_booking`
--
ALTER TABLE `room_booking`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `flight_booking`
--
ALTER TABLE `flight_booking`
  ADD CONSTRAINT `FK8bcont35346oho228l9wvoht0` FOREIGN KEY (`flight_code`) REFERENCES `flight` (`code`);

--
-- Filtros para la tabla `flight_reserve_client`
--
ALTER TABLE `flight_reserve_client`
  ADD CONSTRAINT `FKeor77oyvgn0wd3b29qfe1gl6k` FOREIGN KEY (`flight_reserve_id`) REFERENCES `flight_booking` (`id`),
  ADD CONSTRAINT `FKs7cag9fkh0c44n0dalok7kjk8` FOREIGN KEY (`client_dni`) REFERENCES `client` (`dni`);

--
-- Filtros para la tabla `room`
--
ALTER TABLE `room`
  ADD CONSTRAINT `FK52stjyn379yiqc4od3rie9f9a` FOREIGN KEY (`hotel_code`) REFERENCES `hotel` (`code`);

--
-- Filtros para la tabla `room_booking`
--
ALTER TABLE `room_booking`
  ADD CONSTRAINT `FKbp1u91ch9m66g3wy41j66h4dk` FOREIGN KEY (`room_code`) REFERENCES `room` (`code`);

--
-- Filtros para la tabla `room_reserve_client`
--
ALTER TABLE `room_reserve_client`
  ADD CONSTRAINT `FK9eiwl3oluswvy2bouft7xn04r` FOREIGN KEY (`client_dni`) REFERENCES `client` (`dni`),
  ADD CONSTRAINT `FKbe0oyea1ivtkxbg20tosorpvg` FOREIGN KEY (`room_reserve_id`) REFERENCES `room_booking` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
