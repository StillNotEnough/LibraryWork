package ru.shavlov.springproject1.repositories;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shavlov.springproject1.models.Book;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
    List<Book> findBookByTitleStartingWith(@NotEmpty(message = "Название книги не может быть пустым") String title);
}
