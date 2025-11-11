package com.smart_expense.budget_management_system.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
@Embeddable
public class DateDescription {

    private LocalDateTime createdDate;
    private Long createdByUserId;
    private String createdByUserName;
    private LocalDateTime lastModifiedDate;
    private String lastModifiedByUsername;
    private Long lastModifiedByUserId;


}
