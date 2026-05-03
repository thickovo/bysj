import axios from 'axios'

const api = axios.create({
  baseURL: 'http://localhost:8080/api',
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json'
  }
})

api.interceptors.request.use(config => {
  const token = localStorage.getItem('token')
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
})

export default {
  user: {
    register(data) {
      return api.post('/users/register', data)
    },
    login(data) {
      return api.post('/users/login', data)
    },
    getUserById(id) {
      return api.get(`/users/${id}`)
    }
  },

  route: {
    getAll() {
      return api.get('/routes')
    },
    getById(id) {
      return api.get(`/routes/${id}`)
    },
    search(keyword) {
      return api.get(`/routes/search?keyword=${keyword}`)
    },
    getAvailable() {
      return api.get('/routes/available')
    },
    add(data) {
      return api.post('/routes', data)
    },
    updateStock(id, quantity) {
      return api.put(`/routes/${id}/stock`, { quantity })
    }
  },

  order: {
    getAll() {
      return api.get('/orders')
    },
    getByUserId(userId) {
      return api.get(`/orders/user/${userId}`)
    },
    create(data) {
      return api.post('/orders', data)
    },
    pay(id) {
      return api.put(`/orders/${id}/pay`)
    },
    cancel(id) {
      return api.put(`/orders/${id}/cancel`)
    }
  }
}
