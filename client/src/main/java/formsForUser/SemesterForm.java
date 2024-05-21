package models.formsForUser;

import commandLine.*;
import managers.FileManager;
import models.Semester;

public class SemesterForm extends Form<Semester>{
    private final Printable console;
    private final UserInput userInput;
    public SemesterForm(Printable console) {
        this.console = (FileManager.isIsItInFile() ? new BlankConsole() : console);
        this.userInput = (FileManager.isIsItInFile() ? new ExecuteScriptManager() : new ConsoleInput());
    }
    /**
     * Абстрактный метод, "строящий" новый объект заданного типа
     *
     * @return Semester
     */
    @Override
    public Semester build() {
        console.println("Введите семестр обучения");
        console.println("Возможные семестры: " + Semester.nameAll());
        while (true) {
            String input = userInput.nextLine().trim();
            try {
                return Semester.valueOf(input.toUpperCase());
            } catch (IllegalArgumentException exception) {
                console.printError("Такого семестра нет в списке");
                if (FileManager.isIsItInFile()) console.printError("Невалидные значения для цвета в файле");
            }
        }
    }
}
