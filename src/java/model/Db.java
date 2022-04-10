package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.dataTypes.Product;
import model.dataTypes.User;


public final class Db {
	static private final String url = "jdbc:mysql://localhost/site";
	static private final String username = "admin";
	static private final String password = "admin";
	
	static private Connection connect() {

		Connection con = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url,username,password);
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		catch(ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
		return con;
	}

	
	static public void insertUser(User user) throws SQLException {
		Statement s = connect().createStatement();
		
		String query = "INSERT INTO users " + "(first_name, last_name, email, reg_number,birthdate,gender,password,user_type,reputation) " +
				"VALUES ('" + user.getFirstName() + "', '" + user.getLastName() + "', '"+ 
				user.geteMail() + "',"+ String.valueOf(user.getRegNumber()) + ", '"+ user.getBirthDate() +"','" +
				user.getGender() + "\'," + "\'"+ user.getPassword() + "\',\'"+ user.getType() +"\'" + String.valueOf(user.getReputation()) + ");";
		
		s.executeUpdate(query);
		s.close();
	}

	static public void insertProduct(Product product) throws SQLException {
		Statement s = connect().createStatement();
		
		String query = "INSERT INTO products (name,description,price,supplier_id) VALUES(\'" + product.getName() + "\',\' " +
				product.getDescription() + "\' , " + String.valueOf(product.getPrice()) + 
				"," + String.valueOf(product.getSupplier_id()) + ")";
		
		s.executeUpdate(query);
		s.close();
	}
	
	static public void insertProduct(String t_name,String t_desc,double t_price,int t_supp_id) throws SQLException {
		Statement s = connect().createStatement();
		
		String query = "INSERT INTO products (name,description,price,supplier_id) VALUES(\'" + t_name + "\',\' " +
				t_desc + "\' , " + String.valueOf(t_price) + 
				"," + String.valueOf(t_supp_id) + ")";
		
		s.executeUpdate(query);
		s.close();
	}
	
	static public void insertProduct(String t_name,String t_desc,double t_price,int t_supp_id,int t_id) throws SQLException {
		Statement s = connect().createStatement();
		
		String query = "INSERT INTO products (name,description,price,supplier_id) VALUES(\'" + t_name + "\',\' " +
				t_desc + "\' , " + String.valueOf(t_price) + 
				"," + String.valueOf(t_supp_id) + ")";
		
		s.executeUpdate(query);
		s.close();
	}
	
	static public void updateProduct(String t_name,String t_desc,double t_price,int t_id) throws SQLException {
		Statement s = connect().createStatement();
		
		String query = "UPDATE products SET name = \'" + t_name + "\', description = \'" + t_desc + "\', price = \'" + t_price + "\' WHERE id = \'" + t_id + "\'";
		
		s.executeUpdate(query);
		s.close();
	}
	
	static public void updateUserReputation(int id, float rating) throws SQLException {
		Statement s = connect().createStatement();
		
		String query = "UPDATE users SET reputation = " + String.valueOf(rating) + " WHERE id = \'" + id + "\'";
		
		s.executeUpdate(query);
		s.close();
	}
	
	public static float getUserReputation(int id) throws SQLException {
		Statement s = connect().createStatement();
		
		String query = "SELECT reputation FROM users WHERE id=" + String.valueOf(id);
		
		ResultSet rs = s.executeQuery(query);

		float rep = 0;
		if(rs.next()) {
		    rep = rs.getFloat("reputation");
		}
		
		s.close();
		return rep;
	}
	
	static public void updateProduct(Product product) throws SQLException {
		Statement s = connect().createStatement();
		
		String query = "UPDATE products SET name = \'" + product.getName() + "\', description = \'" + product.getDescription() + 
				"\', price = \'" + product.getPrice() + "\' WHERE id = \'" + product.getId() + "\'";
		
		s.executeUpdate(query);
		s.close();
	}
	
	
	
	static public void deleteProduct(Product product) throws SQLException {
		Statement s = connect().createStatement();
		
		String query = "DELETE FROM products WHERE id=" + String.valueOf(product.getId());
		
		s.executeUpdate(query);
		s.close();
	}
	
	static public void deleteProduct(int id) throws SQLException {
		Statement s = connect().createStatement();
		
		String query = "DELETE FROM products WHERE id=" + String.valueOf(id);
		
		s.executeUpdate(query);
		s.close();
		
	}
	
	public static void deleteUserProducts(int userid) throws SQLException{
		Statement s = connect().createStatement();
		
		String query = "DELETE FROM products WHERE supplier_id=" + String.valueOf(userid);
		
		s.executeUpdate(query);
		s.close();
	}
	
