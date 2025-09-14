<template>
  <!-- Navigation Drawer / Sidebar -->
  <v-navigation-drawer app v-model="show" temporary>
    <v-list>
      <v-list-item link>
        <RouterLink to="/">Home</RouterLink>
      </v-list-item>
      <v-list-item link v-if="authStore.isUser">
        <RouterLink to="/dashboard">Dashboard</RouterLink>
      </v-list-item>
      <v-list-item link v-if="authStore.isAdmin">
        <RouterLink to="/admin">Admin</RouterLink>
      </v-list-item>
      <v-list-item link>
        <RouterLink to="/about">About</RouterLink>
      </v-list-item >
        <v-list-item v-if="authStore.isUser" @click="logout" style="cursor: pointer;">
          <v-list-item-title>Logout</v-list-item-title>
        </v-list-item>
    </v-list>
  </v-navigation-drawer>
</template>

<script>
import {useAuthStore} from "@/stores/authSore.js";

export default {
  name: "LeftMenu",
  data(){
    return{
      show:false,
      authStore:null
    }
  },
  methods:{
    toggle(){
      this.show = !this.show
    },
    logout(){
      this.authStore.logout()
    }
  },
  beforeMount() {
    this.authStore = useAuthStore()
  }
}
</script>

<style scoped>
/* Optional: style links inside drawer */
.v-list-item a {
  text-decoration: none;
  color: inherit;
  width: 100%;
  display: block;
  padding: 8px 0;
}

.v-list-item a.router-link-exact-active {
  font-weight: bold;
  text-decoration: underline;
}
</style>
