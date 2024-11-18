<template>
  <template v-if="user">
    <van-cell title="昵称" is-link to="/user/edit" :value="user.username"  @click="toEdit('username', '昵称', user.username)"/>
    <van-cell title="账号" :value="user.userAccount"/>
    <van-cell title="头像" >
      <img style="height: 48px" :src="user.avatarUrl"/>
      <van-uploader :after-read="updateAvatar">
        <van-button icon="plus" type="primary">上传头像</van-button>
      </van-uploader>
    </van-cell>
    <van-cell title="标签修改" is-link to="/user/tag/edit" />
    <van-cell title="性别" is-link :value="user.gender ? '女' : '男'" @click="toEdit('gender', '性别', user.gender ? '女' : '男')"/>
    <van-cell title="电话" is-link to="/user/edit" :value="user.phone" @click="toEdit('phone', '电话', user.phone)"/>
    <van-cell title="邮箱" is-link to="/user/edit" :value="user.email" @click="toEdit('email', '邮箱', user.email)"/>
    <van-cell title="注册时间" :value="user.createTime"/>
  </template>
</template>

<script setup lang="ts">
import {useRouter} from "vue-router";
import {onMounted, ref} from "vue";
import {getCurrentUser} from "../service/user.ts";
import myAxios from "../plugins/my-axios.ts";
import {showFailToast} from "vant";

const user = ref();

onMounted(async () => {
  user.value = await getCurrentUser();
})

const router = useRouter();

const updateAvatar = async (file) => {
  let formData = new FormData();
  formData.append("file", file.file);
  let res = await myAxios.post("/user/avatar/update", formData,{
    headers: {
      'Content-Type': 'multipart/form-data'
    }})
  if (res.code === 0) {
    user.value.avatarUrl = res.data;
  } else {
    showFailToast(res.description);
  }
}

const toEdit = (editKey: string, editName: string, currentValue: string) => {
  router.push({
    path: '/user/edit',
    query: {
      editKey,
      editName,
      currentValue,
    }
  })
}
</script>

<style scoped>

</style>