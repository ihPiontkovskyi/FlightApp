package models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "airport")
public class Airport implements BaseModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int airportID;
	private String city;
	private String airportCode;

	@Override
	public String toString()
	{
		return  city + " " + airportCode;
	}

}
