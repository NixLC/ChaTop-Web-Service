-- Database user creation :
-- THIS IS AN EXAMPLE ! You should use your own credentials and privileges set
CREATE USER 'rentals_admin'@'localhost' IDENTIFIED BY 'your_secure_password_here';
GRANT ALL PRIVILEGES ON rentals.* TO 'rentals_admin'@'localhost';
FLUSH PRIVILEGES;