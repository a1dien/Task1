package edu.inno.snapshot;

import edu.inno.resources.Currency;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
@AllArgsConstructor
@Getter
public class Memento {
    private String name;
    private Map<Currency, Integer> deposit = new HashMap<>();
    private Deque<byte[]> history = new ArrayDeque();

}
