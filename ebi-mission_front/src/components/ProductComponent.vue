<template>
  <v-col @click="cart">
    <v-card
      outlined
      height="325"
      width="204"
    >
      <v-img
        :src="product.imgFullUrl"
        max-height="204px"
        max-width="204px"
        id="img"
      />
      <v-card-text id="cardText">
        <span class="spdNm">
          <strong>{{product.brdNm}}</strong>
          {{product.spdNm}}
        </span>
        <div>
          <span 
            v-if="product.stscrAvgScr != null"
            class="rating"
          >
            ⭐{{product.stscrAvgScr}}({{product.rvCnt}})
          </span>
          <span 
            v-if="product.mmSlQty != null"
            class="slQty">
            {{product.mmSlQty}}개 구매
          </span>
        </div>
        <div><span class="price">{{price}}</span>원</div>
      </v-card-text>
    </v-card>
  </v-col>
</template>

<script>

export default {
  name: 'ProductComponent',
  props: {
   product: Object,
  },
  computed: {
    price: function() {
      return this.product.frstFvrPrc.toLocaleString('ko-KR');
    }
  },
  methods : {
    cart() {
      this.$emit('cart', this.product);
    }
  }
}
</script>

<style>
</style>