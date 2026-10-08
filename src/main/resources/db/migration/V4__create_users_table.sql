CREATE TABLE users {
    id TEXT PRIMARY KEY UNIQUE NOT NULL,
    username TEXT NOT NULL UNIQUE,
    email TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL,
    approle TEXT NOT NULL,
    height DECIMAL(3,2) NOT NULL,
    gender TEXT NOT NULL
}