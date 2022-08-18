package book.manager.mapper;

import book.manager.entity.AuthUser;
import book.manager.service.AuthService;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    @Insert("insert into users(name, role, password) values(#{name}, #{role}, #{password})")
    int registerUser(AuthUser user);

   @Insert("insert into student(uid, name, grade, sex) values(#{uid}, #{name}, #{grade}, #{sex})")
    int addStudentInfo(@Param("uid") int uid, @Param("name") String name, @Param("grade") String grade, @Param("sex") String sex);

    @Select("select * from users where name = #{name}")
    AuthUser getPasswordByUsername(String name);

    @Select("select sid from student where uid = #{uid}")
    Integer getSidByUserId(Integer uid);

    @Select("select count(*) from student")
    int getStudentCount();
}
