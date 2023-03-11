-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : sam. 11 mars 2023 à 18:40
-- Version du serveur : 8.0.32
-- Version de PHP : 8.0.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `javaee`
--

-- --------------------------------------------------------

--
-- Structure de la table `etudiant`
--

DROP TABLE IF EXISTS `etudiant`;
CREATE TABLE IF NOT EXISTS `etudiant` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(50) DEFAULT NULL,
  `prenom` varchar(50) DEFAULT NULL,
  `genre` enum('homme','femme') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `previousSite` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `previousFormation` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `etudiant`
--

INSERT INTO `etudiant` (`id`, `nom`, `prenom`, `genre`, `previousSite`, `previousFormation`) VALUES
(1, 'Dupont', 'Jean', 'homme', NULL, NULL),
(2, 'Martin', 'Lucie', 'femme', NULL, NULL),
(3, 'Dubois', 'Marie', 'femme', NULL, NULL),
(4, 'Bernard', 'Pierre', 'homme', NULL, NULL),
(5, 'Petit', 'Sophie', 'femme', NULL, NULL),
(6, 'Lefebvre', 'Julien', 'homme', NULL, NULL),
(7, 'Moreau', 'Laura', 'femme', NULL, NULL),
(8, 'Roche', 'Antoine', 'homme', NULL, NULL),
(9, 'Durand', 'Alice', 'femme', NULL, NULL),
(10, 'Garcia', 'Alexandre', 'homme', NULL, NULL),
(11, 'Fournier', 'Emilie', 'femme', NULL, NULL),
(12, 'Rousseau', 'Thomas', 'homme', NULL, NULL),
(13, 'Girard', 'Caroline', 'femme', NULL, NULL),
(14, 'Brun', 'Nicolas', 'homme', NULL, NULL),
(15, 'Barbier', 'Charlotte', 'femme', NULL, NULL),
(16, 'Clement', 'Lucas', 'homme', NULL, NULL),
(17, 'Morin', 'Mathilde', 'femme', NULL, NULL),
(18, 'Nguyen', 'Maxime', 'homme', NULL, NULL),
(19, 'David', 'Chloé', 'femme', NULL, NULL),
(20, 'Johnson', 'Hugo', 'homme', NULL, NULL),
(21, 'Legendre', 'Louis', 'homme', 'wtf is this?', 'et ca? ');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
