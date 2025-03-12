-- Добавляем изображения в таблицу image
INSERT INTO image (id, original, desktop, mobile, image_type)
VALUES (100, 'https://minio/original-image-nogi.jpg', NULL, NULL, 'workout_image');

INSERT INTO image (id, original, desktop, mobile, image_type)
VALUES (101, 'https://minio/sisi.jpg', NULL, NULL, 'workout_image');

-- Привязываем изображения к тренировкам (одно изображение на одну тренировку)
UPDATE image
SET workout_id = 100
WHERE id = 100; -- Тренировка ног

UPDATE image
SET workout_id = 101
WHERE id = 101; -- Тренировка груди