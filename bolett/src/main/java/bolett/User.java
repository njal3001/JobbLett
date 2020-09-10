package bolett;

public class User{

    // username is final after being initalized
    public final String username;

    // password and name can be changed after initalization
    private String password;
    private String givenName;
    private String familyName;

    public User(String username, String password, String givenName, String familyName){
        if(!validUsername(username))
            throw new IllegalArgumentException("Not a valid username");
        this.username = username;
        setPassword(password);
        setName(givenName, familyName);
    }

    // Name criteria:
    // Contains only letters
    // At least 2 characters
    private static boolean validName(String name){
        String pattern = "[a-åA-Å]{2,}";
        return name.matches(pattern);
    }

    // Username criteria:
    // No whitespace
    // At least 2 characters
    private static boolean validUsername(String username){
        String pattern = "[^\\s]{2,}";
        return username.matches(pattern);
    }

    // Password criteria:
    // At least 1 digit
    // At least 1 lower case digit
    // At least 1 upper case digit
    // No whitespace
    // At least 8 characters
    private static boolean validPassword(String password){
        String pattern = "(?=.*[0-9])(?=.*[a-å])(?=.*[A-Å])(?=\\S+$).{8,}";
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
            this.givenName = givenName;
            this.familyName = familyName;
        }
        else
            throw new IllegalArgumentException("Not a valid name");
    }

    public boolean matchesPassword(String password){
        return this.password.matches(password);
    }
    
    public String toString(){
        return givenName + " " + familyName + "(@" + username + ")";
    }

}
