<template>
  <div class="form-container" style="margin-top: 38.2%">
    <van-form @submit="login">
      <van-cell-group inset>
        <van-field
            v-model="username"
            name="用户名"
            label="用户名"
            placeholder="用户名"
            :rules="[{ required: true, message: '请填写用户名' }]"
        />
        <van-field
            v-model="password"
            type="password"
            name="密码"
            label="密码"
            placeholder="密码"
            :rules="[{ required: true, message: '请填写密码' }]"
        />
      </van-cell-group>
      <div style="margin: 16px;">
        <van-button round block type="primary" native-type="submit">
          提交
        </van-button>
      </div>
    </van-form>
  </div>


</template>

<script setup lang="ts">
import {ref} from 'vue'
import {useStore} from "vuex";
import myAxios from "../plugins/my-axios.ts";
import router from "../config/route.ts";

const store = useStore();
let username = ref('');
let password = ref('');

let login = async () => {
  let res = await myAxios.post('/user/login', {
    userAccount: username.value,
    userPassword: password.value,
  });
  if (res.code !== 0) return;
  await store.commit('updateUser', res.data);
  await router.push({path: '/'});
}


</script>

<style scoped>

</style>