<template>
  <div class="home">
    <div class="hero">
      <h1>欢迎使用旅游管理系统</h1>
      <p>探索世界，从这里开始</p>
      <router-link to="/routes" class="btn-primary">查看旅游线路</router-link>
    </div>

    <div class="features">
      <div class="feature-card">
        <h3>丰富线路</h3>
        <p>涵盖国内外热门旅游景点</p>
      </div>
      <div class="feature-card">
        <h3>便捷预订</h3>
        <p>简单几步即可完成预订</p>
      </div>
      <div class="feature-card">
        <h3>订单管理</h3>
        <p>随时查看和管理您的订单</p>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Home'
}
</script>

<style scoped>
.hero {
  text-align: center;
  padding: 60px 20px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  border-radius: 10px;
  margin-bottom: 40px;
}

.hero h1 {
  font-size: 2.5rem;
  margin-bottom: 10px;
}

.hero p {
  font-size: 1.2rem;
  margin-bottom: 30px;
}

.btn-primary {
  display: inline-block;
  background-color: #42b983;
  color: white;
  padding: 12px 30px;
  text-decoration: none;
  border-radius: 5px;
  font-size: 1.1rem;
  transition: background-color 0.3s;
}

.btn-primary:hover {
  background-color: #359268;
}

.features {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 20px;
  margin-top: 40px;
}

.feature-card {
  background: white;
  padding: 30px;
  border-radius: 10px;
  text-align: center;
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
}

.feature-card h3 {
  color: #2c3e50;
  margin-bottom: 10px;
}

.feature-card p {
  color: #666;
}
</style>
