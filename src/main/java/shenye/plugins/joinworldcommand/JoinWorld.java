package shenye.plugins.joinworldcommand;


import io.izzel.taboolib.TabooLibAPI;
import io.izzel.taboolib.module.inject.TListener;
import io.izzel.taboolib.util.Features;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import java.util.List;


@TListener
public class JoinWorld implements Listener {

    @EventHandler
    public static void joinworldlistener(PlayerChangedWorldEvent event){
        Player player = event.getPlayer();
        String worldName = player.getWorld().getName();
        List<String> config = JoinWorldCommand.config.getStringList("worlds." + worldName + ".commands");
        if(config.size() == 0 ){
            return;
        }
        List<String> playerPapi = TabooLibAPI.getPluginBridge().setPlaceholders(player,config);
        for(String i : playerPapi){
            String[] value = i.split(": ");
            switch (value[0]){
                case"player":{
                    Features.dispatchCommand(player,value[1]);
                    break;
                }
                case"op":{
                    Features.dispatchCommand(player,value[1],true);
                    break;
                }
                case"console":{
                    Features.dispatchCommand(Bukkit.getConsoleSender(),value[1]);
                    break;
                }
                default:{}
            }
        }
    }
}