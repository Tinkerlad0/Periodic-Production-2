package com.tinkerlad.chemistry2.registries.element;

/**
 * Created by brock_000 on 7/01/2015.
 */
public class ElementComponent {
    private int quantity;
    private ElementObject element;


    public ElementComponent(ElementObject element, int quantity) {
        this.quantity = quantity;
        this.element = element;
    }

    public ElementComponent(ElementObject element) {
        this.quantity = 1;
        this.element = element;
    }

    @Override
    public String toString() {
        return "Element {" + element.getSymbol() + " : " + quantity + "}";
    }

}
