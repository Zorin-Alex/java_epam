package commandLine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

public class Dir extends Command {
    Dir(Path curentPath) {
        this.currentPath = curentPath;
    }

    @Override
    public void execute(String args) throws BadArgumentsException {
        List<String> argumentList = parse(args);
        if (argumentList.size() > 2) {
            throw new BadArgumentsException("Ошибка в синтаксисе команды");
        }
        Path from;
        Comparator<Path> comparator = (a, b) -> a.getFileName().toString().compareTo(b.getFileName().toString());
        for (String s : argumentList) {
            switch (s) {
                case "|D":
                    comparator = (a, b) -> b.getFileName().toString().compareTo(a.getFileName().toString());
            }
        }


        if (argumentList.size() != 0) {
            from = getFullPath(argumentList.get(0));
        } else {
            from = getFullPath("");
        }


        if (!Files.isDirectory(from)) {
            throw new BadArgumentsException("Не удаётся найти указанную папку");
        }
        List<Path> pathList = new ArrayList<>();

        try {
            for (Path file : Files.newDirectoryStream(from)) {
                pathList.add(file);
            }
            Collections.sort(pathList,comparator);
            for (Path file : pathList) {
                BasicFileAttributes bfa = Files.getFileAttributeView(file, BasicFileAttributeView.class).readAttributes();
                System.out.printf("%td.%<tm.%<tY %<tR %5s %15s %s \n", bfa.lastModifiedTime().toMillis(), bfa.isDirectory() ? "<DIR>" : "", bfa.isDirectory() ? "" : String.format("%,d", bfa.size()), file.getFileName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
