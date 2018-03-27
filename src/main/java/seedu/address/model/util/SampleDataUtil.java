package seedu.address.model.util;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.food.Address;
import seedu.address.model.food.Email;
import seedu.address.model.food.Food;
import seedu.address.model.food.Name;
import seedu.address.model.food.Phone;
import seedu.address.model.food.Price;
import seedu.address.model.food.Rating;
import seedu.address.model.food.allergy.Allergy;
import seedu.address.model.food.exceptions.DuplicateFoodException;
import seedu.address.model.tag.Tag;
import seedu.address.model.user.UserProfile;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Food[] getSampleFoods() {
        return new Food[] {
            new Food(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"), new Price("$0"), new Rating("0"),
                    getTagSet("friends"), getAllergySet("lactose")),
            new Food(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new Price("$0"), new Rating("0"),
                    getTagSet("colleagues", "friends"), getAllergySet("lactose")),
            new Food(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new Price("$0"), new Rating("0"),
                    getTagSet("neighbours"), getAllergySet("lactose")),
            new Food(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new Price("$0"), new Rating("0"),
                    getTagSet("family"), getAllergySet("lactose")),
            new Food(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"), new Price("$0"), new Rating("0"),
                    getTagSet("classmates"), getAllergySet("lactose")),
            new Food(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"), new Price("$0"), new Rating("0"),
                    getTagSet("colleagues"), getAllergySet("lactose"))
        };
    }

    public static UserProfile getSampleProfile() {
        return new UserProfile(new Name("Hacker"), new Phone("123456"),
                new Address("Blk 71 One North MRT Station"), getAllergySet("lactose", "peanut", "cinnamon"));
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        try {
            AddressBook sampleAb = new AddressBook();
            for (Food sampleFood : getSampleFoods()) {
                sampleAb.addFood(sampleFood);
            }
            return sampleAb;
        } catch (DuplicateFoodException e) {
            throw new AssertionError("sample data cannot contain duplicate foods", e);
        }
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        HashSet<Tag> tags = new HashSet<>();
        for (String s : strings) {
            tags.add(new Tag(s));
        }

        return tags;
    }

    /**
     * Returns an allergy set containing the list of strings given.
     */
    public static Set<Allergy> getAllergySet(String... strings) {
        HashSet<Allergy> allergies = new HashSet<>();
        for (String s : strings) {
            allergies.add(new Allergy(s));
        }

        return allergies;
    }
}
