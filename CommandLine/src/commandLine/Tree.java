package commandLine;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

public class Tree extends Command {

    class TreeFileVisitor extends SimpleFileVisitor<Path> {
        String currentDepth = "  ";

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            System.out.println(currentDepth + dir.getFileName());
            currentDepth += "  ";
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            System.out.println(currentDepth + file.getFileName());
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
            System.out.println(exc.getMessage());
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            currentDepth = currentDepth.substring(2);
            return FileVisitResult.CONTINUE;
        }
    }

    public Tree(Path curentPath) {
        this.currentPath = curentPath;
    }

    @Override
    public void execute(String args) throws BadArgumentsException {
        List<String> argumentList = parse(args);
        if (argumentList.size() > 1) {
            throw new BadArgumentsException("Ошибка в синтаксисе команды");
        }
        Path from;
        if (argumentList.size() != 0) {
            from = getFullPath(argumentList.get(0));
        } else {
            from = getFullPath("");
        }
        try {
            Files.walkFileTree(from, new TreeFileVisitor());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
