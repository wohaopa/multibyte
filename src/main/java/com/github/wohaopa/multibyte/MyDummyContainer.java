package com.github.wohaopa.multibyte;

import com.google.common.eventbus.EventBus;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;

import java.util.Arrays;

public class MyDummyContainer extends DummyModContainer {
    public MyDummyContainer()
    {
        super(new ModMetadata());
        ModMetadata meta = getMetadata();
        meta.modId = "multibyte";
        meta.name = "multibyte";
        meta.version = "1.1";
        meta.authorList = Arrays.asList("wohaopa");
        meta.description = "Extend the unicode set supported by Minecraft to support double Char unicode codes.";
        meta.credits = "";
        meta.url = "https://github.com/wohaopa/multibyte";
        meta.updateUrl = "https://github.com/wohaopa/multibyte/releases";
    }
    @Override
    public boolean registerBus(EventBus bus, LoadController controller)
    {
        return true;
    }


}
