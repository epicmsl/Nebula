package emu.nebula.command.commands;

import emu.nebula.Nebula;
import emu.nebula.command.Command;
import emu.nebula.command.CommandArgs;
import emu.nebula.command.CommandHandler;

import java.text.SimpleDateFormat;
import java.util.Date;

@Command(label = "time", permission = "admin.reload", desc = "!time = Show time. !time list = List banners. !time set <epoch>|offset <s>|reset")
public class TimeCommand implements CommandHandler {

    private static final String[][] BANNERS = {
        {"10144", "Chitose", "1760990400"},
        {"10155", "Shia", "1761710400"},
        {"10119", "Nanoha", "1762920000"},
        {"10134", "Fuyuka", "1763524800"},
        {"10149", "Gerie", "1764734400"},
        {"10133", "Nazuka", "1765339200"},
        {"10158", "Snowish Laru", "1766548800"},
        {"10125", "Freesia", "1767153600"},
        {"10110", "Firenze", "1768363200"},
        {"10135", "Mistique", "1769572800"},
        {"10159", "Springseek Coronis", "1770177600"},
        {"10141", "Chixia", "1771387200"},
        {"10143", "Wraith", "1771992000"},
        {"10132", "Minova", "1773201600"},
        {"10130", "Donna", "1773806400"},
        {"10156", "Nazuna", "1775016000"},
        {"10145", "Otoha", "1776398400"},
        {"11144", "Chitose", "1777608000"},
        {"10115", "Firefly", "1778817600"},
        {"11155", "Shia", "1779854400"},
    };

    @Override
    public String execute(CommandArgs args) {
        var sb = new StringBuilder();

        if (args.size() > 0) {
            String sub = args.get(0);

            switch (sub) {
                case "set" -> {
                    if (args.size() < 2) {
                        return "Usage: !time set <epoch_seconds>";
                    }
                    try {
                        long target = Long.parseLong(args.get(1));
                        long now = System.currentTimeMillis() / 1000;
                        Nebula.setTimeOffset(target - now);
                        sb.append("Server time set to ").append(target);
                    } catch (NumberFormatException e) {
                        return "Invalid number.";
                    }
                }
                case "offset" -> {
                    if (args.size() < 2) {
                        return "Usage: !time offset <seconds>";
                    }
                    try {
                        long offset = Long.parseLong(args.get(1));
                        Nebula.setTimeOffset(Nebula.getTimeOffset() + offset);
                        sb.append("Time offset adjusted by ").append(offset).append("s");
                    } catch (NumberFormatException e) {
                        return "Invalid number.";
                    }
                }
                case "reset" -> {
                    Nebula.setTimeOffset(0);
                    sb.append("Time offset reset to 0");
                }
                case "list", "banners" -> {
                    var fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    sb.append(String.format("%-6s %-24s %s\n", "ID", "Banner", "Epoch"));
                    sb.append("------ ------------------------ -------------------------\n");
                    for (var b : BANNERS) {
                        long epoch = Long.parseLong(b[2]);
                        String date = fmt.format(new Date(epoch * 1000));
                        sb.append(String.format("%-6s %-24s %s (%s)\n", b[0], b[1], b[2], date));
                    }
                    return sb.toString();
                }
                default -> {
                    return "Usage: !time [list|set <epoch>|offset <s>|reset]";
                }
            }
        }

        long current = Nebula.getCurrentServerTime();
        long offset = Nebula.getTimeOffset();
        var fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sb.append("\nCurrent server time: ").append(fmt.format(new Date(current * 1000)));
        sb.append("\nOffset: ").append(offset).append("s");

        return sb.toString();
    }
}
