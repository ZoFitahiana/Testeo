
package com.harena.api.Possession;

import com.harena.api.file.BucketComponent;
import com.harena.api.repository.model.Possession;
import com.harena.api.service.PossessionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import software.amazon.awssdk.services.s3.model.S3Object;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

public class PossessionTest {

    @Mock
    private BucketComponent bucketComponent;

    @InjectMocks
    private PossessionService possessionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void test_get_patrimoine_possession_by_name() {
        // Mock data
        S3Object s3Object1 = S3Object.builder()
                .key("possession1")
                .lastModified(Instant.now())
                .size(100L)
                .build();

        S3Object s3Object2 = S3Object.builder()
                .key("possession2")
                .lastModified(Instant.now())
                .size(100L)
                .build();

        List<S3Object> s3Objects = Arrays.asList(s3Object1, s3Object2);
        when(bucketComponent.listObjects("prefix", null)).thenReturn(s3Objects);

        Optional<Possession> possession = possessionService.getPatrimoinePossessionByNom("prefix", "possession1");
        Assertions.assertTrue(possession.isPresent());
        Assertions.assertEquals("possession1", possession.get().name());
        Assertions.assertEquals("possession2" , possession.get().name());
    }
}



