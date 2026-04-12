-- Для production: задайте переменную окружения с РЕАЛЬНЫМ ID администратора
-- Для dev: можно оставить значение по умолчанию
-- ============================================

-- Вставка пользователя-администратора
insert into public.vk_users (user_vk_id, is_closed, is_locked, sex)
select CAST('${BOOTSTRAP_ADMIN_VK_ID}' AS BIGINT), false, false, 2
where not exists (
    select 1 from public.vk_users
    where user_vk_id = CAST('${BOOTSTRAP_ADMIN_VK_ID}' AS BIGINT)
)
-- Не выполняем, если переменная не задана или пустая
  and '${BOOTSTRAP_ADMIN_VK_ID}' == '';

-- Выдача ролей (только для реального админа)
insert into public.id_role (user_id, role_id)
select CAST('${BOOTSTRAP_ADMIN_VK_ID}' AS BIGINT), role_id
from (values (1), (2)) as roles(role_id)
where not exists (
    select 1 from public.id_role
    where user_id = CAST('${BOOTSTRAP_ADMIN_VK_ID}' AS BIGINT)
      and role_id = roles.role_id
)
  and CAST('${BOOTSTRAP_ADMIN_VK_ID}' AS BIGINT) IS NOT NULL;