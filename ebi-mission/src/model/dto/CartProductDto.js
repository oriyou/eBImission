
class CartProductDto {
  constructor(jsonObj) {
    this.trNo = jsonObj.trNo;
    this.spdNm = jsonObj.spdNm;
    this.brdNm = jsonObj.brdNm;
    this.slPrc = jsonObj.slPrc;
    this.estmtDlvTxt = jsonObj.estmtDlvTxt;
  }
}

export default CartProductDto;