package io.github.masyumero.ametierinstaller.mixin;

import astral_mekanism.block.blockentity.base.BlockEntityRecipeFactory;
import astral_mekanism.block.blockentity.interf.IEnergizedMachine;
import mekanism.api.providers.IBlockProvider;
import mekanism.common.inventory.slot.EnergyInventorySlot;
import mekanism.common.tile.component.ITileComponent;
import mekanism.common.tile.prefab.TileEntityConfigurableMachine;
import mekanism.common.upgrade.IUpgradeData;
import mekanism.common.upgrade.MachineUpgradeData;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = BlockEntityRecipeFactory.class, remap = false)
public abstract class MixinBlockEntityRecipeFactory extends TileEntityConfigurableMachine implements IEnergizedMachine {

    @Shadow
    protected EnergyInventorySlot energySlot;

    public MixinBlockEntityRecipeFactory(IBlockProvider blockProvider, BlockPos pos, BlockState state) {
        super(blockProvider, pos, state);
    }

    @Override
    public void parseUpgradeData(@NotNull IUpgradeData upgradeData) {
        if (upgradeData instanceof MachineUpgradeData data) {
            redstone = data.redstone;
            setControlType(data.controlType);
            getEnergyContainer().setEnergy(data.energyContainer.getEnergy());
            energySlot.deserializeNBT(data.energySlot.serializeNBT());
            for (ITileComponent component : getComponents()) {
                component.read(data.components);
            }
        } else {
            super.parseUpgradeData(upgradeData);
        }
    }
}