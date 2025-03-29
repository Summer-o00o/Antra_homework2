package project3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project3.exceptions.DataExistException;
import project3.exceptions.NotFoundException;
import project3.models.entities.Student;
import project3.models.entities.StudentTeacherLink;
import project3.models.entities.Teacher;
import project3.models.pojo.StudentTeacherLinkPojo;
import project3.repository.StudentRepository;
import project3.repository.StudentTeacherLinkRepository;
import project3.repository.TeacherRepository;

import java.util.Optional;

@Service
public class StudentTeacherLinkService {
    private final StudentTeacherLinkRepository studentTeacherLinkRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    //if have teacher repository, also will be a field

    @Autowired
    public StudentTeacherLinkService(StudentTeacherLinkRepository studentTeacherLinkRepository,
                                     StudentRepository studentRepository,
                                     TeacherRepository teacherRepository){
        this.studentTeacherLinkRepository = studentTeacherLinkRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
    }

//    public List<TeacherPojo> getTeachersOfStudent(Long id){
//        Optional<Student> studentOptional = studentRepository.findById(id);
//        if(studentOptional.isEmpty()){
//            throw new NotFoundException("student does not exist");
//        }
//
//        Student student = studentOptional.get();
//        List<TeacherPojo> teacherPojos = new ArrayList<>();
//        for(StudentTeacherLink studentTeacherLink : student.getStudentTeacherLinks()){
//            Teacher teacher = studentTeacherLink.getTeacher();
//            teacherPojos.add(convertEntityToPojo(teacher));
//        }
//        return teacherPojos;
//    }

    //GetMapping method
    public StudentTeacherLinkPojo getStudentTeacherLink(Long studentId, Long teacherId){
        //check if student and teacher exist
        if(!ifStudentExist(studentId)){
            throw new NotFoundException("Student does not exist!");
        }
        if(!ifTeacherExist(teacherId)){
            throw new NotFoundException("Teacher does not exist!");
        }

        //check if the link exist
        StudentTeacherLink studentTeacherLinkCheck = ifStudentTeacherLinkExist(studentId, teacherId);
        if(studentTeacherLinkCheck == null){
            throw new NotFoundException("Student Teacher Link does not exist!");
        }

        StudentTeacherLinkPojo studentTeacherLinkPojo = convertStudentTeacherLinkEntityToPojo(studentTeacherLinkCheck);
        return studentTeacherLinkPojo;
    }

    public StudentTeacherLinkPojo getStudentTeacherLinkById(Long studentTeacherLinkId){
        Optional<StudentTeacherLink> studentTeacherLinkOptional = studentTeacherLinkRepository.findById(studentTeacherLinkId);
        if(studentTeacherLinkOptional.isEmpty()){
            throw new NotFoundException("Student Teacher Link does not exist!");
        }

        StudentTeacherLinkPojo studentTeacherLinkPojo = convertStudentTeacherLinkEntityToPojo(studentTeacherLinkOptional.get());
        return studentTeacherLinkPojo;
    }

