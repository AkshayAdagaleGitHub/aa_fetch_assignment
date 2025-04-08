package org.receipt.aa_fetch_assignment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {

    @JsonProperty("shortDescription")
    @Pattern(regexp = "^[\\w\\s\\-]+$", message = "Invalid short description format.")
    private String shortDescription;
    @JsonProperty("price")
    @Pattern(regexp = "^\\d\\.\\d{2}$", message = "Invalid price format.")
    private double price;

}
