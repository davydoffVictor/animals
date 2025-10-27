create table if not exists users (
    id bigserial primary key,
	name varchar(255) not null,
	username varchar(255) not null unique,
	password varchar(255) not null
);

create table if not exists animals (
	id bigserial primary key,
	type varchar(255) not null,
	birth_date timestamp not null,
	sex varchar(255) not null,
	name varchar(255) not null unique
);

create table if not exists users_animals (
	user_id bigint not null,
	animal_id bigint not null,
	primary key(user_id, animal_id),
	constraint fk_users_animals_users foreign key (user_id) references users (id)
	    on delete cascade on update no action,
	constraint fk_users_animals_animals foreign key (animal_id) references animals (id)
	    on delete cascade on update no action
);

