package contentservice.controllers;

import java.util.List;

import com.datastax.oss.driver.api.core.uuid.Uuids;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.mapping.MapId;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import contentservice.models.Bid;
import contentservice.repositories.BidRepository;

@RestController
public class BidsController {

  @Autowired
  private BidRepository repository;

  @GetMapping("/bids")
  List<Bid> all() {
    return repository.findAll();
  }

  @PostMapping("/bids")
  Bid newBid(@RequestBody Bid newBid) {
    newBid.setBidTimeUuid(Uuids.timeBased());
    return repository.save(newBid);
  }

  @GetMapping("/bids/{id}")
  Bid one(@PathVariable MapId id) {
    return repository.findById(id).orElseThrow();
  }
}
