<template>
  <div >
    <el-container >
      <el-header style="text-align: right; ">
        <div class="syslogo" style="float:left;">研究生课程论文选读系统</div>
        <el-dropdown style="color:#FFF">
          <i class="el-icon-user-solid" style="margin-right: 15px;font-size: 20px;"></i>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item @click.native="Register">注册</el-dropdown-item>
            <el-dropdown-item @click.native="Login">登录</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </el-header>

      <el-main style="margin:0 auto ;">
        <Signin style="background-color: #D3DCE6" msg="登录" />
      </el-main>
    </el-container>
  </div>
</template>

<script>
// @ is an alias to /src
import Signin from "@/components/Signin.vue";

export default {
  name: "Login",
  components: {
    Signin
  },
  methods: {
    Register() {
      this.$router.push({ path: "/register" });
    },
    Login() {
      this.$notify({
        title: "提示",
        message: "您已在登录页面"
      });
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
  background-color:#2F4056;
  color: #ffffff;
  line-height: 60px;
}


</style>


