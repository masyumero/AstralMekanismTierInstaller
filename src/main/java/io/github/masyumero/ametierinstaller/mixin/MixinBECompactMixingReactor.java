package io.github.masyumero.ametierinstaller.mixin;

import astral_mekanism.block.blockentity.compact.BECompactMixingReactor;
import io.github.masyumero.ametierinstaller.common.upgrade.MixingReactorUpgradeData;
import mekanism.api.chemical.gas.IGasTank;
import mekanism.api.heat.IHeatCapacitor;
import mekanism.api.providers.IBlockProvider;
import mekanism.common.capabilities.energy.BasicEnergyContainer;
import mekanism.common.capabilities.fluid.BasicFluidTank;
import mekanism.common.tile.component.ITileComponent;
import mekanism.common.tile.prefab.TileEntityConfigurableMachine;
import mekanism.common.upgrade.IUpgradeData;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = BECompactMixingReactor.class, remap = false)
public abstract class MixinBECompactMixingReactor extends TileEntityConfigurableMachine {

    @Shadow
    public abstract void setInjectionRate(long rate);

    @Shadow
    public IHeatCapacitor heatCapacitor;

    @Shadow
    protected IGasTank leftFuelTank;

    @Shadow
    protected IGasTank mixedFuelTank;

    @Shadow
    protected IGasTank rightFuelTank;

    @Shadow
    protected BasicFluidTank waterTank;

    @Shadow
    protected IGasTank steamTank;

    @Shadow
    protected BasicEnergyContainer energyContainer;

    @Shadow
    public abstract long getInjectionRate();

    public MixinBECompactMixingReactor(IBlockProvider blockProvider, BlockPos pos, BlockState state) {
        super(blockProvider, pos, state);
    }

    @Override
    public void parseUpgradeData(@NotNull IUpgradeData upgradeData) {
        if (upgradeData instanceof MixingReactorUpgradeData data) {
            redstone = data.redstone;
            setControlType(data.controlType);
            setInjectionRate(data.injectionRate);
            heatCapacitor.deserializeNBT(data.heatCapacitor.serializeNBT());
            leftFuelTank.deserializeNBT(data.leftFuelTank.serializeNBT());
            mixedFuelTank.deserializeNBT(data.mixedFuelTank.serializeNBT());
            rightFuelTank.deserializeNBT(data.rightFuelTank.serializeNBT());
            waterTank.deserializeNBT(data.waterTank.serializeNBT());
            steamTank.deserializeNBT(data.steamTank.serializeNBT());
            for (ITileComponent component : getComponents()) {
                component.read(data.components);
            }
        } else {
            super.parseUpgradeData(upgradeData);
        }
    }

    @Override
    public MixingReactorUpgradeData getUpgradeData() {
        return new MixingReactorUpgradeData(redstone, getControlType(), energyContainer, getInjectionRate(), heatCapacitor, leftFuelTank, mixedFuelTank, rightFuelTank, waterTank, steamTank, getComponents());
    }
}
