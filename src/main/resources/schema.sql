create table
    if not exists emailsubject (
        id bigint auto_increment,
        date_query date,
        search_query varchar(500) not null,
        finded_email varchar(1500) not null,
        finded_link varchar(1500) not null
    );

alter table emailsubject add primary key(id);