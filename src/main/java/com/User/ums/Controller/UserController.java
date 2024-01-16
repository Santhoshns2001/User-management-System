package com.User.ums.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.User.ums.Entity.User;
import com.User.ums.Service.UserService;
import com.User.ums.Utility.ResponseStructure;
import com.User.ums.requestdto.UserRequest;
import com.User.ums.responsedto.UserResponse;

import jakarta.validation.Valid;

@RestController
public class UserController {
	@Autowired
	private UserService userService;

	@PostMapping("/user")
	public ResponseEntity<ResponseStructure<UserResponse>> saveUser(@RequestBody @Valid UserRequest userRequest) {
		return userService.saveUser(userRequest);
	}
	@PutMapping("/user/{userId}")
	public  ResponseEntity<ResponseStructure<UserResponse>> updateUser(@PathVariable int userId,@RequestBody User user){
		return userService.updateUser(user,userId);
	}
	@GetMapping("/user")
	public ResponseEntity<ResponseStructure<List<UserResponse>>> fetchAllUser() {
		return userService.fetchAll();
	}
	@GetMapping("/user/{userId}")
	public ResponseEntity<ResponseStructure<UserResponse>> fetchUserById(@PathVariable int userId) {
		return userService.fetchUserById(userId);
	}
	@DeleteMapping("/user/{userId}")
	public ResponseEntity<ResponseStructure<UserResponse>> deleteUser(@PathVariable int userId) {
		return userService.deleteUser(userId);
	}
}
