INSERT INTO public.user_counters (albums, audios, clips_followers, followers, friends, gifts, groups, pages, photos, videos, id, user_id) VALUES (1, 61, 2300, 432, 1869, 22, 96, 31, 35, 0, 1, 1);
INSERT INTO public.user_counters (albums, audios, clips_followers, followers, friends, gifts, groups, pages, photos, videos, id, user_id) VALUES (0, 0, 164, 39, 126, 4, 46, 31, 1, 0, 2, 2);

INSERT INTO public.user_personal (alcohol, life_main, people_main, political, smoking, id, user_id, inspire_by) VALUES (1, 0, 0, 0, 1, 1, 1, '');
INSERT INTO public.user_personal (alcohol, life_main, people_main, political, smoking, id, user_id, inspire_by) VALUES (1, 5, 2, 0, 1, 2, 2, '');

INSERT INTO public.user_role (role_id, user_id) VALUES (1, 1);
INSERT INTO public.user_role (role_id, user_id) VALUES (1, 2);

INSERT INTO public.user_langs (user_personal_id, lang) VALUES (1, 'Русский');
INSERT INTO public.user_langs (user_personal_id, lang) VALUES (1, 'English');
INSERT INTO public.user_langs (user_personal_id, lang) VALUES (1, 'עברית');
INSERT INTO public.user_langs (user_personal_id, lang) VALUES (2, 'English');
INSERT INTO public.user_langs (user_personal_id, lang) VALUES (2, 'Русский');