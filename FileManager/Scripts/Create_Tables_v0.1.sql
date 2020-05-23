USE filemanagerdb;

CREATE TABLE `filemanagerdb`.`user` (
  `UserID` INT NOT NULL AUTO_INCREMENT,
  `Username` VARCHAR(45) NOT NULL,
  `Password` VARCHAR(45) NOT NULL,
  `Email` VARCHAR(45) NULL,
  `Info` VARCHAR(45) NULL,
  PRIMARY KEY (`UserID`),
  UNIQUE INDEX `Username_UNIQUE` (`Username` ASC) VISIBLE,
  UNIQUE INDEX `Email_UNIQUE` (`Email` ASC) VISIBLE);

CREATE TABLE `filemanagerdb`.`global` (
  `g_id` INT NOT NULL AUTO_INCREMENT,
  `type` VARCHAR(26) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`g_id`));

CREATE TABLE `filemanagerdb`.`documents` (
  `DocID` INT NOT NULL AUTO_INCREMENT,
  `Type` VARCHAR(26) NOT NULL,
  `ByteData` LONGBLOB NOT NULL,
  `Name` VARCHAR(45) NOT NULL,
  `Info` VARCHAR(45) NULL,
  `UserID` INT NULL,
  `g_id` INT NOT NULL,
  PRIMARY KEY (`DocID`),
  UNIQUE INDEX `DocID_UNIQUE` (`DocID` ASC) VISIBLE,
  UNIQUE INDEX `Name_UNIQUE` (`Name` ASC) VISIBLE,
  INDEX `UserID_idx_doc` (`UserID` ASC) VISIBLE,
  INDEX `g_id_idx_doc` (`g_id` ASC) VISIBLE,
  CONSTRAINT `UserID_Doc`
    FOREIGN KEY (`UserID`)
    REFERENCES `filemanagerdb`.`user` (`UserID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `g_id_doc`
    FOREIGN KEY (`g_id`)
    REFERENCES `filemanagerdb`.`global` (`g_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
CREATE TABLE `filemanagerdb`.`pictures` (
  `PicID` INT NOT NULL AUTO_INCREMENT,
  `Type` VARCHAR(26) NOT NULL,
  `ByteData` LONGBLOB NOT NULL,
  `Name` VARCHAR(45) NOT NULL,
  `Info` VARCHAR(45) NULL,
  `UserID` INT NULL,
  `g_id` INT NOT NULL,
  PRIMARY KEY (`PicID`),
  UNIQUE INDEX `PicID_UNIQUE` (`PicID` ASC) VISIBLE,
  UNIQUE INDEX `Name_UNIQUE` (`Name` ASC) VISIBLE,
  INDEX `UserID_idx_pic` (`UserID` ASC) VISIBLE,
  INDEX `g_id_idx_pic` (`g_id` ASC) VISIBLE,
  CONSTRAINT `UserID_Pic`
    FOREIGN KEY (`UserID`)
    REFERENCES `filemanagerdb`.`user` (`UserID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `g_id_pic`
    FOREIGN KEY (`g_id`)
    REFERENCES `filemanagerdb`.`global` (`g_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
CREATE TABLE `filemanagerdb`.`music` (
  `MusicID` INT NOT NULL AUTO_INCREMENT,
  `Type` VARCHAR(26) NOT NULL,
  `ByteData` LONGBLOB NOT NULL,
  `Name` VARCHAR(45) NOT NULL,
  `Info` VARCHAR(45) NULL,
  `UserID` INT NULL,
  `g_id` INT NOT NULL,
  PRIMARY KEY (`MusicID`),
  UNIQUE INDEX `MusicID_UNIQUE` (`MusicID` ASC) VISIBLE,
  UNIQUE INDEX `Name_UNIQUE` (`Name` ASC) VISIBLE,
  INDEX `UserID_idx_mus` (`UserID` ASC) VISIBLE,
  INDEX `g_id_idx_mus` (`g_id` ASC) VISIBLE,
  CONSTRAINT `UserID_Music`
    FOREIGN KEY (`UserID`)
    REFERENCES `filemanagerdb`.`user` (`UserID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `g_id_music`
    FOREIGN KEY (`g_id`)
    REFERENCES `filemanagerdb`.`global` (`g_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
CREATE TABLE `filemanagerdb`.`videos` (
  `VideoID` INT NOT NULL AUTO_INCREMENT,
  `Type` VARCHAR(26) NOT NULL,
  `ByteData` LONGBLOB NOT NULL,
  `Name` VARCHAR(45) NOT NULL,
  `Info` VARCHAR(45) NULL,
  `UserID` INT NULL,
  `g_id` INT NOT NULL,
  PRIMARY KEY (`VideoID`),
  UNIQUE INDEX `VideoID_UNIQUE` (`VideoID` ASC) VISIBLE,
  UNIQUE INDEX `Name_UNIQUE` (`Name` ASC) VISIBLE,
  INDEX `UserID_idx_vid` (`UserID` ASC) VISIBLE,
  INDEX `g_id_idx_vid` (`g_id` ASC) VISIBLE,
  CONSTRAINT `UserID_Vid`
    FOREIGN KEY (`UserID`)
    REFERENCES `filemanagerdb`.`user` (`UserID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `g_id_vid`
    FOREIGN KEY (`g_id`)
    REFERENCES `filemanagerdb`.`global` (`g_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
CREATE TABLE `filemanagerdb`.`misc` (
  `MiscID` INT NOT NULL AUTO_INCREMENT,
  `Type` VARCHAR(26) NOT NULL,
  `ByteData` LONGBLOB NOT NULL,
  `Name` VARCHAR(45) NOT NULL,
  `Info` VARCHAR(45) NULL,
  `UserID` INT NULL,
  `g_id` INT NOT NULL,
  PRIMARY KEY (`MiscID`),
  UNIQUE INDEX `MiscID_UNIQUE` (`MiscID` ASC) VISIBLE,
  UNIQUE INDEX `Name_UNIQUE` (`Name` ASC) VISIBLE,
  INDEX `UserID_idx_mis` (`UserID` ASC) VISIBLE,
  INDEX `g_id_idx_mis` (`g_id` ASC) VISIBLE,
  CONSTRAINT `UserID_Misc`
    FOREIGN KEY (`UserID`)
    REFERENCES `filemanagerdb`.`user` (`UserID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `g_id_misc`
    FOREIGN KEY (`g_id`)
    REFERENCES `filemanagerdb`.`global` (`g_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);