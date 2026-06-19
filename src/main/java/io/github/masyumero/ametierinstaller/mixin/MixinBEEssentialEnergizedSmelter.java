package io.github.masyumero.ametierinstaller.mixin;

import astral_mekanism.block.blockentity.base.BlockEntityProgressMachine;
import astral_mekanism.block.blockentity.normalmachine.BEEssentialEnergizedSmelter;
import io.github.masyumero.ametierinstaller.common.upgrade.SmeltingUpgradeData;
import mekanism.api.chemical.infuse.IInfusionTank;
import mekanism.api.providers.IBlockProvider;
import mekanism.api.recipes.cache.CachedRecipe;
import mekanism.common.capabilities.energy.MachineEnergyContainer;
import mekanism.common.inventory.slot.EnergyInventorySlot;
import mekanism.common.inventory.slot.InputInventorySlot;
import mekanism.common.inventory.slot.OutputInventorySlot;
import mekanism.common.tile.TileEntityChemicalTank;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(value = BEEssentialEnergizedSmelter.class, remap = false)
public abstract class MixinBEEssentialEnergizedSmelter extends BlockEntityProgressMachine<SmeltingRecipe> {

    @Shadow
    public abstract MachineEnergyContainer<BEEssentialEnergizedSmelter> getEnergyContainer();

    @Shadow
    private TileEntityChemicalTank.GasMode gasMode;

    @Shadow
    private EnergyInventorySlot energySlot;

    @Shadow
    private IInfusionTank infusionTank;

    @Shadow
    private InputInventorySlot inputSlot;

    @Shadow
    private OutputInventorySlot outputSlot;

    protected MixinBEEssentialEnergizedSmelter(IBlockProvider blockProvider, BlockPos pos, BlockState state, List<CachedRecipe.OperationTracker.RecipeError> errorTypes, int baseTicksRequired) {
        super(blockProvider, pos, state, errorTypes, baseTicksRequired);
    }

    @Override
    public SmeltingUpgradeData getUpgradeData() {
        return new SmeltingUpgradeData(redstone, getControlType(), getEnergyContainer(), getOperatingTicks(), gasMode, energySlot, infusionTank, inputSlot, outputSlot, getComponents());
    }
}
