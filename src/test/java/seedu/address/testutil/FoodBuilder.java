package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.food.Address;
import seedu.address.model.food.Email;
import seedu.address.model.food.Food;
import seedu.address.model.food.Name;
import seedu.address.model.food.Phone;
import seedu.address.model.food.Price;
import seedu.address.model.food.Rating;
import seedu.address.model.food.allergy.Allergy;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Food objects.
 */
public class FoodBuilder {

    public static final String DEFAULT_NAME = "Apple Pie";
    public static final String DEFAULT_PHONE = "85355255";

    public static final String DEFAULT_EMAIL = Email.DEFAULT_EMAIL;
    public static final String DEFAULT_ADDRESS = Address.DEFAULT_ADDRESS;
    public static final String DEFAULT_PRICE = Price.DEFAULT_PRICE;
    public static final String DEFAULT_RATING = Rating.DEFAULT_RATING;
    public static final String DEFAULT_TAGS = "fried";
    public static final String DEFAULT_ALLERGIES = "lactose";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Price price;
    private Rating rating;
    private Set<Tag> tags;
    private Set<Allergy> allergies;

    public FoodBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        price = new Price(DEFAULT_PRICE);
        rating = new Rating(DEFAULT_RATING);
        tags = SampleDataUtil.getTagSet(DEFAULT_TAGS);
        allergies = SampleDataUtil.getAllergySet(DEFAULT_ALLERGIES);
    }

    /**
     * Initializes the FoodBuilder with the data of {@code foodToCopy}.
     */
    public FoodBuilder(Food foodToCopy) {
        name = foodToCopy.getName();
        phone = foodToCopy.getPhone();
        email = foodToCopy.getEmail();
        address = foodToCopy.getAddress();
        price = foodToCopy.getPrice();
        rating = foodToCopy.getRating();
        tags = new HashSet<>(foodToCopy.getTags());
        allergies = new HashSet<>(foodToCopy.getAllergies());
    }

    /**
     * Sets the {@code Name} of the {@code Food} that we are building.
     */
    public FoodBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Food} that we are building.
     */
    public FoodBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Food} that we are building.
     */
    public FoodBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Food} that we are building.
     */
    public FoodBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Food} that we are building.
     */
    public FoodBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Price} of the {@code Food} that we are building
     */
    public FoodBuilder withPrice(String price) {
        this.price = new Price(price);
        return this;
    }

    /**
     * Sets the {@code Rating} of the {@code Food} that we are building
     */
    public FoodBuilder withRating(String rating) {
        this.rating = new Rating(rating);
        return this;
    }

    /**
     * Parses the {@code allergies} into a {@code Set<Allergy>} and set it to the {@code Food} that we are building.
     */
    public FoodBuilder withAllergies(String ... allergies) {
        this.allergies = SampleDataUtil.getAllergySet(allergies);
        return this;
    }

    public Food build() {
        return new Food(name, phone, email, address, price, rating, tags, allergies);
    }

}
