package com.stackroute.paymentservice.repository;

import com.stackroute.paymentservice.entity.Receipt;
import org.springframework.data.repository.CrudRepository;

public interface ReceiptRepository extends CrudRepository<Receipt,Long> {
}
