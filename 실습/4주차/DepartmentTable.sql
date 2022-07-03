create table department(Dname varchar(15) not null,
Dnumber int not null, Mgr_ssn char(9) not null,
Mgr_start_date date,
primary key(Dnumber),
unique(Dname));

insert into department values('Reserch',5,333445555,'1988-05-22'),
('Administration',4,987654321,'1995-01-01'),
('Headquaters',1,888665555,'1981-06-19');

select * from department;
desc department;