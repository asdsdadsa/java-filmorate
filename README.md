# java-filmorate
Template repository for Filmorate project.
## Схема базы данных
![Схема базы данных](./diagram.png)

### Примеры команд

### Запрос на получение всех фильмов

SELECT name
FROM films;

### Запрос на получение информации о фильме по id

SELECT *
FROM films
WHERE film_id = ?


### Запрос на получение информации о пользователе по id

SELECT *
FROM users
WHERE user_id = ?
