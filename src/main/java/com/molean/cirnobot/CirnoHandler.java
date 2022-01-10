package com.molean.cirnobot;

import net.mamoe.mirai.message.data.MessageChain;

public interface CirnoHandler {
    void handler(long id, String nameCard, String message, MessageChain eventMessage);
}
