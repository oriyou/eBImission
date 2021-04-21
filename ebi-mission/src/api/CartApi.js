import AixosClient from "./AxiosClient";

class CartApi {
  constructor() {
    this.client = new AixosClient('/api');
  }

  async register(cart) {
    return (await this.client.post('cart', cart)).data;
  }

  async retrieveCart() {
    return (await this.client.get('cart')).data;
  }

  async modify(cart) {
    return this.client.put('cart', cart);
  }

  async remove(cartSnArr) {
    return this.client.post('cart/remove', cartSnArr);
  }
}

const instance = new CartApi();

export default instance;