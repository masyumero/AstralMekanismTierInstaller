package io.github.masyumero.ametierinstaller.mixin;

import astral_mekanism.block.blockentity.base.BlockEntityRecipeMachine;
import astral_mekanism.block.blockentity.basemachine.BETickWorkEnergizedSmelter;
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

@Mixin(value = BETickWorkEnergizedSmelter.class, remap = false)
public abstract class MixinBETickWorkEnergizedSmelter extends BlockEntityRecipeMachine<SmeltingRecipe> {

    @Shadow
    public abstract MachineEnergyContainer<?> getEnergyContainer();

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

    protected MixinBETickWorkEnergizedSmelter(IBlockProvider blockProvider, BlockPos pos, BlockState state, List<CachedRecipe.OperationTracker.RecipeError> errorTypes) {
        super(blockProvider, pos, state, errorTypes);
    }

    @Override
    public SmeltingUpgradeData getUpgradeData() {
        return new SmeltingUpgradeData(redstone, getControlType(), getEnergyContainer(), 0, gasMode, energySlot, infusionTank, inputSlot, outputSlot, getComponents());
    }
}
