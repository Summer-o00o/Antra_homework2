package project3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project3.models.entities.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
    //Optional<List<Student>> findByLastName(String lastName);
}
