-- -----------------------------------------------------
-- Table `fastlane`.`member`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fastlane`.`member` (
    `member_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
    `create_date` DATETIME NULL DEFAULT NULL,
    `login_id` VARCHAR(255) NULL DEFAULT NULL,
    `password` VARCHAR(255) NULL DEFAULT NULL,
    `refresh_token` VARCHAR(1000) NULL DEFAULT NULL,
    `refresh_token_valid_date` DATETIME NULL DEFAULT NULL,
    PRIMARY KEY (`member_id`),
    UNIQUE INDEX `uk-login_id` (`login_id` ASC))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `fastlane`.`member_role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fastlane`.`member_role` (
    `member_id` BIGINT(20) NOT NULL,
    `roles` VARCHAR(255) NOT NULL,
    INDEX `fk-member-to-member_role-1` (`member_id` ASC),
    CONSTRAINT `fk-member-to-member_role-1`
    FOREIGN KEY (`member_id`)
    REFERENCES `fastlane`.`member` (`member_id`))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;