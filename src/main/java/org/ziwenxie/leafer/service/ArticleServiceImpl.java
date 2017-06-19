package org.ziwenxie.leafer.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ziwenxie.leafer.db.ArticleMapper;
import org.ziwenxie.leafer.model.Article;
import org.ziwenxie.leafer.model.Tag;
import org.ziwenxie.leafer.util.IdWorker;

import java.util.Date;
import java.util.List;


@Service("articleService")
public class ArticleServiceImpl implements IArticleService {

    private ArticleMapper articleMapper;

    private IdWorker idWorker;

    private Logger logger;

    private CacheManager cacheManager;

    @Autowired
    public ArticleServiceImpl(ArticleMapper articleMapper, CacheManager cacheManager) {
        this.articleMapper = articleMapper;
        this.cacheManager = cacheManager;
        this.idWorker = new IdWorker(1);
        this.logger = LoggerFactory.getLogger(ArticleServiceImpl.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Caching(evict = {
            @CacheEvict(value = "getOneArticleById", key = "#article.id"),
            @CacheEvict(value = "getArticlesCount", key = "#article.username")
    })
    public long insertOneArticle(Article article) {
        long articleId = idWorker.nextId();
        article.setId(articleId);
        article.setCreatedTime(new Date());  // add the created time
        article.setModifiedTime(new Date());  // add the modified time

        articleMapper.insertOneArticle(article);

        logger.info("Insert one article successfully: " + article.getTitle());

        deleteAllPagesCache(article.getUsername());

        return articleId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Caching(evict = {
            @CacheEvict(value = "getOneArticleById", key = "#articleId"),
            @CacheEvict(value = "getArticlesCount", key = "#username")
    })
    public boolean deleteOneArticleById(long articleId, String username) {
        articleMapper.deleteOneArticleById(articleId);
        logger.info("Delete one article successfully: " + articleId);

        deleteAllPagesCache(username);

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Caching(evict = {
            @CacheEvict(value = "getOneArticleById", key = "#article.id"),
            @CacheEvict(value = "getArticlesOfOnePage", key = "#username + #page")
    })
    public boolean updateOneArticle(Article article, String username, int page) {
        // note: article object only contains id, title, body property
        article.setModifiedTime(new Date());

        articleMapper.updateOneArticle(article);
        logger.info("Update one article successfully: " + article.getTitle());

        return true;
    }

    @Override
    @Cacheable(value = "getArticlesOfOnePage", key = "#username + #page")
    public List<Article> getArticlesOfOnePage(String username, int page) {
        return articleMapper.getArticlesOfOnePage(username, (page -1)*10);
    }

    @Override
    @Cacheable(value = "getOneArticleById", key = "#id")
    public Article getOneArticleById(long id, String username) {
        return articleMapper.getOneArticleById(id);
    }

    @Override
    @Cacheable(value = "getAllTagsOfOneArticle", key = "#id")
    public List<Tag> getAllTagsOfOneArticle(long id) {
        return articleMapper.getAllTagsOfOneArticle(id);
    }

    @Override
    @Cacheable(value = "getArticlesCount", key = "#username")
    public long getArticlesCount(String username) {
        return articleMapper.getArticlesCount(username);
    }

    @Override
    public int getArticlePage(String username, long articleId) {
        long articleOrder = articleMapper.getArticleOrder(username, articleId) - 1;
        int page;
        if (articleOrder < 0) {  // first article
            page = 1;
        } else {
            page = (int) (articleOrder/10 + 1);
        }

        return page;
    }


    private void deleteAllPagesCache(String username) {
        long pages = articleMapper.getArticlesCount(username)/10 + 1;
        for (int page=1; page<=pages; page++) {
            cacheManager.getCache("getArticlesOfOnePage").evict(username + page);
        }
    }

}
