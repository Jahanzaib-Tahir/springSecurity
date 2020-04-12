package com.example.springSecurity.springSecurity.student;

import org.springframework.security.access.prepost.PreAuthorize;
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


    // hasRole(Role_), hasAnyRole(Role_), hasAuthority(permission), hasAnyAuthority(permission)

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_TRAINEE')")
    public List<Student> getAllStudents(){
        return Students;
    }


    @PostMapping
    @PreAuthorize("hasAuthority('student:write')")
    public void createNewStudent(@RequestBody Student student){
        System.out.println("Post Added" + student);
    }


    @DeleteMapping(path = "{studentID}")
    @PreAuthorize("hasAuthority('student:write')")
    public void deleteStudent(@PathVariable("studentID") Integer studentID)
    {
        System.out.println(studentID);
    }

    @PutMapping(path = "{studentID}")
    @PreAuthorize("hasAuthority('student:write')")
    public void updateStudent(@PathVariable("studentID") Integer studentID, @RequestBody Student student){
        System.out.println(String.format("%s, %s",studentID,student));
    }



}
