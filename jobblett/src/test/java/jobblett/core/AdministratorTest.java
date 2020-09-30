package jobblett.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

public class AdministratorTest {

    @Test
    public void testValidUsername() {
        assertTrue(Administrator.validUsername("njåøl3001"));
        assertTrue(Administrator.validUsername("N2"));
        assertTrue(Administrator.validUsername("Æ+-$3"));
        assertFalse(Administrator.validUsername(" n2"));
        assertFalse(Administrator.validUsername("n"));
    }

    @Test
    public void testValidName() {
        assertTrue(Administrator.validName("Nils"));
        assertTrue(Administrator.validName("nk"));
        assertTrue(Administrator.validName("Njåøæl"));
        assertFalse(Administrator.validName("njal3001"));
        assertFalse(Administrator.validName(" nils"));
        assertFalse(Administrator.validName("n"));
        assertFalse(Administrator.validName("+-33"));
    }

    @Test
    public void testValidPassword() {
        assertTrue(Administrator.validPassword("NiæØls1254"));
        assertTrue(Administrator.validPassword("Nk123456"));
        assertTrue(Administrator.validPassword("Nils+-//1254"));
        assertFalse(Administrator.validPassword("njal3001"));
        assertFalse(Administrator.validPassword("nkdkdD22d k"));
        assertFalse(Administrator.validPassword("nkdkdD2"));
    }

    @Test
    public void testConstructor() {
        try {
            Administrator u1 = new Administrator("njal3001", "Nils1254", "Nils", "Enge");
            assertEquals(u1.getUserName(), "njal3001");         
            assertEquals(u1.getGivenName(), "Nils");
            assertEquals(u1.getFamilyName(), "Enge");    
        } catch (Exception e) {
            fail("Exception should not be thrown for this input");
        }
        try {
            Administrator u2 = new Administrator("", "Nils1254", "Nils", "Enge");
            fail("Exception should be thrown because of invalid username");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }

        try {
            Administrator u2 = new Administrator("njal3001", "", "Nils", "Enge");
            fail("Exception should be thrown because of invalid password");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }

        try {
            Administrator u2 = new Administrator("njal3001", "Nils1254", "", "Enge");
            fail("Exception should be thrown because of invalid given name");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }

        try {
            Administrator u2 = new Administrator("njal3001", "Nils1254", "Nils", "");
            fail("Exception should be thrown because of invalid family name");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
    }

    @Test
    public void testSetName() {
        Administrator u1 = new Administrator("njal3001", "Nils1254", "Nils", "Enge");
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
        Administrator u1 = new Administrator("njal3001", "Nils1254", "Nils", "Enge");
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
        Administrator u1 = new Administrator("njal3001", "Nils1254", "Nils", "Enge");
        assertTrue(u1.matchesPassword("Nils1254"));
        assertFalse(u1.matchesPassword("nils1254"));
    }

    @Test
    public void testToString() {
        Administrator u1 = new Administrator("njal3001", "Nils1254", "Nils", "Enge");
        assertEquals("Nils Enge (@njal3001)", u1.toString());
    }

}