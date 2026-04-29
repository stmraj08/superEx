package com.client.superex.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.client.superex.entity.Course;


public interface CourseRepo extends JpaRepository<Course,Long>{}