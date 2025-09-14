<template>

  <v-app>
    <!-- Top App Bar -->


    <v-app-bar app color="primary" dark>
      <v-app-bar-nav-icon @click="toggleDrawer"></v-app-bar-nav-icon>
      <v-toolbar-title>Spring Boot Startup</v-toolbar-title>
      <v-progress-linear color="bue-darken-2" indeterminate absolute bottom v-if="uiStore.loading">
      </v-progress-linear>
    </v-app-bar>

    <left-menu ref="leftMenu"/>

    <!-- Main Content -->
    <v-main>
      <v-snackbar-queue v-model="uiStore.messageList" />
      <v-container fluid>
         <RouterView/>
      </v-container>
    </v-main>
  </v-app>

</template>

<script>
import LeftMenu from "@/components/menu/LeftMenu.vue";
import {useUiStore} from "@/stores/uiStore.js";
import {useAuthStore} from "@/stores/authSore.js";
export default {
  name: "App",
  components: {LeftMenu},
  data() {
    return {
      uiStore:useUiStore()
    }
  },
  methods: {
    toggleDrawer() {
      this.$refs.leftMenu.toggle()

    }
  },
  beforeMount() {
    const authStore = useAuthStore()
    authStore.init()
    authStore.checkLogin()
  }
}
</script>

<style scoped>

</style>
