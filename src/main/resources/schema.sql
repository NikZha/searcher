create table
    if not exists emailsuppliers (
        id bigint auto_increment,
        date_query date,
        search_query varchar(20) not null,
        finded_email varchar(50) not null,
        finded_link varchar(250) not null
    );

alter table emailsuppliers add primary key(id);