import Vue from 'vue'
import Vuex from 'vuex'
import AixosClient from '../api/AxiosClient'

Vue.use(Vuex)

const store = new Vuex.Store({
    state : {
        productList: [],
    },
    actions: {
        productListGet({commit}, ) {
            this.client = new AixosClient('/api/p/display/category/seltCatePdWishListAjax?pdSortCd=01&pageNo=1&rowsPerPage=60&dshopNo=22276');
            this.client.get()
                .then((res) => {
                    commit("getProductList", res.data);
                })
                .catch((res) => {
                    console.log("Fail get ProductList", res)
                });
        }
    }
})

export default store