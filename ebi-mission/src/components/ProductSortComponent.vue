<template>
  <div class="sortArea">
    <div class="total">
      총 <strong>{{total}}</strong>개
    </div>
    <div class="rightSet">
      <div class="boardCheckList">
        <ul>
          <li class="selected" @click="sort('01', $event)">판매량순</li>
          <li @click="sort('02', $event)">최근 등록순</li>
          <li @click="sort('03', $event)">낮은 가격순</li>
          <li @click="sort('04', $event)">높은 가격순</li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ProductSortComponent',
  props: {
    'totalCount' : Number,
  },
  data: function() {
    return {
      dropdown: [{text: '60개씩보기'}, {text: '120개씩보기'}],
    }
  },
  computed: {
    total: function() {
      return (this.totalCount >= 1000 ? '999+' : this.totalCount)
    }
  },
  watch: {
    '$route.params.dshopNo' : 'resetSort'
  },
  methods: {
    sort(code, event) {
      this.$emit('newSortCd', code);
      const childNodes = event.target.parentNode.childNodes;
      Array.from(childNodes).forEach(element => {
        element.classList.remove('selected');
      });
      event.target.classList.add('selected');
    },
    resetSort() {
      const childNodes = document.querySelector('.boardCheckList > ul').childNodes;
      Array.from(childNodes).forEach( element => {
        element.classList.remove('selected');
      });
      childNodes[0].classList.add('selected');
    }
  }
}
</script>

<style>
  .sortArea {
    width: 100%;
    height: 50px;
    padding: 12px 0;
    margin: 0 0 20px;
    border-bottom: 1px solid #eee;
    font-size: 14px;
  }
  .total {
    float: left;
  }
  .rightSet {
    float: right;
  }
  .boardCheckList {
    float: left;
  }
  .boardCheckList > ul > li {
    padding-left: 20px;
  }
  .selected {
    font-weight: bold;
  }
</style>