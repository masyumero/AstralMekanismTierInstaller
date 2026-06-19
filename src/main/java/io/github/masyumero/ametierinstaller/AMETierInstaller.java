package io.github.masyumero.ametierinstaller;

import com.mojang.logging.LogUtils;
import io.github.masyumero.ametierinstaller.common.registries.AMETItems;
import io.github.masyumero.ametierinstaller.common.upgrade.UpgradeableMachines;
import io.github.masyumero.ametierinstaller.common.utils.AMETUpgradeableUtils;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(AMETierInstaller.MODID)
public class AMETierInstaller {

    public static final String MODID = "ametierinstaller";
    private static final Logger LOGGER = LogUtils.getLogger();

    @SuppressWarnings("removal")
    public AMETierInstaller() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
        UpgradeableMachines.init();
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        AMETUpgradeableUtils.applyDeferredAttributeUpgradeable();
    }
}

