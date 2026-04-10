package com.food.foodorderingsystem.config;

import java.util.Locale;
import java.util.Objects;

/**
 * Stock images for demo data (Unsplash). Distinct restaurant covers; dish URLs are referenced from {@link DataSeeder}.
 */
public final class DemoMediaUrls {

    private static final String Q = "?auto=format&fit=crop&w=1200&q=75";
    private static final String D = "?auto=format&fit=crop&w=800&q=80";

    private static final String[] RESTAURANT_COVERS = {
            "https://images.unsplash.com/photo-1517248135467-4c7edcad34c4" + Q,
            "https://images.unsplash.com/photo-1555396273-367ea4eb4db5" + Q,
            "https://images.unsplash.com/photo-1414235077428-338989a2e8c0" + Q,
            "https://images.unsplash.com/photo-1552566626-52f8b828add9" + Q,
            "https://images.unsplash.com/photo-1544148103-07737bf555d2" + Q,
            "https://images.unsplash.com/photo-1590846406792-0adc7f938f1d" + Q,
            "https://images.unsplash.com/photo-1521017432531-fbd92d768814" + Q,
            "https://images.unsplash.com/photo-1498654896293-37aacf113fd9" + Q,
            "https://images.unsplash.com/photo-1466978913421-dad2ebd01d17" + Q,
            "https://images.unsplash.com/photo-1476224203421-4ac9edb1bd93" + Q,
            "https://images.unsplash.com/photo-1537047902294-62a40c20a6ae" + Q,
            "https://images.unsplash.com/photo-1504674900247-0877df9cc836" + Q,
            "https://images.unsplash.com/photo-1551218808-94e220e084d2" + Q,
            "https://images.unsplash.com/photo-1514933651103-005eec06c04b" + Q,
            "https://images.unsplash.com/photo-1424847657312-08f632a75a1d" + Q,
            "https://images.unsplash.com/photo-1600891964092-4316c288032e" + Q,
            "https://images.unsplash.com/photo-1559339352-11d035aa65de" + Q,
            "https://images.unsplash.com/photo-1567620905732-2d1ec7ab7445" + Q,
    };

    /** Dish / item photos (800px wide, food-related). */
    public static final class Dishes {
        public static final String BURGER_VEG = "https://images.unsplash.com/photo-1520072959219-c59503e54358" + D;
        public static final String PIZZA_MARGHERITA = "https://images.unsplash.com/photo-1513104890138-7c749659a591" + D;
        public static final String WRAP = "https://images.unsplash.com/photo-1626700051175-6818013e1d4f" + D;
        public static final String FRIES = "https://images.unsplash.com/photo-1573080496219-bb080dd4d13c" + D;
        public static final String MOJITO = "https://images.unsplash.com/photo-1551538827-9c037cb4f32a" + D;
        public static final String BROWNIE = "https://images.unsplash.com/photo-1607920591413-4ec007e7007a" + D;

        public static final String BUTTER_CHICKEN = "https://images.unsplash.com/photo-1603894584373-5ac82b2ae398" + D;
        public static final String PANEER_CURRY = "https://images.unsplash.com/photo-1631452180519-c014fe946bc7" + D;
        public static final String DAL = "https://images.unsplash.com/photo-1546833999-b9f581a1996d" + D;
        public static final String BIRYANI = "https://images.unsplash.com/photo-1563379091339-03b74ca9382e" + D;
        public static final String NAAN = "https://images.unsplash.com/photo-1601050690597-df0568f70950" + D;
        public static final String GULAB_JAMUN = "https://images.unsplash.com/photo-1590080876351-941da357a5b5" + D;
        public static final String CHAAS = "https://images.unsplash.com/photo-1544145225-d33b7c4f554b" + D;

        public static final String PIZZA_PEPPERONI = "https://images.unsplash.com/photo-1628840042765-356cda07504e" + D;
        public static final String PIZZA_VEG = "https://images.unsplash.com/photo-1574071318508-1cdbab80d002" + D;
        public static final String PIZZA_CHEESE = "https://images.unsplash.com/photo-1513104890138-7c749659a591" + D;
        public static final String BREADSTICKS = "https://images.unsplash.com/photo-1619535860434-ba4d16e1962c" + D;
        public static final String WINGS = "https://images.unsplash.com/photo-1527477396000-e27137b521c6" + D;
        public static final String TIRAMISU = "https://images.unsplash.com/photo-1571877227200-a00879937099" + D;

