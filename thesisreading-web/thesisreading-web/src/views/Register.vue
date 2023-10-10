<template>
  <div>
    <el-container >
      <el-header style="text-align: right;">
        <div class="syslogo" style="float:left;">研究生课程论文选读系统</div>
        <el-dropdown style="color:#FFF">
          <i class="el-icon-user-solid" style="margin-right: 15px;font-size: 20px;"></i>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item @click.native="Register">注册</el-dropdown-item>
            <el-dropdown-item @click.native="Login">登录</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </el-header>

      <el-main>
        <div class="register">
          <el-tabs v-model="activeName" type="border-card" >
            <el-tab-pane label="教师注册" name="teaRegister" :key="'teaRegister'" >
              <div class="register-box">
                <tea-register />
              </div>
            </el-tab-pane>
            <el-tab-pane label="学生注册" name="stuRegister" :key="'stuRegister'" >
              <div class="register-box">
                <stu-register />
              </div>
            </el-tab-pane>
          </el-tabs>
        </div>
      </el-main>
    </el-container>
  </div>
</template>

<script>
// @ is an alias to /src

import stuRegister from "../components/StuRegister.vue";
import teaRegister from "../components/TeaRegister.vue";

export default {
  name: "Register",
  components: {
    stuRegister,
    teaRegister
  },
  data() {
    return {
      activeName: "teaRegister"
    };
  },
  methods: {
    Register() {
      this.$notify({
        title: "提示",
        message: "您已在注册页面"
      });
    },
    Login() {
      this.$router.push({ path: "/" });
    }
  }
};
</script>

<style>
.el-container{
  height: 700px;
  background-color: #E9EEF3;

}
.el-header {
  background-color:  #2F4056;
  color: #ffffff;
  line-height: 60px;
}

.register{
    margin: 0%;
    text-align: center;
}

.register-box{
    margin:0 auto;
    width:60%;
    padding: 0;
}
</style>
