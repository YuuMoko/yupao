<template>
  <div class="chat-container">
    <message-card-list :loading="false" :messageList="messageList" :avatar-url="avatarUrl" />
    <div class="chat-input">
      <van-field v-model="newMessage" placeholder="输入消息..." />
      <van-button type="primary" icon="send" @click="sendMessage">发送</van-button>
    </div>
  </div>
</template>

<script setup lang="ts">
import MessageCardList from "../components/MessageCardList.vue";
import {useRouter} from "vue-router";
import { ref } from "vue";

const socketUrl = "ws://"

const router = useRouter();

const socket = new WebSocket()


</script>

<style scoped>


.chat-container {
  display: flex;
  flex-direction: column;
  height: 100vh;
  padding-top: 16px; /* 留出顶部空间 */
  padding-bottom: 100px;
  background-color: #f7f7f7;
}

.user-message .van-cell__title,
.bot-message .van-cell__title {
  font-size: 12px;
}


/* 固定输入框在页面底部 */
.chat-input {
  position: fixed;
  bottom: 0;
  left: 0;
  width: 96%;
  display: flex;
  align-items: center;
  background-color: white;
  padding: 8px 16px;
  box-shadow: 0 -2px 8px rgba(0, 0, 0, 0.1);
  border-top: 1px solid #ebebeb;
  margin-bottom: 40px;
}

.chat-input .van-field {
  flex: 1;
}

.chat-input .van-button {
  margin-left: 8px;
}
</style>
