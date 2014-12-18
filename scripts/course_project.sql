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

create table holydays(
  day date
);

create or replace function working_days_count(first_day date, last_day date)
  return number
  is cnt number;
begin
  with days as (
    select
      first_day + level - 1 day
    from
      dual
    connect by
      level <= (last_day - first_day + 1)
  )
  select
    count(*)
  into cnt
  from
    days
  where
    to_char(day, 'D') between 2 and 6
    and not exists (
              select
                day
              from
                holydays h
              where h.day = days.day
            )
  ;
  return(cnt);
end;

create or replace function project_cost(projectId number)
  return number
  is price number;
begin
  with periods as (
    select
      s.value
      ,greatest(s.start_date, pa.start_date) start_date
      ,least(nvl(s.end_date, pa.end_date), pa.end_date) end_date
      ,e.last_name
    from
      project_assignments pa
      ,employees e
      ,salary s
    where
      pa.project_id = projectId
      and pa.employee_id = e.employee_id
      and s.employee_id = e.employee_id
      and (
        s.start_date between pa.start_date and pa.end_date
          or
        s.end_date between pa.start_date and pa.end_date
      )
  )
  ,months as (
    select distinct
      value
      ,decode(level, 1, start_date, trunc(add_months(start_date, level - 1), 'mm')) start_date
      ,case
          when add_months(start_date, level) >= end_date then end_date
          else last_day(add_months(start_date, level - 1))
      end end_date
    from periods
    connect by add_months(start_date, level - 1) < end_date
  )
  ,month_values as (
    select
      round(value / working_days_count(trunc(start_date, 'mm'), last_day(end_date)))
        *
      working_days_count(start_date, end_date) value
    from
      months
  )
  select
    sum(value)
  into price
  from
    month_values
  ;
  return(price);
end;

create or replace procedure terminate(employeeId number) as
begin
  update employees set termination_date = sysdate where employee_id = employeeId;
  update salary set end_date = sysdate where employee_id = employeeId;
end;
