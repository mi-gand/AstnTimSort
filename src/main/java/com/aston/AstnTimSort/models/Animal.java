package com.aston.AstnTimSort.models;

public class Animal implements Comparable<Animal> {

	private final AnimalTypeEnum type;
	private final EyeColorEnum eyeColor;
	private final Boolean withWool;

	private Animal(AnimalTypeEnum type, EyeColorEnum eyeColor, Boolean withWool) {
		this.type = type;
		this.eyeColor = eyeColor;
		this.withWool = withWool;
	}

	public AnimalTypeEnum getType() {
		return type;
	}

	public EyeColorEnum getEyeColor() {
		return eyeColor;
	}

	public boolean isWithWool() {
		return withWool;
	}

	@Override
	public int compareTo(Animal o) {
		int result = type.compareTo(o.getType());
		if (result == 0)
			result = eyeColor.compareTo(o.getEyeColor());
		if (result == 0)
			result = withWool.compareTo(o.isWithWool());
		return result;
	}

	public enum AnimalTypeEnum {
		MAMMAL(true, true, true, true), SHELLFISH(false, true, false, true),
		FISH(true, true, false, true), BIRD(true, false, false, true),
		REPTILE(true, true, false, true), AMPHIBIAN(true, true, false, true),
		ARTHROPOD(true, true, false, true);

		private final boolean canHaveEyes;
		private final boolean canBeWithoutEyes;
		private final boolean canHavefur;
		private final boolean canBeWithoutFur;

		private AnimalTypeEnum(boolean canHaveEyes, boolean canBeWithoutEyes, boolean canHavefur,
				boolean canBeWithoutFur) {
			this.canHaveEyes = canHaveEyes;
			this.canBeWithoutEyes = canBeWithoutEyes;
			this.canHavefur = canHavefur;
			this.canBeWithoutFur = canBeWithoutFur;
		}

		public boolean canHaveEyes() {
			return canHaveEyes;
		}

		public boolean canBeWithoutEyes() {
			return canBeWithoutEyes;
		}

		public boolean canHasFur() {
			return canHavefur;
		}

		public boolean canBeWithoutFur() {
			return canBeWithoutFur;
		}
	}

	public enum EyeColorEnum {
		WITHOUT_EYE, BROWN, BLUE, GREEN, GRAY, BLACK, MIXED
	}

	@Override
	public String toString() {
		return "Animal [type=" + type + ", eyeColor=" + eyeColor + ", withFur=" + withWool + ']';
	}

	public static Builder getBuilder() {
		return new Builder();
	}

	public static class Builder {
		private AnimalTypeEnum type;
		private EyeColorEnum eyeColor;
		private Boolean withWool;

		public Builder setType(AnimalTypeEnum type) {
			this.type = type;
			return this;
		}

		public Builder setEyeColor(EyeColorEnum eyeColor) {
			this.eyeColor = eyeColor;
			return this;
		}

		public Builder setWool(Boolean withWool) {
			this.withWool = withWool;
			return this;
		}

		public Animal build() {
			if (!type.canHasFur() && withWool) {
				throw new IllegalArgumentException("This type cannot have fur");
			}

			if (!type.canHaveEyes() && (eyeColor != EyeColorEnum.WITHOUT_EYE)) {
				throw new IllegalArgumentException("This type cannot have eyes");
			}

			if (!type.canBeWithoutEyes() && (eyeColor == EyeColorEnum.WITHOUT_EYE)) {
				throw new IllegalArgumentException("This type cannot be without eyes");
			}
			
			if (!type.canBeWithoutFur() && !withWool) {
				throw new IllegalArgumentException("This type cannot be without eyes");
			}
			
			if (type == null || eyeColor == null || withWool == null) {
				throw new RuntimeException("Incomplete object fields initialization");
			}

			return new Animal(type, eyeColor, withWool);
		}
	}

}
