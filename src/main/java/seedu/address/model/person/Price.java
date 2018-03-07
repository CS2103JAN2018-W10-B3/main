package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Food's price in the address book
 */
public class Price {


    public static final String MESSAGE_PRICE_CONSTRAINTS =
            "Price can only contain numbers and it should be given to 2 decimal places." +
                    "Price should not begin with zero unless it is less than a dollar.";
    public static final String PRICE_VALIDATION_REGEX = "\\d+([.]\\d{2})";
    public final String value;

    /**
     * Constructs a {@code Price}.
     *
     * @param price A valid price.
     */
    public Price(String price) {
        requireNonNull(price);
        checkArgument(isValidPrice(price), MESSAGE_PRICE_CONSTRAINTS);
        this.value = price;
    }

    /**
     * Returns true if a given string is a valid price.
     */
    public static boolean isValidPrice(String test) {
        return test.matches(PRICE_VALIDATION_REGEX) && !isZeroAtBeginning(test);
    }

    /**
     * Returns true if a given price is more than or equal to a dollar and does not start with digit zero.
     */
    private static boolean isZeroAtBeginning(String test) {
        return test.charAt(0) == '0' && test.charAt(1) != '.';
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Price // instanceof handles nulls
                && this.value.equals(((Price) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
