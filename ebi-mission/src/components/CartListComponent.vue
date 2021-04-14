<template>
  <div>
    <div class="cartHeader">
      <input type="checkbox">
      
    </div> 
    <ul>
      <cart-product-component />
    </ul>
  </div>
</template>

<script>
import CartProductComponent from './CartProductComponent';

export default {
  name: 'CartListComponent',
  components: { CartProductComponent },
}
</script>

<style>
  
</style>