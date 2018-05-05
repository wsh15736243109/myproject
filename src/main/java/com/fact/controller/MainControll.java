package com.fact.controller;

import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fact.domain.User;
import com.fact.service.UserService;

@Controller
@RequestMapping("/hello")
public class MainControll {
	
	@Autowired
	UserService userService;
	
	@RequestMapping("/mvc")
	public String helloSpringMvc(){
		return "main";
	}

	@RequestMapping("/html")
	public String helloHtml(){
		return "FactFace";
	}
	
	@RequestMapping("/login")
	public String loginCheck(HttpServletRequest request, User user){
		boolean isValidUser =userService.hasMatcherUser(user.getUserName(), user.getPassword());
		if(isValidUser){
			User user1 = userService.findUserByUserName(user.getUserName());
            user.setLastIp(request.getLocalAddr());
            user.setLastVisit(new Date());
            user.setUserId(user1.getUserId());
            userService.loginSuccess(user);
            request.getSession().setAttribute("user", user1);
            return ("loginSuccess");
		}else{
			return ("no_user");
		}
	}
	
	@RequestMapping("/getJson")
	public @ResponseBody ArrayList<User> getJson(){
		return userService.getAllUser();
	}
}
