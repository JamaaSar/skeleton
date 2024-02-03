
insert into users ( username,password , fullname ,role) values
                   ('user','$2y$10$HJ1CiI9F072KHET/Idzc8uu4yOE7Nnrwm2SKma7C1PoqoLyxovoyW',
                     'user','ADMIN');

insert into bidlist  ( account, type, bid_quantity)
                                values ('account','type', 1);

insert into curvepoint  ( curve_id, term, value)
                                values (1, 1,1);

insert into rating  ( moodys_rating, sandprating, fitch_rating,order_number)
                                values ('moodys','sand', 'fitch', 1);

insert into rulename  ( name,description , json ,template, sql_part , sql_str)
                values ('name','description', 'json', 'template','part', 'str');

insert into trade  ( account, type, buy_quantity)
                                values ('account','type', 1);
