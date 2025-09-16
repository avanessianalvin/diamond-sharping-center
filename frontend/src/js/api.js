import axios from 'axios';
import {useAuthStore} from "@/stores/authSore.js";
import {useUiStore} from "@/stores/uiStore.js";
import { jwtDecode } from "jwt-decode";



const baseURL = 'http://localhost:8080/api';

const api = axios.create({
  baseURL: baseURL,
  withCredentials: true,
});

const refreshApi = axios.create({
  baseURL,
  withCredentials: true,
});

api.interceptors.request.use(async (config) => {
  useUiStore().startLoading()
  const authStore = useAuthStore();
  let token = sessionStorage.getItem("accessToken")// authStore.accessToken;

  if (token) {
    const decoded = jwtDecode(token);
    const exp = decoded.exp * 1000; // exp in ms
    const now = Date.now();

    if (exp < now + 5000) { // if token will expire in next 5 sec
      // Refresh before sending request
      try {
        const refreshResponse = await refreshApi.post('/auth/refresh');
        const newToken = refreshResponse.data.payload.accessToken;
        authStore.accessToken = newToken;
        sessionStorage.setItem("accessToken",newToken)
        token = newToken;
      } catch (e) {
        authStore.logout();
        sessionStorage.clear()
        console.log("Error")
        useUiStore().addErrorMessage("Not logged in")
        //return Promise.reject(err);
      }
    }
    config.headers['Authorization'] = `Bearer ${token}`;
  }

  return config;
}, (error) => {
  useUiStore().stopLoading()
  return Promise.reject(error)
});


// Response interceptor: handle 401 and refresh token
api.interceptors.response.use(
  response => {
    useUiStore().stopLoading()
    const message = response.data.message
    if (message){
      useUiStore().addMessage(message)
    }
    return response
  },
  async error => {
    useUiStore().stopLoading()

    const originalRequest = error.config;

    if (error.response?.status === 401 && !originalRequest._retry) {
      originalRequest._retry = true;
      try {
        // Use refreshApi (no interceptors) to avoid recursion
        const refreshResponse = (await refreshApi.post('/auth/refresh'));
        const newAccessToken = refreshResponse.data.payload.accessToken;
        useAuthStore().accessToken = newAccessToken

        // Retry original request with new token
        originalRequest.headers['Authorization'] = `Bearer ${newAccessToken}`;
        return api(originalRequest);
      } catch (refreshError) {
        useAuthStore().logout()
        sessionStorage.clear()
        console.log("No refresh token!")
        useUiStore().addErrorMessage("Not logged in")
        //console.log(refreshError)
        //return Promise.reject(refreshError);
      }
    }

    const message = error.message
    if (message){
      useUiStore().addErrorMessage(message)
    }
  }
);

export default api;
