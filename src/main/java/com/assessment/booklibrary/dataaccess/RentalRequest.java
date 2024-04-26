package com.assessment.booklibrary.dataaccess;

import lombok.Data;

@Data
public class RentalRequest {
    private String bookName;
    private String renterName;
}
