import {defineStore} from 'pinia'
import api from '@/js/api.js'
import router from "@/router/index.js";

export const useAuthStore = defineStore('auth', {
  state: () => ({
    user: null,
    roles:[],
    accessToken: null,
    refreshToken: null,
    error: null,
  }),

  getters: {
    isAuthenticated: (state) => !!state.accessToken,
    getUser: (state) => state.user,
    isAdmin: (state) => state.roles.includes('ADMIN'),
    isUser: (state) => state.roles.includes('USER')
  },

  actions: {

    setUser(user){
      this.user = user;
      this.roles = user && user.roleSet? user.roleSet.map(r=>r.role) : []
    },

    removeUser(){
      this.user = null
      this.roles = []
    },

    async login(username, password) {
      this.error = null
      try {
        const response = await api.post('/auth/login', {
          username,
          password,
        })

        this.accessToken = response.data.data.accessToken
        sessionStorage.setItem("accessToken",this.accessToken)
        this.setUser(response.data.data.user)
        router.push("/dashboard")
        console.log("you are logged in")


        return true
      } catch (err) {
        this.error = err.response?.data?.message || 'Login failed'
        return false
      }
    },

    checkLogin() {
      api.get("/auth/me")
        .then(r=>{
          this.setUser(r.data.data)
        }).catch((e)=>{
          sessionStorage.clear()
        console.log("not logged in")
      })
    },

    init(){
      this.accessToken = sessionStorage.getItem("accessToken")
    },

    logout() {
      api.get('/auth/logout').then(()=>{})
      sessionStorage.clear()
      this.removeUser()
      this.accessToken = null
      this.error = null
      router.push("/login")
      console.log("you are logged out")
    },

    pingUser(){
      api.get("/user/ping").then((r)=>{
        console.log(r)
      })
    },
    pingAdmin(){
      api.get("/admin/ping").then((r)=>{
        console.log(r)
      })
    }
  },
})
