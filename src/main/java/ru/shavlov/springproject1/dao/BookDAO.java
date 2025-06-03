package ru.shavlov.springproject1.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.shavlov.springproject1.model.Book;
import ru.shavlov.springproject1.model.Person;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book show(int id) {
        return jdbcTemplate.query("SELECT * FROM book WHERE id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class)).stream().findAny().orElse(null);
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO book(title, author, realise_year) VALUES (?, ?, ?)",
                book.getTitle(), book.getAuthor(), book.getRealiseYear());
    }

    public void update(int id, Book updateBook) {
        jdbcTemplate.update("UPDATE book SET title=?, author=?, realise_year=? WHERE id=?",
                updateBook.getTitle(), updateBook.getAuthor(), updateBook.getRealiseYear(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM book WHERE id=?", id);
    }

    // getBookOwner() - нужен в book в show, проверяем, есть ли владелец у книги или нет
    // join'нем 2 таблицы и получаем человека которому принадлежит книга с указанным id
    // person.* from book - выбирает все колонки из таблицы person
    public Optional<Person> getBookOwner(int id){
        return jdbcTemplate.query("SELECT person.* FROM book JOIN person ON book.person_id = person.id WHERE book.id=?",
                new Object[]{id}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    // realise освобождает книгу у человека
    public void realise(int id){
        jdbcTemplate.update("UPDATE book SET person_id=NULL WHERE id=?", id);
    }

    // назначает книгу человеку
    public void assign(int id, Person selectedPerson){
        jdbcTemplate.update("UPDATE book SET    person_id=? WHERE id=?", selectedPerson.getId(), id);
    }
}