import Vue from 'vue';
import VueRouter from 'vue-router';

import { ProductView, ProductListView, CartView } from '../views';

Vue.use(VueRouter);

const routes = [
  { path: '/', redirect: '/product'},
  { path: '/product', component: ProductView,
    children: [
      { path: '/product/:dshopNo', component: ProductListView },
    ]  
  },
  { path: '/cart', component: CartView},
];

const router = new VueRouter({
  routes,
  mode: 'history'
});

// NavigationDuplicated 에러 무시
const originalPush = VueRouter.prototype.push;
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => {
    if(err.name != 'NavigationDuplicated') throw err;
  })
}

export default router