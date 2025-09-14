<template>
  <ul>
    <li v-for="i in adminStore.userList" :key="i">
      {{i}}
    </li>
  </ul>

</template>

<script>
import {useAdminStore} from "@/stores/AdminStore.js";

export default {
  name: "UserList",
  data(){
    return{
      adminStore:null
    }
  },
  beforeMount() {
    this.adminStore = useAdminStore()
    this.adminStore.getUserList()
  }
}
</script>

<style scoped>

</style>
