package justenoughpetroleum;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.*;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.util.Translator;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DistillationCategory implements IRecipeCategory<DistillationWrapper> {
    public static final String UID = "immersivepetroleum.distillation";
    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawable gauge;
    private final ResourceLocation location = new ResourceLocation(JustEnoughPetroleum.MODID, "textures/gui/distillation.png");

    public DistillationCategory(IGuiHelper helper) {
        background = helper.createDrawable(location, 0, 0, 133, 76);
        icon = helper.createDrawable(location, 134, 51, 16, 16);
        gauge = helper.createDrawable(location, 136, 2, 16, 47);
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return Translator.translateToLocal("tile.immersivepetroleum.metal_multiblock.distillation_tower.name");
    }

    @Override
    public String getModName() {
        return JustEnoughPetroleum.NAME;
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Nullable
    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, DistillationWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
        IGuiFluidStackGroup fluids = recipeLayout.getFluidStacks();

        List<FluidStack> fluidOutputs = recipeWrapper.getFluidOutputs();
        FluidStack fluidInput = recipeWrapper.getInput();
        List<List<ItemStack>> itemOutputs = splitIntoBoxes(recipeWrapper.getOutputs(),4);

        stacks.addTooltipCallback((slotIndex, input, ingredient, tooltip) -> {
            NBTTagCompound compound = ingredient.getTagCompound();
            if(compound != null) {
                float chance = compound.getFloat("chance");
                tooltip.add(1, TextFormatting.LIGHT_PURPLE+""+TextFormatting.BOLD+Translator.translateToLocalFormatted("jei.distillation.chance",chance*100));
            }
        });

        int totalFluid = Math.max(fluidInput.amount,fluidOutputs.stream().mapToInt(fluid -> fluid.amount).sum());
        int fluidIndex = 0;
        int barHeight = 47;
        fluids.init(fluidIndex,true,11,21,16,barHeight,totalFluid,false,gauge);
        fluids.set(fluidIndex++,fluidInput);
        float y = barHeight;
        for (FluidStack output : fluidOutputs) {
            float height = ((float)output.amount / (float)totalFluid)* barHeight;
            y -= height;
            fluids.init(fluidIndex,false,61,21+Math.round(y),16,Math.round(height),output.amount,false,null);
            fluids.set(fluidIndex++,output);
        }

        for(int i = 0; i < 2; i++)
            for(int j = 0; j < 2; j++) {
                stacks.init(i*2 + j, false, 86+j*18, 19+i*18);
            }

        int index = 0;
        for(List<ItemStack> output : itemOutputs)
            stacks.set(index++,output);
    }

    public static <T> List<List<T>> splitIntoBoxes(List<T> stacks, int boxes) {
        ArrayList<List<T>> splitStacks = new ArrayList<>();
        for (int i = 0; i < boxes; i++) {
            final int finalI = i;
            splitStacks.add(IntStream.range(0, stacks.size()).filter(index -> index % boxes == finalI).mapToObj(stacks::get).collect(Collectors.toList()));
        }
        return splitStacks;
    }
}
