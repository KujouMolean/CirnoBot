package com.molean.crinobot;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MemberJoinRequestEvent;
import net.mamoe.mirai.message.data.*;
import net.mamoe.mirai.utils.BotConfiguration;
import net.md_5.bungee.api.ProxyServer;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Robot {
    private static Bot bot;

    public static Bot getBot() {
        return bot;
    }

    private static String getText(QuoteReply quoteReply) {
        MessageChain originalMessage = quoteReply.getSource().getOriginalMessage();
        String originText = originalMessage.contentToString();
        Pattern pattern = Pattern.compile("<([a-zA-Z0-9_]{3,18})> .*");
        Matcher matcher = pattern.matcher(originText);
        if (matcher.matches()) {
            String group = matcher.group(1);
            System.out.println(group);
            return "@" + group + " ";
        } else {
            return quoteReply.contentToString();
        }
    }

    public static void init() {
        ProxyServer.getInstance().getScheduler().runAsync(CrinoBot.getPlugin(), () -> {
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
            bot.getEventChannel().registerListenerHost(new SimpleListenerHost() {
                @EventHandler
                public void onMemberJoinRequest(MemberJoinRequestEvent event) {
                    String message = event.getMessage();
                    if (message.contains("梦幻之屿")) {
                        event.accept();
                    } else {
                        event.reject(false, "答案错误");
                    }
                }
                @EventHandler
                public void onGroupMessage(GroupMessageEvent event) {
                    if (event.getGroup().getId() != 483653595) {
                        return;
                    }
                    if (Calendar.getInstance().getTimeInMillis() / 1000 - event.getTime() > 30) {
                        return;
                    }

                    boolean hasQuote = false;
                    for (SingleMessage singleMessage : event.getMessage()) {
                        if (singleMessage instanceof QuoteReply) {
                            hasQuote = true;
                        }
                    }

                    String nameCard = event.getSender().getNameCard();
                    long id = event.getSender().getId();
                    StringBuilder plainMessage = new StringBuilder();
                    MessageChain rawMessage = event.getMessage();
                    for (SingleMessage singleMessage : rawMessage) {
                        String subMessage;
                        if (singleMessage instanceof QuoteReply) {
                            subMessage = getText((QuoteReply) singleMessage);
                        } else if (hasQuote && singleMessage instanceof At && ((At) singleMessage).getTarget() == bot.getId()) {
                            subMessage = "";
                        } else if (singleMessage instanceof At) {
                            subMessage = singleMessage.contentToString();
                        } else if (singleMessage instanceof PlainText) {
                            subMessage = singleMessage.contentToString();
                        } else if (singleMessage instanceof Image) {
                            subMessage = singleMessage.contentToString();
                        } else if (singleMessage instanceof Voice) {
                            subMessage = singleMessage.contentToString();
                        } else if (singleMessage instanceof Face) {
                            subMessage = singleMessage.contentToString();
                        } else {
                            subMessage = "";
                        }
                        plainMessage.append(subMessage);
                    }
                    if (CrinoBot.crinoHandler != null) {
                        CrinoBot.crinoHandler.handler(id, nameCard, plainMessage.toString(),event.getMessage());
                    }
                }
            });
            bot.join();
        });
    }
}

