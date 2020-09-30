package jobblett.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

public class UserTest {

    @Test
    public void testValidUsername() {
        assertTrue(AbstractUser.validUsername("njåøl3001"));
        assertTrue(AbstractUser.validUsername("N2"));
        assertTrue(AbstractUser.validUsername("Æ+-$3"));
        assertFalse(AbstractUser.validUsername(" n2"));
        assertFalse(AbstractUser.validUsername("n"));
    }

    @Test
    public void testValidName() {
        assertTrue(AbstractUser.validName("Nils"));
        assertTrue(AbstractUser.validName("nk"));
        assertTrue(AbstractUser.validName("Njåøæl"));
        assertFalse(AbstractUser.validName("njal3001"));
        assertFalse(AbstractUser.validName(" nils"));
        assertFalse(AbstractUser.validName("n"));
        assertFalse(AbstractUser.validName("+-33"));
    }

    @Test
    public void testValidPassword() {
        assertTrue(AbstractUser.validPassword("NiæØls1254"));
        assertTrue(AbstractUser.validPassword("Nk123456"));
        assertTrue(AbstractUser.validPassword("Nils+-//1254"));
        assertFalse(AbstractUser.validPassword("njal3001"));
        assertFalse(AbstractUser.validPassword("nkdkdD22d k"));
        assertFalse(AbstractUser.validPassword("nkdkdD2"));
    }

    @Test
    public void testConstructor() {
        try {
            AbstractUser u1 = new AbstractUser("njal3001", "Nils1254", "Nils", "Enge");
            assertEquals(u1.getUserName(), "njal3001");         
            assertEquals(u1.getGivenName(), "Nils");
            assertEquals(u1.getFamilyName(), "Enge");    
        } catch (Exception e) {
            fail("Exception should not be thrown for this input");
        }
        try {
            AbstractUser u2 = new AbstractUser("", "Nils1254", "Nils", "Enge");
            fail("Exception should be thrown because of invalid username");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }

        try {
            AbstractUser u2 = new AbstractUser("njal3001", "", "Nils", "Enge");
            fail("Exception should be thrown because of invalid password");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }

        try {
            AbstractUser u2 = new AbstractUser("njal3001", "Nils1254", "", "Enge");
            fail("Exception should be thrown because of invalid given name");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }

        try {
            AbstractUser u2 = new AbstractUser("njal3001", "Nils1254", "Nils", "");
            fail("Exception should be thrown because of invalid family name");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
    }

    @Test
    public void testSetName() {
        AbstractUser u1 = new AbstractUser("njal3001", "Nils1254", "Nils", "Enge");
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
        AbstractUser u1 = new AbstractUser("njal3001", "Nils1254", "Nils", "Enge");
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
        AbstractUser u1 = new AbstractUser("njal3001", "Nils1254", "Nils", "Enge");
        assertTrue(u1.matchesPassword("Nils1254"));
        assertFalse(u1.matchesPassword("nils1254"));
    }

    @Test
    public void testToString() {
        AbstractUser u1 = new AbstractUser("njal3001", "Nils1254", "Nils", "Enge");
        assertEquals("Nils Enge (@njal3001)", u1.toString());
    }

}