<template>
  <v-app>
    <v-app-bar app>
      <v-toolbar-title>NEXT CAMPUS</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-btn text v-if="!isLoggedIn" to="/">Login</v-btn>
      <v-btn text v-else @click="logout">Logout</v-btn>
      <v-btn text to="/courses">Courses</v-btn>
      <v-btn text to="/history">Purchase History</v-btn>
    </v-app-bar>
    <v-main>
      <router-view/>
    </v-main>
  </v-app>
</template>

<script>
import { provideApolloClient } from "@vue/apollo-composable";
import apolloClient from "@/apollo";
import { ref, watchEffect } from "vue";
import { useRouter } from "vue-router";

provideApolloClient(apolloClient);

export default {
  name: 'App',
  setup() {
    const isLoggedIn = ref(!!localStorage.getItem('jwt'));
    const router = useRouter();

    const logout = () => {
      localStorage.removeItem('jwt');
      localStorage.removeItem('userId');
      isLoggedIn.value = false;
      router.push('/');
    };

    // Watch for changes in localStorage
    watchEffect(() => {
      isLoggedIn.value = !!localStorage.getItem('jwt');
    });

    return {
      isLoggedIn,
      logout,
    };
  },
}
</script>

<style>
/* 필요에 따라 스타일 추가 */
</style>
