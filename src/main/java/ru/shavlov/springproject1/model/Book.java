package ru.shavlov.springproject1.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class Book {

    private int bookId;

    @NotEmpty(message = "Название книги не может быть пустым")
    @Size(min = 2, max = 100, message = "Название книги должно быть от 2 до 100 символов")
    private String bookName;

    @NotEmpty(message = "Имя автора не может быть пустым")
    @Size(min = 2, max = 100, message = "Имя автора должно быть от 2 до 100 символов")
    private String bookAuthor;

    @Min(value = 1000, message = "Дата выпуска книги должна быть больше 1000 года")
    private int realiseYear;

    public Book(){

    }

    public Book(int bookId, String bookName, String bookAuthor, int realiseYear) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.realiseYear = realiseYear;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public int getRealiseYear() {
        return realiseYear;
    }

    public void setRealiseYear(int realiseYear) {
        this.realiseYear = realiseYear;
    }
}
