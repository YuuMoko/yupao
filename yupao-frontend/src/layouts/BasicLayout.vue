<template>
  <van-nav-bar
      :title="title"
      left-arrow
      @click-left="onClickLeft"
      @click-right="onClickRight"
  >
    <template #right>
      <van-icon name="search" size="18"/>
    </template>
  </van-nav-bar>
  <div id="content">
    <router-view/>
  </div>
  <van-tabbar route >
    <van-tabbar-item to="/" icon="home-o" name="index">主页</van-tabbar-item>
    <van-tabbar-item to="/team" icon="friends-o" name="team">队伍</van-tabbar-item>
    <van-tabbar-item to="/user" icon="user-o" name="user">个人</van-tabbar-item>
  </van-tabbar>
</template>

<script setup lang="ts">
import {ref} from "vue";
import 'vant/es/toast/style';
import { useRouter } from 'vue-router';

const router = useRouter()
const onClickLeft = () => {
  if (router.currentRoute.value.path !== '/') {
    router.back();
  }
}
const onClickRight = () => {
  router.push('/search')
}

const active = ref('index');

</script>


<style scoped>
#content {
  padding-bottom: 50px;
}



</style>