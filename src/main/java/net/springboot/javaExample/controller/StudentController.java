package net.springboot.javaExample.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import net.springboot.javaExample.entity.Student;
import net.springboot.javaExample.repository.StudentRepository;

@Controller
@RequestMapping("/students/")
public class StudentController {
	
	@Autowired
	private StudentRepository studentRepository;
	
	@GetMapping(value = {"showForm","signup"})
	private String showStudentForm(Model model) {
		Student student = new Student();
		model.addAttribute("student", student);
		return "add-student";
	}
	
	@GetMapping("list")
	public String students(Model model) {
		model.addAttribute("students", this.studentRepository.findAll());
		System.out.println(this.studentRepository.findAll());
		return "index";
	}
	
	@PostMapping("add")
	public String addStudent(@Valid Student student, BindingResult result, Model model) {
		if(result.hasErrors()) {
			return "add-student";
		}
		this.studentRepository.save(student);
		return "redirect:list";
	}
	
	@PostMapping("update/{id}")
	public String updateStudent(@PathVariable("id") long id, Student student, BindingResult result, Model model) {
		if(result.hasErrors()) {
			student.setId(id);
			return "update-student";
		}
		studentRepository.save(student);
		model.addAttribute("students", this.studentRepository.findAll());
		return "index";
	}
	
	@GetMapping("edit/{id}")
	public String showUpdateForm(@PathVariable("id") long id, Model model) {
		Student student = this.studentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Student id: " + id));
		model.addAttribute("student", student);
		return "update-student";
	}
	
	@DeleteMapping("delete/{id}")
	public String deleteStudent(@PathVariable("id") long id, Model model) {
		Student student = this.studentRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid Student id: " + id));
		this.studentRepository.delete(student);
		
		model.addAttribute("students", this.studentRepository.findAll());
		return "index";
	}
}
