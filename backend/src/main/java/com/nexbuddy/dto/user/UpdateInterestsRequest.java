package com.nexbuddy.dto.user;

import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public class UpdateInterestsRequest {

    @NotNull(message = "Interest IDs list is required")
    private List<UUID> interestIds;

    public List<UUID> getInterestIds() { return interestIds; }
    public void setInterestIds(List<UUID> interestIds) { this.interestIds = interestIds; }
}
