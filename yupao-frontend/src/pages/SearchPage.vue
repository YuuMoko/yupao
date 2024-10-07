<template>
  <form action="/">
    <van-search
        v-model="searchText"
        show-action
        placeholder="请输入要搜索的标签"
        @search="onSearch"
        @cancel="onCancel"
    />
  </form>

  <van-divider
      :style="{ color: '#1989fa', borderColor: '#1989fa', padding: '0 16px' }"
  >
    已选标签
  </van-divider>
  <div v-if="activeIds.length === 0" style="text-align: center; font-style: italic">请选择标签</div>
  <van-row>
    <van-col v-for="tag in activeIds" >
      <van-tag closeable @close="doClose(tag)">{{ tag }}</van-tag>
    </van-col>
  </van-row>

  <van-divider
      :style="{ color: '#1989fa', borderColor: '#1989fa', padding: '0 16px' }"
  >
    未选标签
  </van-divider>

  <van-tree-select
      v-model:active-id="activeIds"
      v-model:main-active-index="activeIndex"
      :items="tagList"
  />
<van-button type="primary" style="width: 100%" @click="doSearchResult">搜索</van-button>

</template>

<script setup lang="ts">
import { ref } from 'vue';
import { useRouter } from "vue-router";
import { useStore } from "vuex";

const store = useStore();
const router = useRouter();
const searchText = ref('');
const activeIds = ref([]);
const activeIndex = ref(0);
// 原始列表
const originTagList = [{
  text: '性别',
  children: [
    {text: '男', id: '男'},
    {text: '女', id: '女'},
  ],
},
  {
    text: '年级',
    children: [
      {text: '大一', id: '大一'},
      {text: '大二', id: '大二'},
      {text: '大三', id: '大三'},
      {text: '大四', id: '大四'},
    ],
  },
];
// 显示的列表
let tagList = ref([]);
tagList.value = originTagList;

const doClose = (tag) => {
  activeIds.value = activeIds.value.filter(s => {return s !== tag})
  searchText.value = "";
}
const onSearch = () => {
  if (searchText.value === "") {
    onCancel();
    return;
  }
  tagList.value = originTagList.map(parentTag => {
    let tempChildren = [...parentTag.children];
    let tempParentTag = {...parentTag};
    tempParentTag.children = tempChildren.filter(item =>{
    return item.text.includes(searchText.value)
    })
    return tempParentTag;
  }).filter(parentTag => parentTag?.children.length > 0);

}
const onCancel = () => {
  tagList.value = originTagList;
}
const doSearchResult = () => {
  store.commit("updateSearchList", [...activeIds.value]);
  router.push('/user/list');
}



</script>

<style scoped>

</style>