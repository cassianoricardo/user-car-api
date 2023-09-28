INSERT INTO public."user"("email", "login","fistname", "lastname", "password")VALUES('user@gmail.com','user','username','fistnamea','$2a$10$CQMsc74IBr0cQkLD.c96kOHmTXUxNCkqYyQCxlB7ys6ue81/gvMXe');
INSERT INTO public."user"("email", "login", "fistname", "lastname", "password")VALUES('user1@gmail.com','user1','username','fistnameb','$2a$10$CQMsc74IBr0cQkLD.c96kOHmTXUxNCkqYyQCxlB7ys6ue81/gvMXe');

INSERT INTO public."role"("name")VALUES('ROLE_USER');

INSERT INTO public."user_role"("user_id", "role_id")VALUES(1,1);
INSERT INTO public."user_role"("user_id", "role_id")VALUES(2,1);