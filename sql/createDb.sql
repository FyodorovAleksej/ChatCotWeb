-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema chatcot
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema chatcot
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `chatcot` DEFAULT CHARACTER SET utf8 ;
USE `chatcot` ;

-- -----------------------------------------------------
-- Table `chatcot`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `chatcot`.`users` (
  `userId` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(100) NOT NULL,
  `login` VARCHAR(100) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  `role` ENUM('user', 'admin') NOT NULL,
  PRIMARY KEY (`userId`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC),
  UNIQUE INDEX `login_UNIQUE` (`login` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `chatcot`.`phrases`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `chatcot`.`phrases` (
  `phraseId` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `type` VARCHAR(45) NOT NULL,
  `phrase` VARCHAR(100) NOT NULL,
  `date` DATE NOT NULL,
  `owner` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`phraseId`),
  UNIQUE INDEX `phrase_UNIQUE` (`phrase` ASC),
  INDEX `fk_phrases_users_idx` (`owner` ASC),
  CONSTRAINT `fk_phrases_users`
    FOREIGN KEY (`owner`)
    REFERENCES `chatcot`.`users` (`userId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
