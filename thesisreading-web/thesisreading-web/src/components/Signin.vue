<template>
  <div>
    <el-row type="flex" justify="center" style="margin-top:100px;">
      <el-card shadow="always" class="box-card">
        <div class="avatar_box">
          <img src="../assets/book.jpg"  />
        </div>
        <h1>{{ msg }}</h1>
        <el-form  label-width="60px" style="margin:40px" >
          <el-form-item >
            <el-input  type="text" v-model="userid"  placeholder="用户名"></el-input>
          </el-form-item>
          <el-form-item >
            <el-input  v-model="password" show-password placeholder="密码"></el-input>
          </el-form-item>
          <el-form-item class="btn">
            <el-button type="primary" @click="login"  round="true" >登录</el-button>
            <el-button type="info"  round="true" native-type="reset">重置</el-button>
          </el-form-item>

        </el-form>
      </el-card>
    </el-row>
  </div>
</template>

<script>
import Vue from "vue";
import axios from "axios";
import { Button, Form, Input, FormItem, Col, Row, Card } from "element-ui";

Vue.use(Button);
Vue.use(Form);
Vue.use(Input);
Vue.use(FormItem);
Vue.use(Col);
Vue.use(Row);
Vue.use(Card);

export default {
  name: "Signin",
  props: {
    msg: String
  },
  data() {
    return {
      userid: "",
      password: "",
    };
  },
  methods: {
    login() {
      axios
        .post("http://localhost:8080/usr/signin", {
          userid: this.userid,
          password: this.password
        })
        .then(
          success => {
            console.log(success.data);

            // 用户存在
            if (success.data.statusResp.code === "1") {
              // token
              window.localStorage.setItem("Token", success.data.Token);

              //根据用户类型跳转到不同的页面
              if (success.data.role == 0) {
                this.$router.push("/admin");
              } else if (success.data.role == 1) {
                this.$router.push("/teacher");
              } else {
                this.$router.push("/student");
              }
            } else {
              //用户不存在，提醒 ------ 用户或密码不正确
              const errorInfo = success.data.statusResp.err;
              this.$message({
                type: "info",
                message: errorInfo
              });
            }
          },
          err => {
            console.log(err);
          }
        )
        .finally();
    } ,

  }

};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="less">

.box-card {
  width: 500px;
  height: 300px;
  margin: 10px;
  padding: 20px;
}
.avatar_box {
  padding: 10px;
  width: 100px;
  height: 100px;
  border: 1px solid #eeeeee;
  border-radius: 50%;
  box-shadow: 0 0 10px #ddd;
  position: absolute;
  left: 50%;
  transform: translate(-50%, -50%);
  background: #fff;
}
img {
  width: 100%;
  height: 100%;
  border-radius: 50%;
  background: #eee;
}
.el-button{
  width: 120px;
  margin:20px;
}
.btn {
margin:0 auto;
}
</style>
