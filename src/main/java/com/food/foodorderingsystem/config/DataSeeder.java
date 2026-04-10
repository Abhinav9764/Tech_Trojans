package com.food.foodorderingsystem.config;

import com.food.foodorderingsystem.entity.MenuItem;
import com.food.foodorderingsystem.entity.Role;
import com.food.foodorderingsystem.entity.User;
import com.food.foodorderingsystem.repository.MenuItemRepository;
import com.food.foodorderingsystem.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

import static com.food.foodorderingsystem.config.DemoMediaUrls.Dishes.*;

@Component
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final MenuItemRepository menuItemRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(
            UserRepository userRepository,
            MenuItemRepository menuItemRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.menuItemRepository = menuItemRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        seedAdmin();
        seedRestaurantsAndMenus();
    }

    private User seedAdmin() {
        return userRepository.findByEmail("admin@foodapp.com").orElseGet(() -> {
            User admin = new User();
            admin.setEmail("admin@foodapp.com");
            admin.setPassword(passwordEncoder.encode("Admin@123"));
            admin.setFullName("System Admin");
            admin.setRole(Role.ADMIN);
            admin.setActive(true);
            return userRepository.save(admin);
        });
    }

    private void seedRestaurantsAndMenus() {
        List<RestaurantSeed> restaurants = List.of(
                new RestaurantSeed("restaurant@foodapp.com", "Demo Restaurant Owner", "Demo Bites", "9999999999", defaultMenu()),
                new RestaurantSeed("spice.route@foodapp.com", "Arjun Sharma", "Spice Route Kitchen", "9999999901", indianMenu()),
                new RestaurantSeed("urban.pizza@foodapp.com", "Nisha Verma", "Urban Pizza Co.", "9999999902", pizzaMenu()),
                new RestaurantSeed("green.bowl@foodapp.com", "Riya Das", "Green Bowl Salads", "9999999903", saladMenu()),
                new RestaurantSeed("street.treats@foodapp.com", "Karan Rao", "Street Treats", "9999999904", snacksMenu()),
                new RestaurantSeed("sushi.co@foodapp.com", "Kenji Sato", "Sushi & Co.", "9999999905", sushiMenu()),
                new RestaurantSeed("dessert.lab@foodapp.com", "Priya Nair", "Dessert Lab", "9999999906", dessertMenu()),
                new RestaurantSeed("burger.barn@foodapp.com", "Aman Khan", "Burger Barn", "9999999907", burgerMenu()),
                new RestaurantSeed("tandoor.flame@foodapp.com", "Ishita Mehta", "Tandoor Flame", "9999999908", tandoorMenu()),
                new RestaurantSeed("wok.story@foodapp.com", "Li Wei", "Wok Story", "9999999909", asianMenu()),
                new RestaurantSeed("coastal.catch@foodapp.com", "Sara Mathew", "Coastal Catch", "9999999910", seafoodMenu()),
                new RestaurantSeed("breakfast.club@foodapp.com", "Dev Patel", "Breakfast Club", "9999999911", breakfastMenu())
        );

        for (RestaurantSeed seed : restaurants) {
            User restaurant = userRepository.findByEmail(seed.email()).orElseGet(() -> {
                User user = new User();
                user.setEmail(seed.email());
                user.setPassword(passwordEncoder.encode("Restaurant@123"));
                user.setFullName(seed.ownerName());
                user.setRole(Role.RESTAURANT);
                user.setRestaurantName(seed.restaurantName());
                user.setPhoneNumber(seed.phoneNumber());
                user.setActive(true);
                return userRepository.save(user);
            });

            if (!menuItemRepository.findByRestaurantId(restaurant.getId()).isEmpty()) continue;

            for (MenuSeed menuSeed : seed.menuItems()) {
                MenuItem item = new MenuItem();
                item.setRestaurant(restaurant);
                item.setName(menuSeed.name());
                item.setDescription(menuSeed.description());
                item.setPrice(menuSeed.priceValue());
                item.setCategory(menuSeed.category());
                item.setImageUrl(menuSeed.imageUrl());
                item.setAvailable(true);
                menuItemRepository.save(item);
            }
        }
    }

    private List<MenuSeed> defaultMenu() {
        return List.of(
                new MenuSeed("Classic Veg Burger", "Loaded with fresh veggies and signature sauce", "Burgers", "129.00", BURGER_VEG),
                new MenuSeed("Margherita Pizza", "Cheesy pizza with fresh basil", "Pizza", "249.00", PIZZA_MARGHERITA),
                new MenuSeed("Paneer Wrap", "Smoky paneer with crunchy veggies", "Wraps", "179.00", WRAP),
                new MenuSeed("French Fries", "Crispy salted fries", "Sides", "99.00", FRIES),
                new MenuSeed("Mojito", "Refreshing mint cooler", "Beverages", "89.00", MOJITO),
                new MenuSeed("Brownie Sundae", "Chocolate brownie with vanilla scoop", "Desserts", "159.00", BROWNIE)
        );
    }

    private List<MenuSeed> indianMenu() {
        return List.of(
                new MenuSeed("Butter Chicken", "Creamy tomato gravy and tender chicken", "North Indian", "329.00", BUTTER_CHICKEN),
                new MenuSeed("Paneer Butter Masala", "Paneer cubes in rich buttery gravy", "North Indian", "279.00", PANEER_CURRY),
                new MenuSeed("Dal Makhani", "Slow-cooked black lentils", "North Indian", "229.00", DAL),
                new MenuSeed("Chicken Biryani", "Fragrant basmati with spiced chicken", "Biryani", "289.00", BIRYANI),
                new MenuSeed("Garlic Naan", "Soft naan brushed with garlic butter", "Breads", "69.00", NAAN),
                new MenuSeed("Gulab Jamun", "Warm syrup-soaked dumplings", "Desserts", "99.00", GULAB_JAMUN),
                new MenuSeed("Masala Chaas", "Spiced buttermilk", "Beverages", "59.00", CHAAS)
        );
    }

    private List<MenuSeed> pizzaMenu() {
        return List.of(
                new MenuSeed("Pepperoni Pizza", "Loaded with pepperoni and mozzarella", "Pizza", "349.00", PIZZA_PEPPERONI),
                new MenuSeed("Farmhouse Pizza", "Veggie-loaded pizza with feta", "Pizza", "319.00", PIZZA_VEG),
                new MenuSeed("Cheese Burst Pizza", "Extra cheese stuffed crust", "Pizza", "379.00", PIZZA_CHEESE),
                new MenuSeed("Garlic Breadsticks", "Baked breadsticks with herbs", "Sides", "149.00", BREADSTICKS),
                new MenuSeed("Chicken Wings", "Spicy glazed wings", "Sides", "259.00", WINGS),
                new MenuSeed("Tiramisu Cup", "Coffee infused creamy dessert", "Desserts", "189.00", TIRAMISU)
        );
    }

    private List<MenuSeed> saladMenu() {
        return List.of(
                new MenuSeed("Caesar Salad", "Romaine, parmesan, croutons", "Salads", "229.00", CAESAR_SALAD),
                new MenuSeed("Greek Salad", "Olives, cucumber, feta, herbs", "Salads", "219.00", GREEK_SALAD),
                new MenuSeed("Quinoa Bowl", "Protein-rich quinoa veggie bowl", "Healthy Bowls", "249.00", QUINOA_BOWL),
                new MenuSeed("Avocado Toast", "Smashed avocado on sourdough", "Healthy", "199.00", AVOCADO_TOAST),
                new MenuSeed("Detox Juice", "Beetroot, carrot and apple juice", "Beverages", "129.00", JUICE),
                new MenuSeed("Granola Yogurt", "Greek yogurt with granola", "Breakfast", "169.00", GRANOLA_YOGURT)
        );
    }

    private List<MenuSeed> snacksMenu() {
        return List.of(
                new MenuSeed("Pani Puri", "Classic street-style golgappa", "Chaat", "89.00", PANI_PURI),
                new MenuSeed("Dahi Puri", "Crisp puri with yogurt and chutneys", "Chaat", "99.00", DAHI_PURI),
                new MenuSeed("Pav Bhaji", "Buttery buns with spicy bhaji", "Snacks", "149.00", PAV_BHAJI),
                new MenuSeed("Samosa Chaat", "Crispy samosa with toppings", "Chaat", "129.00", SAMOSA),
                new MenuSeed("Aloo Tikki", "Pan-fried potato patties", "Snacks", "109.00", ALOO_TIKKI),
                new MenuSeed("Kulfi Falooda", "Traditional kulfi with vermicelli", "Desserts", "159.00", FALOODA)
        );
    }

    private List<MenuSeed> sushiMenu() {
        return List.of(
                new MenuSeed("Salmon Nigiri", "Fresh salmon over seasoned rice", "Japanese", "399.00", SUSHI_NIGIRI),
                new MenuSeed("California Roll", "Crab stick, avocado, cucumber roll", "Japanese", "349.00", SUSHI_ROLL),
                new MenuSeed("Crunchy Tempura Roll", "Tempura prawn roll with sauce", "Japanese", "379.00", TEMPURA),
                new MenuSeed("Miso Soup", "Savory soy-based broth", "Soups", "129.00", MISO_SOUP),
                new MenuSeed("Chicken Katsu", "Crispy breaded chicken cutlet", "Japanese", "329.00", KATSU),
                new MenuSeed("Matcha Cheesecake", "Creamy cheesecake with matcha", "Desserts", "219.00", MATCHA_CAKE)
        );
    }

    private List<MenuSeed> dessertMenu() {
        return List.of(
                new MenuSeed("Chocolate Truffle Cake", "Rich dark chocolate cake slice", "Desserts", "189.00", CHOCOLATE_CAKE),
                new MenuSeed("Red Velvet Jar", "Layered cream cheese dessert jar", "Desserts", "179.00", RED_VELVET),
                new MenuSeed("Classic Waffle", "Crisp waffle with maple syrup", "Desserts", "169.00", WAFFLE),
                new MenuSeed("Nutella Pancakes", "Fluffy pancakes with nutella", "Desserts", "209.00", PANCAKES),
                new MenuSeed("Blueberry Cheesecake", "Creamy cheesecake with berry compote", "Desserts", "229.00", CHEESECAKE),
                new MenuSeed("Vanilla Ice Cream Tub", "Classic vanilla ice cream", "Ice Cream", "149.00", ICE_CREAM)
        );
    }

    private List<MenuSeed> burgerMenu() {
        return List.of(
                new MenuSeed("Smoky Chicken Burger", "Grilled chicken patty with smoky mayo", "Burgers", "219.00", CHICKEN_BURGER),
                new MenuSeed("Double Cheese Burger", "Two patties and double cheese", "Burgers", "269.00", DOUBLE_BURGER),
                new MenuSeed("Peri Peri Fries", "Spicy peri peri fries", "Sides", "129.00", PERI_FRIES),
                new MenuSeed("Onion Rings", "Crispy battered onion rings", "Sides", "139.00", ONION_RINGS),
                new MenuSeed("BBQ Burger", "BBQ sauce glazed burger", "Burgers", "239.00", BBQ_BURGER),
                new MenuSeed("Cold Coffee", "Iced coffee with cream", "Beverages", "119.00", COLD_COFFEE)
        );
    }

    private List<MenuSeed> tandoorMenu() {
        return List.of(
                new MenuSeed("Tandoori Chicken", "Char-grilled tandoori chicken", "Grill", "359.00", TANDOORI),
                new MenuSeed("Malai Tikka", "Creamy marinated chicken tikka", "Grill", "319.00", TIKKA),
                new MenuSeed("Paneer Tikka", "Smoky grilled paneer cubes", "Grill", "279.00", PANEER_TIKKA),
                new MenuSeed("Jeera Rice", "Fragrant cumin rice", "Rice", "149.00", RICE),
                new MenuSeed("Raita", "Curd with herbs and veggies", "Sides", "79.00", RAITA),
                new MenuSeed("Lassi", "Sweet Punjabi yogurt drink", "Beverages", "99.00", LASSI)
        );
    }

    private List<MenuSeed> asianMenu() {
        return List.of(
                new MenuSeed("Veg Hakka Noodles", "Stir-fried noodles and veggies", "Chinese", "209.00", NOODLES),
                new MenuSeed("Chicken Manchurian", "Spicy Indo-Chinese gravy", "Chinese", "269.00", MANCHURIAN),
                new MenuSeed("Schezwan Fried Rice", "Hot and spicy rice", "Chinese", "229.00", FRIED_RICE),
                new MenuSeed("Spring Rolls", "Crispy veggie spring rolls", "Starters", "169.00", SPRING_ROLLS),
                new MenuSeed("Dimsum Platter", "Steamed assorted dumplings", "Asian", "299.00", DIMSUM),
                new MenuSeed("Thai Basil Chicken", "Thai style basil chicken", "Thai", "319.00", THAI_BASIL)
        );
    }

    private List<MenuSeed> seafoodMenu() {
        return List.of(
                new MenuSeed("Grilled Fish Fillet", "Herb grilled fish with lemon butter", "Seafood", "399.00", GRILLED_FISH),
                new MenuSeed("Prawn Curry", "Coastal spiced prawn curry", "Seafood", "369.00", PRAWN_CURRY),
                new MenuSeed("Fish and Chips", "Crispy fish with fries", "Seafood", "349.00", FISH_CHIPS),
                new MenuSeed("Calamari Rings", "Crispy fried calamari", "Starters", "289.00", CALAMARI),
                new MenuSeed("Lemon Garlic Prawns", "Sauteed prawns in garlic butter", "Seafood", "389.00", PRAWNS),
                new MenuSeed("Seafood Soup", "Light soup with mixed seafood", "Soups", "199.00", SEAFOOD_SOUP)
        );
    }

    private List<MenuSeed> breakfastMenu() {
        return List.of(
                new MenuSeed("Masala Dosa", "Crispy dosa with potato masala", "South Indian", "139.00", DOSA),
                new MenuSeed("Idli Sambhar", "Soft idlis with sambhar", "South Indian", "109.00", IDLI),
                new MenuSeed("Aloo Paratha", "Stuffed paratha with curd", "Breakfast", "149.00", PARATHA),
                new MenuSeed("Poha", "Light flattened rice breakfast", "Breakfast", "99.00", POHA),
                new MenuSeed("Omelette Toast", "Cheese omelette with toast", "Breakfast", "159.00", OMELETTE),
                new MenuSeed("Filter Coffee", "Strong South Indian coffee", "Beverages", "79.00", FILTER_COFFEE)
        );
    }

    private record RestaurantSeed(
            String email,
            String ownerName,
            String restaurantName,
            String phoneNumber,
            List<MenuSeed> menuItems
    ) {}

    private record MenuSeed(
            String name,
            String description,
            String category,
            String price,
            String imageUrl
    ) {
        BigDecimal priceValue() {
            return new BigDecimal(price);
        }
    }
}
