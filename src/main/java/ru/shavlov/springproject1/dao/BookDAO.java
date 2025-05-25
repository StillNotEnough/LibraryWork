package ru.shavlov.springproject1.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.shavlov.springproject1.model.Book;

import java.util.List;

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
}