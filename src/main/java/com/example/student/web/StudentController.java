package com.example.student.web;

import com.example.student.entities.Student;
import com.example.student.repositories.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

//@SessionAttributes({"info"})
@Controller
@AllArgsConstructor
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    static int num=0;

    @GetMapping(path="/index")
    public String students(Model model, @RequestParam(name="keyword",defaultValue = "") String keyword, HttpSession session) {




        List<Student> students;
        if (keyword.isEmpty()) {
            students = studentRepository.findAll();
        } else {

            long key = Long.parseLong(keyword);
            students = studentRepository.findStudentById(key);








        }
        model.addAttribute("listStudents", students);



        return "students";
    }


    @GetMapping("/delete")
     public String delete(Long id, HttpSession session){
        studentRepository.deleteById(id);

        session.setAttribute("info", 0);

        return "redirect:/index";
     }

     @GetMapping("/formStudents")
     public String formStudents(Model model){

        num=1;
        model.addAttribute("student", new Student());

        return "formStudents";
     }


    @GetMapping("/editStudents")
    public String editStudents(Model model, Long id, HttpSession session){

        num = 2;

        session.setAttribute("info", 0);

        Student student = studentRepository.findById(id).orElse(null);
        if(student==null) throw new RuntimeException("Patient does not exist");
        model.addAttribute("student", student);

        return "editStudents";
    }

     @PostMapping(path="/save")
     public String save(Model model, Student student, BindingResult bindingResult, ModelMap mm, HttpSession session){
        if(bindingResult.hasErrors()){
            return "formStudents";
        }  else {

            studentRepository.save(student);
            //mm.put("info","1");

            if (num==2){

                session.setAttribute("info", 2);
            } else {
                session.setAttribute("info", 1);
            }


            return "redirect:index";
        }



        //return "redirect:index";

     }






}
