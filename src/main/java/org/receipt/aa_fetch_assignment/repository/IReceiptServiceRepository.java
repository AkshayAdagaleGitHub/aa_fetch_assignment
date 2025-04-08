package org.receipt.aa_fetch_assignment.repository;

import org.receipt.aa_fetch_assignment.entity.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IReceiptServiceRepository extends JpaRepository<Receipt, Long> {

    @Query("SELECT r FROM Receipt r WHERE r.receipt_uuid = :receiptId")
    Receipt findByReceiptUuid(@Param("receiptId") UUID receiptUuid);

}