        public static final String CAESAR_SALAD = "https://images.unsplash.com/photo-1546793665-c74683f339c1" + D;
        public static final String GREEK_SALAD = "https://images.unsplash.com/photo-1540420773420-3366772f4999" + D;
        public static final String QUINOA_BOWL = "https://images.unsplash.com/photo-1512621776951-a57141f2eefd" + D;
        public static final String AVOCADO_TOAST = "https://images.unsplash.com/photo-1541519227354-08fa5d50c44d" + D;
        public static final String JUICE = "https://images.unsplash.com/photo-1622597467836-f3285f2131b9" + D;
        public static final String GRANOLA_YOGURT = "https://images.unsplash.com/photo-1488477181946-6428a0291777" + D;

        public static final String PANI_PURI = "https://images.unsplash.com/photo-1582735689369-4fe89db7114c" + D;
        public static final String DAHI_PURI = "https://images.unsplash.com/photo-1589301760014-d929f3979dbc" + D;
        public static final String PAV_BHAJI = "https://images.unsplash.com/photo-1606491956689-2ea866880c84" + D;
        public static final String SAMOSA = "https://images.unsplash.com/photo-1619096252217-94f368f65532" + D;
        public static final String ALOO_TIKKI = "https://images.unsplash.com/photo-1589302168068-964664d93dc0" + D;
        public static final String FALOODA = "https://images.unsplash.com/photo-1563805042-7684c019e1cb" + D;

        public static final String SUSHI_NIGIRI = "https://images.unsplash.com/photo-1579584425555-c3ce17fd4351" + D;
        public static final String SUSHI_ROLL = "https://images.unsplash.com/photo-1617196034796-73dfa7b1fd56" + D;
        public static final String TEMPURA = "https://images.unsplash.com/photo-1615367423057-4b6b0df0bdbe" + D;
        public static final String MISO_SOUP = "https://images.unsplash.com/photo-1583394291163-3dc20f2f2dc6" + D;
        public static final String KATSU = "https://images.unsplash.com/photo-1569058242567-93de6f36f8e6" + D;
        public static final String MATCHA_CAKE = "https://images.unsplash.com/photo-1505253758473-96b7017fcd26" + D;

        public static final String CHOCOLATE_CAKE = "https://images.unsplash.com/photo-1578985545062-69928b1d9587" + D;
        public static final String RED_VELVET = "https://images.unsplash.com/photo-1586985289688-ca3cf47d3e4e" + D;
        public static final String WAFFLE = "https://images.unsplash.com/photo-1562376552-0d160a2f238d" + D;
        public static final String PANCAKES = "https://images.unsplash.com/photo-1528207776546-365bb710ee93" + D;
        public static final String CHEESECAKE = "https://images.unsplash.com/photo-1533134242443-d4fd215305ad" + D;
        public static final String ICE_CREAM = "https://images.unsplash.com/photo-1560008581-09826d1de69e" + D;

        public static final String CHICKEN_BURGER = "https://images.unsplash.com/photo-1568901346375-23c9450c58cd" + D;
        public static final String DOUBLE_BURGER = "https://images.unsplash.com/photo-1553979459-d2229ba7433f" + D;
        public static final String BBQ_BURGER = "https://images.unsplash.com/photo-1594212699903-ec8a3eca50f5" + D;
        public static final String PERI_FRIES = "https://images.unsplash.com/photo-1583085856873-5620dbdb14c9" + D;
        public static final String ONION_RINGS = "https://images.unsplash.com/photo-1637540926278-ac6f0e7affda" + D;
        public static final String COLD_COFFEE = "https://images.unsplash.com/photo-1461023058943-07fcbe16d735" + D;

        public static final String TANDOORI = "https://images.unsplash.com/photo-1598103442097-8b74394b95c6" + D;
        public static final String TIKKA = "https://images.unsplash.com/photo-1565557623262-b51c2513a641" + D;
        public static final String PANEER_TIKKA = "https://images.unsplash.com/photo-1694845287479-6a31e8e0d608" + D;
        public static final String RICE = "https://images.unsplash.com/photo-1586201375761-83865001e31c" + D;
        public static final String RAITA = "https://images.unsplash.com/photo-1571212515416-f658edd3e84b" + D;
        public static final String LASSI = "https://images.unsplash.com/photo-1572490122747-3968b75cc699" + D;

