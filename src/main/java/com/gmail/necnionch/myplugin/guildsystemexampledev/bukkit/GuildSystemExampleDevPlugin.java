package com.gmail.necnionch.myplugin.guildsystemexampledev.bukkit;

import com.gmail.necnionch.myplugin.guildsystem.bukkit.guild.Guild;
import com.gmail.necnionch.myplugin.guildsystem.bukkit.quest.GuildQuest;
import com.gmail.necnionch.myplugin.guildsystem.bukkit.reward.extension.RewardExtension;
import org.bukkit.plugin.java.JavaPlugin;

public final class GuildSystemExampleDevPlugin extends JavaPlugin {

    private final GuildQuest.Deserializer emptyBucketQuestDeserializer = id -> (id.equals(EmptyBucketQuest.ID)) ? this::createEmptyBucketQuest : null;
    private final RewardExtension.Deserializer fireworkRewardDeserializer = (name, args) -> (name.equals(FireworkReward.ID)) ? this.createFireworkReward() : null;


    @Override
    public void onEnable() {
        registerCustomQuests();
        registerCustomRewards();
    }

    @Override
    public void onDisable() {
        unregisterCustomQuests();
        unregisterCustomRewards();
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

    public void registerCustomRewards() {
        RewardExtension.register(FireworkReward.ID, fireworkRewardDeserializer);
    }

    public void unregisterCustomRewards() {
        RewardExtension.unregister(FireworkReward.ID, fireworkRewardDeserializer);
    }

    private FireworkReward createFireworkReward() {
        return new FireworkReward();
    }


}
