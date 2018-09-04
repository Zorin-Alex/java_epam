package commandLine;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

public class Rmdir extends Command {
    private int fileCount = 0;
    private int dirCount = 0;

    class RmdirFileVisitor extends SimpleFileVisitor<Path> {
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            Files.delete(file);
            fileCount++;
            return FileVisitResult.CONTINUE;
        }


        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            Files.delete(dir);
            dirCount++;
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
            System.out.println(exc.getMessage());
            return  FileVisitResult.CONTINUE;
        }
    }

    public Rmdir(Path curentPath) {
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
            try {
                Main.setCurrentPath(currentPath.resolve("..").toRealPath());
            } catch (IOException e) {
                System.out.println(e.getMessage());;
            }
        }
        try{
            RmdirFileVisitor fileVisitor =  new RmdirFileVisitor();
            Files.walkFileTree(from,fileVisitor);
            System.out.println("Папок удалено " +dirCount);
            System.out.println("Файлов удалено " +fileCount);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
