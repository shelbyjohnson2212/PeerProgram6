package com.meritamerica.assignment6.repository;

import org.springframework.data.jpa.repository.support.*;
import com.meritamerica.assignment6.*;

public class CheckingAccountRepository extends JpaRepository<CheckingAccount, Integer>{
	List<CheckingAccount> findAccountHolderIDs(long id);
}
