package com.thesisreading;

import com.thesisreading.model.Tc;
import com.thesisreading.model.Teacher;
import com.thesisreading.service.TcService;
import com.thesisreading.service.TeacherService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;


@SpringBootTest
public class ServiceTest {

    @Resource
    TeacherService teacherService;

    @Resource
    TcService tcService;

    @Test
    void teacherSelectByPageTest(){
        /**
         * 第1页，显示两条
         */
        List<Teacher> teacherList = teacherService.selectByPage(1,2);
        teacherList.forEach(System.out::println);

        System.out.println("---------------------------------------------------------");
        /**
         * 第2页，显示两条
         */
        teacherList = teacherService.selectByPage(2,2);
        teacherList.forEach(System.out::println);
    }


    @Test
    void tcSelectByTeacherIdTest(){
        List<Tc> tcList = tcService.selectByTeacherId("10001000");
        tcList.forEach(System.out::println);
        System.out.println(tcList);
    }

}
