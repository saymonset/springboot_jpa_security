package com.apress.prospring5.ch8.entities;

/**
 * Created by simon on 26/09/18.
 */
 import java.io.Serializable;
import javax.persistence.Entity;
 import  javax.persistence.Table;
 import javax.persistence.Column;
 import javax.persistence.Id;
 import        javax.persistence.ManyToMany;
 import javax.persistence.JoinTable;
 import javax.persistence.JoinColumn;
 import java.util.Set;
 import java.util.HashSet;
@Entity
@Table(name = "INSTRUMENT",schema = "journal")
public class Instrument implements Serializable {
    @Id
    @Column(name = "INSTRUMENT_ID")
    private String instrumentId;
    @ManyToMany
    @JoinTable(name = "SINGER_INSTRUMENT",
            joinColumns = @JoinColumn(name = "INSTRUMENT_ID"),
            inverseJoinColumns = @JoinColumn(name = "SINGER_ID"))
    private Set<Singer> singers = new HashSet<>();

    public String getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(String instrumentId) {
        this.instrumentId = instrumentId;
    }

    public Set<Singer> getSingers() {
        return singers;
    }

    public void setSingers(Set<Singer> singers) {
        this.singers = singers;
    }
}
