-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema railway_station_ticket
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema railway_station_ticket
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `railway_station_ticket` DEFAULT CHARACTER SET utf8 ;
USE `railway_station_ticket` ;

-- -----------------------------------------------------
-- Table `railway_station_ticket`.`role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `railway_station_ticket`.`role` ;

CREATE TABLE IF NOT EXISTS `railway_station_ticket`.`role` (
                                                               `id` INT NOT NULL AUTO_INCREMENT,
                                                               `name` VARCHAR(255) NOT NULL,
                                                               PRIMARY KEY (`id`));


-- -----------------------------------------------------
-- Table `railway_station_ticket`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `railway_station_ticket`.`user` ;

CREATE TABLE IF NOT EXISTS `railway_station_ticket`.`user` (
                                                               `id` INT NOT NULL AUTO_INCREMENT,
                                                               `login` VARCHAR(16) NOT NULL,
                                                               `password` VARCHAR(32) NOT NULL,
                                                               `first_name` VARCHAR(45) NOT NULL,
                                                               `last_name` VARCHAR(45) NOT NULL,
                                                               `phone` VARCHAR(10) NOT NULL,
                                                               `role_id` INT NOT NULL,
                                                               PRIMARY KEY (`id`, `role_id`),
                                                               UNIQUE INDEX `login_UNIQUE` (`login` ASC) VISIBLE,
                                                               UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
                                                               INDEX `fk_user_role1_idx` (`role_id` ASC) VISIBLE);


-- -----------------------------------------------------
-- Table `railway_station_ticket`.`train`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `railway_station_ticket`.`train` ;

CREATE TABLE IF NOT EXISTS `railway_station_ticket`.`train` (
                                                                `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
                                                                `seats` INT UNSIGNED NOT NULL,
                                                                PRIMARY KEY (`id`),
                                                                UNIQUE INDEX `train_number_UNIQUE` (`id` ASC) VISIBLE);


-- -----------------------------------------------------
-- Table `railway_station_ticket`.`station`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `railway_station_ticket`.`station` ;

CREATE TABLE IF NOT EXISTS `railway_station_ticket`.`station` (
                                                                  `id` INT NOT NULL AUTO_INCREMENT,
                                                                  `name` VARCHAR(255) NOT NULL,
                                                                  PRIMARY KEY (`id`));


-- -----------------------------------------------------
-- Table `railway_station_ticket`.`schedule`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `railway_station_ticket`.`schedule` ;

CREATE TABLE IF NOT EXISTS `railway_station_ticket`.`schedule` (
                                                                   `id` INT NOT NULL AUTO_INCREMENT,
                                                                   `date` DATE NOT NULL,
                                                                   `train_id` INT UNSIGNED NOT NULL,
                                                                   PRIMARY KEY (`id`, `train_id`),
                                                                   INDEX `fk_schedule_train1_idx` (`train_id` ASC) VISIBLE);


-- -----------------------------------------------------
-- Table `railway_station_ticket`.`route`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `railway_station_ticket`.`route` ;

CREATE TABLE IF NOT EXISTS `railway_station_ticket`.`route` (
                                                                `id` INT NOT NULL AUTO_INCREMENT,
                                                                `stoppage_number` INT UNSIGNED NOT NULL,
                                                                `starting_station_id` INT NOT NULL,
                                                                `final_station_id` INT NOT NULL,
                                                                `departure_time` TIME NOT NULL,
                                                                `arrival_time` TIME NOT NULL,
                                                                `available_seats` INT UNSIGNED NOT NULL,
                                                                `day` INT UNSIGNED NOT NULL,
                                                                `schedule_id` INT NOT NULL,
                                                                `train_id` INT UNSIGNED NOT NULL,
                                                                `price` DECIMAL(6,2) UNSIGNED NOT NULL,
                                                                PRIMARY KEY (`id`, `starting_station_id`, `final_station_id`, `schedule_id`, `train_id`),
                                                                UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
                                                                INDEX `fk_route_station1_idx` (`starting_station_id` ASC) VISIBLE,
                                                                INDEX `fk_route_station2_idx` (`final_station_id` ASC) VISIBLE,
                                                                INDEX `fk_route_schedule1_idx` (`schedule_id` ASC, `train_id` ASC) VISIBLE);


-- -----------------------------------------------------
-- Table `railway_station_ticket`.`ticket_status`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `railway_station_ticket`.`ticket_status` ;

CREATE TABLE IF NOT EXISTS `railway_station_ticket`.`ticket_status` (
                                                                        `id` INT NOT NULL AUTO_INCREMENT,
                                                                        `name` VARCHAR(255) NOT NULL,
                                                                        PRIMARY KEY (`id`));


-- -----------------------------------------------------
-- Table `railway_station_ticket`.`ticket`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `railway_station_ticket`.`ticket` ;

CREATE TABLE IF NOT EXISTS `railway_station_ticket`.`ticket` (
                                                                 `id` INT NOT NULL AUTO_INCREMENT,
                                                                 `fare` DECIMAL(8,2) UNSIGNED NOT NULL,
                                                                 `starting_station` INT NOT NULL,
                                                                 `final_station` INT NOT NULL,
                                                                 `departure_time` TIMESTAMP NOT NULL,
                                                                 `arrival_time` TIMESTAMP NOT NULL,
                                                                 `train_number` INT UNSIGNED NOT NULL,
                                                                 `user_id` INT NOT NULL,
                                                                 `ticket_status_id` INT NOT NULL,
                                                                 PRIMARY KEY (`id`, `starting_station`, `final_station`, `user_id`, `ticket_status_id`),
                                                                 INDEX `fk_ticket_user_idx` (`user_id` ASC) VISIBLE,
                                                                 INDEX `fk_ticket_station1_idx` (`starting_station` ASC) VISIBLE,
                                                                 INDEX `fk_ticket_station2_idx` (`final_station` ASC) VISIBLE,
                                                                 INDEX `fk_ticket_ticket_status1_idx` (`ticket_status_id` ASC) VISIBLE);


