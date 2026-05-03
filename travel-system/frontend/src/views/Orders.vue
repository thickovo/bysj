<template>
  <div class="orders-page">
    <h2>我的订单</h2>

    <div v-if="orders.length === 0" class="no-data">
      暂无订单，去 <router-link to="/routes">浏览线路</router-link>
    </div>

    <div v-else class="orders-list">
      <div class="order-card" v-for="order in orders" :key="order.id">
        <div class="order-header">
          <span class="order-no">订单号: {{ order.orderNo }}</span>
          <span class="order-status" :class="getStatusClass(order.status)">
            {{ order.status }}
          </span>
        </div>

        <div class="order-info">
          <p>路线ID: {{ order.routeId }}</p>
          <p>预订人数: {{ order.quantity }}人</p>
          <p>总价: <span class="price">¥{{ order.totalPrice }}</span></p>
          <p>联系人: {{ order.contactName }}</p>
          <p>联系电话: {{ order.contactPhone }}</p>
          <p>下单时间: {{ formatDate(order.createTime) }}</p>
        </div>

        <div class="order-actions">
          <button
            v-if="order.status === '待支付'"
            @click="handlePay(order.id)"
            class="btn-pay"
          >
            支付
          </button>
          <button
            v-if="order.status === '待支付' || order.status === '已支付'"
            @click="handleCancel(order.id)"
            class="btn-cancel"
          >
            取消订单
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import api from '../api'
import { mapState } from 'vuex'

export default {
  name: 'Orders',
  data() {
    return {
      orders: []
    }
  },
  computed: {
    ...mapState(['currentUser'])
  },
  mounted() {
    this.loadOrders()
  },
  methods: {
    async loadOrders() {
      try {
        if (this.currentUser) {
          const response = await api.order.getByUserId(this.currentUser.id)
          this.orders = response.data
        }
      } catch (error) {
        alert('加载订单失败')
      }
    },
    formatDate(dateStr) {
      if (!dateStr) return ''
      const date = new Date(dateStr)
      return date.toLocaleString('zh-CN')
    },
    getStatusClass(status) {
      return {
        'status-pending': status === '待支付',
        'status-paid': status === '已支付',
        'status-cancelled': status === '已取消'
      }
    },
    async handlePay(orderId) {
      if (!confirm('确认支付此订单？')) return

      try {
        const response = await api.order.pay(orderId)
        if (response.data.success) {
          alert('支付成功')
          this.loadOrders()
        } else {
          alert(response.data.message)
        }
      } catch (error) {
        alert('支付失败，请重试')
      }
    },
    async handleCancel(orderId) {
      if (!confirm('确认取消此订单？')) return

      try {
        const response = await api.order.cancel(orderId)
        if (response.data.success) {
          alert('订单已取消')
          this.loadOrders()
        } else {
          alert(response.data.message)
        }
      } catch (error) {
        alert('取消失败，请重试')
      }
    }
  }
}
</script>

<style scoped>
.orders-page {
  background: white;
  padding: 30px;
  border-radius: 10px;
  box-shadow: 0 2px 10px rgba(0,0,0,0.1);
}

.orders-page h2 {
  color: #2c3e50;
  margin-bottom: 30px;
}

.no-data {
  text-align: center;
  padding: 40px;
  color: #999;
}

.no-data a {
  color: #42b983;
}

.orders-list {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.order-card {
  border: 1px solid #e0e0e0;
  border-radius: 10px;
  padding: 20px;
}

.order-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-bottom: 15px;
  border-bottom: 1px solid #e0e0e0;
  margin-bottom: 15px;
}

.order-no {
  font-weight: bold;
  color: #2c3e50;
}

.order-status {
  padding: 5px 15px;
  border-radius: 20px;
  font-size: 14px;
}

.status-pending {
  background-color: #f39c12;
  color: white;
}

.status-paid {
  background-color: #27ae60;
  color: white;
}

.status-cancelled {
  background-color: #95a5a6;
  color: white;
}

.order-info {
  margin-bottom: 15px;
}

.order-info p {
  color: #666;
  margin-bottom: 8px;
  font-size: 14px;
}

.order-info .price {
  color: #e74c3c;
  font-weight: bold;
  font-size: 16px;
}

.order-actions {
  display: flex;
  gap: 10px;
}

.btn-pay,
.btn-cancel {
  padding: 8px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 14px;
}

.btn-pay {
  background-color: #42b983;
  color: white;
}

.btn-cancel {
  background-color: #e74c3c;
  color: white;
}

.btn-pay:hover {
  background-color: #359268;
}

.btn-cancel:hover {
  background-color: #c0392b;
}
</style>
