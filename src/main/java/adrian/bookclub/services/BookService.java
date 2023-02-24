package adrian.bookclub.services;



import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import adrian.bookclub.models.Book;
import adrian.bookclub.repositories.BookRepository;
@Service
public class BookService {

    @Autowired BookRepository bookRepository;

    //! CREATE

    public void addBook(Book book){
        bookRepository.save(book);

    }

    //! READ ALL
    public List<Book> getAll(){
        return bookRepository.findAll();
        
    }

    //! READ ONE

	public Book getOne(Long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        return optionalBook.orElse(null);
	}

    //! UPDATE

    public void updateBook(Book book){
        bookRepository.save(book);
    }

    //! DELETE
    public void destroyBook(Long id){
        bookRepository.deleteById(id);
    }
    
}