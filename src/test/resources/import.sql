insert into composite_task(id, name, score, time) values(1, 'abc', 123, 321);
insert into composite_task(id, name, score, time) values(2, 'bca', 11, 22);
insert into proxy_composite_task(id, start, entity, name) values (1, now(),1,'hello world');
insert into proxy_composite_task(id, start, entity, name) values (2, now(),2,'hell');