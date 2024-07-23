package com.harena.api.endpoint.rest.controller.health.possession;

import com.harena.api.repository.model.Possession;
import com.harena.api.service.PossessionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class PossessionController {

    private final PossessionService possessionService;

    @GetMapping("/patrimoines/{patrimoineName}/possessions")
    public List<Possession> getPossessions(
            @PathVariable String patrimoineName,
            @RequestParam String bucketKeyPrefix,
            @RequestParam(required = false) String continuationToken) {
        return possessionService.getPossessions(bucketKeyPrefix + "/" + patrimoineName, continuationToken);
    }

    @GetMapping("/patrimoines/{patrimoineName}/possessions/{possessionName}")
    public Optional<Possession> getPossessionByName(
            @PathVariable String patrimoineName,
            @PathVariable String possessionName,
            @RequestParam String bucketKeyPrefix) {
        return possessionService.getPossessionByName(bucketKeyPrefix + "/" + patrimoineName, possessionName);
    }

    @PutMapping("/patrimoines/{patrimoineName}/possessions")
    public Possession updatePossession(
            @PathVariable String patrimoineName,
            @RequestParam String bucketKeyPrefix,
            @RequestBody Possession possession) throws IOException {
        return possessionService.updatePossession(bucketKeyPrefix + "/" + patrimoineName, possession);
    }
}
