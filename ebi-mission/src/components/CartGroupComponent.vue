<template>
  <div class="cartListWrapper">
    <div class="cartHeader">
      <div 
        class="checkboxSet"
        @click="selectGroup"
      >
        <input type="checkbox" :checked="this.checked">
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
      checked: false,
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
        this.groupPrice += item.slPrc*item.odQty;
      })
    },
    addGroupPrice: function(price) {
      this.groupPrice += price;
    },
    minusGroupPrice: function(price) {
      this.groupPrice -= price;
    },
    selectGroup: function(event) {
      const isChecked = event.target.parentNode.querySelector('input[type=checkbox]').checked;
      const inputArr = event.target.parentNode.parentNode.parentNode.querySelectorAll('input[type=checkbox]');
      inputArr.forEach(item => item.checked = !isChecked);
    },
  }
}
</script>

<style>

</style>