package ru.shavlov.springproject1.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.shavlov.springproject1.Services.BooksService;
import ru.shavlov.springproject1.Services.PeopleService;
import ru.shavlov.springproject1.models.Book;
import ru.shavlov.springproject1.models.Person;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BooksService booksService;
    private final PeopleService peopleService;

    @Autowired
    public BooksController(BooksService booksService, PeopleService peopleService) {
        this.booksService = booksService;
        this.peopleService = peopleService;
    }

    @GetMapping
    public String index(Model model, @RequestParam(value = "page", required = false) Integer page,
                        @RequestParam(value = "books_per_page", required = false) Integer booksPerPage,
                        @RequestParam(value = "sort_by_realiseYear", required = false) boolean sortByRealiseYear) {
        if (page == null | booksPerPage == null)
            model.addAttribute("books", booksService.findAll(sortByRealiseYear));
        else
            model.addAttribute("books", booksService.findWithPagination(page, booksPerPage, sortByRealiseYear));

        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", booksService.findById(id));
        //Если книга принадлежит человеку, мы должны показывать этого человека

        Person bookOwner = booksService.getBookOwner(id);

        // если в bookOwner есть владелец книги то кладем его,
        // иначе показываем выпадающий список со всеми людьми
        if (bookOwner != null) {
            model.addAttribute("owner", bookOwner);
        } else {
            model.addAttribute("people", peopleService.findAll());
        }
        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(Model model) {
        model.addAttribute("book", new Book());
        return "books/new";
    }

    @PostMapping
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "books/new";

        booksService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", booksService.findById(id));
        return "books/edit";
    }

    @PatchMapping("/{id}/update")
    public String update(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult, @PathVariable("id") int id) {

        if (bindingResult.hasErrors()) return "books/edit";

        booksService.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        booksService.delete(id);
        return "redirect:/books";
    }

    // освобождает книгу
    @PatchMapping("/{id}/realise")
    public String realise(@PathVariable("id") int id) {
        booksService.realise(id);
        return "redirect:/books/" + id;
    }

    // назначает книгу человеку
    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @RequestParam("person.id") int personId) {
        Person selectedPerson = peopleService.findById(personId);
        if (selectedPerson != null) {
            booksService.assign(id, selectedPerson);
        }
        return "redirect:/books/" + id;
    }

    @GetMapping("/search")
    public String search() {
        return "books/search";
    }

    @PostMapping("/search")
    public String makeSearch(Model model, @RequestParam("query") String query) {
        model.addAttribute("books", booksService.findBookByTitle(query));
        return "books/search";
    }
}