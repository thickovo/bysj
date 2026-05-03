<template>
  <div class="routes-page">
    <div class="search-bar">
      <input
        type="text"
        v-model="searchKeyword"
        placeholder="搜索旅游线路..."
        @keyup.enter="handleSearch"
      >
      <button @click="handleSearch" class="btn-search">搜索</button>
    </div>

    <div class="routes-list">
      <div v-if="routes.length === 0" class="no-data">
        暂无旅游线路
      </div>
      <div v-else class="route-card" v-for="route in routes" :key="route.id">
        <div class="route-info">
          <h3>{{ route.routeName }}</h3>
          <p class="destination">目的地: {{ route.destination }}</p>
          <p class="description">{{ route.description }}</p>
          <div class="route-details">
            <span>天数: {{ route.duration }}天</span>
            <span class="price">价格: ¥{{ route.price }}</span>
            <span>库存: {{ route.stock }}</span>
          </div>
        </div>
        <div class="route-actions">
          <button @click="viewDetail(route.id)" class="btn-detail">查看详情</button>
          <button @click="bookRoute(route)" class="btn-book" v-if="isLoggedIn">立即预订</button>
          <router-link to="/login" v-else class="btn-book">登录后预订</router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import api from '../api'
import { mapState } from 'vuex'

export default {
  name: 'Routes',
  data() {
    return {
      routes: [],
      searchKeyword: ''
    }
  },
  computed: {
    ...mapState(['isLoggedIn'])
  },
  mounted() {
    this.loadRoutes()
  },
  methods: {
    async loadRoutes() {
      try {
        const response = await api.route.getAvailable()
        this.routes = response.data
      } catch (error) {
        alert('加载线路失败')
      }
    },
    async handleSearch() {
      try {
        if (this.searchKeyword.trim()) {
          const response = await api.route.search(this.searchKeyword)
          this.routes = response.data
        } else {
          this.loadRoutes()
        }
      } catch (error) {
        alert('搜索失败')
      }
    },
    viewDetail(id) {
      this.$router.push(`/routes/${id}`)
    },
    bookRoute(route) {
      this.$router.push(`/routes/${route.id}`)
    }
  }
}
</script>

<style scoped>
.search-bar {
  display: flex;
  gap: 10px;
  margin-bottom: 30px;
}

.search-bar input {
  flex: 1;
  padding: 12px;
  border: 1px solid #ddd;
  border-radius: 5px;
  font-size: 14px;
}

.btn-search {
  padding: 12px 30px;
  background-color: #42b983;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.routes-list {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.no-data {
  text-align: center;
  padding: 40px;
  color: #999;
  font-size: 16px;
}

.route-card {
  background: white;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.route-info h3 {
  color: #2c3e50;
  margin-bottom: 10px;
}

.destination {
  color: #42b983;
  font-weight: bold;
  margin-bottom: 5px;
}

.description {
  color: #666;
  margin-bottom: 10px;
  font-size: 14px;
}

.route-details {
  display: flex;
  gap: 20px;
  color: #666;
  font-size: 14px;
}

.price {
  color: #e74c3c;
  font-weight: bold;
  font-size: 16px;
}

.route-actions {
  display: flex;
  gap: 10px;
}

.btn-detail,
.btn-book {
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  text-decoration: none;
  font-size: 14px;
}

.btn-detail {
  background-color: #3498db;
  color: white;
}

.btn-book {
  background-color: #42b983;
  color: white;
}

.btn-detail:hover,
.btn-book:hover {
  opacity: 0.9;
}
</style>
