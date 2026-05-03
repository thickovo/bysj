<template>
  <div class="route-detail" v-if="route">
    <div class="detail-header">
      <h1>{{ route.routeName }}</h1>
      <p class="destination">目的地: {{ route.destination }}</p>
    </div>

    <div class="detail-content">
      <div class="info-section">
        <h3>线路介绍</h3>
        <p>{{ route.description }}</p>

        <h3>行程亮点</h3>
        <p>{{ route.highlights }}</p>

        <div class="info-grid">
          <div class="info-item">
            <label>行程天数:</label>
            <span>{{ route.duration }}天</span>
          </div>
          <div class="info-item">
            <label>价格:</label>
            <span class="price">¥{{ route.price }}</span>
          </div>
          <div class="info-item">
            <label>库存:</label>
            <span>{{ route.stock }}</span>
          </div>
        </div>
      </div>

      <div class="booking-section" v-if="isLoggedIn">
        <h3>预订线路</h3>
        <div class="form-group">
          <label>预订人数</label>
          <input type="number" v-model.number="bookingForm.quantity" min="1" :max="route.stock">
        </div>
        <div class="form-group">
          <label>联系人</label>
          <input type="text" v-model="bookingForm.contactName" placeholder="请输入联系人姓名">
        </div>
        <div class="form-group">
          <label>联系电话</label>
          <input type="tel" v-model="bookingForm.contactPhone" placeholder="请输入联系电话">
        </div>
        <div class="total-price">
          总价: <span>¥{{ totalPrice }}</span>
        </div>
        <button @click="handleBooking" class="btn-booking">确认预订</button>
      </div>

      <div class="login-tip" v-else>
        <p>请先 <router-link to="/login">登录</router-link> 后进行预订</p>
      </div>
    </div>

    <button @click="goBack" class="btn-back">返回</button>
  </div>
</template>

<script>
import api from '../api'
import { mapState } from 'vuex'

export default {
  name: 'RouteDetail',
  data() {
    return {
      route: null,
      bookingForm: {
        quantity: 1,
        contactName: '',
        contactPhone: ''
      }
    }
  },
  computed: {
    ...mapState(['isLoggedIn', 'currentUser']),
    totalPrice() {
      return this.route ? this.route.price * this.bookingForm.quantity : 0
    }
  },
  mounted() {
    this.loadRoute()
    if (this.currentUser) {
      this.bookingForm.contactName = this.currentUser.realName || ''
      this.bookingForm.contactPhone = this.currentUser.phone || ''
    }
  },
  methods: {
    async loadRoute() {
      try {
        const id = this.$route.params.id
        const response = await api.route.getById(id)
        this.route = response.data
      } catch (error) {
        alert('加载线路详情失败')
      }
    },
    async handleBooking() {
      if (!this.bookingForm.contactName || !this.bookingForm.contactPhone) {
        alert('请填写完整的联系信息')
        return
      }

      try {
        const orderData = {
          userId: this.currentUser.id,
          routeId: this.route.id,
          quantity: this.bookingForm.quantity,
          contactName: this.bookingForm.contactName,
          contactPhone: this.bookingForm.contactPhone
        }

        const response = await api.order.create(orderData)
        if (response.data.success) {
          alert('预订成功！')
          this.$router.push('/orders')
        } else {
          alert(response.data.message)
        }
      } catch (error) {
        alert('预订失败，请重试')
      }
    },
    goBack() {
      this.$router.push('/routes')
    }
  }
}
</script>

<style scoped>
.route-detail {
  background: white;
  padding: 30px;
  border-radius: 10px;
  box-shadow: 0 2px 10px rgba(0,0,0,0.1);
}

.detail-header {
  border-bottom: 2px solid #42b983;
  padding-bottom: 20px;
  margin-bottom: 30px;
}

.detail-header h1 {
  color: #2c3e50;
  margin-bottom: 10px;
}

.destination {
  color: #42b983;
  font-size: 18px;
  font-weight: bold;
}

.detail-content {
  display: grid;
  grid-template-columns: 2fr 1fr;
  gap: 30px;
}

.info-section h3 {
  color: #2c3e50;
  margin-bottom: 10px;
  margin-top: 20px;
}

.info-section p {
  color: #666;
  line-height: 1.8;
  margin-bottom: 15px;
}

.info-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 15px;
  margin-top: 20px;
}

.info-item {
  display: flex;
  gap: 10px;
}

.info-item label {
  color: #666;
}

.info-item span {
  color: #333;
  font-weight: bold;
}

.price {
  color: #e74c3c !important;
  font-size: 18px;
}

.booking-section {
  background: #f9f9f9;
  padding: 20px;
  border-radius: 10px;
}

.booking-section h3 {
  color: #2c3e50;
  margin-bottom: 20px;
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
}

.total-price {
  font-size: 18px;
  color: #333;
  margin: 20px 0;
}

.total-price span {
  color: #e74c3c;
  font-weight: bold;
  font-size: 24px;
}

.btn-booking {
  width: 100%;
  padding: 12px;
  background-color: #42b983;
  color: white;
  border: none;
  border-radius: 5px;
  font-size: 16px;
  cursor: pointer;
}

.btn-booking:hover {
  background-color: #359268;
}

.login-tip {
  background: #f9f9f9;
  padding: 40px;
  border-radius: 10px;
  text-align: center;
}

.login-tip a {
  color: #42b983;
}

.btn-back {
  margin-top: 30px;
  padding: 10px 30px;
  background-color: #95a5a6;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}
</style>
