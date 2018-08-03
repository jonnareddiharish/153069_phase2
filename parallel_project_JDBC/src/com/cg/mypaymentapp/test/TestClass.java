package com.cg.mypaymentapp.test;
import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.cg.mypaymentapp.beans.Customer;
import com.cg.mypaymentapp.beans.Wallet;
import com.cg.mypaymentapp.exception.InsufficientBalanceException;
import com.cg.mypaymentapp.exception.InvalidInputException;
import com.cg.mypaymentapp.service.WalletService;
import com.cg.mypaymentapp.service.WalletServiceImpl;


public class TestClass {

	
	WalletService service;
	
	@Before
	public void initData(){
		 Map<String,Customer> data= new HashMap<String, Customer>();
		 Customer cust1=new Customer("Amit", "9900112212",new Wallet(new BigDecimal(9000)));
		 Customer cust2=new Customer("Ajay", "9963242422",new Wallet(new BigDecimal(6000)));
		 Customer cust3=new Customer("Yogini", "9922950519",new Wallet(new BigDecimal(7000)));
				
		 data.put("9900112212", cust1);
		 data.put("9963242422", cust2);	
		 data.put("9922950519", cust3);	
			service= new WalletServiceImpl(data);
			
	}

	@Test(expected=InvalidInputException.class)
	public void testCreateAccount1() throws InvalidInputException {
		
		service.createAccount(null, null, null);				
	}

	@Test(expected=InvalidInputException.class)
	public void testCreateAccount2() throws InvalidInputException 
	{
		
	service.createAccount(null, "", null);
	}
	@Test(expected=InvalidInputException.class)
	public void testCreateAccount3() throws InvalidInputException 
	{
		
	service.createAccount("","",null);
	}
	@Test(expected=InvalidInputException.class)
	public void testCreateAccount4() throws InvalidInputException 
	{		
	service.createAccount("","",new BigDecimal(0));
	}
	
	@Test(expected=InvalidInputException.class)
	public void testCreateAccount5() throws InvalidInputException 
	{		
	service.createAccount(null,"9900112212",new BigDecimal(9000));
	}
	
	
	@Test(expected=InvalidInputException.class)
	public void testCreateAccount6() throws InvalidInputException 
	{		
	service.createAccount("","9900112212",new BigDecimal(9000));
	}
	
	
	@Test(expected=InvalidInputException.class)
	public void testCreateAccount7() throws InvalidInputException 
	{		
	service.createAccount("","",new BigDecimal(9000));
	}
	
	@Test(expected=InvalidInputException.class)
	public void testCreateAccount8() throws InvalidInputException 
	{	
	service.createAccount("Eric","9898989898",new BigDecimal(0));
	}
	
	@Test(expected=InvalidInputException.class)
	public void testWithdraw9() throws InvalidInputException, InsufficientBalanceException
	{
		service.withdrawAmount("9600045352", new BigDecimal(2000));
	}
	
	@Test	
	public void testCreateAccount10() throws InvalidInputException {
			Customer cust=new Customer();
			cust=service.createAccount("Eric","9898989898",new BigDecimal(6000));
			assertEquals("Eric", cust.getName());			
		}
	
	
	@Test
	public void testCreateAccount11() throws InvalidInputException {		
		Customer cust=new Customer();
		cust=service.createAccount("Eric","9898989898",new BigDecimal(7000));
		assertEquals("9898989898", cust.getMobileNo());	
	}
	
	
	@Test(expected=InvalidInputException.class)
	public void testCreateAccount12() throws InvalidInputException 
	{		
	service.createAccount("Eric","",new BigDecimal(0));
	}
	
	
	@Test(expected=InvalidInputException.class)
	public void testCreateAccount13() throws InvalidInputException 
	{		
	service.createAccount("Eric","",new BigDecimal(6000));
	}
	
	
	@Test(expected=InvalidInputException.class)
	public void testCreateAccount14() throws InvalidInputException 
	{		
	service.createAccount(null,"",new BigDecimal(0));
	}
	
	@Test(expected=InvalidInputException.class)
	public void testCreateAccount15() throws InvalidInputException 
	{		
	service.createAccount("","",new BigDecimal(6000));
	}
	
	
	@Test(expected=InvalidInputException.class)
	public void testShowBalance16() throws InvalidInputException {
		Customer cust=new Customer();
	cust=service.showBalance("9898989898");
	}
	
	
	@Test(expected=InvalidInputException.class)
	public void testShowBalance17() throws InvalidInputException {
		service.showBalance("");
	}
	
	
	@Test(expected=InvalidInputException.class)
	public void testShowBalance18() throws InvalidInputException {
		service.showBalance("9898989898");
	}
	
	@Test(expected=InvalidInputException.class)
	public void testShowBalance19() throws InvalidInputException {
		service.showBalance("");
	}

	
	@Test
	public void testShowBalance20() throws InvalidInputException {
		Customer cust=new Customer();
	cust=service.showBalance("9900112212");
	BigDecimal actual=cust.getWallet().getBalance();
	BigDecimal expected=new BigDecimal(9000);
	assertEquals(expected, actual);
	}
	
	
	@Test(expected=InvalidInputException.class)
	public void testFundTransfer21() throws InvalidInputException, InsufficientBalanceException {
		service.fundTransfer("","",new BigDecimal(7000));
	}

	
	@Test(expected=InvalidInputException.class)
	public void testFundTransfer22() throws InvalidInputException, InsufficientBalanceException {
		service.fundTransfer("", "", new BigDecimal(6000));
	}

	@Test(expected=InvalidInputException.class)
	public void testFundTransfer23() throws InvalidInputException, InsufficientBalanceException {
		service.fundTransfer("", "", new BigDecimal(0));
	}
	
	
@Test(expected=InvalidInputException.class)
public void testDeposit24() throws InvalidInputException
{
	service.depositAmount("9898989898", new BigDecimal(2000));
}
@Test(expected=InvalidInputException.class)
public void testDeposit25() throws InvalidInputException
{
	service.depositAmount("", new BigDecimal(2000));
}
	

	
}
