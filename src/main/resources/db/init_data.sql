INSERT INTO movie (movie_id, title, total_seats, free_seats, date) VALUES (1, 'Interstellar', 50, 48, '2010-10-10 10:10:10');
INSERT INTO movie (movie_id, title, total_seats, free_seats, date) VALUES (2, 'Guardians of the Galaxy', 20, 17, '2010-10-10 10:10:10');
INSERT INTO movie (movie_id, title, total_seats, free_seats, date) VALUES (3, 'Edge of Tomorrow', 10, 10, '2010-10-10 10:10:10');

INSERT INTO ticket (ticket_id, movie_id, seat_number) VALUES (1, 1, 1);
INSERT INTO ticket (ticket_id, movie_id, seat_number) VALUES (2, 1, 2);

INSERT INTO ticket (ticket_id, movie_id, seat_number) VALUES (3, 2, 2);
INSERT INTO ticket (ticket_id, movie_id, seat_number) VALUES (4, 2, 3);
INSERT INTO ticket (ticket_id, movie_id, seat_number) VALUES (5, 2, 4);