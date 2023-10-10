<template>
  <el-form
      :model="teaRegisterForm"
      :rules="rules"
      ref="teaRegisterForm"
      label-width="80px"
  >
    <el-form-item label="教师号" prop="teaid">
      <el-input type="number" v-model="teaRegisterForm.teaid"></el-input>
    </el-form-item>

    <el-form-item label="姓名" prop="teaname">
      <el-input v-model="teaRegisterForm.teaname"></el-input>
    </el-form-item>

    <el-form-item label="密码" prop="teapwd" required>
      <el-input type="password" v-model="teaRegisterForm.teapwd"></el-input>
    </el-form-item>

    <el-form-item label="性别" prop="gender">
      <el-radio-group v-model="teaRegisterForm.gender">
        <el-radio-button label="男"></el-radio-button>
        <el-radio-button label="女"></el-radio-button>
      </el-radio-group>
    </el-form-item>

    <el-form-item label="用户类型" prop="role">
      <el-select v-model="teaRegisterForm.role" placeholder="请选择用户类型">
        <el-option label="管理员" :value="0"></el-option>
        <el-option label="授课教师" :value="1"></el-option>
      </el-select>
    </el-form-item>

    <el-form-item label="教师职称" prop="protitle">
      <el-select v-model="teaRegisterForm.protitle" placeholder="请选择教师职称">
        <el-option label="助教" value="助教"></el-option>
        <el-option label="讲师" value="讲师"></el-option>
        <el-option label="副教授" value="副教授"></el-option>
        <el-option label="教授" value="教授"></el-option>
      </el-select>
    </el-form-item>
    <el-form-item label="手机号" prop="phone">
      <el-input v-model="teaRegisterForm.phone"></el-input>
    </el-form-item>

    <el-form-item>
      <el-button type="primary" @click="submitForm('teaRegisterForm')">注册</el-button>
      <el-button type="info"  native-type="reset" >重置</el-button>
    </el-form-item>
  </el-form>
</template>


<script>
import axios from "axios";

export default {
  data() {
    return {
      teaRegisterForm: {
        teaid: "",
        teaname: "",
        teapwd: "",
        gender: "",
        role: "",
        protitle: "",
        phone: ""
      },
      rules: {
        teaid: [
          { required: true, message: "请输入教师号", trigger: "blur" },
          { min: 3, max: 10, message: "长度在 3 到 10 个数字", trigger: "blur" }
        ],
        teaname: [
          { required: true, message: "请输入名字", trigger: "blur" },
          { min: 3, max: 10, message: "长度在 3 到 10 个字符", trigger: "blur" }
        ],
        teapwd: [{ required: true, message: "请输入密码", trigger: "blur" }],
        gender: [{ required: true, message: "请选择性别", trigger: "change" }],
        role: [
          { required: true, message: "请选择用户类型", trigger: "change" }
        ],
        protitle: [
          { required: true, message: "请输入教师职称", trigger: "blur" }
        ],
        phone: [{ required: true, message: "请输入手机号", trigger: "blur" }]
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
              axios
                .post("http://localhost:8080/usr/teacher/dosignup", {
                  userid: this.teaRegisterForm.teaid,
                  teachername: this.teaRegisterForm.teaname,
                  password: this.teaRegisterForm.teapwd,
                  role: parseInt(this.teaRegisterForm.role),
                  sex: this.teaRegisterForm.gender,
                  protitle: this.teaRegisterForm.protitle,
                  phone: this.teaRegisterForm.phone
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
    }
  }
};
</script>
