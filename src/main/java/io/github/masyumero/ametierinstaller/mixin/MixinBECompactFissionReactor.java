package io.github.masyumero.ametierinstaller.mixin;

import astral_mekanism.block.blockentity.compact.BECompactFissionReactor;
import io.github.masyumero.ametierinstaller.common.upgrade.FissionUpgradeData;
import mekanism.api.chemical.gas.IGasTank;
import mekanism.api.providers.IBlockProvider;
import mekanism.common.capabilities.fluid.BasicFluidTank;
import mekanism.common.capabilities.heat.BasicHeatCapacitor;
import mekanism.common.inventory.slot.FluidInventorySlot;
import mekanism.common.inventory.slot.chemical.GasInventorySlot;
import mekanism.common.tile.component.ITileComponent;
import mekanism.common.tile.prefab.TileEntityConfigurableMachine;
import mekanism.common.upgrade.IUpgradeData;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = BECompactFissionReactor.class, remap = false)
public abstract class MixinBECompactFissionReactor extends TileEntityConfigurableMachine {

    @Shadow
    private long efficiency;

    @Shadow
    private BasicHeatCapacitor heatCapacitor;

    @Shadow
    private IGasTank fissionFuelTank;

    @Shadow
    private IGasTank nuclearWasteTank;

    @Shadow
    private BasicFluidTank coolantFluidTank;

    @Shadow
    private IGasTank coolantGasTank;

    @Shadow
    private IGasTank heatedFluidCoolantGasTank;

    @Shadow
    private IGasTank heatedGasCoolantGasTank;

    @Shadow
    private GasInventorySlot nuclearWasteSlot;

    @Shadow
    private FluidInventorySlot fluidCoolantSlot;

    @Shadow
    private GasInventorySlot fissionFuelSlot;

    @Shadow
    private GasInventorySlot gasCoolantSlot;

    @Shadow
    private GasInventorySlot heatedFluidSlot;

    @Shadow
    private GasInventorySlot heatedGasSlot;

    @Shadow
    public abstract long getEfficiency();

    public MixinBECompactFissionReactor(IBlockProvider blockProvider, BlockPos pos, BlockState state) {
        super(blockProvider, pos, state);
    }

    @Override
    public void parseUpgradeData(@NotNull IUpgradeData upgradeData) {
        if (upgradeData instanceof FissionUpgradeData data) {
            redstone = data.redstone;
            setControlType(data.controlType);
            efficiency = data.efficiency;
            heatCapacitor.deserializeNBT(data.heatCapacitor.serializeNBT());
            fissionFuelTank.deserializeNBT(data.fissionFuelTank.serializeNBT());
            nuclearWasteTank.deserializeNBT(data.nuclearWasteTank.serializeNBT());
            coolantFluidTank.deserializeNBT(data.coolantFluidTank.serializeNBT());
            coolantGasTank.deserializeNBT(data.coolantGasTank.serializeNBT());
            heatedFluidCoolantGasTank.deserializeNBT(data.heatedFluidCoolantGasTank.serializeNBT());
            heatedGasCoolantGasTank.deserializeNBT(data.heatedGasCoolantGasTank.serializeNBT());
            fissionFuelSlot.deserializeNBT(data.fissionFuelSlot.serializeNBT());
            nuclearWasteSlot.deserializeNBT(data.nuclearWasteSlot.serializeNBT());
            fluidCoolantSlot.deserializeNBT(data.fluidCoolantSlot.serializeNBT());
            gasCoolantSlot.deserializeNBT(data.gasCoolantSlot.serializeNBT());
            heatedFluidSlot.deserializeNBT(data.heatedFluidSlot.serializeNBT());
            heatedGasSlot.deserializeNBT(data.heatedGasSlot.serializeNBT());
            for (ITileComponent component : getComponents()) {
                component.read(data.components);
            }
        } else {
            super.parseUpgradeData(upgradeData);
        }
    }

    @Override
    public FissionUpgradeData getUpgradeData() {
        return new FissionUpgradeData(redstone, getControlType(), getEfficiency(), heatCapacitor, fissionFuelTank, nuclearWasteTank, coolantFluidTank, coolantGasTank, heatedFluidCoolantGasTank, heatedGasCoolantGasTank, fissionFuelSlot, nuclearWasteSlot, fluidCoolantSlot, gasCoolantSlot, heatedFluidSlot, heatedGasSlot, getComponents());
    }
}