        public static final String NOODLES = "https://images.unsplash.com/photo-1612929633738-8fe44f7ec841" + D;
        public static final String MANCHURIAN = "https://images.unsplash.com/photo-1525755662778-989d0524087e" + D;
        public static final String FRIED_RICE = "https://images.unsplash.com/photo-1603133872878-684f208fb84b" + D;
        public static final String SPRING_ROLLS = "https://images.unsplash.com/photo-1628294895950-4fcab4c98fde" + D;
        public static final String DIMSUM = "https://images.unsplash.com/photo-1496116218417-1a781b1c416c" + D;
        public static final String THAI_BASIL = "https://images.unsplash.com/photo-1582878826629-29b7ad1cdc43" + D;

        public static final String GRILLED_FISH = "https://images.unsplash.com/photo-1519708227418-c8fd9a32b7a2" + D;
        public static final String PRAWN_CURRY = "https://images.unsplash.com/photo-1553621043-f0cef0549b88" + D;
        public static final String FISH_CHIPS = "https://images.unsplash.com/photo-1559339352-11d035aa65de" + D;
        public static final String CALAMARI = "https://images.unsplash.com/photo-1599487488170-d11ec9c172f0" + D;
        public static final String PRAWNS = "https://images.unsplash.com/photo-1565680018434-b513d5e5fd47" + D;
        public static final String SEAFOOD_SOUP = "https://images.unsplash.com/photo-1547592166-23ac45744acd" + D;

        public static final String DOSA = "https://images.unsplash.com/photo-1662116765994-2f346a707d6b" + D;
        public static final String IDLI = "https://images.unsplash.com/photo-1589301760014-d929f3979dbc" + D;
        public static final String PARATHA = "https://images.unsplash.com/photo-1626082929489-7b79b5f5c3d1" + D;
        public static final String POHA = "https://images.unsplash.com/photo-1588161305654-37f85533ba86" + D;
        public static final String OMELETTE = "https://images.unsplash.com/photo-1525351484163-7529414344d8" + D;
        public static final String FILTER_COFFEE = "https://images.unsplash.com/photo-1497935586351-b67a49e012bf" + D;

        public static final String BBQ_CHICKEN = "https://images.unsplash.com/photo-1529193591184-b1d58069ecdd" + D;
        public static final String BBQ_PANEER = "https://images.unsplash.com/photo-1694845287479-6a31e8e0d608" + D;
        public static final String CORN = "https://images.unsplash.com/photo-1551754655-9292b87a6d96" + D;

        public static final String BIRYANI_HYD = "https://images.unsplash.com/photo-1589302168068-964664d93dc0" + D;
        public static final String BIRYANI_MUTTON = "https://images.unsplash.com/photo-1642821373181-696a54906e93" + D;
        public static final String DOUBLE_MEETHA = "https://images.unsplash.com/photo-1578985545062-69928b1d9587" + D;

        public static final String TACOS = "https://images.unsplash.com/photo-1565299585323-38174cbca7b3" + D;
        public static final String QUESADILLA = "https://images.unsplash.com/photo-1618040996337-56904b7850b9" + D;
        public static final String CHURROS = "https://images.unsplash.com/photo-1624353365286-3f8d62e63e9d" + D;

        private Dishes() {
        }
    }

