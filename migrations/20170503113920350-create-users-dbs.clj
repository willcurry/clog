;; migrations/20170503113920350-create-users-dbs.clj

(defn up []
  ["DROP TABLE IF EXISTS users"
   "CREATE TABLE roles(role_id serial PRIMARY KEY, name text)"
   "CREATE TABLE users(user_id serial PRIMARY KEY, email text)"
   "CREATE TABLE permissions(permission_id char PRIMARY KEY, name text)"
   "CREATE TABLE user_roles(user_id int references users(user_id), role_id int references roles(role_id))"
   "CREATE TABLE role_permissions(role_id int references roles(role_id), permission_id char references permissions(permission_id))"])

(defn down []
  ["DROP TABLE role_permissions"
   "DROP TABLE user_roles"
   "DROP TABLE roles"
   "DROP TABLE users"
   "DROP TABLE permissions"])
