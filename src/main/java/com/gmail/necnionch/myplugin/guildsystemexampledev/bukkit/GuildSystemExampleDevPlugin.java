package com.gmail.necnionch.myplugin.guildsystemexampledev.bukkit;

import com.gmail.necnionch.myplugin.guildsystem.bukkit.guild.Guild;
import com.gmail.necnionch.myplugin.guildsystem.bukkit.quest.GuildQuest;
import org.bukkit.plugin.java.JavaPlugin;

public final class GuildSystemExampleDevPlugin extends JavaPlugin {

    private final GuildQuest.Deserializer emptyBucketQuestDeserializer = id -> (id.equals(EmptyBucketQuest.ID)) ? this::createEmptyBucketQuest : null;


    @Override
    public void onEnable() {
        registerCustomQuests();
    }

    @Override
    public void onDisable() {
        unregisterCustomQuests();
    }


    public void registerCustomQuests() {
        GuildQuest.register(emptyBucketQuestDeserializer);
    }

    public void unregisterCustomQuests() {
        GuildQuest.unregister(emptyBucketQuestDeserializer);
    }

    private EmptyBucketQuest createEmptyBucketQuest(Guild guild) {
        return new EmptyBucketQuest(this, guild);
    }


}
