package Tests;

import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Savepoint;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;

import models.Customer;
import repositories.CustomerDAO;
import utilities.JDBC;

public class TestData {
	
	private static Savepoint sp;
	private static Connection conn;
	private Integer testId = 4;
	
	@BeforeClass
	public static void beforeClass() {
		conn = JDBC.getConnection();
	}
	
	@Before
	public void before() {
		try {
			conn.setAutoCommit(false);
			sp = conn.setSavepoint();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void addCustomerTest() {
		Customer testAgainst = new Customer(testId, "test", "test", true);
		Customer c = new Customer("test", "test", true);
		Customer test = CustomerDAO.getInstance().add(c);
		Assert.assertEquals(testAgainst, test);
	}
	
	@After
	public void after() {
		try {
			conn.rollback(sp);
			conn.setAutoCommit(true);
			String sql = String.format("alter sequence customers_id_seq restart with %d;", testId);
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
