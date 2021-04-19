import {CartDto} from '~/model';

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
    }

    toCart() {
        return new CartDto(this.spdNo, this.sitmNo, 1);
    }
}

export default Product