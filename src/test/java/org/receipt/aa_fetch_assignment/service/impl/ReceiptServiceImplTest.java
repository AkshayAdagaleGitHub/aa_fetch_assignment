package org.receipt.aa_fetch_assignment.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.receipt.aa_fetch_assignment.dto.ItemDto;
import org.receipt.aa_fetch_assignment.dto.ReceiptDto;
import org.receipt.aa_fetch_assignment.entity.Receipt;
import org.receipt.aa_fetch_assignment.repository.IReceiptServiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReceiptServiceImplTest {


    private static final Logger logger = LoggerFactory.getLogger(ReceiptServiceImplTest.class);

    @Mock
    IReceiptServiceRepository iReceiptServiceRepository;

    @InjectMocks
    ReceiptServiceImpl receiptServiceImpl;

    @Test
    void processReceipt_success() {
        ReceiptDto receiptRequest = createSampleReceiptDto();
        Receipt receipt = new Receipt();
        receipt.setPoints(178.0);
        receipt.setReceipt_uuid(UUID.randomUUID());

        when(iReceiptServiceRepository.save(any(Receipt.class))).thenReturn(receipt);
        Receipt result = receiptServiceImpl.processReceipt(receiptRequest);
        logger.info("Result: {}", result.getPoints());
        logger.info("receipt.getPoints(): {}", receipt.getPoints());
        assertEquals(receipt.getPoints(), result.getPoints());
    }

    private ReceiptDto createSampleReceiptDto() {
        ReceiptDto receiptDto = new ReceiptDto();
        receiptDto.setRetailer("Target");
        receiptDto.setPurchaseDate(LocalDate.now().toString());
        receiptDto.setPurchaseTime(LocalTime.of(15, 0).toString());
        receiptDto.setTotal(10.00);
        receiptDto.setItemsList(createSampleItemList());
        return receiptDto;
    }

    private List<ItemDto> createSampleItemList() {
        List<ItemDto> itemsList = new ArrayList<>();
        ItemDto item1 = new ItemDto();
        item1.setShortDescription("Mountain Dew 12PK");
        item1.setPrice(6.00);

        ItemDto item2 = new ItemDto();
        item2.setShortDescription("Emils Cheese Pizza");
        item2.setPrice(10.00);
        itemsList.add(item1);
        itemsList.add(item2);
        return itemsList;
    }
}
