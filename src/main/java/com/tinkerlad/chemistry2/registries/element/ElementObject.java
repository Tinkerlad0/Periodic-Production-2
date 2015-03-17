package com.tinkerlad.chemistry2.registries.element;

/**
 * Created by brock_000 on 4/01/2015.
 */
public class ElementObject {

    private String name;
    private String symbol;
    private String Z;
    private String atomic_weight;
    private String atomic_radius;
    private String pauling_negativity;
    private String electronic_configuration;
    private String oxidation_states;
    private String density;
    private String atomic_volume;
    private String specific_heat;
    private String ionization_potential;
    private String thermal_conductivity;
    private String melting_point;
    private String boiling_point;

    public ElementObject() {

    }

    public String getMelting_point() {
        return melting_point;
    }

    public String getBoiling_point() {
        return boiling_point;
    }

    @Override
    public String toString() {
        return (name + " : " + symbol + " : " + Z);
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getZ() {
        return Z;
    }

    public String getAtomic_weight() {
        return atomic_weight;
    }

    public String getAtomic_radius() {
        return atomic_radius;
    }

    public String getPauling_negativity() {
        return pauling_negativity;
    }

    public String getElectronic_configuration() {
        return electronic_configuration;
    }

    public String getOxidation_states() {
        return oxidation_states;
    }

    public String getDensity() {
        return density;
    }

    public String getAtomic_volume() {
        return atomic_volume;
    }

    public String getSpecific_heat() {
        return specific_heat;
    }

    public String getIonization_potential() {
        return ionization_potential;
    }

    public String getThermal_conductivity() {
        return thermal_conductivity;
    }

}
