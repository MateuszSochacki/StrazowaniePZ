package com.model;

import javax.persistence.*;

/**
 * Created by msoch_000 on 10-04-2017.
 */
@Entity
@Table(name = "CATEGORY", schema = "PUBLIC", catalog = "DOCUMENTS")
public class CategoryEntity {
    private int idCategory;
    private String name;

    @Id
    @Column(name = "ID_CATEGORY")
    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    @Basic
    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CategoryEntity that = (CategoryEntity) o;

        if (idCategory != that.idCategory) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idCategory;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
