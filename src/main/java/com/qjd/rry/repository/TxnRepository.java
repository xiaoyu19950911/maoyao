package com.qjd.rry.repository;

import com.qjd.rry.entity.Txn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TxnRepository extends JpaRepository<Txn,String>{

    Txn findFirstByOrderId(String orderId);
}
