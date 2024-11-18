// @ts-ignore
import { createStore } from 'vuex';
import ModuleUser from './user.ts'
import {UserType} from "../models/user";

export interface State {
    user: null | UserType
}

const store = createStore({
    namespaced: true,
    state: () => {
        return {
            searchList: [],
            user: null,
            originTagList: [{
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
                {
                    text: '编程语言',
                    children: [
                        {text: 'Java', id: 'Java'},
                        {text: 'C++', id: 'C++'},
                        {text: 'Python', id: 'Python'},
                        {text: 'Go', id: 'Go'},
                        {text: 'Lisp', id: 'Lisp'},
                        {text: 'Rust', id: 'Rust'},
                    ],
                },
                {
                    text: '职业发展方向',
                    children: [
                        {text: '算法工程师', id: '算法工程师'},
                        {text: '网络安全工程师', id: '网络安全工程师'},
                        {text: 'Java后端开发', id: 'Java后端开发'},
                        {text: '前端开发', id: '前端开发'},
                        {text: '游戏开发', id: '游戏开发'},
                    ],
                },
                {
                    text: '工作年限',
                    children: [
                        {text: '工作一年', id: '工作一年'},
                        {text: '工作两年', id: '工作两年'},
                        {text: '工作三年以上', id: '工作三年以上'},
                        {text: '工作五年以上', id: '工作五年以上'},
                        {text: '工作十年以上', id: '工作十年以上'},
                    ],
                },
            ]
        }
    },
    getters: {

    },
    mutations: {
        // @ts-ignore
        updateSearchList(state, list) {
            state.searchList = list;
        },
        // @ts-ignore
        updateUser(state, user) {
            state.user = user;
        }
    },
    actions: {

    },
    modules: {
        user: ModuleUser,
    }
})

export default store;