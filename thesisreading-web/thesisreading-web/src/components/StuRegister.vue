<template>
  <el-form
    :model="stuRegisterForm"
    :rules="rules"
    ref="stuRegisterForm"
    label-width="80px"
  >
    <el-form-item label="学号" prop="stuid">
      <el-input type="number" v-model="stuRegisterForm.stuid"></el-input>
    </el-form-item>

    <el-form-item label="姓名" prop="stuname">
      <el-input v-model="stuRegisterForm.stuname"></el-input>
    </el-form-item>

    <el-form-item label="密码" prop="stupwd" required>
      <el-input type="password" v-model="stuRegisterForm.stupwd"></el-input>
    </el-form-item>

    <el-form-item label="性别" prop="gender">
      <el-radio-group v-model="stuRegisterForm.gender">
        <el-radio-button label="男"></el-radio-button>
        <el-radio-button label="女"></el-radio-button>
      </el-radio-group>
    </el-form-item>

    <el-form-item label="专业" prop="major">
      <el-input v-model="stuRegisterForm.major"></el-input>
    </el-form-item>
    <el-form-item label="班级" prop="classname">
      <el-input v-model="stuRegisterForm.classname"></el-input>
    </el-form-item>

    <el-form-item>
      <el-button type="primary" @click="submitForm('stuRegisterForm')">注册</el-button>
      <el-button type="info"  native-type="reset" >重置</el-button>
    </el-form-item>
  </el-form>
</template>


<script>
export default {
  data() {
    return {
      stuRegisterForm: {
        stuid: "",
        stuname: "",
        stupwd: "",
        gender: "",
        major: "",
        classname: ""
      },
      rules: {
        stuid: [
          { required: true, message: "请输入学号", trigger: "blur" },
          { min: 3, max: 10, message: "长度在 3 到 10 个数字", trigger: "blur" }
        ],
        stuname: [
          { required: true, message: "请输入名字", trigger: "blur" },
          { min: 3, max: 10, message: "长度在3 到 10个字符", trigger: "blur" }
        ],
        stupwd: [{ required: true, message: "请输入密码", trigger: "blur" }],
        gender: [{ required: true, message: "请选择性别", trigger: "change" }],
        major: [{ required: true, message: "请输入专业", trigger: "blur" }],
        classname: [{ required: true, message: "请输入班级", trigger: "blur" }]
      }
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          this.$confirm("确认信息进行注册？", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          })
            .then(() => {
              // eslint-disable-next-line no-undef
              axios
                .post("http://localhost:8080/usr/student/dosignup", {
                  userid: this.stuRegisterForm.stuid,
                  studentname: this.stuRegisterForm.stuname,
                  password: this.stuRegisterForm.stupwd,
                  role: 2,
                  sex: this.stuRegisterForm.gender,
                  major: this.stuRegisterForm.major,
                  classname: this.stuRegisterForm.classname
                })
                .then(
                  success => {
                    console.log(success.data);
                    if (success.data.code === "1") {
                      //提示注册成功
                      this.$message({
                        type: "success",
                        message: "注册成功!"
                      });
                      //并且返回登录页面
                      this.$router.push('/');
                    }else {
                      this.$message({
                        type:"error",
                        message: success.data.err
                      });
                    }
                  },
                  err => {
                    console.log(err);
                  }
                )
            })
            .catch(() => {
              this.$message({
                type: "info",
                message: "取消注册"
              });
            });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  }
};
</script>
