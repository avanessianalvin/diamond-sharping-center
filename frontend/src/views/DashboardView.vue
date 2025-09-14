<template>
  <h2>Dashboard</h2>
  <user-welcome/>

  <v-btn @click="pingUser">ping user</v-btn>
  <v-btn @click="pingAdmin">ping admin</v-btn>

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
