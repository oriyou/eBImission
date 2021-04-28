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
            @click="selectAll"
          >
            <input type="checkbox">
            <label>전체선택</label>
          </div>
          <div class="deleteBtnGroup">
            <v-btn
              @click="deleteSelected" 
              class="deleteCartItem"
              small
            >
              선택삭제
            </v-btn>
          </div>
        </div>

        <cart-group-component
          class="cartGroup"
          v-for="group, index in this.groupingCartArr"
          :group="group"
          :key="index"
        />
      
      </div>
      <div id="sideArea">
        <div class="priceWrap">
          <div class="inner">
            <div class="priceTitle">
              결제예정금액
            </div>
            <div class="priceList">
              <dl>
                <dt> 상품금액 </dt>
                <dd> {{setComma(this.totalPrice)}}원 </dd>
              </dl>
              <dl>
                <dt> 배송비 </dt>
                <dd> 0원 </dd>
              </dl>
              <dl>
                <dt> 상품할인금액 </dt>
                <dd> 0원 </dd>
              </dl>
            </div>
            <dl class="totalPrice">
              <dt>총 {{this.totalOdQty}}건</dt>
              <dt class="price">
                <strong>{{setComma(this.totalPrice)}}</strong>
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
import {CartGroupComponent} from '~/components';
import {EventBus, priceMixin} from '~/utils';

export default {  
  components: { CartGroupComponent },
  name: 'CartView',
  data: function() {
    return {
      originCartArr: [],
      groupingCartArr: [],
      totalPrice: 0,
      totalOdQty: 0,
    }
  },
  created: async function() {
    await this.retrieveCart();
    this.initTotalPriceAndOdQty();
    // EventBus 이벤트 구독
    EventBus.$on('addTotalValue', this.addTotalValue);
    EventBus.$on('minusTotalValue', this.minusTotalValue);
    EventBus.$on('deleteCartProduct', this.deleteCartProduct);
    EventBus.$on('modifyCart', this.modifyCart);
  },
  mixins: [priceMixin],
  methods: {
    retrieveCart: async function() {
      
      await CartApi.retrieveCart()
        .then(cart => {
          this.originCartArr = cart;
        });

      let groupingCart = {};
      this.originCartArr.forEach(e => {    
        if(!Object.keys(groupingCart).includes(e.trNo)) {
          groupingCart[e.trNo] = [];
        }
        groupingCart[e.trNo].push(e);
      });
      
      /*
      groupingCartArr =
                {
                  "LE10002" : [
                    
                  ],
                  "LE10003" : [

                  ],
                  ...
                }
      */
      Object.keys(groupingCart).forEach(key => {
        this.groupingCartArr.push(groupingCart[key])
      });
    },
    selectAll: function(event) {
      const isChecked = event.target.parentNode.querySelector('input[type=checkbox').checked;
      event.target.parentNode.querySelector('input[type=checkbox').checked = !isChecked;
      const inputArr = event.target.parentNode.parentNode.parentNode.querySelectorAll('input[type=checkbox]');
      inputArr.forEach(element => {
        element.checked = !isChecked;
      });
    },
    initTotalPriceAndOdQty: function() {
      this.originCartArr.forEach(item => {
        this.totalPrice += item.slPrc*item.odQty;
        this.totalOdQty += item.odQty*1;
      });                   
    },
    addTotalValue: function(product) {
      this.totalPrice += product.slPrc*1;
      this.totalOdQty++;
    },
    minusTotalValue: function(product) {
      this.totalPrice -= product.slPrc*1;
      this.totalOdQty--;
    },
    modifyCart: function(product, spinner) {
      CartApi.modify(product).then(result => {
        result.status == "200" ? spinner.style.display="none" : '';
      })
    },
    deleteCartProduct: function(productArr) {
      CartApi.remove(productArr);
      this.$router.go();
    },
    deleteSelected: function() {
      let cartSnArr = [];
      const cartGroups = document.querySelectorAll('.cartGroup ul');
      cartGroups.forEach(group => {
        const inputs = group.querySelectorAll('input[type=checkbox');
        inputs.forEach(input => {
          input.checked ? cartSnArr.push(input.value) : '';
        });
      });
      if(cartSnArr.length > 0) {
        CartApi.remove(cartSnArr);
        this.$router.go();
      }
    }
  },
  beforeDestroy: function() {
    EventBus.$off('addTotalValue');
    EventBus.$off('minusTotalValue');
    EventBus.$off('deleteCartProduct');
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
  .checkboxSet {
    display: inline-block;
    z-index: 1;
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
  .checkboxController:hover, .checkboxController > label:hover,
   .checkboxSet:hover, .checkboxSet > label:hover {
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
  #sideArea .priceWrap .priceList dl dt {
    position: relative;
    width: 50%;
    display: table-cell;
    padding-top: 3px;
    font-size: 14px;
    color: #757575;
    line-height: 1.57;
    vertical-align: top;
    white-space: normal;
  }
  #sideArea .priceWrap .priceList dl dd {
    font-size: 16px;
    width: 50%;
    display: table-cell;
    position: relative;
    text-align: right;
    font-weight: 700;
    word-break: break-all;
    vertical-align: top;
    line-height: 1.57;
  }
  .cartListWrapper .cartFooter {
    display: flex;
    height: 48px;
    padding: 0 20px;
    font-size: 13px;
    line-height: 48px;
    color: #757575;
    border-top: 1px solid #eee;
    justify-content: space-between;
  }
  .cartListWrapper .cartFooter .groupPrice {
    float: right;
  }
</style>
