package pl.edu.pw.elka.proz.projekt1;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Implementation of Environment class. The class represents environment in
 * application and manages its users.
 * 
 * The non-parameterized constructor has been omitted, as the environment can
 * not exist without a name, which is its unique identifier.
 * 
 * @author Konrad Bereda
 * @version 1.0
 */
public class Environment {
	private final String name;
	private final HashMap<String, String> users;
	private final MessageDigest digest;

	/**
	 * Default constructor for this class
	 * 
	 * @param name
	 *            Name of the environment
	 */
	Environment(String name) {
		this.name = name;
		users = new HashMap<>();
		try {
			digest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Overrided implementation of toString function
	 * 
	 * @return Name of the environment
	 */
	@Override
	public String toString() {
		return name;
	}

	/**
	 * Adds user to environment database if no other user with the same username
	 * exists.
	 * 
	 * @param username
	 *            New user username
	 * @param password
	 *            New user password
	 */
	public void addUser(String username, String password) {
		if (users.containsKey(username))
			return;
		users.put(username, hashString(password));
	}

	/**
	 * Returns list of existing users
	 * 
	 * @return ObservableList of environment's users usernames
	 */
	public ObservableList<String> getUsers() {
		return FXCollections.observableArrayList(users.keySet());
	}

	/**
	 * Checks if user exists in this environment
	 * 
	 * @param username
	 *            Username to check
	 * @return <em>True</em> if user exists in environment
	 */
	public Boolean isExistingUser(String username) {
		if (users.containsKey(username))
			return true;
		return false;
	}

	/**
	 * Checks if given password matches user's password hash in database
	 * 
	 * @param username
	 *            Username to check
	 * @param password
	 *            Password to check
	 * @return <em>True</em> if given password matches with database
	 */
	public Boolean isPasswordCorrect(String username, String password) {
		if (isExistingUser(username) && users.get(username).equals(hashString(password)))
			return true;
		return false;
	}

	/**
	 * Returns SHA-256 hash of given text
	 * 
	 * @param text
	 *            String that needs to be hashed
	 * @return SHA-256 hash of given text
	 */
	private String hashString(String text) {
		byte[] hash = digest.digest(text.getBytes(StandardCharsets.UTF_8));
		return Base64.getEncoder().encodeToString(hash);
	}
}
