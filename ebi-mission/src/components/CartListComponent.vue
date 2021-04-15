<template>
  <div class="cartListWrapper">
    <div class="cartHeader">
      <div class="checkboxSet">
        <input type="checkbox">
        <label>{{this.trNo}}</label>
      </div>
    </div>
    <ul>
      <cart-product-component 
        v-for="item in this.group"
        :product="item"
        :key="item.cartSn"
      />
      <cart-product-component />
    </ul>
  </div>
</template>

<script>
import CartProductComponent from './CartProductComponent';

export default {
  name: 'CartListComponent',
  components: {
    CartProductComponent,
  },
  props: {
    group: Array,
  },
  data : function() {
    return {
      trNo: this.group[0].trNo,
    }
  },
}
</script>

<style>

</style>