insert into "user"
values (101, '$2a$10$r4uDItrEsHSILwlGMrLo/OZxjB0duHIOzkW9O5IElXEiYpi0UyIkC', 1, 'content_manager1');
insert into "user"
values (102, '$2a$10$GYzwcRiC9Bizu35nyBHIpu4PWsmXuZhE8l12CFLWhNOIpsMwBN3Z6', 2, 'user2');
insert into "user"
values (103, '$2a$10$6ZpeHyNi8peunxD2UP.ufu2eoAfgOiAfksgrIp11j2/ZO23WTzWzS', 3, 'user3');
insert into "user"
values (104, '$2a$10$fKQypoKx4y0UPJZSVG5x2OKL64DR9E2uz3L7Spt76Q7SBZrRBG3au', 4, 'user4');

insert into "user_roles"
values (101, 'ROLE_CONTENT_MANAGER');
insert into "user_roles"
values (102, 'ROLE_CLIENT');
insert into "user_roles"
values (103, 'ROLE_CLIENT');
insert into "user_roles"
values (104, 'ROLE_CLIENT');

insert into "video_content"
values ('b49373cc-e552-11ed-b5ea-0242ac120002',
        'An ancient Ring thought lost for centuries has been found, and through a strange twist of fate has been given to a small Hobbit named Frodo. When Gandalf discovers the Ring is in fact the One Ring of the Dark Lord Sauron, Frodo must make an epic quest to the Cracks of Doom in order to destroy it. However, he does not go alone. He is joined by Gandalf, Legolas the elf, Gimli the Dwarf, Aragorn, Boromir, and his three Hobbit friends Merry, Pippin, and Samwise. Through mountains, snow, darkness, forests, rivers and plains, facing evil and danger at every corner the Fellowship of the Ring must go. Their quest to destroy the One Ring is the only hope for the end of the Dark Lords reign.',
        178, 'The Lord of the Rings: The Fellowship of the Ring', 1000, 12345);
insert into "video_content"
values ('b4934dac-e552-11ed-b5ea-0242ac120002',
        'It is based on the real life story of legendary cryptanalyst Alan Turing. The film portrays the nail-biting race against time by Turing and his brilliant team of code-breakers at Britain''s top-secret Government Code and Cypher School at Bletchley Park, during the darkest days of World War II.',
        114, 'The Imitation Game', 100000, 1000000);
insert into "video_content"
values ('b49350cc-e552-11ed-b5ea-0242ac120002', 'description 3', 102, 'name 3', 3, 12);
insert into "video_content"
values ('b4935298-e552-11ed-b5ea-0242ac120002', 'description 4', 103, 'name 4', 4, 13);
insert into "video_content"
values ('b493541e-e552-11ed-b5ea-0242ac120002', 'description 5', 104, 'name 5', 5, 14);
insert into "video_content"
values ('b493559a-e552-11ed-b5ea-0242ac120002', 'description 6', 105, 'name 6', 6, 15);
insert into "video_content"
values ('b4935734-e552-11ed-b5ea-0242ac120002', 'description 7', 106, 'name 7', 7, 16);
insert into "video_content"
values ('b49358c4-e552-11ed-b5ea-0242ac120002', 'description 8', 107, 'name 8', 8, 17);
insert into "video_content"
values ('b4935f72-e552-11ed-b5ea-0242ac120002', 'description 9', 108, 'name 9', 9, 18);
insert into "video_content"
values ('b4936134-e552-11ed-b5ea-0242ac120002', 'description 10', 109, 'name 10', 10, 19);
insert into "video_content"
values ('b49362ba-e552-11ed-b5ea-0242ac120002', 'description 11', 110, 'name 11', 11, 20);
insert into "video_content"
values ('b493644a-e552-11ed-b5ea-0242ac120002', 'description 12', 111, 'name 12', 12, 21);
insert into "video_content"
values ('b49365d0-e552-11ed-b5ea-0242ac120002', 'description 13', 112, 'name 13', 13, 22);
insert into "video_content"
values ('b4936756-e552-11ed-b5ea-0242ac120002', 'description 14', 113, 'name 14', 14, 23);
insert into "video_content"
values ('b49368dc-e552-11ed-b5ea-0242ac120002', 'description 15', 114, 'name 15', 15, 24);
insert into "video_content"
values ('b4936a58-e552-11ed-b5ea-0242ac120002', 'description 16', 115, 'name 16', 16, 25);
insert into "video_content"
values ('b4936da0-e552-11ed-b5ea-0242ac120002', 'description 17', 116, 'name 17', 17, 26);
insert into "video_content"
values ('b4936f30-e552-11ed-b5ea-0242ac120002', 'description 18', 117, 'name 18', 18, 27);
insert into "video_content"
values ('b49370b6-e552-11ed-b5ea-0242ac120002', 'description 19', 118, 'name 19', 19, 28);
insert into "video_content"
values ('b4937246-e552-11ed-b5ea-0242ac120002', 'description 20', 119, 'name 20', 20, 29);

