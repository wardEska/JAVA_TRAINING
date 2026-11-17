package com.lev2.entity.base;

import jakarta.persistence.*;

@MappedSuperclass
public abstract class BaseEntity<K> {

    public abstract K getId();

    public abstract void setId(K id);

    public abstract boolean isIdHasValue();
}
