package com.meritamerica.assignment6.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.meritamerica.assignment6.*;
import java.util.List;

public interface CheckingAccountRepository extends JpaRepository<CheckingAccount, Integer>{
	List<CheckingAccount> findAccountHolderIDs(long id);
}
