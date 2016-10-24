-- task table
insert into task(id, src, algo, status, description) values (1, 'file1', 'md5', 'NEW', null)
insert into task(id, src, algo, status, description) values (2, 'file2', 'sha-1', 'NEW', null)
insert into task(id, src, algo, status, description) values (3, 'file3', 'md5', 'SUCCESS', 'Task has been done')
insert into task(id, src, algo, status, description) values (4, 'file4', 'sha-1', 'NEW', null)
insert into task(id, src, algo, status, description) values (5, 'file5', 'sha-256', 'NEW', null)
insert into task(id, src, algo, status, description) values (6, 'file6', 'md5', 'FAILURE', 'Error: java.lang.OutOfMemoryError: Java heap space')
insert into task(id, src, algo, status, description) values (7, 'file7', 'sha-256', 'SUCCESS', 'Task has been done')