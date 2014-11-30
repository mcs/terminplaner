package plaani.service.validation;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class PasswordValidatorTest {

    PasswordValidator validator = new PasswordValidator();

    @Test
    public void testEmptyPassword() {
        assertThat(validator.isValid(null, null), is(true));
    }

    @Test
    public void testLowercaseOnly() {
        assertThat(validator.isValid("abcdefgh", null), is(false));
    }

    @Test
    public void testUppercaseOnly() {
        assertThat(validator.isValid("ABCDEFGH", null), is(false));
    }

    @Test
    public void testDigitsOnly() {
        assertThat(validator.isValid("12345678", null), is(false));
    }

    @Test
    public void testDigitsAndLowercaseOnly() {
        assertThat(validator.isValid("a2345678", null), is(false));
    }

    @Test
    public void testDigitsAndUppercaseOnly() {
        assertThat(validator.isValid("A2345678", null), is(false));
    }

    @Test
    public void testUppercaseAndLowercaseOnly() {
        assertThat(validator.isValid("ABCDefgh", null), is(false));
    }

    @Test
    public void testValidPassword() {
        assertThat(validator.isValid("Ab1cdefgh", null), is(true));
    }
}