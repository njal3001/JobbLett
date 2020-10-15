package jobblett.core;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

/**
 * Data object representing a User in real life.
 */
public class User {
  // username is final after being initialized
  private final String username;

  // password and name can be changed after initialization
  private String password;
  private String givenName;
  private String familyName;

  /**
   * Checks if the parameters are valid before creating instance a User. Allows
   * JSON to create empty Users.
   * 
   * @param username
   * @param password
   * @param givenName
   * @param familyName
   */
  public User(String username, String password, String givenName, String familyName) {
    this(username, password, givenName, familyName, false);
  }

  //Sikkert en bedre måte å lage feilmelding...

  // Hadde vært fint med en annen løsning enn å ha passwordHashed boolean, kanskje
  // at setPassword
  // kan finne ut om det er et hashed password på egen hånd hvis det går ann
  public User(String username, String password, String givenName, String familyName, boolean passwordHashed) {
    String errorMessage = "";
    if (!validUsername(username))
      errorMessage += "Not a valid username\n";
    this.username = username;
    if (passwordHashed) {
      this.password = password;
    } else {
      try {
        setPassword(password);
      } catch (IllegalArgumentException e) {
        errorMessage += e.getMessage() + "\n";
      }
    }
    try {
      setName(givenName, familyName);
    } catch (IllegalArgumentException e) {
      errorMessage += e.getMessage();
    }
    if (errorMessage.length() != 0)
      throw new IllegalArgumentException(errorMessage);
  }

  /**
   * Name criteria: - Contains only letters - At least 2 characters
   * 
   * @param name
   * @return true if the criteria are fulfilled, else false
   */
  public static boolean validName(String name) {
    String pattern = "[a-zA-ZæøåÆØÅ]{2,}";
    return name.matches(pattern);
  }

  // Username criteria:
  // No whitespace
  // At least 2 characters
  /**
   * Username criteria: - No whitespace - At least 2 characters
   * 
   * @param username
   * @return true if the criteria are fulfilled, else false
   */
  public static boolean validUsername(String username) {
    String pattern = "[^\\s]{2,}";
    return username.matches(pattern);
  }

  /**
   * Password criteria: - At least digit - At least 1 lowercase letter - At least
   * 1 uppercase letter - No whitespace - At least 8 characters
   * 
   * @param password
   * @return true if the criteria are fulfilled, else false
   */
  public static boolean validPassword(String password) {
    String pattern = "^(?=.*[0-9])(?=.*[a-zæøå])(?=.*[A-ZÆØÅ])(?=\\S+$).{8,}$";
    return password.matches(pattern);
  }

  /**
   * Validates the password before initializing it. Can be used to change password
   * 
   * @param password
   * @throws IllegalArgumentException
   */
  public void setPassword(String password) throws IllegalArgumentException {
    if (validPassword(password)) {
      String hashedPassword = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
      this.password = hashedPassword;
    } else
      throw new IllegalArgumentException("Not a valid password");
  }

  /**
   * Validates the name before initializing it.
   * 
   * @param givenName
   * @param familyName
   * @throws IllegalArgumentException
   */
  public void setName(String givenName, String familyName) throws IllegalArgumentException {
    if (validName(givenName) && validName(familyName)) {
      this.givenName = formatName(givenName);
      this.familyName = formatName(familyName);
    } else
      throw new IllegalArgumentException("Not a valid name");
  }

  /**
   * Formats the first letter to uppercase, and the rest to lowercase.
   * 
   * @param name
   * @return
   */
  private String formatName(String name) {
    return String.valueOf(name.charAt(0)).toUpperCase() + name.substring(1).toLowerCase();
  }

  /**
   * Gets the username. The unique ID for each user.
   * 
   * @return
   */
  public String getUserName() {
    return this.username;
  }

  /**
   * Gets the given name of an user. This is not unique. Use username to get
   * unique identifier for a user.
   * 
   * @return
   */
  public String getGivenName() {
    return this.givenName;
  }

  /**
   * Gets the family name of an user. This is not unique. Use username to get
   * unique identifier for a user.
   * 
   * @return
   */
  public String getFamilyName() {
    return this.familyName;
  }

  /**
   * Checks whether a password matches the user's password.
   * 
   * @param password
   * @return true if the password matches, else false
   */
  public boolean matchesPassword(String password) {
    String hashedPassword = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
    return this.password.matches(hashedPassword);
  }

  /**
   * Only used by JSON Serializer
   * 
   * @return hashedPassword
   */
  public String getHashedPassword() {
    return password;
  }

  @Override
  public String toString() {
    return givenName + " " + familyName + " (@" + username + ")";
  }

  @Override
  public boolean equals(Object o) {
    if (o == null)
      return false;
    if (o instanceof User) {
      User user = (User) o;
      if (!this.username.equals(user.username))
        return false;
      if (!this.password.equals(user.password))
        return false;
      if (!this.givenName.equals(user.givenName))
        return false;
      if (!this.familyName.equals(user.familyName))
        return false;
      return true;
    } else
      return super.equals(o);
  }

  @Override
  public int hashCode() {
    assert false : "hashCode not designed";
    return 42; // any arbitrary constant will do
  }
}
