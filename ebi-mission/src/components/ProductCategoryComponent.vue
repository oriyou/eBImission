<template>
  <li>
    <button @click="clickCategory()" class="tab">
        <span class="icon">
          <img 
            :src="item.img"
            class="categoryImage"
          >
        </span>
        <span class="text">
          {{item.title}}
        </span>
    </button>
  </li>
</template>

<script>
export default {
  name: 'ProductCategoryComponent',
  props: {    
    item : Object,
  },
  methods: {
    clickCategory() {
      this.$emit('clickCategory', this.item.dshopNo);
    }
  }
}
</script>

<style>

</style>