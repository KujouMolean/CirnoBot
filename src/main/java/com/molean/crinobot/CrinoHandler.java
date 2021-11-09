package com.molean.crinobot;

import net.mamoe.mirai.message.data.MessageChain;

public interface CrinoHandler {
    void handler(long id, String nameCard, String message, MessageChain eventMessage);
}
