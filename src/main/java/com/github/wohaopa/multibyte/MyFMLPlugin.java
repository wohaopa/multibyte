package com.github.wohaopa.multibyte;

import java.util.Map;

import cpw.mods.fml.relauncher.FMLLaunchHandler;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

public class MyFMLPlugin implements IFMLLoadingPlugin {

    @Override
    public String[] getASMTransformerClass() {
        if (FMLLaunchHandler.side()
            .isClient()) return new String[] { "com.github.wohaopa.multibyte.MyTransformer" };
        else return null;
    }

    @Override
    public String getModContainerClass() {
        if (FMLLaunchHandler.side()
            .isClient()) return "com.github.wohaopa.multibyte.MyDummyContainer";
        else return null;
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {

    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
