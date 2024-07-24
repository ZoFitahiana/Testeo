package com.harena.api.service;


import com.harena.api.file.BucketComponent;
import com.harena.api.repository.model.Patrimoine;
import com.harena.api.repository.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PatrimoineService {

    private final BucketComponent bucketComponent;


    public List<Patrimoine> getListPatrimoine(String bucketKeyPrefix, String continuationToken) {
        List<S3Object> s3Objects = bucketComponent.listObjects(bucketKeyPrefix, continuationToken);
        return s3Objects.stream().map(this::mapToPatrimoine).collect(Collectors.toList());
    }

    public Patrimoine updatePatrimoine(String bucketKeyPrefix, Patrimoine patrimoine) throws IOException {
        String bucketKey = bucketKeyPrefix + "/" + patrimoine.name();
        File file = convertPatrimoineToFile(patrimoine);
        bucketComponent.upload(file, bucketKey);

        S3Object s3Object = S3Object.builder()
                .key(bucketKey)
                .lastModified(Instant.now())
                .size(file.length())
                .build();

        return mapToPatrimoine(s3Object);
    }

    private File convertPatrimoineToFile(Patrimoine patrimoine) throws IOException {
        File file = new File(patrimoine.name() + ".json");
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(patrimoine.toString());
        }
        return file;
    }


    public Optional<Patrimoine> getPatrimoineByName(String bucketKeyPrefix, String patrimoineName) {
        List<S3Object> s3Objects = bucketComponent.listObjects(bucketKeyPrefix, null);
        return s3Objects.stream()
                .filter(s3Object -> s3Object.key().endsWith("/" + patrimoineName))
                .findFirst()
                .map(this::mapToPatrimoine);
    }


    private Patrimoine mapToPatrimoine(S3Object s3Object) {
        String key = s3Object.key();
        String[] parts = key.split("/");
        String name = parts[parts.length - 1];
        User owner = new User(parts[0]);
        Instant lastModified = s3Object.lastModified();
        Integer size = Math.toIntExact(s3Object.size());
        return new Patrimoine(name, owner, lastModified, size);
    }

}
