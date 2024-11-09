import myAxios from "../plugins/my-axios.ts";
import store from '../store'

export const getCurrentUser = async () => {
    let res = await myAxios.get('/user/current')
    if (res.code === 0) {
        store.commit("updateUser", res.data);
        return res.data;
    }
    return null;
}
