package dev.abidux.survive.manager.food;

import dev.abidux.survive.model.food.FoodSickness;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class FoodSystem {

    private HashMap<Material, FoodSickness> FOOD_SICKNESS_EXPIRE = new HashMap<>();

    public boolean isSickOf(Material material) {
        FoodSickness expiration = FOOD_SICKNESS_EXPIRE.get(material);
        return expiration != null;
    }

    public FoodSickness getSicknessOf(Material material) {
        return FOOD_SICKNESS_EXPIRE.get(material);
    }

    public void incrementSickness(Material material) {
        FoodSickness sickness = getSicknessOf(material);
        if (sickness == null) {
            FOOD_SICKNESS_EXPIRE.put(material, (sickness = new FoodSickness(0, 0)));
        }
        sickness.incrementSickness();
    }

    public void ifSickOf(Material material, Consumer<FoodSickness> sicknessConsumer) {
        FoodSickness sickness = getSicknessOf(material);
        if (sickness != null) sicknessConsumer.accept(sickness);
    }

    public void executeExpirationProcess() {
        FOOD_SICKNESS_EXPIRE.values().removeIf(sickness -> sickness.getExpiration() <= System.currentTimeMillis());
    }

    @Override
    public String toString() {
        return FOOD_SICKNESS_EXPIRE.entrySet().stream()
                .map(entry -> entry.getKey() + ":" + entry.getValue().getLevel() + "/" + entry.getValue().getAmountAte() + "/" + entry.getValue().getExpiration())
                .collect(Collectors.joining(";"));
    }

    public static FoodSystem deserialize(String serialized) {
        FoodSystem foodSystem = new FoodSystem();
        for (String part : serialized.split(";")) {
            String[] separatedMaterial = part.split(":");
            String[] data = separatedMaterial[1].split("/");
            Material material = Material.getMaterial(separatedMaterial[0]);
            int level = Integer.parseInt(data[0]);
            int amountAte = Integer.parseInt(data[1]);
            int expiration = Integer.parseInt(data[2]);

            foodSystem.FOOD_SICKNESS_EXPIRE.put(material, new FoodSickness(expiration, level, amountAte));
        }
        return foodSystem;
    }
}