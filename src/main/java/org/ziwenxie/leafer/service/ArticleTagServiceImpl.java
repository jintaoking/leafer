package org.ziwenxie.leafer.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ziwenxie.leafer.db.ArticleTagMapper;
import org.ziwenxie.leafer.model.ArticleTag;


@Service("articleTagService")
public class ArticleTagServiceImpl implements IArticleTagService {

    private ArticleTagMapper articleTagMapper;

    private Logger logger;

    @Autowired
    public ArticleTagServiceImpl(ArticleTagMapper articleTagMapper) {
        this.articleTagMapper = articleTagMapper;
        this.logger = Logger.getLogger(ArticleServiceImpl.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Caching(evict = {
            @CacheEvict(value = "getOneArticleById", key = "#articleTag.articleId"),
            @CacheEvict(value = "getOneTagById", key = "#articleTag.tagId")
    })
    public boolean insertOneArticleTag(ArticleTag articleTag) {
        articleTagMapper.insertOneArticleTag(articleTag);
        logger.info("Insert articleTag: " + articleTag.toString() + "successfully");
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Caching(evict = {
            @CacheEvict(value = "getOneArticleById", key = "#articleTag.articleId"),
            @CacheEvict(value = "getOneTagById", key = "#articleTag.tagId")
    })
    public boolean deleteOneArticleTag(ArticleTag articleTag) {
        articleTagMapper.deleteOneArticleTag(articleTag);
        logger.info("Delete articleTag: " + articleTag.toString() + "successfully");
        return true;
    }

}
