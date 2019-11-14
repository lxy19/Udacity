package com.udacity.ToDoApp.Repository;

import com.udacity.ToDoApp.entity.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<ToDo, Long> {

}
