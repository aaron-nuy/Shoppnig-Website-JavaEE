package model;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.MessagingException;

import model.dataTypes.Product;
import model.dataTypes.User;

public class Manager {

	
	
	public static void addProduct(String pname, String pdesc,String pprice, String suppid) throws RuntimeException{	
		Double price = Double.parseDouble(pprice);
		int supp_id = Integer.parseInt(suppid);	
		
		try {
			Db.insertProduct(pname,pdesc,price,supp_id);
		}
		catch(SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	
	public static ArrayList<Product> getAllProducts() throws RuntimeException{
		try {
			return Db.selectAllProcuts();
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}		
	}
	
	public static ArrayList<User> getAllUsers() throws RuntimeException{
		try {
			return Db.selectAllUsers();
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}		
	}
	
	public static ArrayList<User> getAllSuppliers() throws RuntimeException{
		try {
			return Db.selectAllSuppliers();
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}		
	}
	
	public static void updateProduct(String productName,String productDescription,String productPrice,String productID) throws RuntimeException {
		validateProductData(productName,productDescription,productPrice);
		
		Double price = Double.parseDouble(productPrice);
		int id = Integer.parseInt(productID);
		
		try {
			Db.updateProduct(productName,productDescription,price, id);
		}
		catch(SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
		
	}
	
	public static void updateUserReputation(int id,float reputation) throws RuntimeException {
		
		try {
			float oldRep = Db.getUserReputation(id);
			
			Db.updateUserReputation(id,oldRep + reputation);
		}
		catch(SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
		
	}
	
	
	public static void deleteProduct(int productID) throws RuntimeException {
		try {
			Db.deleteProduct(productID);
		}
		catch(SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	public static void deleteUser(int userID) throws RuntimeException {
		try {
			Db.deleteUser(userID);
		}
		catch(SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	
	
	public static boolean createAccount(String firstName,String lastName,String eMail
					,String regNum,String password,String gender,String birthdate) throws RuntimeException{
		
		if(validateData(firstName,lastName,eMail,regNum,password,gender)) {
			User user;
			
			// If user has no registration number then it's not a supplier
			if(regNum.equals("none")) {
				user = new User(firstName,lastName,gender,eMail,password,birthdate,"user",0,0,0);		
			}
			else {
				user = new User(firstName,lastName,gender,eMail,password,birthdate,"supplier",0,Integer.parseInt(regNum),0);
			}
			
			
			try {
				Db.insertUser(user);
				return true;
			} catch (SQLException e) {
				throw new RuntimeException("Email Already Exists");
			}

		}
		
		return false;
	}
	
	public static User login(String email, String password) throws RuntimeException{
		User user = null;
		try {
			user = Db.selectUser(email,password);
		}
		catch(SQLException e) {
			throw new RuntimeException("An SQL Error occured");
		}
		
		return user;		
	}
	
	public static User getUser(String email, String password) throws RuntimeException{
		User user = null;
		try {
			user = Db.selectUser(email,password);
		}
		catch(SQLException e) {
			throw new RuntimeException("An SQL Error occured");
		}
		
		return user;		
	}
	
	public static ArrayList<Product> getUserProducts(int userID) throws RuntimeException{
		
		try {
			return Db.selectUserProcuts(userID);
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}	
	}
	
	
	public static Product getProduct(int productID) throws RuntimeException{
		
		try {
			return Db.selectProduct(productID);
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
		
	}
	
	public static User getUser(int user_id) throws RuntimeException{
		
		try {	
			return Db.selectUser(user_id);
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
		
	}
	
	public static void processPurchase(String productString, int userID) {
		
		String[] sProducts = productString.split(" ");
		ArrayList<Product> productList = new ArrayList<Product>();
		
		for(String product : sProducts) {
			int product_id = Integer.parseInt(product);
			
			productList.add(Manager.getProduct(product_id));
		}
		
		
		User user = Manager.getUser(userID);
		
		byte[] invoice = PDFInvoice.render(productList,user);
		
		try {
			EmailMessage invoiceEmail = new EmailMessage(user.geteMail(),"Purchase from SITE_PFC Successful","Thank you for shopping with us."
					+ "	Your invoice is linked below.", invoice);
			
			
			Thread sendingThread = new Thread( new Runnable() {
				public void run(){
					try {
						invoiceEmail.send();
					} catch (MessagingException e) {
						System.out.println("Could not send email: " + e.getMessage());
					}
				}	
			});
			
			sendingThread.start();
			
		} catch (MessagingException | IOException e) {
			System.out.println("Could not send email: " + e.getMessage());
		}
		
	}
	
	
	private static boolean validateData(String first,String last,String email,String number,String password,String gender) throws RuntimeException {
				
		validateEmail(email);
		validateName(first);
		validateName(last);
		validateNumber(number);
		validateGender(gender);
		validatePassword(password);

		return true;
	}

	private static void validateProductData(String name,String description,String price) throws RuntimeException {
		
		validateProductName(name);
		validateProductDescription(description);
		validateProductPrice(price);

	}
	public static void validateProductName(String name) throws RuntimeException {
		if (name.length() > 255) {
			throw new RuntimeException("Product name too long");
		}
	}	
	public static void validateProductDescription(String desc) throws RuntimeException {
		if (desc.length() > 255) {
			throw new RuntimeException("Product description too long");
		}
	}
	public static void validateProductPrice(String price) throws RuntimeException {
		Double.parseDouble(price);
	}
	private static void validateEmail(String email) throws RuntimeException{
		Pattern pt = Pattern.compile("\\w+@\\w+\\.[A-Za-z]+");
		Matcher mt = pt.matcher(email);
		
		if(!mt.matches() || email.length()>30) {
			throw new RuntimeException("Email too long or wrong format");
		}
		
	}
	private static void validateName(String name) throws RuntimeException {
		Pattern pt = Pattern.compile("[A-Za-z]{2,30}");
		Matcher mt = pt.matcher(name);
		
		if(!mt.matches() || name.length()>30) {
			throw new RuntimeException("Name too long or contains numbers/spaces/special characters");
		}
	}
	private static void validateNumber(String number) throws RuntimeException {
		Pattern pt = Pattern.compile("\\d{2,30}");
		Matcher mt = pt.matcher(number);

		if(!mt.matches() && !number.equals("none")) {
			throw new RuntimeException("Registration number can't contain letters");
		}
		else if(number.length()>9) {
			throw new RuntimeException("Registration number too long");
		}
	}
	private static void validatePassword(String password) throws RuntimeException {
		if(password.length() <= 2 || password.length() > 30) {
			throw new RuntimeException("Password too long or too short");
		}
	}
	private static void validateGender(String gender) throws RuntimeException {	
		if(!gender.equals("f") && !gender.equals("m")) {
			throw new RuntimeException("Wrong Gender");
		}
	}
	

}
