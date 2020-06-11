package com.airgap.airgapagent.algo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * com.airgap.airgapagent.algo
 * Created by Jacques Fontignie on 6/10/2020.
 */
public class CharToken extends Token {


    private final char car;
    private final List<Character> list;

    public CharToken(char car) {
        super(true);
        this.car = car;
        list = List.of(car);
    }

    public static List<Token> toList(String source) {
        List<Token> result = new ArrayList<>();
        for (char c : source.toCharArray()) {
            result.add(new CharToken(c));
        }
        return result;
    }

    public char getCar() {
        return car;
    }

    @Override
    public List<Character> matches() {
        return list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CharToken)) return false;
        CharToken charToken = (CharToken) o;
        return car == charToken.car;
    }

    @Override
    public int hashCode() {
        return Objects.hash(car);
    }
}
