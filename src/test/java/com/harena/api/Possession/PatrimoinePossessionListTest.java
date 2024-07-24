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
import static org.mockito.Mockito.when;
public class PatrimoinePossessionListTest {

    @Mock
    private BucketComponent bucketComponent;

    @InjectMocks
    private PossessionService possessionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void test_list_patrimoine_possessions() {
        // Mock data
        S3Object s3Object1 = S3Object.builder()
                .key("possession1")
                .lastModified(Instant.now())
                .size(100L)
                .build();

        S3Object s3Object2 = S3Object.builder()
                .key("possession2")
                .lastModified(Instant.now())
                .size(200L)
                .build();

        List<S3Object> s3Objects = Arrays.asList(s3Object1, s3Object2);
        when(bucketComponent.listObjects("prefix", null)).thenReturn(s3Objects);

        List<Possession> possessions = possessionService.getPatrimoinePossessions("prefix", null);

        Assertions.assertEquals(2, possessions.size());
        Assertions.assertEquals("possession1", possessions.get(0).name());
        Assertions.assertEquals("possession2", possessions.get(1).name());
    }
}


