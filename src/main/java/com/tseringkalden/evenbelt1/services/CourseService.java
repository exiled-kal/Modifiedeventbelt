package com.tseringkalden.evenbelt1.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tseringkalden.evenbelt1.models.Course;
import com.tseringkalden.evenbelt1.repositories.CourseRepository;

@Service
public class CourseService {
	private final CourseRepository courseRepo;

	public CourseService(CourseRepository courseRepo) {
		this.courseRepo = courseRepo;
	}

//	find all

	public List<Course> findAllCourse() {
		return courseRepo.findAll();
	}

//	find by id

	public Course findCourseById(Long id) {
		Optional<Course> myCourse = courseRepo.findById(id);
		if (myCourse.isPresent()) {
			return myCourse.get();
		} else {
			return null;
		}
	}

	// create course
	public Course createCourse(Course myCourse) {
		return courseRepo.save(myCourse);
	}

	// update cours
	public void updateCourse(Course myCourse) {
		courseRepo.save(myCourse);
	}
	public Course findCourse(Long id) {
		Optional<Course> optionalCourse = courseRepo.findById(id);
		if(optionalCourse.isPresent()) {
			return optionalCourse.get();
		} else {
			return null;
		}
	}
	// delete course
	public void deleteCourse(Long id) {
		courseRepo.deleteById(id);
	}
}
