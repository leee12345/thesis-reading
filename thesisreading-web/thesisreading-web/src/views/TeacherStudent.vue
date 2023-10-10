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

          <el-menu-item index="/teacher">所授课程</el-menu-item>
          <el-menu-item index="/teacher/paper">论文评论</el-menu-item>

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
        <div id="teacher-student-indiv">
          <!-- 学生选题查看页面，点击某个学生，可以查看
          以及该学生为该主题上传的论文列表等-->
          <el-row>
            <el-col :span="20">
              <el-breadcrumb separator-class="el-icon-arrow-right" style>
                <el-breadcrumb-item>学生管理</el-breadcrumb-item>
                <el-breadcrumb-item>查看选题</el-breadcrumb-item>
              </el-breadcrumb>
            </el-col>
          </el-row>
          <el-row>
            <el-divider></el-divider>
          </el-row>
          <div style="float:left; margin-bottom:8px; margin-left:10px;">
            <el-button icon="el-icon-back"  :circle="true" type="mini" @click="clickBack"></el-button>
          </div>

          <!--  该学生的选题信息
          （主题ID，主题名，关键词，指导教师姓名）-->
          <div class="block" @click="convert">
            <el-table :data="stutopicList" border style="width:100%">
              <el-table-column sortable prop="stutopicId" label="学生选题号"></el-table-column>
              <el-table-column sortable prop="topicId" label="主题号"></el-table-column>
              <el-table-column prop="topicName" label="主题"></el-table-column>
              <el-table-column prop="teacherName" label="教师"></el-table-column>
              <el-table-column prop="keyword" label="关键字"></el-table-column>
              <el-table-column prop="paper" label="上传论文">
                <template slot-scope="scope">
                  <el-button size="small" type="text" @click="handleClick(scope.row)">查看</el-button>
                  <!-- Table -->
                  <el-dialog title="上传论文列表" :visible.sync="dialogTableVisible" width="80%">
                    <div>
                      <el-table :data="paperList">
                        <el-table-column sortable property="paperId" label="论文号"></el-table-column>
                        <el-table-column property="title" label="题目"></el-table-column>
                        <el-table-column property="author" label="作者"></el-table-column>
                        <el-table-column property="source" label="来源"></el-table-column>
                        <el-table-column property="keyword" label="关键字"></el-table-column>
                        <el-table-column property="abstractText" label="摘要"></el-table-column>
                        <el-table-column property="docLocation" label="物理位置"></el-table-column>
                        <el-table-column sortable property="uploadingTime" label="上传时间"></el-table-column>
                      </el-table>
                    </div>
                  </el-dialog>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </div>
      </el-main>
    </el-container>
  </div>
</template>

<script>
// @ is an alias to /src

import Vue from "vue";
import axios from "axios";
import stuManage from "../components/StuManage.vue";
import topicManage from "../components/TopicManage.vue";
import teaManage from "../components/TeaManage.vue";
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
  name: "TeacherStudent",
  components: {},
  data() {
    return {
      Token: window.localStorage["Token"],
      activeIndex: "/teacher",
      stutopicList: [],
      paperList: [],
      courseId:this.$route.params.courseId,
      dialogTableVisible: false
    };
  },

  // 加载即显示
  created() {
    this.convert();
  },

  methods: {
    convert() {
      // 直接请求
      axios
        .get(
          "http://localhost:8080/teacher/stu/topic/info?studentId=" +
            this.$route.params.studentId+
            "&courseId="+
            this.$route.params.courseId,
          {
            headers: {
              Token: window.localStorage["Token"]
            }
          }
        )
        .then(
          success => {
            if (success.data.statusResp.code == "1") {
              this.stutopicList = success.data.stutopicList;
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
      axios
        .get(
          "http://localhost:8080/teacher/stu/topic/paperlist?studentId=" +
            this.$route.params.studentId +
            "&topicId=" +
            row.topicId,
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
              this.paperList = success.data.paperList;
              this.dialogTableVisible = true;
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

    handleSelect(key, path) {
      console.log(key, path);
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
    },
    clickBack() {
      this.$router.push({ path: "/teacher/course/"+this.courseId});
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
</style>
