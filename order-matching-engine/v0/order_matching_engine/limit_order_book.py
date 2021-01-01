from typing import List, Optional
from .config import PRICE_POINT_MIN, PRICE_POINT_MAX
from .order_book_entry import OrderBookEntry
from .price_point import PricePoint
from .trade import Trade


class LimitOrderBook:
    """
    A LimitOrderBook is instantiated for each symbol traded.
    """

    def __init__(self, symbol: str):
        self.symbol: str = symbol

        price_range = (PRICE_POINT_MAX + 1)
        self.price_points: List[PricePoint] = [PricePoint(i)
                                               for i in range(price_range)]

        self.ask_min: int = PRICE_POINT_MAX + 1
        self.bid_max: int = PRICE_POINT_MIN - 1

    def try_buy(self, order_price: int, order_size: int, buyer_id: int) -> List[Trade]:
        trades: List[Trade] = []
        if order_price >= self.ask_min:
            price_point_entry: PricePoint = self.price_points[self.ask_min]
            while True:
                book_entry: Optional[OrderBookEntry] = price_point_entry.head

                while book_entry:
                    if book_entry.size < order_size:
                        t = Trade(self.symbol, book_entry.trader_id, buyer_id, order_price, book_entry.size)
                        trades.append(t)

                        order_size -= book_entry.size
                        book_entry = book_entry.next_

                    else:
                        t = Trade(self.symbol, book_entry.trader_id, buyer_id, order_price, order_size)
                        trades.append(t)
                        if book_entry.size > order_size:
                            book_entry.size -= order_size
                        else:
                            # assert book_entry.size == order_size
                            book_entry = book_entry.next_

                        price_point_entry.head = book_entry
                        return trades

                # Exhausted all orders at the ask_min price
                price_point_entry.head = None
                self.ask_min += 1
                price_point_entry = self.price_points[self.ask_min]

                if order_price < self.ask_min:
                    break

        self.price_points[order_price].insert(order_size, buyer_id)
        if self.bid_max < order_price:
            self.bid_max = order_price

        return trades

    def try_sell(self, order_price: int, order_size: int, seller_id: int) -> List[Trade]:
        trades: List[Trade] = []
        if order_price <= self.bid_max:
            price_point_entry: PricePoint = self.price_points[self.bid_max]

            while True:
                book_entry: Optional[OrderBookEntry] = price_point_entry.head

                while book_entry:
                    if book_entry.size < order_size:
                        t = Trade(self.symbol, seller_id, book_entry.trader_id,
                                  order_price, book_entry.size)
                        trades.append(t)
                        order_size -= book_entry.size
                        book_entry = book_entry.next_
                    else:
                        t = Trade(self.symbol, seller_id, book_entry.trader_id,
                                  order_price, order_size)
                        trades.append(t)
                        if book_entry.size > order_size:
                            book_entry.size -= order_size
                        else:
                            # assert book_entry.size == order_size
                            book_entry = book_entry.next_

                        price_point_entry.head = book_entry
                        return trades

                # Exhausted all orders at the bid max price
                price_point_entry.head = None
                self.bid_max -= 1
                price_point_entry = self.price_points[self.bid_max]

                if order_price > self.bid_max:
                    break

        self.price_points[order_price].insert(order_size, seller_id)
        if self.ask_min > order_price:
            self.ask_min = order_price

        return trades
