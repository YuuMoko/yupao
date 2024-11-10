<template>
  <van-skeleton title avatar :row="3" :loading="props.loading" v-for="message in props.messageList">
    <div v-if="message.userId == user.id" class="chat-message-right">
      <van-cell class="chat-message-item">
        <div class="message-content">
          <div class="message-text">{{ message.message }}</div>
        </div>
        <van-image top round width="2rem" height="2rem"  :src="user.avatarUrl" />
      </van-cell>
    </div>
    <div v-if="message.userId != user.id" class="chat-message-left">
      <van-cell class="chat-message-item">
        <van-image round width="2rem" height="2rem" :src="props.avatarUrl" />
        <div class="message-content">
          <div class="message-text">{{ message.message }}</div>
        </div>
      </van-cell>
    </div>
  </van-skeleton>
  <div style="padding: 40px 100%; background: #fff">Block</div>
</template>

<script setup lang="ts">
import {MessageType} from "../models/message";
import {getCurrentUser} from "../service/user.ts";
import {onMounted, ref} from "vue";

interface MessageCardListProps { // 这个传类型
  loading: boolean,
  messageList: MessageType[],
  avatarUrl: string,
}

const user = ref({})

onMounted(async () => {
  user.value = await getCurrentUser();
  console.log(user.value.id);
})

const props = withDefaults(defineProps<MessageCardListProps>(), { // 这个传默认的值
  loading: false,
  // @ts-ignore
  messageList: [] as MessageType[],
  avatarUrl: "https://cdn.acwing.com/media/user/profile/photo/411172_lg_50bf1e2bcb.jpg",
});

</script>

<style scoped>
.chat-message-left {
  display: flex;
  align-items: center;
  margin-bottom: 12px;
}

.chat-message-item {
  display: flex;
  align-items: center;
  padding: 0;
}

.message-content {
  margin-left: 12px;
  background-color: #f5f5f5;
  border-radius: 16px;
  padding: 8px 12px;
  max-width: 80%;
}

.message-text {
  font-size: 14px;
  color: #333;
}

.chat-message-right {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  margin-bottom: 12px;
}

.chat-message-item {
  display: flex;
  align-items: center;
  padding: 0;
}

.message-content {
  margin-right: 12px;
  background-color: #d1f7d1;
  border-radius: 16px;
  padding: 8px 12px;
  max-width: 80%;
}

.message-text {
  font-size: 14px;
  color: #333;
}
</style>