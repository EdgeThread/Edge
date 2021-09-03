<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
<%--    <script src="js/vue.js"></script>--%>
<%--    <script src ="js/axios.js"></script>--%>
    <script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
    <script src="https://unpkg.com/axios/dist/axios.min.js"></script>
</head>
<body>
<div id =  "app">
    <table>
        <tr>
            <th>学号</th>
            <th>姓名</th>
        </tr>
        <tr v-for="stu in students" >
            <td>{{stu.sid}}</td>
            <td>{{stu.sname}}</td>
        </tr>
    </table>

</div>

        <script>
            new Vue({
                el:"table",
                data:{
                    students:""
                },
                created(){
                    axios({
                        method:"get",
                        url:"http://localhost:8080/day19_web_war_exploded/StuServlet"
                    }).then(obj => {this.students = obj.data;});
                }
            });


        </script>
</body>
</html>