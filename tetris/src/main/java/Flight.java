import javafx.print.Collation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class Flight {

    private final int numberOfSeats;
    private final String airline;
    private final int bookedSeats;
    private String flightName;
    private static final int flightNm=0;
    private Collection<Flight> flights = new ArrayList<Flight>();
    private Collection<Integer> flightNumbers = new ArrayList<Integer>();

    public Flight(int numberOfSeats, String airline, int bookedSeats){
        this.numberOfSeats = numberOfSeats;
        this.airline = airline;
        this.bookedSeats = bookedSeats;
    }

    private void generateFlightName(){
        String airlineshort = String.valueOf(getAirline().charAt(0))+ getAirline().charAt(1);


    }

    private void generateRandom4digitNumber(){

    }

    private void checkIfNumberExists(int flightNumbers){
        if(this.flightNumbers.stream().anyMatch(n->n.equals(flightNumbers)))
            throw new IllegalArgumentException("Flightnumber allready exisrts");
    }

    public Collection<Flight> getFlights(String airline){
        return this.flights.stream()
                .filter(f->f.getAirline().equals(airline))
                .collect(Collectors.toList());
    }

    public int getBookedSeats() {
        return bookedSeats;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public String getAirline() {
        return airline;
    }
}
