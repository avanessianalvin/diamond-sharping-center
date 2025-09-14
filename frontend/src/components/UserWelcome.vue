<template>
  <h3>
    Welcome {{ user.username }}
  </h3>
</template>

<script>

import {useAuthStore} from "@/stores/authSore.js";

export default {
  name: "UserWelcome",
  data() {
    return {
      user: null
    }
  },
  beforeMount() {
    this.user = useAuthStore().user
  }
}
</script>

<style scoped>

</style>
