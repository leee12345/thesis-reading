package com.thesisreading.controller.StudentController;


import com.thesisreading.controller.AdminController.bean.response.CourseMajorTeaResponse;
import com.thesisreading.controller.StudentController.bean.response.PaperInfo;
import com.thesisreading.controller.StudentController.bean.response.SelectTopicResponse;
import com.thesisreading.controller.StudentController.bean.response.TopicInfoResponse;
import com.thesisreading.controller.StudentController.bean.response.TopicStudent;
import com.thesisreading.controller.StudentController.bean.request.MessageRequest;
import com.thesisreading.controller.bean.response.StatusResponse;
import com.thesisreading.model.*;
import com.thesisreading.service.*;
import com.thesisreading.utils.RequestUtils;
import com.thesisreading.utils.VerifyAuthorityUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/student", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
public class StudentController {

    @Resource
    TcService tcService;

    @Resource
    ScService scService;

    @Resource
    CourseService courseService;

    @Resource
    HttpServletRequest request;

    @Resource
    TeacherService teacherService;

    @Resource
    UserService userService;

    @Resource
    TopicService topicService;

    @Resource
    StutopicService stutopicService;

    @Resource
    StudentService studentService;

    @Resource
    TopicpaperService topicpaperService;

    @Resource
    PaperService paperService;

    @Resource
    MessageService messageService;


    /**
     * 显示该学生所学课程列表
     * 包括(课程ID,课程名，开设学期，总课时数、主讲教师姓名)等信息
     * 没有分页
     */
    // + 学生权限 + 接口测试
    @RequestMapping(value = "/course/list", method = RequestMethod.GET)
    public Map getCourseListByStuId() {
        Map<String, Object> respMap = new HashMap<>();

        //验证学生权限
        StatusResponse statusResponse = verifyStudent();
        respMap.put("statusResp", statusResponse);
        if (statusResponse.getCode().equals("-2")) {
            return respMap;
        }
        String studentId = RequestUtils.getLoginId(request);

        List<Sc> scList = scService.selectByStudentId(studentId);

        List<CourseMajorTeaResponse> cmtList = new ArrayList<>();


        for (Sc sc : scList) {
            String courseId = sc.getCourseId();
            Course c = courseService.selectByPrimaryKey(courseId);

            //对于一个课程，找到对应的主讲教师 的目录
            Tc tc = tcService.selectContainMajorTeacherByCourseId(courseId);

            CourseMajorTeaResponse cmt = CourseMajorTeaResponse.builder().courseHour(c.getCourseHour())
                    .courseId(c.getCourseId()).courseName(c.getCourseName()).term(c.getTerm())
                    .mteacherId(tc != null ? tc.getTeacherId() : "")
                    .mteacherName(tc != null ? teacherService.selectByPrimaryKey(tc.getTeacherId()).getName() : "")
                    .build();
            cmtList.add(cmt);
        }
        respMap.put("courseList", cmtList);
        return respMap;
    }

    /**
     * 课程主题查看和选题页面，
     * 可以查看该课程下的所有主题列表。
     * 每个主题带有一个“我要读”的按钮，
     * 如果该按钮可点，表示该主题选题人数未满（不超过5人），
     * 当前学生可点按此按钮将该选题选为自己名下。
     * 若该主题选题人数已满5人，则该主题不可点选。
     */

    /**
     * 查看课程主题列表
     *
     * @param courseId
     * @return
     */
    // + 学生权限 + 接口测试
    @RequestMapping(value = "/course/topic/list", method = RequestMethod.GET)
    public Map getTopicListByCourseId(@RequestParam(value = "courseId") String courseId) {
        Map<String, Object> respMap = new HashMap<>();

        //验证学生权限
        StatusResponse statusResponse = verifyStudent();
        respMap.put("statusResp", statusResponse);
        if (statusResponse.getCode().equals("-2")) {
            return respMap;
        }

        int page = RequestUtils.getPage(request);
        int pageSize = RequestUtils.getPageSize(request);

        List<Topic> topicList = topicService.selectByCIdByPage(courseId, page, pageSize);

        List<TopicInfoResponse> topicInfoResponseList = new ArrayList<>();



        for (Topic topic : topicList) {

            TopicInfoResponse topicInfoResponse = TopicInfoResponse.builder().topicId(topic.getTopicId())
                    .topic(topic.getTopic()).courseId(topic.getCourseId())
                    .courseName(courseService.selectByPrimaryKey(topic.getCourseId()).getCourseName())
                    .creationTime(topic.getCreationTime()).keyword(topic.getKeyword())
                    .studentNum(topic.getStudentNum()).teacherId(topic.getTeacherId()).maxNum(topic.getMaxNum())
                    .teacherName(teacherService.selectByPrimaryKey(topic.getTeacherId()).getName()).build();

            topicInfoResponseList.add(topicInfoResponse);
        }
        respMap.put("topicList", topicInfoResponseList);
        return respMap;
    }

