import Cart from './Cart';

class Product {
    constructor(product) {
        this.spdNo = product.spdNo;
        this.sitmNo = product.sitmNo;
        this.brdNm = product.brdNm;
        this.spdNm = product.spdNm
        this.stscrAvgScr = product.stscrAvgScr;
        this.rvCnt = product.rvCnt;
        this.slPrc = product.slPrc;
        this.dcVal = product.dcVal;
        this.frstFvrPrc = product.frstFvrPrc;
        this.mmSlQty = product.mmSlQty;
        this.imgFullUrl = product.imgFullUrl;
        this.trNo = product.trNo;
        this.lrtrNo = product.lrtrNo;
        this.spdNo = product.spdNo;
        this.odQty = product.odQty;
        this.regDttm = product.regDttm;
        this.modDttm = product.modDttm;
    }

    toCart() {
        const cart = new Cart(this.trNo, this.lrtrNo, this.spdNo, this.sitmNo, 1);
        
        return cart;
    }
}
export default Product