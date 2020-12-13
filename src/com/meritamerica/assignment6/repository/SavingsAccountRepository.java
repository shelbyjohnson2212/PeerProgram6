package com.meritamerica.assignment6.repository;

import org.hibernate.mapping.List;
import org.springframework.data.jpa.repository.support.*;

public interface SavingsAccountRepository extends JpaRepository<SavingsAccount, Long> {
	List<SavingsAccount> findAccountHolderIDs(long id);
}
