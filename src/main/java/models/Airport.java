package models;

import lombok.Data;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;

@Data
@Entity
@Indexed
@Table(name = "airport")
public class Airport implements BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int airportID;

    @Field
    private String city;

    @Field
    private String airportCode;

    @Override
    public String toString() {
        return city + " " + airportCode;
    }

}
