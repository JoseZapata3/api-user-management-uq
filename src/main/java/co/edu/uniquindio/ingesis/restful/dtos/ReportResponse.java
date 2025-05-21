package co.edu.uniquindio.ingesis.restful.dtos;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class ReportResponse {
    private Long id;
    private String title;
    private String description;
    private String code;
    private String createdAt;
    private Long ownerId;
    private String ownerName;
    private String type;

    public ReportResponse() {}

    public ReportResponse(Long id, String title, String description, String code, String createdAt,
                          Long ownerId, String ownerName, String type) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.code = code;
        this.createdAt = createdAt;
        this.ownerId = ownerId;
        this.ownerName = ownerName;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCode() {
        return code;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getType() {
        return type;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public void setType(String type) {
        this.type = type;
    }
}
