INSERT INTO public."user"("email", "login", "password")VALUES('cassiano_ricardo@hotmail.com','cassiano','$2a$10$CQMsc74IBr0cQkLD.c96kOHmTXUxNCkqYyQCxlB7ys6ue81/gvMXe');

INSERT INTO public."role"("name")VALUES('ROLE_USER');

INSERT INTO public."user_role"("user_id", "role_id")VALUES(1,1);