package models;

import lombok.Data;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.TermVector;

import javax.persistence.*;

@Data
@Entity
@Indexed
@Table(name = "airport")
public class Airport implements BaseModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int airportID;

	@Field(termVector = TermVector.YES)
	private String city;

	@Field(termVector = TermVector.YES)
	private String airportCode;

	@Override
	public String toString()
	{
		return  city + " " + airportCode;
	}

}
