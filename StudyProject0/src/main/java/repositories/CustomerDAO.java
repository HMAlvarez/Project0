package repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Customer;
import utilities.JDBC;

public class CustomerDAO implements GenericDAO<Customer> {

	private Connection conn = JDBC.getConnection();
	private static CustomerDAO instance;
	
	private CustomerDAO() {
	}

	public static CustomerDAO getInstance() {
		if (instance == null)
			instance = new CustomerDAO();
		return instance;
	}
	
	@Override
	public Customer add(Customer c) {
		String sql = "insert into customers values (default, ?, ?, ?) returning *;";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, c.getUsername());
			ps.setString(2, c.getPassword());
			ps.setBoolean(3, c.isEmployee());
			boolean success = ps.execute();
			
			if (success) {
				ResultSet rs = ps.getResultSet();
				
				if(rs.next()) {
					c.setId(rs.getInt("id"));
					return c;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Customer getById(Integer id) {
		String sql = "select * from customers where id = ?;";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Customer c = new Customer();
				c.setId(rs.getInt("id"));
				c.setUsername(rs.getString("username"));
				c.setPassword(rs.getString("password"));
				c.setEmployee(rs.getBoolean("employee_check"));
				c.setAccounts(AccountDAO.getInstance().getAllByCustomerId(c.getId()));
				return c;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public Customer getByLoginInfo(String username, String password) {
		String sql = "select * from customers where username = ? and \"password\" = ?;";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Customer c = new Customer();
				c.setId(rs.getInt("id"));
				c.setUsername(rs.getString("username"));
				c.setPassword(rs.getString("password"));
				c.setEmployee(rs.getBoolean("employee_check"));
				c.setAccounts(AccountDAO.getInstance().getAllByCustomerId(c.getId()));
				return c;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Customer> getEmployees() {
		String sql = "select * from customers where employee_check = true;";
		List<Customer> list = new ArrayList<Customer>();

		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Customer c = new Customer();
				c.setId(rs.getInt("id"));
				c.setUsername(rs.getString("username"));
				c.setPassword(rs.getString("password"));
				c.setEmployee(rs.getBoolean("employee_check"));
				c.setAccounts(AccountDAO.getInstance().getAllByCustomerId(c.getId()));
				list.add(c);
			}

			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public List<Customer> getAll() {
		String sql = "select * from customers;";
		List<Customer> customers = new ArrayList<Customer>();
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Customer c = new Customer();
				c.setId(rs.getInt("id"));
				c.setUsername(rs.getString("username"));
				c.setPassword(rs.getString("password"));
				c.setEmployee(rs.getBoolean("employee_check"));
				c.setAccounts(AccountDAO.getInstance().getAllByCustomerId(c.getId()));
				customers.add(c);
			}

			return customers;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public boolean update(Customer c) {
		String sql = "update customers set username = ?, password = ?, employee_check = ? returning *;";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, c.getUsername());
			ps.setString(2, c.getPassword());
			ps.setBoolean(3, c.isEmployee());
			
			return ps.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean delete(Customer c) {
		String sql = "delete from customers where id = ?;";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, c.getId());
			
			return ps.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}
	
	//This is for testing my CustomerDAO
	public static void main(String[] args) {
//		CustomerDAO cdao = new CustomerDAO();
		
		//Test add() method
//		Customer c = new Customer(5, "test", "test", false);
//		System.out.println(CustomerDAO.getInstance().add(c));
		
		//Test getById() method
//		System.out.println(CustomerDAO.getInstance().getById(1));
		
		//Test getAll() method
//		System.out.println(CustomerDAO.getInstance().getAll());
		
		//Test getByLoginInfo() method
//		System.out.println(CustomerDAO.getInstance().getByLoginInfo("hector", "password"));
		
		//Test getEmployees() method
//		System.out.println(CustomerDAO.getInstance().getEmployees());
	}
	
}
