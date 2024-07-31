package Assignment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import Input.Employee;
import Flight.Flight;
import Input.Booking;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage; 

public class Mainpart {
    
    public static void writeToFile(String filename, String data) {
        try (FileWriter writer = new FileWriter(filename, true)) {
            writer.write(data + "\n");
           
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }
    private static void clearConsole() {
        try {
            // Check if the operating system is Windows
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            // If the operating system is not Windows, assume it's Unix-like (Linux, macOS)
            else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    public static void main(String[] args) {
    	
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n\n\n\n\n\n\n\n");
        System.out.println("\t\t\t      //////////////     //////////////     //          //");
        System.out.println("\t\t\t      //                       //           //          //");
        System.out.println("\t\t\t      //                       //           //          //");
        System.out.println("\t\t\t      //                       //           //          //");
        System.out.println("\t\t\t      //////////////           //           //////////////");
        System.out.println("\t\t\t      //                       //           //          //");
        System.out.println("\t\t\t      //                       //           //          //");
        System.out.println("\t\t\t      //                       //           //          //");
        System.out.println("\t\t\t      //////////////           //           //          //");
        System.out.println("\n");
        System.out.println("\n");
        System.out.println("\t\t\t                            //////////////////////////");
        System.out.println("\t\t\t                           //  YEAR-III SEMESTER-I //");
        System.out.println("\t\t\t                          ///////////////////////////");
        System.out.println("\n\n");
        System.out.println("\t\t\t                    //////////////////////////////////////");
        System.out.println("\t\t\t                   //   AIRLINE RESERVATION SYSTEM     //");
        System.out.println("\t\t\t                  //////////////////////////////////////");
        System.out.println("\n");
        System.out.println("\t\t\t                        Press Enter to Continue .....");
        new Scanner(System.in).nextLine();
        int choice;
        do {
            System.out.println("\n");
            System.out.println("\t\t\t               Welcome to ETHIOPEAN AIRLINE SERVICES   \n");
            System.out.println("1. Booking(Customer)");
            System.out.println("2. Flight");
            System.out.println("3. Employee");
            System.out.println("4. Available flights");
            System.out.println("5. Passenger list");
            System.out.println("6. Payment");
            System.out.println("7. Modification");
            System.out.println("8. Cancellation");
            System.out.println("9. Exit");

            System.out.print("Enter your choice: ");
            try{
            choice = scanner.nextInt();
            scanner.nextLine(); 
            switch (choice) {
                case 1:
                    System.out.println("You are in the booking section.");
                    Booking booking = new Booking("", "", "", ' ', "", '\0', "", 0, 0,  0, ""); 

                    booking.setNameFromInput();
                    booking.setEmailFromInput();
                    
                    booking.setAddressFromInput();
                    booking.setSexFromInput();
                    booking.setDateFromInput();
                    booking.departureinput();
                    booking.arrivalinput();
                    booking.setAgeFromInput();
                    booking.setUserFlightNoFromInput();
                    booking.setPhoneFromInput();
                    booking.bookingId = booking.generateBookingId();
                    int display=booking.displayBookingId();
                    System.out.println("\nEntered Details:");
                    System.out.println("\nName\tSex\tDate\t\tDeparture\tArrival\t\tFlight No.\tPhone No.\t\tEmail\t\tAddress\t\tbook.ID");
                    System.out.println("------\t---\t----\t\t---------\t-------\t\t----------\t---------\t\t-----\t\t-------\t\t-------");
                    System.out.println(booking.name+"  "+booking.sex+"\t"+booking.date+"\t"+booking.departure +"\t"+booking.arrival +"\t\t"+booking.userFlightNo +"\t\t"+booking.phone + "\t"+booking.email+"\t"+booking.address + "\t"+display);
                     Booking.writeToFile(booking.toString());
                   
                     System.out.println("\n");
                    // Sender's email address
                    String from = "bernabasgezahegndegefe@gmail.com";
                    // Sender's password
                    String emailpassword = "xnsf ruck dyfi tqhm";

                    // Recipient's email address
                    String to = booking.email;

                    // SMTP server properties
                    Properties properties = new Properties();
                    properties.put("mail.smtp.auth", "true");
                    properties.put("mail.smtp.starttls.enable", "true");
                    properties.put("mail.smtp.host", "smtp.gmail.com");        
                    properties.put("mail.smtp.port", "587");

                    // Creating a session with the SMTP server
                    Session session = Session.getInstance(properties, new Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(from, emailpassword);
                        }
                    });

                    try {
                        // Creating a message object
                        Message message = new MimeMessage(session);

                        // Setting sender and recipient addresses
                        message.setFrom(new InternetAddress(from));
                        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

                        // Setting the subject and body of the email
                        message.setSubject("Your Flight Booking Confirmation");
                        message.setText("Dear "+booking.name+",\r\n"
                        		+ "\r\n"
                        		+ "Thank you for choosing Ethiopian Airline Services for your upcoming journey! We are delighted to confirm your flight booking details. Here is a summary of your reservation:\r\n"
                        		+ "\r\n"
                        		+ "- Passenger Name: "+ booking.name+"\r\n"
                        		+ "- Date of Booking: "+ booking.date+"\r\n"
                        		+ "- Departure Airport: "+ booking.departure+"\r\n"
                        		+ "- Arrival Airport: "+booking.arrival+"\r\n"
                        		+ "- Flight Number:  "+booking.userFlightNo+" \r\n"
                        		
                        		+ "We appreciate your trust in our services and are committed to providing you with a safe and enjoyable travel experience. Please ensure that you arrive at the airport well in advance and have all necessary travel documents.\r\n"
                        		+ "\r\n"
                        		+ "For any further inquiries or assistance, feel free to contact our customer support at ethiopianairlines@gmail.com.\r\n"
                        		+ "\r\n"
                        		+ "Safe travels, and we look forward to serving you on board Ethiopian Airline Services!\r\n"
                        		+ "\r\n"
                        		+ "Best regards,\r\n"
                        		+ "\r\n"
                        		+ "Ethiopian Arlines\r\n"
                        		+ "");

                        // Sending the email
                        Transport.send(message);

                        System.out.println("Email sent successfully!");

                    } catch (MessagingException e) {
//                    	throw new RuntimeExeption(e);
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    String password = "Group3";
                    int maxAttempts = 3;
                    int attempts = 0;
            
                    while (attempts < maxAttempts) {
                        System.out.print("Enter password to access flight: ");
                        String enteredPassword = scanner.nextLine();
                        if (enteredPassword.equals(password)) {
                            System.out.println("Access granted. You are now in the flight section.");
                          
                            Flight flight = new Flight(null, null, null, null, 0, 0, 0.0f, 0.0f, 0.0f, 0, 0, "",null,null);
            
                            flight.setFlightNameFromInput();
                            flight.setSeatNoFromInput();
                            flight.setFlightOriginInput();
                            flight.setFlightDestinationInput();
                            flight.setflightNoFromInput();
                            flight.businessFareFromInput();
                            flight.fareEconomyFromInput();
                            flight.seatBusinessFromInput();
                            flight.seatEconomyFromInput();
                            flight.setflightDateFromInput();
                            flight.setDepartureTimeFromInput();
                            flight.setArrivalTimeFromInput();
                            
                            Flight.writeToFile(flight.toString());
                           // writeToFile("flight_details.txt", flight.toString());
                            
                           
                            break;
                        } else {
                            attempts++;
                            int remainingAttempts = maxAttempts - attempts;
                            if (remainingAttempts > 0) {
                                System.out.println("Incorrect password. You have " + remainingAttempts + " attempt(s) left.");
                            } else {
                                System.out.println("Incorrect password. No attempts remaining. Shutting down...");
                               
                                System.exit(0); 
                            }
                        }
                    }
                    break;

                 case 3:
                 String pass = "Group3";
                    int maxAttempt = 3;
                    int attempt = 0;
            
                    while (attempt < maxAttempt) {
                        System.out.print("Enter password to access Employee: ");
                        String enteredPassword = scanner.nextLine();
                        if (enteredPassword.equals(pass)) {
                            System.out.println("Access granted. You are now in the Employee section.");
                          
                	  Employee employee = new Employee("","", "", ' ', "",0, 0, "", 0);
                	  employee.setEmployeeId();
                	  employee.setNameFromInput();
                      employee.setEmailFromInput();
                      employee.setDepartment();
                      employee.setSalary();
                      employee.setAddressFromInput();
                      employee.setSexFromInput();
                      employee.setPhoneFromInput();
                      employee.setAgeFromInput();                                          
                
                      System.out.println("\nDetails entered:");
                      System.out.println("Person Information:");
                      System.out.println("Employee.ID \t Name \t Email \t Depatment\t \t Address \t Sex \t Phone \t Age " );
                      System.out.println(employee.getEmployeeId()+"\t\t "+ employee.name +"\t "+ employee.email+"\t "+employee.getDepartment()+"\t "+employee.address +"\t "+employee.sex+"\t "+employee.phone+"\t "+employee.age);
                      break;
                        } else {
                            attempt++;
                            int remainingAttempts = maxAttempt - attempt;
                            if (remainingAttempts > 0) {
                                System.out.println("Incorrect password. You have " + remainingAttempts + " attempt(s) left.");
                            } else {
                                System.out.println("Incorrect password. No attempts remaining. Shutting down...");
                               
                                System.exit(0); 
                            }
                        }
                }
                break;
                case 4:
				 Flight f = new Flight(null, null, null, null, 0, 
				 0, 0.0f, 0.0f, 0.0f, 0, 0, "",
				 null,null);
				 f.displayAllValues();
				    break;


                case 5:
                Booking passengerList = new Booking("", "", "", ' ', "", '\0', "", 0, 0,  0, "");
                passengerList.displayAllValues(); 
            
                break;
                
                case 6:
				    System.out.println("Payment Options:");
				    System.out.println("1. Tele Birr");
				    System.out.println("2. CBE Birr");
				    System.out.println("3. BOA");
				    int paymentChoice = scanner.nextInt();
				    scanner.nextLine(); 
                        switch (paymentChoice) {
                             case 1:
                           System.out.println("Tele Birr payment selected.");
                           System.out.println("Enter Tele Birr account number:");
                           String teleBirrAccount = scanner.nextLine();
                           System.out.println("Enter amount to be paid:");
                           float amountTeleBirr = scanner.nextFloat();
                           System.out.println("Payment of " + amountTeleBirr + " Birr made using Tele Birr from account: " + teleBirrAccount);
                             break;

                             case 2:
                           System.out.println("CBE Birr payment selected.");
                           System.out.println("Enter CBE Birr account number:");
                           String cbeBirrAccount = scanner.nextLine();
                           System.out.println("Enter amount to be paid:");
                           float amountCbeBirr = scanner.nextFloat();
                           System.out.println("Payment of " + amountCbeBirr + " Birr made using CBE Birr from account: " + cbeBirrAccount);
                             break;

                             case 3:
           
                           System.out.println("BOA payment selected.");
                           System.out.println("Enter BOA account number:");
                           String boaAccount = scanner.nextLine();
                           System.out.println("Enter amount to be paid:");
                           float amountBoa = scanner.nextFloat();
                           System.out.println("Payment of " + amountBoa + " Birr made using BOA from account: " + boaAccount);
                             break;
                             default:
                           System.out.println("Invalid payment option.");
                     }
                    break;
                    case 7:
                     Flight f1 = new Flight(null, null, null, null, 0, 0, 0.0f, 0.0f, 0.0f, 0, 0, "",null,null);
                     Booking b1 = new Booking("", "", "", ' ', "", '\0', "", 0, 0,  0, ""); 

				    System.out.println("Modification Menu:");
				    System.out.println("1. Modify Booking");
				    System.out.println("2. Modify Flight");
				    int modifyChoice = scanner.nextInt();
				    scanner.nextLine(); // Consume newline
				    switch (modifyChoice) {
				        case 1:
				        b1.modifyBooking();
				            break;
				        case 2:
				            f1.modifyFlight();
				            break;
				        default:
				            System.out.println("Invalid modification option.");
				    }
				    break;

				case 8:
				Flight f2 = new Flight(null, null, null, null, 0, 0, 0.0f, 0.0f, 0.0f, 0, 0, "",null,null);
				                     
				    System.out.println("Cancellation Menu:");
				    System.out.println("1. Cancel Booking");
				    System.out.println("2. Cancel Flight");
				    int cancelChoice = scanner.nextInt();
				    scanner.nextLine(); // Consume newline
				    switch (cancelChoice) {
				        case 1:
				        Booking b2 = new Booking("", "", "", ' ', "", '\0', "", 0, 0,  0, "");
				        b2.cancelBooking();
				        break;
				
				        case 2:
				            f2.cancelFlight();
				            break;
				        default:
				            System.out.println("Invalid cancellation option.");
				    }
				    break;


                case 9:
                    System.out.println("Exiting the menu. Goodbye!");
                    break;
                    
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            			}
            		}
            catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Consume the invalid input
                choice = -1; // Set choice to an invalid option to continue the loop
            }

           
       
        } while (choice != 9);
        scanner.close();
    }
}
