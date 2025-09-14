<template>
  <v-card class="mx-auto mt-12" min-width="300" max-width="400" elevation="4">
    <v-card-title class="text-h6">Login</v-card-title>
    <v-card-text>
      <v-form @keydown.enter="handleLogin">
        <v-text-field v-model="username" label="Username" prepend-icon="mdi-account" />
        <v-text-field v-model="password" label="Password" type="password" prepend-icon="mdi-lock" />
        <v-btn block color="primary" class="mt-4" @click="handleLogin">Login</v-btn>
      </v-form>
    </v-card-text>
  </v-card>
</template>

<script>
import {useAuthStore} from "@/stores/authSore.js";
export default {
  name: "LoginForm",
  data() {
    return { username: "", password: "" }
  },
  methods: {
    handleLogin() {
      useAuthStore().login(this.username,this.password)
    },
  }
}
</script>
