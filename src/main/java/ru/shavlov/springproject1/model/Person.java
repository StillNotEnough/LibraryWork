package ru.shavlov.springproject1.model;


import jakarta.validation.constraints.*;

public class Person {

    private int personId;

    @NotEmpty(message = "Имя не может быть пустым")
    @Size(min = 2, max = 100, message = "Имя должно содержать от 2 до 100 символов")
    private String fullName;

    @Min(value = 1900, message = "Минимальный допустимы год рождения 1900 год")
    private int yearOfBirth;

    public Person() {

    }

    public Person(int personId, String fullName, int yearOfBirth) {
        this.personId = personId;
        this.fullName = fullName;
        this.yearOfBirth = yearOfBirth;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }
}
