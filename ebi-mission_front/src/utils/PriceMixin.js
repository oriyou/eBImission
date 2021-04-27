const priceMixin = {
  computed: {
    setComma(){
      return (value) => value.toLocaleString('ko-KR');
    },
  }
}

export default priceMixin;