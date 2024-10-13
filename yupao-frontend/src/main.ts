import {createApp} from 'vue'
import App from './App.vue'
import router from "./config/route.ts";
import Vant from 'vant';
import 'vant/lib/index.css';
import '../global.css'
import store from './store'

const app = createApp(App);
app.use(Vant);
app.use(store);
app.use(router);
app.mount('#app')
