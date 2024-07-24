package com.harena.api.endpoint.rest.controller.health.patrimoine;

import com.harena.api.repository.model.Patrimoine;
import com.harena.api.service.PatrimoineService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.io.IOException;

@RestController
@AllArgsConstructor

public class PatrimoinController {
    private final PatrimoineService patrimoineService;


    @GetMapping("/patrimoines")
    public List<Patrimoine> getPatrimoines(
            @RequestParam String bucketKeyPrefix,
            @RequestParam(required = false) String continuationToken) {
        return patrimoineService.getListPatrimoine(bucketKeyPrefix, continuationToken);
    }

    @GetMapping("/patrimoines/{name}")
    public Optional<Patrimoine> getPatrimoineByName(
            @RequestParam String bucketKeyPrefix,
            @PathVariable String name) {
        return patrimoineService.getPatrimoineByName(bucketKeyPrefix, name);
    }

    @PutMapping("/patrimoines")
    public Patrimoine updatePatrimoine(
            @RequestParam String bucketKeyPrefix,
            @RequestBody Patrimoine patrimoine) throws IOException {
        return patrimoineService.updatePatrimoine(bucketKeyPrefix, patrimoine);
    }
}
