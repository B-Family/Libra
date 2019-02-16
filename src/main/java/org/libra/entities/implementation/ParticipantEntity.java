package org.libra.entities.implementation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.libra.entities.Entities;

import javax.persistence.*;

@Entity
@Table(name = "participants")
public class ParticipantEntity implements Entities
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @JsonIgnore
    @Column(name = "presentation_id")
    private Integer presentationId;

    @Column(name = "email")
    private String email;

    public Integer getId()
    {
        return id;
    }
    public void setId(Integer id)
    {
        this.id = id;
    }
    public Integer getPresentationId()
    {
        return presentationId;
    }
    public void setPresentationId(Integer presentationId)
    {
        this.presentationId = presentationId;
    }
    public String getEmail()
    {
        return email;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }
}