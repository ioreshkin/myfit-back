-- Добавляем изображения в таблицу image
INSERT INTO image (id, original, desktop, mobile, image_type)
VALUES (1, 'https://minio/original-image-nogi.jpg', NULL, NULL, 'workout_image');

INSERT INTO image (id, original, desktop, mobile, image_type)
VALUES (2, 'https://minio/sisi.jpg', NULL, NULL, 'workout_image');

-- Привязываем изображения к тренировкам (одно изображение на одну тренировку)
UPDATE image SET workout_id = 1 WHERE id = 1; -- Тренировка ног
UPDATE image SET workout_id = 2 WHERE id = 2; -- Тренировка груди