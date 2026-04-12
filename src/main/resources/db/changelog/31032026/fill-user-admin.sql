-- Задайте переменную окружения с РЕАЛЬНЫМ VK_ID администратора
-- ============================================

-- Вставка пользователя-администратора
with admin as (select nullif('${BOOTSTRAP_ADMIN_VK_ID}', '')::bigint as admin_id)
insert
into public.vk_users (user_vk_id, is_closed, is_locked, sex)
select admin_id, false, false, 2
from admin
where admin_id is not null
  and not exists (select 1
                  from public.vk_users
                  where user_vk_id = admin_id);

-- Выдача ролей (только для реального админа)
insert into public.id_role (user_id, role_id)
select CAST('${BOOTSTRAP_ADMIN_VK_ID}' AS BIGINT), role_id
from (values (1), (2)) as roles(role_id)
where not exists (select 1
                  from public.id_role
                  where user_id = CAST('${BOOTSTRAP_ADMIN_VK_ID}' AS BIGINT)
                    and role_id = roles.role_id)
  and CAST('${BOOTSTRAP_ADMIN_VK_ID}' AS BIGINT) IS NOT NULL;