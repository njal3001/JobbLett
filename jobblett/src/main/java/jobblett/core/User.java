package jobblett.core;

import com.fasterxml.jackson.annotation.*;

@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property = "userName")
public class User{
    // username is final after being initialized
    private String userName;

    // password and name can be changed after initialization
    private String password;
    private String givenName;
    private String familyName;

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

    // Only for testing purpose. Should be removed or made private before finish.
    public String getPassword() {
        return password;
    }

    // Name criteria:
    // Contains only letters
    // At least 2 characters
    public static boolean validName(String name){
        String pattern = "[a-zA-ZæøåÆØÅ]{2,}";
        return name.matches(pattern);
    }

    // Username criteria:
    // No whitespace
    // At least 2 characters
    public static boolean validUsername(String username){
        String pattern = "[^\\s]{2,}";
        return username.matches(pattern);
    }

    // Password criteria:
    // At least 1 digit
    // At least 1 lower case letter
    // At least 1 upper case letter
    // No whitespace
    // At least 8 characters
    public static boolean validPassword(String password){
        String pattern = "^(?=.*[0-9])(?=.*[a-zæøå])(?=.*[A-ZÆØÅ])(?=\\S+$).{8,}$";
        return password.matches(pattern);
    }

    public void setPassword(String password){
        if(validPassword(password))
            this.password = password;
        else
            throw new IllegalArgumentException("Not a valid password"); 
    }

    public void setName(String givenName, String familyName){
        if(validName(givenName) && validName(familyName)){
            this.givenName =  formatName(givenName);
            this.familyName = formatName(familyName);
        }
        else
            throw new IllegalArgumentException("Not a valid name");
    }

    private String formatName(String name){
        return String.valueOf(name.charAt(0)).toUpperCase() + name.substring(1).toLowerCase();
    }

    public String getUserName(){
        return this.userName;
    }

    public String getGivenName(){
        return this.givenName;
    }

    public String getFamilyName(){
        return this.familyName;
    }

    public boolean matchesPassword(String password){
        return this.password.matches(password);
    }
    
    @Override
	public String toString(){
        return givenName + " " + familyName + " (@" + userName + ")";
    }

}
