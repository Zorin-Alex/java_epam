package commandLine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Del extends Command {
    Del(Path curentPath) {
        this.currentPath = curentPath;
    }

    @Override
    public void execute(String args) throws BadArgumentsException {
        List<String> argumentList = parse(args);
        if (argumentList.size() != 1) {
            throw new BadArgumentsException("Ошибка в синтаксисе команды");
        }
        Path from;
        if (argumentList.size() != 0) {
            from = getFullPath(argumentList.get(0));
        } else {
            from = getFullPath("");
        }

        if (!Files.exists(from)) {
            throw new BadArgumentsException("Не удаётся найти указанный файл");
        }
        try {
            if (Files.isDirectory(from)) {
                for (Path file : Files.newDirectoryStream(from)) {
                    if (Files.isRegularFile(file)) {
                        Files.delete(file);
                    }
                }
            } else {
                Files.delete(from);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
