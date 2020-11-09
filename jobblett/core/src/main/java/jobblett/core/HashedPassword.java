package jobblett.core;

import com.google.common.hash.Hashing;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class HashedPassword {
  private final String password;

  private HashedPassword(String password) {
    this.password = password;
  }

  /**
   * Validates the password before initializing it. Can be used to change password.
   *
   * @param password password to be hashed.
   * @throws IllegalArgumentException if password is not valid.
   */
  public static HashedPassword hashPassword(String password) throws IllegalArgumentException {
    if (validPassword(password)) {
      return new HashedPassword(hash(password));
    } else {
      throw new IllegalArgumentException("Not a valid password");
    }
  }

  public static HashedPassword alreadyHashed(String password) {
    return new HashedPassword(password);
  }

  /**
   * Password criteria: - At least digit. - At least 1 lowercase letter. - At least 1 uppercase
   * letter. - No whitespace. - At least 8 characters.
   *
   * @param password the password.
   * @return true if the criteria are fulfilled, else false
   */
  private static boolean validPassword(String password) {
    String pattern = "^(?=.*[0-9])(?=.*[a-zæøå])(?=.*[A-ZÆØÅ])(?=\\S+$).{8,}$";
    return password.matches(pattern);
  }

  private static String hash(String password) {
    return Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    HashedPassword that = (HashedPassword) o;
    return password.equals(that.password);
  }

  @Override
  public int hashCode() {
    return Objects.hash(password);
  }

  @Override
  public String toString() {
    return password;
  }
}
