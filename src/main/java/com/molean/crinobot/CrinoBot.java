package com.molean.crinobot;

import net.md_5.bungee.api.plugin.Plugin;

import java.util.Objects;

public final class CrinoBot extends Plugin {

    protected static CrinoHandler crinoHandler;

    private static CrinoBot crinoBot;

    public static CrinoBot getPlugin() {
        return crinoBot;
    }

    @Override
    public void onEnable() {
        crinoBot = this;
        Robot.init();
    }

    @Override
    public void onDisable() {
        Robot.getBot().close();
    }

    public static void setCrinoHandler(CrinoHandler crinoHandler) {
        CrinoBot.crinoHandler = crinoHandler;
    }

    public static void sendMessage(String message) {
        Objects.requireNonNull(Robot.getBot().getGroup(483653595L)).sendMessage(message);
    }
}
