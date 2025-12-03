insert into users_roles(user_id, role)
values  (1, 'ROLE_ADMIN'),
        (2, 'ROLE_USER'),
        (3, 'ROLE_USER');

--password 123 for all users
update users set password = '$2a$12$NomjLz7YpEEjBQVTL/AbK.1lyizWZCT98nIYHCt1/aOJ/dMviTN86';