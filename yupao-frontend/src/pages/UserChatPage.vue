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
import {onMounted, onUnmounted, ref} from 'vue';
import MessageCardList from "../components/MessageCardList.vue";
import {useRoute} from "vue-router";
import {useStore} from "vuex";
import {MessageType} from "../models/message";
import myAxios from "../plugins/my-axios.ts";

const route = useRoute();
const store = useStore();

let messageList = ref<MessageType[]>([]);
const userA = store.state.user;
let avatarUrl = ref('');

let idA = userA.id;
let idB = route.query.userId;

/**
 * private Long idA; // 用户A的ID
 * private Long idB; // 用户B的ID
 * private Long minMessageId; // 最小的Message的ID
 * private Long teamId; // 当前队伍的ID
 */


const newMessage = ref('');

const socketUrl = `ws://127.0.0.1:3000/api/websocket/user/chat/${idA}/${idB}`;
let socket = new WebSocket(socketUrl);
// 发送消息方法
onMounted(async () => {
  socket.onopen = () => {
    console.log("connected");
  };
  let res = await myAxios.get("/chat/get/user/byid", {
    params: {
      id: idB,
    }
  });
  avatarUrl.value = res.data.avatarUrl;

  res = await myAxios.get("/chat/get/message", {
    params: {
      idA: idA,
      idB: idB,
    }
  }); // 先获取当前两个用户的前二十条信息

  let dataList = res.data;
  for (let i = dataList.length - 1; i >= 0; i --) {
    messageList.value.push(dataList[i]);
  }
})

const sendMessage = () => {
  socket.send(JSON.stringify(newMessage.value)); // 把消息发过去
  newMessage.value = ""; // 把输入框清空
};

socket.onmessage = (msg) => {
  let data = JSON.parse(msg.data); // 解码成对象
  messageList.value.push(data);
  console.log(messageList.value);
}

onUnmounted(() =>{
  socket.close();
})

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
