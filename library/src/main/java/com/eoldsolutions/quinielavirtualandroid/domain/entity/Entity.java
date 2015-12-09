package com.eoldsolutions.quinielavirtualandroid.domain.entity;

public interface Entity {

    Long getId();

    void setId(Long id);

    String toJsonString();
}