    /**
     * Large set of distinct food photos; combined with menu item id + name gives a stable unique image per dish
     * when no valid stored URL exists.
     */
    private static final String[] DISH_VARIETY_POOL = {
            Dishes.BURGER_VEG, Dishes.PIZZA_MARGHERITA, Dishes.WRAP, Dishes.FRIES, Dishes.MOJITO, Dishes.BROWNIE,
            Dishes.BUTTER_CHICKEN, Dishes.PANEER_CURRY, Dishes.DAL, Dishes.BIRYANI, Dishes.NAAN, Dishes.GULAB_JAMUN,
            Dishes.CHAAS, Dishes.PIZZA_PEPPERONI, Dishes.PIZZA_VEG, Dishes.BREADSTICKS, Dishes.WINGS, Dishes.TIRAMISU,
            Dishes.CAESAR_SALAD, Dishes.GREEK_SALAD, Dishes.QUINOA_BOWL, Dishes.AVOCADO_TOAST, Dishes.JUICE,
            Dishes.GRANOLA_YOGURT, Dishes.PANI_PURI, Dishes.DAHI_PURI, Dishes.PAV_BHAJI, Dishes.SAMOSA, Dishes.ALOO_TIKKI,
            Dishes.FALOODA, Dishes.SUSHI_NIGIRI, Dishes.SUSHI_ROLL, Dishes.TEMPURA, Dishes.MISO_SOUP, Dishes.KATSU,
            Dishes.MATCHA_CAKE, Dishes.CHOCOLATE_CAKE, Dishes.RED_VELVET, Dishes.WAFFLE, Dishes.PANCAKES, Dishes.CHEESECAKE,
            Dishes.ICE_CREAM, Dishes.CHICKEN_BURGER, Dishes.DOUBLE_BURGER, Dishes.BBQ_BURGER, Dishes.PERI_FRIES,
            Dishes.ONION_RINGS, Dishes.COLD_COFFEE, Dishes.TANDOORI, Dishes.TIKKA, Dishes.PANEER_TIKKA, Dishes.RICE,
            Dishes.RAITA, Dishes.LASSI, Dishes.NOODLES, Dishes.MANCHURIAN, Dishes.FRIED_RICE, Dishes.SPRING_ROLLS,
            Dishes.DIMSUM, Dishes.THAI_BASIL, Dishes.GRILLED_FISH, Dishes.PRAWN_CURRY, Dishes.FISH_CHIPS, Dishes.CALAMARI,
            Dishes.PRAWNS, Dishes.SEAFOOD_SOUP, Dishes.DOSA, Dishes.IDLI, Dishes.PARATHA, Dishes.POHA, Dishes.OMELETTE,
            Dishes.FILTER_COFFEE, Dishes.BBQ_CHICKEN, Dishes.BBQ_PANEER, Dishes.CORN, Dishes.BIRYANI_HYD,
            Dishes.BIRYANI_MUTTON, Dishes.DOUBLE_MEETHA, Dishes.TACOS, Dishes.QUESADILLA, Dishes.CHURROS,
            "https://images.unsplash.com/photo-1546069901-ba9599a7e63c" + D,
            "https://images.unsplash.com/photo-1504674900247-0877df9cc836" + D,
            "https://images.unsplash.com/photo-1473093295043-cdd812d0e601" + D,
            "https://images.unsplash.com/photo-1432139555190-58524dae6a55" + D,
            "https://images.unsplash.com/photo-1455619452474-d2be8b1e70cd" + D,
            "https://images.unsplash.com/photo-1482049016-208e3ff3c093" + D,
    };

    private DemoMediaUrls() {
    }

    public static String restaurantCover(long restaurantUserId) {
        int idx = Math.floorMod((int) restaurantUserId, RESTAURANT_COVERS.length);
        return RESTAURANT_COVERS[idx];
    }

    /**
     * Use stored URL when it looks like a real remote image; otherwise match dish name, then a stable per-item
     * image from {@link #DISH_VARIETY_POOL} using id + name so items in the same category do not all look identical.
     */
    public static String dishImageOrFallback(String storedUrl, String category, Long menuItemId, String dishName) {
        if (storedUrl != null && !storedUrl.isBlank()) {
            String lower = storedUrl.toLowerCase(Locale.ROOT);
            if (!lower.contains("example.com") && (lower.startsWith("http://") || lower.startsWith("https://"))) {
                return storedUrl;
            }
        }
        String byName = dishImageByDishName(dishName);
        if (byName != null) return byName;
        long id = menuItemId != null ? menuItemId : 0L;
        String name = dishName != null ? dishName : "";
        String cat = category != null ? category : "";
        int idx = Math.floorMod(Objects.hash(id, name, cat), DISH_VARIETY_POOL.length);
        return DISH_VARIETY_POOL[idx];
    }

    /** Backward-compatible overload when id and dish name are unknown. */
    public static String dishImageOrFallback(String storedUrl, String category) {
        return dishImageOrFallback(storedUrl, category, null, null);
    }

