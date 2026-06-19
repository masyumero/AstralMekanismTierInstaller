package io.github.masyumero.ametierinstaller.mixin;

import astral_mekanism.AMETier;
import astral_mekanism.registries.AMEItems;
import io.github.masyumero.ametierinstaller.common.item.AMEItemMaxTierInstaller;
import io.github.masyumero.ametierinstaller.common.item.AMEItemTierInstaller;
import mekanism.common.registration.impl.ItemDeferredRegister;
import mekanism.common.registration.impl.ItemRegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(value = AMEItems.class, remap = false)
public abstract class MixinAMEItems {

    @Shadow
    @Final
    public static ItemDeferredRegister ITEMS;

    @Unique
    private static final ItemRegistryObject<AMEItemTierInstaller> ESSENTIAL_TIER_INSTALLER = ameTierInstaller$registerInstaller(null, AMETier.ESSENTIAL);
    @Unique
    private static final ItemRegistryObject<AMEItemTierInstaller> BASIC_STANDARD_TIER_INSTALLER = ameTierInstaller$registerInstaller(AMETier.ESSENTIAL, AMETier.BASIC);
    @Unique
    private static final ItemRegistryObject<AMEItemTierInstaller> ADVANCED_TIER_INSTALLER = ameTierInstaller$registerInstaller(AMETier.BASIC, AMETier.ADVANCED);
    @Unique
    private static final ItemRegistryObject<AMEItemTierInstaller> ELITE_TIER_INSTALLER = ameTierInstaller$registerInstaller(AMETier.ADVANCED, AMETier.ELITE);
    @Unique
    private static final ItemRegistryObject<AMEItemTierInstaller> ENCHANTED_ULTIMATE_TIER_INSTALLER = ameTierInstaller$registerInstaller(AMETier.ELITE, AMETier.ULTIMATE);
    @Unique
    private static final ItemRegistryObject<AMEItemTierInstaller> ABSOLUTE_OVERCLOCKED_TIER_INSTALLER = ameTierInstaller$registerInstaller(AMETier.ULTIMATE, AMETier.ABSOLUTE);
    @Unique
    private static final ItemRegistryObject<AMEItemTierInstaller> SUPREME_QUANTUM_TIER_INSTALLER = ameTierInstaller$registerInstaller(AMETier.ABSOLUTE, AMETier.SUPREME);
    @Unique
    private static final ItemRegistryObject<AMEItemTierInstaller> COSMIC_DENSE_TIER_INSTALLER = ameTierInstaller$registerInstaller(AMETier.SUPREME, AMETier.COSMIC);
    @Unique
    private static final ItemRegistryObject<AMEItemTierInstaller> INFINITE_MULTIVERSAL_TIER_INSTALLER = ameTierInstaller$registerInstaller(AMETier.COSMIC, AMETier.INFINITE);
    @Unique
    private static final ItemRegistryObject<AMEItemTierInstaller> ASTRONOMICAL_TIER_INSTALLER = ameTierInstaller$registerInstaller(AMETier.INFINITE, AMETier.ASTRAL);
    @Unique
    private static final ItemRegistryObject<AMEItemMaxTierInstaller> ASTRONOMICAL_MAX_TIER_INSTALLER = ITEMS.register("astronomical_max_tier_installer", AMEItemMaxTierInstaller::new);

    @Unique
    private static ItemRegistryObject<AMEItemTierInstaller> ameTierInstaller$registerInstaller(@Nullable AMETier fromTier, @NotNull AMETier toTier) {
        return ITEMS.register(toTier.nameForAstral + "_tier_installer", properties -> new AMEItemTierInstaller(fromTier, toTier, properties));
    }
}
