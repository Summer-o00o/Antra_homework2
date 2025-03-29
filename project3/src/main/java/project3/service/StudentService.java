package project3.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project3.exceptions.MissInfoException;
import project3.exceptions.NotFoundException;
import project3.models.entities.Student;
import project3.models.pojo.StudentPojo;
import project3.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService  {
    private final StudentRepository studentRepository;
    //use LoggerFactory to do log
    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    @Autowired
    public StudentService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    //GetMapping method
    public StudentPojo getByID(Long id){
        logger.info("INFO: entering getByID method with id");
        //check if student exist
        Optional<Student> student = studentRepository.findById(id);
        if(student.isEmpty()){
            logger.error("Error: id not exist");
            throw new NotFoundException("student not found!");
        }

        StudentPojo studentPojo = convertEntityToPojo(student.get());
        return studentPojo;
    }

    public List<StudentPojo> getAll(){
        logger.info("INFO: entering getAll method");

        List<Student> students = studentRepository.findAll();
        List<StudentPojo> studentPojos = new ArrayList<>();
        for(Student student : students){
            StudentPojo studentPojo = convertEntityToPojo(student);
            studentPojos.add(studentPojo);
        }
        return studentPojos;
    }

//    public List<StudentPojo> findByLastName(String lastName){
//        logger.info("INFO: entering findByLastName method with lastName");
//        Optional<List<Student>> students = studentRepository.findByLastName(lastName);
//        if(students.isEmpty()){
//            logger.error("Error: lastName not exist");
//            throw new NotFoundException("no student found");
//        }
//        List<StudentPojo> studentPojos = new ArrayList<>();
//        for(Student s : students.get()){
//            StudentPojo studentPojo = convertEntityToPojo(s);
//            studentPojos.add(studentPojo);
//        }
//        return studentPojos;
//    }


    //PostMapping method
    public StudentPojo createStudent(StudentPojo studentPojo){
        logger.info("INFO: entering createStudent method with studentPojo");
        Student student = convertPojoToEntity(studentPojo);
        student = studentRepository.save(student);
        StudentPojo newStudentPojo = convertEntityToPojo(student);
        return newStudentPojo;
    }

    //PutMapping method
    public StudentPojo updateStudent(StudentPojo studentPojo){
        logger.info("INFO: entering updateStudent method with studentPojo");
        if(studentPojo.getStudentId() == null){
            logger.error("Error: user didn't provide studentId");
            throw new MissInfoException("student id is missing");
        }else{
            Optional<Student> student = studentRepository.findById(studentPojo.getStudentId());
            if(!student.isPresent()){
                logger.error("Error: student id not exist");
                throw new NotFoundException(
                        String.format("student does not exist")
                );
            }
        }

        Student student = convertPojoToEntity(studentPojo);
        student = studentRepository.save(student);
        StudentPojo updatedStudentPojo = convertEntityToPojo(student);
        return updatedStudentPojo;
    }

    //DeleteMapping method
    public String deleteStudentByID(Long id){
        logger.info("INFO: entering deleteStudentByID method with studentId");
        Optional<Student> student = studentRepository.findById(id);
        if(!student.isPresent()){
            throw new NotFoundException("student not found");
        }

        //Student studentDeleted = student.get();
        studentRepository.deleteById(id);

        //StudentPojo studentPojo = convertEntityToPojo(studentDeleted);
        return "Successfully Deleted!";
    }


    private StudentPojo convertEntityToPojo(Student student){
        StudentPojo studentPojo = new StudentPojo();
        studentPojo.setStudentId(student.getStudentId());
        studentPojo.setFirstName(student.getFirstName());
        studentPojo.setLastName(student.getLastName());

        return studentPojo;
    }

    private Student convertPojoToEntity(StudentPojo studentPojo){
        Student student = new Student();
        student.setStudentId(studentPojo.getStudentId());
        student.setFirstName(studentPojo.getFirstName());
        student.setLastName(studentPojo.getLastName());
        return student;
    }
}
