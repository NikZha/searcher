create table
    if not exists emailsuppliers (
        id bigint auto_increment,
        date_query date,
        search_query varchar(100) not null,
        finded_email varchar(1500) not null,
        finded_link varchar(1500) not null
    );

alter table emailsuppliers add primary key(id);