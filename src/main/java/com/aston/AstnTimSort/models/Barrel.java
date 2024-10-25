package com.aston.AstnTimSort.models;

import java.util.Arrays;
import java.util.List;

public class Barrel implements Comparable<Barrel> {

    private final Double amount;
    private final String storedMaterial;
    private final MaterialEnum whichItIsMade;
    private static List<String> materials = Arrays.asList(
            "water", "milk", "juice", "chloramine", "mercury",
            "lemonade", "soda", "soda", "cocktail", "phenol",
            "oil", "vinegar", "syrup", "honey", "kefir",
            "yogurt", "broth", "soup", "compote", "jelly",
            "mors", "nectar", "cream", "sauce", "ketchup",
            "mayonnaise", "vegetable oil", "fish oil", "ghee", "essence",
            "benzene", "lotion", "shampoo", "glycerin", "alcohol",
            "ethanol", "solvent", "paint", "varnish", "ink",
            "fuel oil", "oil", "blood", "diethylamine", "trichloroethylene",
            "hydrogen bromide", "ammonia", "dimethylformamide", "methanol", "hydrogen_peroxide"
    );

    private Barrel(Double amount, String storedMaterial, MaterialEnum whichItIsMade) {
        this.amount = amount;
        this.storedMaterial = storedMaterial;
        this.whichItIsMade = whichItIsMade;
    }

    public Double getAmount() { return amount; }

    public String getStoredMaterial() { return storedMaterial; }

    public MaterialEnum getWhichItIsMade() { return whichItIsMade; }

    public static String getMaterial(Integer number){ return materials.get(number); }

    @Override
    public int compareTo(Barrel o) {
        double result = amount - o.getAmount();
        if (result == 0)
            result = storedMaterial.compareTo(o.getStoredMaterial());
        if (result == 0)
            result = whichItIsMade.compareTo(o.getWhichItIsMade());
        return (int)result;
    }

    public enum MaterialEnum {
        METAL, PLASTIC, WOODEN
    }

    @Override
    public String toString() {
        return "Barrel [amount= " + amount +  ", storedMaterial="
                + storedMaterial + ", whichItIsMade= " + whichItIsMade + ']';
    }

    public static Builder getBuilder(){ return new Builder();}

    public static class Builder{
        private Double amount;
        private String storedMaterial;
        private MaterialEnum whichItIsMade;

        public Builder setAmount(Double amount){
            if (amount <= 0)
                throw new IllegalArgumentException();
            this.amount = amount;
            return this;
        }

        public Builder setStoredMaterial(String storedMaterial){
            if (!storedMaterial.chars().allMatch(Character::isLetter))
                throw new IllegalArgumentException();
            this.storedMaterial = storedMaterial;
            return this;
        }

        public Builder setWhichItIsMade(MaterialEnum whichItIsMade){
            this.whichItIsMade = whichItIsMade;
            return this;
        }

        public Barrel build() {return new Barrel(amount, storedMaterial, whichItIsMade);}
    }
}
