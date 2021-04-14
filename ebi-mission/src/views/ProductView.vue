<template>
  <div>
    <v-layout>
      <product-header />
    </v-layout>
    <router-view /> 
  </div>
</template>

<script>
import {ProductHeader} from '~/components'

export default {
  name: 'ProductView',
  components: {
    ProductHeader,
  }
}
</script>

<style>
  
</style>