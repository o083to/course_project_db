create table employees(
  employee_id         number        primary key,
  last_name           varchar2(50)  not null,
  first_name          varchar2(50)  not null,
  middle_name         varchar2(50)  not null,
  hire_date           date          not null,
  position            varchar2(250) not null,
  department_id       number
);

create sequence employee_seq
start with 1000
increment by 1
nocache
nocycle;

create table departments(
  department_id         number          primary key,
  name                  varchar2(250)   not null,
  manager_id            number,
  constraint dep_manager_id_fk 
    foreign key (manager_id)
    references employees(employee_id)
);

create sequence departments_seq
start with 1000
increment by 1
nocache
nocycle;

alter table employees
add constraint emp_department_id_fk
foreign key (department_id)
references departments(department_id);

create table customers(
  customer_id         number        primary key,
  name                varchar2(200) not null,
  location            varchar2(200) not null
);

create sequence customers_seq
start with 1000
increment by 1
nocache
nocycle;

create table projects(
  project_id          number          primary key,
  name                varchar2(200)   not null,
  start_date          date            not null,
  end_date            date            not null,
  customer_id         number          not null,
  cost                number,
  constraint proj_customer_id_fk
  foreign key (customer_id)
  references customers(customer_id)
);

create sequence projects_seq
start with 1000
increment by 1
nocache
nocycle;

create table project_assignments(
  assignment_id         number        primary key,
  employee_id           number        not null,
  project_id            number        not null,
  start_date            date          not null,
  end_date              date          not null,
  role                  varchar2(250) not null,
  constraint pa_employee_id_fk
  foreign key (employee_id)
  references employees(employee_id),
  constraint pa_project_id_fk
  foreign key (project_id)
  references projects(project_id)
);

create sequence assignment_seq
start with 1000
increment by 1
nocache
nocycle;

create table salary(
  salary_id           number          primary key,
  employee_id         number          not null,
  value               number          not null,
  start_date          date            not null,
  end_date            date,
  constraint sal_employee_id_fk
  foreign key (employee_id)
  references employees(employee_id)
);

create sequence salary_seq
start with 1000
increment by 1
nocache
nocycle;

create table trips(
  trip_id         number          primary key,
  employee_id     number          not null,
  start_date      date            not null,
  end_date        date            not null,
  location        varchar2(100)   not null,
  constraint trip_employee_id_fk
  foreign key (employee_id)
  references employees(employee_id)
);

create sequence trips_seq
start with 1000
increment by 1
nocache
nocycle;

create table vacations(
  vacation_id         number          primary key,
  employee_id         number          not null,
  start_date          date          not null,
  end_date            date          not null,
  reason              varchar2(250)   not null,
  constraint vac_employee_id_fk
  foreign key (employee_id)
  references employees(employee_id)
);

create sequence vacations_seq
start with 1000
increment by 1
nocache
nocycle;

create table users(
  login varchar2(100),
  password varchar2(500)
);