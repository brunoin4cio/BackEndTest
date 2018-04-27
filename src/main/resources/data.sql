
insert into invoice
values('84e8adbf-1a14-403b-ad73-d78ae19b59bf',
PARSEDATETIME( '2018-04-28' , 'yyyy-MM-dd'), 'Bruno Inacio', '1000000',null, 'PENDING');

insert into rule_fine
values( 1, 0, 10, 0.5 );

insert into rule_fine
values( 2, 10, 365000 , 1 );
