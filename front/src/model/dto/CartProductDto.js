
class CartProductDto {
  constructor(cartSn, trNo, lrtrNo, spdNm, brdNm, slPrc, estmtDlvTxt, odQty, spdNo, sitmNo, mbNo, imgJsn, regDttm) {
    this.cartSn = cartSn;
    this.trNo = trNo;
    this.lrtrNo = lrtrNo;
    this.spdNm = spdNm;
    this.brdNm = brdNm;
    this.slPrc = slPrc; 
    this.estmtDlvTxt = estmtDlvTxt;
    this.odQty = odQty;
    this.spdNo = spdNo;
    this.sitmNo = sitmNo;
    this.mbNo = mbNo;
    this.imgJsn = imgJsn;
    this.regDttm = regDttm;
  }
  
  toCart(jsonObj) {
    return new CartProductDto(jsonObj.trNo, jsonObj.spdNm, jsonObj.brdNm, jsonObj.lPrc, jsonObj.estmtDlvTxt, jsonObj.odQty, jsonObj.spdNo, jsonObj.sitmNo, jsonObj.mbNo, jsonObj.imgJsn)
  }
}

export default CartProductDto;