package com.thesisreading.utils;

import com.thesisreading.model.Course;
import com.thesisreading.model.Tc;
import com.thesisreading.model.User;
import com.thesisreading.service.TcService;
import com.thesisreading.service.UserService;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

public class VerifyAuthorityUtils {

    // 验证权限
    // 0：管理员
    public static boolean verifyAdminRole(String loginId, UserService userService){
        User user = userService.selectByPrimaryKey(loginId);
        if (user != null){
            return user.getRole() == 0;
        }
        return false;
    }
    // 1：授课教师
    public static boolean verifyTeacherRole(String loginId, UserService userService){
        User user = userService.selectByPrimaryKey(loginId);
        if (user != null){
            return user.getRole() == 1;
        }
        return false;
    }
    // 2：学生
    public static boolean verifyStudentRole(String loginId, UserService userService){
        User user = userService.selectByPrimaryKey(loginId);
        if (user != null){
            return user.getRole() == 2;
        }
        return false;
    }

    // 3：主讲教师 + courseId
    public static boolean verifyMajorTeacher(String loginId, String courseId, TcService tcService)
    {
        Tc tc =  tcService.selectContainMajorTeacherByCourseId(courseId);
        if (tc!=null){
            return tc.getTeacherId().equals(loginId);
        }
        return false;
    }

    // 4：团队教师 + courseId
    public static boolean verifyTeamTeacher(String loginId, String courseId, TcService tcService) {
        List<Tc> list = tcService.selectContainTeamTeacherByCourseId(courseId);
        for (Tc tc: list){
            if (tc.getTeacherId().equals(loginId)){
                return true;
            }
        }
        return false;
    }

    //5: 主讲教师或者团队教师
    public static boolean verifyCourseTeacher(String loginId, String courseId, TcService tcService) {
        List<Tc> list = tcService.selectByCourseId(courseId);
        for (Tc tc: list){
            if (tc.getTeacherId().equals(loginId)){
                return true;
            }
        }
        return false;
    }

}
