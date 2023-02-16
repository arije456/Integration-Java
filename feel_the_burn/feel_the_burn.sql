-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : jeu. 03 mars 2022 à 21:39
-- Version du serveur :  5.7.31
-- Version de PHP : 7.3.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `feel_the_burn`
--

-- --------------------------------------------------------

--
-- Structure de la table `activite`
--

DROP TABLE IF EXISTS `activite`;
CREATE TABLE IF NOT EXISTS `activite` (
  `id_a` int(11) NOT NULL AUTO_INCREMENT,
  `nom_a` varchar(50) NOT NULL,
  `cat_age` int(11) NOT NULL,
  `type` varchar(30) NOT NULL,
  `id_enfant` int(11) NOT NULL,
  PRIMARY KEY (`id_a`),
  KEY `id_enfant` (`id_enfant`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `activite`
--

INSERT INTO `activite` (`id_a`, `nom_a`, `cat_age`, `type`, `id_enfant`) VALUES
(10, 'Gym', 15, 'Sport', 2),
(15, 'Yoga', 13, 'Sport', 1),
(16, 'GooooaaaaLLLLL', 6, 'Football', 1),
(17, 'Anniversaire', 16, 'Pause', 1),
(18, 'Danser', 13, 'Divertissement', 2),
(19, 'HELLO', 14, 'Sport', 2),
(21, 'Zumba', 7, 'Sport', 2);

-- --------------------------------------------------------

--
-- Structure de la table `blg`
--

DROP TABLE IF EXISTS `blg`;
CREATE TABLE IF NOT EXISTS `blg` (
  `Id_b` int(11) NOT NULL AUTO_INCREMENT,
  `Titre` varchar(25) NOT NULL,
  `Contenu` varchar(255) NOT NULL,
  `Date` varchar(255) NOT NULL,
  `Auteur` varchar(255) NOT NULL,
  `Image` varchar(255) NOT NULL,
  `categorie` varchar(255) NOT NULL,
  PRIMARY KEY (`Id_b`)
) ENGINE=InnoDB AUTO_INCREMENT=89 DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `blg`
--

INSERT INTO `blg` (`Id_b`, `Titre`, `Contenu`, `Date`, `Auteur`, `Image`, `categorie`) VALUES
(56, 're', 'Aziz', 'Date', 'Arije', 'Taswira', '12'),
(57, 're', 'Aziz', 'Date', 'kk', 'Taswira', '12'),
(58, 'r', 'Arije', 'grissiaa', 'Aa', 'Taswira', '19'),
(64, 'Régime', 'Keto Regime', '28/02/2022', 'Farah', 'Planning.png', 'Régime'),
(71, 'Yoga', 'Yoga', 'bb', 'Zeineb', 'Yoga.png', 'mmm'),
(72, 'HELLO WORLD', 'HELLO WORD', '21/02/2022', 'MOI', 'HELLO WORLD', 'BLABLA'),
(73, 'aabb', 'ddd', 'lkkaa', 'ccnn', 'aadd', 'mm'),
(75, 'zeynouba', 'poiu', 'mlkhj', 'arije', 'aze', 'ldoi'),
(80, 'Equipement', 'Equipement', '27/02/2022', 'Farah', 'Equipement.png', 'Sport'),
(81, 'Hello', 'KKKK', 'KKKK', 'KKKKK', 'KKKK', 'Accuiel'),
(82, 'Regime', 'Sport', '01/01/2022', 'Arij', 'Regime.png', 'Sport'),
(83, '22', '22', '22', '22', '22', '22'),
(84, 'BBBB', 'BBBBB', 'BBBB', 'BBBB', 'BBBB', 'BBBBB'),
(85, 'HELLO', 'HHHHH', 'HELLO', 'HELLO', '', 'HHHHHH'),
(86, 'pppp', 'pppp', 'pppp', 'pppp', 'pppp', 'pppp'),
(87, 'ppppp', 'ppppp', 'ppppp', 'ppppp', '', 'ppppp'),
(88, 'SSS', 'SSS', 'SSS', 'SSS', 'C:\\Users\\zeine\\OneDrive\\Bureau\\feel_the_burn\\src\\Assetspanier_icon.png', 'SSS');

-- --------------------------------------------------------

--
-- Structure de la table `categorie_b`
--

DROP TABLE IF EXISTS `categorie_b`;
CREATE TABLE IF NOT EXISTS `categorie_b` (
  `Id_categorie` int(11) NOT NULL AUTO_INCREMENT,
  `Nom_categorie` varchar(255) NOT NULL,
  `Description` varchar(255) NOT NULL,
  PRIMARY KEY (`Id_categorie`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `coaching`
--

DROP TABLE IF EXISTS `coaching`;
CREATE TABLE IF NOT EXISTS `coaching` (
  `Id_S` int(11) NOT NULL AUTO_INCREMENT,
  `Date_S` varchar(255) NOT NULL,
  `Prix` float NOT NULL,
  `Id_Co` int(11) NOT NULL,
  `Nom_User` varchar(25) NOT NULL,
  `Prenom_User` varchar(25) NOT NULL,
  PRIMARY KEY (`Id_S`)
) ENGINE=MyISAM AUTO_INCREMENT=25 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `coaching`
--

INSERT INTO `coaching` (`Id_S`, `Date_S`, `Prix`, `Id_Co`, `Nom_User`, `Prenom_User`) VALUES
(2, '01/02/22', 20.5, 30, 'wael', 'ww'),
(17, '06/06/2022', 56.3, 3, 'HELLO', 'WORLD'),
(24, '01/01/2022', 30.3, 1, 'Wael', 'Belgaied'),
(7, '01/02/22', 20.5, 30, 'wael', 'wael'),
(9, '01/02/22', 30.5, 30, 'wael', 'ww'),
(21, 'HELLO', 24.5, 3, 'HELLO', 'HELLO'),
(23, 'HHHHH', 13.4, 3, 'HHHH', 'HHHHH'),
(18, '27/02/2022', 25, 1, 'Wael', 'Belgaied'),
(19, 'HHHH', 17.6, 2, 'HHHH', 'HHHH'),
(20, 'MMMM', 19.6, 1, 'MMM', 'MMMM');

-- --------------------------------------------------------

--
-- Structure de la table `commentaire`
--

DROP TABLE IF EXISTS `commentaire`;
CREATE TABLE IF NOT EXISTS `commentaire` (
  `Id_com` int(11) NOT NULL AUTO_INCREMENT,
  `Nom_c` varchar(255) NOT NULL,
  `Email` varchar(255) NOT NULL,
  `Message` varchar(255) NOT NULL,
  `Date` varchar(255) NOT NULL,
  `Nom_article` varchar(255) NOT NULL,
  `Id_b` int(11) NOT NULL,
  PRIMARY KEY (`Id_com`),
  KEY `Id_b` (`Id_b`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `commentaire`
--

INSERT INTO `commentaire` (`Id_com`, `Nom_c`, `Email`, `Message`, `Date`, `Nom_article`, `Id_b`) VALUES
(7, 'zeynouba', 'grissiaa', 'hshhd', 'dddd', 'arije', 56),
(8, 'krije', 'FARAH', 'hshhd', 'dddd', 'arije', 57),
(13, 'ARIJE', 'grissiaa', 'hshhd', 'dddd', 'arije', 56),
(15, 'arije', 'arije.grissiaa@esprit.tn', 'aeryhfytgrdes', '12/02/2001', 'zeynoubaaa', 56),
(22, 'HELLO', 'zz', 'HELLO', 'mm', 'HELLO', 56),
(23, 'Sport', 'zeineb.haraketi@esprit.tn', 'Sport', '27/02/2022', 'Sport', 71),
(24, 'Sport', 'Sport', 'regime', '01/01/2022', 'Regime', 56);

-- --------------------------------------------------------

--
-- Structure de la table `consultation`
--

DROP TABLE IF EXISTS `consultation`;
CREATE TABLE IF NOT EXISTS `consultation` (
  `Id_c` int(11) NOT NULL AUTO_INCREMENT,
  `Nom` varchar(255) NOT NULL,
  `Age` int(11) NOT NULL,
  `Sexe` varchar(255) NOT NULL,
  `Date_rdv` varchar(255) NOT NULL,
  `Etat_physique` varchar(255) NOT NULL,
  `categorie_c` varchar(255) NOT NULL,
  `Prenom` varchar(255) NOT NULL,
  `Email` varchar(255) NOT NULL,
  PRIMARY KEY (`Id_c`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `consultation`
--

INSERT INTO `consultation` (`Id_c`, `Nom`, `Age`, `Sexe`, `Date_rdv`, `Etat_physique`, `categorie_c`, `Prenom`, `Email`) VALUES
(1, 'Gamra', 22, 'Femme', '03/03/2022', 'excès de poids', 'Regime', 'Ben Marzouka', 'Gamra.benMarzouka@gmail.com'),
(27, 'Arije', 19, 'Femme', '05/04/2022', 'Blessure', 'Blessure', 'Grissia', 'HELLO'),
(29, 'Ahmed', 16, 'Homme', '04/03/2022', 'ERTYHJ', 'Blessure', 'Belabid', 'QZERTYU'),
(31, 'Bonjour', 30, 'Femme', '01/01/2022', 'Stable', 'Sport', 'Bonjour', 'Bonjour');

-- --------------------------------------------------------

--
-- Structure de la table `cours`
--

DROP TABLE IF EXISTS `cours`;
CREATE TABLE IF NOT EXISTS `cours` (
  `Id_C` int(11) NOT NULL AUTO_INCREMENT,
  `Nom_C` varchar(255) NOT NULL,
  `Date_C` varchar(255) NOT NULL,
  `Id_Co` int(11) NOT NULL,
  PRIMARY KEY (`Id_C`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `cours`
--

INSERT INTO `cours` (`Id_C`, `Nom_C`, `Date_C`, `Id_Co`) VALUES
(1, 'Cardio', '27/03/2022', 1),
(3, 'YOGA', '01/01/2022', 2),
(4, 'Cardioooo', 'll', 85);

-- --------------------------------------------------------

--
-- Structure de la table `enfant`
--

DROP TABLE IF EXISTS `enfant`;
CREATE TABLE IF NOT EXISTS `enfant` (
  `id_enfant` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(30) NOT NULL,
  `prenom` varchar(30) NOT NULL,
  `age` int(11) NOT NULL,
  `sexe` varchar(15) NOT NULL,
  `photo` varchar(255) NOT NULL,
  `id_a` int(11) NOT NULL,
  PRIMARY KEY (`id_enfant`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `enfant`
--

INSERT INTO `enfant` (`id_enfant`, `nom`, `prenom`, `age`, `sexe`, `photo`, `id_a`) VALUES
(1, 'Farah', 'Rekik', 16, 'femme', 'farah.png', 1),
(2, 'Wael', 'Belgaied', 14, 'Homme', 'willy.png', 2),
(4, 'Marwen', 'Haraketi', 17, 'Homme', 'marwen.png', 2),
(5, 'Yosser', 'Bouafif', 16, 'Femme', 'Yosser.png', 3),
(6, 'Wael', 'Belgaied', 6, 'Homme', 'Wael.png', 2),
(7, 'Zeineb', 'Haraketi', 16, 'Femme', 'C:\\Users\\zeine\\OneDrive\\Bureau\\feel_the_burn\\src\\Assetsicon_enf.png', 2),
(8, 'HELLO', 'HELLO', 4, 'FEMME', 'C:\\Users\\zeine\\OneDrive\\Bureau\\feel_the_burn\\src\\Assetsicon_enf.png', 3),
(9, 'Talia', 'RosenBerg', 5, 'Femme', 'C:\\Users\\zeine\\OneDrive\\Bureau\\feel_the_burn\\src\\Assetsconsult_icon.png', 3),
(10, 'KKK', 'KKK', 3, 'HOMME', 'C:\\Users\\zeine\\OneDrive\\Bureau\\feel_the_burn\\src\\Assets123.png', 3),
(11, 'Zeineba', 'Haraketi', 17, 'Femme', 'C:\\Users\\zeine\\OneDrive\\Bureau\\feel_the_burn\\src\\Assetsicon_enf.png', 3);

-- --------------------------------------------------------

--
-- Structure de la table `evennement`
--

DROP TABLE IF EXISTS `evennement`;
CREATE TABLE IF NOT EXISTS `evennement` (
  `Id-event` int(11) NOT NULL AUTO_INCREMENT,
  `Nom` varchar(255) NOT NULL,
  `Date` varchar(255) NOT NULL,
  `Id_user` int(11) NOT NULL,
  PRIMARY KEY (`Id-event`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `panier`
--

DROP TABLE IF EXISTS `panier`;
CREATE TABLE IF NOT EXISTS `panier` (
  `Id_Pa` int(11) NOT NULL AUTO_INCREMENT,
  `Quantite` int(11) NOT NULL,
  `Coupon` varchar(25) NOT NULL,
  `Id_P` int(11) NOT NULL,
  PRIMARY KEY (`Id_Pa`),
  KEY `Id_P` (`Id_P`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `panier`
--

INSERT INTO `panier` (`Id_Pa`, `Quantite`, `Coupon`, `Id_P`) VALUES
(4, 17, 'fifouuuu', 31),
(6, 3, 'farouha', 30),
(10, 4, 'ahhahah', 32),
(11, 8, 'ouiii', 28),
(12, 6, 'waoulee', 30),
(13, 5, 'hahaha', 28),
(15, 9, 'uuuuu', 28),
(16, 15, 'ahhaha', 28),
(18, 8, 'yyyy', 30),
(20, 7, 'ppppp', 30),
(21, 14, 'iiiii', 31),
(22, 9, 'rrrrrrr', 31),
(23, 9, 'ppppp', 31),
(24, 27, 'FARAH', 31),
(29, 23, 'Gratis', 28),
(30, 5, 'HELLO', 28),
(31, 22, '22', 56);

-- --------------------------------------------------------

--
-- Structure de la table `produit`
--

DROP TABLE IF EXISTS `produit`;
CREATE TABLE IF NOT EXISTS `produit` (
  `Id_P` int(11) NOT NULL AUTO_INCREMENT,
  `Nom_P` varchar(25) NOT NULL,
  `Prix` float NOT NULL,
  `Photo` varchar(255) NOT NULL,
  `Categories` varchar(25) NOT NULL,
  PRIMARY KEY (`Id_P`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `produit`
--

INSERT INTO `produit` (`Id_P`, `Nom_P`, `Prix`, `Photo`, `Categories`) VALUES
(28, 'farouha', 16, 'aroujaa', 'waouleee'),
(30, 'farah10', 14.02, 'rekik', '11'),
(32, 'farah', 14.02, 'rekik', '11'),
(34, 'fifi1', 12.7, 'photo1', '19'),
(35, 'fifi', 10.2, 'ajkhkaj', '19'),
(36, 'farah', 23.5, 'ahahhahh', 'zinouba'),
(37, 'arije', 16.3, 'soussaaa', 'adidas'),
(39, 'jblekfmfem', 18.5, 'kdnmlff', 'hhhhhhhhhhh'),
(42, 'farah', 16.2, 'hhhhhh', 'yoyo'),
(43, 'farah', 16.2, 'hhhhhh', 'yoyo'),
(44, 'FARAH', 20.3, 'dljùùfpo', 'hhhjiil'),
(46, 'Keto', 23.6, 'Keto.png', 'Regime'),
(47, '22', 22.2, '22', '22');

-- --------------------------------------------------------

--
-- Structure de la table `programme`
--

DROP TABLE IF EXISTS `programme`;
CREATE TABLE IF NOT EXISTS `programme` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Nom_p` varchar(255) DEFAULT NULL,
  `Date_r` varchar(255) DEFAULT NULL,
  `Id_Kine` int(11) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `Id_c` int(11) NOT NULL,
  PRIMARY KEY (`Id`),
  KEY `Id_c` (`Id_c`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `programme`
--

INSERT INTO `programme` (`Id`, `Nom_p`, `Date_r`, `Id_Kine`, `description`, `Id_c`) VALUES
(38, 'GAMRA', '', 45, 'GAMRAAZZ', 1),
(40, 'TAHER', '122A', 98, 'AAAAA', 29),
(42, 'AZERTY', '12:33', 12, 'AZERTY', 1),
(43, 'QSDF', '', 12, 'GAMRABEN', 1),
(44, 'AAAA', 'GAMRA', 14, 'EDRFTGYH', 1),
(45, 'AZERT', 'SDF', 7, 'XCVB', 1),
(48, 'wi', '33', 22, 'AAA', 1),
(52, 'DFVG', 'DFG', 65, 'SDFGH', 1),
(53, 'Keto', '01/01/2022', 3, 'Regime', 1),
(54, '22', '22', 22, '22', 1);

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) DEFAULT NULL,
  `Nom_U` varchar(255) NOT NULL,
  `Prenom` varchar(255) NOT NULL,
  `Adresse` varchar(255) NOT NULL,
  `Date_N` varchar(255) NOT NULL,
  `Login` varchar(255) NOT NULL,
  `Mdp` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `activite`
--
ALTER TABLE `activite`
  ADD CONSTRAINT `id_enfant` FOREIGN KEY (`id_enfant`) REFERENCES `enfant` (`id_enfant`);

--
-- Contraintes pour la table `commentaire`
--
ALTER TABLE `commentaire`
  ADD CONSTRAINT `Id_b` FOREIGN KEY (`Id_b`) REFERENCES `blg` (`Id_b`);

--
-- Contraintes pour la table `programme`
--
ALTER TABLE `programme`
  ADD CONSTRAINT `Id_c` FOREIGN KEY (`Id_c`) REFERENCES `consultation` (`Id_c`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
