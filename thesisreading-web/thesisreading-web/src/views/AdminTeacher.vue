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
        <div id="admin-teacher">
          <!-- 分页列表显示 -->
          <el-row>
            <el-col :span="20">
              <el-breadcrumb separator-class="el-icon-arrow-right" style>
                <el-breadcrumb-item>教师管理</el-breadcrumb-item>
                <el-breadcrumb-item>教师列表</el-breadcrumb-item>
              </el-breadcrumb>
            </el-col>
            <el-col :span="4">
              <!-- 新增一位教师 -->
              <div style="float:right;">
                <el-button
                  icon="el-icon-plus"
                  :circle="true"
                  type="mini"
                  @click="function(){addTeacherVisible=true;addTeacher()}"
                ></el-button>
                <!-- Form  增加教师信息-->
                <el-dialog title="新增教师" :visible.sync="addTeacherVisible">
                  <el-form
                    :model="addTeacherForm"
                    :rules="addTeacherRules"
                    ref="addTeacherForm"
                    label-width="80px"
                  >
                    <!-- 教师基本信息，与教师注册相同 -->
                    <el-divider>教师基本信息</el-divider>
                    <el-form-item label="教师号" prop="teaid" :label-width="formLabelWidth">
                      <el-input type="number" v-model="addTeacherForm.teaid"></el-input>
                    </el-form-item>
                    <el-form-item label="姓名" prop="teaname" :label-width="formLabelWidth">
                      <el-input v-model="addTeacherForm.teaname"></el-input>
                    </el-form-item>
                    <el-form-item label="密码" prop="teapwd" required :label-width="formLabelWidth">
                      <el-input type="password" v-model="addTeacherForm.teapwd"></el-input>
                    </el-form-item>
                    <el-form-item label="性别" prop="gender" :label-width="formLabelWidth">
                      <el-radio-group v-model="addTeacherForm.gender">
                        <el-radio label="男"></el-radio>
                        <el-radio label="女"></el-radio>
                      </el-radio-group>
                    </el-form-item>
                    <el-form-item label="用户类型" prop="role" :label-width="formLabelWidth">
                      <el-select
                        v-model="addTeacherForm.role"
                        placeholder="请选择用户类型"
                      >
                        <el-option label="管理员" :value="0"></el-option>
                        <el-option label="授课教师" :value="1"></el-option>
                      </el-select>
                    </el-form-item>
                    <el-form-item label="教师职称" prop="protitle" :label-width="formLabelWidth">
                      <el-select
                        v-model="addTeacherForm.protitle"
                        placeholder="请选择教师职称"
                      >
                        <el-option label="助教" value="助教"></el-option>
                        <el-option label="讲师" value="讲师"></el-option>
                        <el-option label="副教授" value="副教授"></el-option>
                        <el-option label="教授" value="教授"></el-option>
                      </el-select>
                    </el-form-item>
                    <el-form-item label="手机号" prop="phone" :label-width="formLabelWidth">
                      <el-input v-model="addTeacherForm.phone"></el-input>
                    </el-form-item>
                    <!-- 以下是授课信息 -->
                    <!-- 从已经存在的课程列表中选择教师 -->
                    <el-divider>授课信息</el-divider>
                    <el-form-item label="课程号" :label-width="formLabelWidth">
                      <el-input v-model="addTeacherForm.courseId" readonly></el-input>
                    </el-form-item>
                    <el-form-item label="课程" :label-width="formLabelWidth" prop="courseId">
                      <el-select
                        v-model="addTeacherForm.courseId"
                        placeholder="请选择所授课程"
                      >
                        <el-option
                          v-for="item in courseList"
                          :key="item.courseId"
                          :label="item.courseName"
                          :value="item.courseId"
                        ></el-option>
                      </el-select>
                    </el-form-item>
                    <el-form-item label="授课身份" prop="teaIdentity" :label-width="formLabelWidth">
                      <el-select
                        v-model="addTeacherForm.teaIdentity"
                        placeholder="请选择教师职称"
                      >
                        <el-option label="主讲教师" value="主讲"></el-option>
                        <el-option label="团队教师" value="团队"></el-option>
                      </el-select>
                    </el-form-item>
                    <el-form-item label="授课学时" :label-width="formLabelWidth" prop="teachHour">
                      <el-input-number
                        v-model="addTeacherForm.teachHour"
                        @change="handleChangeTeaHour"
                        :min="1"
                        :max="128"
                        :step="1"
                        :step-strictly="true"
                      ></el-input-number>
                    </el-form-item>
                  </el-form>

                  <div slot="footer" class="dialog-footer">
                    <el-button
                      @click="function(){addTeacherVisible = false; cancelAddTeacher();}"
                    >取 消</el-button>
                    <el-button
                      type="primary"
                      @click="function(){submitAddTeacher('addTeacherForm');}"
                    >确 定</el-button>
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
              <el-table-column sortable prop="teacherId" label="教师号" width="auto"></el-table-column>
              <el-table-column prop="name" label="姓名" width="auto"></el-table-column>
              <el-table-column prop="sex" label="性别" width="auto"></el-table-column>
              <el-table-column prop="protitle" label="职位" width="auto"></el-table-column>
              <el-table-column prop="phone" label="联系方式" width="auto"></el-table-column>
              <el-table-column prop="info" label="详细信息" width="auto">
                <template slot-scope="scope">
                  <el-button
                    @click="function(){dialogTableVisible = true, handleClick(scope.row)}"
                    type="text"
                    size="small"
                  >查看</el-button>
                  <!-- Table -->
                  <el-dialog title="教师详细信息" :visible.sync="dialogTableVisible" width="80%">
                    <div style="text-align: center">
                      <h3>教师基本信息</h3>
                      <el-table :data="teacherInfo">
                        <el-table-column sortable property="teacherId" label="教师号" width="150"></el-table-column>
                        <el-table-column property="name" label="姓名" width="150"></el-table-column>
                        <el-table-column property="sex" label="性别" width="150"></el-table-column>
                        <el-table-column property="protitle" label="职位" width="auto"></el-table-column>
                        <el-table-column property="phone" label="联系方式" width="auto"></el-table-column>
                      </el-table>
                    </div>
                    <div style="text-align: center">
                      <h3>授课信息</h3>
                      <el-table :data="tcRespList">
                        <el-table-column sortable property="tcId" label="授课编号" width="150"></el-table-column>
                        <el-table-column property="courseId" label="课程号" width="150"></el-table-column>
                        <el-table-column property="courseName" label="课程名" width="150"></el-table-column>
                        <el-table-column property="teaIdentity" label="教师身份" width="150"></el-table-column>
                        <el-table-column property="teachHour" label="授课学时" width="auto"></el-table-column>
                      </el-table>
                    </div>
                  </el-dialog>
                </template>
              </el-table-column>

              <el-table-column prop="edit" label="基本信息" width="auto">
                <template slot-scope="scope">
                  <el-button
                    @click="function(){dialogFormVisible = true, handleEdit(scope.row)}"
                    type="text"
                    size="small"
                  >编辑</el-button>
                  <!-- Form -->
                  <el-dialog title="修改教师信息" :visible.sync="dialogFormVisible" center>
                    <el-form :model="form" :rules="rules" ref="form">
                      <el-divider>教师基本信息</el-divider>
                      <el-form-item label="教师姓名" :label-width="formLabelWidth" prop="teacherName">
                        <el-input v-model="form.teacherName" autocomplete="off"></el-input>
                      </el-form-item>
                      <el-form-item label="性别" prop="gender" :label-width="formLabelWidth">
                        <el-radio-group v-model="form.gender">
                          <el-radio label="男"></el-radio>
                          <el-radio label="女"></el-radio>
                        </el-radio-group>
                      </el-form-item>

                      <el-form-item label="教师职称" prop="protitle" :label-width="formLabelWidth">
                        <el-select
                          v-model="form.protitle"
                          placeholder="请选择教师职称"
                        >
                          <el-option label="助教" value="助教"></el-option>
                          <el-option label="讲师" value="讲师"></el-option>
                          <el-option label="副教授" value="副教授"></el-option>
                          <el-option label="教授" value="教授"></el-option>
                        </el-select>
                      </el-form-item>
                      <el-form-item label="手机号" prop="phone" :label-width="formLabelWidth">
                        <el-input v-model="form.phone"></el-input>
                      </el-form-item>
                    </el-form>
                    <span slot="footer" class="dialog-footer">
                      <el-button @click="function(){dialogFormVisible = false, cancelEdit()}">取 消</el-button>
                      <el-button type="primary" @click="function(){submitEdit('form')}">确 定</el-button>
                    </span>
                  </el-dialog>
                </template>
              </el-table-column>

              <el-table-column prop="editTc" label="授课信息" width="auto">
                <template slot-scope="scope">
                  <el-button
                    @click="function(){dialogTcFormVisible = true, handleEditTc(scope.row)}"
                    type="text"
                    size="small"
                  >编辑</el-button>
                  <!-- Form -->
                  <el-dialog title="修改授课信息" :visible.sync="dialogTcFormVisible" center>
                    <el-form :model="tcForm" :rules="tcRules" ref="tcForm">
                      <el-divider>授课信息</el-divider>

                      <el-form-item label="授课编号" :label-width="formLabelWidth" prop="term">
                        <el-input v-model="tcForm.tcId" readonly></el-input>
                      </el-form-item>

                      <el-form-item label="授课名称" :label-width="formLabelWidth" prop="index">
                        <el-select v-model="tcForm.index" placeholder="请选择所授课程" @change="onChange">
                          <el-option
                            v-for="(item,index) in tcRespList"
                            :key="item.tcId"
                            :label="item.courseName"
                            :value="index"
                          ></el-option>
                        </el-select>
                      </el-form-item>

                      <el-form-item label="授课身份" :label-width="formLabelWidth" prop="teaIdentity">
                        <el-select v-model="tcForm.teaIdentity" placeholder="请选择教师身份">
                          <el-option label="团队教师" value="团队"></el-option>
                          <el-option label="主讲教师" value="主讲"></el-option>
                        </el-select>
                      </el-form-item>
                      <el-form-item label="授课学时" :label-width="formLabelWidth" prop="teachHour">
                        <el-input-number
                          v-model="tcForm.teachHour"
                          @change="handleChangeTeaHour"
                          :min="1"
                          :max="128"
                          :step="1"
                          :step-strictly="true"
                        ></el-input-number>
                      </el-form-item>
                    </el-form>
                    <!-- 没修改 -->
                    <span slot="footer" class="dialog-footer">
                      <el-button
                        @click="function(){dialogTcFormVisible = false, cancelEditTc()}"
                      >取 消</el-button>
                      <el-button type="primary" @click="submitEditTc('tcForm')">确 定</el-button>
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
import { version } from "punycode";

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
  name: "AdminTeacher",
  components: {},
  data() {
    return {
      activeIndex: "/admin/teacher",
      pageSize: 5,//每页数据条数
      currentPage: 1,//当前页码
      total:300,//总条数
      Token: window.localStorage["Token"],
      formLabelWidth: "100px",
      addTeacherVisible: false,
      list: [],
      tcRespList: [],
      courseList: [],
      teacherInfo: [],
      dialogTableVisible: false,
      dialogFormVisible: false,
      dialogTcFormVisible: false,
      addTeacherForm: {
        teaid: "",
        teaname: "",
        teapwd: "",
        gender: "",
        role: "",
        protitle: "",
        phone: "",
        courseId: "",
        teaIdentity: "",
        teachHour: 1
      },
      addTeacherRules: {
        teaid: [
          { required: true, message: "请输入教师号", trigger: "blur" },
          { min: 7, max: 9, message: "长度在 7 到 9 个数字", trigger: "blur" }
        ],
        teaname: [
          { required: true, message: "请输入名字", trigger: "blur" },
          { min: 2, max: 10, message: "长度在2-10个字符", trigger: "blur" }
        ],
        teapwd: [{ required: true, message: "请输入密码", trigger: "blur" }],
        gender: [{ required: true, message: "请选择性别", trigger: "change" }],
        role: [
          { required: true, message: "请选择用户类型", trigger: "change" }
        ],
        protitle: [
          { required: true, message: "请选择教师职称", trigger: "change" }
        ],
        phone: [{ required: true, message: "请输入手机号", trigger: "blur" }],
        courseId: [
          { required: true, message: "请选择课程", trigger: "change" }
        ],
        teaIdentity: [
          { required: true, message: "请选择授课身份", trigger: "change" }
        ],
        teachHour: [
          { required: true, message: "请选择授课学时", trigger: "change" }
        ]
      },
      // 修改教师信息
      form: {
        teacherId: "",
        teacherName: "",
        gender: "",
        protitle: "",
        phone: ""
      },
      rules: {
        teacherName: [
          { required: true, message: "请输入名字", trigger: "blur" },
          { min: 2, max: 10, message: "长度在2-10个字符", trigger: "blur" }
        ],
        gender: [{ required: true, message: "请选择性别", trigger: "change" }],
        role: [
          { required: true, message: "请选择用户类型", trigger: "change" }
        ],
        protitle: [
          { required: true, message: "请选择教师职称", trigger: "change" }
        ],
        phone: [{ required: true, message: "请输入手机号", trigger: "change" }]
      },
      // 修改教师授课信息
      tcForm: {
        tcId: "",
        index: "",
        courseId: "",
        teaIdentity: "",
        teachHour: 1
      },
      tcRules: {
        index: [{ required: true, message: "请选择课程", trigger: "change" }],
        teaIdentity: [
          { required: true, message: "请选择授课身份", trigger: "change" }
        ],
        teachHour: [
          { required: true, message: "请选择授课学时", trigger: "change" }
        ]
      }
    };
  },

  // 加载即显示第一页
  created() {
    this.convert();
  },

  methods: {
    onChange(value) {
      console.log(this.tcRespList[value]);
      this.tcForm.teaIdentity = this.tcRespList[value].teaIdentity;
      this.tcForm.tcId = this.tcRespList[value].tcId;
      this.tcForm.teachHour = this.tcRespList[value].teachHour;
    },

    // 分页
    convert() {
      console.log(this.currentPage);
      console.log(this.pageSize);

      axios
        .post(
          "http://localhost:8080/admin/teacher/info/list?page=" +
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
              this.list = success.data.teaList;
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

    // 新增教师
    addTeacher() {
      // Ajax请求课程列表
      axios
        .post(
          "http://localhost:8080/admin/course/info/all",
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
              this.courseList = success.data.cListAll;
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

    //确认新增教师
    submitAddTeacher(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          this.$confirm("确认教师信息添加？", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          })
            .then(() => {
              // Ajax请求新增教师基本信息
              axios
                .post(
                  "http://localhost:8080/admin/teacher/info/add",
                  {
                    userid: this.addTeacherForm.teaid,
                    teachername: this.addTeacherForm.teaname,
                    password: this.addTeacherForm.teapwd,
                    role: this.addTeacherForm.role,
                    sex: this.addTeacherForm.gender,
                    protitle: this.addTeacherForm.protitle,
                    phone: this.addTeacherForm.phone
                  },
                  {
                    headers: {
                      Token: window.localStorage["Token"]
                    }
                  }
                )
                .then(success => {
                  if (success.data.statusResp.code == "1") {
                    this.$message({
                      type: "success",
                      message: "成功添加教师信息"
                    });
                    //  新增教师之后  Ajax请求新增教师授课信息
                    axios
                      .post(
                        "http://localhost:8080/admin/teacher/tcinfo/add",
                        {
                          courseId: this.addTeacherForm.courseId,
                          teacherId: this.addTeacherForm.teaid,
                          teaIdentity: this.addTeacherForm.teaIdentity,
                          teachHour: this.addTeacherForm.teachHour
                        },
                        {
                          headers: {
                            Token: window.localStorage["Token"]
                          }
                        }
                      )
                      .then(success => {
                        if (success.data.statusResp.code == "1") {
                          this.$message({
                            type: "success",
                            message: "成功添加授课信息"
                          });
                        } else {
                          this.$message({
                            type: "error",
                            message: success.data.statusResp.err
                          });
                        }
                        this.addTeacherVisible = false;
                      });
                  } else {
                    this.$message({
                      type: "error",
                      message: success.data.statusResp.err
                    });
                  }
                });
            })
            .catch(() => {
              this.$message({
                type: "info",
                message: "取消注册"
              });
              this.addTeacherVisible = false;
            });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },

    //取消新增教师
    cancelAddTeacher() {
      this.$message({
        type: "info",
        message: "放弃新增教师"
      });
    },

    //点击编辑教师信息
    handleEdit(row) {
      this.form.teacherId = row.teacherId;
      //  Ajax请求 拉数据
      axios
        .post(
          "http://localhost:8080/admin/teacher/info/get?teacherid=" +
            this.form.teacherId,
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
              this.form.teacherName = success.data.teacher.name;
              this.form.gender = success.data.teacher.sex;
              this.form.protitle = success.data.teacher.protitle;
              this.form.phone = success.data.teacher.phone;
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

    //确认编辑
    submitEdit(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          this.$confirm("确认教师信息更新？", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          })
            .then(() => {
              //发ajax请求，更新教师基本信息
              axios
                .post(
                  "http://localhost:8080/admin/teacher/info/update",
                  {
                    teacherId: this.form.teacherId,
                    name: this.form.teacherName,
                    sex: this.form.gender,
                    protitle: this.form.protitle,
                    phone: this.form.phone
                  },
                  {
                    headers: {
                      Token: window.localStorage["Token"]
                    }
                  }
                )
                .then(
                  success => {
                    if (success.data.statusResp.code == "1") {
                      this.$message({
                        type: "success",
                        message: "更新成功"
                      });
                      this.dialogFormVisible = false;
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
                    this.dialogFormVisible = false;
                  }
                );
            })
            .catch(() => {
              this.$message({
                type: "info",
                message: "取消提交"
              });
            });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    //取消编辑
    cancelEdit() {
      this.$message({
        type: "info",
        message: "放弃修改"
      });
    },

    //点击编辑授课信息
    handleEditTc(row) {
      this.form.teacherId = row.teacherId;
      //  Ajax请求 拉数据
      axios
        .post(
          "http://localhost:8080/admin/teacher/info/get?teacherid=" +
            this.form.teacherId,
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
              this.tcRespList = success.data.tcRespList;
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

    //确认编辑 更新授课信息
    submitEditTc(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          this.$confirm("确认授课信息更新？", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          })
            .then(() => {
              //更新授课信息
              axios
                .post(
                  "http://localhost:8080/admin/teacher/tcinfo/update",
                  {
                    tcId: this.tcForm.tcId,
                    teaIdentity: this.tcForm.teaIdentity,
                    teachHour: this.tcForm.teachHour
                  },
                  {
                    headers: {
                      Token: window.localStorage["Token"]
                    }
                  }
                )
                .then(
                  success => {
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
                      this.dialogTcFormVisible.false;
                    }
                  },
                  err => {
                    console.log(err);
                    this.dialogTcFormVisible = false;
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
    //取消编辑
    cancelEditTc() {
      this.$message({
        type: "info",
        message: "放弃修改"
      });
    },

    // 查看某个教师的详细信息
    handleClick(row) {
      //发ajax请求
      axios
        .post(
          "http://localhost:8080/admin/teacher/info/get?teacherid=" +
            row.teacherId,
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
              this.teacherInfo = [success.data.teacher];
              this.tcRespList = success.data.tcRespList;
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
