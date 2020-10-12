package contentservice.models;

import java.util.UUID;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Table(value = "bids")
public class Bid {

  @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED, value = "country")
  public String country;

  @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED, value = "ticker_symbol")
  public String tickerSymbol;

  @PrimaryKeyColumn(value = "initiater_id")
  public String initiaterId;

  @PrimaryKeyColumn(value = "bid_time_uuid")
  public UUID bidTimeUuid;

  @Column(value = "bid_price")
  public Double bidPrice;

  @Column(value = "number_of_shares")
  public Double numberOfShares;
}
