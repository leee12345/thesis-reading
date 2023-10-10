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
                <el-breadcrumb-item>我的主题</el-breadcrumb-item>
              </el-breadcrumb>
            </el-col>
          </el-row>
          <el-row>
            <!-- 分割线 -->
            <el-divider></el-divider>
          </el-row>
          <!-- 展示主题列表 -->
          <div class="block" @click="convert">
            <el-table :data="selectTopicList" border style="width:100%">
              <el-table-column sortable prop="topicId" label="主题号"></el-table-column>
              <el-table-column prop="topic" label="主题"></el-table-column>
              <el-table-column sortable prop="courseId" label="课程号"></el-table-column>
              <el-table-column prop="courseName" label="课程"></el-table-column>
              <el-table-column prop="teacherName" label="教师"></el-table-column>
              <el-table-column prop="keyword" label="关键字"></el-table-column>
              <el-table-column sortable prop="creationTime" label="创建时间"></el-table-column>
              <el-table-column sortable prop="selectionTime" label="选题时间"></el-table-column>
              <el-table-column prop="paperUp" label="论文列表">
                <template slot-scope="scope">
                  <el-button size="small" type="text" @click="handleClick(scope.row)">上传</el-button>
                  <el-dialog title="导入excel" :visible.sync="dialogVisible">
                    <div>
                      <el-upload
                        drag
                        :limit="limitNum"
                        :auto-upload="false"
                        accept=".xls"
                        :action="UploadUrl()"
                        :before-upload="beforeUploadFile"
                        :on-change="fileChange"
                        :on-exceed="exceedFile"
                        :on-success="handleSuccess"
                        :on-error="handleError"
                        :file-list="fileList"
                      >
                        <i class="el-icon-upload"></i>
                        <div class="el-upload__text">
                          将文件拖到此处，或
                          <em>点击上传</em>
                        </div>
                        <div class="el-upload__tip" slot="tip">只能上传xls文件，且不超过10M</div>
                      </el-upload>
                      <br />
                      <el-button size="small" type="primary" @click="uploadFile">立即上传</el-button>
                      <el-button size="small" @click="cancelUploadFile">取消</el-button>
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
  name: "StudentTopic",
  components: {},
  data() {
    return {
      Token: window.localStorage["Token"],
      activeIndex: "/student/topic",
      selectTopicList: [],
      dialogVisible: false,
      limitNum: 1, // 上传excell时，同时允许上传的最大数
      fileList: [], // excel文件列表
      selectTopicId: ""
    };
  },

  // 加载即显示第一页
  created() {
    this.convert();
  },

  methods: {
    handleClick(row) {
      this.dialogVisible = true;
      this.selectTopicId = row.topicId;
    },

    // 分页 -- 拉数据
    convert() {
      // 直接请求
      axios
        .get("http://localhost:8080/student/topic/self/list", {
          headers: {
            Token: window.localStorage["Token"]
          }
        })
        .then(
          success => {
            if (success.data.statusResp.code == "1") {
              this.selectTopicList = success.data.selectTopicList;
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


    // 文件超出个数限制时的钩子
    exceedFile(files, fileList) {
      this.$message.warning(
        `只能选择 ${this.limitNum} 个文件，当前共选择了 ${files.length +
          fileList.length} 个`
      );
    },
    // 文件状态改变时的钩子
    fileChange(file, fileList) {
      console.log(file.raw);
      this.fileList.push(file.raw);
      console.log(this.fileList);
    },
    // 上传文件之前的钩子, 参数为上传的文件,若返回 false 或者返回 Promise 且被 reject，则停止上传
    beforeUploadFile(file) {
      console.log("before upload");
      console.log(file);
      let extension = file.name.substring(file.name.lastIndexOf(".") + 1);
      let size = file.size / 1024 / 1024;
      if (extension !== "xls") {
        this.$message.warning("只能上传后缀是.xls的文件");
      }
      if (size > 10) {
        this.$message.warning("文件大小不得超过10M");
      }
    },
    // 文件上传成功时的钩子
    handleSuccess(res, file, fileList) {
      this.$message.success("文件上传成功");
    },
    // 文件上传失败时的钩子
    handleError(err, file, fileList) {
      this.$message.error("文件上传失败");
    },
    UploadUrl: function() {
      // 因为action参数是必填项，我们使用二次确认进行文件上传时，直接填上传文件的url会因为没有参数导致api报404，所以这里将action设置为一个返回为空的方法就行，避免抛错
      return "";
    },
    uploadFile() {
      if (this.fileList.length === 0) {
        this.$message.warning("请上传文件");
      } else {
        let form = new FormData();
        form.append("file", this.fileList[0]);
        this.$axios({
          method: "post",
          url:
            "http://localhost:8080/student/paper/upload?topicId=" +
            this.selectTopicId,
          headers: {
            "Content-Type": "multipart/form-data",
            Token: window.localStorage["Token"]
          },
          data: form
        }).then(
          success => {
            if (success.data.statusResp.code == "1") {
              this.$message({
                type: "success",
                message: "上传成功"
              });
              this.dialogVisible = false;
            } else {
              this.$message({
                type: "error",
                message: success.data.statusResp.err
              });
            }
          },
          err => {}
        );
      }
    },
    cancelUploadFile() {
      this.dialogVisible = false;
      this.$message({
        type: "info",
        message: "取消上传"
      });
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
