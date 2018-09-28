package com.apress.prospring5.ch8.entities;
import static javax.persistence.GenerationType.IDENTITY;



import
        java.io.Serializable;
import java.util.Date;
import  javax.persistence.*;
/**
 * Created by simon on 26/09/18.
 */
@Entity
@Table(name = "ALBUM",schema = "journal")
public class Album implements Serializable {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Version
    @Column(name = "VERSION")
    private int version;
    @Column
    private String title;
    @Temporal(TemporalType.DATE)
    @Column(name = "RELEASE_DATE")
    private Date releaseDate;
    @ManyToOne
    @JoinColumn(name = "SINGER_ID")
    private Singer singer;
    public Album() {
        //needed  byJPA
    }
    public Album(String title, Date releaseDate) {
        this.setTitle(title);
        this.setReleaseDate(releaseDate);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Singer getSinger() {
        return singer;
    }

    public void setSinger(Singer singer) {
        this.singer = singer;
    }
}
