import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

// car class contains info of car
class Car {
    private String carId;
    private String brand;
    private String model;
    private double basePricePerDay;
    private boolean isAvailable;

    public Car(String carId, String brand, String model, double basePricePerDay) {
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.basePricePerDay = basePricePerDay;
        this.isAvailable = true;
    }

    public String getCarId() {
        return carId;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public double calculatePrice(int days) {
        return basePricePerDay * days;
    }

    public void rent() {
        isAvailable = false;
    }

    public void returnCar() {
        isAvailable = true;
    }

    public boolean getIsAvailable() {
        return isAvailable;
    }
}

// customer class contains info of customers

class Customer {
    private String name;
    private String id; // id proof adhar or pancard or voterid
    private String phoneNumber;
    private String address;

    Customer(String name,String id, String phoneNumber,String address){
    this.name=name;
    this.id=id;
    this.phoneNumber=phoneNumber;
    this.address=address;
    }
    // Customer(String name, String id) {
    //     this.name = name;
    //     this.id = id;
    // }

    public String getCustomerId() {
        return id;
    }

    public String getCustomerName() {
        return name;
    }

    public String getCustomerPhone() {
        return phoneNumber;
    }

    public String getCustomerAddress() {
        return address;
    }
}

class Rental {
    private Car car;
    private Customer customer;
    private int days;

    public Rental(Car car, Customer customer, int days) {
        this.car = car;
        this.customer = customer;
        this.days = days;
    }

    public Car getCar() {
        return car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getDays() {
        return days;
    }
}

class CarRentalSystem {
    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;

    public CarRentalSystem() {
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public void addCustomer(Customer cus) {
        customers.add(cus);
    }

    public void rentCar(Car car, Customer cus, int days) {
        if (car.getIsAvailable()) {
            car.rent();
            rentals.add(new Rental(car, cus, days));
        } else {
            System.out.println("Car is not available for rent!");
        }
    }

    public void returnCar(Car car) {

        Rental rentalToremove = null;

        for (Rental rental : rentals) {
            if (rental.getCar() == car) {
                rentalToremove = rental;
                break;
            }

        }

        if (rentalToremove != null) {
            rentals.remove(rentalToremove);
            System.out.println("Car returned Successfully!");
            car.returnCar();
        } else {
            System.out.println("Car was not rented!");
        }

    }

    public void menu() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("======== Car Rent System ==========");
            System.out.println("1. Rent a Car");
            System.out.println("2. Return a Car");
            System.out.println("3. Exit");
            System.out.println("Enter your Choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 1) {
                System.out.println("\n== Return a Car ==\n");
                System.out.println("Enter your name: ");
                String customerName = sc.nextLine();
                System.out.println("Enter your phone number: ");
                String phoneNumber = sc.nextLine();
                System.out.println("Enter your Address: ");
                String Address = sc.nextLine();

                System.out.println("\nAvailable Cars\n");
                System.out.println("CarID " + " " +" "+ " Car Brand " +" "+ "  " + " CarModel ");
                for (Car car : cars) {
                    if (car.getIsAvailable()) {
                        System.out.print(car.getCarId()+" "+"  "+"  "+"  ");
                        System.out.print(car.getBrand()+"  "+"  "+"  "+"  ");
                        System.out.println(car.getModel()+"  "+"  "+"  "+"  ");
                    }
                }

                System.out.println("\n Enter the Car ID you want to rent: ");
                String carId = sc.nextLine();
                System.out.println("\n Enter the Number of days for rent: ");
                int rentalDays = sc.nextInt();
                sc.nextLine(); // consume new line

                Customer newCustomer = new Customer(customerName, "CUS" + (customers.size() + 1),phoneNumber,Address);
                addCustomer(newCustomer);

                Car selectedCar = null;

                for (Car car : cars) {
                    if (car.getCarId().equals(carId) && car.getIsAvailable()) {
                        selectedCar = car;
                        break;
                    }
                }

                if (selectedCar != null) {
                    double totalPrice = selectedCar.calculatePrice(rentalDays);
                    System.out.println("\n==========Rental Information=============\n");
                    System.out.println("Customer ID: " + newCustomer.getCustomerId());
                    System.out.println("Customer Name: " + newCustomer.getCustomerName());
                    System.out.println("Customer phoneNumber: " + newCustomer.getCustomerPhone());
                    System.out.println("Customer Address: " + newCustomer.getCustomerAddress());
                    System.out.println("Car: " + selectedCar.getBrand() + " " + selectedCar.getModel());
                    System.out.println("Rentals Days: " + rentalDays);
                    System.out.printf("TotalPrice: %.2f%n ", totalPrice);

                    System.out.println("\nConfirmed Rental: (Y/N)\n");
                    String confirm = sc.nextLine();

                    if (confirm.equalsIgnoreCase("Y")) {
                        rentCar(selectedCar, newCustomer, rentalDays);
                        System.out.println("\n========Car rented Successfully=======\n");
                    } else {
                        System.out.println("\n============Rental cancelled!");
                    }

                } else {
                    System.out.println("\nInvalid Car Selection or Car not available for rent!.\n");
                }
            } else if (choice == 2) { // return a car
                System.out.println("\n=====Return Car=====\n");
                System.out.println("Enter the car ID you want to return");
                String carId = sc.nextLine();

                Car carToReturn = null;
                for (Car car : cars) {
                    if (car.getCarId().equals(carId) && !car.getIsAvailable()) {
                        carToReturn = car;
                        break;
                    }
                }

                if (carToReturn != null) {
                    Customer customer = null;
                    for (Rental rental : rentals) {
                        if (rental.getCar() == carToReturn) {
                            customer = rental.getCustomer();
                            break;
                        }
                    }

                    if (customer != null) {
                        returnCar(carToReturn);
                        System.out.println("\nCar Returned Successfully by " + customer.getCustomerName()+"\n\n");

                    } else {
                        System.out.println("\nCar was not rented or car information is missing\n\n");
                    }

                } else {
                    System.out.println("Invalid car ID or car is not rented\n\n");
                }

            } else if (choice == 3) {
                break;
            } else {
                System.out.println("Invalid choice enter a valid options");
            }

        }

        System.out.println("\n Thank you  for using the car rental system");

    }

}

public class CarRent {

    public static void main(String[] args) {
        CarRentalSystem rentalSystem=new CarRentalSystem();

        Car car1=new Car("C001", "Toyota", "Camry", 3500);
        Car car2=new Car("C002", "Honda", "Accord", 1500);
        Car car3=new Car("C003", "Mahindra", "bolero", 1000);

        rentalSystem.addCar(car1);
        rentalSystem.addCar(car2);
        rentalSystem.addCar(car3);
        rentalSystem.menu();
    }
}
