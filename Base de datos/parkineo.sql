-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 16-06-2022 a las 12:53:37
-- Versión del servidor: 10.4.17-MariaDB
-- Versión de PHP: 8.0.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `parkineo`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `parkings`
--

CREATE TABLE `parkings` (
  `id_parking` int(10) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `ubicacion` varchar(50) NOT NULL,
  `link_maps` varchar(100) NOT NULL,
  `numero_plazas` int(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Tabla para guardar los parkings asociados con la app';

--
-- Volcado de datos para la tabla `parkings`
--

INSERT INTO `parkings` (`id_parking`, `nombre`, `ubicacion`, `link_maps`, `numero_plazas`) VALUES
(1, 'Martín Zapatero', 'Calahorra', 'https://goo.gl/maps/Bn9kwzxxGdATkuq86', 8),
(2, 'Arcca', 'Calahorra', 'https://goo.gl/maps/1xCRSrXzzE2mYy3E6', 8),
(3, 'Parkia', 'Logroño', 'https://goo.gl/maps/z2uA3zDaZfKs5hsx7', 8),
(4, 'APK2', 'Logroño', 'https://goo.gl/maps/Mx2rVg1D8wiVk9vJ9', 8);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `plazas`
--

CREATE TABLE `plazas` (
  `id_plaza` int(10) NOT NULL,
  `nombre` varchar(10) NOT NULL,
  `disponibilidad` varchar(1) NOT NULL,
  `id_parking` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Tabla para guardar/consultar las plazas de cada parking';

--
-- Volcado de datos para la tabla `plazas`
--

INSERT INTO `plazas` (`id_plaza`, `nombre`, `disponibilidad`, `id_parking`) VALUES
(1, 'A1', 'T', 1),
(2, 'A2', 'T', 1),
(3, 'B1', 'T', 1),
(4, 'B2', 'T', 1),
(5, 'C1', 'T', 1),
(6, 'C2', 'T', 1),
(7, 'D1', 'T', 1),
(8, 'D2', 'T', 1),
(9, 'A1', 'T', 2),
(10, 'A2', 'T', 2),
(11, 'B1', 'T', 2),
(12, 'B2', 'T', 2),
(13, 'C1', 'T', 2),
(14, 'C2', 'T', 2),
(15, 'D1', 'T', 2),
(16, 'D2', 'T', 2),
(17, 'A1', 'T', 3),
(18, 'A2', 'T', 3),
(19, 'B1', 'T', 3),
(20, 'B2', 'T', 3),
(21, 'C1', 'T', 3),
(22, 'C2', 'T', 3),
(23, 'D1', 'T', 3),
(24, 'D2', 'T', 3),
(25, 'A1', 'T', 4),
(26, 'A2', 'T', 4),
(27, 'B1', 'T', 4),
(28, 'B2', 'T', 4),
(29, 'C1', 'T', 4),
(30, 'C2', 'T', 4),
(31, 'D1', 'T', 4),
(32, 'D2', 'T', 4);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `reservas`
--

CREATE TABLE `reservas` (
  `id_reserva` int(4) NOT NULL,
  `titular` varchar(50) NOT NULL,
  `matricula` varchar(15) NOT NULL,
  `vehiculo` varchar(100) DEFAULT NULL,
  `plaza` varchar(4) NOT NULL,
  `parking` varchar(100) NOT NULL,
  `dias` int(5) NOT NULL,
  `fecha_reserva` date NOT NULL,
  `fecha_final` date NOT NULL,
  `seguro` varchar(1) NOT NULL DEFAULT 'F',
  `email` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Tabla de las reservas de los usuarios';

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id_usuario` int(10) NOT NULL,
  `usuario` varchar(25) NOT NULL,
  `email` varchar(50) NOT NULL,
  `contrasena` varchar(30) NOT NULL,
  `fecha_nacimiento` varchar(10) NOT NULL,
  `sexo` varchar(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Tabla en la que se guardan todos los usuarios registrados';

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id_usuario`, `usuario`, `email`, `contrasena`, `fecha_nacimiento`, `sexo`) VALUES
(11, 'David', 'davidcalahorra2002@gmail.com', 'DAVIDPARKINEO', '30/03/2002', 'Hombre'),
(87, 'Administrador', 'administrador@gmail.com', 'root1234', '30/03/2002', 'Hombre');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `valoraciones`
--

CREATE TABLE `valoraciones` (
  `id_valoracion` int(10) NOT NULL,
  `estrellas` float NOT NULL,
  `comentario` varchar(200) NOT NULL,
  `email` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Tabla para guardar las valoraciones de los usuarios';

--
-- Volcado de datos para la tabla `valoraciones`
--

INSERT INTO `valoraciones` (`id_valoracion`, `estrellas`, `comentario`, `email`) VALUES
(7, 4, 'Aplicación muy buena', 'davidcalahorra2002@gmail.com');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `parkings`
--
ALTER TABLE `parkings`
  ADD PRIMARY KEY (`id_parking`);

--
-- Indices de la tabla `plazas`
--
ALTER TABLE `plazas`
  ADD PRIMARY KEY (`id_plaza`),
  ADD KEY `fk_plazas` (`id_parking`);

--
-- Indices de la tabla `reservas`
--
ALTER TABLE `reservas`
  ADD PRIMARY KEY (`id_reserva`),
  ADD KEY `fk_email_reservas` (`email`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id_usuario`),
  ADD UNIQUE KEY `usuario` (`usuario`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indices de la tabla `valoraciones`
--
ALTER TABLE `valoraciones`
  ADD PRIMARY KEY (`id_valoracion`),
  ADD KEY `fk_email_valoraciones` (`email`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `parkings`
--
ALTER TABLE `parkings`
  MODIFY `id_parking` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `plazas`
--
ALTER TABLE `plazas`
  MODIFY `id_plaza` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;

--
-- AUTO_INCREMENT de la tabla `reservas`
--
ALTER TABLE `reservas`
  MODIFY `id_reserva` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=103;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id_usuario` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=129;

--
-- AUTO_INCREMENT de la tabla `valoraciones`
--
ALTER TABLE `valoraciones`
  MODIFY `id_valoracion` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `plazas`
--
ALTER TABLE `plazas`
  ADD CONSTRAINT `fk_plazas` FOREIGN KEY (`id_parking`) REFERENCES `parkings` (`id_parking`);

--
-- Filtros para la tabla `reservas`
--
ALTER TABLE `reservas`
  ADD CONSTRAINT `fk_email_reservas` FOREIGN KEY (`email`) REFERENCES `usuarios` (`email`);

--
-- Filtros para la tabla `valoraciones`
--
ALTER TABLE `valoraciones`
  ADD CONSTRAINT `fk_email_valoraciones` FOREIGN KEY (`email`) REFERENCES `usuarios` (`email`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
