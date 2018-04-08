package justenoughpetroleum;

import flaxbeard.immersivepetroleum.api.crafting.DistillationRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;

@mezz.jei.api.JEIPlugin
public class JEIPlugin implements IModPlugin {
    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(new DistillationCategory(registry.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void register(IModRegistry registry) {
        registry.handleRecipes(DistillationRecipe.class, DistillationWrapper::new, DistillationCategory.UID);
        registry.addRecipes(DistillationRecipe.recipeList, DistillationCategory.UID);
    }
}
