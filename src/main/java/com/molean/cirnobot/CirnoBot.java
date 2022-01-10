package com.molean.cirnobot;

import net.md_5.bungee.api.plugin.Plugin;

public final class CirnoBot extends Plugin {

    private static CirnoHandler cirnoHandler;

    private static CirnoBot cirnoBot;

    public static CirnoBot getPlugin() {
        return cirnoBot;
    }

    @Override
    public void onEnable() {
        cirnoBot = this;
        Robot.init();
    }

    @Override
    public void onDisable() {
        Robot.getBot().close();
    }

    public static void setCirnoHandler(CirnoHandler cirnoHandler) {
        CirnoBot.cirnoHandler = cirnoHandler;
    }

    public static CirnoHandler getCirnoHandler() {
        return cirnoHandler;
    }
}
