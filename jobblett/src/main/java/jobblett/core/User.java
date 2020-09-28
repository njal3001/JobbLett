package jobblett.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * Data object representing a User in real life.
 */
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property = "userName")
public class User {
    // username is final after being initialized
    private final String userName;

    // password and name can be changed after initialization
    private String password;
    private String givenName;
    private String familyName;
    
    /**
     * Checks if the parameters are valid before creating instance a User.
     * Allows JSON to create empty Users.
     * 
     * @param userName
     * @param password
     * @param givenName
     * @param familyName
     */
    @JsonCreator
    public User(
            @JsonProperty("userName") String userName,
            @JsonProperty("password") String password,
            @JsonProperty("givenName") String givenName,
            @JsonProperty("familyName") String familyName
    ){
        if (userName != null) if(!validUsername(userName)) throw new IllegalArgumentException("Not a valid userName");
        this.userName = userName;
        setPassword(password);
        setName(givenName, familyName);
    }

    /**
     * Only for testing purpose.
     * Should be removed or made private before finish.
     *  
     * @return
     * @deprecated Prevent using this method 
     */
    @Deprecated
    public String getPassword() {
        return password;
    }

   /**
     * Name criteria:
     * 	- Contains only letters
     * 	- At least 2 characters
     * 
     * @param name
     * @return true if the criteria are fulfilled, else false
     */
    public static boolean validName(String name){
        String pattern = "[a-zA-ZæøåÆØÅ]{2,}";
        return name.matches(pattern);
    }

    // Username criteria:
    // No whitespace
    // At least 2 characters
    /**
     * Username criteria:
     * 	- No whitespace
     *  - At least 2 characters
     * @return true if the criteria are fulfilled, else false
     */
    public static boolean validUsername(String userName){
        String pattern = "[^\\s]{2,}";
        return userName.matches(pattern);
    }


    /**
     * Password criteria:
     * 	- At least digit
     * 	- At least 1 lowercase letter
     *  - At least 1 uppercase letter
     *  - No whitespace
     *  - At least 8 characters
     * @param password
     * @return true if the criteria are fulfilled, else false
     */
    public static boolean validPassword(String password){
        String pattern = "^(?=.*[0-9])(?=.*[a-zæøå])(?=.*[A-ZÆØÅ])(?=\\S+$).{8,}$";
        return password.matches(pattern);
    }
    
    /**
     * Validates the password before initializing it.
     * Can be used to change password
     * @param password
     * @throws IllegalArgumentException
     */
    public void setPassword(String password) throws IllegalArgumentException{
        if(validPassword(password))
            this.password = password;
        else
            throw new IllegalArgumentException("Not a valid password"); 
    }
    
    /**
     * Validates the name before initializing it.
     * @param givenName
     * @param familyName
     * @throws IllegalArgumentException
     */
    public void setName(String givenName, String familyName) throws IllegalArgumentException{
        if(validName(givenName) && validName(familyName)){
            this.givenName =  formatName(givenName);
            this.familyName = formatName(familyName);
        }
        else
            throw new IllegalArgumentException("Not a valid name");
    }
    
    /**
     * Formats the first letter to uppercase, and the rest to lowercase.
     * @param name
     * @return
     */
    private String formatName(String name){
        return String.valueOf(name.charAt(0)).toUpperCase() + name.substring(1).toLowerCase();
    }

    /**
     * Gets the username.
     * The unique ID for each user.
     * @return
     */
    public String getUserName(){
        return this.userName;
    }
    
    /**
     * Gets the given name of an user.
     * This is not unique. Use username to get unique identifier for a user.
     * @return
     */
    public String getGivenName(){
        return this.givenName;
    }
    

    /**
     * Gets the family name of an user.
     * This is not unique. Use username to get unique identifier for a user.
     * @return
     */
    public String getFamilyName(){
        return this.familyName;
    }

    /**
     * Checks whether a password matches the user's password.
     * @param password
     * @return true if the password matches, else false
     */
    public boolean matchesPassword(String password){
        return this.password.matches(password);
    }
    
    @Override
	public String toString(){
        return givenName + " " + familyName + " (@" + userName + ")";
    }

}
