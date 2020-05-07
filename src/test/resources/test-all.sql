insert into repo (id_repo, de_root)
values (101, 'test1');
insert into repo (id_repo, de_root)
values (102, 'test2');

insert into scan (id_scan, id_repo)
values (110, 101);
insert into scan (id_scan, id_repo)
values (111, 101);
insert into scan (id_scan, id_repo)
values (112, 101);
insert into scan (id_scan, id_repo)
values (113, 102);
insert into scan (id_scan, id_repo)
values (114, 102);
insert into scan (id_scan, id_repo)
values (115, 102);

insert into visit (id_visit, id_scan, st_visit, de_path)
values (301, 110, 'VISITED', 'path');
