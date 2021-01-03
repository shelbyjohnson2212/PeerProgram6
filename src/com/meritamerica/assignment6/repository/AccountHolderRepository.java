package com.meritamerica.assignment6.repository;

import com.meritamerica.assignment6.misc.*;
import com.meritamerica.assignment6.model.AccountHolder;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountHolderRepository extends JpaRepository<AccountHolder, Long> {
	AccountHolder findById(long id);

	
 	
	

}
