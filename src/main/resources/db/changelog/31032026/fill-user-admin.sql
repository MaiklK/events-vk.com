-- Создание пользователя vk_users с указанным id
insert into public.vk_users (user_vk_id, is_closed, is_locked, sex)
values (420214979, false, false, 2);

-- Связка пользователя с ролями в таблице id_role
insert into public.id_role (user_id, role_id)
values (420214979, 1),
       (420214979, 2);