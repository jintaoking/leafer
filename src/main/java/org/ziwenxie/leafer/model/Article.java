package org.ziwenxie.leafer.model;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Article implements Serializable {

    private long id;
    private String title;
    private String body;
    private Date createdTime;
    private Date modifiedTime;
    private List<Tag> tags;
    private String username;

    // required for mybatis
    public Article() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Article{" +
                "title='" + title + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
