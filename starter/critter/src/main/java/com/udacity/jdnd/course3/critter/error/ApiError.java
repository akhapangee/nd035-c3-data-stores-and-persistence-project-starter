package com.udacity.jdnd.course3.critter.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * Declares methods to return errors and other messages from the API.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
class ApiError {
    private HttpStatus status;
    private String message;
    private List<String> errors;
}
