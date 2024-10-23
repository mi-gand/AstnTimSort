package com.aston.AstnTimSort.models;

public class Animal {

    private final TypeEnum type;
    private final String eyeColor;
    private final Boolean withWool;

    private Animal(TypeEnum type, String eyeColor, Boolean withWool) {
        this.type = type;
        this.eyeColor = eyeColor;
        this.withWool = withWool;
    }

    public TypeEnum getType() { return type; }

    public String getEyeColor() { return eyeColor; }

    public boolean isWithWool() { return withWool; }

    // method compareTo

    public enum TypeEnum{
        MAMMALS, SHELLFISH, FISH, BIRDS, REPTILES, AMPHIBIANS, ARTHROPODS
    }

    @Override
    public String toString() {
        return "Animal [type= " + type + ", eyeColor= " + eyeColor +", withWool=" + withWool + ']';
    }

    public static Builder newBuilder(){ return new Builder();}

    public static class Builder{
        private TypeEnum type;
        private String eyeColor;
        private Boolean withWool;

        public Builder setType(TypeEnum type){
            this.type = type;
            return this;
        }

        public Builder setEyeColor(String eyeColor){
            if (!eyeColor.chars().allMatch(Character::isLetter))
                throw new IllegalArgumentException();
            this.eyeColor = eyeColor;
            return this;
        }

        public Builder setWool(Boolean withWool){
            if(withWool == null)
                throw new IllegalArgumentException();
            this.withWool = withWool;
            return this;
        }

        public Animal build() {return new Animal(type, eyeColor, withWool);}
    }

}
