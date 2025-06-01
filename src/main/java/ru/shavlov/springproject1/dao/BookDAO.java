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

    public Book show(int bookId) {
        return jdbcTemplate.query("SELECT * FROM book WHERE book_id=?", new Object[]{bookId},
                new BeanPropertyRowMapper<>(Book.class)).stream().findAny().orElse(null);
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO book(book_name, book_author, realise_year) VALUES (?, ?, ?)",
                book.getBookName(), book.getBookAuthor(), book.getRealiseYear());
    }

    public void update(int bookId, Book book) {
        jdbcTemplate.update("UPDATE book SET book_name=?, book_author=?, realise_year=? WHERE book_id=?",
                book.getBookName(), book.getRealiseYear(), book.getRealiseYear(), bookId);
    }

    public void delete(int bookId) {
        jdbcTemplate.update("DELETE FROM book WHERE book_id=?", bookId);
    }

    // getBookOwner() - нужен в book в show, проверяем, есть ли владелец у книги или нет
    // join'нем 2 таблицы и получаем человека которому принадлежит книга с указанным id
    // person.* from book - выбирает все колонки из таблицы person
    public Optional<Person> getBookOwner(int bookId){
        return jdbcTemplate.query("SELECT person.* FROM book JOIN person ON book.person_id = person.person_id WHERE book_id= ?",
                new Object[bookId], new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    // realise освобождает книгу у человека
    public void realise(int personId){
        jdbcTemplate.update("UPDATE book SET book.person_id=NULL WHERE person_id=?", personId);
    }

    // назначает книгу человеку
    public void assign(int bookId, Person selectedPerson){
        jdbcTemplate.update("UPDATE book SET book.person_id=? WHERE book_id=?", selectedPerson.getPersonId(), bookId);
    }
}