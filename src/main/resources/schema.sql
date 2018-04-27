
create table invoice
(
   id UUID not null,
   due_date date not null,
   customer varchar(255) not null,
   total_in_cents bigint not null,
   fine decimal,
   status varchar(255) not null,
   primary key(id)
);


create table rule_fine
(
    id varchar(255) not null,
    days_of_delay_initial integer not null,
    days_of_delay_final integer not null,
    rate decimal not null
)