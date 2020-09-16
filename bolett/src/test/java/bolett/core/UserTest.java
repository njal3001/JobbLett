package bolett.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;

import bolett.core.User;
import org.junit.jupiter.api.Test;

public class UserTest {

    @Test
    public void testValidUsername() {
        assertTrue(User.validUsername("njåøl3001"));
        assertTrue(User.validUsername("N2"));
        assertTrue(User.validUsername("Æ+-$3"));
        assertFalse(User.validUsername(" n2"));
        assertFalse(User.validUsername("n"));
    }

    @Test
    public void testValidName() {
        assertTrue(User.validName("Nils"));
        assertTrue(User.validName("nk"));
        assertTrue(User.validName("Njåøæl"));
        assertFalse(User.validName("njal3001"));
        assertFalse(User.validName(" nils"));
        assertFalse(User.validName("n"));
        assertFalse(User.validName("+-33"));
    }

    @Test
    public void testValidPassword() {
        assertTrue(User.validPassword("NiæØls1254"));
        assertTrue(User.validPassword("Nk123456"));
        assertTrue(User.validPassword("Nils+-//1254"));
        assertFalse(User.validPassword("njal3001"));
        assertFalse(User.validPassword("nkdkdD22d k"));
        assertFalse(User.validPassword("nkdkdD2"));
    }

    @Test
    public void testConstructor() {
        try {
            User u1 = new User("njal3001", "Nils1254", "Nils", "Enge");
            assertEquals(u1.getUserName(), "njal3001");         
            assertEquals(u1.getGivenName(), "Nils");
            assertEquals(u1.getFamilyName(), "Enge");    
        } catch (Exception e) {
            fail("Exception should not be thrown for this input");
        }
        try {
            User u2 = new User("", "Nils1254", "Nils", "Enge");
            fail("Exception should be thrown because of invalid username");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }

        try {
            User u2 = new User("njal3001", "", "Nils", "Enge");
            fail("Exception should be thrown because of invalid password");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }

        try {
            User u2 = new User("njal3001", "Nils1254", "", "Enge");
            fail("Exception should be thrown because of invalid given name");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }

        try {
            User u2 = new User("njal3001", "Nils1254", "Nils", "");
            fail("Exception should be thrown because of invalid family name");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
    }

    @Test
    public void testSetName() {
        User u1 = new User("njal3001", "Nils1254", "Nils", "Enge");
        try{
            u1.setName("Kåre", "Heins");
            assertEquals(u1.getGivenName(), "Kåre");
            assertEquals(u1.getFamilyName(), "Heins");
        } catch(Exception e){
            fail("Exception should not be thrown for this input");
        }
        try{
            u1.setName("", "");
            fail("Exception should be thrown");
        } catch(Exception e){
            assertTrue(e instanceof IllegalArgumentException);
            assertEquals(u1.getGivenName(), "Kåre");
            assertEquals(u1.getFamilyName(), "Heins");
        }
    }

    @Test
    public void testSetPassword() {
        User u1 = new User("njal3001", "Nils1254", "Nils", "Enge");
        try{
            u1.setPassword("Nils1254");
        } catch(Exception e){
            fail("Exception should not be thrown for this input");
        }
        try{
            u1.setPassword("");
            fail("Exception should be thrown");
        } catch(Exception e){
            assertTrue(e instanceof IllegalArgumentException);
        }
    }

    @Test
    public void testMatchesPassword() {
        User u1 = new User("njal3001", "Nils1254", "Nils", "Enge");
        assertTrue(u1.matchesPassword("Nils1254"));
        assertFalse(u1.matchesPassword("nils1254"));
    }

    @Test
    public void testToString() {
        User u1 = new User("njal3001", "Nils1254", "Nils", "Enge");
        assertEquals("Nils Enge (@njal3001)", u1.toString());
    }

}