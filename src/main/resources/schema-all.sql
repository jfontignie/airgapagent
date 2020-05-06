-- DROP TABLE if EXISTS repo;
-- DROP TABLE if EXISTS scan;
-- DROP TABLE if EXISTS visit;

create table IF NOT EXISTS repo (
    id_repo INTEGER NOT NULL PRIMARY KEY autoincrement,
    de_root VARCHAR(1023)
) ;

create table IF NOT EXISTS scan(
    id_scan INTEGER NOT NULL PRIMARY KEY autoincrement,
    id_repo INTEGER
);

create table IF NOT EXISTS visit (
    id_visit INTEGER NOT NULL PRIMARY KEY autoincrement,
    de_path varchar(1023),
    st_visit varchar(15),
    dt_update datetime,
    id_scan INTEGER
);

