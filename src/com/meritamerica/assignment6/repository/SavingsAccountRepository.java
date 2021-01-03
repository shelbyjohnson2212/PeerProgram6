package com.meritamerica.assignment6.repository;

import com.meritamerica.assignment6.accounts.*;
import com.meritamerica.assignment6.model.SavingsAccount;

import javafx.scene.control.ListCell;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavingsAccountRepository extends JpaRepository<SavingsAccount, Long> {
	ListCell<SavingsAccount> findAccountHolderIDs(long id);
}