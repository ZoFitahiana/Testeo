package com.harena.api.repository.model;

import java.io.Serializable;
import java.time.Instant;

public record Possession(  Instant t ,String name, Integer carryingAmount , Devise devise) implements Serializable {
}
