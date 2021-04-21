<template>
  <li v-if="product">
    <div class="cartProduct">
      <input type="checkbox" 
        @click="selectOne"
        :value="product.cartSn"
      >
      <div class="productItem">
        <div class="productThumb">
          <a>
            <v-img
              :src="img"
            />
          </a>
        </div>
        <div class="productData">
          <p><strong>{{product.brdNm}}</strong> {{product.spdNm}} </p>
          <p class="productDeliveryInfo">
            {{product.estmtDlvTxt}}
          </p>
        </div>
      </div>
    </div>
    <div class="cartQty"> 
      <button class="minus" @click="minusOdQty"/>
      <div class="number">
        <input type="number" id="number_00" :value="product.odQty" max="5">
        <label for="number_00" class="blind"></label>
      </div>
      <button class="plus" @click="plusOdQty"/>
    </div>
    <div class="cartPrice">
      <p>
        <strong v-if="this.totalPrice">{{this.totalPrice}}</strong>
        원
      </p>
    </div>
    <button 
      type="button" 
      class="deleteItem"
      @click="this.deleteCartProduct"
    />
  </li>
</template>

<script>
import {CartProductDto} from '~/model';
import {EventBus} from '~/utils';

export default {
  name: 'CartProductComponent',
  props: {
    product: new CartProductDto(),
  },
  data: function() {
    return {
      totalPrice: this.product.slPrc * this.product.odQty,
    }
  },
  computed: {
    img : function() {
      return this.product.imgJsn.length > 0 ? this.product.imgJsn[0].origImgFileNm : require('~/assets/default_image.png');
    }
  },
  methods: {
    plusOdQty: function() {
      const price = this.product.slPrc*1;
      this.product.odQty++;
      this.totalPrice += price;
      EventBus.$emit('addTotalValue', this.product);  // EventBus 이벤트 발행
      this.$emit('addGroupPrice', price); // 그룹가격 이벤트
    },
    minusOdQty: function() {
      const price = this.product.slPrc*1;
      if(this.product.odQty > 1) {
        this.product.odQty--;
        this.totalPrice -= price;
        EventBus.$emit('minusTotalValue', this.product);  // EventBus 이벤트 발행
        this.$emit('minusGroupPrice', price); // 그룹가격 이벤트
      } else {
        alert('최소1개까지 구매 가능한 상품입니다.');
      }
    },
    deleteCartProduct: function() {
      EventBus.$emit('deleteCartProduct', [this.product.cartSn]);
    },
    selectOne: function(event) {
      console.log(event.target)
    }
  }
}
</script>

<style>
  .cartListWrapper+.cartListWrapper {
    margin-top: 25px;
  }
  .productItem {
    padding-left: 28px;
    display: flex;
    width: 385px;
  }
  .productThumb {
    height: 80px;
    width: 80px;
    margin-right: 12px;
  }
  .productItem .productThumb {
    width: 80px;
    min-width: 80px;
    height: 80px;
    
  }
  .productThumb a {
    background: #e5e5e5;
    width: 80px;
    height: 80px;
  }
  .productThumb a .v-image {
    border-radius: 6px;
  }
  .cartProduct {
    position: relative;
    padding-right: 56px;
    border-right: 1px solid #ddd;
  }
  .cartProduct input[type=checkBox] {
    position: absolute;
    top: 0;
    left: 0;
  }
  .productItem .producData {
    width: 100%;
    width: 271px;
  }
  .productItem .productData .productDeliveryInfo {
    margin-top: 6px;
    font-size: 14px;
    line-height: 1.57;
    color: #4185ed;
  }
  .cartQty {
    min-width: 100px;
    display: flex;
    border: 1px solid #ddd;
    border-radius: 6px;
    width: 100px;
    height: 32px;
    margin: 0 20px;
  }
  .cartQty .minus {
    overflow: hidden;
    width: 100px;
    height: 30px;
    background: url('//static.lotteon.com/p/order/assets/img/btn_minus.svg') no-repeat 50%;
  }
  .cartQty .plus {
    overflow: hidden;
    width: 100px;
    height: 30px;
    background: url('//static.lotteon.com/p/order/assets/img/btn_plus.svg') no-repeat 50%;
  }
  .cartQty .number input {
    padding: 0;
    width: 100%;
    line-height: 30px;
    border: 0;
    text-align: center;
  }
  .cartQty .number {
    border-left: 1px solid #ddd;
    border-right: 1px solid #ddd;
  }
  input::-webkit-outer-spin-button,
  input::-webkit-inner-spin-button {
    -webkit-appearance: none;
    margin: 0;
  }
  .cartPrice {
    padding-left: 20px;
    border-left: 1px solid #ddd;
    min-width: 166px;
    text-align: center;
  }
  .deleteItem {
    position: absolute;
    top: 10px;
    right: 10px;
    width: 22px;
    min-width: 22px;
    height: 22px;
    background-repeat: no-repeat;
    background-position: 50%;
    background-size: 100% 100%;
    background-image: url(//static.lotteon.com/p/order/assets/img/icon_delete-item_new.svg);
  }
</style>