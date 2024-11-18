<template>
  <van-form @submit="onSubmit">
    <van-cell-group inset>
      <van-tree-select
          v-model:active-id="activeIds"
          v-model:main-active-index="activeIndex"
          :items="tagList"
      />
    </van-cell-group>
    <div style="margin: 16px;">
      <van-button round block type="primary" native-type="submit">
        提交
      </van-button>
    </div>
  </van-form>
</template>

<script setup lang="ts">

import { useStore } from "vuex";
import {onMounted, ref} from "vue";
import myAxios from "../plugins/my-axios.ts";
import {showFailToast, showSuccessToast} from "vant";
import {useRouter} from "vue-router";
import {getCurrentUser} from "../service/user.ts";

const store = useStore();
let user = store.state.user;

const tagList = store.state.originTagList;
const activeIds = ref([]);
const activeIndex = ref(0);
const router = useRouter();

const onSubmit = async () => {
  let params = {
    id: user.id,
    tags: JSON.stringify([...activeIds.value]),
  };

  let res = await myAxios.post("/user/update", params);

  if (res.code != 0) {
    showFailToast(res.description);
    return;
  }
  showSuccessToast("修改成功");

  user = getCurrentUser();

  await router.push({
    path: "/user/update",
    replace: true,
  })
}

onMounted(async () => {
  if (user.tags != null) {
    activeIds.value = JSON.parse(user.tags);
  }
})

</script>

<style scoped>

</style>