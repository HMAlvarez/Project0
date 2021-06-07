create schema project0;
set schema 'project0';

create table users (

	user_id serial primary key,
	username varchar(30) not null,
	password varchar(30) not null,
	first_name varchar(30),
	last_name varchar(30),
	customer_type boolean not null

);

create table bank_acc (

	bank_id serial primary key,
	customer_id int unique references users(user_id),
	mail_address varchar(80) not null,
	pending_transaction boolean not null,
	inital_deposit numeric(15,2) not null check(inital_deposit  >= 0)

);

create table checking_acc (

	checking_acc_number varchar(30) not null,
	bank_id int unique references bank_acc(bank_id),
	checking_balance numeric(15,2) not null check (checking_balance >= 0),
	constraint checking_acc_pk primary key (bank_id, checking_acc_number)

);

create table saving_acc (

	saving_acc_number varchar(30) not null,
	bank_id int unique references bank_acc(bank_id),
	saving_balance numeric(15,2) not null check (saving_balance >= 0),
	constraint saving_acc_pk primary key (bank_id, saving_acc_number)

);

create table transactions (

	transaction_id serial primary key,
	recipient_id int references users(user_id),
	sender_id int references users(user_id),
	sender_acc_number varchar(30) not null,
	transaction_amount numeric(15,2) not null check (transaction_amount >= 0)

);


select * from users;
select * from bank_acc;
select * from checking_acc;
select * from saving_acc;
select * from transactions;
