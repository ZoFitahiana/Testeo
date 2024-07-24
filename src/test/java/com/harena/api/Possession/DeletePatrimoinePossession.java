package com.harena.api.Possession;

import com.harena.api.file.BucketComponent;
import com.harena.api.service.PossessionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class DeletePatrimoinePossession {

    @Mock
    private BucketComponent bucketComponent;

    @InjectMocks
    private PossessionService possessionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void test_delete_possession() {
        // Mock data
        String bucketKeyPrefix = "prefix";
        String possessionName = "possession1";
        String bucketKey = bucketKeyPrefix + "/" + possessionName;
        possessionService.deletePatrimoinePossession(bucketKeyPrefix, possessionName);
        verify(bucketComponent, times(1)).deleteObject(bucketKey);
    }
}
