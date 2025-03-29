package project3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project3.models.entities.StudentTeacherLink;

import java.util.Optional;

public interface StudentTeacherLinkRepository extends JpaRepository<StudentTeacherLink, Long>{
    @Query(value = "SELECT * FROM student_teacher_link WHERE student_id = :studentId AND teacher_id = :teacherId", nativeQuery = true)
    Optional<StudentTeacherLink> findStudentTeacherLink(@Param("studentId") Long studentId, @Param("teacherId") Long teacherId);
}
