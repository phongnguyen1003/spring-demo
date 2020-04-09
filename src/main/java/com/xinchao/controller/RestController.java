package com.xinchao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import com.xinchao.mapper.MyBatisRepository;
import com.xinchao.model.ResponseData;
import com.xinchao.model.UserEntity;

@org.springframework.web.bind.annotation.RestController
@RestControllerAdvice
public class RestController {
	@Autowired
	private MyBatisRepository mybatisRepository;

	@PostMapping(value = "changeUserInfo")
	public ResponseData changeData(UserEntity user) {
		ResponseData res = new ResponseData();
		res.setResultStatus("Successful");
		res.setUser(user);
		return res;
	}

	@RequestMapping("/register")
	public ModelAndView register() {
		ModelAndView mav = new ModelAndView("register");
		List<UserEntity> user =mybatisRepository.findAll();
		mav.addObject("userlist", user);
		return mav;
	}

//	@RequestMapping(value = "/register", method = RequestMethod.POST)
//	public ModelAndView register(UserEntity user,Model model) {
//		ModelAndView mav = new ModelAndView("redirect:/user");
//		UserEntity checkuser =  mybatisRepository.findbyUserName(user.getUsername());
//		if (checkuser!=null) {
//			model.addAttribute("userNameError", "User Name is used");
//			return new ModelAndView("register");
//		}else {
//		mybatisRepository.insert(user);
//		return mav;}
//	}
	@PostMapping(value = "/registerAjax")
	@ResponseBody
	public ResponseData registerAjax(UserEntity user) {
		ResponseData res=new ResponseData();
		UserEntity checkuser =mybatisRepository.findbyUserName(user.getUsername());
		if (checkuser!=null) {
			res.setResultStatus("UserNaem is used");
		} else {
			res.setResultStatus("Register succecss");
			mybatisRepository.insert(user);
			
		}
		return res;
		
	}
	
	//login
	@RequestMapping(value = { "/login" }, method = RequestMethod.POST)
	public ModelAndView login(UserEntity loginForm, Model model) {
		UserEntity user = mybatisRepository.findbyUserName(loginForm.getUsername());
		if (user == null) {
			model.addAttribute("loginError", "Nhập sai tên hoặc pass");
			return new ModelAndView("login");
		} else if (!user.getPassword().equals(loginForm.getPassword())) {
			model.addAttribute("loginError", "Nhập sai tên hoặc pass");
			return new ModelAndView("login");
		}
		ModelAndView modelView = new ModelAndView("redirect:/user");
		modelView.addObject("user", loginForm);
		return modelView;

	}
	//user
	@RequestMapping("/user")
	public ModelAndView user(UserEntity user) {
		ModelAndView mav = new ModelAndView("user");
		mav.addObject("user", user);
		List<UserEntity> listUser = mybatisRepository.findAll();
		mav.addObject("userlist", listUser);
		return mav;
	}
	
	//ADD USER
	@RequestMapping(value = "user/adduser", method = RequestMethod.POST)
	public ModelAndView adduser(UserEntity user) {
		ModelAndView mav = new ModelAndView("redirect:/user");
		mybatisRepository.insert(user);
		return mav;
	}
	//EDIT USER
	@RequestMapping(value = "user/edituser/{id}", method = RequestMethod.POST)
	public ModelAndView edtituser(UserEntity user,@PathVariable("id") Long id) {
		ModelAndView mav = new ModelAndView("redirect:/user");
		user.setId(id);
		mybatisRepository.update(user);
		return mav;
	}
	@RequestMapping(value = "user/deleteUser/{id}", method = RequestMethod.POST)
	public ModelAndView deleteUser(@PathVariable("id") Long id) {
		ModelAndView mav = new ModelAndView("redirect:/user");
		mybatisRepository.deleteByID(id);
		return mav;
	}
}
