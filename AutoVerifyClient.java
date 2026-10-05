package com.example.autoverify;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.minecraft.client.MinecraftClient;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AutoVerifyClient implements ClientModInitializer {

    // Matches "/verify" followed by the code. Change [A-Za-z0-9]+ to [A-Za-z]+ for letters only.
    private static final Pattern VERIFY = Pattern.compile("/verify\\s+([A-Za-z0-9]+)");

    private String lastCode = "";
    private long lastSent = 0;

    @Override
    public void onInitializeClient() {
        // GAME = system/server messages (what most plugins use). Only these are
        // listened to, so other players can't trigger the mod by typing in chat.
        ClientReceiveMessageEvents.GAME.register((message, overlay) -> {
            if (overlay) return; // ignore action bar text
            handle(message.getString());
        });
    }

    private void handle(String text) {
        Matcher m = VERIFY.matcher(text);
        if (!m.find()) return;

        String code = m.group(1);
        long now = System.currentTimeMillis();

        // Don't re-send the same code repeatedly
        if (code.equals(lastCode) && now - lastSent < 10_000) return;
        lastCode = code;
        lastSent = now;

        MinecraftClient client = MinecraftClient.getInstance();
        client.execute(() -> {
            if (client.player != null && client.player.networkHandler != null) {
                // No leading slash for sendChatCommand
                client.player.networkHandler.sendChatCommand("verify " + code);
            }
        });
    }
}
