<template>
  <div id="app">
    <div>
      <router-view />
    </div>
  </div>
</template>

<script>

import Login from "./views/Login.vue";

export default {
  name: "app",
  components: {
  }
};
</script>

<style>
#app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
.el-select-dropdown {
  z-index: 90000 !important;
}
</style>
