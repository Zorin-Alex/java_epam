package commandLine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UnZip extends Command {
    public UnZip(Path curentPath) {
        this.currentPath = curentPath;
    }

    @Override
    public void execute(String args) throws BadArgumentsException {
        List<String> argumentList = parse(args);
        if (argumentList.size() < 1) {
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
        if (!Files.exists(to)) {
            try {
                Files.createDirectories(to);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(from.toFile()))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().matches(".*/$")) {
                    Files.createDirectory(to.resolve(entry.getName()));
                } else {
                    FileOutputStream fos = new FileOutputStream(to.resolve(entry.getName()).toFile());
                    byte[] buffer = new byte[1024];
                    while (zis.read(buffer) != -1) {
                        fos.write(buffer);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
