package com.example.library.Service;

import com.example.library.Model.Student;
import com.example.library.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents() {
        logger.info("Fetching all students");
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentById(Long id) {
        logger.info("Fetching student with id: {}", id);
        return studentRepository.findById(id);
    }

    public Student createStudent(Student student) {
        logger.info("Creating new student: {}", student);
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        logger.info("Deleting student with id: {}", id);
        studentRepository.deleteById(id);
    }

    public Student updateStudent(Long id, Student studentDetails) {
        logger.info("Updating student with id: {}", id);
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));

        student.setName(studentDetails.getName());
        student.setEmail(studentDetails.getEmail());
        // Update other fields as needed

        return studentRepository.save(student);
    }
}