    /**
     * 点击"我要读"进行选题--增加一条选题记录
     */
    // + 学生权限 + 接口测试
    @RequestMapping(value = "/stutopic/add", method = RequestMethod.GET)
    public Map addToMyTopics(@RequestParam(value = "topicId") String topicId) {
        Map<String, Object> respMap = new HashMap<>();

        //验证学生权限
        StatusResponse statusResponse = verifyStudent();
        if (statusResponse.getCode().equals("-2")) {
            respMap.put("statusResp", statusResponse);
            return respMap;
        }

        String loginId = RequestUtils.getLoginId(request);

        Topic topic = topicService.selectByPrimaryKey(topicId);

        Integer maxNum=topic.getMaxNum();
        Integer studentNum=topic.getStudentNum();

        if(studentNum>=maxNum)
        {
            respMap.put("statusResp", StatusResponse.error("-1", "选题人数已达到上限"));
            return respMap;
        }

        String stutopicId = loginId + topicId;

        if (stutopicService.selectByPrimaryKey(stutopicId) != null) {
            respMap.put("statusResp", StatusResponse.error("-1", "已经选了该主题"));
            return respMap;
        }

        //如果已选了该门课下的主题，则不可再选
        String courseId=topic.getCourseId();
        String studentId=loginId;

        List <Stutopic> stutopicList=stutopicService.selectByStuIdAndCourseId(studentId,courseId);
        if (stutopicList.size()!=0) {
            respMap.put("statusResp", StatusResponse.error("-1", "已经选了该课程下的其他主题"));
            return respMap;
        }


        Stutopic stutopic = Stutopic.builder().stutopicId(stutopicId).flag("0")
                .selectTime(new Date()).studentId(loginId).topicId(topicId).build();

        //插入一条学生选题记录
        stutopicService.insert(stutopic);

        //相应主题的选题人数++
        Integer stuNum = topic.getStudentNum() + 1;
        topic.setStudentNum(stuNum);
        topicService.updateByPrimaryKey(topic);

        respMap.put("statusResp", StatusResponse.ok());
        return respMap;
    }


    /**
     * 对课程下的每个主题
     * 允许查看选题学生名单等基本信息
     */
    // + 学生权限 + 接口测试
    @RequestMapping(value = "/topic/stu/list", method = RequestMethod.GET)
    public Map getStuListByTopicId(@RequestParam(value = "topicId") String topicId) {
        Map<String, Object> respMap = new HashMap<>();

        //验证学生权限
        StatusResponse statusResponse = verifyStudent();
        respMap.put("statusResp", statusResponse);
        if (statusResponse.getCode().equals("-2")) {
            return respMap;
        }

        List<TopicStudent> tstuList = new ArrayList<>();

        List<Stutopic> stutopicList = stutopicService.selectByTopicId(topicId);

        for (Stutopic st : stutopicList) {
            String studentId = st.getStudentId();
            Student s = studentService.selectByPrimaryKey(studentId);

            TopicStudent ts = TopicStudent.builder().className(s.getClassName())
                    .selectionTime(st.getSelectTime()).major(s.getMajor()).sex(s.getSex())
                    .studentId(s.getStudentId()).studentName(s.getName()).build();
            tstuList.add(ts);
        }

        respMap.put("tstuList", tstuList);
        return respMap;
    }

