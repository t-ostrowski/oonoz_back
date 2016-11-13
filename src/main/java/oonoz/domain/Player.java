package oonoz.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;


// 
/**
 * The Class Player.
 * 
 * Description :
 * 		
 */
@Entity
@Table(name="PLAYER")
@Inheritance(strategy=InheritanceType.JOINED)
public class Player {

	
	/** The id player. */
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idPlayer;
	
	
	/** The first name. */
	@Column(unique = false, nullable = false,name="FIRSTNAME")
	private String firstName;
	
	/** The last name. */
	@Column(unique = false, nullable = false,name="LASTNAME")
	private String lastName;
	
	/** The mail. */
	@Column(unique = true, nullable = false,name="MAIL")
	private String mail;
	
	/** The username. */
	@Column(unique=true, nullable = false, name="USERNAME")
	private String username;
	
	/** The password. */
	@Column(unique=false, nullable = false, name="PASSWORD")
	private String password;
	
	/** The birth date. */
	@Column(unique = false, nullable = false,name="BIRTHDATE")
	private Date birthDate;
	
	/** The is active. */
	@Column(unique = false, nullable = false, name="IS_ACTIVE")
	private Boolean isActive;
	
	@Column(unique = false, nullable = false, name="IS_SUPPLIER")
	private Boolean isSupplier;
	
	/**
	 * Sets the id.
	 *
	 * @param idPlayer the new id
	 */
	public void setId(long idPlayer){
		this.idPlayer = idPlayer;
	}
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public long getId(){
		return this.idPlayer;
	}

	/**
	 * Sets the first name.
	 *
	 * @param firstName the new first name
	 */
	public void setFirstName(String firstName){
		this.firstName = firstName;
	}
	
	/**
	 * Gets the first name.
	 *
	 * @return the first name
	 */
	public String getFirstName(){
		return this.firstName;
	}

	/**
	 * Sets the last name.
	 *
	 * @param lastName the new last name
	 */
	public void setLastName(String lastName){
		this.lastName = lastName;
	}
	
	/**
	 * Gets the last name.
	 *
	 * @return the last name
	 */
	public String getLastName(){
		return this.lastName;
	}
	
	/**
	 * Sets the mail.
	 *
	 * @param mail the new mail
	 */
	public void setMail(String mail){
		this.mail = mail;
	}
	
	/**
	 * Gets the mail.
	 *
	 * @return the mail
	 */
	public String getMail(){
		return this.mail;
	}
	
	/**
	 * Sets the username.
	 *
	 * @param username the new username
	 */
	public void setUsername(String username){
		this.username = username;
	}
	
	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public String getUsername(){
		return this.username;
	}
	
	/**
	 * Sets the password.
	 *
	 * @param password the new password
	 */
	public void setPassword(String password){
		this.password = password;
	}
	
	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword(){
		return this.password;
	}
	
	/**
	 * Sets the birth date.
	 *
	 * @param birthDate the new birth date
	 */
	public void setBirthDate(Date birthDate){
		this.birthDate = birthDate;
	}
	
	/**
	 * Gets the birth date.
	 *
	 * @return the birth date
	 */
	public Date getBirthDate(){
		return this.birthDate;
	}
	
	/**
	 * Sets the checks if is active.
	 *
	 * @param isActive the new checks if is active
	 */
	public void setIsActive(Boolean isActive){
		this.isActive = isActive;
	}
	
	/**
	 * Checks if is active.
	 *
	 * @return the boolean
	 */
	public Boolean isActive(){
		return this.isActive;
	}

	public Boolean getIsSupplier() {
		return isSupplier;
	}

	public void setIsSupplier(Boolean isSupplier) {
		this.isSupplier = isSupplier;
	}
	
	
}
