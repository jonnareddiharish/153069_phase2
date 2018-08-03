package com.cg.mypaymentapp.pl;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.*;
import com.cg.mypaymentapp.beans.Customer;
import com.cg.mypaymentapp.exception.InvalidInputException;
import com.cg.mypaymentapp.service.WalletService;
import com.cg.mypaymentapp.service.WalletServiceImpl;

public class Client {
//	WalletServiceImpl cus = new WalletServiceImpl();
	Scanner sc = new Scanner(System.in);
	WalletService ser;
	public Client()
	{
		ser = new WalletServiceImpl();
	}
	public void menu()
	{
		
		System.out.println("1. Create Account ");
		System.out.println("2. Find user ");
		System.out.println("3. Fund transfer ");
		System.out.println("4. Deposit money ");
		System.out.println("5. WithDraw money ");
		System.out.println("6. List transactions ");

		System.out.println("Please enter your choice");
		int choice= sc.nextInt();
		
			switch(choice)
			{
			case 1:
				try 
				{
				System.out.print("Enter your name    : ");
				String name = sc.next();
				System.out.print("Enter phone number : ");
				String phoneNumber = sc.next();
				System.out.print("Enter the amount   : ");
				BigDecimal amount=sc.nextBigDecimal();
				
				try {
					ser.createAccount(name,phoneNumber,amount);
					System.out.println("Account successfully created");
					System.out.println("your Balance is :" + amount);
					System.out.println("");
				} catch (InvalidInputException |InputMismatchException |NullPointerException e) {
					//e.printStackTrace();
					System.out.println(e.getMessage());
				}catch(Exception e)
				{
					//e.printStackTrace();
					System.out.println(e.getMessage());
				}
				
			}
		catch (Exception e) {
			System.out.println(e.getMessage());
			//e.printStackTrace();
		}
				break;
			case 2:
				System.out.print("enter mobile number : ");
				String mob = sc.next();
				try {
					Customer customer = ser.showBalance(mob);
					System.out.println("MobileNo :"+customer.getMobileNo());
					System.out.println("Name     :"+customer.getName());
					System.out.println("Balance  :"+customer.getWallet().getBalance());
					System.out.println("");
				} catch (InvalidInputException |InputMismatchException e) {
					System.out.println(e.getMessage());
					//e.printStackTrace();
				}catch(Exception e)
				{
					System.out.println(e.getMessage());
				}
				break;
			case 3:
				System.out.print("Enter source mobile number       : ");
				String s_mob = sc.next();
				System.out.print("Enter destination mobile number  : ");
				String d_mob = sc.next();
				System.out.print("        Enter amount             : ");
				BigDecimal bal=sc.nextBigDecimal();
				
				try {
					Customer customer =ser.fundTransfer(s_mob, d_mob, bal);
					System.out.println("successfully transfered");
					System.out.println("Your Balance is :"+ customer.getWallet().getBalance());
					System.out.println("");
				} catch (InvalidInputException |InputMismatchException e1) {
					System.out.println(e1.getMessage());
					//e1.printStackTrace();
				}catch(Exception e)
				{
					System.out.println(e.getMessage());
				}
				
				break;
			case 4:
				System.out.print("Enter mobile number : ");
				String mobile=sc.next();
				System.out.print("    Enter amount    : ");
				BigDecimal balance = sc.nextBigDecimal();				
				try {
					Customer custom=ser.depositAmount(mobile, balance);
					System.out.println("sucussfully completed");
					System.out.println("Your Balance is :"+custom.getWallet().getBalance());
					System.out.println("");
				} catch (InvalidInputException |InputMismatchException e) {					
					System.out.println(e.getMessage());
					//e.printStackTrace();
				}catch(Exception e)
				{
					System.out.println(e.getMessage());
				}
				break;
			case 5:
				System.out.print("Enter mobile number      : ");
				String mobileno=sc.next();
				System.out.print("Enter amount to withdraw :");
				BigDecimal balance1 = sc.nextBigDecimal();
				
				try {
					Customer customer1=ser.withdrawAmount(mobileno,balance1);
					System.out.println("successfully withdrawed");
					System.out.println("Your Balance is "+customer1.getWallet().getBalance());
					System.out.println("");
				} catch (InvalidInputException |InputMismatchException e) {
					System.out.println(e.getMessage());
					//e.printStackTrace();
				}catch(Exception e)
				{
					System.out.println(e.getMessage());
				}
				
			break;
			case 6:
				System.out.print("enter mobile number : ");
				String mobil=sc.next();
				
				List l=ser.getTransaction(mobil);
				if(l!=null)
				{
				Iterator it = l.iterator();
				while(it.hasNext())
				{
					System.out.println(it.next());
				}
				}
				else
					System.out.println("Mobile number not found");
			}
		
	}

	public static void main(String[] args) {
		
		Client cl = new Client();
		while(true)
		cl.menu();
		
		
	}
}
