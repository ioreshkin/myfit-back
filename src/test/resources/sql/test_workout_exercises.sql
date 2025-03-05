-- Упражнения
INSERT INTO exercise (id, title, description, video_url, owner)
VALUES (1, 'Приседания', 'Приседания со штангой', 'https://example.com/squat', 1);

INSERT INTO exercise (id, title, description, video_url, owner)
VALUES (2, 'Жим ногами', 'Жим ногами в тренажёре', 'https://example.com/leg-press', 1);

INSERT INTO exercise (id, title, description, video_url, owner)
VALUES (3, 'Жим лежа', 'Жим штанги лежа', 'https://example.com/bench', 1);


INSERT INTO exercise (id, title, description, video_url, owner)
VALUES (4, 'Разведение гантелей', 'Разведение гантелей лёжа', 'https://example.com/flyes', 1);

-- Приседания в тренировке ног (2 подхода)
INSERT INTO workout_exercise (id, workout_id, exercise_id, repeats, weight, orders)
VALUES (1, 1, 1, 10, 80.0, 1);

INSERT INTO workout_exercise (id, workout_id, exercise_id, repeats, weight, orders)
VALUES (2, 1, 1, 8, 100.0, 2);

-- Жим ногами в тренировке ног (2 подхода)
INSERT INTO workout_exercise (id, workout_id, exercise_id, repeats, weight, orders)
VALUES (5, 1, 2, 15, 120.0, 3);

INSERT INTO workout_exercise (id, workout_id, exercise_id, repeats, weight, orders)
VALUES (6, 1, 2, 12, 140.0, 4);

-- Жим лёжа в тренировке груди (2 подхода)
INSERT INTO workout_exercise (id, workout_id, exercise_id, repeats, weight, orders)
VALUES (3, 2, 3, 12, 60.0, 1);

INSERT INTO workout_exercise (id, workout_id, exercise_id, repeats, weight, orders)
VALUES (4, 2, 3, 10, 80.0, 2);

-- Разведение гантелей в тренировке груди (3 подхода)
INSERT INTO workout_exercise (id, workout_id, exercise_id, repeats, weight, orders)
VALUES (7, 2, 4, 15, 12.0, 3);

INSERT INTO workout_exercise (id, workout_id, exercise_id, repeats, weight, orders)
VALUES (8, 2, 4, 12, 14.0, 4);

INSERT INTO workout_exercise (id, workout_id, exercise_id, repeats, weight, orders)
VALUES (9, 2, 4, 10, 16.0, 5);