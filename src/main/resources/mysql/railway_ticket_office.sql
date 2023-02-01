-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema railway_ticket_office
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema railway_ticket_office
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `railway_ticket_office` DEFAULT CHARACTER SET utf8 ;
USE `railway_ticket_office` ;

-- -----------------------------------------------------
-- Table `railway_ticket_office`.`role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `railway_ticket_office`.`role` ;

CREATE TABLE IF NOT EXISTS `railway_ticket_office`.`role` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`));


-- -----------------------------------------------------
-- Table `railway_ticket_office`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `railway_ticket_office`.`user` ;

CREATE TABLE IF NOT EXISTS `railway_ticket_office`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `login` VARCHAR(32) NOT NULL,
  `password` VARCHAR(66) NOT NULL,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `phone` VARCHAR(16) NOT NULL,
  `role_id` INT NOT NULL,
  PRIMARY KEY (`id`, `role_id`),
  UNIQUE INDEX `login_UNIQUE` (`login` ASC) VISIBLE,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_user_role1_idx` (`role_id` ASC) VISIBLE);


-- -----------------------------------------------------
-- Table `railway_ticket_office`.`train`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `railway_ticket_office`.`train` ;

CREATE TABLE IF NOT EXISTS `railway_ticket_office`.`train` (
  `number` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `seats` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`number`),
  UNIQUE INDEX `train_number_UNIQUE` (`number` ASC) VISIBLE);


-- -----------------------------------------------------
-- Table `railway_ticket_office`.`station`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `railway_ticket_office`.`station` ;

CREATE TABLE IF NOT EXISTS `railway_ticket_office`.`station` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`));


-- -----------------------------------------------------
-- Table `railway_ticket_office`.`schedule`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `railway_ticket_office`.`schedule` ;

CREATE TABLE IF NOT EXISTS `railway_ticket_office`.`schedule` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `date` DATE NOT NULL,
  `train_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`, `train_id`),
  INDEX `fk_schedule_train1_idx` (`train_id` ASC) VISIBLE);


-- -----------------------------------------------------
-- Table `railway_ticket_office`.`route`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `railway_ticket_office`.`route` ;

CREATE TABLE IF NOT EXISTS `railway_ticket_office`.`route` (
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
-- Table `railway_ticket_office`.`ticket_status`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `railway_ticket_office`.`ticket_status` ;

CREATE TABLE IF NOT EXISTS `railway_ticket_office`.`ticket_status` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(32) NOT NULL,
  PRIMARY KEY (`id`));


-- -----------------------------------------------------
-- Table `railway_ticket_office`.`ticket`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `railway_ticket_office`.`ticket` ;

CREATE TABLE IF NOT EXISTS `railway_ticket_office`.`ticket` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `fare` DECIMAL(8,2) UNSIGNED NOT NULL,
  `starting_station` VARCHAR(45) NOT NULL,
  `final_station` VARCHAR(45) NOT NULL,
  `departure_time` TIMESTAMP NOT NULL,
  `arrival_time` TIMESTAMP NOT NULL,
  `train_number` INT UNSIGNED NOT NULL,
  `user_id` INT NOT NULL,
  `ticket_status_id` INT NOT NULL,
  PRIMARY KEY (`id`, `user_id`, `ticket_status_id`),
  INDEX `fk_ticket_user_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_ticket_ticket_status1_idx` (`ticket_status_id` ASC) VISIBLE);


-- -----------------------------------------------------
-- Table `railway_ticket_office`.`status_flow`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `railway_ticket_office`.`status_flow` ;

CREATE TABLE IF NOT EXISTS `railway_ticket_office`.`status_flow` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `ticket_status_id` INT NOT NULL,
  `ticket_status_id1` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_status_flow_ticket_status1_idx` (`ticket_status_id` ASC) VISIBLE,
  INDEX `fk_status_flow_ticket_status2_idx` (`ticket_status_id1` ASC) VISIBLE);


-- -----------------------------------------------------
-- Table `railway_ticket_office`.`ticket_has_route`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `railway_ticket_office`.`ticket_has_route` ;

CREATE TABLE IF NOT EXISTS `railway_ticket_office`.`ticket_has_route` (
  `ticket_id` INT NOT NULL,
  `ticket_user_id` INT NOT NULL,
  `ticket_ticket_status_id` INT NOT NULL,
  `route_id` INT NOT NULL,
  `route_starting_station_id` INT NOT NULL,
  `route_final_station_id` INT NOT NULL,
  `route_schedule_id` INT NOT NULL,
  `route_train_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`ticket_id`, `ticket_user_id`, `ticket_ticket_status_id`, `route_id`, `route_starting_station_id`, `route_final_station_id`, `route_schedule_id`, `route_train_id`),
  INDEX `fk_ticket_has_route_route1_idx` (`route_id` ASC, `route_starting_station_id` ASC, `route_final_station_id` ASC, `route_schedule_id` ASC, `route_train_id` ASC) VISIBLE,
  INDEX `fk_ticket_has_route_ticket1_idx` (`ticket_id` ASC, `ticket_user_id` ASC, `ticket_ticket_status_id` ASC) VISIBLE);



