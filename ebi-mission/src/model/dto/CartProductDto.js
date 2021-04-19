
class CartProductDto {
  constructor(cartSn, trNo, spdNm, brdNm, slPrc, estmtDlvTxt, odQty, spdNo, sitmNo, mbNo, imgJsn) {
    this.cartSn = cartSn;
    this.trNo = trNo;
    this.spdNm = spdNm;
    this.brdNm = brdNm;
    this.slPrc = slPrc; 
    this.estmtDlvTxt = estmtDlvTxt;
    this.odQty = odQty;
    this.spdNo = spdNo;
    this.sitmNo = sitmNo;
    this.mbNo = mbNo;
    this.imgJsn = imgJsn;
  }
  
  toCart(jsonObj) {
    return new CartProductDto(jsonObj.trNo, jsonObj.spdNm, jsonObj.brdNm, jsonObj.lPrc, jsonObj.estmtDlvTxt, jsonObj.odQty, jsonObj.spdNo, jsonObj.sitmNo, jsonObj.mbNo, jsonObj.imgJsn)
  }
}

export default CartProductDto;