    /**
     * 和其上传的论文列表。
     *
     * @param topicId
     * @param studentId
     * @return
     */
    // + 学生权限 + 接口测试
    @RequestMapping(value = "/topic/stu/paper/list", method = RequestMethod.GET)
    public Map getPaperListByTopicIdByStuId(@RequestParam(value = "topicId") String topicId,
                                            @RequestParam(value = "studentId") String studentId) {
        Map<String, Object> respMap = new HashMap<>();

        //验证学生权限
        StatusResponse statusResponse = verifyStudent();
        respMap.put("statusResp", statusResponse);
        if (statusResponse.getCode().equals("-2")) {
            return respMap;
        }

        Stutopic stutopic = stutopicService.selectByStuIdAndTopicId(studentId, topicId);

        if (stutopic == null) {
            respMap.put("statusResp", StatusResponse.error("-1", "学生选题记录不存在"));
            return respMap;
        }
        String prelink="https://wenku.baidu.com/search?word=";
        String sufflink="&ie=utf-8";
        List<Topicpaper> topicpaperList = topicpaperService.selectByStutopicId(stutopic.getStutopicId());
        List<PaperInfo> paperList = new ArrayList<>();

        for (Topicpaper tp : topicpaperList) {
            Paper paper = paperService.selectByPrimaryKey(tp.getPaperId());
            if (paper.getStudentId().equals(studentId)) {
                String link=prelink+paper.getTitle()+sufflink;
                PaperInfo paperInfo=PaperInfo.builder().paperId(paper.getPaperId())
                        .title(paper.getTitle()).author(paper.getAuthor())
                        .source(paper.getSource()).keyword(paper.getKeyword())
                        .abstractText(paper.getAbstractText()).docLocation(paper.getDocLocation())
                        .studentId(paper.getStudentId()).uploadingTime(paper.getUploadingTime())
                        .link(link).build();
                paperList.add(paperInfo);
            }
        }

        respMap.put("paperList", paperList);
        return respMap;
    }


    @RequestMapping(value = "/topic/stu/paper/list/all", method = RequestMethod.GET)
    public Map getPaperListByTopicId(@RequestParam(value = "topicId") String topicId) {
        Map<String, Object> respMap = new HashMap<>();

        //验证学生权限
        StatusResponse statusResponse = verifyStudent();
        respMap.put("statusResp", statusResponse);
        if (statusResponse.getCode().equals("-2")) {
            return respMap;
        }

        List<Stutopic> stutopicList = stutopicService.selectByTopicId(topicId);


        if (stutopicList == null) {
            respMap.put("statusResp", StatusResponse.error("-1", "学生选题记录不存在"));
            return respMap;
        }
        String prelink="https://wenku.baidu.com/search?word=";
        String sufflink="&ie=utf-8";
        List<PaperInfo> paperList = new ArrayList<>();
        for (Stutopic stutopic : stutopicList) {
            List<Topicpaper> topicpaperList = topicpaperService.selectByStutopicId(stutopic.getStutopicId());
            for (Topicpaper tp : topicpaperList) {
                Paper paper = paperService.selectByPrimaryKey(tp.getPaperId());

                String link=prelink+paper.getTitle()+sufflink;

                PaperInfo paperInfo=PaperInfo.builder().paperId(paper.getPaperId())
                        .title(paper.getTitle()).author(paper.getAuthor())
                        .source(paper.getSource()).keyword(paper.getKeyword())
                        .abstractText(paper.getAbstractText()).docLocation(paper.getDocLocation())
                        .studentId(paper.getStudentId()).uploadingTime(paper.getUploadingTime())
                        .link(link).build();
                paperList.add(paperInfo);
            }
        }
        respMap.put("paperList", paperList);
        return respMap;
    }

    /**
     * 展示所有的论文信息，被下面调用
     */
    private List<Paper> allPaperList( int page, int pageSize) {
        //分页主题列表
        List<Paper> paperList = paperService.selectByPage( page, pageSize);
        return paperList;
    }
    /**
     * 以分页列表的形式展示所有的论文信息。
     */
    @RequestMapping(value = "/paper/list", method = RequestMethod.GET)
    public Map getAllPaperList() {
        Map<String, Object> respMap = new HashMap<>();

        //验证学生权限
        StatusResponse statusResponse = verifyStudent();
        respMap.put("statusResp", statusResponse);
        if (statusResponse.getCode().equals("-2")) {
            return respMap;
        }
        int page = RequestUtils.getPage(request);
        int pageSize = RequestUtils.getPageSize(request);
        respMap.put("paperList", allPaperList(page,pageSize));
        return respMap;
    }


