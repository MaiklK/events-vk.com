INSERT INTO public.user_counters (albums, audios, clips_followers, followers, friends, gifts, groups, pages, photos, videos, user_id) VALUES (1, 61, 2300, 432, 1869, 22, 96, 31, 35, 0, '420214979');
INSERT INTO public.user_counters (albums, audios, clips_followers, followers, friends, gifts, groups, pages, photos, videos, user_id) VALUES (0, 0, 164, 39, 126, 4, 46, 31, 1, 0, '483775940');

INSERT INTO public.user_personal (alcohol, life_main, people_main, political, smoking, inspire_by, user_id) VALUES (1, 0, 0, 0, 1, 1, '420214979');
INSERT INTO public.user_personal (alcohol, life_main, people_main, political, smoking, inspire_by, user_id) VALUES (1, 5, 2, 0, 1, 2, '483775940');

INSERT INTO public.user_role (role_id, user_id) VALUES (1, '420214979');
INSERT INTO public.user_role (role_id, user_id) VALUES (1, '483775940');

INSERT INTO public.user_langs (user_personal_id, lang) VALUES ('420214979', 'Русский');
INSERT INTO public.user_langs (user_personal_id, lang) VALUES ('420214979', 'English');
INSERT INTO public.user_langs (user_personal_id, lang) VALUES ('420214979', 'עברית');
INSERT INTO public.user_langs (user_personal_id, lang) VALUES ('483775940', 'English');
INSERT INTO public.user_langs (user_personal_id, lang) VALUES ('483775940', 'Русский');