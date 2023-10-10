package com.thesisreading;

import com.thesisreading.mapper.TeacherMapper;
import com.thesisreading.model.Teacher;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;


@SpringBootTest
public class MapperTest {

    @Resource
    TeacherMapper teacherMapper;

    @Test
    void teacherSelectPageMapperTest(){
        List<Teacher> teacherList = teacherMapper.selectPage(2,2);
        teacherList.forEach(System.out::println);
    }

}
