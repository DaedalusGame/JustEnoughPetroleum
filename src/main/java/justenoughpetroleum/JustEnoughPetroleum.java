package justenoughpetroleum;

import com.google.common.collect.BiMap;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

@Mod(modid = justenoughpetroleum.JustEnoughPetroleum.MODID, acceptedMinecraftVersions = "[1.12, 1.13)", clientSideOnly = true, dependencies = "required-after:immersivepetroleum;")
@Mod.EventBusSubscriber
public class JustEnoughPetroleum
{
    public static final String MODID = "justenoughpetroleum";
    public static final String NAME = "JustEnoughPetroleum";
}
