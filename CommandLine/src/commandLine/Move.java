package commandLine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class Move extends Command {

    Move(Path curentPath) {
        this.currentPath = curentPath;
    }

    @Override
    public void execute(String args) throws BadArgumentsException {
        List<String> argumentList = parse(args);
        if (argumentList.size() > 2 || argumentList.size() < 1) {
            throw new BadArgumentsException("Ошибка в синтаксисе команды");
        }
        Path from = getFullPath(argumentList.get(0));
        Path to;

        if (argumentList.size() == 1) {
            to = currentPath;
        } else {
            to = getFullPath(argumentList.get(1));
        }

        if (!Files.exists(from)) {
            throw new BadArgumentsException("Не удаётся найти указанный файл");
        }
        if (!Files.exists(to) && !Files.exists(to.getParent())) {
            throw new BadArgumentsException("Не удаётся найти указанный файл");
        }

        if (Files.isDirectory(to)) {
            to = to.resolve(from.getFileName());
        }
        try {
            if (Files.isRegularFile(to)) {
                if (getConfirm("Заменить " + to.toString() + " [Yes / No]: ")) {
                    Files.move(from, to, StandardCopyOption.REPLACE_EXISTING);
                } else {
                    return;
                }
            } else {
                Files.move(from, to);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
