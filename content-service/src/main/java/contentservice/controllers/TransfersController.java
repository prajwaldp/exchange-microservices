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

import contentservice.models.Transfer;
import contentservice.repositories.TransferRepository;

@RestController
public class TransfersController {

  @Autowired
  private TransferRepository repository;

  @GetMapping("/transfers")
  List<Transfer> all() {
    return repository.findAll();
  }

  @PostMapping("/transfers")
  Transfer newTransfer(@RequestBody Transfer newTransfer) {
    newTransfer.setTransferTimeUuid(Uuids.timeBased());
    return repository.save(newTransfer);
  }

  @GetMapping("/transfers/{id}")
  Transfer one(@PathVariable MapId id) {
    return repository.findById(id).orElseThrow();
  }
}
