package io.github.masyumero.ametierinstaller.common.upgrade;

import astral_mekanism.AMETier;
import astral_mekanism.registries.AMEMachines;
import io.github.masyumero.ametierinstaller.common.utils.AMETEnumUtils;

import static io.github.masyumero.ametierinstaller.common.utils.AMETUpgradeableUtils.addDeferredAttributeUpgradeable;

public class UpgradeableMachines {

    public static void init() {
        for (AMETier tier : AMETEnumUtils.AME_TIERS) {
            addDeferredAttributeUpgradeable(() -> tier, AMEMachines.ASTRAL_ENERGIZED_SMELTING_FACTRIES::get);
            addDeferredAttributeUpgradeable(() -> tier, AMEMachines.ENERGIZED_SMELTING_FACTORIES::get);
            addDeferredAttributeUpgradeable(() -> tier, AMEMachines.COMPACT_TEP::get);
            addDeferredAttributeUpgradeable(() -> tier, AMEMachines.COMPACT_FIR::get);
            addDeferredAttributeUpgradeable(() -> tier, AMEMachines.COMPACT_FUSION_REACTOR::get);
            addDeferredAttributeUpgradeable(() -> tier, AMEMachines.COMPACT_NAQUADAH_REACTOR::get);
        }
        addDeferredAttributeUpgradeable(() -> null, AMEMachines.ASTRAL_ENERGIZED_SMELTER::getBlockType, (tier) -> AMEMachines.ASTRAL_ENERGIZED_SMELTING_FACTRIES.get(AMETier.ESSENTIAL));
        addDeferredAttributeUpgradeable(() -> null, AMEMachines.ESSENTIAL_ENERGIZED_SMELTER::getBlockType, (tier) -> AMEMachines.ENERGIZED_SMELTING_FACTORIES.get(AMETier.ESSENTIAL));
    }
}
