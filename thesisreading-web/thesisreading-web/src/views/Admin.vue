<template>
  <div>
    <el-container>
      <el-header style="text-align: right;">
        <el-menu
          :default-active="activeIndex"
          mode="horizontal"
          background-color="#545c64"
          text-color="#fff"
          active-text-color="#409EFF"
          @select="handleSelect"
          :router="true"
        >
          <div class="syslogo" style="float:left;">研究生课程论文选读系统</div>

          <el-menu-item index="/admin">课程管理</el-menu-item>
          <el-menu-item index="/admin/teacher">教师管理</el-menu-item>

          <el-dropdown style="color:#FFF">
            <i class="el-icon-user-solid" style="margin-right: 15px;font-size: 16px;">{{Token}}</i>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item @click.native="clickRegister">注册</el-dropdown-item>
              <el-dropdown-item @click.native="clickLogin">登录</el-dropdown-item>
              <el-dropdown-item @click.native="clickLogout">退出</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </el-menu>
      </el-header>

      <el-main>
        <div id="admin-course">
          <!-- 分页列表显示 -->
          <el-row>
            <el-col :span="20">
              <el-breadcrumb separator-class="el-icon-arrow-right" style>
                <el-breadcrumb-item>课程管理</el-breadcrumb-item>
                <el-breadcrumb-item>课程列表</el-breadcrumb-item>
              </el-breadcrumb>
            </el-col>
            <el-col :span="4">
              <!-- 新增一门课程 -->
              <div style="float:right;">
                <el-button
                  icon="el-icon-plus"
                  :circle="true"
                  type="mini"
                  @click="addCourseVisible=true"
                ></el-button>

                <!-- Form  增加课程信息-->
                <el-dialog title="新增课程" :visible.sync="addCourseVisible">
                  <el-form :model="addCourseForm" :rules="addCourseRules" ref="addCourseForm">
                    <el-form-item label="课程号" :label-width="formLabelWidth" prop="courseId">
                      <el-input v-model="addCourseForm.courseId" autocomplete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="课程名" :label-width="formLabelWidth" prop="courseName">
                      <el-input v-model="addCourseForm.courseName" autocomplete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="开设学期" :label-width="formLabelWidth" prop="term">
                      <el-input v-model="addCourseForm.term" autocomplete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="课时数" :label-width="formLabelWidth" prop="courseHour">
                      <el-input-number v-model="addCourseForm.courseHour" autocomplete="off"></el-input-number>
                    </el-form-item>
                  </el-form>
                  <!-- 内层对话框，为新增课程指定主讲教师 -->
                  <el-dialog
                    width="30%"
                    title="为新增课程指定主讲教师"
                    :visible.sync="addmTeaVisible"
                    append-to-body
                  >
                    <!-- 这里是添加主讲教师的form -->

                    <el-form :model="addmTeaForm" :rules="addmTeaRules" ref="addmTeaForm">
                      <!-- 从已经存在的教师列表中选择教师 -->
                      <el-form-item label="教师姓名" :label-width="formLabelWidth" prop="teacherId">
                        <el-select v-model="addmTeaForm.teacherId" placeholder="请选择主讲教师">
                          <el-option
                            v-for="item in teacherList"
                            :key="item.teacherId"
                            :label="item.name"
                            :value="item.teacherId"
                          ></el-option>
                        </el-select>
                      </el-form-item>
                      <el-form-item label="授课学时" :label-width="formLabelWidth" prop="teachHour">
                        <el-input-number
                          v-model="addmTeaForm.teachHour"
                          @change="handleChangeTeaHour"
                          :min="1"
                          :max="128"
                          :step="1"
                          :step-strictly="true"
                        ></el-input-number>
                      </el-form-item>
                    </el-form>
                    <!-- 内层新增主讲教师的button -->
                    <div slot="footer" class="dialog-footer">
                      <el-button
                        @click="function(){addCourseVisible = false;addmTeaVisible = false;cancelAddmTea();}"
                      >取 消</el-button>
                      <el-button type="primary" @click="submitAddmTea('addmTeaForm');">确 定</el-button>
                    </div>
                  </el-dialog>

                  <!-- 外层新增课程的button -->
                  <div slot="footer" class="dialog-footer">
                    <el-button @click="function(){addCourseVisible = false; cancelAddCourse();}">取 消</el-button>
                    <el-button type="primary" @click="submitAddCourse('addCourseForm');">确 定</el-button>
                  </div>
                </el-dialog>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <!-- 分割线 -->
            <el-divider></el-divider>
          </el-row>

          <!-- 分页列表显示 table -->
          <div class="block" @click="convert">
            <el-table :data="list" border style="width: 100%">
              <el-table-column sortable prop="courseId" label="课程号" width="auto" ></el-table-column>
              <el-table-column prop="courseName" label="课程名" width="auto"></el-table-column>
              <el-table-column prop="term" label="开设学期" width="auto"></el-table-column>
              <el-table-column sortable prop="courseHour" label="学时数" width="auto"></el-table-column>
              <el-table-column prop="mteacherName" label="主讲教师" width="auto"></el-table-column>
              <el-table-column prop="info" label="详细信息" width="auto">
                <template slot-scope="scope">
                  <el-button
                    @click="function(){dialogTableVisible = true, handleClick(scope.row)}"
                    type="text"
                    size="small"
                  >查看</el-button>
                  <!-- Table -->
                  <el-dialog title="课程详细信息" :visible.sync="dialogTableVisible">
                    <div style="text-align: center">
                      <h3>课程基本信息</h3>
                      <el-table :data="courseInfo">
                        <el-table-column sortable property="courseId" label="课程号" width="150"></el-table-column>
                        <el-table-column property="courseName" label="课程名" width="150"></el-table-column>
                        <el-table-column property="term" label="学期" width="150"></el-table-column>
                        <el-table-column sortable property="courseHour" label="学时总数" width="auto"></el-table-column>
                      </el-table>
                    </div>
                    <div style="text-align: center">
                      <h3>授课教师信息</h3>
                      <el-table :data="infoList">
                        <el-table-column sortable property="teacherId" label="教师号" width="150"></el-table-column>
                        <el-table-column property="teacherName" label="教师姓名" width="150"></el-table-column>
                        <el-table-column property="teaIdentity" label="教师身份" width="150"></el-table-column>
                        <el-table-column sortable property="teachHour" label="授课学时"></el-table-column>
                      </el-table>
                    </div>
                  </el-dialog>
                </template>
              </el-table-column>

              <el-table-column prop="edit" label="课程信息" width="auto">
                <template slot-scope="scope">
                  <el-button
                    @click="function(){dialogFormVisible = true, handleEdit(scope.row)}"
                    type="text"
                    size="small"
                  >编辑</el-button>

                  <!-- Form -->
                  <el-dialog title="修改课程信息" :visible.sync="dialogFormVisible" center>
                    <el-form :model="form" :rules="rules" ref="form">
                      <el-form-item label="课程名" :label-width="formLabelWidth" prop="courseName">
                        <el-input v-model="form.courseName" autocomplete="off"></el-input>
                      </el-form-item>
                      <el-form-item label="开设学期" :label-width="formLabelWidth" prop="term">
                        <el-input v-model="form.term" autocomplete="off"></el-input>
                      </el-form-item>
                      <el-form-item label="课时数" :label-width="formLabelWidth" prop="courseHour">
                        <el-input-number v-model="form.courseHour"></el-input-number>
                      </el-form-item>
                    </el-form>
                    <span slot="footer" class="dialog-footer">
                      <el-button @click="function(){dialogFormVisible = false, cancelEdit()}">取 消</el-button>
                      <el-button type="primary" @click="submitEdit('form')">确 定</el-button>
                    </span>
                  </el-dialog>
                </template>
              </el-table-column>
            </el-table>

            <!-- 分页 -->
            <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page="currentPage"
              :page-sizes="[5, 10, 15, 20, 25, 30]"
              :page-size="pageSize"
              layout="total, sizes, prev, pager, next, jumper"
              :total="total">
            </el-pagination>
          </div>
        </div>
      </el-main>
    </el-container>
  </div>
