package adrian.bookclub.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import adrian.bookclub.models.Book;
import adrian.bookclub.services.BookService;

@Controller
public class BookController {

  @Autowired BookService bookService;
  

  //create book

  @GetMapping("/books/new")
  public String newBook(@ModelAttribute("book")Book book){
    return "books/new.jsp";
  }

  @PostMapping("/books")
  public String createBook(@ModelAttribute("book")Book book){
    bookService.addBook(book);
    return "redirect:/books";
  }

  //read all
  @GetMapping("/books")
  public String allBooks(Model model){
    List<Book> books = bookService.getAll();
    model.addAttribute("books", books);
    return "books/index.jsp";
  }

  // read one 
  @GetMapping("books/{id}") // all were doing is getting data from db
  public String show(@PathVariable("id")Long id,Model model){
    Book book = bookService.getOne(id);
    model.addAttribute("book",book);
    return "books/show.jsp";
  }
  

  //update // renders
  @GetMapping("/books/edit/{id}")
  public String edit(@PathVariable("id")Long id,Model model){
    
    Book book = bookService.getOne(id);
    model.addAttribute("book",book);
    return "books/edit.jsp";
  }

  // update handles form data
  @PutMapping("/books/{id}")
  public String update(@ModelAttribute("book")Book book){
    bookService.updateBook(book);
    return "redirect:/books";
  }

  // delete
  @DeleteMapping("/books/{id}")
  public String destroy(@PathVariable("id")Long id){
    bookService.destroyBook(id);
      return "redirect:/books";
  }



}
