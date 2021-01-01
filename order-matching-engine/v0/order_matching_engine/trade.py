class Trade:
    def __init__(self, symbol: str, seller_id: int, buyer_id: int, price: int, size: int):
        self.symbol: str = symbol
        self.seller_id: int = seller_id
        self.buyer_id: int = buyer_id
        self.price: int = price
        self.size: int = size

    def __repr__(self):
        return f'Trade[symbol: {self.symbol}, seller_id: {self.seller_id}, buyer_id: {self.buyer_id}, price: {self.price}, size: {self.size}]'
