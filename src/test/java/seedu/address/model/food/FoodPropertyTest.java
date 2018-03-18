package seedu.address.model.food;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class FoodPropertyTest {

    private static final String FOOD_STUB_MESSAGE_CONSTRAINT = "Message constraint example.";
    private static final String FOOD_STUB_VALID_REGEX = "[^\\s].*";

    @Test
    public void constructor_variables_assertEqual() {
        FoodPropertyStub foodPropertyStub = new FoodPropertyStub("Test");

        // Test value
        assertEquals(foodPropertyStub.value, "Test");

        // Test MESSAGE_STUB_CONSTRAINTS override
        assertEquals(foodPropertyStub.MESSAGE_CONSTRAINT, FOOD_STUB_MESSAGE_CONSTRAINT);

        // Test PROPERTY_VALID_REGEX override
        assertEquals(foodPropertyStub.PROPERTY_VALID_REGEX, FOOD_STUB_VALID_REGEX);
    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new FoodPropertyStub(null));
    }

    @Test
    public void constructor_invalidFoodPropertyValue_throwsIllegalArgumentException() {
        String invalidFoodStubValue = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new FoodPropertyStub(invalidFoodStubValue));
    }

    @Test
    public void isValid() {
        // TODO: Add isValid for all child classes

        // invalid address
        assertFalse(FoodProperty.isValid("", Address.ADDRESS_VALIDATION_REGEX)); // empty string

        // valid addresses
        assertTrue(FoodProperty.isValid("Blk 456, Den Road, #01-355", Address.ADDRESS_VALIDATION_REGEX));
    }

    private class FoodPropertyStub extends FoodProperty {

        public static final String MESSAGE_CONSTRAINT = FOOD_STUB_MESSAGE_CONSTRAINT;
        public static final String PROPERTY_VALID_REGEX = FOOD_STUB_VALID_REGEX;

        public FoodPropertyStub(String property) {
            super(property);
        }

    }
}
