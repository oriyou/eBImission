import Vue from 'vue';
import Vuex from 'vuex';

import CartStore from './CartStore';

Vue.use(Vuex);

const store = new Vuex.Store({
  modules : {
      CartStore : CartStore,
  },
});

export default store