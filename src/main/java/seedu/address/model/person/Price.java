package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Food's price in the address book
 */
public class Price {


    public static final String MESSAGE_PRICE_CONSTRAINTS =
            "Price can only contain numbers and it should be given to 2 decimal places";
    public static final String PRICE_VALIDATION_REGEX = "[0-9]+([,.][0-9]{1,2})?";
    public final String value;

    /**
     * Constructs a {@code Phone}.
     *
     * @param price A valid price.
     */
    public Price(String price) {
        requireNonNull(price);
        checkArgument(isValidPrice(price), MESSAGE_PRICE_CONSTRAINTS);
        this.value = price;
    }

    /**
     * Returns true if a given string is a valid person phone number.
     */
    public static boolean isValidPrice(String test) {
        return test.matches(PRICE_VALIDATION_REGEX);
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
