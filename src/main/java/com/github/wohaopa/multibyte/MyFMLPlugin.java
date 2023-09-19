package com.github.wohaopa.multibyte;

import java.util.Map;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

public class MyFMLPlugin implements IFMLLoadingPlugin {

    @Override
    public String[] getASMTransformerClass() {
        return new String[] { "com.github.wohaopa.multibyte.MyTransformer" };
    }

    @Override
    public String getModContainerClass() {
        return null;
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
