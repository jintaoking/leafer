package org.ziwenxie.leafer.db;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.ziwenxie.leafer.model.ArticleTag;


@Component
public interface ArticleTagMapper {

    String insertOneArticleTag = "INSERT INTO article_tag(article_id, tag_id)" +
            " VALUES(#{articleTag.articleId}, #{articleTag.tagId})";

    String deleteOneArticleTag = "DELETE FROM article_tag WHERE(" +
            "article_id = #{articleTag.articleId} AND tag_id = #{articleTag.tagId})";

    @Insert(insertOneArticleTag)
    void insertOneArticleTag(@Param("articleTag") ArticleTag articleTag);

    @Delete(deleteOneArticleTag)
    void deleteOneArticleTag(@Param("articleTag") ArticleTag articleTag);
}
