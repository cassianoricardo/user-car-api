INSERT INTO user(email, login, password)VALUES('cassiano_ricardo@hotmail.com','cassiano','$2a$10$CQMsc74IBr0cQkLD.c96kOHmTXUxNCkqYyQCxlB7ys6ue81/gvMXe');

INSERT INTO role(name)VALUES('ROLE_USER');

INSERT INTO user_role(user_id, role_id)VALUES(1,1);