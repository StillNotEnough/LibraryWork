package ru.shavlov.springproject1.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.shavlov.springproject1.model.Person;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int personId) {
        return jdbcTemplate.query("SELECT * FROM person WHERE person_id=?", new Object[]{personId},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
    }

    public Optional<Person> show(String fullName) {
        return jdbcTemplate.query("SELECT * FROM person WHERE full_name=?", new Object[]{fullName},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO person(full_name, year_of_birth) VALUES (?, ?)",
                person.getFullName(), person.getYearOfBirth());
    }

    public void update(int personId, Person updatedPerson) {
        jdbcTemplate.update("UPDATE person SET full_name=?, year_of_birth=? WHERE person_id=?",
                updatedPerson.getFullName(), updatedPerson.getYearOfBirth(), personId);
    }

    public void delete(int personId) {
        jdbcTemplate.update("DELETE FROM person WHERE person_id=?", personId);
    }
}