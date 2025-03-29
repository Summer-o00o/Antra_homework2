package project3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project3.models.entities.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}
