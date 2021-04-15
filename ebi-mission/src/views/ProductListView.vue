<template>
  <v-container fluid class="grey lighten-5">
    <product-sort-component 
      :totalCount="computedTotalCount"
      @newSortCd="getSortCd"
    />
    <v-row
    >
      <product-component
        v-for="product in productList" 
        :key="product.spdNo"
        :product="toProduct(product)"
        @cart="registerInCart"
      />
    </v-row>
    <pagination-component
      :page="pageNo"
      :length="length"
      @newPageNo="getPageNo"
    />
  </v-container>
</template>

<script>
import axios from 'axios';
import {ProductComponent, ProductSortComponent, PaginationComponent} from '~/components';
import {Product} from '~/model';
import {CartApi} from '~/api';

export default {
  name: "ProductListView",
  components: { 
    ProductComponent, 
    ProductSortComponent, 
    PaginationComponent 
  },
  data: function() {
    return {
      productList: [],
      pdSortCd: '01',
      pageNo: 1,
      rowsPerPage: 60,
      dshopNo: 0,
      totalCount: 0,
    }
  },
  watch: {
    '$route.params': function() {
      Object.assign(this.$data, this.$options.data());

      // 정렬 초기화
      const childNodes = document.querySelector('.boardCheckList > ul').childNodes;
      Array.from(childNodes).forEach( element => {
        element.classList.remove('selected');
      });
      childNodes[0].classList.add('selected');

      this.fetchData();
    },
    'pdSortCd': 'fetchData',
    'pageNo': 'fetchData',
  },
  created: function() {
    this.fetchData();
  },
  computed: {
    length: function() {
      return Math.round(this.totalCount/60);
    },
    computedTotalCount: function() {
       return (this.totalCount >= 1000 ? '999+' : this.totalCount)
    }
  },
  methods: {
    async fetchData() {
      this.dshopNo = this.$route.params.dshopNo;
      const catePdList = await axios.get('https://www.lotteon.com/p/display/category/seltCatePdWishListAjax', 
      {params: {pdSortCd: this.pdSortCd, pageNo: this.pageNo, rowsPerPage: this.rowsPerPage, dshopNo: this.dshopNo}})
      .then(result => result.data.catePdList);
      this.productList = catePdList.dataList;
      this.totalCount = catePdList.totalCount;
    },
    toProduct(product) {
      return new Product(product)
    },
    getSortCd(newSortCd, event) {
      this.pdSortCd = newSortCd;

      // selected 추가
      const childNodes = event.target.parentNode.childNodes;
      Array.from(childNodes).forEach(element => {
        element.classList.remove('selected');
      });
      event.target.classList.add('selected');
    },
    getPageNo(pageNo) {
      this.pageNo = pageNo;
    },
    registerInCart(product) {
      CartApi.register(product.toCart()).then(res => console.log(res));
      alert('장바구니에 등록되었습니다.')
    }
  }
}
</script>

<style>
  #cardText{
   color: black;
  }
  .spdNm{
    overflow: hidden;
    text-overflow: ellipsis;
    display: -webkit-box;
    -webkit-line-clamp: 3; /* 라인수 */
    -webkit-box-orient: vertical;
    word-wrap:break-word; 
    line-height: 1.3em;
  }
  .price {
    font-size: 18px;
    font-weight: bold;
  }
  .slQty {
    height: 13px;
    font-size: 11px;
    line-height: 16px;
    color: #999;
    padding-left: 8px;
    border-left: 1px solid #eee;
  }
  .col {
    flex-grow: unset;
  }

</style>