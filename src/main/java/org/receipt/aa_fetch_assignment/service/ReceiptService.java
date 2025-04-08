package org.receipt.aa_fetch_assignment.service;

import org.receipt.aa_fetch_assignment.dto.ReceiptDto;
import org.receipt.aa_fetch_assignment.entity.Receipt;

public interface ReceiptService {
    Receipt processReceipt(ReceiptDto receiptDto);
    Receipt getPointsByReceiptId(String receiptId);
}