    //PostMapping Method
    public StudentTeacherLinkPojo createStudentTeacherLink(StudentTeacherLinkPojo studentTeacherLinkPojo){
        //check if student and teacher exist
        if(!ifStudentExist(studentTeacherLinkPojo.getStudentId())){
            throw new NotFoundException("Student does not exist!");
        }

        if(!ifTeacherExist(studentTeacherLinkPojo.getTeacherId())){
            throw new NotFoundException("Teacher does not exist!");
        }

        //check if record exist already
        StudentTeacherLink studentTeacherLinkCheck = ifStudentTeacherLinkExist(studentTeacherLinkPojo.getStudentId(),
                                                                                studentTeacherLinkPojo.getTeacherId());
        if(studentTeacherLinkCheck != null){
            throw new DataExistException("Student Teacher Link already exist!");
        }

        //create new record
        StudentTeacherLink studentTeacherLink = buildStudentTeacherLink(studentTeacherLinkPojo);
        return convertStudentTeacherLinkEntityToPojo(studentTeacherLinkRepository.save(studentTeacherLink));
    }

//    //PutMapping Method
//    public StudentTeacherLinkPojo updateStudentTeacherLink(StudentTeacherLinkPojo studentTeacherLinkPojo,
//                                                           Long studentTeacherLinkId){
//        //check if the link id exist, if not, create a new record
//        Optional<StudentTeacherLink> studentTeacherLinkOptional =
//                studentTeacherLinkRepository.findById(studentTeacherLinkId);
//        if(studentTeacherLinkOptional.isEmpty()){
//            throw new NotFoundException("Record does not exist");
//        }else{
//            studentTeacherLinkPojo.setStudentTeacherLinkPojoId(studentTeacherLinkId);
//            //check if student and teacher exist
//            if(!ifStudentExist(studentTeacherLinkPojo.getStudentId())){
//                throw new NotFoundException("Student does not exist!");
//            }
//            if(!ifTeacherExist(studentTeacherLinkPojo.getTeacherId())){
//                throw new NotFoundException("Teacher does not exist!");
//            }
//            //check if the record exist already
//            StudentTeacherLink studentTeacherLinkCheck = ifStudentTeacherLinkExist(studentTeacherLinkPojo.getStudentId(),
//                    studentTeacherLinkPojo.getTeacherId());
//            if(studentTeacherLinkCheck != null){
//                throw new DataExistException("Student Teacher Link already exist!");
//            }
//
//            //update record
//            StudentTeacherLink studentTeacherLink = buildStudentTeacherLink(studentTeacherLinkPojo);
//            return convertStudentTeacherLinkEntityToPojo(studentTeacherLinkRepository.save(studentTeacherLink));
//        }
//    }
//
//    //DeleteMapping Method
//    public String deleteStudentTeacherLink(Long studentTeacherLinkId){
//        Optional<StudentTeacherLink> studentTeacherLinkOptional = studentTeacherLinkRepository.findById(studentTeacherLinkId);
//        if(studentTeacherLinkOptional.isEmpty()){
//            throw new NotFoundException("Record does not exist");
//        }
//        studentTeacherLinkRepository.delete(studentTeacherLinkOptional.get());
//        return "Successfully Deleted";
//    }

    private StudentTeacherLinkPojo convertStudentTeacherLinkEntityToPojo(StudentTeacherLink studentTeacherLink){
        StudentTeacherLinkPojo studentTeacherLinkPojo = new StudentTeacherLinkPojo();
        studentTeacherLinkPojo.setStudentId(studentTeacherLink.getStudent().getStudentId());
        studentTeacherLinkPojo.setTeacherId(studentTeacherLink.getTeacher().getTeacherId());
        studentTeacherLinkPojo.setStudentTeacherLinkPojoId(studentTeacherLink.getStudentTeacherLinkId());
        return studentTeacherLinkPojo;
    }

    private boolean ifStudentExist(Long studentId){
        Optional<Student> student = studentRepository.findById(studentId);
        if(student.isEmpty()){
            return false;
        }
        return true;
    }
    private boolean ifTeacherExist(Long teacherId){
        Optional<Teacher> teacher = teacherRepository.findById(teacherId);
        if(teacher.isEmpty()){
            return false;
        }
        return true;
    }

    private StudentTeacherLink ifStudentTeacherLinkExist(Long student_id, Long teacher_id){
        Optional<StudentTeacherLink> studentTeacherLink =
                studentTeacherLinkRepository.findStudentTeacherLink(student_id,teacher_id);
        if(studentTeacherLink.isEmpty()){
            return null;
        }
        return studentTeacherLink.get();
    }

    private StudentTeacherLink buildStudentTeacherLink(StudentTeacherLinkPojo studentTeacherLinkPojo){
        Student student = studentRepository.findById(studentTeacherLinkPojo.getStudentId()).get();
        Teacher teacher = teacherRepository.findById(studentTeacherLinkPojo.getTeacherId()).get();
        StudentTeacherLink studentTeacherLink = new StudentTeacherLink();
        studentTeacherLink.setStudentTeacherLinkId(studentTeacherLinkPojo.getStudentTeacherLinkPojoId());
        studentTeacherLink.setTeacher(teacher);
        studentTeacherLink.setStudent(student);
        return studentTeacherLink;
    }
}
