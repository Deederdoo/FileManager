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

CREATE TABLE `filemanagerdb`.`documents` (
  `DocID` INT NOT NULL AUTO_INCREMENT,
  `Type` VARCHAR(26) NOT NULL,
  `ByteData` VARCHAR(666) NOT NULL,
  `Name` VARCHAR(45) NOT NULL,
  `Info` VARCHAR(45) NULL,
  `UserID` INT NULL,
  PRIMARY KEY (`DocID`),
  UNIQUE INDEX `DocID_UNIQUE` (`DocID` ASC) VISIBLE,
  UNIQUE INDEX `Name_UNIQUE` (`Name` ASC) VISIBLE,
  INDEX `UserID_idx_doc` (`UserID` ASC) VISIBLE,
  CONSTRAINT `UserID_Doc`
    FOREIGN KEY (`UserID`)
    REFERENCES `filemanagerdb`.`user` (`UserID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
CREATE TABLE `filemanagerdb`.`pictures` (
  `PicID` INT NOT NULL AUTO_INCREMENT,
  `Type` VARCHAR(26) NOT NULL,
  `ByteData` VARCHAR(666) NOT NULL,
  `Name` VARCHAR(45) NOT NULL,
  `Info` VARCHAR(45) NULL,
  `UserID` INT NULL,
  PRIMARY KEY (`PicID`),
  UNIQUE INDEX `PicID_UNIQUE` (`PicID` ASC) VISIBLE,
  UNIQUE INDEX `Name_UNIQUE` (`Name` ASC) VISIBLE,
  INDEX `UserID_idx_pic` (`UserID` ASC) VISIBLE,
  CONSTRAINT `UserID_Pic`
    FOREIGN KEY (`UserID`)
    REFERENCES `filemanagerdb`.`user` (`UserID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
CREATE TABLE `filemanagerdb`.`music` (
  `MusicID` INT NOT NULL AUTO_INCREMENT,
  `Type` VARCHAR(26) NOT NULL,
  `ByteData` VARCHAR(666) NOT NULL,
  `Name` VARCHAR(45) NOT NULL,
  `Info` VARCHAR(45) NULL,
  `UserID` INT NULL,
  PRIMARY KEY (`MusicID`),
  UNIQUE INDEX `MusicID_UNIQUE` (`MusicID` ASC) VISIBLE,
  UNIQUE INDEX `Name_UNIQUE` (`Name` ASC) VISIBLE,
  INDEX `UserID_idx_mus` (`UserID` ASC) VISIBLE,
  CONSTRAINT `UserID_Music`
    FOREIGN KEY (`UserID`)
    REFERENCES `filemanagerdb`.`user` (`UserID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
CREATE TABLE `filemanagerdb`.`movies` (
  `MovieID` INT NOT NULL AUTO_INCREMENT,
  `Type` VARCHAR(26) NOT NULL,
  `ByteData` VARCHAR(666) NOT NULL,
  `Name` VARCHAR(45) NOT NULL,
  `Info` VARCHAR(45) NULL,
  `UserID` INT NULL,
  PRIMARY KEY (`MovieID`),
  UNIQUE INDEX `MovieID_UNIQUE` (`MovieID` ASC) VISIBLE,
  UNIQUE INDEX `Name_UNIQUE` (`Name` ASC) VISIBLE,
  INDEX `UserID_idx_mov` (`UserID` ASC) VISIBLE,
  CONSTRAINT `UserID_Mov`
    FOREIGN KEY (`UserID`)
    REFERENCES `filemanagerdb`.`user` (`UserID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
CREATE TABLE `filemanagerdb`.`misc` (
  `MiscID` INT NOT NULL AUTO_INCREMENT,
  `Type` VARCHAR(26) NOT NULL,
  `ByteData` VARCHAR(666) NOT NULL,
  `Name` VARCHAR(45) NOT NULL,
  `Info` VARCHAR(45) NULL,
  `UserID` INT NULL,
  PRIMARY KEY (`MiscID`),
  UNIQUE INDEX `MiscID_UNIQUE` (`MiscID` ASC) VISIBLE,
  UNIQUE INDEX `Name_UNIQUE` (`Name` ASC) VISIBLE,
  INDEX `UserID_idx_mis` (`UserID` ASC) VISIBLE,
  CONSTRAINT `UserID_Misc`
    FOREIGN KEY (`UserID`)
    REFERENCES `filemanagerdb`.`user` (`UserID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);