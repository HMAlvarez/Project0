package repositories;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Account;
import utilities.JDBC;

public class AccountDAO implements GenericDAO<Account> {

	private Connection conn = JDBC.getConnection();
	private static AccountDAO instance;
	
	private AccountDAO() {
	}

	public static AccountDAO getInstance() {
		if (instance == null)
			instance = new AccountDAO();
		return instance;
	}

	@Override
	public Account add(Account a) {
		String sql = "insert into accounts values (default, ?, ?, ?) returning *;";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setFloat(1, a.getBalance());
			ps.setInt(2, a.getCustomer_id());
			ps.setBoolean(3, a.getPending());
			boolean success = ps.execute();
			
			if (success) {
				ResultSet rs = ps.getResultSet();
				
				if (rs.next()) {
					a.setId(rs.getInt("id"));
					return a;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Account getById(Integer id) {
		String sql = "select * from accounts where id = ?;";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				Account a = new Account();
				a.setId(rs.getInt("id"));
				a.setBalance(rs.getFloat("balance"));
				a.setCustomer_id(rs.getInt("customer"));
				a.setPending(rs.getBoolean("pending"));
				return a;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public List<Account> getPendingAccounts() {
		String sql = "select * from accounts where pending = true;";
		try {
			List<Account> list = new ArrayList<Account>();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Account a = new Account();
				a.setId(rs.getInt("id"));
				a.setBalance(rs.getFloat("balance"));
				a.setCustomer_id(rs.getInt("customer"));
				a.setPending(rs.getBoolean("pending"));
				list.add(a);
			}

			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public List<Account> getAll() {
		String sql = "select * from accounts;";
		List<Account> accounts = new ArrayList<Account>();
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Account a = new Account();
				a.setId(rs.getInt("id"));
				a.setBalance(rs.getFloat("balance"));
				a.setCustomer_id(rs.getInt("customer"));
				a.setPending(rs.getBoolean("pending"));
				accounts.add(a);
			}

			return accounts;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public List<Account> getAllByCustomerId(Integer id) {
		String sql = "select * from accounts where customer = ?;";
		List<Account> accounts = new ArrayList<Account>();
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Account a = new Account();
				a.setId(rs.getInt("id"));
				a.setBalance(rs.getFloat("balance"));
				a.setCustomer_id(rs.getInt("customer"));
				a.setPending(rs.getBoolean("pending"));
				accounts.add(a);
			}

			return accounts;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public boolean update(Account a) {
		String sql = "update accounts set balance = ?, pending = ? where id = ? returning *;";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setFloat(1, a.getBalance());
			ps.setBoolean(2, a.getPending());
			ps.setInt(3, a.getId());
			
			return ps.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean delete(Account a) {
		String sql = "delete from accounts where id = ?;";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, a.getId());
			
			return ps.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}
	
	//This is for testing my AccountDAO
	public static void main(String[] args) {
//		AccountDAO adao = new AccountDAO();
		
		//Testing add() method
//		Account a = new Account(1, (float) 5000, 1, true);
//		System.out.println(adao.add(a));
		
		//Testing getByID() method
//		System.out.println(adao.getById(1));
		
		//Testing getAll() method
//		System.out.println(adao.getAll());
		
		//Testing getAllByCustomerId method
//		System.out.println(adao.getAllByCustomerId(3));



	}
	
}
