package commandLine;

import java.io.*;
import java.net.URI;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zip extends Command {

    public Zip(Path curentPath) {
        this.currentPath = curentPath;
    }

    class ZipFileVisitor extends SimpleFileVisitor<Path> {
        Path relative;
        FileSystem fileSystem;

        public ZipFileVisitor(Path relative, FileSystem fileSystem) {
            this.relative = relative;
            this.fileSystem = fileSystem;
        }

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            Path pathInZipfile = fileSystem.getPath(relative.relativize(dir).toString());
            Files.copy(dir, pathInZipfile, StandardCopyOption.REPLACE_EXISTING);
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            Path pathInZipfile = fileSystem.getPath(relative.relativize(file).toString());
            Files.copy(file, pathInZipfile, StandardCopyOption.REPLACE_EXISTING);
            return FileVisitResult.CONTINUE;
        }
    }

    @Override
    public void execute(String args) throws BadArgumentsException {
        List<String> argumentList = parse(args);
        if (argumentList.size() > 2 || argumentList.size() <= 1) {
            throw new BadArgumentsException("Ошибка в синтаксисе команды");
        }
        Path from = getFullPath(argumentList.get(0));
        Path to = getFullPath(argumentList.get(1));

        if (!Files.exists(from)) {
            throw new BadArgumentsException("Не удаётся найти указанный файл");
        }
        if (!Files.exists(to) && !Files.exists(to.getParent())) {
            throw new BadArgumentsException("Не удаётся найти указанный файл1");
        }
        Map<String, String> env = new HashMap<>();
        env.put("create", "true");
        URI uri = URI.create("jar:" + to.toUri());
        try (FileSystem zipfs = FileSystems.newFileSystem(uri, env)) {
            Files.walkFileTree(from, new ZipFileVisitor(from.getParent(), zipfs));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}