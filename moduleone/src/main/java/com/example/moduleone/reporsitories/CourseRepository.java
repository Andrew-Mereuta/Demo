package com.example.moduleone.reporsitories;

import com.example.moduleone.models.Course;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends CrudRepository<Course, String> {
}
