package net.grishka.testmod.item.custom;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.Nullable;

public class FualItem extends Item {
    private int burnTime = 0;
    public FualItem(Properties pProperties, int burnTime) {
        super(pProperties);
        this.burnTime=burnTime;
    }

    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
        return this.burnTime;
    }
}
