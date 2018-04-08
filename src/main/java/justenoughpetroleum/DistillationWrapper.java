package justenoughpetroleum;

import com.google.common.collect.Lists;
import flaxbeard.immersivepetroleum.api.crafting.DistillationRecipe;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.util.Translator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DistillationWrapper implements IRecipeWrapper {
    public static final float INVALID_CHANCE = -1;
    public DistillationRecipe recipe;

    public DistillationWrapper(DistillationRecipe recipe) {
        this.recipe = recipe;
    }

    public int getEnergyPerTick() {
        return recipe.getTotalProcessEnergy() / recipe.getTotalProcessTime();
    }

    public int getTime() {
        return recipe.getTotalProcessTime();
    }

    public FluidStack getInput() {
        return recipe.input.copy();
    }

    public List<FluidStack> getFluidOutputs() {
        return Lists.newArrayList(recipe.fluidOutput);
    }

    public List<ItemStack> getOutputs() {
        return IntStream.range(0, recipe.itemOutput.length).mapToObj(i -> markChance(recipe.itemOutput[i], getChance(i))).collect(Collectors.toList());
    }

    private float getChance(int i) {
        return i < recipe.chances.length ? recipe.chances[i] : INVALID_CHANCE;
    }

    private ItemStack markChance(ItemStack stack, float chance) {
        stack = stack.copy();
        NBTTagCompound compound = new NBTTagCompound();
        compound.setFloat("chance", chance);
        stack.setTagCompound(compound);
        return stack;
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        /*int foregroundcolor = new Color(150, 150, 150).getRGB();
        int backgroundcolor = new Color(20, 20, 20).getRGB();

        String energyString = Translator.translateToLocalFormatted("jei.distillation.energy_cost",getEnergyPerTick());

        int energyX = 104 - minecraft.fontRenderer.getStringWidth(energyString) / 2;
        int energyY = 60;
        drawShadowed(minecraft, foregroundcolor, backgroundcolor, energyX, energyY, energyString);*/
    }

    private void drawShadowed(Minecraft minecraft, int foregroundcolor, int backgroundcolor, int drawoffsetX, int drawoffsetY, String costString) {
        minecraft.fontRenderer.drawString(costString, drawoffsetX, drawoffsetY + 1, backgroundcolor);
        minecraft.fontRenderer.drawString(costString, drawoffsetX + 1, drawoffsetY + 1, backgroundcolor);
        minecraft.fontRenderer.drawString(costString, drawoffsetX + 1, drawoffsetY + 1, backgroundcolor);
        minecraft.fontRenderer.drawString(costString, drawoffsetX, drawoffsetY, foregroundcolor);
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setOutputs(ItemStack.class, getOutputs());
        ingredients.setOutputs(FluidStack.class, getFluidOutputs());
        ingredients.setInput(FluidStack.class, getInput());
    }
}
