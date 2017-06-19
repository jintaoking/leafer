package org.ziwenxie.leafer.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ziwenxie.leafer.db.TagMapper;
import org.ziwenxie.leafer.model.Tag;
import org.ziwenxie.leafer.util.IdWorker;

import java.util.Date;
import java.util.List;


@Service("tagService")
public class TagServiceImpl implements ITagService {

    private TagMapper tagMapper;

    private IdWorker idWorker;

    private Logger logger;

    @Autowired
    public TagServiceImpl(TagMapper tagMapper) {
        this.tagMapper = tagMapper;
        this.idWorker = new IdWorker(1);
        this.logger = LoggerFactory.getLogger(TagServiceImpl.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertOneTag(Tag tag) {
        tag.setId(idWorker.nextId());
        tag.setCreatedTime(new Date());
        tag.setModifiedTime(new Date());

        // Predicate<String> findTag = tagName -> tagName.equals(tag.getName());
        // boolean isExisted = this.getAllTags().stream().map(Tag :: getName).anyMatch(findTag);
        tagMapper.insertOneTag(tag);

        logger.info("Insert one tag successfully: " + tag.getName());
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteOneTagById(long tagId) {
        tagMapper.deleteOneTagById(tagId);

        logger.info("Delete one tag successfully: " + tagId);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateOneTag(Tag tag) {
        tag.setModifiedTime(new Date());

        tagMapper.updateOneTag(tag);

        logger.info("Delete one tag successfully: " + tag.getName());
        return true;
    }

    @Override
    @Cacheable(value = "getOneTagById", key = "#tagId")
    public Tag getOneTagById(long tagId) {
        return tagMapper.getOneTagById(tagId);
    }

    @Override
    @Cacheable(value = "getOneTagByName", key = "#tagName + #username")
    public Tag getOneTagByName(String tagName, String username) {
        return tagMapper.getOneTagByName(tagName, username);
    }

    @Override
    @Cacheable(value = "getAllTags", key = "#username")
    public List<Tag> getAllTags(String username) {
        return tagMapper.getAllTags(username);
    }

}
