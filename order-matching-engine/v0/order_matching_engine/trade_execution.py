class TradeExecution:
    """
    Record the execution of a trade (post trade hooks, saving to persistent storage etc.)
    """

    def __init__(self, symbol: str, seller_id: int, buyer_id: int, price: int, size: int):
        self.symbol: str = symbol
        self.seller_id: int = seller_id
        self.buyer_id: int = buyer_id
        self.price: int = price
        self.size: int = size
