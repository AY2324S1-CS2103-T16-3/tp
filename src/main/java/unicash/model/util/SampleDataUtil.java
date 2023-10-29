package unicash.model.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import unicash.model.ReadOnlyUniCash;
import unicash.model.UniCash;
import unicash.model.category.Category;
import unicash.model.category.UniqueCategoryList;
import unicash.model.commons.Amount;
import unicash.model.transaction.DateTime;
import unicash.model.transaction.Location;
import unicash.model.transaction.Name;
import unicash.model.transaction.Transaction;
import unicash.model.transaction.Type;

/**
 * Contains utility methods for populating {@code UniCash} with sample data.
 */
public class SampleDataUtil {
    /**
     * This private constructor is declared to support a constructor initialisation
     * by code coverage.
     */
    private SampleDataUtil() {

    }

    public static Transaction[] getSampleTransactions() {
        return new Transaction[]{
            new Transaction(
                    new Name("Lunch at McDonalds"),
                    new Type("expense"),
                    new Amount(17.40),
                    new DateTime("15-09-2023 11:00"),
                    new Location("Clementi Mall"),
                    getCategoryList("food")
            ),
            new Transaction(
                    new Name("Buy clothes"),
                    new Type("expense"),
                    new Amount(109.00),
                    new DateTime("17-09-2023 18:30"),
                    new Location("Uniqlo Bugis"),
                    getCategoryList("shopping")
            ),

            new Transaction(
                    new Name("Intern allowance september"),
                    new Type("income"),
                    new Amount(1800.00),
                    new DateTime("17-09-2023 00:00"),
                    new Location(""),
                    getCategoryList("salary")
            ),

            new Transaction(
                    new Name("Evening with friends"),
                    new Type("expense"),
                    new Amount(49.50),
                    new DateTime("17-09-2023 00:00"),
                    new Location("Clarke Quay"),
                    getCategoryList("social")
            )

        };
    }

