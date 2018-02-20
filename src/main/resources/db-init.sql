CREATE DATABASE osoon_db ENCODING 'UTF8';
CREATE USER osoon_admin WITH PASSWORD 'osoon_passwd';
GRANT ALL PRIVILEGES ON DATABASE "osoon_db" to osoon_admin;
