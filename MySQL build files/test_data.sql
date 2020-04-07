/* add users */
call AddAdvisor(250, 'PenelopePitstop', 'Pinkobile', 'Penelope', 'Pitstop', @a);
call AddAdvisor(211, 'DennisMenace', 'Gnasher', 'Dennis', 'Menace', @a);
call AddUser(220, 'MinnieMinx', 'NotiGirl', 1, @a);
call AddUser(320, 'ArthurDaley', 'LiesaLot', 2, @a);

/* add blank stock */
call AddBlanks(44400000001, 44400000100, DATE('2019-04-01'), @a);
call AddBlanks(42000000001, 42000000100, DATE('2019-05-08'), @a);
call AddBlanks(20100000001, 20100000100, DATE('2019-06-03'), @a);
call AddBlanks(10100000001, 10100000050, DATE('2019-07-09'), @a);

/* add customer accounts */
call AddCustomer('Chris', '', 'Chris', 'Smart', 2, @a);
call AddCustomer('DaveD', '', 'David', 'Dodson', 2, @a);
call AddCustomer('SarahB', '', 'Sarah', 'Broklehurst', 2, @a);
call AddCustomer('Dom', '', 'Dominic', 'Beatty', 1, @a);

/* add customer discounts */
call AddFixedDiscount('Chris', 0.01, @a);
call AddFlexibleDiscount('DaveD', 1000, 2000, 0.01, @a);
call AddFlexibleDiscount('DaveD', 2000, NULL, 0.02, @a);
call AddFixedDiscount('SarahB', 0.02, @a);

/* assign blanks to advisors */
call AssignBlanks(44400000001, 44400000020, 250, DATE('2019-04-01'), @a);
call AssignBlanks(42000000001, 42000000030, 250, DATE('2019-05-08'), @a);
call AssignBlanks(20100000001, 20100000010, 250, DATE('2019-06-03'), @a);
call AssignBlanks(44400000021, 44400000040, 211, DATE('2019-04-05'), @a);
call AssignBlanks(42000000031, 42000000050, 211, DATE('2019-05-10'), @a);
call AssignBlanks(20100000011, 20100000025, 211, DATE('2019-06-15'), @a);
call AssignBlanks(10100000001, 10100000050, 211, DATE('2019-07-11'), @a);

/* add commission rates to database */
INSERT INTO CommissionRates (Rate, CommissionDate, Active) VALUES (0.05, 2020-01-01, TRUE);
INSERT INTO CommissionRates (Rate, CommissionDate, Active) VALUES (0.09, 2020-01-01, TRUE);

/* add exchange rates to database */
INSERT INTO ExchangeRates (ExchangeDate, Code, Rate) VALUES (2020-01-01, 'AAA', 0.54);

/* add sales into the database */
