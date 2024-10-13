<template>
  <template v-if="user">
    <van-cell title="当前用户" :value="user?.username" />
    <van-cell title="修改信息" is-link to="/user/update" />
  </template>
</template>

<script setup lang="ts">

import {getCurrentUser} from "../service/user.ts";
import {ref, onMounted} from 'vue'

const user = ref();

onMounted(async () => {
  user.value = await getCurrentUser();
})

</script>

<style scoped>

</style>