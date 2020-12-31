from typing import List, Optional
from order_matching_engine.config import PRICE_POINT_MIN, PRICE_POINT_MAX
from order_matching_engine.order_book_entry import OrderBookEntry
from order_matching_engine.price_point import PricePoint
from order_matching_engine.trade_execution import TradeExecution


class LimitOrderBook:
    """
    A LimitOrderBook is instantiated for each symbol traded.
    """

    def __init__(self, symbol: str):
        self.symbol: str = symbol

        price_range = (PRICE_POINT_MAX + 1)
        self.price_points: List[PricePoint] = [PricePoint()] * price_range

        self.ask_min: int = PRICE_POINT_MIN
        self.bid_max: int = PRICE_POINT_MAX

    def try_buy(self, order_price: int, order_size: int, buyer_id: int) -> bool:
        if order_price >= self.ask_min:
            price_point_entry: PricePoint = self.price_points[self.ask_min]
            while True:
                book_entry: Optional[OrderBookEntry] = price_point_entry.head

                while book_entry:
                    if book_entry.size < order_size:
                        TradeExecution(self.symbol, book_entry.seller_id, buyer_id,
                                       order_price, book_entry.size)

                        order_size -= book_entry.size
                        book_entry = book_entry.next_

                    else:
                        TradeExecution(self.symbol, book_entry.seller_id, buyer_id,
                                       order_price, order_size)
                        if book_entry.size > order_size:
                            book_entry.size -= order_size
                        else:
                            # assert book_entry.size == order_size
                            book_entry = book_entry.next_

                        price_point_entry.head = book_entry
                        return True

                # Exhausted all orders at the ask_min price
                price_point_entry.head = None
                self.ask_min += 1
                price_point_entry = self.price_points[self.ask_min]

                if order_price < self.ask_min:
                    break

    def try_sell(self, price: int, size: int):
        # TODO
        pass
