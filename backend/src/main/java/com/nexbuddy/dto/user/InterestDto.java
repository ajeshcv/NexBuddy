package com.nexbuddy.dto.user;

import java.util.UUID;

public class InterestDto {
    private UUID id;
    private String name;
    private String category;
    private String icon;

    public InterestDto() {
    }

    public InterestDto(UUID id, String name, String category, String icon) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.icon = icon;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }
}
