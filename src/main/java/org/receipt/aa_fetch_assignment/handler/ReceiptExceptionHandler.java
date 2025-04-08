package org.receipt.aa_fetch_assignment.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ReceiptExceptionHandler {

     @ExceptionHandler(ReceiptProcessorException.class)
    public ResponseEntity<ErrorResponse> handleReceiptNotFoundException(
            ReceiptProcessorException exception
    ) {
         ErrorResponse errorResponse = new ErrorResponse(exception.getMessage());

        return new ResponseEntity<>(errorResponse,exception.getErrorCode());
    }


}
