CREATE TABLE IF NOT EXISTS roles (
  id INT PRIMARY KEY AUTO_INCREMENT,
  role VARCHAR(15) NOT NULL
);

CREATE TABLE IF NOT EXISTS users (
  id INT PRIMARY KEY AUTO_INCREMENT,
  first_name VARCHAR(255) NOT NULL ,
  last_name VARCHAR(255),
  password VARCHAR(255) NOT NULL,
  role_id INT NOT NULL,
  FOREIGN KEY (role_id) REFERENCES roles(id)
);

# INSERT INTO roles (role) VALUE ('developer');
# INSERT INTO roles (role) VALUES ('manger'), ('customer');

CREATE TABLE IF NOT EXISTS tasks (
  id INT PRIMARY KEY AUTO_INCREMENT,
  customer_id INT NOT NULL ,
  title VARCHAR(255) NOT NULL,
  description VARCHAR(1000),
  checked TINYINT NOT NULL ,
  FOREIGN KEY (customer_id) REFERENCES users(id)
);

# ALTER TABLE tasks MODIFY checked TINYINT(1);
#
# ALTER TABLE tasks ADD created_at TIMESTAMP DEFAULT now(),
#   ADD edited_at TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ;

CREATE TABLE IF NOT EXISTS accounts (
  id INT PRIMARY KEY AUTO_INCREMENT,
  value INT,
  accepted TINYINT(1) DEFAULT 0
);

ALTER TABLE tasks ADD account_id INT DEFAULT NULL ,
  ADD FOREIGN KEY (account_id) REFERENCES accounts(id);