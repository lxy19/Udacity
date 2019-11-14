package com.udacity.ToDoApp.controller;

import com.udacity.ToDoApp.Repository.TodoRepository;
import com.udacity.ToDoApp.entity.ToDo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/todo/api")
public class ToDoController {

    @Autowired
    private TodoRepository todoRepository;

    @RequestMapping(value="", method = RequestMethod.POST)
    public ResponseEntity<ToDo> create(@RequestBody ToDo todo){
        ToDo savedTodo = todoRepository.save(todo);
        return ResponseEntity.ok(savedTodo);
    }


    @RequestMapping(value="", method = RequestMethod.GET)
    public ResponseEntity<List<?>> get(){
        return ResponseEntity.ok(todoRepository.findAll());
    }

    @RequestMapping(value="{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable Long id){
        Optional<ToDo> todoOpt = todoRepository.findById(id);
        if (todoOpt.isPresent()) {
            return ResponseEntity.ok(todoRepository.findById(id));
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value="{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateById(@PathVariable Long id, @RequestBody ToDo todo){
        Optional<ToDo> todoOpt = todoRepository.findById(id);
        if (todoOpt.isPresent()) {
            ToDo savedTodo = todoOpt.get();
            BeanUtils.copyProperties(todo,savedTodo,"id");
            savedTodo = todoRepository.save(savedTodo);
            return ResponseEntity.ok(savedTodo);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value="{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteTodo(@PathVariable Long id){
        Optional<ToDo> toDoOptional = todoRepository.findById(id);
        if (toDoOptional.isPresent()) {
            ToDo deletedTodo = toDoOptional.get();
            todoRepository.delete(deletedTodo);
            return ResponseEntity.ok(deletedTodo);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }


}
