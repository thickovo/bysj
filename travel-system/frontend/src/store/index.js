import { createStore } from 'vuex'

export default createStore({
  state: {
    isLoggedIn: false,
    currentUser: null,
    token: null
  },
  mutations: {
    SET_USER(state, user) {
      state.currentUser = user
      state.isLoggedIn = true
      state.token = user ? user.id : null
    },
    LOGOUT(state) {
      state.currentUser = null
      state.isLoggedIn = false
      state.token = null
    }
  },
  actions: {
    login({ commit }, user) {
      commit('SET_USER', user)
    },
    logout({ commit }) {
      commit('LOGOUT')
    }
  },
  modules: {}
})
