DROP TABLE if EXISTS repo;
DROP TABLE if EXISTS scan;
DROP TABLE if EXISTS visit;

create table repo (
    id_repo INTEGER NOT NULL PRIMARY KEY autoincrement,
    de_root VARCHAR(1023)
);

create table scan(
    id_scan INTEGER NOT NULL PRIMARY KEY autoincrement,
    id_repo INTEGER,
    sq_sequence INTEGER,
    st_scan VARCHAR(15)
);

create table visit
(
    id_visit INTEGER NOT NULL PRIMARY KEY autoincrement,
    de_path varchar(1023),
    st_visit varchar(15),
    dt_update datetime,
    id_scan INTEGER
);

