<template>
  <user-card-list :user-list="userList" :loading="loading" />
  <van-empty v-if="!userList || userList.length < 1" description="搜索结果为空" />
</template>

<script setup lang="ts">
import { useStore } from "vuex";
import {onMounted} from "vue";
import myAxios from "../plugins/my-axios.ts";
import qs from 'qs';
import { ref } from 'vue';
import {UserType} from "../models/user";
import UserCardList from "../components/UserCardList.vue";


const store = useStore();
const userList = ref([]);
let loading = true;
let searchList = store.state.searchList;
onMounted(async () => {
  const userListData: UserType[] = await myAxios.get('/user/search/tags', {
    params: {
      tagList: [...searchList],
    },
    paramsSerializer: params => {
      return qs.stringify(params, { arrayFormat: "repeat" });
    }
  })
      .then(function (response) {
        return response?.data;
      })
      .catch(function (error) {
        console.log(error);
      })
      .finally(function () {
        // 总是会执行
      });
  userListData.forEach(user => {
    if (user.tags) {
      user.tags = JSON.parse(user.tags);
    }
  });
  userList.value = userListData;
  loading = false;

})

</script>

<style scoped>

</style>