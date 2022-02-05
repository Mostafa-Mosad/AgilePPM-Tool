package io.agile.ppmtool.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Project name is required")
    @Size(min = 8, max = 50, message = "Please use from 8 to 50 characters")
    private String projectName;

    @Column(unique = true, updatable = false)
    @NotBlank(message = "Project identifier is required")
    @Size(min = 5, max = 11, message = "Please use from 5 to 11 characters")
    private String projectIdentifier;

    @NotBlank(message = "Project description is required")
    @Size(min = 20, message = "Please use from 20 to 60 characters")
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date start_date;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date end_date;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date();
    }
}
