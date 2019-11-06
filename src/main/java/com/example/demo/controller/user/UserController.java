package com.example.demo.controller.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.user.User;
import com.example.demo.service.user.UserService;

@RestController
public class UserController {
	@Autowired
	UserService userService;
	@RequestMapping("/getUserInfo")
	public List<User> getUserInfo() {
		return userService.getUserInfo();
	}
}
