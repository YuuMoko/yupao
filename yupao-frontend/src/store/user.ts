import { createStore } from 'vuex';
import {UserType} from "../models/user";

export interface State {
    user: UserType | null;
}

const store = createStore({
    namespaced: true,
    state: () => {
        return {
            user: null,
        }
    },
    getters: {
    },
    mutations: {
        updateUser(state:any, user:UserType) {
            state.user = user;
        }
    },
    actions: {

    },
    modules: {

    }
})

export default store;