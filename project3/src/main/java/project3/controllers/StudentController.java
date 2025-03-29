package project3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project3.exceptions.MissInfoException;
import project3.exceptions.NotFoundException;
import project3.models.pojo.StudentPojo;
import project3.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<StudentPojo> getStudentByID(@PathVariable Long id){
        StudentPojo studentPojo = studentService.getByID(id);
        return ResponseEntity.status(200).body(studentPojo);
    }


    @GetMapping("")
    public ResponseEntity<List<StudentPojo>> getAllStudents(){
        List<StudentPojo> list = studentService.getAll();
        return ResponseEntity.status(200).body(list);
    }


    @PostMapping("")
    public ResponseEntity<StudentPojo> createStudent(@RequestBody StudentPojo studentPojo){
        StudentPojo newStudentPojo = studentService.createStudent(studentPojo);
        return ResponseEntity.status(201).body(newStudentPojo);
    }


    @PutMapping("/{id}")
    public ResponseEntity<StudentPojo> updateStudent(@RequestBody StudentPojo studentPojo,
                                                     @PathVariable Long id){
        studentPojo.setStudentId(id);
        StudentPojo updatedStudentPojo = studentService.updateStudent(studentPojo);
        return ResponseEntity.status(200).body(updatedStudentPojo);
    }

    //method level role restriction
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudentByID(@PathVariable Long id){
        String s = studentService.deleteStudentByID(id);
        return ResponseEntity.status(200).body(s);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException exception){
        return ResponseEntity.status(404).body(exception.getMessage());
    }
    @ExceptionHandler(MissInfoException.class)
    public ResponseEntity<String> handleMissInfoException(MissInfoException exception){
        return ResponseEntity.status(400).body(exception.getMessage());
    }
}
