package org.ziwenxie.leafer.model;

import java.io.Serializable;

public class ArticleTag implements Serializable {

    private long articleId;
    private long tagId;

    // required for mybatis
    public ArticleTag() {
    }

    public long getArticleId() {
        return articleId;
    }

    public void setArticleId(long articleId) {
        this.articleId = articleId;
    }

    public long getTagId() {
        return tagId;
    }

    public void setTagId(long tagId) {
        this.tagId = tagId;
    }

    @Override
    public String toString() {
        return "ArticleTag{" +
                "articleId=" + articleId +
                ", tagId=" + tagId +
                '}';
    }
}
