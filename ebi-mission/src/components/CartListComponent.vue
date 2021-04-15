<template>
  <div class="cartListWrapper">
    <div class="cartHeader">
      <div class="checkboxSet">
        <input type="checkbox">
        <label>(주)아담스패밀리</label>
      </div>
    </div>
    <ul>
      <cart-product-component />
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
    
  }
}
</script>

<style>

</style>