    private static String dishImageByDishName(String dishName) {
        if (dishName == null || dishName.isBlank()) return null;
        String n = dishName.toLowerCase(Locale.ROOT).trim();
        if (n.contains("margherita")) return Dishes.PIZZA_MARGHERITA;
        if (n.contains("pepperoni")) return Dishes.PIZZA_PEPPERONI;
        if (n.contains("farmhouse")) return Dishes.PIZZA_VEG;
        if (n.contains("cheese burst")) return Dishes.PIZZA_CHEESE;
        if (n.contains("garlic bread")) return Dishes.BREADSTICKS;
        if (n.contains("veg burger") || n.contains("classic veg burger")) return Dishes.BURGER_VEG;
        if (n.contains("double cheese burger")) return Dishes.DOUBLE_BURGER;
        if (n.contains("bbq burger")) return Dishes.BBQ_BURGER;
        if (n.contains("smoky chicken burger") || n.contains("chicken burger")) return Dishes.CHICKEN_BURGER;
        if (n.contains("paneer wrap") || n.contains("wrap")) return Dishes.WRAP;
        if (n.contains("peri peri")) return Dishes.PERI_FRIES;
        if (n.contains("french fries") || n.equals("fries")) return Dishes.FRIES;
        if (n.contains("onion ring")) return Dishes.ONION_RINGS;
        if (n.contains("mojito")) return Dishes.MOJITO;
        if (n.contains("brownie")) return Dishes.BROWNIE;
        if (n.contains("butter chicken")) return Dishes.BUTTER_CHICKEN;
        if (n.contains("paneer butter")) return Dishes.PANEER_CURRY;
        if (n.contains("dal makhani")) return Dishes.DAL;
        if (n.contains("chicken biryani")) return Dishes.BIRYANI;
        if (n.contains("hyderabadi")) return Dishes.BIRYANI_HYD;
        if (n.contains("mutton biryani")) return Dishes.BIRYANI_MUTTON;
        if (n.contains("garlic naan") || n.contains("naan")) return Dishes.NAAN;
        if (n.contains("gulab jamun")) return Dishes.GULAB_JAMUN;
        if (n.contains("chaas") || n.contains("buttermilk")) return Dishes.CHAAS;
        if (n.contains("caesar")) return Dishes.CAESAR_SALAD;
        if (n.contains("greek salad")) return Dishes.GREEK_SALAD;
        if (n.contains("quinoa")) return Dishes.QUINOA_BOWL;
        if (n.contains("avocado toast")) return Dishes.AVOCADO_TOAST;
        if (n.contains("detox juice") || n.contains("juice")) return Dishes.JUICE;
        if (n.contains("granola")) return Dishes.GRANOLA_YOGURT;
        if (n.contains("pani puri")) return Dishes.PANI_PURI;
        if (n.contains("dahi puri")) return Dishes.DAHI_PURI;
        if (n.contains("pav bhaji")) return Dishes.PAV_BHAJI;
        if (n.contains("samosa")) return Dishes.SAMOSA;
        if (n.contains("aloo tikki")) return Dishes.ALOO_TIKKI;
        if (n.contains("falooda") || n.contains("kulfi")) return Dishes.FALOODA;
        if (n.contains("nigiri") || n.contains("salmon")) return Dishes.SUSHI_NIGIRI;
        if (n.contains("california roll") || n.contains("sushi roll")) return Dishes.SUSHI_ROLL;
        if (n.contains("tempura")) return Dishes.TEMPURA;
        if (n.contains("miso soup")) return Dishes.MISO_SOUP;
        if (n.contains("katsu")) return Dishes.KATSU;
        if (n.contains("matcha")) return Dishes.MATCHA_CAKE;
        if (n.contains("truffle cake") || n.contains("chocolate")) return Dishes.CHOCOLATE_CAKE;
        if (n.contains("red velvet")) return Dishes.RED_VELVET;
        if (n.contains("waffle")) return Dishes.WAFFLE;
        if (n.contains("pancake") || n.contains("nutella")) return Dishes.PANCAKES;
        if (n.contains("cheesecake")) return Dishes.CHEESECAKE;
        if (n.contains("ice cream")) return Dishes.ICE_CREAM;
        if (n.contains("tiramisu")) return Dishes.TIRAMISU;
        if (n.contains("cold coffee")) return Dishes.COLD_COFFEE;
        if (n.contains("tandoori chicken")) return Dishes.TANDOORI;
        if (n.contains("malai tikka")) return Dishes.TIKKA;
        if (n.contains("paneer tikka")) return Dishes.PANEER_TIKKA;
        if (n.contains("jeera rice")) return Dishes.RICE;
        if (n.contains("raita")) return Dishes.RAITA;
        if (n.contains("lassi")) return Dishes.LASSI;
        if (n.contains("hakka noodle")) return Dishes.NOODLES;
        if (n.contains("manchurian")) return Dishes.MANCHURIAN;
        if (n.contains("fried rice") || n.contains("schezwan")) return Dishes.FRIED_RICE;
        if (n.contains("spring roll")) return Dishes.SPRING_ROLLS;
        if (n.contains("dimsum")) return Dishes.DIMSUM;
        if (n.contains("thai basil")) return Dishes.THAI_BASIL;
        if (n.contains("grilled fish")) return Dishes.GRILLED_FISH;
        if (n.contains("prawn curry")) return Dishes.PRAWN_CURRY;
        if (n.contains("fish and chip")) return Dishes.FISH_CHIPS;
        if (n.contains("calamari")) return Dishes.CALAMARI;
        if (n.contains("lemon garlic prawn") || (n.contains("prawn") && !n.contains("curry"))) return Dishes.PRAWNS;
        if (n.contains("seafood soup")) return Dishes.SEAFOOD_SOUP;
        if (n.contains("masala dosa") || n.contains("dosa")) return Dishes.DOSA;
        if (n.contains("idli")) return Dishes.IDLI;
        if (n.contains("paratha")) return Dishes.PARATHA;
        if (n.contains("poha")) return Dishes.POHA;
        if (n.contains("omelette")) return Dishes.OMELETTE;
        if (n.contains("filter coffee")) return Dishes.FILTER_COFFEE;
        if (n.contains("bbq chicken") || n.contains("smoked bbq chicken")) return Dishes.BBQ_CHICKEN;
        if (n.contains("bbq paneer")) return Dishes.BBQ_PANEER;
        if (n.contains("corn on the cob") || n.contains("grilled corn")) return Dishes.CORN;
        if (n.contains("taco")) return Dishes.TACOS;
        if (n.contains("quesadilla")) return Dishes.QUESADILLA;
        if (n.contains("churro")) return Dishes.CHURROS;
        if (n.contains("wing")) return Dishes.WINGS;
        return null;
    }

