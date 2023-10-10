<template>
  <div>
    <el-container>
      <el-header style="text-align: right;">
        <el-menu
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
        <div id="teacher-topic-indiv">
          <!-- 可以查看该老师布置的、
          选取该主题的学生信息、
          以及学生上传的论文列表-->
          <el-row>
            <el-col :span="20">
              <el-breadcrumb separator-class="el-icon-arrow-right" style>
                <el-breadcrumb-item>选题管理</el-breadcrumb-item>
                <el-breadcrumb-item>查看主题</el-breadcrumb-item>
              </el-breadcrumb>
            </el-col>
          </el-row>
          <div>
            <el-tabs v-model="activeName">
              <el-tab-pane label="主题内容" name="topicContent">
                <div style="float:left; margin-bottom:8px; margin-left:10px;">
                  <el-button icon="el-icon-back"  :circle="true" type="mini" @click="clickBack"></el-button>
                </div>

                <el-table
                    :show-header="false"
                    :data="tableData"
                    border
                    :cell-style="columnStyle"
                    style="margin-bottom:10px"
                >
                  <el-table-column prop="name1" width="180" align="center"></el-table-column>
                  <el-table-column prop="amount1" align="center"></el-table-column>
                  <el-table-column prop="name2" width="180" align="center"></el-table-column>
                  <el-table-column prop="amount2" align="center"></el-table-column>
                  </el-table>

              </el-tab-pane>
              <el-tab-pane label="选题学生" name="topicStudent">
                <div style="float:left; margin-bottom:8px; margin-left:10px;">
                  <el-button icon="el-icon-back"  :circle="true" type="mini" @click="clickBack"></el-button>
                </div>
                <el-table :data="studentList">
                  <el-table-column sortable property="studentId" label="学号" align="center"></el-table-column>
                  <el-table-column property="name" label="姓名" align="center"></el-table-column>
                  <el-table-column property="sex" label="性别" align="center"></el-table-column>
                  <el-table-column property="major" label="专业" align="center"></el-table-column>
                  <el-table-column property="className" label="班级" align="center"></el-table-column>
                </el-table>
              </el-tab-pane>
              <el-tab-pane label="上传论文" name="topicPaper">
                <div style="float:left; margin-bottom:8px; margin-left:10px;">
                  <el-button icon="el-icon-back"  :circle="true" type="mini" @click="clickBack"></el-button>
                </div>
                <div>
                  <el-table :data="paperList">
                    <el-table-column sortable property="paperId" label="论文号" align="center"></el-table-column>
                    <el-table-column property="title" label="题目" align="center"></el-table-column>
                    <el-table-column property="author" label="作者" align="center"></el-table-column>
                    <el-table-column property="source" label="来源" align="center"></el-table-column>
                    <el-table-column property="keyword" label="关键字" align="center"></el-table-column>
                    <el-table-column property="abstractText" label="摘要" align="center"></el-table-column>
                    <el-table-column property="docLocation" label="物理位置" align="center"></el-table-column>
                    <el-table-column sortable property="uploadingTime" label="上传时间" align="center"></el-table-column>
                    <el-table-column property="link" label="文库链接" align="center">
                      <template slot-scope="scope">
                        <a :href="scope.row.link" target="_blank" class="buttonText">{{scope.row.link}}</a>
                      </template>
                    </el-table-column>
                  </el-table>
                </div>
              </el-tab-pane>
            </el-tabs>
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
import topicContent from "../components/TopicContent.vue";
import topicStudent from "../components/TopicStudent.vue";
import topicPaper from "../components/TopicPaper.vue";
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
  name: "TeacherTopic",
  components: {
    topicContent,
    topicStudent,
    topicPaper
  },
  data() {
    return {
      Token: window.localStorage["Token"],
      activeIndex: "/teacher",
      activeName: "topicContent",
      paperList: [],
      studentList: [],
      courseId:this.$route.params.courseId,
      topic:{}
    };
  },

  // 加载即显示
  created() {
    this.convert();
  },
  computed: {
    //因为数据用到了dataform中的数据，所以写在了computed中
    tableData() {
      return [
        {
          name1: "主题号",
          amount1: this.topic.topicId,
          name2: "主题",
          amount2: this.topic.topic
        },
        {
          name1: "课程号",
          amount1: this.topic.courseId,
          name2: "课程",
          amount2: this.topic.courseName
        },
        {
          name1: "教师号",
          amount1: this.topic.teacherId,
          name2: "出题教师",
          amount2: this.topic.teacherName
        },
        {
          name1: "关键字",
          amount1: this.topic.keyword,
          name2: "摘要",
          amount2: this.topic.creationTime
        },
        {
          name1: "选题人数",
          amount1: this.topic.studentNum,
          name2: "人数上限",
          amount2: this.topic.maxNum
        }
      ];
    }
  },
  methods: {
    convert() {
      // 直接请求
      axios
        .get(
          "http://localhost:8080/teacher/topic/details?topicId=" +
            this.$route.params.topicId,
          {
            headers: {
              Token: window.localStorage["Token"]
            }
          }
        )
        .then(
          success => {
            if (success.data.statusResp.code == "1") {
              this.studentList = success.data.studentList;
              this.topic = success.data.topic;
              this.paperList = success.data.paperList;
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
    // 自定义列背景色
    columnStyle({ columnIndex }) {
      if (columnIndex == 0  || columnIndex == 2) {
        return "background:#f3f6fc;";
      }else{
        return "background:#ffffff;";
      }
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
