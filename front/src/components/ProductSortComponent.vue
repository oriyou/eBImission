<template>
  <div class="sortArea">
    <div class="total">
      총 <strong>{{totalCount}}</strong>개
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
  props: [
    'totalCount',
  ],
  methods: {
    sort(code, event) {
      this.$emit('newSortCd', code, event);
    }
  }
}
</script>

<style scoped>
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