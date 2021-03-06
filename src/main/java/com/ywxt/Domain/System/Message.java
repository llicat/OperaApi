package com.ywxt.Domain.System;

import com.ywxt.Annotation.NotFilterColumn;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "system_message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Date createdTime;
    private Date modifyTime;
    private String title;
    private String theme;
    private String themeId;
    @Column(columnDefinition = "TEXT")
    private String message;
    private String status;
    @NotFilterColumn
    private String imageUrl;
    @NotFilterColumn
    private String evalMatches;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getThemeId() {
        return themeId;
    }

    public void setThemeId(String themeId) {
        this.themeId = themeId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean hasImageUrl() {
        return !(this.imageUrl == null);
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getEvalMatches() {
        return evalMatches;
    }

    public void setEvalMatches(String evalMatches) {
        this.evalMatches = evalMatches;
    }
}
