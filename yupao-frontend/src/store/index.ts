import { createStore } from 'vuex';


const store = createStore({
    state: () => {
        return {
            searchList: [],
        }
    },
    getters: {

    },
    mutations: {
        updateSearchList(state, list) {
            state.searchList = list;
        }
    },
    actions: {

    },
    modules: {

    }
})

export default store;