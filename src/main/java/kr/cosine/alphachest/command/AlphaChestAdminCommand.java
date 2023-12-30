package kr.cosine.alphachest.command;

import kr.cosine.alphachest.service.AlphaChestService;
import kr.cosine.alphachest.util.SchdulerUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AlphaChestAdminCommand implements CommandExecutor, TabCompleter {

    private final AlphaChestService alphaChestService;

    public AlphaChestAdminCommand(AlphaChestService alphaChestService) {
        this.alphaChestService = alphaChestService;
    }

    private final String[] commandList = {
        "§6[ /창고관리 열기 §7[닉네임] §6] §8- §f해당 유저의 창고를 오픈합니다.",
        "§6[ /창고관리 저장 §6] §8- §f창고의 변경된 사항을 수동으로 저장합니다.",
        "§6[ /창고관리 리로드 ] §8- §fconfig.yml을 리로드합니다."
    };

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            printHelp(sender);
            return true;
        }
        checker(sender, args);
        return true;
    }

    private void printHelp(CommandSender sender) {
        for (String command : commandList) {
            sender.sendMessage(command);
        }
    }

    private void checker(CommandSender sender, String[] args) {
        switch (args[0]) {
            case "열기": {
                if (!(sender instanceof Player)) {
                    sender.sendMessage("§c콘솔에서 사용할 수 없는 명령어입니다.");
                    return;
                }
                open((Player) sender, args);
                return;
            }
            case "저장": {
                save(sender);
                return;
            }
            case "리로드": {
                reload(sender);
                return;
            }
            default: printHelp(sender);
        }
    }

    private void open(Player player, String[] args) {
        if (args.length == 1) {
            player.sendMessage("§c닉네임을 입력해주세요.");
            return;
        }
        SchdulerUtils.runAsync(() -> {
            String targetName = args[1];
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(targetName);
            SchdulerUtils.runSync(() -> {
                if (!alphaChestService.openAlphaChestFromAdmin(player, offlinePlayer)) {
                    player.sendMessage("§c창고 데이터가 존재하지 않는 유저입니다.");
                }
            });
        });
    }

    private void save(CommandSender sender) {
        alphaChestService.save();
        sender.sendMessage("§a창고의 변경된 사항이 저장되었습니다.");
    }

    private void reload(CommandSender sender) {
        alphaChestService.reload();
        sender.sendMessage("§aconfig.yml을 리로드하였습니다.");
    }

    private final List<String> commandTabList = Arrays.asList("열기", "저장", "리로드");

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length <= 1) {
            return StringUtil.copyPartialMatches(args[0], commandTabList, new ArrayList<>());
        }
        if (args[0].equals("열기") && args.length == 2) {
            return getPlayersWithIgnoreCase(sender.getServer(), args[1]);
        }
        return Collections.emptyList();
    }

    private List<String> getPlayersWithIgnoreCase(Server server, String input) {
        return server.getOnlinePlayers()
            .stream()
            .map(Player::getName)
            .filter(name -> StringUtil.startsWithIgnoreCase(name, input))
            .collect(Collectors.toList());
    }
}
