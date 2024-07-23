package com.harena.api.service;

import com.harena.api.file.BucketComponent;
import com.harena.api.repository.model.Devise;
import com.harena.api.repository.model.Possession;
import software.amazon.awssdk.services.s3.model.S3Object;

import javax.swing.text.html.Option;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PossessionService {
    private final BucketComponent bucketComponent;

    public PossessionService(BucketComponent bucketComponent) {
        this.bucketComponent = bucketComponent;
    }

    public List<Possession> getPossessions(String bucketKeyPrefix, String continuationToken) {
        List<S3Object> s3Objects = bucketComponent.listObjects(bucketKeyPrefix, continuationToken);
        return s3Objects.stream().map(this::mapToPossession).collect(Collectors.toList());
    }

    public Optional<Possession> getPossessionByName(String bucketKeyPrefix , String possessionName){
        List<S3Object> s3Objects = bucketComponent.listObjects(bucketKeyPrefix , null) ;
        return s3Objects.stream()
                .filter(s3Object -> s3Object.key().endsWith("/" + possessionName))
                .findFirst()
                .map(this::mapToPossession);
    }

    private File convertPossessionToFile(Possession possession) throws IOException{
        File file = new File(possession.name() + ".json");
        try(FileWriter writer = new FileWriter(file)){
            writer.write(possession.toString());
        }
        return file;
    }
    public Possession updatePossession(String bucketKeyPrefix, Possession possession) throws IOException {
        String bucketKey = bucketKeyPrefix + "/" + possession.name();
        File file = convertPossessionToFile(possession);
        bucketComponent.upload(file, bucketKey);

        S3Object s3Object = S3Object.builder()
                .key(bucketKey)
                .lastModified(Instant.now())
                .size(file.length())
                .build();

        return mapToPossession(s3Object);
    }



    private Possession mapToPossession(S3Object s3Object) {
        String key = s3Object.key();
        String[] parts = key.split("/");
        String name = parts[parts.length - 1];
        Instant lastModified = s3Object.lastModified();
        Integer size = Math.toIntExact(s3Object.size());
        Devise devise = new Devise("USD", "840");
        return new Possession(lastModified, name, size, devise);
    }

}
