<template>
  <div class="chat-container">
    <message-card-list />
    <!-- 输入框和发送按钮，固定在页面底部 -->
    <div class="chat-input">
      <van-field v-model="newMessage" placeholder="输入消息..." />
      <van-button type="primary" icon="send" @click="sendMessage">发送</van-button>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import MessageCardList from "../components/MessageCardList.vue";

let currentMessageList = ref([]);
let dataMessageList = ref([]);


const messages = ref([
  { sender: 'bot', content: '您好，有什么可以帮您的吗？', time: '10:00' },
  { sender: 'user', content: '我想了解一下产品详情。', time: '10:01' },
]);

const newMessage = ref('');

// 发送消息方法
const sendMessage = () => {
  if (!newMessage.value.trim()) return;

  // 添加用户消息
  messages.value.push({
    sender: 'user',
    content: newMessage.value,
    time: new Date().toLocaleTimeString(),
  });

  // 清空输入框
  newMessage.value = '';

  // 模拟客服回复
  setTimeout(() => {
    messages.value.push({
      sender: 'bot',
      content: '好的，我们的产品有多种类型，您想了解哪一类？',
      time: new Date().toLocaleTimeString(),
    });
  }, 1000);
};
</script>

<style scoped>
.chat-container {
  display: flex;
  flex-direction: column;
  height: 100vh;
  padding-top: 16px; /* 留出顶部空间 */
  background-color: #f7f7f7;
}

.chat-messages {
  flex: 1;
  overflow-y: auto;
  padding-bottom: 60px; /* 留出底部空间，以免输入框遮挡消息 */
  margin-bottom: 10px;
}

.message {
  margin-bottom: 8px;
}

.user-message .van-cell__title,
.bot-message .van-cell__title {
  font-size: 12px;
}

.user-message {
  text-align: right;
}

.bot-message {
  text-align: left;
}

/* 固定输入框在页面底部 */
.chat-input {
  position: fixed;
  bottom: 0;
  left: 0;
  width: 95%;
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
