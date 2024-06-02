package edu.inno;

import edu.inno.exception.UndoException;
import edu.inno.resources.Currency;
import edu.inno.snapshot.Memento;
import lombok.*;

import java.io.*;
import java.util.*;

@Getter
@ToString
public class Account implements Serializable{
    @ToString.Exclude
    private Deque<byte[]> history = new ArrayDeque();
    private String name;
    private Map<Currency, Integer> deposit = new HashMap<>();

    public Account(String name) {
        this.setName(name);
    }
    public void setName(String name) {
        if (name != null && !name.trim().isEmpty()) {
            if (this.name != null) save();
            this.name = name;
        } else throw new IllegalArgumentException("Имя владельца не может быть пустым.");
    }
    public void addMoney(Currency currency, int amount){
        if (amount >= 0) {
            save();
            this.deposit.put(currency, amount);
        }
        else throw new IllegalArgumentException("Сумма на счете может быть равна или больше нуля.");
    }
    public Map<Currency, Integer> getMoney() {
        return new HashMap<Currency, Integer>(this.deposit);
    }
    public int getMoney(Currency currency) {
        return this.deposit.get(currency);
    }

    public void save() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this);
            oos.close();
            byte[] byteResult = baos.toByteArray();
            history.push(byteResult);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка во время сохранения состояние счета.");
        }
    }
    public Account undo() throws UndoException {
        if (history.isEmpty())  throw new UndoException("Откат дальше невозможен. Счет находится в своем изначальном состоянии.");
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(history.pop());
            ObjectInputStream ois = new ObjectInputStream(bais);
            ois.close();
            return (Account) ois.readObject();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка во время десериализации счета.");
        }
    }

    public Memento saveMemento() {
        return new Memento(this.name, new HashMap<>(this.deposit), new ArrayDeque<>(this.history));
    }
    public void restoreMemento(Memento memento) {
        this.name = memento.getName();
        this.deposit = memento.getDeposit();
        this.history = memento.getHistory();
    }
}
