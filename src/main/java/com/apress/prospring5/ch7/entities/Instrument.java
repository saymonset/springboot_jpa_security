package com.apress.prospring5.ch7.entities;

/**
 * Created by simon on 24/09/18.
 */


import
        javax.persistence.*;
import java.io.Serializable;
import        java.util.HashSet;
import      java.util.Set;
@Entity
@Table(name = "INSTRUMENT",schema = "journal")
public class Instrument implements Serializable {
    private String instrumentId;
    private Set<Singer> singers = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "SINGER_INSTRUMENT",
            joinColumns = @JoinColumn(name = "INSTRUMENT_ID"),
            inverseJoinColumns = @JoinColumn(name = "SINGER_ID"))
    public Set<Singer> getSingers() {
        return this.singers;
    }
    public void setSingers(Set<Singer> singers) {
        this.singers = singers;
    }

    @Id
    @Column(name = "INSTRUMENT_ID")
    public String getInstrumentId() {
        return this.instrumentId;
    }
    public void setInstrumentId(String instrumentId) {
        this.instrumentId = instrumentId;
    }
    @Override
    public String toString() {
        return "Instrument :" + getInstrumentId();
    }
}
