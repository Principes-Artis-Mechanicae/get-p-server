alter table project modify column status varchar(255);

update project set status = 'APPLICATION_OPENED' where status = 'APPLYING';

alter table project modify column status enum(
    'PREPARING',
    'APPLICATION_OPENED',
    'APPLICATION_CLOSED',
    'MEETING',
    'CONFIRMED',
    'PROGRESSING',
    'COMPLETED',
    'CANCELLED'
);

