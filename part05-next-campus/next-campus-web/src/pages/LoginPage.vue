<template>
  <v-container>
    <v-row justify="center">
      <v-col cols="12" md="4">
        <v-card>
          <v-card-title>Login</v-card-title>
          <v-card-text>
            <v-form @submit.prevent="login">
              <v-text-field v-model="email" label="Email" type="email" required></v-text-field>
              <v-text-field v-model="password" label="Password" type="password" required></v-text-field>
              <v-btn type="submit" color="primary">Login</v-btn>
            </v-form>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import axios from 'axios';
import { ref } from 'vue';
import { useRouter } from 'vue-router';

export default {
  name: 'LoginPage',
  setup() {
    const email = ref('');
    const password = ref('');
    const router = useRouter();

    const login = async () => {
      try {
        const response = await axios.post('http://localhost:9000/auth/token', {
          email: email.value,
          password: password.value,
        });

        const token = response.data.token;
        localStorage.setItem('jwt', token);

        const validateResponse = await axios.post('http://localhost:9000/auth/validate', {
          token: token,
        });

        const userId = validateResponse.data.id;
        localStorage.setItem('userId', userId);

        axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;

        router.push('/courses');
      } catch (error) {
        console.error('Login failed:', error);
        alert('Login failed. Please check your credentials.');
      }
    };

    return {
      email,
      password,
      login,
    };
  },
};
</script>
