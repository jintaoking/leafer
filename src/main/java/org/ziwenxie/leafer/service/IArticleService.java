package org.ziwenxie.leafer.service;


import org.ziwenxie.leafer.model.Article;
import org.ziwenxie.leafer.model.Tag;

import java.util.List;

public interface IArticleService {

    long insertOneArticle(Article article);

    boolean deleteOneArticleById(long id, String username);

    boolean updateOneArticle(Article article, String username, int page);

    List<Article> getArticlesOfOnePage(String username, int page);

    Article getOneArticleById(long id, String username);

    List<Tag> getAllTagsOfOneArticle(long id);

    long getArticlesCount(String username);

    int getArticlePage(String username, long articleId);

}
