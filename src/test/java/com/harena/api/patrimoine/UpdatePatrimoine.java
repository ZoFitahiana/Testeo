package com.harena.api.patrimoine;

import com.harena.api.file.BucketComponent;
import com.harena.api.file.FileHash;
import com.harena.api.file.FileHashAlgorithm;
import com.harena.api.repository.model.Patrimoine;
import com.harena.api.repository.model.User;
import com.harena.api.service.PatrimoineService;
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

public class UpdatePatrimoine {

    @Mock
    private BucketComponent bucketComponent;

    @InjectMocks
    private PatrimoineService patrimoineService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testUpdatePatrimoine() throws IOException {
        // Mock data
        User owner = new User("ownerFifa");
        Patrimoine patrimoine = new Patrimoine("patrimoine1", owner, Instant.now(), 110);
        String bucketKeyPrefix = "prefix";
        String bucketKey = bucketKeyPrefix + "/" + patrimoine.name();
        when(bucketComponent.upload(any(File.class), eq(bucketKey)))
                .thenReturn(new FileHash(FileHashAlgorithm.SHA256, "checksum"));
        Patrimoine updatedPatrimoine = patrimoineService.updatePatrimoine(bucketKeyPrefix, patrimoine);
        Assertions.assertEquals(patrimoine.name(), updatedPatrimoine.name());
        Assertions.assertEquals(patrimoine.carryingAmount(), updatedPatrimoine.carryingAmount());
        verify(bucketComponent, times(1)).upload(any(File.class), eq(bucketKey));
    }
}
