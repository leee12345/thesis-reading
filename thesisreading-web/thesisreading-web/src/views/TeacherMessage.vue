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
        <div id="teacher-message-indiv">
          <el-row>
            <el-col :span="20">
              <el-breadcrumb separator-class="el-icon-arrow-right" style>
                <el-breadcrumb separator-class="el-icon-arrow-right">
                  <el-breadcrumb-item >评论列表</el-breadcrumb-item>
                  <el-breadcrumb-item>论文ID: {{this.$route.params.paperId}}</el-breadcrumb-item>
                </el-breadcrumb>
              </el-breadcrumb>
            </el-col>
          </el-row>

          <el-row>
            <!-- 分割线 -->
            <el-divider></el-divider>
          </el-row>

          <!-- 新增一条评论 -->
          <div id="teacher-message-manage">
            <div style="float:left; margin-bottom:8px; margin-left:10px;">
              <el-button icon="el-icon-back"  :circle="true" type="mini" @click="clickBack"></el-button>
            </div>
            <div style="float:right; margin-bottom:8px; margin-right:10px;">
              <el-button icon="el-icon-plus" :circle="true" type="mini" @click="addMessage"></el-button>
            </div>
            <!-- Form  增加评论-->
            <el-dialog title="新增评论" :visible.sync="addMessageVisible">
              <el-form :model="addMessageForm"  ref="addMessageForm">
                <el-form-item label="论文编号" :label-width="formLabelWidth">
                  <el-input v-model="addMessageForm.paperId" readonly></el-input>
                </el-form-item>
                <el-form-item label="用户ID" :label-width="formLabelWidth">
                  <el-input v-model="userId" readonly></el-input>
                </el-form-item>
                <el-form-item label="评论内容" :label-width="formLabelWidth">
                  <el-input type="textarea" :rows="5" placeholder="请输入评论" v-model="addMessageForm.content">
                  </el-input>
                </el-form-item>

              </el-form>
              <div slot="footer" class="dialog-footer">
                <el-button @click="function(){addMessageVisible = false; cancelAddMessage();}">取 消</el-button>
                <el-button type="primary" @click="submitAddMessage('addMessageForm');">确 定</el-button>
              </div>
            </el-dialog>

          </div>


          <!-- 展示评论列表 -->
          <div class="block" @click="convert">
            <el-table :data="messageList" border style="width:100%">
              <el-table-column sortable prop="messageId" label="评论ID" width="150"></el-table-column>
              <el-table-column sortable prop="userId" label="用户ID" width="150"></el-table-column>
              <el-table-column prop="content" label="评论内容"></el-table-column>
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
  name: "TeacherMessage",
  components: {},
  data() {
    return {
      Token: window.localStorage["Token"],
      activeIndex: "/teacher/paper",
      messageList: [],
      pageSize: 5,//每页数据条数
      currentPage: 1,//当前页码
      total:300,//总条数
      dialogTableVisible: false,
      addMessageForm: {
        paperId: this.$route.params.paperId,
        content: "",
      },
      userId:"",
      addMessageVisible: false,
      formLabelWidth: "80px"
    };
  },

  // 加载即显示第一页
  created() {
    this.convert();
  },

  methods: {

    // 分页 -- 拉数据 messageList
    convert() {
      // 直接请求
      axios
        .get(
          "http://localhost:8080/teacher/paper/message/list?paperId=" +
            this.$route.params.paperId +
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
              this.messageList = success.data.messageList;
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
    // 点击 + 增加一条评论
    addMessage() {
      // Ajax请求
      axios
          .get("http://localhost:8080/teacher/paper/message/info/all", {
            headers: {
              Token: window.localStorage["Token"]
            }
          })
          .then(success => {
            if (success.data.statusResp.code == "1") {
              //显示弹框
              this.addMessageVisible = true;
              this.userId= success.data.userId;
            } else {
              this.$message({
                type: "error",
                message: success.data.statusResp.err
              });
            }
          });
    },
    // 确定 增加一条评论
    submitAddMessage(formName) {
      this.$refs[formName].validate(valid => {
        this.$confirm("确定发布评论？", "提示", {
          cancelButtonText: "取消",
          confirmButtonText: "确定",
          type: "info"
        })
            .then(() => {
              axios
                  .post(
                      "http://localhost:8080/teacher/paper/message/add",
                      {
                        paperId:this.$route.params.paperId,
                        content:this.addMessageForm.content
                      },
                      { headers: { Token: window.localStorage["Token"] } }
                  )
                  .then(
                      success => {
                        if (success.data.statusResp.code == "1") {
                          this.$message({
                            type: "success",
                            message: "发布成功"
                          });
                          this.$router.go(0); // 刷新页面
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
              //如果取消了增加评论
              this.$message({
                type: "info",
                message: "取消发布"
              });
            });
      });
    },
    cancelAddMessage() {
      this.$message({
        type: "info",
        message: "放弃发布评论"
      });
    },
    clickBack() {
      this.$router.push({ path: "/teacher/paper" });
    },
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
