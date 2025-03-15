package com.is.data;

import com.is.data.abilities.AbstractAbility;
import com.is.data.abilities.AgilityAbility;
import com.is.data.abilities.SpeedAbility;
import com.is.data.abilities.StrengthAbility;

public enum DelphiAbilityType {

    AGILITY(100.0d, "is.abilities.agility.name", "is.abilities.agility.description", 71, 257, new AgilityAbility()),
    STRENGTH(130.0d, "is.abilities.strength.name", "is.abilities.strength.description", 36, 257, new StrengthAbility()),
    SPEED(200.0d, "is.abilities.speed.name", "is.abilities.speed.description", 1, 257, new SpeedAbility());

    public final double price;
    public final String nameTranslation;
    public final String descriptionTranslation;
    public final int xUVOffset;
    public final int yUVOffset;
    public final AbstractAbility ability;

    DelphiAbilityType(double price, String nameTranslation, String descriptionTranslation, int xUVOffset, int yUVOffset, AbstractAbility ability) {
        this.price = price;
        this.nameTranslation = nameTranslation;
        this.descriptionTranslation = descriptionTranslation;
        this.xUVOffset = xUVOffset;
        this.yUVOffset = yUVOffset;
        this.ability = ability;
    }

}