CREATE EVENT IF NOT EXISTS ticket_status_changer
    ON SCHEDULE
        EVERY 1 MINUTE
    DO
    update ticket
    set ticket_status_id = 2
    where departure_time < current_timestamp
      and ticket_status_id !=3;

CREATE TRIGGER decrementSeats AFTER INSERT ON ticket_has_route
    FOR EACH ROW
    BEGIN
        update route r
        set r.available_seats = r.available_seats-1
        where r.id = NEW.route_id;
    end;

DELIMITER //
CREATE TRIGGER incrementSeats after update
              on ticket
              for each row
BEGIN
    IF ( (OLD.ticket_status_id = 1) AND (NEW.ticket_status_id = 3 or NEW.ticket_status_id = 4))
       THEN
update route r, ticket_has_route ticketRoutes
set r.available_seats = r.available_seats+1
where ticketRoutes.ticket_id=OLD.id AND ticketRoutes.route_id=r.id;
end IF;
end;




SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


INSERT INTO role
values (default, 'USER'),
       (default, 'ADMIN');

INSERT INTO user
values (default, 'Vova123', 'e6dac66f9c7513b27ff95899ad70807b5657b2407e8f6938b63c90dea01976bd', 'Vova1', 'Muradin1', '380123456789', 2),
       (default, 'Ivan234', '1223c7b48a508cb5cedead8e9b28a4f10fa713c2586f7a99259edd256938ae3e', 'Іван', 'Іванович', '380123456789', 2),
       (default, 'Petrov', 'aa784d98ec7b230f843ed6b21fddb3bf9e3f837f3b0e163df6d040824d6882ff', 'Петро', 'Петрович', '380123456789', 1);



INSERT INTO train
values (201, 10),
       (202, 15),
       (203, 20),
       (204, 25);

insert into schedule
values (default, current_date + 1, 201),
       (default, current_date + 2, 201),
       (default, current_date + 1, 202),
       (default, current_date + 2, 202),
       (default, current_date + 1, 203),
       (default, current_date, 203),
       (default, current_date + 2, 204),
       (default, current_date + 3, 204);

insert into station
values (default, 'Yablunivka'),
       (default, 'Kalunovka'),
       (default, 'Olenivka'),
       (default, 'Shalenivka'),
       (default, 'Mucolaivka'),
       (default, 'Chornobaivka'),
       (default, 'Dibrova');

insert into route
values (default, 1, 1, 2, '10:00', '11:30', 10, 0, 1, 201, 21.0),
       (default, 2, 2, 4, '11:35', '12:20', 10, 0, 1, 201, 15.5),
       (default, 3, 4, 6, '12:25', '13:00', 10, 0, 1, 201, 12.5),
       (default, 4, 6, 7, '14:10', '14:55', 10, 0, 1, 201, 15.0),

       (default, 1, 1, 3, '9:00', '10:10', 15, 0, 3, 202, 20.5),
       (default, 2, 3, 5, '10:15', '11:15', 15, 0, 3, 202, 18.0),
       (default, 3, 5, 6, '12:20', '13:25', 15, 0, 3, 202, 20.5),
       (default, 4, 6, 7, '1:30', '13:45', 15, 0, 3, 202, 21.0),

       (default, 1, 1, 2, '16:30', '17:45', 20, 0, 5, 203, 20.5),
       (default, 2, 2, 4, '17:50', '18:30', 20, 0, 5, 203, 16.5),
       (default, 3, 4, 6, '18:35', '19:10', 20, 0, 5, 203, 16.5),
       (default, 4, 6, 5, '19:15', '20:10', 20, 0, 5, 203, 21.0),

       (default, 1, 1, 2, '21:30', '22:45', 20, 0, 6, 203, 20.5),
       (default, 2, 2, 3, '22:50', '23:00', 20, 0, 6, 203, 16.5),
       (default, 3, 3, 4, '23:35', '00:35', 20, 0, 6, 203, 16.5),
       (default, 4, 4, 5, '00:40', '01:15', 20, 1, 6, 203, 21.0),
       (default, 5, 5, 6, '01:20', '02:20', 20, 1, 6, 203, 16.5),
       (default, 6, 6, 7, '02:30', '03:20', 20, 1, 6, 203, 16.5),

       (default, 1, 7, 6, '20:30', '21:45', 20, 0, 7, 204, 21.0),
       (default, 2, 6, 5, '21:50', '22:30', 20, 0, 7, 204, 16.5),
       (default, 3, 5, 3, '22:40', '23:40', 20, 0, 7, 204, 18.0),
       (default, 4, 3, 2, '23:45', '00:30', 20, 1, 7, 204, 16.5),
       (default, 5, 2, 1, '00:35', '02:05', 20, 1, 7, 204, 21.0);

insert into ticket_status
values (default, 'QUEUED'),
       (default, 'CLOSED'),
       (default, 'CANCELED'),
       (default, 'REFUNDED');

insert into status_flow
values (default, 1, 2),
       (default, 1, 3),
       (default, 1, 4);


