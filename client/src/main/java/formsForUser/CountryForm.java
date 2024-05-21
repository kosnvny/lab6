package models.formsForUser;

import commandLine.*;
import managers.FileManager;
import models.Country;

public class CountryForm extends Form<Country>{
    private final Printable console;
    private final UserInput userInput;
    public CountryForm(Printable console) {
        this.console = (FileManager.isIsItInFile() ? new BlankConsole() : console);
        this.userInput = (FileManager.isIsItInFile() ? new ExecuteScriptManager() : new ConsoleInput());
    }
    /**
     * Абстрактный метод, "строящий" новый объект заданного типа
     * @return Country
     */
    @Override
    public Country build() {
        console.println("Введите страну");
        console.println("Возможные страны: " + Country.nameAll());
        while (true) {
            String input = userInput.nextLine().trim();
            try{
                return Country.valueOf(input.toUpperCase());
            } catch (IllegalArgumentException exception){
                console.printError("Такой страны нет в списке");
                if (FileManager.isIsItInFile()) console.printError("Невалидные значения для цвета в файле");
            }
        }
    }
}
