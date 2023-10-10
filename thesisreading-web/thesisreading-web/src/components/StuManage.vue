<template>
  <div>
    <!-- 新增一个选课学生 -->
    <div id="stu-manage">
      <div style="float:left; margin-bottom:8px; margin-left:10px;">
            <el-button icon="el-icon-back"  :circle="true" type="mini" @click="clickBack"></el-button>
      </div>
      <div style="float:right; margin-bottom:8px; margin-right:10px;">
        <el-button icon="el-icon-plus" :circle="true" type="mini" @click="addStudent"></el-button>
      </div>

      <!-- Form  增加选课学生信息-->
      <el-dialog title="新增选课学生" :visible.sync="addStudentVisible">
        <el-form :model="addStudentForm" :rules="addStudentRules" ref="addStudentForm">
          <!-- 从已经存在的学生列表中选择学生 -->
          <el-form-item label="学号" :label-width="formLabelWidth">
            <el-input v-model="addStudentForm.studentId" readonly></el-input>
          </el-form-item>
          <el-form-item label="学生姓名" :label-width="formLabelWidth" prop="studentId">
            <el-select v-model="addStudentForm.studentId" placeholder="请选择学生">
              <el-option
                v-for="item in allStudents"
                :key="item.studentId"
                :label="item.name"
                :value="item.studentId"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="function(){addStudentVisible = false; cancelAddStudent();}">取 消</el-button>
          <el-button type="primary" @click="submitAddStudent('addStudentForm');">确 定</el-button>
        </div>
      </el-dialog>
    </div>

    <!-- 分页显示学生信息 -->
    <div class="block" @click="convert">
      <el-table :data="stuList" border style="width:100%">
        <el-table-column sortable prop="studentId" label="学号"></el-table-column>
        <el-table-column prop="name" label="姓名"></el-table-column>
        <el-table-column prop="sex" label="性别"></el-table-column>
        <el-table-column prop="major" label="专业"></el-table-column>
        <el-table-column prop="className" label="班级"></el-table-column>

        <el-table-column prop="topicInfo" label="选题信息">
          <template slot-scope="scope">
            <el-button size="small" type="text" @click="handleDetail(scope.row)">查看</el-button>
          </template>
        </el-table-column>

        <el-table-column prop="className" label="操作">
          <template slot-scope="scope">
            <el-button size="mini" @click="editStudent(scope.row)">编辑</el-button>

            <!-- Form  编辑学生选课信息-->
            <el-dialog title="编辑学生选课信息" :visible.sync="editStudentVisible">
              <el-form :model="editStudentForm" :rules="editStudentRules" ref="editStudentForm">
                <el-form-item label="学号" :label-width="formLabelWidth">
                  <el-input v-model="editStudentForm.studentId" readonly></el-input>
                </el-form-item>
                <el-form-item label="学生姓名" :label-width="formLabelWidth">
                  <el-input v-model="editStudentForm.studentName" readonly></el-input>
                </el-form-item>
                <el-form-item label="课程号" :label-width="formLabelWidth">
                  <el-input v-model="editStudentForm.courseId" readonly></el-input>
                </el-form-item>
                <el-form-item label="分数" :label-width="formLabelWidth" prop="score">
                  <el-input-number v-model.number="editStudentForm.score" readonly></el-input-number>
                </el-form-item>
              </el-form>
              <div slot="footer" class="dialog-footer">
                <el-button @click="function(){editStudentVisible = false; cancelEditStudent();}">取 消</el-button>
                <el-button type="primary" @click="submitEditStudent('editStudentForm');">确 定</el-button>
              </div>
            </el-dialog>

            <el-button size="mini" type="danger" @click="deleteStudent(scope.row)">删除</el-button>
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
  name: "stuManage",
  components: {},

  data() {
    return {
      Token: window.localStorage["Token"],
      stuList: [],
      pageSize: 10,//每页数据条数
      currentPage: 1,//当前页码
      courseId:this.$route.params.courseId,
      total:300,//总条数
      allStudents: [],
      addStudentForm: {
        studentId: "",
        courseId: this.$route.params.courseId
      },
      addStudentRules: {
        studentId: [
          {
            required: true,
            message: "请选择学生",
            trigger: "change"
          }
        ]
      },
        editStudentForm: {
      scId: "",
          studentId: "",
          studentName: "",
          courseId: this.$route.params.courseId,
          score: ""
    },
    editStudentRules: {
      score: [
        { required: true, message: "请输入分数", trigger: "blur" },
        {
          type: "number",
          message: "分数必须位于0-100之间"
        }
      ]
    },
      addStudentVisible: false,
      editStudentVisible: false,
      formLabelWidth: "80px"
    };
  },

  // 加载即显示第一页
  created() {
    this.convert();
  },

  methods: {
    //提交编辑的选课记录 -- 分数
    submitEditStudent(formName) {
      this.$refs[formName].validate(valid => {
        this.$confirm("确定修改分数？", "提示", {
          cancelButtonText: "取消",
          confirmButtonText: "确定",
          type: "info"
        })
          .then(() => {
            axios
              .post(
                "http://localhost:8080/teacher/sc/edit/score",
                {
                  scId: this.editStudentForm.scId,
                  score: this.editStudentForm.score
                },
                { headers: { Token: window.localStorage["Token"] } }
              )
              .then(
                success => {
                  if (success.data.statusResp.code == "1") {
                    this.$message({
                      type: "success",
                      message: "修改成功"
                    });
                    this.editStudentVisible = false;
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
            //如果取消了修改学生
            this.$message({
              type: "info",
              message: "取消提交"
            });
          });
      });
    },

    cancelEditStudent() {
      this.$message({
        type: "info",
        message: "放弃编辑"
      });
    },

    //编辑一条选课记录 -- 编辑分数
    editStudent(row) {
      this.editStudentForm.studentId = row.studentId;
      this.editStudentForm.studentName = row.name;
      this.editStudentForm.courseId = this.$route.params.courseId;

      axios
        .get(
          "http://localhost:8080/teacher/sc/get?studentId=" +
            this.editStudentForm.studentId +
            "&courseId=" +
            this.editStudentForm.courseId,
          {
            headers: { Token: window.localStorage["Token"] }
          }
        )
        .then(success => {
          if (success.data.statusResp.code == "1") {
            this.editStudentForm.score = success.data.sc.score;
            this.editStudentForm.scId = success.data.sc.scId;
            this.editStudentVisible = true;
          } else {
            this.$message({
              type: "error",
              message: success.data.statusResp.err
            });
          }
        });
    },

    //删除一条选课记录
    deleteStudent(row) {
      this.$confirm("确定删除选课学生？", "提示", {
        cancelButtonText: "取消",
        confirmButtonText: "确定",
        type: "info"
      }).then(() => {
        axios
          .get(
            "http://localhost:8080/teacher/sc/delete?studentId=" +
              row.studentId +
              "&courseId=" +
              this.$route.params.courseId,
            {
              headers: { Token: window.localStorage["Token"] }
            }
          )
          .then(success => {
            if (success.data.statusResp.code == "1") {
              this.$message({
                type: "error",
                message: "删除成功"
              });
              this.$router.go(0);
            } else {
              this.$message({
                type: "error",
                message: success.data.statusResp.err
              });
            }
          });
      });
    },

    // 点击 + 增加一个选课学生
    addStudent() {
      // Ajax请求
      axios
        .get("http://localhost:8080/teacher/stu/info/all", {
          headers: {
            Token: window.localStorage["Token"]
          }
        })
        .then(success => {
          if (success.data.statusResp.code == "1") {
            //显示弹框，拉学生表
            this.addStudentVisible = true;
            this.allStudents = success.data.allStudents;
          } else {
            this.$message({
              type: "error",
              message: success.data.statusResp.err
            });
          }
        });
    },
    // 确定 增加选课学生
    submitAddStudent(formName) {
      this.$refs[formName].validate(valid => {
        this.$confirm("确定添加选课学生？", "提示", {
          cancelButtonText: "取消",
          confirmButtonText: "确定",
          type: "info"
        })
          .then(() => {
            axios
              .post(
                "http://localhost:8080/teacher/sc/add?studentId=" +
                  this.addStudentForm.studentId +
                  "&courseId=" +
                  this.addStudentForm.courseId,
                {},
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
            //如果取消了增加学生
            this.$message({
              type: "info",
              message: "取消提交"
            });
          });
      });
    },
    cancelAddStudent() {
      this.$message({
        type: "info",
        message: "放弃增加选课学生"
      });
    },

    // 分页 -- 拉数据
    convert() {
      // 直接请求
      axios
        .get(
          "http://localhost:8080/teacher/course/stu/list?courseId=" +
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
              this.stuList = success.data.stuList;
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
    handleDetail(row) {
      this.$router.push({ path: `/teacher/course/stu/${row.studentId}/${this.courseId}` });
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
      this.$router.push({ path: "/teacher" });
    },
  }
};
</script>

<style>
</style>
