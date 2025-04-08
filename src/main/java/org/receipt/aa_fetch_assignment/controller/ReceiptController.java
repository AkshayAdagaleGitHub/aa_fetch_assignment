package org.receipt.aa_fetch_assignment.controller;

import org.receipt.aa_fetch_assignment.dto.ItemDto;
import org.receipt.aa_fetch_assignment.dto.ReceiptDto;
import org.receipt.aa_fetch_assignment.entity.GetPointsResponse;
import org.receipt.aa_fetch_assignment.entity.ProcessReceiptResponse;
import org.receipt.aa_fetch_assignment.entity.Receipt;
import org.receipt.aa_fetch_assignment.handler.ReceiptProcessorException;
import org.receipt.aa_fetch_assignment.service.ReceiptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Objects;

@RestController
@RequestMapping("")
@CrossOrigin(origins = "*")
public class ReceiptController {
    private static final Logger logger = LoggerFactory.getLogger(ReceiptController.class);

    ReceiptService receiptService;

    public ReceiptController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @PostMapping("/receipts/process")
    public ResponseEntity<ProcessReceiptResponse> receiptProcessor(@RequestBody ReceiptDto receipt) {
        try {
            validateRequest(receipt);
            logger.info("Received receipt: {}", receipt);
            Receipt savedReceipt = receiptService.processReceipt(receipt);
            logger.info("Processed receipt: {}", savedReceipt.getReceipt_uuid());
            ProcessReceiptResponse processReceiptResponse = new ProcessReceiptResponse(savedReceipt.getReceipt_uuid());
            return ResponseEntity.ok(processReceiptResponse);
        }catch (ReceiptProcessorException e){
            logger.error("Error processing receipt: {}", e.getMessage());
            throw e;
        }
    }

    private void validateRequest(ReceiptDto receipt){
        if(receipt == null){
            logger.error("Receipt is empty:");
            throw new ReceiptProcessorException("The receipt is invalid.", HttpStatus.BAD_REQUEST);
        }
        if(receipt.getRetailer() == null || receipt.getRetailer().trim().isEmpty()){
            logger.error("Retailer name is empty:");
            throw new ReceiptProcessorException("The receipt is invalid.",HttpStatus.BAD_REQUEST);
        }else if(!receipt.getRetailer().isEmpty()){
            String retailer = receipt.getRetailer().trim();
            if(!retailer.matches("^[\\w\\s\\-&]+$")){
                logger.error("Retailer name is invalid:");
                throw new ReceiptProcessorException("The receipt is invalid.", HttpStatus.BAD_REQUEST);
            }
        }
        if(receipt.getItemsList() == null || receipt.getItemsList().isEmpty()){
            logger.error("Items list is empty:");
            throw new ReceiptProcessorException("The receipt is invalid.", HttpStatus.BAD_REQUEST);
        } else if ( receipt.getItemsList().size() > 0) {
            for(ItemDto item : receipt.getItemsList()){

                if(Objects.nonNull(item.getShortDescription())) {
                    if (!item.getShortDescription().trim().matches("^[\\w\\s\\-]+$")) {
                        logger.error("Short description is invalid:");
                        throw new ReceiptProcessorException("The receipt is invalid.", HttpStatus.BAD_REQUEST);
                    }
                }
                if(Objects.isNull(item.getPrice())) {
                    if (!Double.toString(item.getPrice()).matches("^\\d+\\.\\d{2}")) {
                        logger.error("Item Price is invalid:");
                        throw new ReceiptProcessorException("The receipt is invalid.", HttpStatus.BAD_REQUEST);
                    }
                }

            }
        }
        if(receipt.getPurchaseDate() == null || receipt.getPurchaseDate().trim().isEmpty()){
            throw new ReceiptProcessorException("The receipt is invalid.", HttpStatus.BAD_REQUEST);
        }else if(!receipt.getPurchaseDate().isEmpty()){
            String purchaseDate = receipt.getPurchaseDate().trim();
            if(!purchaseDate.matches("^\\d{4}-\\d{2}-\\d{2}$")){
                logger.error("Purchase date is invalid:");
                throw new ReceiptProcessorException("The receipt is invalid.", HttpStatus.BAD_REQUEST);
            }
        }

        if(receipt.getPurchaseTime() == null || receipt.getPurchaseTime().trim().isEmpty()){
            throw new ReceiptProcessorException("The receipt is invalid.", HttpStatus.BAD_REQUEST);
        }else if(!receipt.getPurchaseTime().isEmpty()){
            String purchaseTime = receipt.getPurchaseTime().trim();
            if(!purchaseTime.matches("^\\d{2}:\\d{2}$")){
                throw new ReceiptProcessorException("The receipt is invalid.", HttpStatus.BAD_REQUEST);
            }
        }

        if(receipt.getTotal() == null || receipt.getTotal() < 0){
            throw new ReceiptProcessorException("The receipt is invalid", HttpStatus.BAD_REQUEST);
        }else if (receipt.getTotal() > 0) {
            if(!receipt.getTotal().toString().matches("^\\d+\\.\\d{2}$")){
                throw new ReceiptProcessorException("The receipt is invalid.", HttpStatus.BAD_REQUEST);
            }
        }

    }

    @GetMapping("/receipts/{id}/points")
    public ResponseEntity<GetPointsResponse> getReceiptPoints(@PathVariable("id") String receiptId) {
        try {
            logger.info("Received receipt id: {}", receiptId);
            if(receiptId == null || receiptId.trim().isEmpty()){
                throw new ReceiptProcessorException(
                        "No receipt found for that ID.",
                        HttpStatus.NOT_FOUND);
            }
            GetPointsResponse getPointsResponse = new GetPointsResponse();
            Receipt receipt = receiptService.getPointsByReceiptId(receiptId);
            getPointsResponse.setPoints(receipt.getPoints());
            return ResponseEntity.ok(getPointsResponse);
        }catch (Exception e){
            logger.error("Error fetching points for receipt: {}", e.getMessage());
            throw new ReceiptProcessorException("No receipt found for that ID.",HttpStatus.NOT_FOUND);
        }
    }


}
