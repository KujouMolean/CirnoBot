package com.molean.cirnobot;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.utils.BotConfiguration;
import net.md_5.bungee.api.ProxyServer;

public class Robot {
    private static Bot bot;

    public static Bot getBot() {
        return bot;
    }


    public static void init() {
        ProxyServer.getInstance().getScheduler().runAsync(CirnoBot.getPlugin(), () -> {
            bot = BotFactory.INSTANCE.newBot(1604249679, "123asd..", new BotConfiguration() {
                {
                    fileBasedDeviceInfo("deviceInfo.json");
                    int playerLimit = ProxyServer.getInstance().getConfig().getPlayerLimit();
                    if (playerLimit != 5) {
                        this.setProtocol(MiraiProtocol.ANDROID_PAD);
                    } else {
                        this.setProtocol(MiraiProtocol.ANDROID_PHONE);
                    }
                }
            });
            bot.login();
            bot.join();
        });
    }
}

