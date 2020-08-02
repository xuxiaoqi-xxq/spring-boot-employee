create table employee(
    employee_id int auto_increment primary key,
    name char(50),
    gender char(6),
    age int,
    salary int,
    company_id int,
    foreign key(company_id) references company(company_id)
)