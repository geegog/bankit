insert into customer (id, created, updated, first_name, last_name, birth_of_birth, address)
    values (1, '2017-03-22 14:23:05', '2018-03-22 14:23:05', 'Onuche', 'Idoko', '1990-08-31', '123T Tartu Eesti');
insert into customer (id, created, updated, first_name, last_name, birth_of_birth, address)
    values (2, '2017-07-24 17:23:05', '2018-03-22 14:23:05', 'Bill', 'Doors', '2001-03-24', '13T Tartu Eesti');

insert into account (id, created, updated, ac_number, account_type, customer_id, balance, currency)
    values (1, '2017-03-22 14:23:25', null, '051234562313', 'SAVINGS', 1, 500000.00, 'EUR');
insert into account (id, created, updated, ac_number, account_type, customer_id, balance, currency)
    values (2, '2017-03-22 14:23:25', null, '053248946241', 'CURRENT', 2, 200000.00, 'EUR');