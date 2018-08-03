package com.cg.mypaymentapp.repo;

import java.math.BigDecimal;
import java.util.List;

import com.cg.mypaymentapp.beans.Customer;
import com.cg.mypaymentapp.exception.InvalidInputException;

public interface WalletRepo {

public boolean save(Customer customer) throws InvalidInputException;
	
	public Customer findOne(String mobileNo)throws InvalidInputException;
//	public void saveTransaction(String str,BigDecimal bd, String meth);
	
	public void saveTransaction(String mob, String trans1);
	public List getTransaction(String mob);
	
}
