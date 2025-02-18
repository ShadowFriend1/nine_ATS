CREATE TABLE Sale (SaleID int AUTO_INCREMENT, TravelAgentCode int DEFAULT 0 NOT NULL, CustomerAlias varchar(10), CommissionRatesRate float DEFAULT 0 NOT NULL, BlankStockID bigint NOT NULL, payment float NOT NULL, PaymentType int NOT NULL, ExchangeRatesDate date, ExchangeRatesCode varchar(3), CardNumber bigint, CardName varchar(255), SaleDate date NOT NULL, LocalTax float, OtherTax float, PRIMARY KEY (SaleID));

CREATE TABLE TravelAgent (SysAccountCode int, FirstName varchar(20) NOT NULL, LastName varchar(20) NOT NULL, PRIMARY KEY (SysAccountCode));

CREATE TABLE CustomerAccount (Alias varchar(10) NOT NULL, CustomerEmail varchar(255) , FirstName varchar(20) NOT NULL, LastName varchar(20) NOT NULL, Type int NOT NULL, DiscountID int, PRIMARY KEY (Alias));

CREATE TABLE Discount(DiscountID int AUTO_INCREMENT, Type int NOT NULL, PRIMARY KEY (DiscountID));

CREATE TABLE FixedDiscount(FixedID int AUTO_INCREMENT, DiscountID int NOT NULL UNIQUE, DiscountValue float NOT NULL, PRIMARY KEY (FixedID));

CREATE TABLE FlexibleBand(FlexibleID int AUTO_INCREMENT, DiscountID int NOT NULL, LowerBound float, UpperBound float, DiscountValue float NOT NULL, PRIMARY KEY (FlexibleID));

CREATE TABLE SysAccount (Code int NOT NULL, UserName varchar(15) NOT NULL, PasswordHash VARBINARY(1024) NOT NULL, Type int NOT NULL, PRIMARY KEY (Code));

CREATE TABLE CommissionRates (Rate float NOT NULL, CommissionDate date NOT NULL, Active int NOT NULL, PRIMARY KEY (Rate));

CREATE TABLE ExchangeRates (ExchangeDate date NOT NULL, Code varchar(3) NOT NULL, Rate double NOT NULL, PRIMARY KEY (ExchangeDate, Code));

CREATE TABLE BlankStock (ID bigint UNIQUE, Type int NOT NULL, TravelAgentCode int, AssignedDate date, MCOText varchar(255), Date date NOT NULL, PRIMARY KEY (ID));

ALTER TABLE Sale ADD CONSTRAINT FKSale604739 FOREIGN KEY (CustomerAlias) REFERENCES CustomerAccount (Alias) ON UPDATE CASCADE ON DELETE SET NULL;

ALTER TABLE TravelAgent ADD CONSTRAINT FKTravelAgen745141 FOREIGN KEY (SysAccountCode) REFERENCES SysAccount (Code) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE Sale ADD CONSTRAINT FKSale537230 FOREIGN KEY (ExchangeRatesDate, ExchangeRatesCode) REFERENCES ExchangeRates (ExchangeDate, Code) ON UPDATE CASCADE ON DELETE SET NULL;

ALTER TABLE Sale ADD CONSTRAINT  FKSale861056 FOREIGN KEY (CommissionRatesRate) REFERENCES CommissionRates (Rate) ON UPDATE CASCADE;

ALTER TABLE Sale ADD CONSTRAINT FKSale847210 FOREIGN KEY (BlankStockID) REFERENCES BlankStock (ID) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE CustomerAccount ADD CONSTRAINT FKCustomerAc123123 FOREIGN KEY (DiscountID) REFERENCES Discount (DiscountID) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE FixedDiscount ADD CONSTRAINT FKFixedDisco545201 FOREIGN KEY (DiscountID) REFERENCES Discount (DiscountID) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE FlexibleBand ADD CONSTRAINT FKFlexibleBan579108 FOREIGN KEY (DiscountID) REFERENCES Discount (DiscountID) ON UPDATE CASCADE ON DELETE CASCADE;

