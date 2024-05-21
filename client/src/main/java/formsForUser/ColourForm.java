package models.formsForUser;

import commandLine.*;
import managers.FileManager;
import models.Colour;

public class ColourForm extends Form<Colour> {
    private final Printable console;
    private final UserInput userInput;
    public ColourForm(Printable console) {
        this.console = (FileManager.isIsItInFile() ? new BlankConsole() : console);
        this.userInput = (FileManager.isIsItInFile() ? new ExecuteScriptManager() : new ConsoleInput());
    }
    /**
     * Абстрактный метод, "строящий" новый объект заданного типа
     * @return Colour
     */
    @Override
    public Colour build() {
        console.println("Введите цвет волос");
        console.println("Возможные цвета: " + Colour.nameAll());
        while (true) {
            String input = userInput.nextLine().trim();
            try{
                return Colour.valueOf(input.toUpperCase());
            } catch (IllegalArgumentException exception){
                console.printError("Такого цвета нет в списке");
                if (FileManager.isIsItInFile()) console.printError("Невалидные значения для цвета в файле");
            }
        }
    }
}
