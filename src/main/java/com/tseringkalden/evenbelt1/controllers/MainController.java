package com.tseringkalden.evenbelt1.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tseringkalden.evenbelt1.models.Course;
import com.tseringkalden.evenbelt1.models.User;
import com.tseringkalden.evenbelt1.services.CourseService;
import com.tseringkalden.evenbelt1.services.UserService;

@Controller
public class MainController {

	private final UserService userService;

	private final CourseService cS;

	public MainController(UserService userService, CourseService cS) {
		this.userService = userService;
		this.cS = cS;
	}

	@RequestMapping("/")
	public String index(@ModelAttribute("user") User user) {
		return "index.jsp";
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result, HttpSession session) {

		if (result.hasErrors()) {
			return "index.jsp";
		}
		User u = userService.registerUser(user);
		session.setAttribute("userId", u.getId());
		return "redirect:/courses";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginUser(@RequestParam("email") String email, @RequestParam("password") String password, Model model,
			HttpSession session) {
		boolean isAuthenticated = userService.authenticateUser(email, password);
		if (isAuthenticated) {
			User u = userService.findByEmail(email);
			session.setAttribute("userId", u.getId());
			return "redirect:/courses";
		} else {
			model.addAttribute("user", new User());
			model.addAttribute("error", "Invalid Credentials. Please try again.");
			return "index.jsp";
		}
	}

	@RequestMapping("/courses")
	public String homepage(HttpSession session, Model model) {

		// if current user is in session then proceed, if not redirect to login page
		if (session.getAttribute("userId") != null) {
			// get user from session, save them in the model and return the home page
			Long userId = (Long) session.getAttribute("userId");
			User u = userService.findUserById(userId);

			// model pass u user to jsp in order to display current user login name
			model.addAttribute("user", u);

			// render courses in table

			List<Course> courseList = cS.findAllCourse();
			model.addAttribute("courses", courseList);
			return "homePage.jsp";

		} else {
			return "redirect:/";
		}
	}


	// logout
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}

	// Render course creation page
	@RequestMapping("/courses/new")
	public String courseCreation(@ModelAttribute("course") Course myCourse) {
		return "course.jsp";
	}

	// Create Course
	@PostMapping("/courses/new")
	public String createCourse(@Valid @ModelAttribute("course") Course myCourse, BindingResult result) {
		if (result.hasErrors()) {
			return "course.jsp";
		} else {

			cS.createCourse(myCourse);
			return "redirect:/courses";
		}
	}

	// display course information
	@RequestMapping("courses/{id}")
	public String displayCourse(@PathVariable("id") Long id, Model model, HttpSession session) {
		Course myCourse = cS.findCourseById(id);
		model.addAttribute("course", myCourse);

		List<User> users = myCourse.getUsers();
		Long userId = (Long) session.getAttribute("userId");
		User u = userService.findUserById(userId);
		model.addAttribute("currentUser", u);

		return "courseinfo.jsp";
	}
	
//	--------------add user to the course--------------
	@RequestMapping("courses/add/{id}")
	public String addUser(@PathVariable("id") Long id, Model model, HttpSession session) {
		User user = userService.findUserById((Long) session.getAttribute("userId"));
		Course myCourse = cS.findCourseById(id);
		model.addAttribute("course", myCourse);
		List<User> users = myCourse.getUsers();
		users.add(user);
		myCourse.setUsers(users);
		userService.udpateUser(user);
		return "courseinfo.jsp";
	}
	@RequestMapping("courses/delete/{id}")
	public String deleteCourse(@PathVariable("id") Long id) {
		cS.deleteCourse(id);
		return "redirect:/courses";

	}

	@GetMapping("courses/{id}/edit")
	public String editPage(@PathVariable("id") Long id, Model model) {
		Course myCourse = cS.findCourseById(id);
		model.addAttribute("course", myCourse);
		return "edit.jsp";
	}

	@PutMapping("/courses/{id}")
		public String updateCourse(@Valid @ModelAttribute("course") Course myCourse, BindingResult result) {
			if(result.hasErrors()) {
				return "edit.jsp";
			} else {
				cS.updateCourse(myCourse);
				return "redirect:/courses";
			}
		}
}