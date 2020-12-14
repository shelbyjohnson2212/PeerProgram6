package com.meritamerica.assignment6.repository;

import org.hibernate.mapping.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.*;

import com.meritamerica.assignment6.model.SavingsAccount;

import javafx.scene.control.ListCell;

public interface SavingsAccountRepository extends JpaRepository<SavingsAccount, Long> {
	ListCell<SavingsAccount> findAccountHolderIDs(long id);
}