    public static String dishFallbackByCategory(String category) {
        if (category == null || category.isBlank()) return Dishes.PIZZA_MARGHERITA;
        String c = category.toLowerCase(Locale.ROOT);
        if (c.contains("pizza")) return Dishes.PIZZA_MARGHERITA;
        if (c.contains("burger")) return Dishes.BURGER_VEG;
        if (c.contains("south indian")) return Dishes.DOSA;
        if (c.contains("biryani")) return Dishes.BIRYANI;
        if (c.contains("north indian") || c.contains("indian")) return Dishes.BUTTER_CHICKEN;
        if (c.contains("salad")) return Dishes.CAESAR_SALAD;
        if (c.contains("chaat") || c.contains("snack")) return Dishes.PANI_PURI;
        if (c.contains("japanese") || c.contains("sushi")) return Dishes.SUSHI_NIGIRI;
        if (c.contains("dessert") || c.contains("ice cream")) return Dishes.CHOCOLATE_CAKE;
        if (c.contains("grill") || c.contains("tandoor")) return Dishes.TANDOORI;
        if (c.contains("chinese") || c.contains("thai") || c.contains("asian")) return Dishes.NOODLES;
        if (c.contains("seafood")) return Dishes.GRILLED_FISH;
        if (c.contains("breakfast")) return Dishes.PARATHA;
        if (c.contains("bbq")) return Dishes.BBQ_CHICKEN;
        if (c.contains("mexican")) return Dishes.TACOS;
        if (c.contains("beverage")) return Dishes.MOJITO;
        if (c.contains("soup")) return Dishes.MISO_SOUP;
        if (c.contains("bread")) return Dishes.NAAN;
        if (c.contains("healthy") || c.contains("bowl")) return Dishes.QUINOA_BOWL;
        if (c.contains("side")) return Dishes.FRIES;
        if (c.contains("wrap")) return Dishes.WRAP;
        if (c.contains("starters")) return Dishes.SPRING_ROLLS;
        if (c.contains("rice")) return Dishes.RICE;
        return Dishes.PIZZA_MARGHERITA;
    }
}
