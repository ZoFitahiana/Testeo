
package com.harena.api.Possession;

import com.harena.api.file.BucketComponent;
import com.harena.api.file.FileHash;
import com.harena.api.file.FileHashAlgorithm;
import com.harena.api.repository.model.Devise;
import com.harena.api.repository.model.Possession;
import com.harena.api.service.PossessionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.IOException;
import java.time.Instant;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class UpdatePatrimoinePossessionTest {

    @Mock
    private BucketComponent bucketComponent;

    @InjectMocks
    private PossessionService possessionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void test_update_patrimoine_possession() throws IOException {
        Devise devise = new Devise("devise1", "12");
        Possession possession = new Possession(Instant.now(), "possession1", 11, devise);
        String bucketKeyPrefix = "prefix";
        String bucketKey = bucketKeyPrefix + "/" + possession.name();

        when(bucketComponent.upload(any(File.class), eq(bucketKey)))
                .thenReturn(new FileHash(FileHashAlgorithm.SHA256, "checksum"));

        Possession updatedPossession = possessionService.updatePatrimoinePossession(bucketKeyPrefix, possession);

        Assertions.assertEquals(possession.name(), updatedPossession.name());
        Assertions.assertEquals(possession.carryingAmount(), updatedPossession.carryingAmount());
        verify(bucketComponent, times(1)).upload(any(File.class), eq(bucketKey));
    }
}
