<template>
  <user-card-list :user-list="userList" :loading="loading" />
  <van-empty v-if="!userList || userList.length < 1" description="搜索结果为空" />
</template>

<script setup lang="ts">

import myAxios from "../plugins/my-axios.ts";
import {showFailToast} from "vant";
import { ref, watchEffect } from 'vue';
import {UserType} from "../models/user";
import UserCardList from "../components/UserCardList.vue";

const userList = ref([]);
const loading = ref(true);

const loadData = async () => {
  let userListData;
  loading.value = true;
  userListData = await myAxios.get('/user/recommend', {
    params: {
      pageSize: 8,
      pageNum: 1,
    },
  })
      .then(function (response) {
        console.log('/user/recommend succeed', response);
        return response?.data?.records;
      })
      .catch(function (error) {
        console.error('/user/recommend error', error);
        showFailToast("请求失败");
      })
  if (userListData) { // 如果请求到数据
    userListData.forEach((user: UserType) => {
      if (user.tags) {
        user.tags = JSON.parse(user.tags);
      }
    })
    userList.value = userListData;
  }
  loading.value = false;
}
watchEffect(() => {
  loadData();
})

</script>

<style scoped>

</style>