insert into role (id_role,role) values (1,'USER');
insert into role (id_role,role) values (2,'ADMIN');
insert into role (id_role,role) values (3,'BOSS');

insert into user_data (id_user_data,first_name,phone_number,second_name) values (1,'boss','223344','boss');
insert into user (id_user,confirmation_token,email,enabled,password,user_data_id_user_data) values (1,'derwef','boss@boss.pl',true,'1234',1);
insert into user_roles (id_user,id_role) values (1,1);
insert into user_roles (id_user,id_role) values (1,2);
insert into user_roles (id_user,id_role) values (1,3);

insert into user_data (id_user_data,first_name,phone_number,second_name) values (2,'admin','223344','admin');
insert into user (id_user,confirmation_token,email,enabled,password,user_data_id_user_data) values (2,'derwef','admin@admin.pl',true,'1234',2);
insert into user_roles (id_user,id_role) values (2,1);
insert into user_roles (id_user,id_role) values (2,2);

insert into user_data (id_user_data,first_name,phone_number,second_name) values (3,'user','223344','user');
insert into user (id_user,confirmation_token,email,enabled,password,user_data_id_user_data) values (3,'derwef','user@user.pl',true,'1234',3);
insert into user_roles (id_user,id_role) values (3,1);

insert into price_list (category,name,price,time) values ('man', 'Strzyżenie', 50, 60);
insert into price_list (category,name,price,time) values ('man', 'Strzyżenie z myciem głowy', 80, 80);

insert into price_list (category,name,price,time) values ('woman', 'Strzyżenie', 50, 60);
insert into price_list (category,name,price,time) values ('woman', 'Modelowanie', 70, 40);
insert into price_list (category,name,price,time) values ('woman', 'Polerowanie', 80, 20);
insert into price_list (category,name,price,time) values ('woman', 'Strzyżenie kręconych włosów', 80, 20);

insert into price_list (category,name,price,time) values ('colorization', 'Farbowanie całość lub odrosty', 120, 100);
insert into price_list (category,name,price,time) values ('colorization', 'Farbowanie 2 i więcej kolorów', 150, 160);
insert into price_list (category,name,price,time) values ('colorization', 'Pasemka', 160, 120);
insert into price_list (category,name,price,time) values ('colorization', 'Ombre/Sombre', 280, 180);
insert into price_list (category,name,price,time) values ('colorization', 'Balejaż', 150, 160);

insert into price_list (category,name,price,time) values ('otherServices', 'Bio-Trwała ondulacja', 90, 60);
insert into price_list (category,name,price,time) values ('otherServices', 'Rekonstrukcja włosa', 90, 80);
