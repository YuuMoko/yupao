<template>
  <div class="form-container" style="margin-top: 38.2%">
    <van-form @submit="login">
      <van-cell-group inset>
        <div style="text-align: center; font-weight: 450; font-size: 30px; color: #1989fa;">登录</div>
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
      <div style="text-align: center; margin: 8px 0;">
        <div>没有账号？</div>
        <a href="/user/register" style="font-size: 14px; color: #1989fa; text-decoration: underline; cursor: pointer;">
          去注册
        </a>
      </div>
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
import {showFailToast} from "vant";

const store = useStore();
let username = ref('');
let password = ref('');

let login = async () => {
  let res = await myAxios.post('/user/login', {
    userAccount: username.value,
    userPassword: password.value,
  });
  if (res.code !== 0) {
    showFailToast("登录失败，请重新输入账号密码")
    return;
  }
  await store.commit('updateUser', res.data);
  await router.push({path: '/'});
}


</script>

<style scoped>

</style>