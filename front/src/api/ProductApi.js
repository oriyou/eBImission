import axios from 'axios';

class ProductApi {
  constructor() {
    this.client = axios.create(
      {
        headers: {
            "Content-Type": "application/json",
        },
      }
    );
  }

  async retrieveProductList(pdSortCd, pageNo, rowsPerPage, dshopNo) {
    return this.client.get('https://www.lotteon.com/p/display/category/seltCatePdWishListAjax', 
      {params: {pdSortCd: pdSortCd, pageNo: pageNo, rowsPerPage: rowsPerPage, dshopNo: dshopNo}});
  }
}

const instance = new ProductApi();

export default instance;