alter table animals
    drop constraint animals_name_key;

alter table animals
    add constraint animals_unique unique (name, type, sex, birth_date);