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
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.when;

@Component

public class PatrimoineListTest {
    @Mock
    private BucketComponent bucketComponent;

    @InjectMocks
    private PatrimoineService patrimoineService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void test_get_list_patrimoine() {
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
        List<Patrimoine> patrimoines = patrimoineService.getListPatrimoine("prefix", null);

        Assertions.assertEquals(2, patrimoines.size());
        Assertions.assertEquals("patrimoine1", patrimoines.get(0).name());
        Assertions.assertEquals("owner1", patrimoines.get(0).Owner().name());

        Assertions.assertEquals("patrimoine2", patrimoines.get(1).name());
        Assertions.assertEquals("owner2", patrimoines.get(1).Owner().name());
    }

}
