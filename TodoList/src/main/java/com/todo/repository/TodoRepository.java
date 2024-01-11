package com.todo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.todo.entities.Task;

public interface TodoRepository extends JpaRepository<Task, Integer> {
	Page<Task> findByCompleted(boolean completed , Pageable pageable);
	
	public Page<Task> findAll(Pageable pageable);
}
