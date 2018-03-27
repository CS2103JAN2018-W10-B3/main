package seedu.address.model.food;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.food.allergy.Allergy;
import seedu.address.model.food.allergy.UniqueAllergyList;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Represents a Food in HackEat.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Food {

    private final Name name;
    private final Phone phone;
    private final Email email;
    private final Address address;
    private final Price price;
    private final Rating rating;

    private final UniqueAllergyList allergies;
    private final UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Food(Name name, Phone phone, Email email, Address address, Price price, Rating rating,
                Set<Tag> tags, Set<Allergy> allergies) {
        requireAllNonNull(name, phone, email, address, price, rating, tags, allergies);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.price = price;
        this.rating = rating;
        // protect internal tags from changes in the arg list
        this.tags = new UniqueTagList(tags);
        this.allergies = new UniqueAllergyList(allergies);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public Price getPrice() {
        return price;
    }

    public Rating getRating() {
        return rating;
    }

    /**
     * Returns an immutable allergy set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Allergy> getAllergies() {
        return Collections.unmodifiableSet(allergies.toSet());
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags.toSet());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Food)) {
            return false;
        }

        Food otherFood = (Food) other;
        return otherFood.getName().equals(this.getName())
                && otherFood.getPhone().equals(this.getPhone())
                && otherFood.getEmail().equals(this.getEmail())
                && otherFood.getAddress().equals(this.getAddress())
                && otherFood.getPrice().equals(this.getPrice())
                && otherFood.getRating().equals(this.getRating());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, price, rating, tags, allergies);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Address: ")
                .append(getAddress())
                .append(" Price: ")
                .append(getPrice())
                .append(" Rating: ")
                .append(getRating())
                .append(" Allergies: ");
        getAllergies().forEach(builder::append);
        builder.append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
