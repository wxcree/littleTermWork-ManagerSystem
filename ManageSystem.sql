create schema if not exists ManageSystem collate utf8mb4_0900_ai_ci;

create table if not exists ASSET
(
    id int not null #资产id
        primary key,
    name varchar(11) null,
    total int null
);

create table if not exists USER_ASSET
(
    userid int not null, #持有者的id
    assetid int not null, #持有的库存id
    total int null,  #所持有的数量
    primary key (userid, assetid)
);

create table if not exists User
(
    id int not null
        primary key,
    name varchar(11) not null,
    type int not null, #0为普通用户 1为管理员 2为库管
    password varchar(15) null
);

INSERT INTO ManageSystem.User (id, name, type, password) VALUES (1, 'oyjp', 1, '123456');
INSERT INTO ManageSystem.User (id, name, type, password) VALUES (2, 'pjl', 2, '123456');
INSERT INTO ManageSystem.User (id, name, type, password) VALUES (777, 'wxc', 0, '123456');


INSERT INTO ManageSystem.ASSET (id, name, total) VALUES (1, 'MacBookPro', 30);

INSERT INTO ManageSystem.USER_ASSET (userid, assetid, total) VALUES (777, 1, 10);