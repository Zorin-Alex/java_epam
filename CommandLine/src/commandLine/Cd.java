package commandLine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Cd extends Command {

    public Cd(Path curentPath) {
        this.currentPath = curentPath;
    }

    @Override
    public void execute(String args) throws BadArgumentsException {
        List<String> argumentList = parse(args);
        if (argumentList.size() != 1) {
            throw new BadArgumentsException("Ошибка в синтаксисе команды");
        }
        Path newPath = getFullPath(argumentList.get(0));
        if (Files.isDirectory(newPath)) {
            try {
                Main.setCurrentPath(newPath.toRealPath());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } else {
            throw new BadArgumentsException("Не удаётся найти указанный файл");
        }
    }
}
