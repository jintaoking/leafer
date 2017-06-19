package org.ziwenxie.leafer.service;


import org.ziwenxie.leafer.model.Tag;

import java.util.List;

public interface ITagService {

    boolean insertOneTag(Tag tag);
    boolean deleteOneTagById(long tagId);
    boolean updateOneTag(Tag tag);
    Tag getOneTagById(long tagId);
    Tag getOneTagByName(String tagName, String username);
    List<Tag> getAllTags(String username);
}
