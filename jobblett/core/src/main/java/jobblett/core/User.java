package jobblett.core;

/**
 * Data object representing a User in real life.
 */
public class User extends JobblettPropertyChangeSupporter {
  // username is final after being initialized
  private final String username;

  // password and name can be changed after initialization
  private HashedPassword password;
  private String givenName;
  private String familyName;

  /**
   * Checks if the parameters are valid before creating User.
   *
   * @param username username. 
   * @param password password.
   * @param givenName given name.
   * @param familyName family name.
   */
  public User(String username, HashedPassword password, String givenName, String familyName) {
    if (!validUsername(username)) {
      throw new IllegalArgumentException("Not a valid username");
    }
    this.username = username;
    setPassword(password);
    setName(givenName, familyName);
  }

  /**
   * Name criteria: - Contains only letters. - At least 2 characters.
   *
   * @param name name to be validated.
   * @return true if the criteria are fulfilled, else false.
   */
  public static boolean validName(String name) {
    String pattern = "[a-zA-ZæøåÆØÅ]{2,}";
    return name.matches(pattern);
  }

  // Username criteria:
  // No whitespace
  // At least 2 characters

  /**
   * Username criteria: - No whitespace. - At least 2 characters.
   *
   * @param username username to be validated.
   * @return true if the criteria are fulfilled, else false.
   */
  public static boolean validUsername(String username) {
    String pattern = "[^\\s]{2,}";
    return username.matches(pattern);
  }

  /**
   * Sets the password.
   *
   * @param password password to be set.
   * @throws IllegalArgumentException if password is null.
   */
  public void setPassword(HashedPassword password) {
    if (password == null) {
      throw new IllegalArgumentException("Password can´t be null.");
    }
    this.password = password;
  }

  /**
   * Validates the name before initializing it.
   *
   * @param givenName  the given name to be set.
   * @param familyName the family name to be set.
   * @throws IllegalArgumentException if name is not valid.
   */
  public void setName(String givenName, String familyName) throws IllegalArgumentException {
    if (validName(givenName) && validName(familyName)) {
      firePropertyChange("givenName", this.givenName, givenName);
      firePropertyChange("familyName", this.familyName, familyName);
      this.givenName = formatName(givenName);
      this.familyName = formatName(familyName);
    } else {
      throw new IllegalArgumentException("Not a valid name");
    }
  }

  /**
   * Formats the first letter to uppercase, and the rest to lowercase.
   *
   * @param name name to be formatted.
   * @return the formatted name.
   */
  private String formatName(String name) {
    return String.valueOf(name.charAt(0)).toUpperCase() + name.substring(1).toLowerCase();
  }

  /**
   * Gets the username. The unique ID for each user.
   *
   * @return the username.
   */
  public String getUsername() {
    return this.username;
  }

  /**
   * Gets the given name of an user. This is not unique. Use username to get unique identifier for a
   * user.
   *
   * @return the given name.
   */
  public String getGivenName() {
    return this.givenName;
  }

  /**
   * Gets the family name of an user. This is not unique. 
   * Use username to get unique identifier for a
   * user.
   *
   * @return the family name.
   */
  public String getFamilyName() {
    return this.familyName;
  }

  /**
   * Gets the hashed password.
   *
   * @return hashedPassword
   */
  public HashedPassword getPassword() {
    return password;
  }

  @Override
  public String toString() {
    return givenName + " " + familyName + " (@" + username + ")";
  }
}
