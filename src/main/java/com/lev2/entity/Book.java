package com.lev2.entity;

import com.lev2.entity.base.IdentityBaseEntity;
import com.lev2.entity.dto.base.BaseEntityDto;
import com.lev2.helper.table.names.TableNames;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = TableNames.book)
public class Book extends IdentityBaseEntity implements BaseEntityDto {
    @Column(name = "title")
    private String title;
    @Column(name = "author")
    private String author;
    @Column(name = "price")
    private double price;
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
