package org.ziwenxie.leafer.db;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import org.ziwenxie.leafer.model.Article;
import org.ziwenxie.leafer.model.Tag;

import java.util.List;


@Component
public interface ArticleMapper {

    String insertOneArticle = "INSERT INTO article(id, title, body, created_time," +
            " modified_time, username) VALUES(#{article.id}, #{article.title}, #{article.body}," +
            " #{article.createdTime}, #{article.modifiedTime}, #{article.username})";

    String deleteOneArticleById = "DELETE FROM article WHERE id=#{id}";

    String updateOneArticle = "UPDATE article SET title=#{article.title}, body=#{article.body}," +
            "modified_time=#{article.modifiedTime} WHERE id=#{article.id}";

    String getArticlesOfOnePage = "SELECT * FROM article WHERE username=#{username} " +
            "ORDER BY id DESC LIMIT 10 OFFSET #{begin}";

    String getOneArticleById = "SELECT * FROM article WHERE id=#{id}";

    String getAllTagsOfOneArticle = "SELECT tag.* FROM tag INNER JOIN article_tag " +
            "ON tag.id=article_tag.tag_id WHERE article_tag.article_id=#{articleId}";

    String getArticlesCount = "SELECT COUNT(*) FROM article WHERE username=#{username}";

    String getArticleOrder = "SELECT COUNT(*) FROM article " +
            "WHERE username=#{username} AND id > #{id}";

    @Insert(insertOneArticle)
    void insertOneArticle(@Param("article") Article article);

    @Delete(deleteOneArticleById)
    void deleteOneArticleById(@Param("id") long id);

    @Update(updateOneArticle)
    void updateOneArticle(@Param("article") Article article);

    @Select(getArticlesOfOnePage)
    @Results(value={
            @Result(property="id", column="id"),
            @Result(property="title", column="title"),
            @Result(property="body", column="body"),
            @Result(property = "createdTime", column = "created_time"),
            @Result(property = "modifiedTime", column = "modified_time"),
            @Result(property="username", column="username"),
            @Result(property="tags", column="id", many=@Many(
                    select="org.ziwenxie.leafer.db.ArticleMapper.getAllTagsOfOneArticle")),
    })
    List<Article> getArticlesOfOnePage(@Param("username") String username, @Param("begin") int begin);

    @Select(getOneArticleById)
    @Results(value={
            @Result(property="id", column="id"),
            @Result(property="title", column="title"),
            @Result(property="body", column="body"),
            @Result(property = "createdTime", column = "created_time"),
            @Result(property = "modifiedTime", column = "modified_time"),
            @Result(property="username", column="username"),
            @Result(property="tags", column="id", many=@Many(
                    select="org.ziwenxie.leafer.db.ArticleMapper.getAllTagsOfOneArticle")),
    })
    Article getOneArticleById(@Param("id") long id);

    @Select(getAllTagsOfOneArticle)
    List<Tag> getAllTagsOfOneArticle(@Param("articleId") long articleId);

    @Select(getArticlesCount)
    long getArticlesCount(@Param("username") String username);

    @Select(getArticleOrder)
    long getArticleOrder(@Param("username") String username, @Param("id") long id);

}
