package ru.shavlov.springproject1.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shavlov.springproject1.models.Book;
import ru.shavlov.springproject1.models.Person;
import ru.shavlov.springproject1.repositories.BooksRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {

    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> findAll(boolean sortByRealiseYear) {
        if (sortByRealiseYear)
            return booksRepository.findAll(Sort.by("realiseYear"));
        else
            return booksRepository.findAll();
    }

    public List<Book> findWithPagination(Integer page, Integer booksPerPage, boolean sortByRealiseYear){
        if (sortByRealiseYear)
            return booksRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("realiseYear"))).getContent();
        else
            return booksRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();

    }

    public Book findById(int id) {
        Optional<Book> book = booksRepository.findById(id);
        return book.orElse(null);
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        Book bookToBeUpdated = booksRepository.findById(id).get();

        updatedBook.setId(id);
        updatedBook.setOwner(bookToBeUpdated.getOwner()); // чтобы связь не терялась

        booksRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    // if owner is not - return null
    // side one, here eager loading, hibernate.initialize() don't need
    public Person getBookOwner(int id){
        return booksRepository.findById(id).map(Book::getOwner).orElse(null);
    }

    // assign назначает книгу человеку
    @Transactional
    public void assign(int id, Person selectedPerson){
       booksRepository.findById(id).ifPresent(book -> {
           book.setOwner(selectedPerson);
           book.setTakenAt(Instant.now()); // I am not sure
       });
    }

    // realise освобождает книгу у человека
    @Transactional
    public void realise(int id){
        booksRepository.findById(id).ifPresent(book -> {
            book.setOwner(null);
            book.setTakenAt(null);
        });
    }

    public List<Book> findBookByTitle(String titleStarting){
        return booksRepository.findBookByTitleStartingWith(titleStarting);
    }
}