<template>
  <div id="app">
    <nav class="navbar">
      <div class="nav-container">
        <h1 class="logo">旅游管理系统</h1>
        <div class="nav-links">
          <router-link to="/">首页</router-link>
          <router-link to="/routes">旅游线路</router-link>
          <router-link to="/orders" v-if="isLoggedIn">我的订单</router-link>
          <router-link to="/login" v-if="!isLoggedIn">登录</router-link>
          <router-link to="/register" v-if="!isLoggedIn">注册</router-link>
          <span v-if="isLoggedIn" class="user-info">
            欢迎, {{ currentUser.realName }}
            <button @click="logout" class="logout-btn">退出</button>
          </span>
        </div>
      </div>
    </nav>
    <div class="content">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'

export default {
  name: 'App',
  computed: {
    ...mapState(['isLoggedIn', 'currentUser'])
  },
  methods: {
    ...mapActions(['logout'])
  }
}
</script>

<style>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  font-family: 'Microsoft YaHei', Arial, sans-serif;
  background-color: #f5f5f5;
}

.navbar {
  background-color: #2c3e50;
  color: white;
  padding: 1rem 0;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.nav-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.logo {
  font-size: 1.5rem;
}

.nav-links {
  display: flex;
  gap: 20px;
  align-items: center;
}

.nav-links a {
  color: white;
  text-decoration: none;
  padding: 8px 15px;
  border-radius: 4px;
  transition: background-color 0.3s;
}

.nav-links a:hover,
.nav-links a.router-link-active {
  background-color: #42b983;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 10px;
}

.logout-btn {
  background-color: #e74c3c;
  color: white;
  border: none;
  padding: 5px 10px;
  border-radius: 4px;
  cursor: pointer;
}

.content {
  max-width: 1200px;
  margin: 20px auto;
  padding: 20px;
}
</style>
