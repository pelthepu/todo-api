package com.pavan.todo.models;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Document(collection = "todos")
@Getter
@Setter
public class Todo {

    @Id
    private String id;

    @NotBlank
    @Size(max = 255)
    @Indexed(unique = true)
    private String title;

    private boolean completed = false;

    private Date createdAt = new Date();

}