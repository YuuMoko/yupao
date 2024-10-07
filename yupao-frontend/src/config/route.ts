import Index from '../pages/Index.vue'
import Team from '../pages/Team.vue'
import UserLogin from '../pages/UserLogin.vue'
import User from '../pages/User.vue'
import SearchPage from "../pages/SearchPage.vue";
import SearchResultPage from "../pages/SearchReslutPage.vue";
// 定义路由

const routes = [
    { path: '/', component: Index },
    { path: '/team', title: '找队伍', component: Team },
    { path: '/user/login', title: '登录', component: UserLogin },
    { path: '/user', title: '个人页', component: User },
    { path: '/search', title: '搜索页', component: SearchPage },
    { path: '/user/list', title: '搜索页', component: SearchResultPage },
]


export default routes