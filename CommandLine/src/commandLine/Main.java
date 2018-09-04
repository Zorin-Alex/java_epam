package commandLine;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    static private Path currentPath = Paths.get(System.getProperty("user.dir"));

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        exit:
        while (true) {
            System.out.print(currentPath);
            System.out.print("> ");
            String param = scanner.nextLine();
            String command = param.split(" ")[0];
            try {
                switch (command.toLowerCase()) {
                    case "":
                        continue;
                    case "exit":
                        break exit;
                    case "cd":
                        new Cd(currentPath).execute(param);
                        break;
                    case "move":
                        new Move(currentPath).execute(param);
                        break;
                    case "copy":
                        new Copy(currentPath).execute(param);
                        break;
                    case "del":
                        new Del(currentPath).execute(param);
                        break;
                    case "dir":
                        new Dir(currentPath).execute(param);
                        break;
                    case "mkdir":
                        new Mkdir(currentPath).execute(param);
                        break;
                    case "rmdir":
                        new Rmdir(currentPath).execute(param);
                        break;
                    case "tree":
                        new Tree(currentPath).execute(param);
                        break;
                    case "zip":
                        new Zip(currentPath).execute(param);
                        break;
                    case "unzip":
                        new UnZip(currentPath).execute(param);
                        break;
                    default:
                        System.out.println("Неизвестная команда");
                }
            } catch (BadArgumentsException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void setCurrentPath(Path currentPath) {
        Main.currentPath = currentPath;
    }

}