DELIMITER //
/* finds lowest value */
CREATE FUNCTION AirVia.MinVal(a bigint, b bigint)
RETURNS bigint
BEGIN
    IF a <= b THEN
        RETURN a;
    ELSE
        RETURN b;
    end if;
end //

/* finds highest value */
//
CREATE FUNCTION AirVia.MaxVal(a bigint, b bigint)
RETURNS bigint
BEGIN
    IF a <= b THEN
        RETURN b;
    ELSE
        RETURN a;
    end if;
end //

//
/* Store encrypted password and account information */
CREATE PROCEDURE AirVia.AddUser (
    IN ICode INT,
    IN IUserName VARCHAR(15),
    IN IPassword VarChar(20),
    IN IType INT,
    OUT Response varchar(255)
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
        BEGIN
            SHOW ERRORS;
            ROLLBACK;
        end;

    IF NOT EXISTS(SELECT Code FROM SysAccount WHERE Code=ICode) THEN
        INSERT INTO AirVia.SysAccount (Code, UserName, PasswordHash, Type)
        VALUES(ICode, IUserName, aes_encrypt(IPassword, 'catdog'), IType);
        SET Response = 'System Account Created';
    ELSE
        UPDATE SysAccount
        SET UserName=IUserName, PasswordHash=aes_encrypt(IPassword, 'catdog'), Type=IType
        WHERE Code=ICode;
        SET Response = 'System Account Created';
    end if;
end //

//
/* Create a new advisor account */
CREATE PROCEDURE AirVia.AddAdvisor (
    IN ICode INT,
    IN IUserName VARCHAR(15),
    IN IPassword VarChar(20),
    IN IFirstName varchar(20),
    IN ILastName varchar(20),
    OUT Response varchar(255)
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
        BEGIN
            SHOW ERRORS;
            ROLLBACK;
        end;

    call AddUser(ICode, IUserName, IPassword, 0, @a);

    IF NOT EXISTS(SELECT SysAccountCode FROM TravelAgent WHERE SysAccountCode=ICode) THEN
        INSERT INTO AirVia.TravelAgent (SysAccountCode, FirstName, LastName)
        VALUES (ICode, IFirstName, ILastName);
        SET Response = 'Advisor Account Created';
    ELSE
        UPDATE TravelAgent
        SET FirstName=IFirstName, LastName=ILastName
        WHERE SysAccountCode=ICode;
        SET Response = 'Advisor Account Updated';
    end if;
end //

//
/* Attempt to match login info to database account and return account type */
CREATE PROCEDURE AirVia.Login (
    IN IUserName VARCHAR(15),
    IN IPassword VARCHAR(20),
    OUT IResponseMessage INT
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
        BEGIN
            SHOW ERRORS;
            ROLLBACK;
        end;


    SET IResponseMessage = 999;

    IF EXISTS (SELECT PasswordHash FROM SysAccount WHERE UserName=IUserName) THEN
        IF EXISTS(SELECT PasswordHash FROM SysAccount WHERE UserName=IUserName AND aes_decrypt(PasswordHash, 'catdog')=IPassword) THEN
            /* Successful login */
            SET IResponseMessage = (SELECT Type FROM SysAccount WHERE UserName=IUserName AND aes_decrypt(PasswordHash, 'catdog')=IPassword LIMIT 1);
        ELSE
            /* Password invalid */
            SET IResponseMessage = 222;
        end if;
    ELSE
        /* Username invalid */
        SET IResponseMessage = 111;
    end if;
end //

//
/* Add a customer account onto the system */
CREATE PROCEDURE AirVia.AddCustomer (
    IN IAlias varchar(10),
    IN IEmail varchar(255),
    IN IFirstName varchar(20),
    IN ILastName varchar(20),
    IN IType int,
    OUT Response varchar(255)
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
        BEGIN
            SHOW ERRORS;
            ROLLBACK;
        end;

    IF NOT EXISTS(SELECT Alias FROM CustomerAccount WHERE Alias=IAlias) THEN
        INSERT INTO CustomerAccount(Alias, CustomerEmail, FirstName, LastName, Type)
        VALUES (IAlias, IEmail, IFirstName, ILastName, IType);
        SET Response = 'Customer Account Created';
    ELSE
        UPDATE CustomerAccount
        SET CustomerEmail=IEmail, FirstName=IFirstName, LastName=ILastName, Type=IType
        WHERE Alias=IAlias;
        SET Response = 'Customer Account Updated';
    end if;
end //

//
/* Add fixed discount to a customer account */
CREATE PROCEDURE AirVia.AddFixedDiscount (
    IN IAlias varchar(10),
    IN Value float,
    OUT Response varchar(255)
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
        BEGIN
            SHOW ERRORS;
            ROLLBACK;
        end;

    IF EXISTS(SELECT Alias FROM CustomerAccount WHERE Alias=IAlias) THEN
        INSERT INTO Discount (Type) VALUES (0);
        UPDATE CustomerAccount
        SET DiscountID=last_insert_id()
        WHERE Alias=IAlias;
        INSERT INTO FixedDiscount (DiscountID, DiscountValue)
        VALUES (last_insert_id(), Value);
        SET Response = 'Fixed Discount Created';
    end if;
end //

//
/* Add flexible discount to a customer account */
CREATE PROCEDURE AirVia.AddFlexibleDiscount (
    IN IAlias varchar(10),
    IN Lower int,
    IN Upper int,
    IN Value float,
    OUT Response varchar(255)
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
        BEGIN
            SHOW ERRORS;
            ROLLBACK;
        end;

    IF EXISTS(SELECT Alias FROM CustomerAccount WHERE Alias=IAlias) THEN
        IF (SELECT DiscountID FROM CustomerAccount WHERE Alias=IAlias LIMIT 1) IS NULL THEN
            INSERT INTO Discount (Type) VALUES (1);
            UPDATE CustomerAccount
            SET DiscountID=last_insert_id()
            WHERE Alias=IAlias;
            INSERT INTO FlexibleBand (DiscountID, UpperBound, LowerBound, DiscountValue)
            VALUES (last_insert_id(), Upper, Lower, Value);
            SET Response = 'Flexible Discount Created';
        ELSE
            INSERT INTO FlexibleBand (DiscountID, UpperBound, LowerBound, DiscountValue)
            VALUES ((SELECT DiscountID FROM CustomerAccount WHERE Alias=IAlias), Upper, Lower, Value);
            SET Response = 'Flexible Discount Updated';
        end if;
    end if;
end //

//
/* Add blanks onto the system */
CREATE PROCEDURE AirVia.AddBlanks (
    IN StartBlank BIGINT,
    IN EndBlank BIGINT,
    IN CurrentDate DATE,
    OUT Response varchar(255)
)
BEGIN
    DECLARE Counter BIGINT;
    DECLARE BlankType int;
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
        BEGIN
            SET Response = 'Error adding blanks';
            SHOW ERRORS;
            ROLLBACK;
        end;

    SET Counter = StartBlank;
    SET BlankType = LEFT(StartBlank, 3);

    REPEAT
        INSERT INTO BlankStock(ID, Type, Date) VALUES(Counter, BlankType, CurrentDate);
        SET Counter = Counter + 1;
    UNTIL Counter > EndBlank
    END REPEAT;
    SET Response = 'Blanks Successfully Added';
end //

//
/* deletes blanks from the database */
CREATE PROCEDURE AirVia.DeleteBlanks (
    IN StartBlank BIGINT,
    IN EndBlank BIGINT,
    OUT Response varchar(255)
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
        BEGIN
            SET Response = 'Error deleting blanks';
            SHOW ERRORS;
            ROLLBACK;
        end;

    DELETE FROM BlankStock WHERE ID BETWEEN StartBlank AND EndBlank AND TravelAgentCode IS NULL;
    SET Response = 'Unused Blanks Successfully Deleted';
end //

//
/* assign blanks to advisors */
CREATE PROCEDURE AirVia.AssignBlanks (
    IN StartBlank BIGINT,
    IN EndBlank BIGINT,
    IN ICode int,
    IN IDate date,
    OUT Response varchar(255)
)
BEGIN
    DECLARE Counter BIGINT;

    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        SHOW ERRORS;
        ROLLBACK;
    end;

    SET Counter = StartBlank;

    IF EXISTS(SELECT SysAccountCode FROM TravelAgent WHERE SysAccountCode=ICode) THEN
        REPEAT
            UPDATE BlankStock
            SET TravelAgentCode = ICode, AssignedDate = IDate
            WHERE ID = Counter;
            SET Counter = Counter + 1;
        UNTIL Counter > EndBlank
        END REPEAT;
        SET Response = 'Blanks Assigned';
    ELSE
        SET Response = 'No such advisor';
    end if;
end //

//
/* Log delayed sale */
CREATE PROCEDURE AirVia.MakeSaleDelayed (
    IN BlankID bigint,
    IN ICode int,
    IN ILocalTax float,
    IN IOtherTax float,
    IN IAlias varchar(10),
    IN Commission float,
    IN IPayment float,
    IN IExchangeCode varchar(3),
    IN ICurrentDate Date
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
        BEGIN
            SHOW ERRORS;
            ROLLBACK;
        end;

    INSERT INTO Sale (customeralias, TravelAgentCode, blankstockid, payment, paymenttype, exchangeratesdate,
                      exchangeratescode, saledate, LocalTax, OtherTax, CommissionRatesRate)
    VALUES (IAlias, ICode, BlankID, IPayment, 0,
            (SELECT ExchangeDate FROM ExchangeRates WHERE Code=IExchangeCode ORDER BY ExchangeDate LIMIT 1),
            IExchangeCode, ICurrentDate, ILocalTax, IOtherTax, Commission);
end //

//
/* Log cash sale information in table */
CREATE PROCEDURE AirVia.MakeSaleCash (
    IN BlankID bigint,
    IN ICode int,
    IN ILocalTax float,
    IN IOtherTax float,
    IN IAlias varchar(10),
    IN Commission float,
    IN IPayment float,
    IN IExchangeCode varchar(3),
    IN ICurrentDate Date
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
        BEGIN
            SHOW ERRORS;
            ROLLBACK;
        end;

    INSERT INTO Sale (customeralias, TravelAgentCode, blankstockid, payment, paymenttype, exchangeratesdate,
                      exchangeratescode, saledate, LocalTax, OtherTax, CommissionRatesRate)
    VALUES (IAlias, ICode, BlankID, IPayment, 1,
            (SELECT ExchangeDate FROM ExchangeRates WHERE Code=IExchangeCode ORDER BY ExchangeDate LIMIT 1),
            IExchangeCode, ICurrentDate, ILocalTax, IOtherTax, Commission);
end //

//
/* Log card sale information in table */
CREATE PROCEDURE AirVia.MakeSaleCard (
    IN BlankID bigint,
    IN ICode int,
    IN ILocalTax float,
    IN IOtherTax float,
    IN IPayment float,
    IN IAlias varchar(10),
    IN Commission float,
    IN ICardNumber bigint,
    IN ICardType varchar(10),
    IN IExchangeCode varchar(3),
    IN ICurrentDate Date
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
        BEGIN
            SHOW ERRORS;
            ROLLBACK;
        end;

    INSERT INTO Sale (customeralias, TravelAgentCode, blankstockid, payment, paymenttype, exchangeratesdate, exchangeratescode,
                      saledate, LocalTax, OtherTax, CommissionRatesRate, CardName, CardNumber)
    VALUES (IAlias, ICode, BlankID, IPayment, 2,
            (SELECT ExchangeDate FROM ExchangeRates WHERE Code=IExchangeCode ORDER BY ExchangeDate),
            IExchangeCode, ICurrentDate, ILocalTax, IOtherTax, Commission, ICardType, ICardNumber);
end //

/* reports */
//
/* Stock turnover report */
CREATE PROCEDURE AirVia.StockTurnover(
    IN startDate DATE,
    IN currentDate DATE
)
BEGIN
    CREATE TEMPORARY TABLE temp1(Code int, ID bigint, Type int);
    INSERT INTO temp1(Code, ID, Type)  SELECT Sale.TravelAgentCode, BlankStockID, Type
    FROM Sale INNER JOIN BlankStock ON Sale.BlankStockID = BlankStock.ID WHERE startDate <= AssignedDate <= currentDate;

    CREATE TEMPORARY TABLE temp2(Code int, ID bigint, Type int);
    INSERT INTO temp2(Code, ID, Type) SELECT BlankStock.TravelAgentCode, ID, Type
    FROM BlankStock WHERE BlankStock.TravelAgentCode IS NOT NULL AND startDate <= AssignedDate <= currentDate;

    CREATE TEMPORARY TABLE temp3(SoldCode int, SoldStartBlank bigint, SoldEndBlank bigint, SoldAmount int, SoldType int);
    INSERT INTO temp3(SoldCode, SoldStartBlank, SoldEndBlank, SoldAmount, SoldType)  
    SELECT Code, MIN(ID), MAX(ID), COUNT(*), Type FROM temp1 GROUP BY Code, Type;

    CREATE TEMPORARY TABLE temp3_2(SoldCode int, SoldStartBlank bigint, SoldEndBlank bigint, SoldAmount int, SoldType int);
    INSERT INTO temp3_2(SoldCode, SoldStartBlank, SoldEndBlank, SoldAmount, SoldType)  
    SELECT Code, MIN(ID), MAX(ID), COUNT(*), Type FROM temp1 GROUP BY Code, Type;

    CREATE TEMPORARY TABLE temp4(AssCode int, AssStartBlank bigint, AssEndBlank bigint, AssAmount int, AssType int);
    INSERT INTO temp4(AssCode, AssStartBlank, AssEndBlank, AssAmount, AssType)
    SELECT Code, MIN(ID), MAX(ID), COUNT(*), Type FROM temp2 GROUP BY Code, Type;

    CREATE TEMPORARY TABLE temp4_2(AssCode int, AssStartBlank bigint, AssEndBlank bigint, AssAmount int, AssType int);
    INSERT INTO temp4_2(AssCode, AssStartBlank, AssEndBlank, AssAmount, AssType) 
    SELECT Code, MIN(ID), MAX(ID), COUNT(*), Type FROM temp2 GROUP BY Code, Type;

    CREATE TEMPORARY TABLE temp5(Code int, AssStartBlank bigint, AssEndBlank bigint, AssAmount int, Type int,
    SoldStartBlank bigint, SoldEndBlank bigint, SoldAmount int, AdvisorAmount int);
    INSERT INTO temp5(Code, AssStartBlank, AssEndBlank, AssAmount, Type, SoldStartBlank, SoldEndBlank, SoldAmount, AdvisorAmount)
    SELECT AssCode, AssStartBlank, AssEndBlank, AssAmount, AssType, SoldStartBlank, SoldEndBlank,
    SoldAmount, (AssAmount-SoldAmount)
    FROM temp4 LEFT JOIN temp3 ON SoldCode=AssCode AND SoldType=AssType;

    SELECT * FROM temp5;

    CREATE TEMPORARY TABLE temp6(StartBlank bigint, EndBlank bigint, Amount int, Type int);
    INSERT INTO temp6(StartBlank, EndBlank, Amount, Type)
    SELECT MIN(ID), MAX(ID), COUNT(*), Type FROM BlankStock WHERE startDate <= Date <= currentDate GROUP BY Type;

    CREATE TEMPORARY TABLE temp7(RecStartBlank bigint, RecEndBlank bigint, RecAmount int, Type int, FinalAmount int);
    INSERT INTO temp7(RecStartBlank, RecEndBlank, RecAmount, Type, FinalAmount)
    SELECT StartBlank, EndBlank, Amount, Type, (Amount-SoldAmount) FROM temp6 INNER JOIN temp3 ON SoldType=Type;

    SELECT * FROM temp7;

    DROP TABLE temp1;
    DROP TABLE temp2;
    DROP TABLE temp3;
    DROP TABLE temp3_2;
    DROP TABLE temp4;
    DROP TABLE temp4_2;
    DROP TABLE temp5;
    DROP TABLE temp6;
    DROP TABLE temp7;
end //
DELIMITER ;