package com.tobeto.rentACar.services.dtos.user.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {

    @NotNull
    @Positive
    private int id;

    @NotBlank
    @Length(max = 40, message = "The email cannot exceed 40 characters!")
    @Pattern(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+(?:.[a-zA-Z0-9-]+)*$",
            message = "Please enter a valid email address.")
    private String email;

    @NotBlank
    private String password;

}