-- -----------------------------------------------------
-- Table `railway_station_ticket`.`connecting_stations`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `railway_station_ticket`.`connecting_stations` ;

CREATE TABLE IF NOT EXISTS `railway_station_ticket`.`connecting_stations` (
                                                                              `stations_from` INT NOT NULL,
                                                                              `stations_to` INT NOT NULL,
                                                                              INDEX `fk_ways_stations1_idx` (`stations_from` ASC) VISIBLE,
                                                                              INDEX `fk_ways_stations2_idx` (`stations_to` ASC) VISIBLE,
                                                                              PRIMARY KEY (`stations_from`, `stations_to`));


-- -----------------------------------------------------
-- Table `railway_station_ticket`.`status_flow`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `railway_station_ticket`.`status_flow` ;

CREATE TABLE IF NOT EXISTS `railway_station_ticket`.`status_flow` (
                                                                      `id` INT NOT NULL AUTO_INCREMENT,
                                                                      `ticket_status_id` INT NOT NULL,
                                                                      `ticket_status_id1` INT NOT NULL,
                                                                      PRIMARY KEY (`id`),
                                                                      INDEX `fk_status_flow_ticket_status1_idx` (`ticket_status_id` ASC) VISIBLE,
                                                                      INDEX `fk_status_flow_ticket_status2_idx` (`ticket_status_id1` ASC) VISIBLE);


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


INSERT INTO role
values (default, 'User'),
       (default, 'Manager');



INSERT INTO user
values (default, 'Vova123', 'e6dac66f9c7513b27ff95899ad70807b5657b2407e8f6938b63c90dea01976bd', 'Vova1', 'Muradin1', '1111111111', 2),
       (default, 'Ivan234', '1223c7b48a508cb5cedead8e9b28a4f10fa713c2586f7a99259edd256938ae3e', 'Іван', 'Іванович', '1111111111', 1),
       (default, 'Vova345', '1111', 'Vova3', 'Muradin3', '1111111111', 1);



INSERT INTO train
values (201, 10),
       (202, 15),
       (203, 20),
       (204, 25);

insert into schedule
values (default, current_date, 201),
       (default, current_date + 1, 201),
       (default, current_date, 202),
       (default, current_date + 1, 202),
       (default, current_date, 203),
       (default, current_date + 1, 203),
       (default, current_date + 1, 204),
       (default, current_date + 3, 204);

insert into station
values (default, 'Vesele'),
       (default, 'Kalunovka'),
       (default, 'Olenivka'),
       (default, 'Shalene'),
       (default, 'Mucolaivka'),
       (default, 'Chornobaivka'),
       (default, 'Veres');

insert into route
values (default, 1, 1, 2, '10:00', '11:30', 10, 1, 1, 201, 21.0),
       (default, 2, 2, 4, '11:35', '12:20', 10, 1, 1, 201, 15.5),
       (default, 3, 4, 6, '12:25', '13:00', 10, 1, 1, 201, 12.5),
       (default, 4, 6, 7, '14:10', '14:55', 10, 1, 1, 201, 15.0),

       (default, 1, 1, 3, '9:00', '10:10', 15, 1, 3, 202, 20.5),
       (default, 2, 3, 5, '10:15', '11:15', 15, 1, 3, 202, 18.0),
       (default, 3, 5, 6, '11:20', '12:25', 15, 1, 3, 202, 20.5),
       (default, 4, 6, 7, '12:30', '14:45', 15, 1, 3, 202, 21.0),

       (default, 1, 1, 2, '16:30', '17:45', 20, 1, 5, 203, 20.5),
       (default, 2, 2, 4, '17:50', '18:30', 20, 1, 5, 203, 16.5),
       (default, 3, 4, 6, '18:35', '19:10', 20, 1, 5, 203, 16.5),
       (default, 4, 6, 5, '19:15', '20:10', 20, 1, 5, 203, 21.0),

       (default, 1, 7, 6, '20:30', '21:45', 20, 1, 7, 204, 21.0),
       (default, 1, 6, 5, '21:50', '22:30', 20, 1, 7, 204, 16.5),
       (default, 1, 5, 3, '22:40', '23:40', 20, 1, 7, 204, 18.0),
       (default, 1, 3, 2, '23:45', '00:30', 20, 2, 7, 204, 16.5),
       (default, 1, 2, 1, '00:35', '02:05', 20, 2, 7, 204, 21.0);

insert into ticket_status
values (default, 'QUEUED'),
       (default, 'CLOSED');

insert into status_flow
values (default, 1, 2);

insert into ticket
values (default, 100.2, 1, 7, current_timestamp, current_timestamp + 1, 201,1, 1),
       (default, 150.2, 7, 1, current_timestamp - 2, current_timestamp - 1,204, 2, 2);

insert into connecting_stations
values (1, 2),
       (1, 3),
       (2, 1),
       (2, 3),
       (2, 4),
       (3, 1),
       (3, 2),
       (3, 4),
       (3, 5),
       (4, 2),
       (4, 3),
       (4, 6),
       (5, 3),
       (5, 6),
       (6, 4),
       (6, 5),
       (6, 7),
       (7, 6);