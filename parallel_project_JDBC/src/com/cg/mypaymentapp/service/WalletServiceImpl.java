
package com.cg.mypaymentapp.service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.cg.mypaymentapp.beans.Customer;
import com.cg.mypaymentapp.beans.Wallet;
import com.cg.mypaymentapp.exception.InsufficientBalanceException;
import com.cg.mypaymentapp.exception.InvalidInputException;
import com.cg.mypaymentapp.repo.WalletRepo;
import com.cg.mypaymentapp.repo.WalletRepoImpl;


public class WalletServiceImpl implements WalletService{

	private WalletRepo repo;
	
	public WalletServiceImpl()
	{
		repo =new WalletRepoImpl();
	}

	public WalletServiceImpl(Map<String, Customer> data){
		//repo= new WalletRepoImpl(data);
	}
	public WalletServiceImpl(WalletRepo repo) {
		super();
		this.repo = repo;
	}

//		Customer cus ;

	public Customer createAccount(String name, String mobileNo, BigDecimal amount) throws InvalidInputException {
		Customer cus=new Customer();	
		if(name==null)
			throw new InvalidInputException("Name cannot be null");
		
		if(mobileNo==null)
			throw new InvalidInputException("Mobile number cannot be null");
		
		if(amount==null)
			throw new InvalidInputException("amount cannot be null");
		
		Customer customer=repo.findOne(mobileNo);
		if(customer.getMobileNo()!=null)
			throw new InvalidInputException("mobile number exists");
		
			if(isvalid(name,mobileNo,amount)==1)
			{
			cus.setName(name);
			cus.setMobileNo(mobileNo);
			cus.setWallet(new Wallet(amount));
			repo.save(cus);
			}
		else
			throw new InvalidInputException("wrong input");
		return cus;
		}
	
	 int isvalid(String name, String mobileNo, BigDecimal amount)
		{
		 int flag=0;
			String pattern = "[a-zA-Z]{3,15}";
			
			if(name.matches(pattern))
				flag = 1 ;
			else
				return 0;
					
			String mobpattern ="[1-9][0-9]{9}";
			
			if(mobileNo.matches(mobpattern))
				flag = 1;
			else
				return 0;
			
			if(amount.compareTo(new BigDecimal(0))>0)
				flag = 1;
			else
				return 0;
		return flag;	
		}

	public Customer showBalance(String mobileNo) throws InvalidInputException {
		Customer customer=repo.findOne(mobileNo);
		if(customer.getMobileNo()!=null)
			return customer;
		else
			throw new InvalidInputException("Invalid mobile number ");
	}

	public Customer fundTransfer(String sourceMobileNo, String targetMobileNo, BigDecimal amount) throws InvalidInputException, InsufficientBalanceException {
		
		if(amount==null)
			throw new InvalidInputException("Amount cannot be null");
		
		if(sourceMobileNo==null)
			throw new InvalidInputException("SourceMobile mobile number cannot be null");
		
		if(targetMobileNo==null)
			throw new InvalidInputException("Destination mobile number cannot be null");

		
		Customer cust1=repo.findOne(sourceMobileNo);
		Customer cust2=repo.findOne(targetMobileNo);
		if(cust1!=null)
		{
			if(cust2!=null)
			{
				BigDecimal bal1 = cust1.getWallet().getBalance();
				BigDecimal bal2 = cust2.getWallet().getBalance();
				if(bal1.compareTo(amount)>=0)
				{
					bal1 = bal1.subtract(amount);
				cust1.setWallet(new Wallet(bal1));
				repo.save(cust1);
					bal2=bal2.add(amount);
				cust2.setWallet(new Wallet(bal2));
				repo.save(cust2);
				String trans1=new java.util.Date() + "  your account  " + sourceMobileNo +"  is debited with " + amount + "  towards transfer with  "+ targetMobileNo +"  Balance is : "+bal1;
				repo.saveTransaction(sourceMobileNo,trans1);
				String trans2=new java.util.Date() + "  your account  " + targetMobileNo +"  is deposited with " + amount + "  towards transfer from  "+ sourceMobileNo +"  Balance is : "+bal2;
				repo.saveTransaction(targetMobileNo,trans2);
				
				}
				else
				{
					throw new InsufficientBalanceException("insufficient balance");
					//System.out.println("Insufficient balance");
				}
			}
			else
			{
				throw new InvalidInputException("Destination mobile number not found");
			}
		}else
		{
			throw new InvalidInputException("Source mobile number not found");
		}
		
		
		return cust1;
	}

	public Customer depositAmount(String mobileNo, BigDecimal amount) throws InvalidInputException {
		
		if(amount==null)
			throw new InvalidInputException("Amount cannot be null");
		
		if(mobileNo==null)
			throw new InvalidInputException("SourceMobile mobile number cannot be null");	
		
		Customer cust=repo.findOne(mobileNo);
		if(cust.getMobileNo()!=null)
		{
			BigDecimal bal = cust.getWallet().getBalance().add(amount);
			
			cust.setWallet(new Wallet(bal));
			repo.save(cust);
			String trans=new java.util.Date() + "  your account  "+ mobileNo +"  is deposited with  " + amount +"  your Balance is : "+cust.getWallet().getBalance();
			repo.saveTransaction(mobileNo,trans);
		}else
			throw new InvalidInputException("Mobile number not found");
		
		return cust;	
	}

	public Customer withdrawAmount(String mobileNo, BigDecimal amount) throws InvalidInputException, InsufficientBalanceException {
		if(amount==null)
			throw new InvalidInputException("Amount cannot be null");
		
		if(mobileNo==null)
			throw new InvalidInputException("SourceMobile mobile number cannot be null");

		Customer cust=repo.findOne(mobileNo);
		if(cust.getMobileNo()==null)
			throw new InvalidInputException("Mobile number not found");
		BigDecimal bal = cust.getWallet().getBalance();
	if(bal.compareTo(amount)>=0)
	{
		bal = bal.subtract(amount);
	cust.setWallet(new Wallet(bal));
	repo.save(cust);
	String trans=new java.util.Date() + "  your account  "+ mobileNo +"  is withdrawed with  " + amount +"  your Balance is : "+cust.getWallet().getBalance();
	repo.saveTransaction(mobileNo,trans);
	}
	else
	{
		throw new InsufficientBalanceException("Insufficient balance");
		//System.out.println("Insufficient balance");
	}
	return cust;
}

	@Override
	public List getTransaction(String mob) {
		
		return repo.getTransaction(mob);
	}
}