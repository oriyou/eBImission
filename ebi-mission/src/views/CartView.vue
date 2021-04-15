<template>
  <div>
    <h2 class="pageTitle">
      장바구니
    </h2>
    <div class="cartContents">
      <div class="detailWrap">
        <div class="itemController">
          <div 
            class="checkboxController"
            @click="allClick"
          >
            <input type="checkbox">
            <label>전체선택</label>
          </div>
          <div class="deleteBtnGroup">
            <v-btn 
              class="deleteCartItem"
              small
            >
              선택삭제
            </v-btn>
          </div>
        </div>

        <cart-list-component />
        <cart-list-component />
        <cart-list-component />

      </div>
      <div id="sideArea">
        <div class="priceWrap">
          <div class="inner">
            <div class="priceTitle">
              결제예정금액
            </div>
            <div class="priceList">
              <dl>상품금액 </dl>
              <dl>배송비 </dl>
              <dl>상품할인금액 </dl>
            </div>
            <dl class="totalPrice">
              <dt>총 ?건</dt>
              <dt class="price">
                <strong>1,000,000</strong>
                <span>원</span>
              </dt>
            </dl>
            <v-btn depressed color="#ef2a23">주문하기</v-btn>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {CartApi} from '~/api';
import {CartListComponent} from '~/components';

export default {
  components: { CartListComponent },
  name: 'CartView',
  data: function() {
    return {
      cart: {},
    }
  },
  created: function() {
    this.getCart();
  },
  methods: {
    getCart: async function() {
      let cartData = [];
      await CartApi.getCart()
        .then(cart => {
          cartData = cart;
        });

      cartData.forEach(e => {    
        if(!Object.keys(this.cart).includes(e.trNo)) {
          this.cart[e.trNo] = [];
        }
        this.cart[e.trNo].push(e);
      });
      console.log(this.cart);
      // console.log(Object.keys(this.cart)[0]);
    },
    allClick: function(event) {
      const parentNode = event.target.parentNode;
      let isChecked = parentNode.querySelector('input[type=checkbox').checked;
      parentNode.querySelector('input[type=checkbox').checked = !isChecked;
      const inputArr = parentNode.parentNode.parentNode.querySelectorAll('input[type=checkbox]');
      inputArr.forEach(element => {
        element.checked = !isChecked;
      });
    },
  }
}
</script>

<style>
  .pageTitle {
    padding: 14px 0;
    margin-bottom: 30px;
    font-size: 36px;
    line-height: 52px;
    letter-spacing: -1.1px;
    text-align: center;
    border-bottom: 1px solid #333;
  }
  .cartContents {
    position: relative;
    min-height: 390px;
    margin-top: 40px;
    margin-bottom: 80px;
  }
  .detailWrap {
    width: 786px;
  }
  #sideArea {
    position: absolute;
    top: 0;
    right: 0;
    width: 298px;
    height: 100%;
  }
  .cartContents ul {
    list-style: none;
  }
  .cartContents  ul  li:first-child {
    border-top: none;
  }
  .cartContents  ul  li {
    padding: 24px 10px 24px 20px;
    border-top: 1px solid #eee;
    display: flex;
    position: relative;
    width: 100%;
  }
  .inner {
    width: 100%;
    background: #fff;
    box-sizing: border-box;
    border: 1px solid #ddd;
    border-radius: 10px;
    padding: 24px 20px 20px 20px;
  }
  .priceTitle {
    font-size: 20px;
    line-height: 1.4;
    margin-bottom: 28px;
  }
  .priceList {
    border-bottom: 1px solid #eee;
    padding-bottom: 18px;
  }
  .cartHeader {
    height: 53px;
    padding: 0 20px 0 19px;
    border-bottom: 1px solid #eee;
  }
  .cartListWrapper {
    border: 1px solid #ddd;
    border-radius: 10px;
    overflow: hidden;
    width: 100%;
  }
  .checkboxSet label {
    font-size: 18px;
    line-height: 53px;
    font-weight: 800;
    color: #333;
    vertical-align: middle;
    padding-left: 10px;
  }
  .deleteBtnGroup {
    float: right;
  }
  .checkboxController {
    float: left;
  }
  .checkboxController:hover, .checkboxController > label:hover {
    cursor: pointer;
  }
  .checkboxController input[type='checkbox'] {
    margin-right: 10px;
  }
  
  .itemController {
    width: 100%;
    padding: 0 0 12px;
    float: left;
  }
</style>
