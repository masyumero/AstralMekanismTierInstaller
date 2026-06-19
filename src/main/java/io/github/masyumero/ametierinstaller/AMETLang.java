package io.github.masyumero.ametierinstaller;

import astral_mekanism.AMEConstants;
import mekanism.api.text.ILangEntry;
import net.minecraft.Util;
import org.jetbrains.annotations.NotNull;

public enum AMETLang implements ILangEntry {
    DESCRIPTION_ASTRONOMICAL_MAX_TIER_INSTALLER("description", "astronomical_max_tier_installer");

    private final String key;

    AMETLang(String type, String path) {
        this(Util.makeDescriptionId(type, AMEConstants.rl(path)));
    }

    AMETLang(String key) {
        this.key = key;
    }

    @Override
    public @NotNull String getTranslationKey() {
        return key;
    }
}
