package com.comp3000.project.cms.SDC;

import java.awt.Color;

public abstract class Listing<T> {
    private T member;
    private Color color;

    public Listing(T member, Color color) {
        this.member = member;
        this.color = color;
    }

    public Listing(T member) {
        this(member, Color.WHITE);
    }

    public T getMember() {
        return member;
    }

    public void setMember(T member) {
        this.member = member;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
