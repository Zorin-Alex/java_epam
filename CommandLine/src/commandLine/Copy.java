package commandLine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class Copy extends Command {

    Copy(Path curentPath) {
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
        if (!Files.isDirectory(to.getParent())) {
            throw new BadArgumentsException("Не удаётся найти указанный путь");
        }

        try {
            if (Files.isDirectory(from)) {
                if (!Files.isDirectory(to)) {
                    copy(from, to);
                }
                for (Path file : Files.newDirectoryStream(from)) {
                    if (!Files.isDirectory(file)) {
                        copy(file, to.resolve(file.getFileName()));
                    }
                }
            } else {
                if (Files.isDirectory(to)) {
                    copy(from, to.resolve(from.getFileName()));
                } else {
                    copy(from, to);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            ;
        }
    }

    private void copy(Path from, Path to) throws IOException {
        if (Files.exists(to)) {
            if (getConfirm("Заменить " + to.toString() + " [Yes / No]: ")) {
                Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
            }
        } else {
            Files.copy(from, to);
        }
    }
}
