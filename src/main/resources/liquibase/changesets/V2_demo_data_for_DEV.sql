insert into users (name, username, password)
values  ('Anton Petrov', 'petrov@yandex.ru', 'N/A'),
        ('Petr Antonov', 'antonov@mail.ru', 'N/A'),
        ('Maxim Oreshnikov', 'oreshnik@gmail.com', 'N/A');


insert into animals (type, birth_date, sex, name)
values  ('DOG', '2020-10-07', 'FEMALE', 'Charlie'),
        ('CAT', '2019-07-05', 'FEMALE', 'Leslie'),
        ('CAT', '2022-01-30', 'MALE', 'Murzik'),
        ('CAT', '2017-06-15', 'MALE', 'Barsik'),
        ('CAT' ,'2021-11-07', 'FEMALE', 'Kitty'),
        ('PIG', '2024-12-11', 'MALE', 'Piggy'),
        ('RAT', '2023-11-11', 'FEMALE', 'Sterva');

 insert into users_animals(user_id, animal_id)
 values (1, 1),
        (2, 2),
        (2, 3),
        (3, 4),
        (3, 5),
        (3, 6),
        (3, 7);