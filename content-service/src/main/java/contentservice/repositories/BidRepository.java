package contentservice.repositories;

import org.springframework.data.cassandra.core.mapping.MapId;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import contentservice.models.Bid;

@Repository
public interface BidRepository extends CassandraRepository<Bid, MapId> {

}
