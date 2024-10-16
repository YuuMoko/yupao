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