package book.manager.service;

import book.manager.entity.Book;
import book.manager.entity.BorrowDetails;

import java.util.List;

public interface BookService {
    public List<Book> getAllBook();
    public List<Book> getAllBookWithoutBorrow();
    List<Book> getAllBorrowedBookByUid(int uid);


    void deleteBook(int id);

    void addBook(String title, String desc, double price);
    boolean borrowBook(int bid, int uid);
    void returnBook(int bid, int uid);
    List<BorrowDetails> getBorrowDetailsList();
}
