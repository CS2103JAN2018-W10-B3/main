package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class PriceTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Price(null));
    }

    @Test
    public void constructor_invalidPrice_throwsIllegalArgumentException() {
        String invalidPrice = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Price(invalidPrice));
    }

    @Test
    public void isValidPrice() {
        // null price
        Assert.assertThrows(NullPointerException.class, () -> Price.isValidPrice(null));

        // invalid prices
        assertFalse(Price.isValidPrice(""));
        assertFalse(Price.isValidPrice("abc.ef"));
        assertFalse(Price.isValidPrice("1a2b.3c"));
        assertFalse(Price.isValidPrice("abc.ef"));
        assertFalse(Price.isValidPrice("1.1"));
        assertFalse(Price.isValidPrice("34.567"));
        assertFalse(Price.isValidPrice("043.23"));

        // valid prices
        assertTrue(Price.isValidPrice("123.45"));
        assertTrue(Price.isValidPrice("56.34"));
        assertTrue(Price.isValidPrice("0.00"));
        assertTrue(Price.isValidPrice("0.24"));
    }
}
