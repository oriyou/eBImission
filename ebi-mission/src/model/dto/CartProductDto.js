
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
}

export default CartProductDto;