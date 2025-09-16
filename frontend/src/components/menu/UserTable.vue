<template>
  <v-row>
    <v-text-field label="username" max-width="300" v-model="username" @keydown.enter="loadItems"/>
    <v-text-field label="role" max-width="300" v-model="role" @keydown.enter="loadItems"/>
    <v-btn density="comfortable" icon="mdi-refresh" color="blue" @click="loadItems"></v-btn>
  </v-row>


  <v-data-table-server
    v-model:items-per-page="itemsPerPage"
    v-model:page="page"
    :headers="headers"
    :items="adminStore.userList"
    :items-length="adminStore.userPage.totalElements"
    :search="search"
    @update:options="loadItems"
  >
    <template v-slot:item.roleSet="{item}">
      <v-chip v-for="r in item.roleSet" :color="r.role==='ADMIN'?'primary':'secondary'" variant="flat">{{r.role}}</v-chip>
    </template>

    <template v-slot:item.actions="{item}">
      <v-btn density="comfortable" icon="mdi-delete" color="red" @click="removeUser(item)"></v-btn>
    </template>
  </v-data-table-server>
</template>

<script>
import {useAdminStore} from "@/stores/adminStore.js";

export default {
  name: 'UserTable',
  data() {
    return {
      adminStore:null,
      page:1,
      itemsPerPage: 10,
      username:'',
      role:'',
      headers: [
        { title: 'ID', align: 'start', sortable: false, key: 'id' },
        { title: 'Username', key: 'username', align: 'end' },
        { title: 'Roles', key: 'roleSet', align: 'end' },
        { title: 'Actions', key: 'actions', align: 'end' },
      ],
      search: '',
      loading: true,
      totalItems: 0,
    }
  },
  methods: {
    loadItems({ page,itemsPerPage }) {
      const filter={
        page:page?page-1:this.page-1,
        size:itemsPerPage?itemsPerPage:this.itemsPerPage,
        username:this.username,
        role:this.role
      }
      this.adminStore.getUserList(filter)
    },
    removeUser(item){
      if (confirm('Remove user ' + item.username + ' ?')){
        const page = this.page
        const itemsPerPage = this.itemsPerPage
        this.adminStore.removeUser(item).then(()=>this.loadItems({page,itemsPerPage}))
      }

    }
  },
  beforeMount() {
    this.adminStore = useAdminStore()
  },
}
</script>
