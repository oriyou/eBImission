<template>
  <div class="text-center paginationWrap">
    <div class="text-center">
      <v-pagination
        v-model="currentPage"
        :length="length"
        :total-visible="7"
      ></v-pagination>
    </div>
  </div>
</template>

<script>
export default {
  name: 'PaginationComponent',
  props: {
    length: Number,
    page: Number,
  },
  data: function() {
    return {
      currentPage : this.page,
    }
  },
  watch: {
    'currentPage': 'clickPage',
  },
  methods: {
    clickPage: function() {
      this.$emit('newPageNo', this.currentPage);
    }
  }
}
</script>
  
<style>
  .theme--light.v-pagination .v-pagination__item--active {
    background: #343b4d !important;
  }
  .paginationWrap {
    padding: 20px 0 60px;
  }
</style>