package model.dataTypes;

import java.io.Serializable;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	private int regNumber;
	private float reputation;
	private String firstName;
	private String lastName;
	private String eMail;
	private String birthDate;
	private String password;
	private String gender;
	private String type;
	
	public User(String firstname, String lastname,String m_gender, String email, String m_password,String u_birthdate, String t_type,int t_id,int u_regnum, float u_reputation ) {
		firstName = firstname;
		lastName = lastname;
		eMail = email;
		birthDate = u_birthdate;
		regNumber = u_regnum;
		password = m_password;
		gender = m_gender;
		id = t_id;
		type = t_type;
		reputation = u_reputation;
		
	}
	
	public int getId() {
		return id;
	}


	public int getRegNumber() {
		return regNumber;
	}

	public void setRegNumber(int regNumber) {
		this.regNumber = regNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public String getBirthDate() {
		return birthDate;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public boolean equals(User user) {
		return id==user.getId();
	}

	public String getType() {
		return type;
	}

	public float getReputation() {
		return reputation;
	}

	public void setReputation(float reputation) {
		this.reputation = reputation;
	}
	
	
}
