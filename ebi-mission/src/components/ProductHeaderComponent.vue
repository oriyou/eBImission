<template>
  <v-container class="grey lighten-5">
    <ul>
      <product-category-component 
        v-for="(item, index) in category"
        :key="index"
        :item="item"
        @clickCategory="handleCategory"
      />

      <!-- 장바구니 버튼 -->
      <li>
        <button class="tab" @click="cart">
          <span class="icon cart">
            <v-icon large>mdi-cart</v-icon>
          </span>
        </button>
      </li>
      <!--  -->
      
    </ul>
  </v-container>
</template>

<script>
import ProductCategoryComponent from './ProductCategoryComponent.vue';

export default {
  name: 'ProductHeaderComponent',
  components: { ProductCategoryComponent, },
  props: {
    category: Array,
  },
  methods: {
    handleCategory(dshopNo) {
      this.$router.push(`/product/${dshopNo}`)
    },
    cart() {
      this.$router.push('/cart');
    },
  }
}
</script>

<style>
ul {
  list-style:none;
  margin:0;
  padding:0;
}

li {
  margin: 0 5px 0 0;
  padding: 0 0 0 0;
  border : 0;
  float: left;
}

.icon{
  display: block;
  position: relative;
  width: 72px;
  height: 72px;
  padding: 0;
  border-radius: 36px;
  overflow: hidden;
  margin: 0 auto;
}

.icon.selected {
  border: 1px solid #343b4d;
}

.categoryImage {
  width: 100%;
  height: 100%;
}

.text {
  font-size: 14px;
}

.tab {
  margin: 0 5px;
}

.icon.cart {
  line-height: 83px;
}
</style>