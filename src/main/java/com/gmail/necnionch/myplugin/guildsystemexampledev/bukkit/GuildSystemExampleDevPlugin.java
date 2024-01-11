package com.gmail.necnionch.myplugin.guildsystemexampledev.bukkit;

import com.gmail.necnionch.myplugin.guildsystem.bukkit.guild.Guild;
import com.gmail.necnionch.myplugin.guildsystem.bukkit.quest.GuildQuest;
import com.gmail.necnionch.myplugin.guildsystem.bukkit.reward.extension.RewardExtension;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class GuildSystemExampleDevPlugin extends JavaPlugin {

    public static final String GUILD_QUEST_FOLDER = "guildquests";
    public static final String EXTENSION_ID = "ExampleDev";

    private final GuildQuest.Deserializer emptyBucketQuestDeserializer = id -> (id.equals(EmptyBucketQuest.ID)) ? this::createEmptyBucketQuest : null;
    private final RewardExtension.Deserializer fireworkRewardDeserializer = (name, args) -> (name.equals(FireworkReward.NAME)) ? this.createFireworkReward() : null;


    @Override
    public void onEnable() {
        new File(getDataFolder(), GUILD_QUEST_FOLDER).mkdirs();

        // ギルドシステムの初期化の前に登録する必要があるので、
        // plugin.yml にて load: STARTUP を指定する必要がある
        registerCustomQuests();
        registerCustomRewards();
    }

    @Override
    public void onDisable() {
        unregisterCustomQuests();
        unregisterCustomRewards();
    }


    /**
     * 設定例:
     * plugins/LOGuildSystem/config.yml
     *   guild-quests:
     *     - emptyBucket
     */
    public void registerCustomQuests() {
        GuildQuest.register(emptyBucketQuestDeserializer);
    }

    public void unregisterCustomQuests() {
        GuildQuest.unregister(emptyBucketQuestDeserializer);
    }

    private EmptyBucketQuest createEmptyBucketQuest(Guild guild) {
        return new EmptyBucketQuest(this, guild);
    }

    /**
     * 設定例:
     * plugins/LOGuildSystem/config.yml
     *   rank-rewards:
     *     "*":
     *       extensions:
     *         - ExampleDev:simpleFirework
     */
    public void registerCustomRewards() {
        RewardExtension.register(EXTENSION_ID, fireworkRewardDeserializer);
    }

    public void unregisterCustomRewards() {
        RewardExtension.unregister(EXTENSION_ID, fireworkRewardDeserializer);
    }

    private FireworkReward createFireworkReward() {
        return new FireworkReward();
    }


}
