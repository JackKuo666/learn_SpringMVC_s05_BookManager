package book.manager.service.impl;

import book.manager.entity.Book;
import book.manager.entity.Borrow;
import book.manager.entity.BorrowDetails;
import book.manager.mapper.BookMapper;
import book.manager.mapper.UserMapper;
import book.manager.service.BookService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    @Resource
    BookMapper bookMapper;

    @Resource
    UserMapper userMapper;


    @Override
    public List<Book> getAllBook() {
        return bookMapper.allBook();
    }

    @Override
    public List<Book> getAllBookWithoutBorrow() {
        List<Book> books = bookMapper.allBook();
        List<Integer> borrowBids = bookMapper.borrowList().stream()
                .map(Borrow::getBid).collect(Collectors.toList());

        return books.stream()
                .filter(book -> !borrowBids.contains(book.getBid()))
                .collect(Collectors.toList());

    }

    @Override
    public List<Book> getAllBorrowedBookByUid(int uid) {
        Integer sid = userMapper.getSidByUserId(uid);
        if (sid == null) {
            return Collections.emptyList();
        }
        return bookMapper.getBorrowListBySid(sid).stream()
                .map(borrow -> bookMapper.getBookByBid(borrow.getBid()))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteBook(int bid) {
        bookMapper.deleteBook(bid);
    }

    @Override
    public void addBook(String title, String desc, double price) {
        bookMapper.addBook(title, desc, price);
    }

    @Override
    public boolean borrowBook(int bid, int uid) {
        Integer sid = userMapper.getSidByUserId(uid);
        if (sid == null) {
            return false;
        }
        return bookMapper.addBorrow(bid, sid);
    }

    @Override
    public void returnBook(int bid, int uid) {
        Integer sid = userMapper.getSidByUserId(uid);
        if (sid != null) {
            bookMapper.deleteBorrow(bid, sid);
        }
    }

    @Override
    public List<BorrowDetails> getBorrowDetailsList() {
        return bookMapper.borrowDetailsList();
    }
}
