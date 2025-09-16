<template>
  <h2>Dashboard</h2>
  <user-welcome/>
</template>

<script>
import UserWelcome from "@/components/UserWelcome.vue";
import {useAuthStore} from "@/stores/authSore.js";

export default {
  name: "DashboardView",
  components: {UserWelcome},
  methods:{
    pingUser(){
      useAuthStore().pingUser()
    },
    pingAdmin(){
      useAuthStore().pingAdmin()
    },
  },


}
</script>

<style scoped>

</style>
