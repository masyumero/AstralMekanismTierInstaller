package io.github.masyumero.ametierinstaller.common.utils;

import astral_mekanism.AMETier;
import astral_mekanism.registration.MachineRegistryObject;
import io.github.masyumero.ametierinstaller.common.block.attribute.AMEAttributeUpgradeable;
import mekanism.common.content.blocktype.BlockType;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class AMETUpgradeableUtils {

    private static void addAttributeUpgradeable(BlockType type, MachineRegistryObject<?, ?, ?, ?> upgradeBlock) {
        type.add(new AMEAttributeUpgradeable(upgradeBlock::getBlockObject));
    }

    private static void addAttributeUpgradeable(AMETier tier, Function<AMETier, MachineRegistryObject<?, ?, ?, ?>> upgradeBlock) {
        if (tier.ordinal() < AMETier.values().length - 1) {
            upgradeBlock.apply(tier).getBlockType().add(new AMEAttributeUpgradeable(() -> upgradeBlock.apply(AMETEnumUtils.AME_TIERS[tier.ordinal() + 1]).getBlockObject()));
        }
    }

    private static final List<BlockUpgradeBlock> BLOCK_UPGRADE_BLOCKS = new ArrayList<>();

    public static void addDeferredAttributeUpgradeable(Supplier<@Nullable AMETier> tier, Supplier<BlockType> block, Function<AMETier, MachineRegistryObject<?, ?, ?, ?>> upgradeBlock) {
        BLOCK_UPGRADE_BLOCKS.add(new BlockUpgradeBlock(tier, block, upgradeBlock));
    }

    public static void addDeferredAttributeUpgradeable(Supplier<@Nullable AMETier> tier, Function<AMETier, MachineRegistryObject<?, ?, ?, ?>> upgradeBlock) {
        BLOCK_UPGRADE_BLOCKS.add(new BlockUpgradeBlock(tier, () -> null, upgradeBlock));
    }

    public static void applyDeferredAttributeUpgradeable() {
        BLOCK_UPGRADE_BLOCKS.forEach(blockUpgradeBlock -> {
            if (blockUpgradeBlock.tier.get() == null) {
                addAttributeUpgradeable(blockUpgradeBlock.block.get(), blockUpgradeBlock.upgradeBlock.apply(null));
            } else {
                addAttributeUpgradeable(blockUpgradeBlock.tier.get(), blockUpgradeBlock.upgradeBlock);
            }
        });
    }

    private record BlockUpgradeBlock(Supplier<@Nullable AMETier> tier, Supplier<@Nullable BlockType> block, Function<@Nullable AMETier, MachineRegistryObject<?, ?, ?, ?>> upgradeBlock) {}
}
