package seedu.address.model.food;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Food's property in HackEat
 */
public abstract class FoodProperty {

    public static final String MESSAGE_CONSTRAINT =
            "To be implemented by child classes";
    public static final String PROPERTY_VALID_REGEX = "[^\\s].*";
    public final String value;

    /**
     * Constructs a {@code FoodProperty}.
     *
     * @param foodProperty A valid FoodProperty.
     */
    public FoodProperty(String foodProperty) {
        requireNonNull(foodProperty);
        checkArgument(isValid(foodProperty, PROPERTY_VALID_REGEX), MESSAGE_CONSTRAINT);
        this.value = foodProperty;
    }

    /**
     * Returns true if a given string is a valid food property.
     */
    public static boolean isValid (String test, String regex) {
        return test.matches(regex);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FoodProperty // instanceof handles nulls
                && this.value.equals(((FoodProperty) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }


}
