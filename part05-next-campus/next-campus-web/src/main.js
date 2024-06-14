import { createApp, h } from 'vue';
import App from './App.vue';
import router from './router';
import vuetify from './plugins/vuetify'; // Vuetify 플러그인 가져오기

const app = createApp({
    render: () => h(App),
});

// app.use(apolloProvider);
app.use(router);
app.use(vuetify); // Vuetify 사용

app.mount('#app');
