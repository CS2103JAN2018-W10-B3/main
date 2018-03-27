package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.food.Address;
import seedu.address.model.food.Email;
import seedu.address.model.food.Food;
import seedu.address.model.food.Name;
import seedu.address.model.food.Phone;
import seedu.address.model.food.Price;
import seedu.address.model.food.Rating;
import seedu.address.model.food.allergy.Allergy;
import seedu.address.model.tag.Tag;

/**
 * JAXB-friendly version of the Food.
 */
public class XmlAdaptedFood {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Food's %s field is missing!";

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String phone;
    @XmlElement(required = true)
    private String email;
    @XmlElement(required = true)
    private String address;
    @XmlElement(required = true)
    private String price;
    @XmlElement(required = true)
    private String rating;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();
    @XmlElement
    private List<XmlAdaptedAllergy> addedAllergies = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedFood.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedFood() {}

    /**
     * Constructs an {@code XmlAdaptedFood} with the given food details.
     */
    public XmlAdaptedFood(String name, String phone, String email, String address, String price,
                          String rating, List<XmlAdaptedTag> tagged, List<XmlAdaptedAllergy> addedAllergies) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.price = price;
        this.rating = rating;
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
        if (addedAllergies != null) {
            this.addedAllergies = new ArrayList<>(addedAllergies);
        }
    }

    /**
     * Converts a given Food into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedFood
     */
    public XmlAdaptedFood(Food source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        price = source.getPrice().getValue();
        rating = source.getRating().value;
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
        for (Allergy allergy: source.getAllergies()) {
            addedAllergies.add(new XmlAdaptedAllergy(allergy));
        }
    }

    /**
     * Converts this jaxb-friendly adapted food object into the model's Food object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted food
     */
    public Food toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        final List<Allergy> foodAllergies = new ArrayList<>();

        for (XmlAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }

        for (XmlAdaptedAllergy allergy: addedAllergies) {
            foodAllergies.add(allergy.toModelType());
        }

        if (this.name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(this.name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name name = new Name(this.name);

        if (this.phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(this.phone)) {
            throw new IllegalValueException(Phone.MESSAGE_PHONE_CONSTRAINTS);
        }
        final Phone phone = new Phone(this.phone);

        if (this.email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(this.email)) {
            throw new IllegalValueException(Email.MESSAGE_EMAIL_CONSTRAINTS);
        }
        final Email email = new Email(this.email);

        if (this.address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(this.address)) {
            throw new IllegalValueException(Address.MESSAGE_ADDRESS_CONSTRAINTS);
        }
        final Address address = new Address(this.address);

        if (this.price == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Price.class.getSimpleName()));
        }
        if (!Price.isValidPrice(this.price)) {
            throw new IllegalValueException(Price.MESSAGE_PRICE_CONSTRAINTS);
        }
        final Price price = new Price(this.price);

        if (this.rating == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Rating.class.getSimpleName()));
        }
        if (!Rating.isValidRating(this.rating)) {
            throw new IllegalValueException(Rating.MESSAGE_RATING_CONSTRAINTS);
        }
        final Rating rating = new Rating(this.rating);

        final Set<Tag> tags = new HashSet<>(personTags);
        final Set<Allergy> allergies = new HashSet<>(foodAllergies);
        return new Food(name, phone, email, address, price, rating, tags, allergies);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedFood)) {
            return false;
        }

        XmlAdaptedFood otherFood = (XmlAdaptedFood) other;
        return Objects.equals(name, otherFood.name)
                && Objects.equals(phone, otherFood.phone)
                && Objects.equals(email, otherFood.email)
                && Objects.equals(address, otherFood.address)
                && Objects.equals(price, otherFood.price)
                && Objects.equals(rating, otherFood.rating)
                && tagged.equals(otherFood.tagged)
                && addedAllergies.equals(otherFood.addedAllergies);
    }
}
