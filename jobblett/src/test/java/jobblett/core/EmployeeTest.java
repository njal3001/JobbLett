package jobblett.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeTest {

    @Test
    public void testValidUsername() {
        assertTrue(Employee.validUsername("njåøl3001"));
        assertTrue(Employee.validUsername("N2"));
        assertTrue(Employee.validUsername("Æ+-$3"));
        assertFalse(Employee.validUsername(" n2"));
        assertFalse(Employee.validUsername("n"));
    }

    @Test
    public void testValidName() {
        assertTrue(Employee.validName("Nils"));
        assertTrue(Employee.validName("nk"));
        assertTrue(Employee.validName("Njåøæl"));
        assertFalse(Employee.validName("njal3001"));
        assertFalse(Employee.validName(" nils"));
        assertFalse(Employee.validName("n"));
        assertFalse(Employee.validName("+-33"));
    }

    @Test
    public void testValidPassword() {
        assertTrue(Employee.validPassword("NiæØls1254"));
        assertTrue(Employee.validPassword("Nk123456"));
        assertTrue(Employee.validPassword("Nils+-//1254"));
        assertFalse(Employee.validPassword("njal3001"));
        assertFalse(Employee.validPassword("nkdkdD22d k"));
        assertFalse(Employee.validPassword("nkdkdD2"));
    }

    @Test
    public void testConstructor() {
        try {
            Employee u1 = new Employee("njal3001", "Nils1254", "Nils", "Enge");
            assertEquals(u1.getUserName(), "njal3001");         
            assertEquals(u1.getGivenName(), "Nils");
            assertEquals(u1.getFamilyName(), "Enge");    
        } catch (Exception e) {
            fail("Exception should not be thrown for this input");
        }
        try {
            Employee u2 = new Employee("", "Nils1254", "Nils", "Enge");
            fail("Exception should be thrown because of invalid username");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }

        try {
            Employee u2 = new Employee("njal3001", "", "Nils", "Enge");
            fail("Exception should be thrown because of invalid password");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }

        try {
            Employee u2 = new Employee("njal3001", "Nils1254", "", "Enge");
            fail("Exception should be thrown because of invalid given name");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }

        try {
            Employee u2 = new Employee("njal3001", "Nils1254", "Nils", "");
            fail("Exception should be thrown because of invalid family name");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
    }

    @Test
    public void testSetName() {
        Employee u1 = new Employee("njal3001", "Nils1254", "Nils", "Enge");
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
        Employee u1 = new Employee("njal3001", "Nils1254", "Nils", "Enge");
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
        Employee u1 = new Employee("njal3001", "Nils1254", "Nils", "Enge");
        assertTrue(u1.matchesPassword("Nils1254"));
        assertFalse(u1.matchesPassword("nils1254"));
    }

    @Test
    public void testToString() {
        Employee u1 = new Employee("njal3001", "Nils1254", "Nils", "Enge");
        assertEquals("Nils Enge (@njal3001)", u1.toString());
    }

}