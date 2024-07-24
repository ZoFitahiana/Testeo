package com.harena.api.endpoint.rest.controller.health.Possession;

import com.harena.api.repository.model.Possession;
import com.harena.api.service.PossessionService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
public class PossessionController {

    private final PossessionService possessionService;

    public PossessionController(PossessionService possessionService) {
        this.possessionService = possessionService;
    }

    @GetMapping("/patrimoines/{patrimoineName}/possessions")
    public List<Possession> getPatrimoinePossessions(
            @PathVariable String patrimoineName,
            @RequestParam String bucketKeyPrefix,
            @RequestParam(required = false) String continuationToken) {
        return possessionService.getPatrimoinePossessions(bucketKeyPrefix + "/" + patrimoineName, continuationToken);
    }

    @GetMapping("/patrimoines/{patrimoineName}/possessions/{possessionName}")
    public Optional<Possession> getPatrimoinePossessionByNom(
            @PathVariable String patrimoineName,
            @PathVariable String possessionName,
            @RequestParam String bucketKeyPrefix) {
        return possessionService.getPatrimoinePossessionByNom(bucketKeyPrefix + "/" + patrimoineName, possessionName);
    }

    @PutMapping("/patrimoines/{patrimoineName}/possessions")
    public Possession updatePatrimoinePossession(
            @PathVariable String patrimoineName,
            @RequestParam String bucketKeyPrefix,
            @RequestBody Possession possession) throws IOException {
        return possessionService.updatePatrimoinePossession(bucketKeyPrefix + "/" + patrimoineName, possession);
    }

}
