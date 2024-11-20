<template>
  <div class="chat-container">
      <message-card-list :loading="false" :avatarMap="avatarMap" :messageList="messageList" />
    <div class="chat-input">
      <van-field v-model="newMessage" placeholder="输入消息..." />
      <van-button type="primary" icon="send" @click="sendMessage">发送</van-button>
    </div>
  </div>
</template>

<script setup lang="ts">
import MessageCardList from "../components/MessageCardList.vue";
import {useRoute, useRouter} from "vue-router";
import {onMounted, onUnmounted, ref} from "vue";
import {useStore} from "vuex";
import myAxios from "../plugins/my-axios.ts";
import {MessageType} from "../models/message";

const store = useStore();
const route = useRoute();
const teamId = route.query.teamId;
const user = store.state.user;
const newMessage = ref('');

const socketUrl = `ws://127.0.0.1:3000/api/websocket/team/chat/${user.id}/${teamId}`;
const router = useRouter();
const socket = new WebSocket(socketUrl);

let avatarMap = new Map<number, string>();
let messageList = ref<MessageType[]>([]);

socket.onmessage = (msg) => {
  let data = JSON.parse(msg.data);
  messageList.value.push(data);
}

const sendMessage = () => {
  socket.send(newMessage.value);
  newMessage.value = "";
}

onMounted(async () => { // 从后端获取聊天记录

  let res = await myAxios.get("/chat/get/team/avatar", {
    params: {
      teamId: teamId,
    }
  });
  for (let key in res.data) {
    avatarMap.set(Number(key), String(res.data[key]));
  }

  res = await myAxios.get("/chat/get/message", {
    params: {
      teamId: teamId,
    }
  });

  let dataList = res.data;

  for (let i = dataList.length - 1; i >= 0; i --) {
    messageList.value.push(dataList[i]);
  }
});

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