    public static Transaction[] getTestTransactions() {
        return new Transaction[]{
            new Transaction(
                    new Name("Lunch at McDonalds"),
                    new Type("expense"),
                    new Amount(17.40),
                    new DateTime("15-09-2023 11:00"),
                    new Location("Clementi Mall"),
                    getCategoryList("food")
                    ),

            new Transaction(
                    new Name("Buy clothes"),
                    new Type("expense"),
                    new Amount(109.00),
                    new DateTime("17-09-2023 18:30"),
                    new Location("Uniqlo Bugis"),
                    getCategoryList("shopping")
                    ),

            new Transaction(
                    new Name("Intern allowance september"),
                    new Type("income"),
                    new Amount(1800.00),
                    new DateTime("17-09-2023 00:00"),
                    new Location(""),
                    getCategoryList("salary")
                    ),

            new Transaction(
                    new Name("Evening with friends"),
                    new Type("expense"),
                    new Amount(49.50),
                    new DateTime("17-09-2023 00:00"),
                    new Location("Clarke Quay"),
                    getCategoryList("social")
                    ),

            new Transaction(
                    new Name("Morning Coffee"),
                    new Type("expense"),
                    new Amount(4.50),
                    new DateTime("18-09-2023 09:00"),
                    new Location("Starbucks"),
                    getCategoryList("food")
                    ),

            new Transaction(
                    new Name("Monthly rent"),
                    new Type("expense"),
                    new Amount(1200.00),
                    new DateTime("01-09-2023 00:00"),
                    new Location(""),
                    getCategoryList("housing")
                    ),

            new Transaction(
                    new Name("Grocery shopping"),
                    new Type("expense"),
                    new Amount(65.30),
                    new DateTime("19-09-2023 14:30"),
                    new Location("NTUC Fairprice"),
                    getCategoryList("groceries")
                    ),

            new Transaction(
                    new Name("Book purchase"),
                    new Type("expense"),
                    new Amount(20.00),
                    new DateTime("20-09-2023 17:10"),
                    new Location("Kinokuniya"),
                    getCategoryList("shopping")
                    ),

            new Transaction(
                    new Name("Gym membership"),
                    new Type("expense"),
                    new Amount(100.00),
                    new DateTime("22-09-2023 14:00"),
                    new Location("ActiveSG Gym"),
                    getCategoryList("fitness")
                    ),

            new Transaction(
                    new Name("Freelance project payment"),
                    new Type("income"),
                    new Amount(500.00),
                    new DateTime("25-09-2023 13:45"),
                    new Location(""),
                    getCategoryList("freelance")
                    ),

            new Transaction(
                    new Name("Sale of old laptop"),
                    new Type("income"),
                    new Amount(400.00),
                    new DateTime("26-09-2023 16:10"),
                    new Location(""),
                    getCategoryList("sales")
                    ),

            new Transaction(
                    new Name("Phone bill"),
                    new Type("expense"),
                    new Amount(50.00),
                    new DateTime("28-09-2023 12:00"),
                    new Location(""),
                    getCategoryList("utilities")
                    ),

            new Transaction(
                    new Name("Dinner at KFC"),
                    new Type("expense"),
                    new Amount(25.40),
                    new DateTime("29-09-2023 19:00"),
                    new Location("Jurong West"),
                    getCategoryList("food")

                    ),

            new Transaction(
                    new Name("New Shoes"),
                    new Type("expense"),
                    new Amount(129.00),
                    new DateTime("30-09-2023 15:45"),
                    new Location("Nike Outlet"),
                    getCategoryList("shopping")
                    ),

            new Transaction(
                    new Name("Birthday Gift"),
                    new Type("expense"),
                    new Amount(60.00),
                    new DateTime("30-09-2023 12:30"),
                    new Location("Gift Shop"),
                    getCategoryList("gifts")
                    ),

            new Transaction(
                    new Name("Movie night"),
                    new Type("expense"),
                    new Amount(30.00),
                    new DateTime("01-10-2023 20:10"),
                    new Location("GV Cinemas"),
                    getCategoryList("entertainment")
                    ),

            new Transaction(
                    new Name("Petrol fill-up"),
                    new Type("expense"),
                    new Amount(70.50),
                    new DateTime("02-10-2023 10:15"),
                    new Location("Shell Station"),
                    getCategoryList("transport")
                    ),

            new Transaction(
                    new Name("Water bill"),
                    new Type("expense"),
                    new Amount(20.00),
                    new DateTime("02-10-2023 12:05"),
                    new Location(""),
                    getCategoryList("utilities")
                    ),

            new Transaction(
                    new Name("Music subscription"),
                    new Type("expense"),
                    new Amount(9.99),
                    new DateTime("03-10-2023 11:55"),
                    new Location(""),
                    getCategoryList("entertainment")
                    ),

            new Transaction(
                    new Name("Dentist appointment"),
                    new Type("expense"),
                    new Amount(55.00),
                    new DateTime("04-10-2023 14:20"),
                    new Location("Central Clinic"),
                    getCategoryList("health")
                    ),

            new Transaction(
                    new Name("Spotify subscription"),
                    new Type("expense"),
                    new Amount(9.99),
                    new DateTime("05-10-2023 11:55"),
                    new Location(""),
                    getCategoryList("entertainment")
                    ),

            new Transaction(
                    new Name("Netflix subscription"),
                    new Type("expense"),
                    new Amount(12.99),
                    new DateTime("06-10-2023 13:00"),
                    new Location(""),
                    getCategoryList("entertainment")
                    ),

            new Transaction(
                    new Name("Lunch with John"),
                    new Type("expense"),
                    new Amount(45.50),
                    new DateTime("07-10-2023 12:30"),
                    new Location("Downtown Restaurant"),
                    getCategoryList("social")
                    ),

            new Transaction(
                    new Name("Train ticket"),
                    new Type("expense"),
                    new Amount(2.50),
                    new DateTime("08-10-2023 08:30"),
                    new Location("MRT Station"),
                    getCategoryList("transport")
                    ),

            new Transaction(
                    new Name("Bus fare"),
                    new Type("expense"),
                    new Amount(1.40),
                    new DateTime("09-10-2023 09:15"),
                    new Location("Bus Stop"),
                    getCategoryList("transport")
                    ),

            new Transaction(
                    new Name("Bakery visit"),
                    new Type("expense"),
                    new Amount(15.40),
                    new DateTime("10-10-2023 10:00"),
                    new Location("Local Bakery"),
                    getCategoryList("food")
                    ),

            new Transaction(
                    new Name("Flower purchase"),
                    new Type("expense"),
                    new Amount(25.00),
                    new DateTime("11-10-2023 14:15"),
                    new Location("Flower Shop"),
                    getCategoryList("gifts")
                    ),

            new Transaction(
                    new Name("Office lunch"),
                    new Type("expense"),
                    new Amount(8.00),
                    new DateTime("12-10-2023 12:00"),
                    new Location("Cafeteria"),
                    getCategoryList("food")
                    ),

            new Transaction(
                    new Name("New headphones"),
                    new Type("expense"),
                    new Amount(99.00),
                    new DateTime("13-10-2023 16:30"),
                    new Location("Electronics Store"),
                    getCategoryList("shopping")
                    ),

            new Transaction(
                    new Name("Taxi ride"),
                    new Type("expense"),
                    new Amount(15.50),
                    new DateTime("14-10-2023 18:20"),
                    new Location(""),
                    getCategoryList("transport")
                    ),
        };
    }

    public static ReadOnlyUniCash getSampleUniCash() {
        UniCash sample = new UniCash();
        for (var transaction : getSampleTransactions()) {
            sample.addTransaction(transaction);
        }
        return sample;
    }

    public static ReadOnlyUniCash getTestUniCash() {
        UniCash sample = new UniCash();
        for (var transaction : getTestTransactions()) {
            sample.addTransaction(transaction);
        }
        return sample;
    }

    /**
     * Returns a category set containing the list of strings given.
     */
    public static UniqueCategoryList getCategoryList(String... strings) {
        List<Category> categoryList = Arrays.stream(strings)
                .map(Category::new)
                .collect(Collectors.toList());

        return new UniqueCategoryList(categoryList);
    }
}
