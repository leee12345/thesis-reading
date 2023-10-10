package com.thesisreading.controller.UserController;

import com.thesisreading.controller.UserController.bean.request.LoginRequest;
import com.thesisreading.controller.UserController.bean.request.StudentSignUpRequest;
import com.thesisreading.controller.UserController.bean.request.TeacherSignUpRequest;
import com.thesisreading.controller.bean.response.StatusResponse;
import com.thesisreading.model.Student;
import com.thesisreading.model.Teacher;
import com.thesisreading.model.User;
import com.thesisreading.service.StudentService;
import com.thesisreading.service.TeacherService;
import com.thesisreading.service.UserService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(value = "/usr", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
public class UserController {

    @Resource
    HttpServletRequest request;

    @Resource
    TeacherService teacherService;

    @Resource
    StudentService studentService;

    @Resource
    UserService userService;

    /**
     * 用户登录：验证用户身份
     * @param loginRequest
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public Map signin(@RequestBody LoginRequest loginRequest) throws Exception {

        Map<String, Object> respMap = new HashMap<>();

        //验证用户身份
        User user = userService.loginUserById(loginRequest.getUserid(), loginRequest.getPassword());
        System.out.println(user);

        //用户不存在
        if (user == null){
            respMap.put("statusResp",StatusResponse.error("-1","用户不存在或密码错误。"));
            System.out.println(respMap);
            return respMap;
        }

        //用户存在
        respMap.put("statusResp", StatusResponse.ok());
        respMap.put("role", user.getRole());
        respMap.put("Token", user.getUserId());  //TODO 将userid放到Token中
        return respMap;
    }

    /**
     * 退出登录：将userid移出session。
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public StatusResponse logout(){
        HttpSession session = request.getSession();
        session.removeAttribute("userid");
        return StatusResponse.ok();
    }

    /**
     * 教师用户注册
     */
    @RequestMapping(value = "/teacher/dosignup", method = RequestMethod.POST)
    public StatusResponse teaSignUp(@RequestBody TeacherSignUpRequest teacherSignUpRequest){
        System.out.println("到这里来了吗");
        System.out.println(teacherSignUpRequest);

        System.out.println(request.getHeader("Token"));
        String val = request.getHeader("Accept-Encoding");
        System.out.println(val);
        //判断用户名是否已经注册
        User user = userService.selectByPrimaryKey(teacherSignUpRequest.getUserid());
        if (user != null){
            return StatusResponse.error("-1", "user exists.");
        }

        //插入数据库（用户表）
        User newUser = User.builder().password(teacherSignUpRequest.getPassword())
                .role(teacherSignUpRequest.getRole())
                .userId(teacherSignUpRequest.getUserid()).build();
        userService.insert(newUser);

        //插入数据库（教师表）
        Teacher t = Teacher.builder().name(teacherSignUpRequest.getTeachername())
                .phone(teacherSignUpRequest.getPhone()).protitle(teacherSignUpRequest.getProtitle())
                .sex(teacherSignUpRequest.getSex()).teacherId(teacherSignUpRequest.getUserid()).build();
        teacherService.insert(t);

        return StatusResponse.ok();
    }

    /**
     * 学生用户注册
     * @param studentSignUpRequest
     */
    @RequestMapping(value = "/student/dosignup", method = RequestMethod.POST)
    public StatusResponse stuSignUp(@RequestBody StudentSignUpRequest studentSignUpRequest){
        //判断用户名是否已经注册
        User user = userService.selectByPrimaryKey(studentSignUpRequest.getUserid());
        if (user != null){
            return StatusResponse.error("-1", "user exists.");
        }

        //插入数据库（用户表）
        User newUser = User.builder().password(studentSignUpRequest.getPassword())
                .role(studentSignUpRequest.getRole())
                .userId(studentSignUpRequest.getUserid()).build();
        userService.insert(newUser);

        //插入数据库（学生表）
        Student s = Student.builder().className(studentSignUpRequest.getClassname())
                .major(studentSignUpRequest.getMajor()).name(studentSignUpRequest.getStudentname())
                .studentId(studentSignUpRequest.getUserid()).sex(studentSignUpRequest.getSex()).build();
        studentService.insert(s);

        return StatusResponse.ok();
    }

    // 以下暂没有要求

    /**
     * 更新教师用户个人信息
     */

    /**
     * 获取教师用户个人信息
     */

    /**
     * 更新学生用户个人信息
     */

    /**
     * 获取学生用户个人信息
     */

}

