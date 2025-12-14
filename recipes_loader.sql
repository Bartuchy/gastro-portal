BEGIN;

INSERT INTO recipe (date, name, category, description, picture, ingredients, directions, user_id, id)
VALUES (now() - interval '10 days', 'Zupa pomidorowa (u452)', 'Zupa', 'Klasyczna zupa z pomidorów.', NULL,
        'pomidory, cebula, bulion, sól, pieprz', '1. Podsmaż cebulę. 2. Dodaj pomidory i bulion. 3. Gotuj 20 min.', 452, nextval('recipe_seq')),
       (now() - interval '9 days', 'Sałatka grecka (u452)', 'Sałatka', 'Świeża sałatka z serem feta.', NULL,
        'sałata, pomidor, ogórek, feta, oliwki, oliwa', '1. Pokrój warzywa. 2. Dodaj fetę i oliwę.', 452, nextval('recipe_seq')),
       (now() - interval '8 days', 'Placki ziemniaczane (u452)', 'Danie główne', 'Złociste placki ziemniaczane.', NULL,
        'ziemniaki, jajko, mąka, sól, olej', '1. Zetrzyj ziemniaki. 2. Smaż na złoto.', 452, nextval('recipe_seq')),
       (now() - interval '7 days', 'Spaghetti bolognese (u452)', 'Danie główne', 'Makaron z sosem mięsnym.', NULL,
        'makaron, mięso mielone, pomidory, cebula, czosnek',
        '1. Podsmaż mięso. 2. Dodaj sos pomidorowy. 3. Podawaj z makaronem.', 452, nextval('recipe_seq')),
       (now() - interval '6 days', 'Naleśniki z dżemem (u452)', 'Deser', 'Proste naleśniki z ulubionym dżemem.', NULL,
        'mąka, mleko, jajka, dżem, olej', '1. Przygotuj ciasto. 2. Smaż cienkie naleśniki. 3. Podawaj z dżemem.', 452, nextval('recipe_seq'));

INSERT INTO recipe (date, name, category, description, picture, ingredients, directions, user_id, id)
VALUES (now() - interval '15 days', 'Krem z brokułów (u752)', 'Zupa', 'Delikatny krem z brokułów.', NULL,
        'brokuły, ziemniak, bulion, śmietana, sól', '1. Ugotuj składniki. 2. Zblenduj na krem.', 752, nextval('recipe_seq')),
       (now() - interval '14 days', 'Sałatka z kurczakiem (u752)', 'Sałatka', 'Sałatka na ciepło z kurczakiem.', NULL,
        'kurczak, sałata, pomidor, sos, przyprawy', '1. Usmaż kurczaka. 2. Połącz składniki.', 752, nextval('recipe_seq')),
       (now() - interval '13 days', 'Gulasz wołowy (u752)', 'Danie główne', 'Wolno duszony gulasz wołowy.', NULL,
        'wołowina, cebula, marchew, papryka, bulion', '1. Podsmaż mięso. 2. Duś 1.5-2h z warzywami.', 752, nextval('recipe_seq')),
       (now() - interval '12 days', 'Ryż z warzywami (u752)', 'Danie główne', 'Szybkie danie z ryżem i warzywami.',
        NULL, 'ryż, marchew, groszek, sos sojowy, cebula', '1. Ugotuj ryż. 2. Podsmaż warzywa. 3. Połącz.', 752, nextval('recipe_seq')),
       (now() - interval '11 days', 'Ciasto marchewkowe (u752)', 'Deser', 'Wilgotne ciasto z marchewką.', NULL,
        'marchew, mąka, jajka, cukier, olej, przyprawy', '1. Wymieszaj składniki. 2. Piecz 40-50 min.', 752, nextval('recipe_seq'));

INSERT INTO recipe (date, name, category, description, picture, ingredients, directions, user_id, id)
VALUES (now() - interval '5 days', 'Omlet warzywny (u1)', 'Danie główne', 'Szybki omlet z warzywami.', NULL,
        'jajka, papryka, pomidor, cebula, sól, pieprz', '1. Wymieszaj jajka z warzywami. 2. Smaż na patelni.', 1, nextval('recipe_seq')),
       (now() - interval '4 days', 'Kuskus z warzywami (u1)', 'Danie główne', 'Lekki posiłek kuskus z warzywami.', NULL,
        'kuskus, cukinia, papryka, sos, przyprawy', '1. Zalej kuskus wrzątkiem. 2. Dodaj warzywa.', 1, nextval('recipe_seq')),
       (now() - interval '3 days', 'Zupa ogórkowa (u1)', 'Zupa', 'Tradycyjna zupa ogórkowa.', NULL,
        'ogórki kiszone, ziemniaki, marchew, bulion, śmietana', '1. Ugotuj warzywa. 2. Dodaj ogórki i śmietanę.', 1, nextval('recipe_seq')),
       (now() - interval '2 days', 'Pesto z bazylii (u1)', 'Dodatek', 'Domowe pesto bazyliowe.', NULL,
        'bazylia, orzeszki piniowe, ser, oliwa, czosnek', '1. Zblenduj wszystkie składniki.', 1, nextval('recipe_seq')),
       (now() - interval '1 days', 'Pieczone warzywa (u1)', 'Danie główne', 'Warzywa pieczone w ziołach.', NULL,
        'ziemniaki, marchew, cukinia, oliwa, zioła', '1. Pokrój warzywa. 2. Piecz 30-40 min w 200°C.', 1, nextval('recipe_seq'));

COMMIT;