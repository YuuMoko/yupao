import Index from '../pages/Index.vue'
import Team from '../pages/TeamPage.vue'
import UserLogin from '../pages/UserLogin.vue'
import SearchPage from "../pages/SearchPage.vue";
import SearchResultPage from "../pages/SearchReslutPage.vue";
import UserPage from "../pages/UserPage.vue";
import * as VueRouter from "vue-router";
import store from '../store/index'
import UserUpdatePage from "../pages/UserUpdatePage.vue";
import UserEditPage from "../pages/UserEditPage.vue";
import TeamAddPage from '../pages/TeamAddPage.vue';
import TeamUpdatePage from "../pages/TeamUpdatePage.vue";
import UserRegisterPage from "../pages/UserRegisterPage.vue";
import UserChatPage from "../pages/UserChatPage.vue";
import UserTeamCreatePage from '../pages/UserTeamCreatePage.vue';
import UserTeamJoinPage from '../pages/UserTeamJoinPage.vue';
import UserTagEditPage from '../pages/UserTagEditPage.vue';
import TeamChatPage from '../pages/TeamChatPage.vue';

// 定义路由


const routes = [
    { path: '/', component: Index },
    { path: '/team', title: '找队伍', component: Team },
    { path: '/user/login', title: '登录', component: UserLogin },
    { path: '/search', title: '搜索页', component: SearchPage },
    { path: '/user/list', title: '搜索页', component: SearchResultPage },
    { path: '/user', title: "用户个人信息", component: UserPage},
    { path: '/user/update', title: "修改信息页", component: UserUpdatePage },
    { path: '/user/edit', title: "编辑信息页", component: UserEditPage },
    { path: '/team/add', title: "队伍创建页", component: TeamAddPage },
    { path: '/team/update', title: "更新队伍", component: TeamUpdatePage },
    { path: '/user/register', title: "注册", component: UserRegisterPage },
    { path: '/user/chat', title: "用户聊天页", component: UserChatPage },
    { path: '/user/team/create', title: "用户队伍创建", component: UserTeamCreatePage },
    { path: '/user/team/join', title: "用户队伍加入", component: UserTeamJoinPage },
    { path: '/user/tag/edit', title: "用户标签修改页", component: UserTagEditPage },
    { path: '/team/chat', title: "队伍聊天页", component: TeamChatPage },

]

const router = VueRouter.createRouter({
    // 内部提供了 history 模式的实现。为了简单起见，我们在这里使用 hash 模式。
    history: VueRouter.createWebHistory(),
    routes, // `routes: routes` 的缩写
})

router.beforeEach(async (to, from) => {
    const isAuthenticated = store.state.user.user !== null;
    if (
        // 检查用户是否已登录
        !isAuthenticated &&
        // ❗️ 避免无限重定向
        to.path !== '/user/login' && to.path !== '/user/register'
    ) {
        // 将用户重定向到登录页面
        return { path: '/user/login' }
    }
})


export default router;