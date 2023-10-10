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
        <div id="teacher-paper-list">
          <el-row>
            <el-col :span="20">
              <el-breadcrumb separator-class="el-icon-arrow-right" style>
                <el-breadcrumb-item>论文列表</el-breadcrumb-item>
              </el-breadcrumb>
            </el-col>
          </el-row>
          <el-row>
            <!-- 分割线 -->
            <el-divider></el-divider>
          </el-row>
          <!-- 展示列表 -->
          <div class="block">
            <el-table :data="paperList" border style="width:100%">
              <el-table-column  sortable prop="paperId" label="论文编号" width="auto"></el-table-column>
              <el-table-column prop="title" label="论文名" width="auto"></el-table-column>
              <el-table-column prop="author" label="作者" width="auto"></el-table-column>
              <el-table-column prop="source" label="出处" width="auto"></el-table-column>
              <el-table-column prop="keyword" label="关键词" width="auto"></el-table-column>
              <el-table-column prop="abstractText" label="简介" width="auto"></el-table-column>
              <el-table-column prop="docLocation" label="文件物理位置" width="auto"></el-table-column>
              <el-table-column  sortable prop="studentId" label="学号" width="auto"></el-table-column>
              <el-table-column  sortable prop="uploadingTime" label="上传时间" width="auto"></el-table-column>
              <el-table-column prop="messageInfo" label="查看评论">
                <template slot-scope="scope">
                  <el-button size="small" type="text" @click="handleDetail(scope.row)">查看</el-button>
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
  name: "TeacherPaper",
  components: {},

  data() {
    return {
      Token: window.localStorage["Token"],
      activeIndex: "/teacher/paper",
      paperList: [],
      pageSize: 5,//每页数据条数
      currentPage: 1,//当前页码
      total:300//总条数
    };
  },

  // 加载即显示第一页
  created() {
    this.convert();
  },

  methods: {
    convert() {
      axios
          .get("http://localhost:8080/teacher/paper/list?paperId="+
              this.$route.params.paperId +
              "&page=" +
              this.currentPage +
              "&size=" +
              this.pageSize,
              {
            headers: {
              Token: window.localStorage["Token"]
            }
          })
          .then(
              success => {
                if (success.data.statusResp.code == "1") {
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

    handleDetail(row){
      console.log("row", row);
      this.$router.push({ path: `/teacher/paper/message/${row.paperId}` });
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
