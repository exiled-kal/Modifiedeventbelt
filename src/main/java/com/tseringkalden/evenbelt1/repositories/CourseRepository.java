package com.tseringkalden.evenbelt1.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tseringkalden.evenbelt1.models.Course;

@Repository
public interface CourseRepository extends CrudRepository <Course, Long> {
	List<Course> findAll();

}
