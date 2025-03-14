INSERT INTO image (id, original, desktop, mobile, image_type)
VALUES (100, 'https://minio/original-image-nogi.jpg', NULL, NULL, 'exercise_image');

INSERT INTO image (id, original, desktop, mobile, image_type)
VALUES (101, 'https://minio/sisi.jpg', NULL, NULL, 'exercise_image');

UPDATE image
SET exercise_id = 100
WHERE id = 100;

UPDATE image
SET exercise_id = 101
WHERE id = 101;