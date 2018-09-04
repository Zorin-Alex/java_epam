package commandLine;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Command {
    Path currentPath;

    List<String> parse(String args) {
        List<String> listArgs = new ArrayList<>();
        Matcher matcher = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(args);
        while (matcher.find()) {
            listArgs.add(matcher.group().replaceAll("\"", "").trim());
        }
        listArgs.remove(0);
        return listArgs;
    }

    Path getFullPath(String sPath) {
        Path fullPath;
        if (sPath.matches("(^.:.*$)|(^/.*$)")) {
            fullPath = Paths.get(sPath);
        } else {
            fullPath = Paths.get(currentPath.toString(), sPath);
        }
        return fullPath;
    }

    boolean getConfirm(String message) {
        Scanner scanner = new Scanner(System.in);
        String answer;
        boolean confirmation;
        do {
            System.out.print(message);
            answer = scanner.nextLine();
            confirmation = answer.matches("[Yy].*");
        } while (!answer.matches("[Yy].*|[Nn].*"));
        return confirmation;
    }

    public abstract void execute(String args) throws BadArgumentsException;
}
