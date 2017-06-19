package org.ziwenxie.leafer.db;


import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import org.ziwenxie.leafer.model.Article;
import org.ziwenxie.leafer.model.Tag;

import java.util.List;

@Component
public interface TagMapper {

    String insertOneTag = "INSERT INTO tag(id, name, description, username, created_time," +
            "modified_time) VALUES(#{tag.id}, #{tag.name}, #{tag.description}," +
            "#{tag.username}, #{tag.createdTime}, #{tag.modifiedTime})";

    String deleteOneTagById = "DELETE FROM tag WHERE id = #{tagId}";

    String updateOneTag = "UPDATE tag name=#{tag.name}," +
            "description=#{tag.description}, modified_time=#{tag.modifiedTime} WHERE id=#{tag.id}";

    String getAllTags = "SELECT * FROM tag WHERE username = #{username}";

    String getOneTagById = "SELECT * FROM tag WHERE id = #{tagId}";

    String getOneTagByName = "SELECT * FROM tag WHERE name = #{tagName} AND username = #{username}";

    String getAllArticlesInOneTag = "SELECT article.* FROM article INNER JOIN article_tag" +
            " ON article.id = article_tag.article_id WHERE article_tag.tag_id = #{tagId} " +
            "ORDER BY article.id DESC";

    @Insert(insertOneTag)
    void insertOneTag(@Param("tag") Tag tag);

    @Delete(deleteOneTagById)
    void deleteOneTagById(@Param("tagId") long tagId);

    @Update(updateOneTag)
    void updateOneTag(@Param("tag") Tag tag);

    @Select(getOneTagById)
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "description", column = "description"),
            @Result(property = "createdTime", column = "created_time"),
            @Result(property = "username", column = "username"),
            @Result(property = "articles", column = "id", many = @Many(
                    select = "org.ziwenxie.leafer.db.TagMapper.getAllArticlesInOneTag")),
    })
    Tag getOneTagById(@Param("tagId") long tagId);

    @Select(getOneTagByName)
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "description", column = "description"),
            @Result(property = "createdTime", column = "created_time"),
            @Result(property = "username", column = "username"),
            @Result(property = "articles", column = "id", many = @Many(
                    select = "org.ziwenxie.leafer.db.TagMapper.getAllArticlesInOneTag")),
    })
    Tag getOneTagByName(@Param("tagName") String tagName, @Param("username") String username);

    @Select(getAllTags)
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "description", column = "description"),
            @Result(property = "createdTime", column = "created_time"),
            @Result(property = "username", column = "username"),
            @Result(property = "articles", column = "id", many = @Many(
                    select = "org.ziwenxie.leafer.db.TagMapper.getAllArticlesInOneTag")),
    })
    List<Tag> getAllTags(@Param("username") String username);


    @Select(getAllArticlesInOneTag)
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "title", column = "title"),
            @Result(property = "post", column = "post"),
            @Result(property = "createdTime", column = "created_time"),
            @Result(property = "modifiedTime", column = "modified_time"),
            @Result(property = "tags", column="id", many=@Many(
                    select="org.ziwenxie.leafer.db.ArticleMapper.getAllTagsOfOneArticle")),
    })
    List<Article> getAllArticlesInOneTag(@Param("tagId") long tagId);

}
