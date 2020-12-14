package com.meritamerica.assignment6.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.meritamerica.assignment6.*;
import com.meritamerica.assignment6.model.CDAccount;

public interface CDAaccountRepository extends JpaRepository<CDAccount, Long> {
	List<CDAccount> findByAccountHolder(long id);

}

