public class Ticket {
    private String row;
    private int seat;
    private double price;
    private Person user;

    public Ticket(String row,int seat, double price, Person user){
        this.row = row;
        this.seat = seat;
        this.price = price;
        this.user = user;
    }

    public Person getUser() {
        return this.user;
    }

    public String getRow(){
        return row;
    }

    public void setRow(String row){
        this.row = row;
    }

    public int getSeat(){
        return seat;
    }

    public void setSeat(int seat){
        this.seat = seat;
    }

    public double getPrice(){
        return price;
    }

    public void setPrice(double price){
        this.price = price;
    }

}
