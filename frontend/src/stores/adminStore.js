import {defineStore} from 'pinia'
import api from "@/js/api.js";

export const useAdminStore = defineStore('adminStore', {
  state: () => ({
    userList:[],
    userPage: {
      size: 0,
      number: 0,
      totalElements: 0,
      totalPages: 0
    }
  }),
  actions: {

      getUserList(filter){
      api.get("/user/all",{params:filter})
        .then(res=>{
          this.userList = res.data.payload.content
          this.userPage = res.data.payload.page
        })
    },

    removeUser(user){
        return api.post("/user/remove",user)
    }
  }
})
