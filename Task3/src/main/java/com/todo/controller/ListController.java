package com.todo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.todo.entities.Task;
import com.todo.repository.TodoRepository;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ListController {

	@Autowired
	private TodoRepository todoRepository;

	@GetMapping("/addList")
	public String CreateList(Model model) {
		model.addAttribute("title", "Add List");
		return "addList";
	}

	@GetMapping("/AllTasks/{page}")
	public String AllList(@PathVariable("page") Integer page, Model model) {
		Pageable pageable = PageRequest.of(page, 4 , Sort.by("createdAt").descending());

		Page<Task> allTask = this.todoRepository.findAll(pageable);

		model.addAttribute("allTasks", allTask);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", allTask.getTotalPages());
		return "AllTasks";
	}

	@PostMapping("/doAdd")
	public String processAddTask(@ModelAttribute("task") Task task, Model model) {

		Task result = this.todoRepository.save(task);
		System.out.println(result);

		return "redirect:/AllTasks/0";
	}

	@GetMapping("/CompletedTasks/{page}")
	public String CompletedList(@PathVariable("page") Integer page, Model model) {
		Pageable pageable = PageRequest.of(page, 4);
		Page<Task> completedTasks = this.todoRepository.findByCompleted(true, pageable);
		model.addAttribute("completedTasks", completedTasks);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", completedTasks.getTotalPages());
		return "CompletedTasks";
	}

	@PostMapping("/completeTask")
	public String completeTask(@ModelAttribute("taskId") int taskId, @RequestParam("page") int page) {
		Optional<Task> taskOptional = this.todoRepository.findById(taskId);
		if (taskOptional.isPresent()) {
			Task task = taskOptional.get();
			task.setCompleted(true);
			this.todoRepository.save(task);
			System.out.println(page);
		}
		return "redirect:/AllTasks/" + page;
	}

	@PostMapping("/completePending")
	public String completeTask1(@ModelAttribute("taskId") int taskId, @RequestParam("page") int page) {
		Optional<Task> taskOptional = this.todoRepository.findById(taskId);
		if (taskOptional.isPresent()) {
			Task task = taskOptional.get();
			task.setCompleted(true);
			this.todoRepository.save(task);
		}
		return "redirect:/PendingTasks/" + page;
	}

	@GetMapping("/PendingTasks/{page}")
	public String pendingList(@PathVariable("page") Integer page, Model model) {
		Pageable pageable = PageRequest.of(page, 4);
		Page<Task> pendingTasks = this.todoRepository.findByCompleted(false, pageable);
		model.addAttribute("pendingTasks", pendingTasks);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", pendingTasks.getTotalPages());
		return "PendingTasks";
	}

	@GetMapping("/deleteAll/{id}")
	public String deleteAllTask(@PathVariable("id") Integer id,
			@RequestParam(name = "currentPage", defaultValue = "1") Integer currentPage) {
		Optional<Task> taskOptional = this.todoRepository.findById(id);
		Task task = taskOptional.get();
		this.todoRepository.delete(task);
		return "redirect:/AllTasks/" + currentPage;
	}

	@GetMapping("/deleteCompleted/{id}")
	public String deleteCompletedTask(@PathVariable("id") Integer id,
			@RequestParam(name = "currentPage", defaultValue = "1") Integer currentPage) {
		Optional<Task> taskOptional = this.todoRepository.findById(id);
		Task task = taskOptional.get();
		this.todoRepository.delete(task);
		return "redirect:/CompletedTasks/" + currentPage;
	}

	@GetMapping("/deletePending/{id}")
	public String deletePendingTask(@PathVariable("id") Integer id,
			@RequestParam(name = "currentPage", defaultValue = "1") Integer currentPage) {
		Optional<Task> taskOptional = this.todoRepository.findById(id);
		Task task = taskOptional.get();
		this.todoRepository.delete(task);
		return "redirect:/PendingTasks/" + currentPage;
	}

	@RequestMapping("/updateTask/{id}")
	public String updateHandler(@PathVariable("id") Integer id, Model model, @RequestParam("source") String source,
			@RequestParam("page") Integer page) {
		model.addAttribute("source", source);
		Task task = this.todoRepository.findById(id).get();
		model.addAttribute("task", task);
		model.addAttribute("page", page);
		return "update";
	}

	@PostMapping("/doUpdateTask")
	public String doUpdateAllHandler(@ModelAttribute Task task, @RequestParam("id") Integer id,
			HttpServletRequest request) {

		String source = request.getParameter("source");
		String page = request.getParameter("page");

		System.out.println(source);
		task.setId(id);
		Task updatedTask = this.todoRepository.save(task);

		// Determine the redirect URL based on the source parameter
		String redirectUrl = "";
		if ("PendingTasks".equals(source)) {
			redirectUrl = "redirect:/PendingTasks/" + Integer.parseInt(page);
		} else {
			redirectUrl = "redirect:/AllTasks/" + Integer.parseInt(page);
		}

		System.out.println("Updated Task : ");
		System.out.println(updatedTask);

		return redirectUrl;
	}

}
