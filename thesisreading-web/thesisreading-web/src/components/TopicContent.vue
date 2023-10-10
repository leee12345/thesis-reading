<template>
  <div>
    <div id="topic-manage">
      <div style="float:right; margin-bottom:8px; margin-right:10px;">
      </div>
    </div>

    <!-- 分页显示主题信息 -->
    <div class="block" @click="convert">
      <el-table :data="topictnlist" border style="width:100%">
        <el-table-column sortable prop="topicId" label="主题号"></el-table-column>
        <el-table-column prop="topicName" label="主题名"></el-table-column>
        <el-table-column prop="keyword" label="关键字"></el-table-column>
        <el-table-column prop="teacherName" label="教师名"></el-table-column>
        <el-table-column prop="creationTime" label="创建时间"></el-table-column>
        <el-table-column sortable prop="studentNum" label="选题人数"></el-table-column>
        <el-table-column sortable prop="maxNum" label="人数上限"></el-table-column>
        <el-table-column prop="topicInfo" label="选题信息">
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
  name: "topicContent",
  components: {},

  data() {
    return {
      topictnlist: [],
      pageSize: 10,
      currentPage: 1,
      addTopicForm: {},
      addTopicRules: {},
      addTopicVisible: false,
      Token: window.localStorage["Token"],
      cmtList: [],
      formLabelWidth: "80px"
    };
  },

  // 加载即显示第一页
  created() {
    this.convert();
  },

  methods: {
    handleDetail(row) {
      // 先判断是不是主讲教师，不是主讲教师不能跳转详情页面
      axios
        .get(
          "http://localhost:8080/teacher/topic/details?topicId=" + row.topicId,
          { headers: { Token: window.localStorage["Token"] } }
        )
        .then(success => {
          if (success.data.statusResp.code == "1") {
            this.$router.push({ path: `/teacher/topic/info/${row.topicId}` });
          } else {
            this.$message({
              type: "error",
              message: success.data.statusResp.err
            });
          }
        });
    },

    // 点 + 新增主题，请求该教师所教授课程的列表
    addTopic() {
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

    // 确定 增加topic
    submitAddTopic(formName) {
      this.$refs[formName].validate(valid => {
        this.$confirm("确定添加主题？", "提示", {
          cancelButtonText: "取消",
          confirmButtonText: "确定",
          type: "info"
        })
          .then(() => {
            axios
              .post(
                "http://localhost:8080/teacher/topic/add",
                {
                  topicId: this.addTopicForm.topicId,
                  courseId: this.addTopicForm.courseId,
                  topic: this.addTopicForm.topic,
                  keyword: this.addTopicForm.keyword,
                  maxNum: this.addTopicForm.maxNum
                },
                { headers: { Token: window.localStorage["Token"] } }
              )
              .then(
                success => {
                  if (success.data.statusResp.code == "1") {
                    this.$message({
                      type: "success",
                      message: "添加成功"
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
            //如果取消了
            this.$message({
              type: "info",
              message: "取消提交"
            });
          });
      });
    },
    cancelAddTopic() {
      this.$message({
        type: "info",
        message: "放弃增加主题"
      });
    },

    // 分页 -- 拉数据
    convert() {
      // 直接请求
      axios
        .get(
          "http://localhost:8080/teacher/topic/list?courseId=" +
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
              this.topictnlist = success.data.topictnlist;
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
</style>
