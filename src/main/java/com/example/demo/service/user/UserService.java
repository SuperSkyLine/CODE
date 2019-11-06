package com.example.demo.service.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.user.UserMapper;
import com.example.demo.model.user.User;

@Service
public class UserService implements UserMapper{
	@Autowired
	UserMapper userMapper;
	@Override
	public List<User> getUserInfo() {
		return userMapper.getUserInfo();
	}

}
