<template>
  <div class="register-container">
    <div class="register-box">
      <h2>用户注册</h2>
      <form @submit.prevent="handleRegister">
        <div class="form-group">
          <label>用户名</label>
          <input type="text" v-model="registerForm.username" required placeholder="请输入用户名">
        </div>
        <div class="form-group">
          <label>密码</label>
          <input type="password" v-model="registerForm.password" required placeholder="请输入密码">
        </div>
        <div class="form-group">
          <label>真实姓名</label>
          <input type="text" v-model="registerForm.realName" required placeholder="请输入真实姓名">
        </div>
        <div class="form-group">
          <label>手机号</label>
          <input type="tel" v-model="registerForm.phone" required placeholder="请输入手机号">
        </div>
        <div class="form-group">
          <label>邮箱</label>
          <input type="email" v-model="registerForm.email" placeholder="请输入邮箱">
        </div>
        <div class="form-group">
          <label>身份证号</label>
          <input type="text" v-model="registerForm.idCard" placeholder="请输入身份证号">
        </div>
        <div class="form-actions">
          <button type="submit" class="btn-submit">注册</button>
          <router-link to="/login" class="btn-link">已有账号？去登录</router-link>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import api from '../api'

export default {
  name: 'Register',
  data() {
    return {
      registerForm: {
        username: '',
        password: '',
        realName: '',
        phone: '',
        email: '',
        idCard: '',
        role: '用户'
      }
    }
  },
  methods: {
    async handleRegister() {
      try {
        const response = await api.user.register(this.registerForm)
        if (response.data.success) {
          alert('注册成功，请登录')
          this.$router.push('/login')
        } else {
          alert(response.data.message)
        }
      } catch (error) {
        alert('注册失败，请重试')
      }
    }
  }
}
</script>

<style scoped>
.register-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 60vh;
}

.register-box {
  background: white;
  padding: 40px;
  border-radius: 10px;
  box-shadow: 0 2px 10px rgba(0,0,0,0.1);
  width: 100%;
  max-width: 450px;
}

.register-box h2 {
  text-align: center;
  color: #2c3e50;
  margin-bottom: 30px;
}

.form-group {
  margin-bottom: 15px;
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
  margin-top: 20px;
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
