package com.example.springSecurity.springSecurity.student;

import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("management/api/v1/student")
public class StudentManagementController {

    private static final List<Student> Students = Arrays.asList(new Student(1,"John"),
                                                                new Student(2,"Anna"),
                                                                new Student(3,"Maria"),
                                                                new Student(4,"Adams"));

    @GetMapping("/all")
    public List<Student> getAllStudents(){
        return Students;
    }


    @PostMapping
    public void createNewStudent(@RequestBody Student student){

        System.out.println("Post Added" + student);
    }


    @DeleteMapping(path = "{studentID}")
    public void deleteStudent(@PathVariable("studentID") Integer studentID)
    {
        System.out.println(studentID);
    }

    @PutMapping(path = "{studentID}")
    public void updateStudent(@PathVariable("studentID") Integer studentID, @RequestBody Student student){
        System.out.println(String.format("%s, %s",studentID,student));
    }



}
