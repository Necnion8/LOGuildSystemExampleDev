package com.gmail.necnionch.myplugin.guildsystemexampledev.bukkit;

import com.gmail.necnionch.myplugin.guildsystem.bukkit.guild.Guild;
import com.gmail.necnionch.myplugin.guildsystem.bukkit.quest.GuildQuest;
import com.gmail.necnionch.myplugin.guildsystem.bukkit.quest.errors.QuestLoadError;
import com.gmail.necnionch.myplugin.guildsystem.common.BukkitConfigDriver;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.jetbrains.annotations.Nullable;

public class EmptyBucketQuest extends GuildQuest implements Listener {

    private final GuildSystemExampleDevPlugin plugin;  // リスナー登録のためにプラグインインスタンスを設定しておく

    public static final String ID = "emptyBucket";  // クエストID
    public static final int TOTAL_COUNT = 5;  // 目標の数
    private final Config config;
    private int count;


    public EmptyBucketQuest(GuildSystemExampleDevPlugin plugin, Guild guild) {
        super(guild, ID);
        // 変数の初期化
        this.plugin = plugin;
        this.config = new Config(guild);
    }


    @EventHandler(ignoreCancelled = true)
    public void onEmpty(PlayerBucketEmptyEvent event) {
        Player player = event.getPlayer();

        if (getGuild().isJoined(player.getUniqueId())) {
            count++;

            if (count == TOTAL_COUNT) {
                updateTier(1);
            }
        }
    }


    @Override
    public void onLoad() throws QuestLoadError {
        // ロード処理

        // ファイル読み込み
        config.load();
        count = config.getCount();

        // イベントリスナーの登録
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public void onUnload() {
        // アンロード処理

        // ファイル保存
        config.setCount(count);
        config.save();

        // イベントリスナーの登録解除
        HandlerList.unregisterAll(this);
    }


    @Override
    public int getCurrentTier() {
        return count <= TOTAL_COUNT ? 1 : 0;  // 達成したTier数。これが getMaxTier() まで達したら完全完了と見なされる。
    }

    @Override
    public int getMaxTier() {
        return 1;  // このクエストの最大Tier数
    }

    @Override
    public float getProgressValue() {
        return count;  // 現Tierでの進行度。getProgressMaxValue() と計算され割合表示にも使われます。
    }

    @Override
    public float getProgressMaxValue() {
        return TOTAL_COUNT;  // 現Tierでの最大進行度
    }


    @Override
    public long getRewardExperience() {
        return 0;  // 現Tierの完了時に与えられるギルド経験値
    }


    @Override
    public @Nullable String getDisplayName() {
        return "ジャンプ5回";  // このクエストの表示名を返す
    }

    @Override
    public @Nullable String getDisplayNameLast() {
        return null;  // getDisplayName() と同等。ただし、表示名を動的に変更する場合に必要
    }


    // 状態を保持するためのファイル
    public class Config extends BukkitConfigDriver {
        public Config(Guild guild) {
            super(plugin, GuildSystemExampleDevPlugin.GUILD_QUEST_FOLDER + "/" + guild.getId() + "_" + ID + ".yml", "empty.yml");
            // 他のギルドと被らないファイル名にする
        }

        public int getCount() {
            return config.getInt("count");
        }

        public void setCount(int count) {
            config.set("count", count);
        }

    }

}
