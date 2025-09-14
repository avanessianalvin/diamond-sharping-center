import {defineStore} from 'pinia'

export const useUiStore = defineStore('ui', {
  state: () => ({
    loading: false,
    messageList: [],
    currentMessage:null
  }),
  actions: {
    startLoading() {
      this.loading = true
    },
    stopLoading() {
      this.loading = false
    },
    addMessage(text, color = 'primary', timeout = 2500) {
      const m = {text, color, timeout}
      this.messageList.push(m)
    },
    addErrorMessage(text) {
      const m =
        {
          text,
          color:'error',
          timeout:4000
        }
      this.messageList.push(m)
    },



  }
})
