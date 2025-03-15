-- Упражнения
INSERT INTO exercise (id, title, description, video_url, owner)
VALUES (100, 'Приседания', 'Приседания со штангой', 'https://example.com/squat', 100);

INSERT INTO exercise (id, title, description, video_url, owner)
VALUES (101, 'Жим ногами', 'Жим ногами в тренажёре', 'https://example.com/leg-press', 100);

INSERT INTO exercise (id, title, description, video_url, owner)
VALUES (102, 'Жим лежа', 'Жим штанги лежа', 'https://example.com/bench', 100);

INSERT INTO exercise (id, title, description, video_url, owner)
VALUES (103, 'Разведение гантелей', 'Разведение гантелей лёжа', 'https://example.com/flyes', 100);

INSERT INTO exercise (id, title, description, video_url, owner)
VALUES (104, 'Жим гантелей', 'Жим гантелей лёжа', 'https://example.com/push', 100);

-- Приседания в тренировке ног (2 подхода)
INSERT INTO workout_exercise (id, workout_id, exercise_id, repeats, weight, orders)
VALUES (100, 100, 100, 10, 80.0, 1);

INSERT INTO workout_exercise (id, workout_id, exercise_id, repeats, weight, orders)
VALUES (101, 100, 100, 8, 100.0, 2);

-- Жим лёжа в тренировке груди (2 подхода)
INSERT INTO workout_exercise (id, workout_id, exercise_id, repeats, weight, orders)
VALUES (102, 101, 102, 12, 60.0, 1);

INSERT INTO workout_exercise (id, workout_id, exercise_id, repeats, weight, orders)
VALUES (103, 101, 102, 10, 80.0, 2);

-- Жим ногами в тренировке ног (2 подхода)
INSERT INTO workout_exercise (id, workout_id, exercise_id, repeats, weight, orders)
VALUES (104, 100, 101, 15, 120.0, 3);

INSERT INTO workout_exercise (id, workout_id, exercise_id, repeats, weight, orders)
VALUES (105, 100, 101, 12, 140.0, 4);

-- Разведение гантелей в тренировке груди (3 подхода)
INSERT INTO workout_exercise (id, workout_id, exercise_id, repeats, weight, orders)
VALUES (106, 101, 103, 15, 12.0, 3);

INSERT INTO workout_exercise (id, workout_id, exercise_id, repeats, weight, orders)
VALUES (107, 101, 103, 12, 14.0, 4);

INSERT INTO workout_exercise (id, workout_id, exercise_id, repeats, weight, orders)
VALUES (108, 101, 103, 10, 16.0, 5);