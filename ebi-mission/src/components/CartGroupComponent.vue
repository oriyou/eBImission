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
        @addGroupPrice="addGroupPrice"
        @minusGroupPrice="minusGroupPrice"
      />
    </ul>
    <div class="cartFooter">
      <p class="groupPrice"><strong>{{this.computedGroupPrice}}</strong>원</p>
    </div>
  </div>
</template>

<script>
import CartProductComponent from './CartProductComponent';

export default {
  name: 'CartGroupComponent',
  components: {
    CartProductComponent,
  },
  props: {
    group: Array,
  },
  data : function() {
    return {
      trNo: this.group[0].trNo,
      groupPrice: 0,
    }
  },
  created: function() {
    this.calculateGroupPrice();
  },
  computed: {
    computedGroupPrice: function() {
      return this.groupPrice.toLocaleString('ko-KR');
    }
  },
  methods: {
    calculateGroupPrice: function() {
      this.group.forEach(item => {
        this.groupPrice += item.slPrc*1;
      })
    },
    addGroupPrice: function(price) {
      this.groupPrice += price;
    },
    minusGroupPrice: function(price) {
      this.groupPrice -= price;
    }
  }
}
</script>

<style>

</style>