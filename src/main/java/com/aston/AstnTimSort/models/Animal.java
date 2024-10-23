package com.aston.AstnTimSort.models;

public class Animal {

    private final AnimalTypeEnum type;
    private final EyeColorEnum eyeColor;
    private final Boolean withWool;

    private Animal(AnimalTypeEnum type, EyeColorEnum eyeColor, Boolean withWool) {
        this.type = type;
        this.eyeColor = eyeColor;
        this.withWool = withWool;
    }

    public AnimalTypeEnum getType() { return type; }

    public EyeColorEnum getEyeColor() { return eyeColor; }

    public boolean isWithWool() { return withWool; }

    // method compareTo

    public enum AnimalTypeEnum{
        MAMMALS, SHELLFISH, FISH, BIRDS, REPTILES, AMPHIBIANS, ARTHROPODS
    }

    public enum EyeColorEnum{
        BROWN, BLUE, GREEN, GRAY, BLACK, MIXED
    }

    @Override
    public String toString() {
        return "Animal [type= " + type + ", eyeColor= " + eyeColor +", withWool=" + withWool + ']';
    }

    public static Builder newBuilder(){ return new Builder();}

    public static class Builder{
        private AnimalTypeEnum type;
        private EyeColorEnum eyeColor;
        private Boolean withWool;

        public Builder setType(AnimalTypeEnum type){
            this.type = type;
            return this;
        }

        public Builder setEyeColor(EyeColorEnum eyeColor){
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
