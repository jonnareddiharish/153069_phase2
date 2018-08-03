package com.cg.mypaymentapp.repo;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.cg.mypaymentapp.DBUtil.DButil;
import com.cg.mypaymentapp.beans.Customer;
import com.cg.mypaymentapp.beans.Wallet;
import com.cg.mypaymentapp.exception.InvalidInputException;

public class WalletRepoImpl implements WalletRepo{

	public WalletRepoImpl()
	{
	super();	
	}
	
	public boolean save(Customer customer) throws InvalidInputException {
		
		String mob=customer.getMobileNo();
		String name=customer.getName();
		BigDecimal amount = customer.getWallet().getBalance();
		Customer cust=findOne(mob);
		if(cust.getMobileNo()!=null)
		{
			try(Connection con = DButil.getConnection())
			{
				PreparedStatement pstm1=con.prepareStatement("update wallet_balance set balance =? where mob_no=?");
				pstm1.setBigDecimal(1, amount);
				pstm1.setString(2, mob);
				pstm1.executeQuery();
				
			}catch(ClassNotFoundException e)
			{
				e.printStackTrace();
			}catch(SQLException e)
			{
				e.printStackTrace();
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}else
		{				
		try(Connection con = DButil.getConnection())
		{
			PreparedStatement pstm1 = con.prepareStatement("insert into bank_customer values(?,?)");
			pstm1.setString(1, mob);
			pstm1.setString(2, name);
		//	pstm2.setBigDecimal(3, amount);
			pstm1.executeQuery();
			
			PreparedStatement pstm2 =con.prepareStatement("insert into wallet_balance values(?,?)");
			pstm2.setString(1, mob);
			pstm2.setBigDecimal(2, amount);
			
			pstm2.executeQuery();
			String trans=new java.util.Date() + " : Account created with Balance of " +customer.getWallet().getBalance();
			saveTransaction(mob, trans);
			con.commit();
			System.out.println("successfully commited");
						
		}catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}catch(SQLException e)
		{
			e.printStackTrace();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		}
		return false;

	}

	public Customer findOne(String mobileNo)  {
		Customer cust=new Customer();
		
		try(Connection con = DButil.getConnection())
		{
			//cust=new Customer();
			PreparedStatement pstm1=con.prepareStatement("select * from bank_customer where mob_no =?");			
			pstm1.setString(1, mobileNo);
			ResultSet set= pstm1.executeQuery();
			
//			if(!set.next())			
//				throw new InvalidInputException(" mobile number not found ");
			
				while(set.next())
			{
				String mob= set.getString("mob_no");
				
				//System.out.println("mobile should be empty "+mob);
				
				if(mob==null)
					throw new InvalidInputException("Mobile number not found");
				
				String name=set.getString(2);
				cust.setMobileNo(mob);
				cust.setName(name);
			}
			
			PreparedStatement pstm2=con.prepareStatement("select * from wallet_balance where mob_no =?");
			pstm2.setString(1, mobileNo);
			ResultSet set2=pstm2.executeQuery();
			
			while(set2.next())
			{
				String mob= set2.getString(1);
				BigDecimal bd=set2.getBigDecimal(2);
				cust.setWallet(new Wallet(bd));
			}
			con.commit();
		}catch(ClassNotFoundException |InvalidInputException e)
		{
			System.out.println(e.getMessage());
			//e.printStackTrace();
		}catch(SQLException e)
		{
			System.out.println(e.getMessage());
			//e.printStackTrace();
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
			//e.printStackTrace();
		}
		return cust;
		
	}


	@Override
	public  void saveTransaction(String mob,String trans) {
		
		 try(Connection con = DButil.getConnection())
			{
			 PreparedStatement pstm=con.prepareStatement("insert into bank_customer_transaction values(?,?) ");
			 pstm.setString(1, mob);
			 pstm.setString(2, trans);
			 pstm.execute();
			 
			}catch(ClassNotFoundException e)
			{
				System.out.println(e.getMessage());
				//e.printStackTrace();
			}catch(SQLException e)
			{
				System.out.println(e.getMessage());
				//e.printStackTrace();
			}catch(Exception e)
			{
				System.out.println(e.getMessage());
				//e.printStackTrace();
			}		 
		
	}
	
	public List getTransaction(String mob)
	{
		List li = new ArrayList<String>();
		try(Connection con = DButil.getConnection())
		{
			 PreparedStatement pstm=con.prepareStatement("select name from bank_customer_transaction where mob_no=? ");
			pstm.setString(1, mob);
			ResultSet set= pstm.executeQuery();
			while(set.next())
			{
				li.add(set.getString(1));
			}
	
		}catch(ClassNotFoundException e)
		{
			System.out.println(e.getMessage());
			//e.printStackTrace();
		}catch(SQLException e)
		{
			System.out.println(e.getMessage());
			//e.printStackTrace();
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
			//e.printStackTrace();
		}
		return li;
	}
	
	
}
