   
package Flight;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
public class Flight {

	    public String flightName;
	    public String seatNo;
	    public String flightOrigin;
	    public String flightDestination;
	    private int seatEconomy;
	    private int seatBusiness;
	    private float fareEconomy;
	    private float fareBusiness;
		private LocalTime departureTime;
		private LocalTime arrivalTime;
	    public int flightNo;
	    public String flightDate;
	    public String[] locations = new String[6];
		/*public void readFromFile(String filename) {
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                
                System.out.println(data);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }*/
	    
	
	public Flight(String flightName, String seatNo, String flightOrgin, String flightDestination,int seatEconomy, 
	int seatBusiness, float fareEconomy, float fareBusiness,float farePremium, int flightNo, int userFlightNo, 
	String flightDate, LocalTime departureTime, LocalTime arrivalTime)  {
this.flightName = flightName;
this.seatNo = seatNo;
this.flightOrigin = flightOrgin;
this.flightDestination = flightDestination;
this.seatEconomy = seatEconomy;
this.seatBusiness = seatBusiness;
this.fareEconomy = fareEconomy;
this.fareBusiness = fareBusiness;
this.flightNo = flightNo;
this.flightDate = flightDate;
this.departureTime = departureTime;
this.arrivalTime = arrivalTime;
}

	    Scanner scanner = new Scanner(System.in);
		public void setDepartureTimeFromInput() {
        try {
            System.out.print("Enter Departure Time (HH:mm): ");
            String input = scanner.nextLine();
            departureTime = LocalTime.parse(input);

        } catch (DateTimeParseException e) {
            System.out.println("Error: Invalid time format. Please enter time in HH:mm format.");
            setDepartureTimeFromInput(); // Ask for input again
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
	public void setArrivalTimeFromInput() {
        try {
            System.out.print("Enter Arrival Time (HH:mm): ");
            String input = scanner.nextLine();
            arrivalTime = LocalTime.parse(input);

        } catch (DateTimeParseException e) {
            System.out.println("Error: Invalid time format. Please enter time in HH:mm format.");
            setArrivalTimeFromInput(); // Ask for input again
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

		public void setFlightNameFromInput() {
			while (true) {
				try {
					System.out.print("Enter Name of the flight: ");
					String inputFlightName = scanner.nextLine().trim();
		
					if (inputFlightName.matches("[a-zA-Z]+") && inputFlightName.length() >= 2 && inputFlightName.length() <= 30) {
						flightName = inputFlightName;
						break;
					} else {
						System.out.println("Invalid flightName format. Please enter a valid flightName (2-30 characters, only alphabets).");
					}
				} catch (Exception e) {
					System.out.println("An error occurred: " + e.getMessage());
					
				}
			}
		}
		
		public void setSeatNoFromInput() {
			while (true) {
				try {
					System.out.print("Enter the number of seats available in the flight: ");
					String input = scanner.nextLine();
					int seats = Integer.parseInt(input);
					if (seats > 0) {
						seatNo = input;
						break;
					} else {
						System.out.println("Please enter a valid positive number for the number of seats.");
					}
				} catch (NumberFormatException e) {
					System.out.println("Invalid input. Please enter a valid positive number for the number of seats.");
				} catch (Exception e) {
					System.out.println("An error occurred: " + e.getMessage());
					
				}
			}
		}
	       
	        public void setflightDateFromInput() {
	            
	        	while (true) {
				try {
					System.out.print("Enter a date (yyyy-MM-dd): ");
					String inputflightDate = scanner.nextLine();
	
					// Validate input format
					DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					LocalDate date = LocalDate.parse(inputflightDate, dateFormatter);
	
					// Validate year greater than 2016
					if (date.getYear() < 2016) {
						throw new IllegalArgumentException("Please enter a date after 2016 (yyyy-MM-dd).");
					}
	
					this.flightDate=inputflightDate;
					break; // Exit loop if input is valid
				} catch (Exception e) {
					System.out.println("Invalid date format or year. Please enter a valid date (yyyy-MM-dd) after 2016.");
				}
			}
	        }

	        public void displaylocation()
	        {
	        	locations[0] = "ADDIS ABABA" ;
        		locations[1] = "HAWASSA" ; 
        		locations[2] = "BAHIR DAR" ;
        	   	locations[3] = "DIRE DAWA" ;
        	   	locations[4] = "MEKELLE" ;
        		locations[5] = "ARBA MINCH" ;
	            System.out.println("\n");
	        	for(int i=0;i<=5;i++){
	                System.out.println((i+1) + "."+ locations[i] );
	    			}
	        }
	        
	       public void setFlightOriginInput() {
    try {
        System.out.println("The Origin of the Flight is: ");
        displaylocation();

        int input = scanner.nextInt();

        if (input >= 1 && input <= locations.length) {
            this.flightOrigin = locations[input - 1];
        } else {
            System.out.println("Invalid input. Please enter a valid location number.");
            setFlightOriginInput(); // Ask for input again
            return;
        }
    } catch (InputMismatchException e) {
        System.out.println("Invalid input type. Please enter a valid location number.");
        scanner.nextLine(); // Clear the input buffer
        setFlightOriginInput(); // Ask for input again
        return;
    } catch (Exception e) {
        System.out.println("An error occurred: " + e.getMessage());
    }
}

public void setFlightDestinationInput() {
    try {
        System.out.println("The Destination of the Flight is: ");
        displaylocation();

        int input = scanner.nextInt();

        if (input >= 1 && input <= locations.length) {
            String selectedDestination = locations[input - 1];
            
            if (selectedDestination.equals(flightOrigin)) {
                System.out.println("Destination cannot be the same as the origin. Please choose a different location.");
                setFlightDestinationInput(); // Ask for input again
                return;
            }
            
            this.flightDestination = selectedDestination;
        } else {
            System.out.println("Invalid input. Please enter a valid location number.");
            setFlightDestinationInput(); // Ask for input again
            return;
        }
    } catch (InputMismatchException e) {
        System.out.println("Invalid input type. Please enter a valid location number.");
        scanner.nextLine(); // Clear the input buffer
        setFlightDestinationInput(); // Ask for input again
        return;
    } catch (Exception e) {
        System.out.println("An error occurred: " + e.getMessage());
    }
}


	        	
	        public void setflightNoFromInput() {
	            while (true) {
	                System.out.print("Enter the fight Number: ");
	                if (scanner.hasNextInt()) {
	                    this.flightNo = scanner.nextInt();
	                    break;
	                } else {
	                    System.out.println("Please enter a valid flightNo number.");
	                    scanner.next(); 
	                }
	            }
	        }

	      

	        
	        public void businessFareFromInput() {
	        System.out.print("Enter fare for business class: ");
	        float businessFare = scanner.nextFloat();
	        this.fareBusiness=businessFare;
	        }
	        public void fareEconomyFromInput() {
		        System.out.print("Enter fare for Economy class: ");
		        float fareEconomy = scanner.nextFloat();
		        this.fareEconomy= fareEconomy;
		        }
	        
	        public void seatBusinessFromInput() {
	        System.out.print("Enter number of business seats: ");
	        int seatBusiness = scanner.nextInt();
	        this.seatBusiness= seatBusiness;
	        scanner.nextLine();
	   
	        }
	        public void seatEconomyFromInput() {
		        System.out.print("Enter number of economy seats: ");
		        int seatEconomy = scanner.nextInt();
		        this.seatEconomy= seatEconomy;
		        scanner.nextLine();
		   
		        }

				public void modifyFlight() {
					Scanner scanner = new Scanner(System.in);
					System.out.println("Enter the flight number to modify:");
					int flightNumberToModify = scanner.nextInt();
					scanner.nextLine(); // Consume newline
				
					List<String> flightData = readFromFile();
					boolean flightFound = false;
				
					for (int i = 0; i < flightData.size(); i++) {
						String[] flightDetails = flightData.get(i).split("\t");
				
						for (int j = 0; j < flightDetails.length; j++) {
							try {
								int flightNo = Integer.parseInt(flightDetails[j].trim());
				
								if (flightNo == flightNumberToModify) {
									flightFound = true;
				
									System.out.println("Choose the attribute to modify:");
                    System.out.println("1. Flight Name");
                    System.out.println("2. Total Seat Number");
                    System.out.println("3. Fare for Business Class");
                    System.out.println("4. Fare for Economy Class");
                    System.out.println("5. Departure Time");
                    System.out.println("6. Arrival Time");

                    int choice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    switch (choice) {
                        case 1:
                            System.out.println("Enter new flight name:");
                            String newFlightName = scanner.nextLine();
                            flightDetails[0] = newFlightName;
                            break;
                        case 2:
                            System.out.println("Enter new total seat number:");
                            int newTotalSeats = scanner.nextInt();
                            scanner.nextLine(); // Consume newline
                            flightDetails[1] = String.valueOf(newTotalSeats);
                            break;
                        case 3:
                            System.out.println("Enter new fare for business class:");
                            float newBusinessFare = scanner.nextFloat();
                            flightDetails[6] = String.valueOf(newBusinessFare);
                            break;
                        case 4:
                            System.out.println("Enter new fare for economy class:");
                            float newEconomyFare = scanner.nextFloat();
                            flightDetails[7] = String.valueOf(newEconomyFare);
                            break;
                        case 5:
                            System.out.println("Enter new departure time (HH:mm):");
                            String newDepartureTime = scanner.nextLine();
                            flightDetails[10] = newDepartureTime;
                            break;
                        case 6:
                            System.out.println("Enter new arrival time (HH:mm):");
                            String newArrivalTime = scanner.nextLine();
                            flightDetails[11] = newArrivalTime;
                            break;
                        default:
                            System.out.println("Invalid choice. No modifications were made.");
                            return;
                    }
				
									if (choice >= 1 && choice <= flightDetails.length) {
										System.out.println("Enter new value for the selected attribute:");
										String newValue = scanner.nextLine();
										flightDetails[choice - 1] = newValue;
									} else {
										System.out.println("Invalid attribute number. No modifications were made.");
										return;
									}
				
									flightData.set(i, String.join("\t", flightDetails));
									break;
								}
							} catch (NumberFormatException ignored) {
								// Ignore non-integer fields
							}
						}
					}
				
					if (flightFound) {
						try (BufferedWriter writer = new BufferedWriter(new FileWriter("flight_details.txt"))) {
							for (String updatedLine : flightData) {
								writer.write(updatedLine);
								writer.newLine();
							}
							System.out.println("Flight details modified successfully.");
						} catch (IOException e) {
							System.out.println("An error occurred while updating the flight details file: " + e.getMessage());
						}
					} else {
						System.out.println("Flight number not found. No modifications were made.");
					}
				}
				
				public void cancelFlight() {
					Scanner scanner = new Scanner(System.in);
					System.out.println("Enter the flight number to cancel:");
					int flightNumberToCancel = scanner.nextInt();
					scanner.nextLine(); // Consume newline
				
					List<String> flightData = readFromFile();
					List<String> updatedFlightData = new ArrayList<>();
				
					boolean flightFound = false;
				
					for (String line : flightData) {
						String[] flightDetails = line.split("\t"); // Assuming tab-separated values
				
						boolean canceled = false;
				
						for (int i = 0; i < flightDetails.length; i++) {
							try {
								int flightNo = Integer.parseInt(flightDetails[i].trim());
								if (flightNo == flightNumberToCancel) {
									canceled = true;
									flightFound = true;
									break;
								}
							} catch (NumberFormatException ignored) {
								// Ignore non-integer fields
							}
						}
				
						if (!canceled) {
							updatedFlightData.add(line); // Add non-cancelled flights to the updated data
						}
					}
				
					if (flightFound) {
						try (BufferedWriter writer = new BufferedWriter(new FileWriter("flight_details.txt"))) {
							for (String updatedLine : updatedFlightData) {
								writer.write(updatedLine);
								writer.newLine();
							}
							System.out.println("Flight canceled successfully.");
						} catch (IOException e) {
							System.out.println("An error occurred while updating the flight details file: " + e.getMessage());
						}
					} else {
						System.out.println("Flight number not found. No cancellations were made.");
					}
				}
				
				/*private void updateFlightDetailsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("flight_details.txt"))) {
           
            writer.write(toString()); 
            writer.newLine();
        } catch (IOException e) {
            System.out.println("An error occurred while updating the flight details file: " + e.getMessage());
        }
    }*/
				
				@Override
    public String toString() {
        return  flightName +
               "\t " + seatNo +
               "\t" + flightOrigin +
               "\t " + flightDestination +
               "\t" + flightNo +
               "\t" + flightDate +
               "\t" + fareBusiness +
               "\t\t" + fareEconomy +
               "\t" + seatBusiness +
               "\t" + seatEconomy +
			   "\t\t" + departureTime +
			   "\t\t" + arrivalTime;
        
    }
	public void displayAllValues() {
		try {
			File f = new File("flight_details.txt");
			Scanner scanner = new Scanner(f);
	
			System.out.println("\nDisplaying Flight Details:");
			System.out.println("F.Name  Seat.No\tOrigin\t\tDestin.    Flight.No\tDate\t\tBusi.Fare   Eco.Fare   B.Seats   E.Seats   Depart.Time   Arriv.Time");
			System.out.println("------  -------\t------\t\t-------    ---------\t----\t\t---------   --------   -------   -------   ------------  ----------");
			while (scanner.hasNextLine()) {
				String l = scanner.nextLine();
					System.out.println(l);
				}
				scanner.close();
				
			}
	
			
		 catch (IOException e) {
			System.out.println("An error occurred while reading the flight details: " + e.getMessage());
		}
	}
	public static List<String> readFromFile() {
        List<String> flightData = new ArrayList<>();
        try {
            File file = new File("flight_details.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                flightData.add(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
        return flightData;
    }

	public static void writeToFile(String data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("flight_details.txt", true))) {
            writer.write(data + "\n");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }
	
	}

