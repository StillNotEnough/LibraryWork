package ru.shavlov.springproject1.Services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shavlov.springproject1.models.Book;
import ru.shavlov.springproject1.models.Person;
import ru.shavlov.springproject1.repositories.PeopleRepository;

import java.time.*;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    public Person findById(int id) {
        Optional<Person> person = peopleRepository.findById(id);
        return person.orElse(null);
    }

    @Transactional
    public void save(Person person) {
        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person updatedPerson) {
        Person personToBeUpdated = peopleRepository.findById(id).get();

        updatedPerson.setId(id);
        updatedPerson.setBooks(personToBeUpdated.getBooks());

        peopleRepository.save(updatedPerson);
    }

    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }

    // метод для уникальности ФИО
    public Optional<Person> findPersonByFullName(String fullName) {
        return peopleRepository.findPersonByFullName(fullName);
    }

    // метод для получения книг по id Person
    public List<Book> getBooksByPersonId(int id) {
        Optional<Person> person = peopleRepository.findById(id);
        if (person.isPresent()) {
            Hibernate.initialize(person.get().getBooks());

            person.get().getBooks().forEach(book -> {
                // timestamp use microseconds

                // time is adding object in microseconds
                long objectMicrosecond = (book.getTakenAt().getEpochSecond() * 1_000_000) +
                        (book.getTakenAt().getNano() / 1000);

                // current time in microseconds
                long currentMicrosecond = (Instant.now().getEpochSecond() * 1_000_000) +
                        (Instant.now().getNano()) / 1000;

                long diffInMicro = Math.abs(objectMicrosecond - currentMicrosecond);

                // 10 day = 864_000_000_000 microseconds
                long tenDays = 864_000_000_000L;
                if (diffInMicro > 864_000_000_000L)
                    book.setExpired(true); // book is expired
            });
            return person.get().getBooks();
        } else {
            return Collections.emptyList();
        }
    }
}