insert into "genre"
values (101, 'Action');
insert into "genre"
values (102, 'Animation');
insert into "genre"
values (103, 'Comedy');
insert into "genre"
values (104, 'Crime');
insert into "genre"
values (105, 'Drama');
insert into "genre"
values (106, 'Fantasy');
insert into "genre"
values (107, 'Historical');
insert into "genre"
values (108, 'Horror');
insert into "genre"
values (109, 'Romance');
insert into "genre"
values (110, 'Science fiction');
insert into "genre"
values (111, 'Biography');
insert into "genre"
values (112, 'Thriller');
insert into "genre"
values (113, 'War');

insert into "video_content_genres"
values ('b4937246-e552-11ed-b5ea-0242ac120002', 101);
insert into "video_content_genres"
values ('b4937246-e552-11ed-b5ea-0242ac120002', 102);
insert into "video_content_genres"
values ('b4937246-e552-11ed-b5ea-0242ac120002', 103);
insert into "video_content_genres"
values ('b4937246-e552-11ed-b5ea-0242ac120002', 104);
insert into "video_content_genres"
values ('b49370b6-e552-11ed-b5ea-0242ac120002', 105);
insert into "video_content_genres"
values ('b49370b6-e552-11ed-b5ea-0242ac120002', 106);
insert into "video_content_genres"
values ('b49370b6-e552-11ed-b5ea-0242ac120002', 107);
insert into "video_content_genres"
values ('b4934dac-e552-11ed-b5ea-0242ac120002', 105);
insert into "video_content_genres"
values ('b4934dac-e552-11ed-b5ea-0242ac120002', 111);
insert into "video_content_genres"
values ('b4934dac-e552-11ed-b5ea-0242ac120002', 112);
insert into "video_content_genres"
values ('b4934dac-e552-11ed-b5ea-0242ac120002', 113);

insert into "language"
values (11, 'English', 'en');
insert into "language"
values (12, 'Russian', 'ru');
insert into "language"
values (13, 'French', 'fr');

insert into "video_content_loc"
values ('adf91fd4-e7e2-11ed-a05b-0242ac120003', 11, 'b49373cc-e552-11ed-b5ea-0242ac120002');
insert into "video_content_loc"
values ('adf92330-e7e2-11ed-a05b-0242ac120003', 12, 'b49373cc-e552-11ed-b5ea-0242ac120002');
insert into "video_content_loc"
values ('adf9247a-e7e2-11ed-a05b-0242ac120003', 13, 'b49373cc-e552-11ed-b5ea-0242ac120002');
insert into "video_content_loc"
values ('84f8f4e2-e855-11ed-a05b-0242ac120003', 11, 'b4934dac-e552-11ed-b5ea-0242ac120002');
insert into "video_content_loc"
values ('84f900cc-e855-11ed-a05b-0242ac120003', 12, 'b4934dac-e552-11ed-b5ea-0242ac120002');
insert into "video_content_loc"
values ('84f901f8-e855-11ed-a05b-0242ac120003', 13, 'b4934dac-e552-11ed-b5ea-0242ac120002');

insert into "video_content_video_content_locs"
values ('b49373cc-e552-11ed-b5ea-0242ac120002', 'adf91fd4-e7e2-11ed-a05b-0242ac120003');
insert into "video_content_video_content_locs"
values ('b49373cc-e552-11ed-b5ea-0242ac120002', 'adf92330-e7e2-11ed-a05b-0242ac120003');
insert into "video_content_video_content_locs"
values ('b49373cc-e552-11ed-b5ea-0242ac120002', 'adf9247a-e7e2-11ed-a05b-0242ac120003');
insert into "video_content_video_content_locs"
values ('b4934dac-e552-11ed-b5ea-0242ac120002', '84f8f4e2-e855-11ed-a05b-0242ac120003');
insert into "video_content_video_content_locs"
values ('b4934dac-e552-11ed-b5ea-0242ac120002', '84f900cc-e855-11ed-a05b-0242ac120003');
insert into "video_content_video_content_locs"
values ('b4934dac-e552-11ed-b5ea-0242ac120002', '84f901f8-e855-11ed-a05b-0242ac120003');

insert into "subtitle"
values ('4c0225a4-e7e3-11ed-a05b-0242ac120003', 12);
insert into "subtitle"
values ('4c0229be-e7e3-11ed-a05b-0242ac120003', 13);
insert into "subtitle"
values ('4868d1b8-e856-11ed-a05b-0242ac120003', 12);
insert into "subtitle"
values ('4868d5d2-e856-11ed-a05b-0242ac120003', 13);

insert into "video_content_loc_subtitles"
values ('adf91fd4-e7e2-11ed-a05b-0242ac120003', '4c0225a4-e7e3-11ed-a05b-0242ac120003');
insert into "video_content_loc_subtitles"
values ('adf91fd4-e7e2-11ed-a05b-0242ac120003', '4c0229be-e7e3-11ed-a05b-0242ac120003');
insert into "video_content_loc_subtitles"
values ('84f8f4e2-e855-11ed-a05b-0242ac120003', '4868d1b8-e856-11ed-a05b-0242ac120003');
insert into "video_content_loc_subtitles"
values ('84f8f4e2-e855-11ed-a05b-0242ac120003', '4868d5d2-e856-11ed-a05b-0242ac120003');

insert into "duo_watch_request"
values (101, now(), 'OPEN', now(), 103, 12, '84f8f4e2-e855-11ed-a05b-0242ac120003')











