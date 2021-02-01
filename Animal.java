/**
 * @description:
 * @author: Gavin Wang
 * @time: 2021/2/1 16:09
 */
public class Animal {
    public String name;
    public String hobby;
    public int old;
    public String food;

    public Animal(String name, int old, String food) {
        this.name = name;
        this.old = old;
        this.food = food;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public int getOld() {
        return old;
    }

    public void setOld(int old) {
        this.old = old;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }
}
