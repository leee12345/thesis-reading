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

          <el-menu-item index="/student">所学课程</el-menu-item>
          <el-menu-item index="/student/topic">我的主题</el-menu-item>
          <el-menu-item index="/student/paper">论文评论</el-menu-item>

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
        <div id="student-course-indiv">
          <el-row>
            <el-col :span="20">
              <el-breadcrumb separator-class="el-icon-arrow-right" style>
                <el-breadcrumb-item>主题列表</el-breadcrumb-item>
              </el-breadcrumb>
            </el-col>
          </el-row>
          <el-row>
            <!-- 分割线 -->
            <el-divider></el-divider>
          </el-row>
          <div style="float:left; margin-bottom:8px; margin-left:10px;">
            <el-button icon="el-icon-back"  :circle="true" type="mini" @click="clickBack"></el-button>
          </div>
          <!-- 展示主题列表 -->
          <div class="block" @click="convert">
            <el-table :data="topicList" border style="width:100%">
              <el-table-column sortable prop="topicId" label="主题号"></el-table-column>
              <el-table-column prop="topic" label="主题"></el-table-column>
              <el-table-column sortable prop="courseId" label="课程号"></el-table-column>
              <el-table-column prop="courseName" label="课程"></el-table-column>
              <el-table-column prop="teacherName" label="教师"></el-table-column>
              <el-table-column prop="keyword" label="关键字"></el-table-column>
              <el-table-column sortable prop="creationTime" label="创建时间"></el-table-column>
              <el-table-column sortable prop="studentNum" label="选题人数"></el-table-column>
              <el-table-column sortable prop="maxNum" label="人数上限"></el-table-column>
              <el-table-column prop="topicInfo" label="选题信息">
                <template slot-scope="scope">
                  <el-button size="small" type="text" @click="handleClick(scope.row)">查看</el-button>
                  <!-- Table -->
                  <el-dialog width="80%" title="主题详细信息" :visible.sync="dialogTableVisible" >
                    <div>
                      <el-divider>选题学生名单</el-divider>
                      <el-table :data="tstuList">
                        <el-table-column sortable property="studentId" label="学号" width="120"></el-table-column>
                        <el-table-column property="studentName" label="姓名" width="120"></el-table-column>
                        <el-table-column property="major" label="专业" width="120"></el-table-column>
                        <el-table-column property="className" label="班级" width="auto"></el-table-column>
                        <el-table-column sortable property="selectionTime" label="选题时间" width="auto"></el-table-column>
                      </el-table>
                    </div>
                    <div>
                      <el-divider>上传论文列表</el-divider>
                      <el-table :data="paperList" >
                        <el-table-column sortable property="paperId" label="论文号" ></el-table-column>
                        <el-table-column property="title" label="题目" > </el-table-column>
                        <el-table-column property="author" label="作者" ></el-table-column>
                        <el-table-column property="source" label="出处"></el-table-column>
                        <el-table-column property="keyword" label="关键字"></el-table-column>
                        <el-table-column property="abstractText" label="摘要"></el-table-column>
                        <el-table-column property="docLocation" label="文件物理位置"></el-table-column>
                        <el-table-column sortable property="studentId" label="学号"></el-table-column>
                        <el-table-column sortable property="uploadingTime" label="上传时间"></el-table-column>
                        <el-table-column  label="文库链接" >
                          <template slot-scope="scope">
                              <a :href="scope.row.link" target="_blank" class="buttonText">{{scope.row.link}}</a>
                          </template>
                        </el-table-column>
                      </el-table>
                    </div>
                  </el-dialog>
                </template>
              </el-table-column>

              <el-table-column prop="className" label="选题">
                <template slot-scope="scope">
                  <el-button size="mini" type="success" @click="handleRead(scope.row)">我要读</el-button>
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
  name: "StudentTopic",
  components: {},
  data() {
    return {
      Token: window.localStorage["Token"],
      activeIndex: "/student",
      topicList: [],
      pageSize: 10,//每页数据条数
      currentPage: 1,//当前页码
      total:300,//总条数
      dialogTableVisible: false,
      tstuList:[],
      paperList:[]
    };
  },

  // 加载即显示第一页
  created() {
    this.convert();
  },

  methods: {


    //主题详细信息
    handleClick(row) {
      // 学生
      axios
        .get(
          "http://localhost:8080/student/topic/stu/list?topicId=" + row.topicId,
          {
            headers: {
              Token: window.localStorage["Token"]
            }
          }
        )
        .then(success => {
          if (success.data.statusResp.code == "1") {
            this.tstuList = success.data.tstuList;
          } else {
            this.$message({
              type: "error",
              message: success.data.statusResp.err
            });
          }
        });

      //课程
      axios
        .get(
          "http://localhost:8080/student/topic/stu/paper/list/all?topicId=" +
            row.topicId,
          {
            headers: {
              Token: window.localStorage["Token"]
            }
          }
        )
        .then(success => {
          if (success.data.statusResp.code == "1") {
            this.paperList = success.data.paperList;
          } else {
            this.$message({
              type: "error",
              message: success.data.statusResp.err
            });
          }
        });
      this.dialogTableVisible = true;
    },

    // 我要读
    handleRead(row) {
      // 直接请求
      axios
        .get(
          "http://localhost:8080/student/stutopic/add?topicId=" + row.topicId,
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
                message: "选题成功"
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
    },

    // 分页 -- 拉数据
    convert() {
      // 直接请求
      axios
        .get(
          "http://localhost:8080/student/course/topic/list?courseId=" +
            this.$route.params.courseId +
            "&page=" +
            this.currentPage +
            "&size=" +
            this.pageSize,
          {
            headers: {
              Token: window.localStorage["Token"]
            }
          }
        )
        .then(
          success => {
            if (success.data.statusResp.code == "1") {
              this.topicList = success.data.topicList;
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
    clickBack() {
      this.$router.push({ path: "/student" });
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
