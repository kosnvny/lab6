package models;

import com.google.gson.annotations.SerializedName;

/**Класс, отвечающий за координаты*/
public class Coordinates implements Validator {
    @SerializedName("x")
    private float x;
    @SerializedName("y")
    private Float y; //Значение поля должно быть больше -964, Поле не может быть null

    public Coordinates(float x, Float y) {
        this.x = x;
        this.y = y;
    }
    /** Метод проверяющий правильность полей
     * @return правильны ли поля*/
    @Override
    public boolean validate() {
        return this.y != null && this.y > -964;
    }

    @Override
    public String toString() {
        return "Координата x=" + x + ", координата y=" + y + '}';
    }
}
