<template>
  <div>
    <div id="tea-manage">
      <div style="float:left; margin-bottom:8px; margin-left:10px;">
        <el-button icon="el-icon-back"  :circle="true" type="mini" @click="clickBack"></el-button>
      </div>
      <div style="float:right; margin-bottom:8px; margin-right:10px;">
        <el-button icon="el-icon-plus" :circle="true" type="mini" @click="addTeacher"></el-button>
      </div>

      <!-- Form  增加授课教师-->
      <el-dialog title="新增团队教师" :visible.sync="addTeacherVisible">
        <el-form :model="addTeacherForm" :rules="addTeacherRules" ref="addTeacherForm">
          <!-- 从已经存在的教师列表中选择 -->
          <el-form-item label="教师号" :label-width="formLabelWidth">
            <el-input v-model="addTeacherForm.teacherId" readonly></el-input>
          </el-form-item>
          <el-form-item label="教师" :label-width="formLabelWidth" prop="teacherId">
            <el-select v-model="addTeacherForm.teacherId" placeholder="请选择教师" style="999999">
              <el-option
                v-for="item in allTeachers"
                :key="item.teacherId"
                :label="item.name"
                :value="item.teacherId"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="授课学时" :label-width="formLabelWidth" prop="teachHour">
            <el-input-number v-model="addTeacherForm.teachHour"></el-input-number>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="function(){addTeacherVisible = false; cancelAddTeacher();}">取 消</el-button>
          <el-button type="primary" @click="submitAddTeacher('addTeacherForm');">确 定</el-button>
        </div>
      </el-dialog>
    </div>
    <!-- 该课程所有授课教师信息 -->
    <div class="block" @click="convert">
      <el-table :data="tcList" border style="width:100%">
        <el-table-column sortable prop="tcId" label="授课号"></el-table-column>
        <el-table-column sortable prop="courseId" label="课程号"></el-table-column>
        <el-table-column prop="courseName" label="课程"></el-table-column>
        <el-table-column sortable prop="teacherId" label="教师号"></el-table-column>
        <el-table-column prop="teacherName" label="教师"></el-table-column>
        <el-table-column prop="teaIdentity" label="身份"></el-table-column>
        <el-table-column sortable prop="teachHour" label="授课学时"></el-table-column>
        <el-table-column prop="className" label="操作">
          <template slot-scope="scope">
            <el-button size="mini" @click="editTeacher(scope.row)">编辑</el-button>

            <!-- Form  编辑教师授课信息-->
            <el-dialog title="编辑教师授课信息" :visible.sync="editTeacherVisible">
              <el-form :model="editTeacherForm" :rules="editTeacherRules" ref="editTeacherForm">
                <el-form-item label="授课编号" :label-width="formLabelWidth">
                  <el-input v-model="editTeacherForm.tcId" readonly></el-input>
                </el-form-item>
                <el-form-item label="课程" :label-width="formLabelWidth">
                  <el-input v-model="editTeacherForm.courseName" readonly></el-input>
                </el-form-item>
                <el-form-item label="教师" :label-width="formLabelWidth">
                  <el-input v-model="editTeacherForm.teacherName" readonly></el-input>
                </el-form-item>
                <el-form-item label="授课学时" :label-width="formLabelWidth" prop="score">
                  <el-input-number v-model.number="editTeacherForm.teachHour" readonly></el-input-number>
                </el-form-item>
              </el-form>
              <div slot="footer" class="dialog-footer">
                <el-button @click="function(){editTeacherVisible = false; cancelEditTeacher();}">取 消</el-button>
                <el-button type="primary" @click="submitEditTeacher('editTeacherForm');">确 定</el-button>
              </div>
            </el-dialog>

            <el-button size="mini" type="danger" @click="deleteTc(scope.row)">删除</el-button>
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
  name: "teaManage",
  components: {},

  data() {
    return {
      Token: window.localStorage["Token"],
      pageSize: 10,//每页数据条数
      currentPage: 1,//当前页码
      total:300,//总条数
      formLabelWidth: "80px",
      tcList: [],
      addTeacherVisible: false,
      addTeacherForm: {
        teacherId: "",
        teachHour: ""
      },
      addTeacherRules: {},
      allTeachers: [],
      editTeacherForm: {
        tcId: "",
        courseName: "",
        teacherName: "",
        teachHour: ""
      },
      editTeacherRules: {},
      editTeacherVisible: false
    };
  },

  // 加载即显示第一页
  created() {
    this.convert();
  },

  methods: {
    //提交编辑的授课记录 -- 授课学时
    submitEditTeacher(formName) {
      this.$refs[formName].validate(valid => {
        this.$confirm("确定修改授课学时？", "提示", {
          cancelButtonText: "取消",
          confirmButtonText: "确定",
          type: "info"
        })
          .then(() => {
            axios
              .post(
                "http://localhost:8080/teacher/tc/update?tcId=" +
                  this.editTeacherForm.tcId +
                  "&teachHour=" +
                  this.editTeacherForm.teachHour,
                {},
                { headers: { Token: window.localStorage["Token"] } }
              )
              .then(
                success => {
                  if (success.data.statusResp.code == "1") {
                    this.$message({
                      type: "success",
                      message: "修改成功"
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
          })
          .catch(() => {
            //如果取消了修改
            this.$message({
              type: "info",
              message: "取消提交"
            });
          });
      });
    },

    cancelEditTeacher() {
      this.$message({
        type: "info",
        message: "放弃编辑"
      });
    },

    //编辑一条授课记录 -- 编辑授课学时
    editTeacher(row) {
      this.editTeacherForm.tcId = row.tcId;
      this.editTeacherForm.courseName = row.courseName;
      this.editTeacherForm.teacherName = row.teacherName;

      axios
        .get("http://localhost:8080/teacher/tc/get?tcId=" + row.tcId, {
          headers: { Token: window.localStorage["Token"] }
        })
        .then(success => {
          if (success.data.statusResp.code == "1") {
            this.editTeacherForm.teachHour = success.data.tc.teachHour;
            this.editTeacherVisible = true;
          } else {
            this.$message({
              type: "error",
              message: success.data.statusResp.err
            });
          }
        });
    },

    //删除一条选课记录
    deleteTc(row) {
      this.$confirm("确定删除该条授课记录？", "提示", {
        cancelButtonText: "取消",
        confirmButtonText: "确定",
        type: "info"
      })
        .then(() => {
          axios
            .post(
              "http://localhost:8080/teacher/tc/delete?tcId=" + row.tcId,
              {},
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
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "取消删除"
          });
        });
    },

    // 点击 + 增加一个教师
    addTeacher() {
      // Ajax请求
      axios
        .get("http://localhost:8080/teacher/listall", {
          headers: {
            Token: window.localStorage["Token"]
          }
        })
        .then(success => {
          if (success.data.statusResp.code == "1") {
            //显示弹框
            this.addTeacherVisible = true;
            this.allTeachers = success.data.allTeachers;
          } else {
            this.$message({
              type: "error",
              message: success.data.statusResp.err
            });
          }
        });
    },
    // 确定 增加团队教师
    submitAddTeacher(formName) {
      this.$refs[formName].validate(valid => {
        this.$confirm("确定添加团队教师？", "提示", {
          cancelButtonText: "取消",
          confirmButtonText: "确定",
          type: "info"
        })
          .then(() => {
            axios
              .post(
                "http://localhost:8080/teacher/tc/add",
                {
                  courseId: this.$route.params.courseId,
                  teacherId: this.addTeacherForm.teacherId,
                  teachHour: this.addTeacherForm.teachHour
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
            //如果取消了增加
            this.$message({
              type: "info",
              message: "取消提交"
            });
          });
      });
    },
    cancelAddTeacher() {
      this.$message({
        type: "info",
        message: "放弃增加团队教师"
      });
    },
    //分页拉数据
    convert() {
      // 直接请求
      axios
        .post(
          "http://localhost:8080/teacher/list?courseId=" +
            this.$route.params.courseId +
            "&page=" +
            this.currentPage +
            "&size=" +
            this.pageSize,
          {},
          {
            headers: {
              Token: window.localStorage["Token"]
            }
          }
        )
        .then(
          success => {
            if (success.data.statusResp.code == "1") {
              this.tcList = success.data.tcList;
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
    } ,
    clickBack() {
      this.$router.push({ path: "/teacher" });
    },
  }
};
</script>

<style>
</style>
