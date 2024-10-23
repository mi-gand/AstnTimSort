package com.aston.AstnTimSort.models;

public class Barrel {

    private final Double amount;
    private final String storedMaterial;
    private final MaterialEnum whichItIsMade;

    private Barrel(Double amount, String storedMaterial, MaterialEnum whichItIsMade) {
        this.amount = amount;
        this.storedMaterial = storedMaterial;
        this.whichItIsMade = whichItIsMade;
    }

    public Double getAmount() { return amount; }

    public String getStoredMaterial() { return storedMaterial; }

    public MaterialEnum getWhichItIsMade() { return whichItIsMade; }

    //method compareTo

    public enum MaterialEnum {
        METAL, PLASTIC, WOODEN
    }

    @Override
    public String toString() {
        return "Barrel [amount= " + amount +  ", storedMaterial="
                + storedMaterial + ", whichItIsMade= " + whichItIsMade + ']';
    }

    public static Builder newBuilder(){ return new Builder();}

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
