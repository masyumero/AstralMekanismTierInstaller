package io.github.masyumero.ametierinstaller.mixin;

import astral_mekanism.block.blockentity.compact.BECompactTEP;
import astral_mekanism.block.blockentity.elements.slot.paged.PagedEnergyInventorySlot;
import astral_mekanism.block.blockentity.elements.slot.paged.PagedOutputInventorySlot;
import io.github.masyumero.ametierinstaller.common.upgrade.TEPUpgradeData;
import mekanism.api.math.FloatingLong;
import mekanism.api.providers.IBlockProvider;
import mekanism.api.recipes.FluidToFluidRecipe;
import mekanism.api.recipes.cache.CachedRecipe;
import mekanism.common.capabilities.energy.MachineEnergyContainer;
import mekanism.common.capabilities.fluid.BasicFluidTank;
import mekanism.common.capabilities.heat.VariableHeatCapacitor;
import mekanism.common.inventory.slot.FluidInventorySlot;
import mekanism.common.tile.component.ITileComponent;
import mekanism.common.tile.prefab.TileEntityRecipeMachine;
import mekanism.common.upgrade.IUpgradeData;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(value = BECompactTEP.class, remap = false)
public abstract class MixinBECompactTEP extends TileEntityRecipeMachine<FluidToFluidRecipe> {

    @Shadow
    private PagedEnergyInventorySlot energySlot;

    @Shadow
    public VariableHeatCapacitor heatCapacitor;

    @Shadow
    public abstract void setEnergyUsageFromPacket(FloatingLong floatingLong);

    @Shadow
    public BasicFluidTank inputTank;

    @Shadow
    public BasicFluidTank outputTank;

    @Shadow
    private FluidInventorySlot inputInputSlot;

    @Shadow
    private FluidInventorySlot outputInputSlot;

    @Shadow
    private PagedOutputInventorySlot inputOutputSlot;

    @Shadow
    private PagedOutputInventorySlot outputOutputSlot;

    @Shadow
    public abstract MachineEnergyContainer<BECompactTEP> getEnergyContainer();

    @Shadow
    private MachineEnergyContainer<BECompactTEP> energyContainer;

    protected MixinBECompactTEP(IBlockProvider blockProvider, BlockPos pos, BlockState state, List<CachedRecipe.OperationTracker.RecipeError> errorTypes) {
        super(blockProvider, pos, state, errorTypes);
    }

    @Override
    public void parseUpgradeData(@NotNull IUpgradeData upgradeData) {
        if (upgradeData instanceof TEPUpgradeData data) {
            redstone = data.redstone;
            setControlType(data.controlType);
            getEnergyContainer().setEnergy(data.energyContainer.getEnergy());
            energySlot.deserializeNBT(data.energySlot.serializeNBT());
            heatCapacitor.deserializeNBT(data.heatCapacitor.serializeNBT());
            setEnergyUsageFromPacket(((MachineEnergyContainer<?>)data.energyContainer).getEnergyPerTick());
            inputTank.deserializeNBT(data.inputTank.serializeNBT());
            outputTank.deserializeNBT(data.outputTank.serializeNBT());
            inputInputSlot.deserializeNBT(data.inputInputSlot.serializeNBT());
            outputInputSlot.deserializeNBT(data.outputInputSlot.serializeNBT());
            inputOutputSlot.deserializeNBT(data.inputOutputSlot.serializeNBT());
            outputOutputSlot.deserializeNBT(data.outputOutputSlot.serializeNBT());
            for (ITileComponent component : getComponents()) {
                component.read(data.components);
            }
        } else {
            super.parseUpgradeData(upgradeData);
        }
    }

    @Override
    public TEPUpgradeData getUpgradeData() {
        return new TEPUpgradeData(redstone, getControlType(), energyContainer, heatCapacitor, inputTank, outputTank, inputInputSlot, outputInputSlot, energySlot, inputOutputSlot, outputOutputSlot, getComponents());
    }
}