	static public void deleteUser(int userid) throws SQLException {
		Statement s = connect().createStatement();
		
		deleteUserProducts(userid);
		
		String query = "DELETE FROM users WHERE id=" + String.valueOf(userid);
		
		s.executeUpdate(query);
		s.close();
		
	}
	
	static public ArrayList<Product> selectUserProcuts(User user) throws SQLException {
		
		ArrayList<Product> productList = new ArrayList<Product>();
		
		Statement s = connect().createStatement();
		
		String query = "SELECT * FROM products WHERE supplier_id = " + String.valueOf(user.getId()) ;
		ResultSet rs = s.executeQuery(query);

		while(rs.next()) {
	        String p_name = rs.getString("name");
	        String p_description = rs.getString("description");
	        double p_price = rs.getDouble("price");
	        int p_id = rs.getInt("id");
	        int p_supplier_id = rs.getInt("supplier_id");

	        productList.add(new Product(p_name,p_description,p_price,p_id,p_supplier_id));
		}
		
		s.close();
		return productList;	
	}

	static public ArrayList<Product> selectUserProcuts(int user_id) throws SQLException {
		
		ArrayList<Product> productList = new ArrayList<Product>();
		
		Statement s = connect().createStatement();
		
		String query = "SELECT * FROM products WHERE supplier_id = " + String.valueOf(user_id) ;
		ResultSet rs = s.executeQuery(query);

		while(rs.next()) {
	        String p_name = rs.getString("name");
	        String p_description = rs.getString("description");
	        double p_price = rs.getDouble("price");
	        int p_id = rs.getInt("id");
	        int p_supplier_id = rs.getInt("supplier_id");

	        productList.add(new Product(p_name,p_description,p_price,p_id,p_supplier_id));
		}
		
		s.close();
		return productList;	
	}
	
	static public ArrayList<Product> selectAllProcuts() throws SQLException {
		
		ArrayList<Product> productList = new ArrayList<Product>();
		
		Statement s = connect().createStatement();
		
		String query = "SELECT * FROM products WHERE TRUE;";
		ResultSet rs = s.executeQuery(query);

		while(rs.next()) {
	        String p_name = rs.getString("name");
	        String p_description = rs.getString("description");
	        double p_price = rs.getDouble("price");
	        int p_id = rs.getInt("id");
	        int p_supplier_id = rs.getInt("supplier_id");

	        productList.add(new Product(p_name,p_description,p_price,p_id,p_supplier_id));
		}
		
		s.close();
		return productList;	
	}

	static public User selectUser(int id) throws SQLException {
		
		Statement s = connect().createStatement();
		
		String query = "SELECT * FROM users WHERE id=" + String.valueOf(id);
		
		ResultSet rs = s.executeQuery(query);
		
		if(rs.next()) {
	        int u_id = rs.getInt("id");
	        int u_regnum = rs.getInt("reg_number");
	        float reputation = rs.getFloat("reputation");
	        String u_firstName = rs.getString("first_name");
	        String u_lastName = rs.getString("last_name");
	        String u_email = rs.getString("email");
	        String u_type = rs.getString("user_type");
	        String u_gender = rs.getString("gender");
	        String u_password = rs.getString("password");
	        String u_birthdate = rs.getDate("birthdate").toString();
	        
			s.close();
	        return new User(u_firstName,u_lastName,u_gender,u_email,u_password,u_birthdate,u_type,u_id,u_regnum,reputation);

		}
		
		s.close();
		throw new RuntimeException("No user exists with that id");
		
	}
	
	static public User selectUser(User user) throws SQLException {
		
		Statement s = connect().createStatement();
		
		String query = "SELECT * FROM users WHERE email=\'" + user.geteMail() + "\' AND password=\'" + user.getPassword() + "\';";
		
		ResultSet rs = s.executeQuery(query);
		
		if(rs.next()) {
	        int u_id = rs.getInt("id");
	        int u_regnum = rs.getInt("reg_number");
	        float reputation = rs.getFloat("reputation");
	        String u_firstName = rs.getString("first_name");
	        String u_lastName = rs.getString("last_name");
	        String u_email = rs.getString("email");
	        String u_type = rs.getString("user_type");
	        String u_gender = rs.getString("gender");
	        String u_password = rs.getString("password");
	        String u_birthdate = rs.getDate("birthdate").toString();
	        
			s.close();
	        return new User(u_firstName,u_lastName,u_gender,u_email,u_password,u_birthdate,u_type,u_id,u_regnum,reputation);

		}
		
		s.close();
		throw new RuntimeException("No user exists with those details");
		
	}
	