</template>

<script>
// @ is an alias to /src

import navMenu from "../components/Menu.vue";
import Vue from "vue";
import axios from "axios";
import {
  Button,
  Form,
  Input,
  FormItem,
  Col,
  Row,
  Select,
  Menu,
  MenuItem,
  Header,
  Container,
  Main,
  Breadcrumb,
  BreadcrumbItem,
  Divider
} from "element-ui";
import { floralwhite } from "color-name";

Vue.use(Button);
Vue.use(Form);
Vue.use(Input);
Vue.use(FormItem);
Vue.use(Col);
Vue.use(Row);
Vue.use(Menu);
Vue.use(MenuItem);
Vue.use(Select);
Vue.use(Header);
Vue.use(Container);
Vue.use(Breadcrumb);
Vue.use(BreadcrumbItem);
Vue.use(Divider);

export default {
  name: "Admin",
  components: {},

  data() {
    return {
      pageSize: 5,//每页数据条数
      currentPage: 1,//当前页码
      total:300,//总条数
      activeIndex: "/admin",
      list: [],
      infoList: [],
      courseInfo: [],
      dialogTableVisible: false,
      dialogFormVisible: false,
      Token: window.localStorage["Token"],
      formLabelWidth: "100px",
      form: {
        courseId: "",
        courseName: "",
        term: "",
        courseHour: 1
      },
      rules: {
        courseName: [
          { required: true, message: "请输入课程名", trigger: "change" },
          { min: 1, max: 16, message: "长度在1-16个字符", trigger: "change" }
        ],
        term: [
          { required: true, message: "请输入开设学期", trigger: "change" }
        ],
        courseHour: [
          { required: true, message: "请选择课时数", trigger: "change" }
        ]
      },
      addCourseVisible: false,
      addmTeaVisible: false,

      addCourseForm: {
        courseId: "",
        courseName: "",
        term: "",
        courseHour: 1
      },
      addCourseRules: {
        courseId: [
          { required: true, message: "请输入课程号", trigger: "blur" },
          { min: 6, max: 6, message: "长度为 6 个数字", trigger: "blur" }
        ],
        courseName: [
          { required: true, message: "请输入课程名", trigger: "blur" },
          { min: 1, max: 16, message: "长度在1-16个字符", trigger: "blur" }
        ],
        term: [{ required: true, message: "请输入开设学期", trigger: "blur" }],
        courseHour: [
          { required: true, message: "请输入课时总数", trigger: "change" }
        ]
      },
      addmTeaForm: {
        courseId: "",
        teacherId: "",
        teachHour: 1
      },
      addmTeaRules: {
        teacherId: [
          { required: true, message: "请选择主讲教师", trigger: "change" }
        ],
        teachHour: [
          { required: true, message: "请选择授课学时", trigger: "change" }
        ]
      },
      teacherList: []
    };
  },

  // 加载即显示第一页
  created() {
    this.convert();
  },

  methods: {
    convert() {
      console.log(this.currentPage);
      console.log(this.pageSize);

      axios
        .post(
          "http://localhost:8080/admin/course/info/list?page=" +
            this.currentPage +
            "&size=" +
            this.pageSize,
          {
            page: this.currentPage,
            size: this.pageSize
          },
          {
            headers: {
              Token: window.localStorage["Token"]
            }
          }
        )
        .then(
          success => {
            //可以显示列表
            if (success.data.statusResp.code == "1") {
              this.list = success.data.courseMajorTeaList;
            } else {
              this.$message({
                type: "error",
                message: success.data.statusResp.err
              });
            }
          },
          err => {
            console.log(err);
          }
        );
    },
    handleClick(row) {
      console.log("here:", row);
      //发ajax请求
      axios
        .post(
          "http://localhost:8080/admin/course/info/get?courseid=" +
            row.courseId,
          {},
          {
            headers: {
              Token: window.localStorage["Token"]
            }
          }
        )
        .then(
          success => {
            //可以显示列表
            if (success.data.statusResp.code == "1") {
              this.infoList = success.data.ctiList;
              this.courseInfo = [success.data.course];
            } else {
              this.$message({
                type: "error",
                message: success.data.statusResp.err
              });
            }
          },
          err => {
            console.log(err);
          }
        );
    },
    handleEdit(row) {
      console.log("here:", row);
      this.form.courseId = row.courseId;
      this.form.courseName = row.courseName;
      this.form.term = row.term;
      this.form.courseHour = row.courseHour;
    },
    submitEdit(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          this.$confirm("确认课程信息修改？", "提示", {
            type: "warning",
            confirmButtonText: "确定",
            cancelButtonText: "取消"
          })
            .then(() => {
              //发ajax请求
              axios
                .post(
                  "http://localhost:8080/admin/course/info/update",
                  {
                    courseId: this.form.courseId,
                    courseName: this.form.courseName,
                    term: this.form.term,
                    courseHour: this.form.courseHour
                  },
                  {
                    headers: {
                      Token: window.localStorage["Token"]
                    }
                  }
                )
                .then(
                  success => {
                    //更新课程基本信息
                    if (success.data.statusResp.code == "1") {
                      this.$message({
                        type: "success",
                        message: "更新成功"
                      });
                      this.$router.go(0);
                    } else {
                      this.$message({
                        type: "error",
                        message: success.data.statusResp.err
                      });
                      this.dialogFormVisible = false;
                    }
                  },
                  err => {
                    console.log(err);
                  }
                );
            })
            .catch(() => {
              this.$message({
                type: "info",
                message: "取消提交"
              });
            });
        }
      });
    },
    cancelEdit() {
      this.$message({
        type: "info",
        message: "放弃修改"
      });
    },
    cancelAddCourse() {
      this.$message({
        type: "info",
        message: "放弃新增课程"
      });
    },
    submitAddCourse(formName) {
      //首先验证表单
      this.$refs[formName].validate(valid => {
        if (valid) {
          this.$confirm("确定添加课程？", "提示", {
            cancelButtonText: "取消",
            confirmButtonText: "确定",
            type: "info"
          })
            .then(() => {
              //如果确定添加课程，ajax请求，新增课程
              axios
                .post(
                  "http://localhost:8080/admin/course/info/add",
                  {
                    courseId: this.addCourseForm.courseId,
                    courseName: this.addCourseForm.courseName,
                    term: this.addCourseForm.term,
                    courseHour: this.addCourseForm.courseHour
                  },
                  {
                    headers: {
                      Token: window.localStorage["Token"]
                    }
                  }
                )
                .then(
                  success => {
                    //新增课程
                    if (success.data.statusResp.code == "1") {
                      this.$message({
                        type: "success",
                        message: "成功新增课程"
                      });

                      // 请求教师信息，为了之后添加主讲教师
                      axios
                        .post(
                          "http://localhost:8080/admin/teacher/info/all",
                          {},
                          {
                            headers: {
                              Token: window.localStorage["Token"]
                            }
                          }
                        )
                        .then(
                          success => {
                            //请求教师列表
                            if (success.data.statusResp.code == "1") {
                              this.teacherList = success.data.tListAll;
                              console.log(this.teacherList);
                              // 请求成功 -- 显示新增教师的弹窗
                              this.addmTeaVisible = true;
                            } else {
                              this.$message({
                                type: "error",
                                message: success.data.statusResp.err
                              });
                            }
                          },
                          err => {
                            console.log(err);
                          }
                        );
                    } else {
                      this.$message({
                        type: "error",
                        message: success.data.statusResp.err
                      });
                    }
                  },
                  err => {
                    console.log(err);
                  }
                )
                .finally();
            })
            .catch(() => {
              //如果取消了增加课程
              this.$message({
                type: "info",
                message: "取消提交"
              });
            });
        }
      });
    },
    cancelAddmTea() {
      this.$message({
        type: "info",
        message: "放弃指定主讲教师"
      });
      this.$router.go(0);
    },
    submitAddmTea(formName) {
      //courseId
      this.addmTeaForm.courseId = this.addCourseForm.courseId;

      this.$refs[formName].validate(valid => {
        if (valid) {
          this.$confirm("确定添加主讲教师？", "提示", {
            cancelButtonText: "取消",
            confirmButtonText: "确定",
            type: "info"
          })
            .then(() => {
              // ajax请求，指定主讲教师
              axios
                .post(
                  "http://localhost:8080/admin/course/info/addmt?courseid=" +
                    this.addmTeaForm.courseId +
                    "&teacherid=" +
                    this.addmTeaForm.teacherId +
                    "&teachHour=" +
                    this.addmTeaForm.teachHour,
                  {},
                  {
                    headers: {
                      Token: window.localStorage["Token"]
                    }
                  }
                )
                .then(
                  success => {
                    //指定教师
                    if (success.data.statusResp.code == "1") {
                      this.$message({
                        type: "success",
                        message: "成功指定主讲教师"
                      });
                      this.$router.go(0);
                    } else {
                      this.$message({
                        type: "error",
                        message: success.data.statusResp.err
                      });
                    }
                  },
                  err => {
                    console.log(err);
                  }
                );
            })
            .catch(() => {
              // 如果取消了增加主讲教师
              this.$message({
                type: "info",
                message: "取消提交"
              });
            });
        }
      });
    },
    handleChangeTeaHour(val) {
      console.log(val);
    },
    //每页改变的时候出发，跳转到其他页
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.currentPage = 1;
      this.pageSize = val;
      this.convert();
    },
    //当前页改变时触发，跳转到其他页
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.currentPage = val;
      this.convert();
    },
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
    },
    clickRegister() {
      this.$router.push({ path: "/register" });
    },
    clickLogin() {
      this.$router.push({ path: "/" });
    },
    clickLogout() {
      window.localStorage.removeItem("Token");
      this.$message({
        title: "提示",
        type: "info",
        message: "退出成功"
      });
      this.$router.push({ path: "/" });
    }
  }
};
</script>

<style>
.el-header {
  background-color: #545c64;
  color: #ffffff;
  line-height: 58px;
  height: 60px;
}

.register {
  margin: 0%;
  text-align: center;
}

.register-box {
  width: 60%;
  padding: 0;
}
</style>
