package com.lunarbreaker.api;

import org.bukkit.plugin.java.JavaPlugin;

public class LunarBreakerPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        new LunarBreakerAPI(this);
    }
}
