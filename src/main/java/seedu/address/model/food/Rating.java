package seedu.address.model.food;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Food's rating in HackEat.
 * Guarantees: immutable; is valid as declared in {@link #isValidRating(String)}
 */
public class Rating {

    public static final String MESSAGE_RATING_CONSTRAINTS =
            "Please enter a number between 1 to 5.";

    /*
     * User must enter only a single digit.
     */
    public static final String RATING_VALIDATION_REGEX = "\\b\\d\\b";

    public final String value;

    /**
     * Constructs a {@code Rating}.
     *
     * @param rating A valid rating.
     */
    public Rating(String rating) {
        requireNonNull(rating);
        checkArgument(isValidRating(rating), MESSAGE_RATING_CONSTRAINTS);
        this.value = rating;
    }

    /**
     * Returns true if a given string is a valid food email.
     */
    public static boolean isValidRating(String test) {
        if (test.matches(RATING_VALIDATION_REGEX)) {
            int rating = Integer.parseInt(test);
            if (rating >= 0 && rating <= 5) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Rating // instanceof handles nulls
                && this.value.equals(((Rating) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}