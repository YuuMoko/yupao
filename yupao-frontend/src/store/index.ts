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
            ]
        }
    },
    getters: {

    },
    mutations: {
        updateSearchList(state, list) {
            state.searchList = list;
        },
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