package project3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project3.exceptions.DataExistException;
import project3.exceptions.MissInfoException;
import project3.exceptions.NotFoundException;
import project3.models.pojo.StudentTeacherLinkPojo;
import project3.service.StudentTeacherLinkService;

@RestController
@RequestMapping("/registration")
public class StudentTeacherLinkController {
    private final StudentTeacherLinkService studentTeacherLinkService;

    @Autowired
    public StudentTeacherLinkController(StudentTeacherLinkService studentTeacherLinkService){
        this.studentTeacherLinkService = studentTeacherLinkService;
    }

//    @GetMapping("/{id}/teacher")
//    public ResponseEntity<List<TeacherPojo>> getTeachersOfStudent(@PathVariable Long id){
//        List<TeacherPojo> teacherPojos = studentTeacherLinkService.getTeachersOfStudent(id);
//        return ResponseEntity.status(200).body(teacherPojos);
//    }


    @GetMapping("")
    public ResponseEntity<StudentTeacherLinkPojo> getStudentTeacherLink(@RequestParam Long studentId,
                                                                        @RequestParam Long teacherId){
        StudentTeacherLinkPojo studentTeacherLinkPojo = studentTeacherLinkService.getStudentTeacherLink(studentId, teacherId);
        return ResponseEntity.status(200).body(studentTeacherLinkPojo);
    }


    @GetMapping("/{studentTeacherLinkId}")
    public ResponseEntity<StudentTeacherLinkPojo> getStudentTeacherLinkById(@PathVariable Long studentTeacherLinkId){
        StudentTeacherLinkPojo studentTeacherLinkPojo = studentTeacherLinkService.getStudentTeacherLinkById(studentTeacherLinkId);
        return ResponseEntity.status(200).body(studentTeacherLinkPojo);
    }

    @PostMapping("")
    public ResponseEntity<StudentTeacherLinkPojo> createStudentTeacherLink(@RequestBody StudentTeacherLinkPojo studentTeacherLinkPojo){
        StudentTeacherLinkPojo newStudentTeacherLinkPojo = studentTeacherLinkService.createStudentTeacherLink(studentTeacherLinkPojo);
        return ResponseEntity.status(201).body(newStudentTeacherLinkPojo);
    }
//
//    @PutMapping("/{studentTeacherLinkId}")
//    public ResponseEntity<StudentTeacherLinkPojo> updateStudentTeacherLink(@RequestBody StudentTeacherLinkPojo studentTeacherLinkPojo,
//                                                                           @PathVariable Long studentTeacherLinkId){
//        StudentTeacherLinkPojo updatedStudentTeacherLinkPojo =
//                studentTeacherLinkService.updateStudentTeacherLink(studentTeacherLinkPojo, studentTeacherLinkId);
//        return ResponseEntity.status(200).body(updatedStudentTeacherLinkPojo);
//    }
//
//    @DeleteMapping("/{studentTeacherLinkId}")
//    public ResponseEntity<String> deleteStudentTeacherLinkById(@PathVariable Long studentTeacherLinkId){
//        String message = studentTeacherLinkService.deleteStudentTeacherLink(studentTeacherLinkId);
//        return ResponseEntity.status(200).body(message);
//    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException exception){
        return ResponseEntity.status(404).body(exception.getMessage());
    }
    @ExceptionHandler(MissInfoException.class)
    public ResponseEntity<String> handleMissInfoException(MissInfoException exception){
        return ResponseEntity.status(400).body(exception.getMessage());
    }
    @ExceptionHandler(DataExistException.class)
    public ResponseEntity<String> handleDataExistException(DataExistException exception){
        return ResponseEntity.status(400).body(exception.getMessage());
    }
}
