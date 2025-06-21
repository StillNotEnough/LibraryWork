package ru.shavlov.springproject1.repositories;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shavlov.springproject1.models.Person;

import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
    Optional<Person> findPersonByFullName(@NotEmpty(message = "Имя не может быть пустым") String fullName);
}
