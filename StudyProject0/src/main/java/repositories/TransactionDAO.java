package repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Account;
import models.Transaction;
import utilities.JDBC;

public class TransactionDAO implements GenericDAO<Transaction> {

	private Connection conn = JDBC.getConnection();
	private static TransactionDAO instance;
	
	private TransactionDAO() {
	}

	public static TransactionDAO getInstance() {
		if (instance == null)
			instance = new TransactionDAO();
		return instance;
	}

	@Override
	public Transaction add(Transaction t) {
		String sql = "insert into transactions values (default, ?, ?, ?, ?) returning *;";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, t.getSource().getId());
			ps.setString(2, t.getType());
			ps.setFloat(3, t.getAmount());
			
			//if you are doing a transfer, we need to look at another account id
			if (t.getReceiver() != null && t.getType().equalsIgnoreCase("transfer"))
				ps.setInt(4, t.getReceiver().getId());
			//Otherwise look at the same account
			else
				ps.setInt(4, t.getSource().getId());
			
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				t.setId(rs.getInt("id"));
				return t;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Transaction getById(Integer id) {
		String sql = "select * from transactions where id = ?;";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Transaction t = new Transaction();
				t.setId(rs.getInt("id"));
				t.setSource(AccountDAO.getInstance().getById(rs.getInt("send_from")));
				t.setType(rs.getString("type"));
				t.setAmount(rs.getFloat("amount"));
				t.setReceiver(AccountDAO.getInstance().getById(rs.getInt("send_to")));
				return t;

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<Transaction> getAll() {
		String sql = "select * from transactions;";
		List<Transaction> transactions = new ArrayList<Transaction>();

		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Transaction t = new Transaction();
				t.setId(rs.getInt("id"));
				t.setSource(AccountDAO.getInstance().getById(rs.getInt("send_from")));
				t.setType(rs.getString("type"));
				t.setAmount(rs.getFloat("amount"));

				if (t.getType().equalsIgnoreCase("transfer")) {
					t.setReceiver(AccountDAO.getInstance().getById(rs.getInt("send_to")));
				}

				transactions.add(t);
			}

			return transactions;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public boolean update(Transaction t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Transaction t) {
		// TODO Auto-generated method stub
		return false;
	}
	
	//This is for testing my TransactionDAO
	public static void main(String[] args) {
//		TransactionDAO tdao = new TransactionDAO();
		
		//Testing add() method
//		Transaction t = new Transaction(1, AccountDAO.getInstance().getById(2), "transfer", (float) 5000, AccountDAO.getInstance().getById(3));
//		System.out.println(tdao.add(t));
		
		//Testing getById() method
//		System.out.println(TransactionDAO.getInstance().getById(4));
		
		//Testing getAll() method
//		System.out.println(TransactionDAO.getInstance().getAll());
		
	}

}
