package com.harena.api.repository.model;

import java.io.Serializable;
import java.time.Instant;

public record Patrimoine(String name , User Owner, Instant t ,Integer carryingAmount)implements Serializable {
}
