<template>
  <div class="login-container">
    <div class="login-box">
      <h2>用户登录</h2>
      <form @submit.prevent="handleLogin">
        <div class="form-group">
          <label>用户名</label>
          <input type="text" v-model="loginForm.username" required placeholder="请输入用户名">
        </div>
        <div class="form-group">
          <label>密码</label>
          <input type="password" v-model="loginForm.password" required placeholder="请输入密码">
        </div>
        <div class="form-actions">
          <button type="submit" class="btn-submit">登录</button>
          <router-link to="/register" class="btn-link">还没有账号？去注册</router-link>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import api from '../api'
import { mapActions } from 'vuex'

export default {
  name: 'Login',
  data() {
    return {
      loginForm: {
        username: '',
        password: ''
      }
    }
  },
  methods: {
    ...mapActions(['login']),
    async handleLogin() {
      try {
        const response = await api.user.login(this.loginForm)
        if (response.data.success) {
          const user = response.data.user
          this.login(user)
          localStorage.setItem('isLoggedIn', 'true')
          localStorage.setItem('token', user.id)
          localStorage.setItem('user', JSON.stringify(user))
          alert('登录成功')
          this.$router.push('/routes')
        } else {
          alert(response.data.message)
        }
      } catch (error) {
        alert('登录失败，请检查用户名和密码')
      }
    }
  }
}
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 60vh;
}

.login-box {
  background: white;
  padding: 40px;
  border-radius: 10px;
  box-shadow: 0 2px 10px rgba(0,0,0,0.1);
  width: 100%;
  max-width: 400px;
}

.login-box h2 {
  text-align: center;
  color: #2c3e50;
  margin-bottom: 30px;
}

.form-group {
  margin-bottom: 20px;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
  color: #333;
  font-weight: bold;
}

.form-group input {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
  font-size: 14px;
}

.form-group input:focus {
  outline: none;
  border-color: #42b983;
}

.form-actions {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 15px;
}

.btn-submit {
  width: 100%;
  padding: 12px;
  background-color: #42b983;
  color: white;
  border: none;
  border-radius: 5px;
  font-size: 16px;
  cursor: pointer;
}

.btn-submit:hover {
  background-color: #359268;
}

.btn-link {
  color: #42b983;
  text-decoration: none;
}

.btn-link:hover {
  text-decoration: underline;
}
</style>
