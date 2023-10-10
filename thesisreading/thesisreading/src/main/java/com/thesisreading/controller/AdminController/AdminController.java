package com.thesisreading.controller.AdminController;

import com.thesisreading.controller.AdminController.bean.request.*;
import com.thesisreading.controller.AdminController.bean.response.CourseMajorTeaResponse;
import com.thesisreading.controller.AdminController.bean.response.CourseTeachInfo;
import com.thesisreading.controller.AdminController.bean.response.TcResponse;
import com.thesisreading.controller.UserController.UserController;
import com.thesisreading.controller.UserController.bean.request.TeacherSignUpRequest;
import com.thesisreading.controller.bean.response.StatusResponse;
import com.thesisreading.model.Course;
import com.thesisreading.model.Tc;
import com.thesisreading.model.Teacher;
import com.thesisreading.service.CourseService;
import com.thesisreading.service.TcService;
import com.thesisreading.service.TeacherService;
import com.thesisreading.service.UserService;
import com.thesisreading.utils.RequestUtils;
import com.thesisreading.utils.VerifyAuthorityUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(value = "/admin", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
public class AdminController {

    @Resource
    HttpServletRequest request;

    @Resource
    TeacherService teacherService;

    @Resource
    TcService tcService;

    @Resource
    CourseService courseService;

    @Resource
    UserService userService;

    @Resource
    UserController userController;

    /**
     * 教师基本信息列表 分页显示
     */
    // + admin权限
    @RequestMapping(value = "/teacher/info/list", method = RequestMethod.POST)
    public Map teacherInfoList() {
        Map<String, Object> respMap = new HashMap<>();
        StatusResponse statusResponse = verifyAdmin();
        respMap.put("statusResp", statusResponse);
        if (statusResponse.getCode().equals("-2")){
            return respMap;
        }
        respMap.put("teaList", teacherService.selectByPage(RequestUtils.getPage(request), RequestUtils.getPageSize(request)));
        return respMap;
    }

    /**
     * 教师详情 teacherid
     * 教师基本信息
     * 授课详情信息：所授课程列表、主讲/团队身份、课时数
     * 可以编辑～～
     * 注意：
     * 检查核对课时数冲突——一门课下所有教师的课时数之和不能超过该门课程的总课时数
     * 检查身份冲突——一门课下只有一位教师是主讲教师身份
     */

    /**
     * 查看教师详情
     */
    // + admin权限
    @RequestMapping(value = "/teacher/info/get", method = RequestMethod.POST)
    public Map getTeacherInfo(@RequestParam(value = "teacherid") String teacherid) {
        Map<String, Object> respMap = new HashMap<>();

        StatusResponse statusResponse = verifyAdmin();
        respMap.put("statusResp", statusResponse);
        if (statusResponse.getCode().equals("-2")){
            return respMap;
        }

        //查教师表 -- 找到教师基本信息
        Teacher teacher = teacherService.selectByPrimaryKey(teacherid);

        //查tc表 -- 找到教师授课信息
        List<Tc> tcList = tcService.selectByTeacherId(teacherid);

        List<TcResponse> tcRespList = new ArrayList<>();

        for (Tc tc:tcList){
            TcResponse tcr = TcResponse.builder().courseId(tc.getCourseId())
                    .courseName(courseService.selectByPrimaryKey(tc.getCourseId()).getCourseName())
                    .tcId(tc.getTcId()).teacherId(tc.getTeacherId()).teachHour(tc.getTeachHour())
                    .teaIdentity(tc.getTeaIdentity()).build();
            tcRespList.add(tcr);
        }

        respMap.put("teacher", teacher);
        respMap.put("tcRespList", tcRespList);

        return respMap;
    }

    /**
     * 更新教师基本信息--基本信息
     */
    // + admin权限
    @RequestMapping(value = "/teacher/info/update", method = RequestMethod.POST)
    public Map updateTeacherInfo(@RequestBody TeacherInfoRequest teacherInfoRequest) {
        Map<String, Object> respMap = new HashMap<>();

        StatusResponse statusResponse = verifyAdmin();
        respMap.put("statusResp", statusResponse);
        if (statusResponse.getCode().equals("-2")){
            return respMap;
        }

        //直接更新教师的基本信息 -- 不允许修改用户名 -- 唯一标示符
        Teacher t = Teacher.builder().name(teacherInfoRequest.getName()).phone(teacherInfoRequest.getPhone())
                .protitle(teacherInfoRequest.getProtitle()).teacherId(teacherInfoRequest.getTeacherId())
                .sex(teacherInfoRequest.getSex()).build();
        if (teacherService.selectByPrimaryKey(teacherInfoRequest.getTeacherId()) == null) {
            respMap.put("statusResp", StatusResponse.error("-1", "用户不存在"));
            return respMap;
        }

        teacherService.updateByPrimaryKey(t);

        //返回状态码和更新后的基本信息
        Teacher teacher = teacherService.selectByPrimaryKey(t.getTeacherId());

        respMap.put("teacher", teacher);
        return respMap;
    }

    /**
     * 教师详情里 更新一条授课信息-- 根据tc -id
     * 身份和课时数可更改
     */
    // + admin权限
    @RequestMapping(value = "/teacher/tcinfo/update", method = RequestMethod.POST)
    public Map updateTeacherTcInfo(@RequestBody TeacherTcInfoRequest teacherTcInfoRequest) {

        Map<String, Object> respMap = new HashMap<>();

        // admin权限验证
        StatusResponse statusResponse = verifyAdmin();
        respMap.put("statusResp", statusResponse);
        if (statusResponse.getCode().equals("-2")){
            return respMap;
        }

        /**
         * tcid唯一
         * 要求课程不能修改，如果要修改就删除这一条记录再重新添加一条
         */
        String tcid = teacherTcInfoRequest.getTcId();
        String tearcherId = tcService.selectByPrimaryKey(tcid).getTeacherId();

        String newTeaIdentity = teacherTcInfoRequest.getTeaIdentity();
        Integer newTeachHour = teacherTcInfoRequest.getTeachHour();

        respMap.put("statusResp", updateTcInfo(tcid, newTeaIdentity, newTeachHour));

        //更新后的该教师的授课记录列表
        List<Tc> tcList = tcService.selectByTeacherId(tearcherId);
        respMap.put("tcList", tcList);
        return respMap;
    }


    /**
     * 教师详情里 删除一条授课信息-- 根据tc -id
     */
    // + admin权限
    @RequestMapping(value = "/teacher/tcinfo/delete", method = RequestMethod.POST)
    public Map deleteTeacherTcInfo(@RequestParam(value = "tcid") String tcid) {
        Map<String, Object> respMap = new HashMap<>();
        // admin权限验证
        StatusResponse statusResponse = verifyAdmin();
        respMap.put("statusResp", statusResponse);
        if (statusResponse.getCode().equals("-2")){
            return respMap;
        }

        Tc tc = tcService.selectByPrimaryKey(tcid);
        if (tc == null){
            respMap.put("statusResp", StatusResponse.error("-1", "授课记录不存在"));
            return respMap;
        }

        String teacherid = tc.getTeacherId();
        int i = tcService.deleteByPrimaryKey(tcid);
        if (i == 0) {
            respMap.put("statusResp", StatusResponse.error("-1", "delete error"));
            return respMap;
        }
        respMap.put("tcList", tcService.selectByTeacherId(teacherid));

        //返回状态 和 授课列表
        return respMap;
    }

    /**
     * 新增一个教师的基本信息。
     * 编辑该教师基本信息以及授课信息
     * 从系统中已有的课程列表中选择课程
     * 设置主讲\团队身份以及课时数
     */

    /**
     * 管理员 新增一个教师的基本信息
     * 按照教师注册的信息来（用户名、密码、姓名、性别、职位、电话、用户类型）
     * TeacherSignUpRequest
     *
     * @return
     */
    // + admin权限
    @RequestMapping(value = "/teacher/info/add", method = RequestMethod.POST)
    public Map teacherInfoAdd(@RequestBody TeacherSignUpRequest teacherSignUpRequest) {
        Map<String, Object> respMap = new HashMap<>();

        // admin权限验证
        // 注意这里与其他地方稍稍有些不同
        StatusResponse statusResponse = verifyAdmin();
        if (statusResponse.getCode().equals("-2")){
            respMap.put("statusResp", statusResponse);
            return respMap;
        }

        respMap.put("statusResp", userController.teaSignUp(teacherSignUpRequest));
        return respMap;
    }

    /**
     * 为指定教师新增一条授课信息
     */
    // + admin权限
    @RequestMapping(value = "/teacher/tcinfo/add", method = RequestMethod.POST)
    public Map teacherTcInfoAdd(@RequestBody TeacherTcInfoAddRequest trequest) {
        Map<String, Object> respMap = new HashMap<>();

        // admin权限验证
        // 注意这里与其他地方稍稍有些不同
        StatusResponse statusResponse = verifyAdmin();
        if (statusResponse.getCode().equals("-2")){
            respMap.put("statusResp", statusResponse);
            return respMap;
        }

        //在增加教师时候增加授课信息，教师指定，课程指定

        //为了保证主码不重复，将teaid（8位） + courseid（6位）拼接起来作为tc（14位）的主码

        String courseId = trequest.getCourseId();
        String tearcherId = trequest.getTeacherId();
        String tcid = tearcherId + courseId;

        /**
         * 检查冲突
         */

        //检查是否存在这样一门课程，即检查courseid是否在course 表中存在
        if (courseService.selectByPrimaryKey(courseId) == null) {
            respMap.put("statusResp", StatusResponse.error("-1", "课程不存在，请从已有课程列表里选择课程。"));
            return respMap;
        }

        //检查是否已经存的在这样tc关系
        if (tcService.selectByPrimaryKey(tcid) != null) {
            respMap.put("statusResp", StatusResponse.error("-1", "已经存在这样的教师授课记录，不能再添加"));
            return respMap;
        }

        String newTeaIdentity = trequest.getTeaIdentity();
        Integer newTeachHour = trequest.getTeachHour();

        //1检查主讲教师的冲突（如果进行了更改）
        if (!passTeacherIdentityCheck(courseId, tearcherId, newTeaIdentity)) {
            respMap.put("statusResp", StatusResponse.error("-1", "教师身份冲突，一门课程只有一个主讲教师"));
            return respMap;
        }

        //2检查课时数的冲突（如果进行了更改
        if (!passCourseHourCheck(courseId, 0, newTeachHour)) {
            respMap.put("statusResp", StatusResponse.error("-1", "课时数冲突"));
            return respMap;
        }

        //新的tc记录
        Tc newerTc = Tc.builder().tcId(tcid).courseId(courseId)
                .teacherId(tearcherId).teachHour(newTeachHour)
                .teaIdentity(newTeaIdentity).build();

        //更新记录
        tcService.insert(newerTc);

        respMap.put("statusResp", StatusResponse.ok());
        respMap.put("tc", newerTc);
        return respMap;
    }


    /**
     * 课程管理
     */

    /**
     * 分页列表显示课程信息
     * 课程 ID,课程名，开设学期，总课时数，主讲教师
     */
    // + admin权限
    @RequestMapping(value = "/course/info/list", method = RequestMethod.POST)
    public Map courseInfoList() {
        Map<String, Object> respMap = new HashMap<>();

        // admin权限验证
        StatusResponse statusResponse = verifyAdmin();
        respMap.put("statusResp", statusResponse);
        if (statusResponse.getCode().equals("-2")){
            return respMap;
        }

        List<Course> courseList = courseService.selectByPage(RequestUtils.getPage(request), RequestUtils.getPageSize(request));

        List<CourseMajorTeaResponse> cmtList = new ArrayList<>();

        //对于每一门课程，找到对应的主讲教师id和name
        for (Course c : courseList) {
            //对于一个课程，找到对应的主讲教师 的目录
            Tc tc = tcService.selectContainMajorTeacherByCourseId(c.getCourseId());
            CourseMajorTeaResponse cmt = CourseMajorTeaResponse.builder().courseHour(c.getCourseHour())
                    .courseId(c.getCourseId()).courseName(c.getCourseName()).term(c.getTerm())
                    .mteacherId(tc != null ? tc.getTeacherId() : "")
                    .mteacherName(tc != null ? teacherService.selectByPrimaryKey(tc.getTeacherId()).getName() : "")
                    .build();
            cmtList.add(cmt);
        }

        respMap.put("courseMajorTeaList", cmtList);
        return respMap;
    }

    /**
     * 课程详情  courseid
     * 课程基本信息以及授课教师信息(教师 ID，姓名，身份(主讲教师或者团队教师)，授课学时)
     * 只能编辑该课程基本信息，不能编辑教师授课信息。
     */
    // + admin权限
    @RequestMapping(value = "/course/info/get", method = RequestMethod.POST)
    public Map getCourseInfo(@RequestParam(value = "courseid") String courseid) {
        Map<String, Object> respMap = new HashMap<>();

        // admin权限验证
        StatusResponse statusResponse = verifyAdmin();
        respMap.put("statusResp", statusResponse);
        if (statusResponse.getCode().equals("-2")){
            return respMap;
        }

        //查课程表 -- 找到课程基本信息
        Course course = courseService.selectByPrimaryKey(courseid);
        respMap.put("course", course);

        //根据授课表
        //查tc表 -- 找到教师授课信息
        List<Tc> tcList = tcService.selectByCourseId(courseid);
        List<CourseTeachInfo> ctiList = new ArrayList<>();
        for (Tc tc : tcList) {
            CourseTeachInfo cti = CourseTeachInfo.builder().teacherId(tc.getTeacherId())
                    .teacherName(teacherService.selectByPrimaryKey(tc.getTeacherId()).getName())
                    .teachHour(tc.getTeachHour()).teaIdentity(tc.getTeaIdentity()).build();
            ctiList.add(cti);
        }
        respMap.put("ctiList", ctiList);
        return respMap;
    }

    /**
     * 课程详情中：更新该课程基本信息
     */
    // + admin 权限
    @RequestMapping(value = "/course/info/update", method = RequestMethod.POST)
    public Map updateCourseInfo(@RequestBody CourseInfoRequest courseInfoRequest) {
        //更新课程的基本信息 -- 不允许修改课程号 -- 唯一标示符
        Map<String, Object> respMap = new HashMap<>();
        // admin权限验证，put放在if里面
        StatusResponse statusResponse = verifyAdmin();
        if (statusResponse.getCode().equals("-2")){
            respMap.put("statusResp", statusResponse);
            return respMap;
        }

        String courseId = courseInfoRequest.getCourseId();
        Integer courseHour = courseInfoRequest.getCourseHour();
        Course c = Course.builder().courseHour(courseInfoRequest.getCourseHour())
                .courseId(courseId).term(courseInfoRequest.getTerm())
                .courseName(courseInfoRequest.getCourseName()).build();

        //检查课时冲突
        Integer nowAllTeachHour = tcService.getNowTeachHourByCourseId(courseId);
        if (courseHour < nowAllTeachHour) {
            respMap.put("statusResp", StatusResponse.error("-1", "课时数冲突"));
            return respMap;
        }

        //检查之后，更新课程基本信息
        courseService.updateByPrimaryKey(c);
        respMap.put("statusResp", StatusResponse.ok());
        return respMap;
    }

    /**
     * 新增课程页面
     */

    /**
     * 新增一门课程的基本信息
     */
    // + admin 权限
    @RequestMapping(value = "/course/info/add", method = RequestMethod.POST)
    public Map courseInfoAdd(@RequestBody CourseInfoAddRequest courseInfoAddRequest) {

        Map<String, Object> respMap = new HashMap<>();

        // admin权限验证，put放在if里面
        StatusResponse statusResponse = verifyAdmin();
        if (statusResponse.getCode().equals("-2")){
            respMap.put("statusResp", statusResponse);
            return respMap;
        }

        String courseid = courseInfoAddRequest.getCourseId();   /*暂且 6 位*/

        //课程号已经存在
        if (courseService.selectByPrimaryKey(courseid) != null) {
            respMap.put("statusResp", StatusResponse.error("-1", "course id has existed."));
            return respMap;
        }

        Course c = Course.builder().courseId(courseid).courseHour(courseInfoAddRequest.getCourseHour())
                .courseName(courseInfoAddRequest.getCourseName()).term(courseInfoAddRequest.getTerm()).build();
        if (courseService.insert(c) > 0) {
            respMap.put("statusResp", StatusResponse.ok());
        } else {
            respMap.put("statusResp", StatusResponse.error("-1", "异常，请再试一次"));
            return respMap;
        }

        respMap.put("course", c);
        return respMap;
    }

    /**
     * 为新增加的课程指定主讲教师
     */
    // + admin 权限
    @RequestMapping(value = "/course/info/addmt", method = RequestMethod.POST)
    public Map courseInfoAddMTea(@RequestParam(value = "courseid") String courseid,
                                 @RequestParam(value = "teacherid") String teacherid,
                                 @RequestParam(value = "teachHour") int teachHour) {
        Map<String, Object> respMap = new HashMap<>();

        // admin权限验证，put放在if里面
        StatusResponse statusResponse = verifyAdmin();
        if (statusResponse.getCode().equals("-2")){
            respMap.put("statusResp", statusResponse);
            return respMap;
        }

        //只需要验证课时数冲突
        if (passCourseHourCheck(courseid, 0, teachHour)){
            String tcid = teacherid + courseid;
            Tc tc = Tc.builder().tcId(tcid).courseId(courseid).teacherId(teacherid).
                    teaIdentity("主讲").teachHour(teachHour).build();
            //增加一条tc记录
            int i = tcService.insert(tc);
            if (i == 0) {
                respMap.put("statusResp", StatusResponse.error("-1", "异常，请再试一次"));
                return respMap;
            }
            respMap.put("tc", tc);
        }else {
            respMap.put("statusResp", StatusResponse.error("-1", "课时数冲突"));
            return respMap;
        }

        respMap.put("statusResp", StatusResponse.ok());

        return respMap;
    }


    /**
     * 得到数据库中现有的课程列表 -- 选择现有的课程
     */
    // + admin 权限
    @RequestMapping(value = "/course/info/all", method = RequestMethod.GET)
    public Map getCourseList() {
        Map<String, Object> respMap = new HashMap<>();
        // admin权限验证，put放在if外
        StatusResponse statusResponse = verifyAdmin();
        respMap.put("statusResp", statusResponse);
        if (statusResponse.getCode().equals("-2")){
            return respMap;
        }
        List<Course> cListAll = courseService.selectAll();
        respMap.put("cListAll", cListAll);
        return respMap;
    }

    /**
     * 得到数据库中现有的教师列表 -- 选择现有的教师
     */
    // + admin 权限
    @RequestMapping(value = "/teacher/info/all", method = RequestMethod.GET)
    public Map getTeacherList() {
        Map<String, Object> respMap = new HashMap<>();
        // admin权限验证，put放在if外
        StatusResponse statusResponse = verifyAdmin();
        respMap.put("statusResp", statusResponse);
        if (statusResponse.getCode().equals("-2")){
            return respMap;
        }
        List<Teacher> tListAll = teacherService.selectAll();
        respMap.put("tListAll", tListAll);
        return respMap;
    }

    /**
     * 检查冲突1检查主讲教师的冲突
     * 更新和添加都能用 = =
     */
    public boolean passTeacherIdentityCheck(String courseId, String teacherId, String newTeaIdentity) {
        //只有新身份为主讲的时候，才需要check
        if (newTeaIdentity.contains("主讲")) {
            //冲突
            //找到数据库中现有的tc记录，根据课程id
            List<Tc> tcList = tcService.selectByCourseId(courseId);

            for (Tc tc : tcList) {
                //如果是其他老师主讲
                if ((!tc.getTeacherId().equals(teacherId)) && tc.getTeaIdentity().contains("主讲")) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 检查冲突2检查课时数的冲突（如果进行了更改）
     *
     * @param courseId
     * @param oldTeachHour
     * @param newTeachHour
     * @return
     */
    public boolean passCourseHourCheck(String courseId, Integer oldTeachHour, Integer newTeachHour) {
        if (tcService.getNowTeachHourByCourseId(courseId) - oldTeachHour + newTeachHour
                > courseService.selectByPrimaryKey(courseId).getCourseHour()) {
            //课时有冲突
            return false;
        }
        return true;
    }


    public StatusResponse updateTcInfo(String tcid, String newTeaIdentity, Integer newTeachHour) {
        Tc oldTc = tcService.selectByPrimaryKey(tcid);

        if (oldTc == null) {
            return StatusResponse.error("-1", "illegal operation.");
        }

        String tearcherId = oldTc.getTeacherId();
        String courseId = oldTc.getCourseId();

        String oldTeaIdentity = oldTc.getTeaIdentity();

        Integer oldTeachHour = oldTc.getTeachHour();

        //新的tc记录
        Tc newerTc = Tc.builder().tcId(tcid).courseId(courseId)
                .teacherId(tearcherId).teachHour(newTeachHour)
                .teaIdentity(newTeaIdentity).build();

        /**
         * 检查冲突
         */
        //要求是同一门课程
        //1检查主讲教师的冲突（如果进行了更改）
        if (!oldTeaIdentity.equals(newTeaIdentity)) {
            if (!passTeacherIdentityCheck(courseId, tearcherId, newTeaIdentity)) {
                return StatusResponse.error("-1", "教师身份冲突，一门课程只有一个主讲教师");
            }
        }

        //2检查课时数的冲突（如果进行了更改）
        if (!oldTeachHour.equals(newTeachHour)) {
            //同一门课程下 更新授课学时
            if (!passCourseHourCheck(courseId, oldTeachHour, newTeachHour)) {
                //课时有冲突
                return StatusResponse.error("-1", "课时数冲突");
            }
        }

        //更新记录
        tcService.updateByPrimaryKey(newerTc);
        return StatusResponse.ok();  //statusResp
    }

    //验证管理员权限
    private StatusResponse verifyAdmin(){
        String loginId = RequestUtils.getLoginId(request);
        if (!VerifyAuthorityUtils.verifyAdminRole(loginId, userService)){
            return StatusResponse.error("-2", "权限不够，要求管理员权限。");
        }
        return StatusResponse.ok();
    }
}
