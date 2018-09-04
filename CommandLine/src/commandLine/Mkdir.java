package commandLine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Mkdir extends Command {
    Mkdir(Path curentPath) {
        this.currentPath = curentPath;
    }

    @Override
    public void execute(String args) throws BadArgumentsException {
        List<String> argumentList = parse(args);
        if (argumentList.size() != 1) {
            throw new BadArgumentsException("Ошибка в синтаксисе команды");
        }
        Path from = getFullPath(argumentList.get(0));
        try {
            Files.createDirectories(from);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
}