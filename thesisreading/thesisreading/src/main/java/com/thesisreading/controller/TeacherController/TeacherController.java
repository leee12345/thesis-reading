package com.thesisreading.controller.TeacherController;


import com.thesisreading.controller.AdminController.AdminController;
import com.thesisreading.controller.AdminController.bean.response.CourseMajorTeaResponse;
import com.thesisreading.controller.StudentController.bean.request.MessageRequest;
import com.thesisreading.controller.StudentController.bean.response.PaperInfo;
import com.thesisreading.controller.TeacherController.bean.request.EditScRequest;
import com.thesisreading.controller.TeacherController.bean.request.NewTopicRequest;
import com.thesisreading.controller.TeacherController.bean.request.TcInfoAddRequest;
import com.thesisreading.controller.TeacherController.bean.response.*;
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
@RequestMapping(value = "/teacher", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
public class TeacherController {

    @Resource
    HttpServletRequest request;

    @Resource
    CourseService courseService;

    @Resource
    TeacherService teacherService;

    @Resource
    TcService tcService;

    @Resource
    StudentService studentService;

    @Resource
    ScService scService;

    @Resource
    StutopicService stutopicService;

    @Resource
    TopicService topicService;

    @Resource
    PaperService paperService;

    @Resource
    TopicpaperService topicpaperService;

    @Resource
    AdminController adminController;

    @Resource
    UserService userService;

    @Resource
    MessageService messageService;
    /**
     * 页面内直接展示出该教师所授课程列表
     * 课程ID,课程名，开设学期，总课时数、主讲教师姓名
     */
    // + 权限 + 接口测试
    @RequestMapping(value = "/teach/course/list", method = RequestMethod.GET)
    public Map courseListByTeacherId(@RequestParam(value = "teacherId") String teacherId) {
        Map<String, Object> respMap = new HashMap<>();

        StatusResponse statusResponse = verifyTeacher();
        respMap.put("statusResp", statusResponse);
        if (statusResponse.getCode().equals("-2")) {
            return respMap;
        }

        List<Tc> tcList = tcService.selectByTeacherId(teacherId);


        /*课程基本信息+主讲教师id和name*/
        List<CourseMajorTeaResponse> cmtList = new ArrayList<>();

        for (Tc tc : tcList) {

            //对于每一条授课记录，得到相应的课程号
            String courseId = tc.getCourseId();

            //对应的课程
            Course course = courseService.selectByPrimaryKey(courseId);

            //课程名
            String courseName = course.getCourseName();

            //课程学时
            Integer courseHour = course.getCourseHour();

            //开设学期
            String courseTerm = course.getTerm();

            String mteacherId = null;
            String mteacherName = null;

            //根据课程号再到tc表中找到主讲教师
            //如果这门课设置了主讲教师
            Tc mtc = tcService.selectContainMajorTeacherByCourseId(courseId);
            if (mtc != null) {
                mteacherId = mtc.getTeacherId();
                mteacherName = teacherService.selectByPrimaryKey(mteacherId).getName();
            }

            CourseMajorTeaResponse cmt = CourseMajorTeaResponse.builder()
                    .courseId(courseId).courseName(courseName)
                    .courseHour(courseHour).term(courseTerm)
                    .mteacherId(mteacherId == null ? "" : mteacherId)
                    .mteacherName(mteacherName == null ? "" : mteacherName).build();

            cmtList.add(cmt);
        }
        respMap.put("cmtList", cmtList);
        return respMap;
    }

    /**
     * 学生管理
     */

    /**
     * 以分页列表形式展示该课程所有学生信息，
     * 包括（学号，姓名，性别，专业，班级）等信息。
     */
    // + 权限 + 接口测试
    @RequestMapping(value = "/course/stu/list", method = RequestMethod.GET)
    public Map allStuInfoByCourseId(@RequestParam("courseId") String courseId) {
        Map<String, Object> respMap = new HashMap<>();

        //教师权限
        StatusResponse statusResponse = verifyTeacher();
        respMap.put("statusResp", statusResponse);
        if (statusResponse.getCode().equals("-2")) {
            return respMap;
        }

        List<Sc> scList = scService.selectByCourseId(courseId);

        //该课程的所有学生
        List<Student> studentList = new ArrayList<>();
        for (Sc sc : scList) {
            //对于每一条选课记录，得到相应的学号
            String stuId = sc.getStudentId();

            //根据学号找到对应的学生信息
            Student s = studentService.selectByPrimaryKey(stuId);

            //将学生添加到学生列表中
            studentList.add(s);
        }

        //得到page size
        int totalSize = studentList.size();
        int page = RequestUtils.getPage(request);
        int size = RequestUtils.getPageSize(request);

        List<Student> stuList = new ArrayList<>();

        //显示的范围：
        //[(page - 1) * size, page * size - 1]
        int startIndex = (page - 1) * size;
        int endIndex = page * size - 1;
        for (int i = startIndex; i < totalSize && i <= endIndex; i++) {
            stuList.add(studentList.get(i));
        }

        respMap.put("stuList", stuList);
        return respMap;
    }

    /**
     * 学生选题查看页面，点击某个学生，可以查看该学生的选题信息
     * （主题ID，主题名，关键词，指导教师姓名）以及该学生为该主题上传的论文列表等
     */

    //  选题信息
    // + 教师权限 + 接口测试
    @RequestMapping(value = "/stu/topic/info", method = RequestMethod.GET)
    public Map getStuTopicInfo(@RequestParam(value = "studentId") String studentId,
                               @RequestParam(value = "courseId") String courseId) {
        Map<String, Object> respMap = new HashMap<>();

        //check teacher
        StatusResponse statusResponse = verifyTeacher();
        respMap.put("statusResp", statusResponse);
        if (statusResponse.getCode().equals("-2")) {
            return respMap;
        }

        //根据学号选出 该学生的选题记录
        List<Stutopic> stList = stutopicService.selectByStuIdAndCourseId(studentId,courseId);
        List<StuTopicInfo> stiList = new ArrayList<>();

        //对于每一条选题记录
        for (Stutopic st : stList) {
            String stutopicId = st.getStutopicId();
            String topicId = st.getTopicId();
            Topic tp = topicService.selectByPrimaryKey(topicId);
            StuTopicInfo sti = StuTopicInfo.builder().stutopicId(stutopicId).topicId(topicId)
                    .topicName(tp.getTopic()).keyword(tp.getKeyword()).teacherId(tp.getTeacherId())
                    .teacherName(teacherService.selectByPrimaryKey(tp.getTeacherId()).getName()).build();
            stiList.add(sti);
        }
        respMap.put("stutopicList", stiList);
        return respMap;
    }

    //为某主题上传的论文列表
    // + 教师权限 +
    @RequestMapping(value = "/stu/topic/paperlist", method = RequestMethod.GET)
    public Map getStuPaperInfo(@RequestParam(value = "studentId") String studentId,
                               @RequestParam(value = "topicId") String topicId) {
        Map<String, Object> respMap = new HashMap<>();

        //check teacher
        StatusResponse statusResponse = verifyTeacher();
        respMap.put("statusResp", statusResponse);
        if (statusResponse.getCode().equals("-2")) {
            return respMap;
        }

        //找到stutopic
        Stutopic stutopic = stutopicService.selectByStuIdAndTopicId(studentId, topicId);
        if (stutopic == null) {
            respMap.put("statusResp", StatusResponse.error("-1", "学生选题记录不存在"));
            return respMap;
        }

        //根据stutopic的id找到对应的论文id。
        List<Topicpaper> topicpaperList = topicpaperService.selectByStutopicId(stutopic.getStutopicId());

        //上传的论文列表
        List<Paper> paperList = new ArrayList<>();

        for (Topicpaper tp : topicpaperList) {
            Integer paperId = tp.getPaperId();
            //在论文表里找 论文id 学号id 的论文
            Paper paper = paperService.selectByPrimaryKey(paperId);
            if (paper.getStudentId().equals(studentId)) {
                paperList.add(paper);
            }
        }
        respMap.put("paperList", paperList);
        return respMap;
    }

    /**
     * 若是该课主讲教师，可进入学生名单编辑页面。
     * 新增、编辑、删除选课学生
     */

    /**
     * 新增选课学生 -- 新增一条记录
     */
    // + 权限 + 接口测试
    @RequestMapping(value = "/sc/add", method = RequestMethod.POST)
    public Map addSc(@RequestParam(value = "studentId") String studentId,
                     @RequestParam(value = "courseId") String courseId) {
        Map<String, Object> respMap = new HashMap<>();
        //主讲教师的权限
        StatusResponse statusResponse = verifyMajorTeacher(courseId);
        respMap.put("statusResp", statusResponse);
        if (statusResponse.getCode().equals("-2")) {
            return respMap;
        }

        //从已有的学生中选择
        // 非法操作
        if (studentService.selectByPrimaryKey(studentId) == null
                || courseService.selectByPrimaryKey(courseId) == null) {
            respMap.put("statusResp", StatusResponse.error("-1", "ileagal operation."));
            return respMap;
        }
        //记录已存在
        Sc sc1 = scService.selectByStuIdAndCourseId(studentId, courseId);
        if (sc1 != null) {
            respMap.put("statusResp", StatusResponse.error("-1", "学生已经选课，不可重复操作。"));
            return respMap;
        }

        //用studentId+courseId拼接作为scId
        String scId = studentId + courseId;

        //创建一条学生选课记录，score默认为0
        Sc sc = Sc.builder().scId(scId).courseId(courseId).studentId(studentId).score(0).build();

        //插入数据库
        scService.insert(sc);

        respMap.put("statusResp", StatusResponse.ok());
        return respMap;
    }

    /**
     * 编辑选课学生--- 编辑学生分数 -- 更新一条记录
     */

    // + 主讲权限 + 接口测试
    @RequestMapping(value = "/sc/edit/score", method = RequestMethod.POST)
    public Map editSc(@RequestBody EditScRequest editScRequest) {

        Map<String, Object> respMap = new HashMap<>();

        String scId = editScRequest.getScId();
        Sc sc = scService.selectByPrimaryKey(scId);
        if (sc == null) {
            respMap.put("statusResp", StatusResponse.error("-1", "选课记录不存在"));
            return respMap;
        }
        String courseId = sc.getCourseId();

        //主讲教师的权限
        StatusResponse statusResponse = verifyMajorTeacher(courseId);
        respMap.put("statusResp", statusResponse);
        if (statusResponse.getCode().equals("-2")) {
            return respMap;
        }

        // 修改分数
        sc.setScore(editScRequest.getScore());
        scService.updateByPrimaryKey(sc);

        return respMap;
    }

    /**
     * 删除选课学生 -- 删除一条记录
     */
    // + 主讲权限 + 接口测试
    @RequestMapping(value = "/sc/delete", method = RequestMethod.GET)
    public Map deleteSc(@RequestParam(value = "studentId") String studentId,
                        @RequestParam(value = "courseId") String courseId) {
        Map<String, Object> respMap = new HashMap<>();

        //主讲教师的权限
        StatusResponse statusResponse = verifyMajorTeacher(courseId);
        respMap.put("statusResp", statusResponse);
        if (statusResponse.getCode().equals("-2")) {
            return respMap;
        }

        Sc sc = scService.selectByStuIdAndCourseId(studentId, courseId);
        if (sc == null) {
            respMap.put("statusResp", StatusResponse.error("-1", "illegal operation."));
            return respMap;
        }

        scService.deleteByPrimaryKey(sc.getScId());

        respMap.put("statusResp", StatusResponse.ok());
        return respMap;
    }

    //得到一条选课记录
    @RequestMapping(value = "/sc/get", method = RequestMethod.GET)
    public Map getSc(@RequestParam(value = "studentId") String studentId,
                     @RequestParam(value = "courseId") String courseId) {
        Map<String, Object> respMap = new HashMap<>();

        //主讲教师的权限
        StatusResponse statusResponse = verifyMajorTeacher(courseId);
        respMap.put("statusResp", statusResponse);
        if (statusResponse.getCode().equals("-2")) {
            return respMap;
        }
        Sc sc = scService.selectByStuIdAndCourseId(studentId, courseId);
        if (sc == null) {
            respMap.put("statusResp", StatusResponse.error("-1", "illegal operation."));
            return respMap;
        }

        respMap.put("sc", sc);

        respMap.put("statusResp", StatusResponse.ok());
        return respMap;
    }

    /**
     * 选题管理
     */

    /**
     * 以分页列表形式展示该课程所有论文主题信息
     */
    // 被下面调用
    private List<TopicTeaName> topicTeaNamesList(String courseId, int page, int pageSize) {
        //分页主题列表
        List<Topic> topicList = topicService.selectByCIdByPage(courseId, page, pageSize);

        //调整返回信息
        List<TopicTeaName> topictnlist = new ArrayList<>();
        for (Topic topic : topicList) {
            String teacherName = teacherService.selectByPrimaryKey(topic.getTeacherId()).getName();
            TopicTeaName ttn = TopicTeaName.builder().topicId(topic.getTopicId())
                    .teacherName(teacherName).topicName(topic.getTopic())
                    .keyword(topic.getKeyword()).creationTime(topic.getCreationTime())
                    .studentNum(topic.getStudentNum()).maxNum(topic.getMaxNum()).build();
            topictnlist.add(ttn);
        }
        return topictnlist;
    }

    /**
     * 查看某课程下的主题
     *
     * @param courseId
     * @return
     */
    // + 教师权限 + 接口测试
    @RequestMapping(value = "/topic/list", method = RequestMethod.GET)
    public Map getPaperTopicByCIdByPage(@RequestParam(value = "courseId") String courseId) {
        Map<String, Object> respMap = new HashMap<>();

        //check teacher
        StatusResponse statusResponse = verifyTeacher();
        respMap.put("statusResp", statusResponse);
        if (statusResponse.getCode().equals("-2")) {
            return respMap;
        }

        int page = RequestUtils.getPage(request);
        int pageSize = RequestUtils.getPageSize(request);
        respMap.put("topictnlist", topicTeaNamesList(courseId, page, pageSize));
        return respMap;
    }


    /**
     * 可以查看该老师布置的主题的内容、 === 主题表的基本信息 record
     * 选取该主题的学生信息、 === 选取该主题的学生list
     * 以及学生上传的论文列表 === paperlist
     * （若是该课程主讲教师可以查看所有主题详情）。
     *
     * @param topicId
     * @return
     */
    // + 主讲教师或者出题教师的权限 + 接口测试
    @RequestMapping(value = "/topic/details", method = RequestMethod.GET)
    public Map getTopicDetailsByTopicId(@RequestParam(value = "topicId") String topicId) {

        //团队教师可以查看他所教课程下面他自己出的主题的详情
        //主讲教师可以查看该课程下所有主题的详情

        Map<String, Object> respMap = new HashMap<>();

        //登录用户
        String loginId = RequestUtils.getLoginId(request);

        Topic topic = topicService.selectByPrimaryKey(topicId);
        if (topic == null) {
            respMap.put("statusResp", StatusResponse.error("-1", "主题不存在"));
            return respMap;
        }

        TopicInfo topicInfo = TopicInfo.builder().courseId(topic.getCourseId())
                .courseName(courseService.selectByPrimaryKey(topic.getCourseId()).getCourseName())
                .creationTime(topic.getCreationTime()).keyword(topic.getKeyword())
                .studentNum(topic.getStudentNum()).teacherId(topic.getTeacherId())
                .teacherName(teacherService.selectByPrimaryKey(topic.getTeacherId()).getName())
                .maxNum(topic.getMaxNum()).topic(topic.getTopic()).topicId(topicId).build();

        String courseId = topic.getCourseId();

        // 拥有主讲教师或者出题教师中的任何一种权限，都可以查看主题详情，其他情况都不可以
        if ((VerifyAuthorityUtils.verifyMajorTeacher(loginId, courseId, tcService)
                || topic.getTeacherId().equals(loginId))) {

            //可以查看该老师布置的主题的内容 === 主题表的基本信息 record
            respMap.put("topic", topicInfo);

            //课程下的主题
            List<Stutopic> stutopicList = stutopicService.selectByTopicId(topicId);

            //选取该主题的学生信息、 === 选取该主题的学生list
            //逻辑上student不会重复，也可以考虑set集合
            List<Student> studentList = new ArrayList<>();

            //以及学生上传的论文列表 === paperlist -- 所有学生上传的该主题的论文列表
            List<TcPaperInfo> paperList = new ArrayList<>();


            for (Stutopic st : stutopicList) {
                Student student = studentService.selectByPrimaryKey(st.getStudentId());
                if (student != null)
                    studentList.add(student);

                List<Topicpaper> topicpaperList = topicpaperService.selectByStutopicId(st.getStutopicId());
                String prelink="https://wenku.baidu.com/search?word=";
                String sufflink="&ie=utf-8";

                for (Topicpaper tp : topicpaperList) {
                    Paper paper = paperService.selectByPrimaryKey(tp.getPaperId());
                    if (paper != null)
                    {
                        String link=prelink+paper.getTitle()+sufflink;
                        TcPaperInfo tcpaperInfo=TcPaperInfo.builder().paperId(paper.getPaperId())
                                .title(paper.getTitle()).author(paper.getAuthor())
                                .source(paper.getSource()).keyword(paper.getKeyword())
                                .abstractText(paper.getAbstractText()).docLocation(paper.getDocLocation())
                                .studentId(paper.getStudentId()).uploadingTime(paper.getUploadingTime())
                                .link(link).build();
                        paperList.add(tcpaperInfo);
                    }

                }
            }
            respMap.put("studentList", studentList);
            respMap.put("paperList", paperList);
            respMap.put("statusResp", StatusResponse.ok());
            return respMap;

        } else {
            respMap.put("statusResp", StatusResponse.error("-2", "权限不够，需要主讲教师或者出题教师的权限"));
            return respMap;
        }
    }

    /**
     * 课程教师新增主题
     *
     * @param newTopicRequest
     * @return
     */
    // + 课程教师权限 + 接口测试
    @RequestMapping(value = "/topic/add", method = RequestMethod.POST)
    public Map addTopic(@RequestBody NewTopicRequest newTopicRequest) {

        Map<String, Object> respMap = new HashMap<>();
        String loginId = RequestUtils.getLoginId(request);
        String courseId = newTopicRequest.getCourseId();

        Course course = courseService.selectByPrimaryKey(courseId);
        if (course == null) {
            respMap.put("statusResp", StatusResponse.error("-1", "课程不存在"));
            return respMap;
        }

        //检查是否是这门课程的教师
        //验证课程教师权限
        StatusResponse statusResponse = verifyCourseTeacher(courseId);
        respMap.put("statusResp", statusResponse);
        if (statusResponse.getCode().equals("-2")) {
            return respMap;
        }

        if (topicService.selectByPrimaryKey(newTopicRequest.getTopicId()) != null) {
            respMap.put("statusResp", StatusResponse.error("-1", "主题id冲突"));
            return respMap;
        }

        Integer maxNum=newTopicRequest.getMaxNum();

        if (maxNum<1||maxNum>20) {
            respMap.put("statusResp", StatusResponse.error("-1", "人数上限必须在1~20内"));
            return respMap;
        }

        Topic topic = Topic.builder().topic(newTopicRequest.getTopic())
                .topicId(newTopicRequest.getTopicId()).courseId(courseId)
                .keyword(newTopicRequest.getKeyword()).teacherId(loginId)
                .creationTime(new Date()).studentNum(0).maxNum(maxNum).build();
        topicService.insert(topic);

        return respMap;
    }

    /**
     * 教师管理页面。
     * 主讲教师对课程授课教师的管理
     * 以分页列表形式展示该课程所有授课教师信息，
     * 包括（教师ID，姓名，身份（主讲教师或者团队教师），授课学时）
     */
    // + 主讲权限 + 接口测试
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Map getTeacherInfoListByCId(@RequestParam(value = "courseId") String courseId) {
        Map<String, Object> respMap = new HashMap<>();

        Course course = courseService.selectByPrimaryKey(courseId);
        if (course == null) {
            respMap.put("statusResp", StatusResponse.error("-1", "课程不存在"));
            return respMap;
        }

        // 查看的时候只需要验证教师权限，不必主讲身份
        StatusResponse statusResponse = verifyTeacher();
        respMap.put("statusResp", statusResponse);
        if (statusResponse.getCode().equals("-2")) {
            return respMap;
        }

        int page = RequestUtils.getPage(request);
        int pageSize = RequestUtils.getPageSize(request);
        List<TcInfoResponse> tcIList = new ArrayList<>();

        List<Tc> list = tcService.selectByCIdByPage(courseId, page, pageSize);

        for (Tc tc :list){
            TcInfoResponse tci = TcInfoResponse.builder().courseId(tc.getCourseId())
                    .courseName(courseService.selectByPrimaryKey(courseId).getCourseName())
                    .tcId(tc.getTcId()).teacherId(tc.getTeacherId())
                    .teacherName(teacherService.selectByPrimaryKey(tc.getTeacherId()).getName())
                    .teachHour(tc.getTeachHour()).teaIdentity(tc.getTeaIdentity()).build();
            tcIList.add(tci);
        }

        respMap.put("tcList", tcIList);
        return respMap;
    }

    //check：
    //课时数冲突——一门课下所有教师的课时数之和不能超过该门课程的总课时数
    //检查身份冲突——一门课下只有一位教师是主讲教师身份）

    /**
     * 主讲教师可进入授课信息修改 只是修改授课学时
     */
    // + 权限 + 主讲权限 + 接口测试
    @RequestMapping(value = "/tc/update", method = RequestMethod.POST)
    public Map updateTcByCId(@RequestParam(value = "tcId") String tcId,
                             @RequestParam(value = "teachHour") int teachHour) {

        Map<String, Object> respMap = new HashMap<>();

        //check teacher
        StatusResponse statusResponse = verifyTeacher();
        respMap.put("statusResp", statusResponse);
        if (statusResponse.getCode().equals("-2")) {
            return respMap;
        }

        Tc tc = tcService.selectByPrimaryKey(tcId);
        if (tc == null) {
            respMap.put("statusResp", StatusResponse.error("-1", "授课记录不存在"));
            return respMap;
        }

        String courseId = tc.getCourseId();

        //主讲教师的权限
        statusResponse = verifyMajorTeacher(courseId);
        respMap.put("statusResp", statusResponse);
        if (statusResponse.getCode().equals("-2")) {
            return respMap;
        }

        //check 课时数
        if (!adminController.passCourseHourCheck(courseId, tc.getTeachHour(), teachHour)) {
            respMap.put("statusResp", StatusResponse.error("-1", "课时数冲突"));
            return respMap;
        }

        //改变授课学时
        tc.setTeachHour(teachHour);
        //更新数据库
        tcService.updateByPrimaryKey(tc);

        respMap.put("statusResp", StatusResponse.ok());

        //更新后的该课程下的授课记录列表
        List<Tc> tcList = tcService.selectByCourseId(courseId);

        respMap.put("tcList", tcList);
        return respMap;
    }


    /**
     * 删除授课 -- 删除一条tc记录
     */
    // + 权限 + 主讲权限 + 接口测试
    @RequestMapping(value = "/tc/delete", method = RequestMethod.POST)
    public Map deleteTc(@RequestParam(value = "tcId") String tcId) {
        Map<String, Object> respMap = new HashMap<>();
        //check teacher
        StatusResponse statusResponse = verifyTeacher();
        respMap.put("statusResp", statusResponse);
        if (statusResponse.getCode().equals("-2")) {
            return respMap;
        }

        Tc tc = tcService.selectByPrimaryKey(tcId);
        if (tc == null) {
            respMap.put("statusResp", StatusResponse.error("-1", "授课记录不存在，无法删除"));
            return respMap;
        }

        String courseId = tc.getCourseId();
        //主讲教师的权限
        statusResponse = verifyMajorTeacher(courseId);
        respMap.put("statusResp", statusResponse);
        if (statusResponse.getCode().equals("-2")) {
            return respMap;
        }

        //不需要check冲突
        tcService.deleteByPrimaryKey(tcId);

        //更新后的该课程下的授课记录列表
        List<Tc> tcList = tcService.selectByTeacherId(courseId);
        respMap.put("tcList", tcList);

        return respMap;
    }


    /**
     * 得到一条tc记录
     */
    @RequestMapping(value = "/tc/get", method = RequestMethod.GET)
    public Map getTc(@RequestParam(value = "tcId") String tcId) {
        Map<String, Object> respMap = new HashMap<>();
        //check teacher
        StatusResponse statusResponse = verifyTeacher();
        respMap.put("statusResp", statusResponse);
        if (statusResponse.getCode().equals("-2")) {
            return respMap;
        }

        Tc tc = tcService.selectByPrimaryKey(tcId);
        if (tc == null) {
            respMap.put("statusResp", StatusResponse.error("-1", "授课记录不存在，无法编辑"));
            return respMap;
        }

        String courseId = tc.getCourseId();
        //主讲教师的权限
        statusResponse = verifyMajorTeacher(courseId);
        respMap.put("statusResp", statusResponse);
        if (statusResponse.getCode().equals("-2")) {
            return respMap;
        }

        respMap.put("tc", tc);
        return respMap;
    }

    /**
     * 主讲教师可进入新增授课教师页面。
     * 从已注册的教师用户中选择一名教师新增为本课程的授课教师并设置授课学时。
     */
    // + 权限 + 主讲权限 + 接口测试
    @RequestMapping(value = "/tc/add", method = RequestMethod.POST)
    public Map addTc(@RequestBody TcInfoAddRequest tcInfoAddRequest) {
        Map<String, Object> respMap = new HashMap<>();

        String teacherId = tcInfoAddRequest.getTeacherId();
        String courseId = tcInfoAddRequest.getCourseId();

        //check teacher
        StatusResponse statusResponse = verifyTeacher();
        respMap.put("statusResp", statusResponse);
        if (statusResponse.getCode().equals("-2")) {
            return respMap;
        }

        Course course = courseService.selectByPrimaryKey(courseId);

        if (course == null) {
            respMap.put("statusResp", StatusResponse.error("-1", "该课程不存在"));
            return respMap;
        }

        //主讲教师的权限
        statusResponse = verifyMajorTeacher(courseId);
        respMap.put("statusResp", statusResponse);
        if (statusResponse.getCode().equals("-2")) {
            return respMap;
        }

        Teacher teacher = teacherService.selectByPrimaryKey(teacherId);

        if (teacher == null) {
            respMap.put("statusResp", StatusResponse.error("-1", "该教师未注册"));
            return respMap;
        }

        String tcId = teacherId + courseId;
        Tc tc = tcService.selectByPrimaryKey(tcId);

        if (tc != null) {
            respMap.put("statusResp", StatusResponse.error("-1", "该授课记录已经存在，不可重复添加"));
            return respMap;
        }
        String newTeaIdentity = "团队";
        Integer newTeaHour = tcInfoAddRequest.getTeachHour();
        Tc newTc = Tc.builder().tcId(tcId).courseId(courseId).teacherId(teacherId).teaIdentity(newTeaIdentity)
                .teachHour(newTeaHour).build();

        // 设置为团队教师，不需要检查教师身份冲突

        // 检查课时数冲突
        if (!adminController.passCourseHourCheck(courseId, 0, newTeaHour)) {
            respMap.put("statusResp", StatusResponse.error("-1", "课时数冲突"));
            return respMap;
        }

        //新增tc记录
        tcService.insert(newTc);
        respMap.put("statusResp", StatusResponse.ok());
        respMap.put("newTc", newTc);
        return respMap;
    }

    /**
     * 得到所有的注册教师list
     *
     * @return
     */
    // + 权限 + 接口测试
    @RequestMapping(value = "/listall", method = RequestMethod.GET)
    public Map getAllTeachers() {
        Map<String, Object> respMap = new HashMap<>();

        //check teacher
        StatusResponse statusResponse = verifyTeacher();
        respMap.put("statusResp", statusResponse);
        if (statusResponse.getCode().equals("-2")) {
            return respMap;
        }
        List<Teacher> teacherList = teacherService.selectAll();
        respMap.put("allTeachers", teacherList);
        return respMap;
    }

    /**
     * 得到所有学生list =》 新增学生
     * @return
     */
    @RequestMapping(value = "/stu/info/all", method = RequestMethod.GET)
    public Map getAllStudents() {
        Map<String, Object> respMap = new HashMap<>();
        //check teacher
        StatusResponse statusResponse = verifyTeacher();
        respMap.put("statusResp", statusResponse);
        if (statusResponse.getCode().equals("-2")) {
            return respMap;
        }
        List<Student> studentList = studentService.selectAll();
        respMap.put("allStudents", studentList);
        return respMap;
    }

    /**
     * 评论管理
     */
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
        StatusResponse statusResponse = verifyTeacher();
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
        StatusResponse statusResponse = verifyTeacher();
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
        StatusResponse statusResponse = verifyTeacher();
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
        StatusResponse statusResponse = verifyTeacher();
        respMap.put("statusResp", statusResponse);
        if (statusResponse.getCode().equals("-2")) {
            return respMap;
        }
        String userId=RequestUtils.getLoginId(request);
        respMap.put("userId", userId);
        return respMap;
    }

    //验证教师权限
    private StatusResponse verifyTeacher() {
        String loginId = RequestUtils.getLoginId(request);
        if (!VerifyAuthorityUtils.verifyTeacherRole(loginId, userService)) {
            return StatusResponse.error("-2", "权限不够，要求教师权限。");
        }
        return StatusResponse.ok();
    }

    //验证主讲教师权限
    private StatusResponse verifyMajorTeacher(String courseId) {
        String loginId = RequestUtils.getLoginId(request);
        if (!VerifyAuthorityUtils.verifyMajorTeacher(loginId, courseId, tcService)) {
            return StatusResponse.error("-2", "权限不够，要求主讲教师权限。");
        }
        return StatusResponse.ok();
    }

    //验证课程教师权限
    private StatusResponse verifyCourseTeacher(String courseId) {
        String loginId = RequestUtils.getLoginId(request);
        if (!VerifyAuthorityUtils.verifyCourseTeacher(loginId, courseId, tcService)) {
            return StatusResponse.error("-2", "权限不够，要求课程教师权限。");
        }
        return StatusResponse.ok();
    }

}
