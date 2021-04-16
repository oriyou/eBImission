import AixosClient from "./AxiosClient";

class CartApi {
  constructor() {
    this.client = new AixosClient('/api');
  }

  async register(cart) {
    return this.client.post('cart', cart);
  }

  async getCart() {
    return (await this.client.get('cart')).data;
  }
}

const instance = new CartApi();

export default instance;