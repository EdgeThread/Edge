package com.ujiuye.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ujiuye.entity.Student;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "StuServlet", value = "/StuServlet")
public class StuServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        List<Student> list = new ArrayList<>();
        list.add(new Student(1,"张三"));
        list.add(new Student(2,"李四"));
        list.add(new Student(3,"王五"));
        ObjectMapper om = new ObjectMapper();
        String s = om.writeValueAsString(list);
        response.getWriter().print(s);
        System.out.println(s);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
