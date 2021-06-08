create schema project0;
set schema 'public';

create table customers (
	id serial primary key,
	username varchar(30) unique not null,
	password varchar(30) not null,
	employee bool
);

create table accounts (
	id serial primary key,
	balance numeric(20, 2),
	customer int references customers, -- FK to customers table
	pending bool
);

create table transactions (
	id serial primary key,
	source int references accounts(id), -- FK to accounts table
	type varchar(20),
	amount numeric(20, 2),
	receiver int references accounts(id) -- FK to accounts table, null if type is not "transfer"
);

select * from customers;
select * from accounts;
select * from transactions;
