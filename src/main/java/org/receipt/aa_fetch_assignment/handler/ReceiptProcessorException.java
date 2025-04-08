package org.receipt.aa_fetch_assignment.handler;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ReceiptProcessorException extends RuntimeException {

    private final String message;
    private final HttpStatus errorCode;

    public ReceiptProcessorException(String message, HttpStatus errorCode) {
        super(message);
        this.message = message;
        this.errorCode = errorCode;
    }
}
