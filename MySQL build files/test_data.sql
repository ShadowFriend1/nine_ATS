/* add users */
call AddAdvisor(250, 'PenelopePitstop', 'PinkMobile', 'Penelope', 'Pitstop', @a);
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
call AddFlexibleDiscount('DaveD', NULL, 1000, 0.00, @a);
call AddFlexibleDiscount('DaveD', 1000, 2000, 0.01, @a);
call AddFlexibleDiscount('DaveD', 2000, NULL, 0.02, @a);
call AddFixedDiscount('SarahB', 0.02, @a);

/* assign blanks to advisors */
call AssignBlanks(44400000001, 44400000020, 250, DATE('2019-04-01'), TRUE, @a);
call AssignBlanks(42000000001, 42000000030, 250, DATE('2019-05-08'), TRUE, @a);
call AssignBlanks(20100000001, 20100000010, 250, DATE('2019-06-03'), TRUE, @a);
call AssignBlanks(44400000021, 44400000040, 211, DATE('2019-04-05'), TRUE, @a);
call AssignBlanks(42000000031, 42000000050, 211, DATE('2019-05-10'), TRUE, @a);
call AssignBlanks(20100000011, 20100000025, 211, DATE('2019-06-15'), TRUE, @a);
call AssignBlanks(10100000001, 10100000050, 211, DATE('2019-07-11'), TRUE, @a);

/* add commission rates to database */
INSERT INTO CommissionRates (Rate, CommissionDate, Active)
VALUES (0.05, '2020-01-01', TRUE);
INSERT INTO CommissionRates (Rate, CommissionDate, Active)
VALUES (0.09, '2020-01-01', TRUE);

/* add exchange rates to database */
INSERT INTO ExchangeRates (ExchangeDate, Code, Rate)
VALUES ('2020-01-01', 'GBP', 0.54);
INSERT INTO ExchangeRates (ExchangeDate, Code, Rate)
VALUES ('2020-01-01', 'USD', 1);

/* add sales into the database */
/* interline: 01/01/2020 */
call MakeSaleCash(44400000001, 250, 23, 35, 'SarahB',
                  0.09, 220, 'GBP', '2020-01-01', @a);
call MakeSaleCard(44400000002, 250, 43, 55, 230,
                  NULL, 0.09, 4901000223453456, 'VISA', 'GBP',
                  '2020-01-01', @a);

/* domestic: 01/01/2020 */
call MakeSaleCashDomestic(20100000001, 250, 15.60, NULL,
                          0.05, 86, '2020-01-01', @a);

/* interline: 02/01/2020 */
call MakeSaleDelayed(44400000003, 250, 63, 75, 'DaveD',
                     0.09, 220, 'GBP', '2020-01-02', @a);
call MakeSaleDelayed(44400000004, 250, 23, 35, 'Chris',
                     0.09, 230, 'GBP', '2020-01-02', @a);
call MakeSaleDelayed(44400000021, 211, 25, 35, 'SarahB',
                     0.09, 250, 'GBP', '2020-01-02', @a);
call MakeSaleCard(44400000022, 211, 28, 37, 300,
                  NULL, 0.09, 7449155545893456, 'VISA', 'GBP',
                  '2020-01-02', @a);

/* domestic: 02/01/2020 */
call MakeSaleCardDomestic(20100000002, 250, 13.80, 75,
                          NULL, 0.05, 6454986387338876, 'VISA',
                          '2020-01-02', @a);
call MakeSaleCashDomestic(20100000011, 211, 13.80, NULL,
                          0.05, 75, '2020-01-02', @a);