import {CartApi} from '~/api';

const CartStore = {
  namespaced: true,
  state : {
    orgCartList: [],
    groupingCartArr: [],
    totalPrice: 0,
    totalOdQty: 0,
  },
  getters: {
    getGroupingCartArr(state) {
      state.groupingCartArr = [];
      let tempCart = {};

      state.orgCartList.forEach(e => {    
        if(!Object.keys(tempCart).includes(e.trNo)) {
          tempCart[e.trNo] = [];
        }
        tempCart[e.trNo].push(e);
      });

      Object.keys(tempCart).forEach(key => {
        state.groupingCartArr.push(tempCart[key])
      });
      
      return state.groupingCartArr;
    },
  },
  mutations: {
    setCartList: (state, payload) => {
      state.orgCartList = payload;
    },
    initTotalValue: (state) => {
      state.totalPrice = 0;
      state.totalOdQty = 0;
      state.orgCartList.forEach(item => {
        state.totalPrice += item.slPrc*item.odQty;
        state.totalOdQty += item.odQty*1;
      });     
    },
    addTotalValue: (state, payload) => {
      state.totalPrice += Number(payload.slPrc);
      state.totalOdQty++;
    },
    minusTotalValue: (state, payload) => {
      state.totalPrice -= payload.slPrc;
      state.totalOdQty--;
    }
  },
  actions: {
    retrieveCart: ({commit}) => {
        return CartApi.get().then(response => {
          commit('setCartList', response);
          commit('initTotalValue');
        })
    },
    updateCart: (context, product) => {
      // 액션이 완료되었는지 확인하기 위해 Promise를 반환
      return new Promise((resolve, reject) => {
        CartApi.modify(product)
          .then(response => resolve(response))
          .catch(error => reject(error));
      })
    },
    deleteCart: ({dispatch}, cartSnArr) => {
      CartApi.remove(cartSnArr).then(() => dispatch('retrieveCart'));
    },
  }
}

export default CartStore