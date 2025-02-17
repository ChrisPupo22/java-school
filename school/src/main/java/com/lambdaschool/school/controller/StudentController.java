package com.lambdaschool.school.controller;

import com.lambdaschool.school.model.Student;
import com.lambdaschool.school.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController
{
    @Autowired
    private StudentService studentService;
    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    // Please note there is no way to add students to course yet!

    @GetMapping(value = "/students", produces = {"application/json"})
    public ResponseEntity<?> listAllStudents(HttpServletRequest request)
    {
        List<Student> myStudents = studentService.findAll();
        logger.info(request.getMethod() + " " + request.getRequestURI() + " accessed");
        return new ResponseEntity<>(myStudents, HttpStatus.OK);

    }


    @GetMapping(value = "/Student/{StudentId}",
                produces = {"application/json"})
    public ResponseEntity<?> getStudentById(HttpServletRequest request,
            @PathVariable
                    Long StudentId)
    {
        logger.info(request.getMethod() + " " + request.getRequestURI() + " accessed");

        Student r = studentService.findStudentById(StudentId);

        return new ResponseEntity<>(r, HttpStatus.OK);

    }


    @GetMapping(value = "/student/namelike/{name}",
                produces = {"application/json"})
    public ResponseEntity<?> getStudentByNameContaining(HttpServletRequest request,
            @PathVariable String name)
    {
        List<Student> myStudents = studentService.findStudentByNameLike(name);
        logger.info(request.getMethod() + " " + request.getRequestURI() + " accessed");
        return new ResponseEntity<>(myStudents, HttpStatus.OK);
    }


    @PostMapping(value = "/Student",
                 consumes = {"application/json"},
                 produces = {"application/json"})
    public ResponseEntity<?> addNewStudent(HttpServletRequest request,
                                            @Valid
                                           @RequestBody
                                                   Student newStudent) throws URISyntaxException
    {
        newStudent = studentService.save(newStudent);

        // set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newStudentURI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{Studentid}").buildAndExpand(newStudent.getStudid()).toUri();
        responseHeaders.setLocation(newStudentURI);
        logger.info(request.getMethod() + " " + request.getRequestURI() + " accessed");

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }


    @PutMapping(value = "/Student/{Studentid}")
    public ResponseEntity<?> updateStudent(HttpServletRequest request,
            @RequestBody
                    Student updateStudent,
            @PathVariable
                    long Studentid)
    {
        studentService.update(updateStudent, Studentid);
        logger.info(request.getMethod() + " " + request.getRequestURI() + " accessed");
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @DeleteMapping("/Student/{Studentid}")
    public ResponseEntity<?> deleteStudentById(HttpServletRequest request,
            @PathVariable
                    long Studentid)
    {
        studentService.delete(Studentid);
        logger.info(request.getMethod() + " " + request.getRequestURI() + " accessed");
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
