package com.ekrem.school_management_system.repository.businnes;

import com.ekrem.school_management_system.entity.concretes.businnes.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {

   Optional<Lesson>findByLessonNameEqualsIgnoreCase(String lessonName);


}
