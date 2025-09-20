# Library Management System (Spring MVC + Hibernate)

Простое веб-приложение для управления библиотекой книг и читателей.  
Проект построен на **Spring MVC**, **Hibernate (JPA)**, **Thymeleaf** и **PostgreSQL**.

---

## ✨ Возможности
- 📚 Управление книгами:
  - добавление, редактирование, удаление книг;
  - просмотр всех книг с пагинацией и сортировкой по году издания;
  - поиск книг по названию (по префиксу);
  - назначение книги читателю и освобождение книги;
  - отметка книг как «просроченные», если они на руках более 10 дней.

- 👤 Управление читателями:
  - добавление, редактирование, удаление читателей;
  - валидация уникальности имени читателя;
  - просмотр всех книг, находящихся у конкретного читателя;
  - подсветка просроченных книг.

---

## 🛠️ Технологии
- **Java 17+**
- **Spring Framework (Spring MVC, Spring Data JPA)**
- **Hibernate**
- **Thymeleaf**
- **PostgreSQL**
- **Maven**

---

## 📂 Архитектура
- `controllers/` – контроллеры
- `services/` – бизнес-логика
- `repositories/` – работа с БД 
- `models/` – сущности JPA 
- `util/` – вспомогательные классы и валидаторы 
- `resources/templates/` – HTML-шаблоны 
- `resources/hibernate.properties` – настройки подключения к БД 

---

## ⚙️ Настройка и запуск

### 1. Клонировать проект
```bash
git clone https://github.com/your-username/library-management.git
cd library-management
```
2. Настроить базу данных PostgreSQL
Создать БД, например:

```sql
CREATE DATABASE library_db;
```
3. Указать параметры подключения
В файле src/main/resources/hibernate.properties (создать вручную, если его нет):

```properties
hibernate.driver_class=org.postgresql.Driver
hibernate.connection.url=jdbc:postgresql://localhost:5432/library_db
hibernate.connection.username=your_username
hibernate.connection.password=your_password

hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
hibernate.show_sql=true
hibernate.hbm2ddl.auto=update
```
4. Запустить приложение
```bash
mvn clean install
mvn tomcat7:run
```
Приложение будет доступно по адресу:
👉 http://localhost:8080/
