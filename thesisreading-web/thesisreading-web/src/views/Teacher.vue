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
        <div id="teacher-course-list">
          <el-row>
            <el-col :span="20">
              <el-breadcrumb separator-class="el-icon-arrow-right" style>
                <el-breadcrumb-item>课程列表</el-breadcrumb-item>
              </el-breadcrumb>
            </el-col>
          </el-row>
          <el-row>
            <!-- 分割线 -->
            <el-divider></el-divider>
          </el-row>

          <!-- 展示列表 -->
          <div class="block">
            <el-table :data="cmtList" border style="width:100%" @row-click="handleClickRow">
              <el-table-column sortable prop="courseId" label="课程号" width="auto"></el-table-column>
              <el-table-column prop="courseName" label="课程名" width="auto"></el-table-column>
              <el-table-column prop="term" label="开设学期" width="auto"></el-table-column>
              <el-table-column sortable prop="courseHour" label="学时数" width="auto"></el-table-column>
              <el-table-column prop="mteacherName" label="主讲教师" width="auto"></el-table-column>
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
  name: "Teacher",
  components: {},

  data() {
    return {
      Token: window.localStorage["Token"],
      activeIndex: "/teacher",
      teacherId: "",
      cmtList: []
    };
  },

  // 加载即显示第一页
  created() {
    this.convert();
  },

  methods: {
    convert() {
      this.teacherId = this.Token;
      axios
        .get(
          "http://localhost:8080/teacher/teach/course/list?teacherId=" +
            this.teacherId,
          {
            headers: {
              Token: window.localStorage["Token"]
            }
          }
        )
        .then(
          success => {
            if (success.data.statusResp.code == "1") {
              this.cmtList = success.data.cmtList;
              console.log(success.data.cmtList);
            } else {
              this.$message({
                type: "error",
                message: success.data.statusResp.err
              });
            }
          },
          err => {}
        );
    },
    handleSelect(key, path) {
      console.log(key, path);
    },

    handleClickRow(row){
      console.log("row", row);
      this.$router.push({path: `/teacher/course/${row.courseId}`});
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
</style>
