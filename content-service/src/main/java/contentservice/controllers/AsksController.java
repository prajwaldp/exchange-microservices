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

import contentservice.models.Ask;
import contentservice.repositories.AskRepository;

@RestController
public class AsksController {

  @Autowired
  private AskRepository repository;

  @GetMapping("/asks")
  List<Ask> all() {
    return repository.findAll();
  }

  @PostMapping("/asks")
  Ask newBid(@RequestBody Ask newAsk) {
    newAsk.setAskTimeUuid(Uuids.timeBased());
    return repository.save(newAsk);
  }

  @GetMapping("/asks/{id}")
  Ask one(@PathVariable MapId id) {
    return repository.findById(id).orElseThrow();
  }
}