	static public User selectUser(String email, String password) throws SQLException {
		
		Statement s = connect().createStatement();
		
		String query = "SELECT * FROM users WHERE email=\'" + email + "\' AND password=\'" + password + "\';";
		
		ResultSet rs = s.executeQuery(query);
		
		if(rs.next()) {
	        int u_id = rs.getInt("id");
	        int u_regnum = rs.getInt("reg_number");
	        float reputation = rs.getFloat("reputation");
	        String u_firstName = rs.getString("first_name");
	        String u_lastName = rs.getString("last_name");
	        String u_email = rs.getString("email");
	        String u_type = rs.getString("user_type");
	        String u_gender = rs.getString("gender");
	        String u_password = rs.getString("password");
	        String u_birthdate = rs.getDate("birthdate").toString();
	        
			s.close();
	        return new User(u_firstName,u_lastName,u_gender,u_email,u_password,u_birthdate,u_type,u_id,u_regnum,reputation);

		}
		
		s.close();
		throw new RuntimeException("No account exists with those credentials");
		
	}
	
	static public boolean userExists(String email,String password) throws SQLException {
		Statement s = connect().createStatement();
		
		String query = "SELECT * FROM users WHERE email=\'" + email + "\' AND password=\'" + password + "\'";
		
		ResultSet rs = s.executeQuery(query);
		
		if(rs.next()) {
			s.close();
	        return true;
		}
		
		s.close();
		return false;
	}
	
	static public boolean userExists(User user) throws SQLException {
		Statement s = connect().createStatement();
		
		String query = "SELECT * FROM users WHERE email=\'" + user.geteMail() + "\' AND password=\'" + user.getPassword() + "\'";
		
		ResultSet rs = s.executeQuery(query);
		
		if(rs.next()) {
			s.close();
	        return true;
		}
		
		s.close();
		return false;
	}

	public static ArrayList<User> selectAllUsers() throws SQLException{
		
		ArrayList<User> userList = new ArrayList<User>();
		
		Statement s = connect().createStatement();
		
		String query = "SELECT * FROM users WHERE TRUE";
		ResultSet rs = s.executeQuery(query);

		while(rs.next()) {
	        int u_id = rs.getInt("id");
	        int u_regnum = rs.getInt("reg_number");
	        float reputation = rs.getFloat("reputation");
	        String u_firstName = rs.getString("first_name");
	        String u_lastName = rs.getString("last_name");
	        String u_email = rs.getString("email");
	        String u_type = rs.getString("user_type");
	        String u_gender = rs.getString("gender");
	        String u_password = rs.getString("password");
	        String u_birthdate = rs.getDate("birthdate").toString();
	        
			s.close();
	        userList.add(new User(u_firstName,u_lastName,u_gender,u_email,u_password,u_birthdate,u_type,u_id,u_regnum,reputation));
		}
		
		s.close();
		return userList;	
		
	}
	
	public static ArrayList<User> selectAllSuppliers() throws SQLException{
		
		ArrayList<User> userList = new ArrayList<User>();
		
		Statement s = connect().createStatement();
		
		String query = "SELECT * FROM users WHERE user_type = \'supplier\';";
		ResultSet rs = s.executeQuery(query);


		while(rs.next()) {
	        int u_id = rs.getInt("id");
	        int u_regnum = rs.getInt("reg_number");
	        float reputation = rs.getFloat("reputation");
	        String u_firstName = rs.getString("first_name");
	        String u_lastName = rs.getString("last_name");
	        String u_email = rs.getString("email");
	        String u_type = rs.getString("user_type");
	        String u_gender = rs.getString("gender");
	        String u_password = rs.getString("password");
	        String u_birthdate = rs.getDate("birthdate").toString();
	        
	        userList.add(new User(u_firstName,u_lastName,u_gender,u_email,u_password,u_birthdate,u_type,u_id,u_regnum,reputation));
		}
		
		s.close();
		return userList;	
		
	}


	public static Product selectProduct(int productID) throws SQLException {
		Statement s = connect().createStatement();
		
		String query = "SELECT * FROM products WHERE id=" + String.valueOf(productID) ;
		
		ResultSet rs = s.executeQuery(query);
		
		if(rs.next()) {
	        int id = rs.getInt("id");
	        int supplier_id = rs.getInt("supplier_id");
	        double price = rs.getDouble("price");
	        String name = rs.getString("name");
	        String description = rs.getString("description");

	        
			s.close();
			return new Product(name,description,price,id,supplier_id);
		}
		throw new RuntimeException("Product doesn't exist");
	}
		
}
