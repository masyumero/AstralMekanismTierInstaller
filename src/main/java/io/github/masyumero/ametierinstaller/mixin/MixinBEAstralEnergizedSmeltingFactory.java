package io.github.masyumero.ametierinstaller.mixin;

import astral_mekanism.block.blockentity.astralfactory.BEAstralEnergizedSmeltingFactory;
import astral_mekanism.block.blockentity.base.BlockEntityRecipeFactory;
import astral_mekanism.block.blockentity.elements.slot.paged.PagedInputInventorySlot;
import astral_mekanism.block.blockentity.elements.slot.paged.PagedOutputInventorySlot;
import io.github.masyumero.ametierinstaller.common.upgrade.SmeltingUpgradeData;
import mekanism.api.chemical.infuse.IInfusionTank;
import mekanism.api.providers.IBlockProvider;
import mekanism.api.recipes.cache.CachedRecipe;
import mekanism.common.capabilities.energy.MachineEnergyContainer;
import mekanism.common.tile.TileEntityChemicalTank;
import mekanism.common.upgrade.IUpgradeData;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;
import java.util.Set;

@Mixin(value = BEAstralEnergizedSmeltingFactory.class, remap = false)
public abstract class MixinBEAstralEnergizedSmeltingFactory extends BlockEntityRecipeFactory<SmeltingRecipe, BEAstralEnergizedSmeltingFactory> {

    @Shadow
    private IInfusionTank infusionTank;

    @Shadow
    private TileEntityChemicalTank.GasMode gasMode;

    @Shadow
    private PagedInputInventorySlot[] inputSlots;

    @Shadow
    private PagedOutputInventorySlot[] outputSlots;

    @Shadow
    public abstract MachineEnergyContainer<BEAstralEnergizedSmeltingFactory> getEnergyContainer();

    protected MixinBEAstralEnergizedSmeltingFactory(IBlockProvider blockProvider, BlockPos pos, BlockState state, List<CachedRecipe.OperationTracker.RecipeError> errorTypes, Set<CachedRecipe.OperationTracker.RecipeError> globalErrorTypes) {
        super(blockProvider, pos, state, errorTypes, globalErrorTypes);
    }

    @Override
    public void parseUpgradeData(@NotNull IUpgradeData upgradeData) {
        if (upgradeData instanceof SmeltingUpgradeData data) {

            super.parseUpgradeData(data);

            infusionTank.deserializeNBT(data.stored.serializeNBT());
            gasMode = data.gasMode;
            for (int i = 0; i < data.inputSlots.size(); i++) {
                inputSlots[i].deserializeNBT(data.inputSlots.get(i).serializeNBT());
            }
            for (int i = 0; i < data.outputSlots.size(); i++) {
                outputSlots[i].setStack(data.outputSlots.get(i).getStack());
            }
        } else {
            super.parseUpgradeData(upgradeData);
        }
    }

    @Override
    public SmeltingUpgradeData getUpgradeData() {
        return new SmeltingUpgradeData(redstone, getControlType(), getEnergyContainer(), new int[] {0}, gasMode, energySlot, infusionTank, List.of(inputSlots), List.of(outputSlots), false, getComponents());
    }
}
