<template>
  <van-search v-model="searchText" placeholder="搜索队伍" @search="onSearch" />
  <van-tabs v-model:active="active" @change="onTabChange">
    <van-tab title="公开" name="public" />
    <van-tab title="加密" name="private" />
  </van-tabs>
  <div style="margin-bottom: 16px">
    <van-button class="add-button" type="primary" icon="plus" @click="toAddTeam" />
    <team-card-list :teamList="teamList" @update:teamList="teamList = $event" />
    <van-empty v-if="teamList?.length < 1" description="数据为空"/>
  </div>
</template>

<script setup lang="ts">
import {onMounted, ref} from "vue";
import {useRouter} from "vue-router";
import myAxios from "../plugins/my-axios.ts";
import {showFailToast} from "vant";
import TeamCardList from "../components/TeamCardList.vue";


const active = ref('public');
const router = useRouter();
const searchText = ref('');
const teamList = ref([]);
const listTeam = async (val='', status=0) => {
  const res = await myAxios.get('/team/list', {
    params: {
      searchText: val,
      pageNum: 1,
      status,
    }
  });
  if (res?.code === 0) {
    teamList.value = res.data;
  } else {
    showFailToast("队伍加载失败，请重试");
  }
}
// 页面加载时只触发一次
onMounted(() => {
  listTeam();
})

const onSearch = (val) => {
  listTeam(val);
}

const onTabChange = (name) => {
  // 查公开
  if (name === 'public') {
    listTeam(searchText.value, 0);
  } else {
    // 查加密
    listTeam(searchText.value, 2);
  }
}

const toAddTeam = () => {
  router.push({
    path: "/team/add"
  })
}

</script>

<style scoped>

</style>