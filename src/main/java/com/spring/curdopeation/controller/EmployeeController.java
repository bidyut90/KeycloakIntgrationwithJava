package com.spring.curdopeation.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Supplier;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.spring.curdopeation.dao.EmployeeRepository;
import com.spring.curdopeation.excepation.ResourceNotFoundException;
import com.spring.curdopeation.model.Employee;

@RestController
@RequestMapping("/api/vi")
@CrossOrigin(origins = "*")
public class EmployeeController {
	@Autowired
	private EmployeeRepository employeeRepository;
	// get all employee;
	@RolesAllowed({"user" ,"amdin"})
	@GetMapping("/employees")
	public List<Employee> getAllEmployees(){
		return employeeRepository.findAll();
		
	}
	@RolesAllowed("admin")
	@PostMapping("/employees")
	public Employee createEmployee(@RequestBody Employee employee) {
		return employeeRepository.save(employee);
	}
	
 // create empolyee rest api
	@RolesAllowed("admin")
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable long id) {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("not exist "+id));
			return ResponseEntity.ok(employee);
	}
	//update employee rest api
	@RolesAllowed("admin")
	@PutMapping("/employees/{id}")
	
	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id,@RequestBody Employee employeeDetails){
		Employee employee = employeeRepository.findById(id)
				
				.orElseThrow(()-> new ResourceNotFoundException("not exist "+id));
		
		employee.setFirstName(employeeDetails.getFirstName());
		employee.setLastName(employeeDetails.getLastName());
		employee.setEmailId(employeeDetails.getEmailId());
		employee.setStatus(employeeDetails.isStatus());
		Employee updatedEmployee =employeeRepository.save(employee);
		return ResponseEntity.ok(updatedEmployee);
		
	}
	@RolesAllowed("admin")
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id){
		Employee employee =employeeRepository.findById(id)
				.orElseThrow((()-> new ResourceNotFoundException("data is not exist " +id)));
		employeeRepository.delete(employee);
		Map<String , Boolean> response =new HashMap<>();
		response.put("delete", Boolean.TRUE);
		return  ResponseEntity.ok(response);
	}
		
	}

