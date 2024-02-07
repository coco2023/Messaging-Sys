### 2\. Topic Exchange
(系统优惠券，商家优惠券)

**Scenario**: A stock market application that publishes updates for different stocks across various sectors like technology and finance.

*   **Exchange**: A topic exchange named "StockUpdates".
*   **Queues**: Queues for different purposes, e.g., "TechStocksQueue" for technology sector updates and "FinanceStocksQueue" for finance sector updates.
*   **Producers**: The application publishes stock updates with specific routing keys like "stock.tech.apple" or "stock.finance.jpmorgan".
*   **Consumers**: Different services or dashboards that monitor stock updates in the technology or finance sectors.

**How it works**: Queues are bound to the exchange with pattern-based binding keys like "stock.tech._" for the technology sector and "stock.finance._" for the finance sector. The exchange routes messages to the queues based on wildcard matches between the routing key and the binding key.
