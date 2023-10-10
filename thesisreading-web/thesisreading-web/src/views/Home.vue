<template>
  <div class="home">
    <img alt="Vue logo" src="../assets/logo.png">
    <HelloWorld msg="Welcome to Your Vue.js App 1"/>
    <ul>
      <li v-for="(item, index) in list" :key="index">{{item.text}}</li>
    </ul>
    <span @click="getTokenLocal">点击我保存token</span>
  </div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue'

export default {
  name: 'home',
  components: {
    HelloWorld
  },
  data() {
    return {
      page: 1,
      list: []
    }
  },
  methods: {
    pagePlus(newVal) {
      this.page = this.page + newVal
    },
    insertNewStudent() {
      this.list.push({ text: Math.random() })
    },
    saveTokenToLocal() {
      const testToken = 't'
      window.localStorage.setItem('token', testToken)
    },
    getTokenLocal() {
      alert(window.localStorage.getItem('token'))
    }
  }
}
</script>
