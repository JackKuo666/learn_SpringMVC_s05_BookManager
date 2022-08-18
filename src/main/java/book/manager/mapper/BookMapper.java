package book.manager.mapper;

import book.manager.entity.Book;
import book.manager.entity.Borrow;
import book.manager.entity.BorrowDetails;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BookMapper {

    @Select("select * from book")
    List<Book> allBook();

    @Delete("delete from book where bid = #{bid}")
    void deleteBook(int bid);

    @Insert("insert into book(title, `desc`, price) values(#{title}, #{desc}, #{price})")
    void addBook(@Param("title") String title, @Param("desc") String desc, @Param("price") double price);

    @Insert("insert into borrow(bid, sid, `time`) values(#{bid}, #{sid}, NOW())")
    Boolean addBorrow(@Param("bid") int bid, @Param("sid") int sid);

    @Select("select * from borrow ")
    List<Borrow> borrowList();

    @Delete("delete from borrow where bid = #{bid} and sid = #{sid}")
    void deleteBorrow(@Param("bid") int bid, @Param("sid") int sid);

    @Select("select * from borrow where sid = #{sid}")
    List<Borrow> getBorrowListBySid(@Param("sid") int sid);

    @Select("select * from book where bid = #{bid}")
    Book getBookByBid(@Param("bid") int bid);

    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "title", property = "book_title"),
            @Result(column = "name", property = "user_name"),
            @Result(column = "time", property = "time")
    })
    @Select("SELECT * FROM borrow LEFT JOIN book on book.bid = borrow.bid " +
            "LEFT JOIN student ON borrow.sid = student.sid")
    List<BorrowDetails> borrowDetailsList();

    @Select("select count(*) from book")
    int getBookCount();

    @Select("select count(*) from borrow")
    int getBorrowCount();

}
