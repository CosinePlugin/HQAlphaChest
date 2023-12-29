package kr.cosine.alphachest.command;

import kr.cosine.alphachest.service.AlphaChestService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AlphaChestUserCommand implements CommandExecutor {

    private final AlphaChestService alphaChestService;

    public AlphaChestUserCommand(AlphaChestService alphaChestService) {
        this.alphaChestService = alphaChestService;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§c콘솔에서 사용할 수 없는 명령어입니다.");
            return true;
        }
        Player player = (Player) sender;
        if (!alphaChestService.openAlphaChestFromUser(player)) {
            player.sendMessage("§c관리자가 창고를 확인 중입니다.");
        }
        return true;
    }
}
