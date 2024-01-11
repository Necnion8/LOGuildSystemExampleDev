package com.gmail.necnionch.myplugin.guildsystemexampledev.bukkit;


import com.gmail.necnionch.myplugin.guildsystem.bukkit.OnlinePlayer;
import com.gmail.necnionch.myplugin.guildsystem.bukkit.guild.Guild;
import com.gmail.necnionch.myplugin.guildsystem.bukkit.reward.GuildReward;
import com.gmail.necnionch.myplugin.guildsystem.bukkit.reward.PlayerReward;
import com.gmail.necnionch.myplugin.guildsystem.bukkit.reward.RewardExtra;
import com.gmail.necnionch.myplugin.guildsystem.bukkit.reward.extension.RewardExtension;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.jetbrains.annotations.Nullable;

public class FireworkReward extends RewardExtension {
    public static final String ID = "simpleFirework";

    @Override
    public String getName() {
        return ID;
    }

    @Override
    public void execute(@Nullable Player p, RewardExtra reward) {

        if (reward instanceof GuildReward.Extra guildReward) {
            // ギルド報酬として設定されている
            Guild guild = guildReward.getGuild();

            // ギルド報酬だったらメンバー全員に花火を
            for (OnlinePlayer onlinePlayer : guild.getOnlinePlayers()) {
                Player player = onlinePlayer.getPlayer();

                if (player != null) {
                    spawnFireworks(player);
                }
            }

        } else if (reward instanceof PlayerReward.Extra playerReward) {
            // プレイヤー報酬として設定されている
            OnlinePlayer onlinePlayer = playerReward.getOnlinePlayer();
            Player player = onlinePlayer.getPlayer();

            if (player != null) {
                spawnFireworks(player);
            }
        }

    }


    private void spawnFireworks(Player player) {
        Firework firework = (Firework) player.getWorld().spawnEntity(player.getLocation(), EntityType.FIREWORK);
        FireworkMeta meta = firework.getFireworkMeta();
        meta.setPower(2);
        meta.addEffect(FireworkEffect.builder()
                .flicker(true)
                .withColor(Color.RED)
                .withFade(Color.YELLOW)
                .build());
        firework.setFireworkMeta(meta);
    }

}