    /**
     * 以分页列表的形式展示所有的评论信息。
     */
    @RequestMapping(value = "/paper/message/list", method = RequestMethod.GET)
    public Map getMessageListByPaperId(@RequestParam(value = "paperId") Integer paperId) {
        Map<String, Object> respMap = new HashMap<>();

        //验证学生权限
        StatusResponse statusResponse = verifyStudent();
        respMap.put("statusResp", statusResponse);
        if (statusResponse.getCode().equals("-2")) {
            return respMap;
        }
        int page = RequestUtils.getPage(request);
        int pageSize = RequestUtils.getPageSize(request);
        List<Message> messageList = messageService.selectByPIdByPage(paperId,page,pageSize);
        respMap.put("messageList", messageList);
        return respMap;
    }
    /**
     * 新增评论 -- 新增一条评论
     */
    @RequestMapping(value = "/paper/message/add", method = RequestMethod.POST)
    public Map addMessage(@RequestBody MessageRequest messageRequest) {

        Map<String, Object> respMap = new HashMap<>();
        //验证学生权限
        StatusResponse statusResponse = verifyStudent();
        respMap.put("statusResp", statusResponse);
        if (statusResponse.getCode().equals("-2")) {
            System.out.println(-2);
            return respMap;
        }
        Integer paperId=messageRequest.getPaperId();
        String content=messageRequest.getContent();
        String userId=RequestUtils.getLoginId(request);
        Message message=Message.builder().paperId(paperId).content(content).userId(userId).build();
        //插入数据库
        messageService.insert(message);
        respMap.put("statusResp", StatusResponse.ok());

        return respMap;

    }
    /**
     * 返回userId。
     */
    @RequestMapping(value = "/paper/message/info/all", method = RequestMethod.GET)
    public Map getUserId() {
        Map<String, Object> respMap = new HashMap<>();

        //验证学生权限
        StatusResponse statusResponse = verifyStudent();
        respMap.put("statusResp", statusResponse);
        if (statusResponse.getCode().equals("-2")) {
            return respMap;
        }
        String userId=RequestUtils.getLoginId(request);
        respMap.put("userId", userId);
        return respMap;
    }
    /**
     * 查看我的主题和论文列表上传页面。
     * 在完成课程选题后，
     * 可在 “我的主题”页面查看自己的选题并进入论文列表上传页面，
     * 从excel中导入论文列表。
     */

    //查看我的选题
    // + 学生权限 + 接口测试
    @RequestMapping(value = "/topic/self/list", method = RequestMethod.GET)
    public Map getMyTopics() {
        Map<String, Object> respMap = new HashMap<>();

        //验证学生权限
        StatusResponse statusResponse = verifyStudent();
        respMap.put("statusResp", statusResponse);
        if (statusResponse.getCode().equals("-2")) {
            return respMap;
        }

        String loginId = RequestUtils.getLoginId(request);

        List<SelectTopicResponse> selectTopicList = new ArrayList<>();
        List<Stutopic> stutopicList = stutopicService.selectByStudentId(loginId);

        //整理返回信息
        for (Stutopic st : stutopicList) {

            Topic topic = topicService.selectByPrimaryKey(st.getTopicId());

            String courseId = topic.getCourseId();
            Course course = courseService.selectByPrimaryKey(courseId);

            String teacherId = topic.getTeacherId();
            Teacher teacher = teacherService.selectByPrimaryKey(teacherId);

            SelectTopicResponse selectTopicResponse = SelectTopicResponse.builder().topic(topic.getTopic())
                    .topicId(topic.getTopicId()).courseId(courseId).courseName(course.getCourseName())
                    .creationTime(topic.getCreationTime()).selectionTime(st.getSelectTime()).keyword(topic.getKeyword())
                    .teacherId(teacherId).teacherName(teacher.getName()).build();
            selectTopicList.add(selectTopicResponse);
        }

        respMap.put("selectTopicList", selectTopicList);
        return respMap;
    }

    /**
     * 从excel中导入论文列表。
     */
    @RequestMapping(value = "/paper/upload", method = RequestMethod.POST)
    public Map uploadPaperList(@RequestParam MultipartFile file, @RequestParam(value = "topicId") String topicId) {

        Map<String, Object> respMap = new HashMap<>();

        //验证学生权限
        StatusResponse statusResponse = verifyStudent();
        if (statusResponse.getCode().equals("-2")) {
            respMap.put("statusResp", statusResponse);
            return respMap;
        }

        boolean a = false;

        String fileName = file.getOriginalFilename();

        try {
            a = paperService.batchImport(fileName, file, topicId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String studentId = RequestUtils.getLoginId(request);

        // 上传成功
        if (a) {
            Stutopic stutopic = stutopicService.selectByStuIdAndTopicId(studentId, topicId);

            if (stutopic == null) {
                respMap.put("statusResp", StatusResponse.error("-1", "学生选题记录不存在"));
                return respMap;
            }
            stutopic.setFlag("1");
            stutopicService.updateByPrimaryKey(stutopic);
            respMap.put("statusResp", StatusResponse.ok());
        }

        return respMap;
    }

    //验证学生权限
    private StatusResponse verifyStudent() {
        String loginId = RequestUtils.getLoginId(request);
        if (!VerifyAuthorityUtils.verifyStudentRole(loginId, userService)) {
            return StatusResponse.error("-2", "权限不够，要求学生权限。");
        }
        return StatusResponse.ok();
    }
}
