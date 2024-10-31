<template>
  <template v-if="user">
    <van-cell title="当前用户" :value="user?.username" />
    <van-cell title="修改信息" is-link to="/user/update" />
    <van-button type="danger" @click="logout" style="width: 100%">注销</van-button>
  </template>
</template>

<script setup lang="ts">

import {getCurrentUser} from "../service/user.ts";
import {ref, onMounted} from 'vue'
import { useStore } from "vuex";
import myAxios from "../plugins/my-axios.ts";
import router from "../config/route.ts";
import {showFailToast} from "vant";

const user = ref();
const store = useStore();

onMounted(async () => {
  user.value = await getCurrentUser();
})

const logout = () => {
  myAxios.post("/user/logout", {
  });
  router.push("/user/login").then((response) => {
    if (response.code !== 0) {
      showFailToast("注销失败");
    }
    store.commit("updateUser", null);
  })
}

</script>

<style scoped>

</style>