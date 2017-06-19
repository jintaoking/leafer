package org.ziwenxie.leafer.db;


import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import org.ziwenxie.leafer.model.User;

@Component
public interface UserMapper {

    String insertOneUser = "INSERT INTO user(id, password, username, is_male, bio, email, blog, created_time," +
            "birthday) VALUES(#{user.id}, #{user.password}, #{user.username}, #{user.gender}, #{user.description}," +
           "#{user.email}, #{user.blog}, #{user.createdTime}, #{user.birthday})";

    String insertOneRole = "INSERT INTO role(id, username, role) VALUES(#{user.id}," +
            "#{user.username}, 'ROLE_USER')";

    String updateUserPassword = "UPDATE user SET password = #{password} WHERE id = #{userId}";

    @Insert(insertOneUser)
    void insertOneUser(@Param("user") User user);

    @Insert(insertOneRole)
    void insertOneRole(@Param("user") User user);

    @Update(updateUserPassword)
    void updateUserPassword(@Param("password") String password, @Param("userId") Long userId);

}
