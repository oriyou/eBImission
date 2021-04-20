import AixosClient from "./AxiosClient";

class CartApi {
  constructor() {
    this.client = new AixosClient('/api');
  }

  async register(cart) {
    return (await this.client.post('cart', cart)).data;
  }

  async getCart() {
    return (await this.client.get('cart')).data;
  }

  async modify(cart) {
    return this.client.put('cart', cart);
  }
}

const instance = new CartApi();

export default instance;