package com.harena.api.patrimoine;

import com.harena.api.file.BucketComponent;
import com.harena.api.repository.model.Patrimoine;
import com.harena.api.service.PatrimoineService;
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

public class PatrimoineTest {

    @Mock
    private BucketComponent bucketComponent;

    @InjectMocks
    private PatrimoineService patrimoineService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void test_get_patrimoine_by_name() {
        // Mock data
        S3Object s3Object1 = S3Object.builder()
                .key("owner1/patrimoine1")
                .lastModified(Instant.now())
                .size(100L)
                .build();

        S3Object s3Object2 = S3Object.builder()
                .key("owner2/patrimoine2")
                .lastModified(Instant.now())
                .size(200L)
                .build();

        List<S3Object> s3Objects = Arrays.asList(s3Object1, s3Object2);
        when(bucketComponent.listObjects("prefix", null)).thenReturn(s3Objects);
        Optional<Patrimoine> patrimoine = patrimoineService.getPatrimoineByName("prefix", "patrimoine1");

        Assertions.assertTrue(patrimoine.isPresent());
        Assertions.assertEquals("patrimoine1", patrimoine.get().name());
        Assertions.assertEquals("owner1", patrimoine.get().Owner().name());
    }
}
