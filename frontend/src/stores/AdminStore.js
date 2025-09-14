import {defineStore} from 'pinia'
import api from "@/js/api.js";

export const useAdminStore = defineStore('adminStore', {
  state: () => ({
    userList:[]
  }),
  actions: {
    getUserList(){
      api.get("/admin/user/all")
        .then(res=>{
          this.userList = res.data.data
          console.log(res.data)
        })

    }
  }
})
