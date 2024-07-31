package Input;
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
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
public class Booking extends People {
	    public static int bookingCounter = 1000;
	    public String departure;
	    public String arrival;
	    public int bookingId;
	    public int userFlightNo;
	    public String date;
	    public String[] locations = new String[6];
	    
	    public void readFromFile(String filename) {
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                // Process and display the data as per your requirement
                System.out.println(data);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }
	    public Booking(String name, String email, String address, char sex, String phone, int age,
		               String departure, int i, int bookingId, int userFlightNo, String date) {
			super(name, email, address, sex, phone, age);
	        this.departure = departure;
	        this.arrival = locations[i];
	        this.bookingId = generateBookingId();
	        this.userFlightNo = userFlightNo;
	        this.date = date;
	    }
		

		public String getDeparture() {
			return departure;
		}

		public String getArrival() {
			return arrival;
		}		
		public int getBookingId() {
			return bookingId;
		}

		public int generateBookingId() {
//			int bookingIdToModify = scanner.nextInt();
//			scanner.nextLine(); // Consume newline
//			
//			List<String> bookingData = readFromFile();
//			boolean bookingFound = false;
//		
//			for (int i = 0; i < bookingData.size(); i++) {
//				++bookingCounter;
//				String[] bookingDetails = bookingData.get(i).split("\t");
//		
//				int currentBookingId;
//				try {
//					currentBookingId = Integer.parseInt(bookingDetails[bookingDetails.length - 1].trim());
//				} catch (NumberFormatException e) {
//					// Skip if the booking ID is not a valid integer
//					continue;
//				}
//				if (currentBookingId == bookingIdToModify) {
//					break;}
//				}
			return bookingCounter++; 
		}
		public int displayBookingId() {
			System.out.println("Booking ID: " + bookingId);
			return bookingId;
		}

		public int getUserFlightNo() {
			return userFlightNo;
		}

		public String getDate() {
			return date;
		}
	    Scanner scanner = new Scanner(System.in);

		public void setDateFromInput() {
			while (true) {
				try {
					System.out.print("Enter a date (yyyy-MM-dd): ");
					String inputDate = scanner.nextLine();
	
					// Validate input format
					DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					LocalDate date = LocalDate.parse(inputDate, dateFormatter);
	
					// Validate year greater than 2016
					if (date.getYear() < 2016) {
						throw new IllegalArgumentException("Please enter a date after 2016 (yyyy-MM-dd).");
					}
	
					this.date = inputDate;
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
	            // System.out.println("The locations are:\n  ");
	        	for(int i=0;i<=5;i++){
	                System.out.println( (i+1) + "."+ locations[i] );
	    			}
	        }
	        
	        public void departureinput()
		        {
	        		
		            System.out.print("Your place of depature is:  ");
		            displaylocation();
		            {
		            int input = scanner.nextInt();
		            this.departure = locations[input-1];}
	        }
	        
	

			public void arrivalinput() {
				boolean validInput = false;
			
				while (!validInput) {
					try {
						System.out.print("Your place of arrival is: ");
						displaylocation();
			
						int input = scanner.nextInt();
						scanner.nextLine(); // Consume the newline character
			
						if (input < 1 || input > locations.length) {
							throw new IllegalArgumentException("Invalid location selection. Please enter a valid number.");
						}
			
						String selectedArrival = locations[input - 1];
			
						if (selectedArrival.equals(departure)) {
							throw new IllegalArgumentException("Arrival place cannot be the same as departure place. Please choose again!");
						}
			
						this.arrival = selectedArrival;
						validInput = true; // Set flag to true for valid input
					} catch (IllegalArgumentException e) {
						System.out.println(e.getMessage());
					} catch (java.util.InputMismatchException e) {
						System.out.println("Please enter a valid number.");
						scanner.nextLine(); // Consume the invalid input
					}
				}
			}
			



			
	
	        public void setUserFlightNoFromInput() {
	 
	            while (true) {
	                System.out.print("Enter Your Flight Number: ");
	                if (scanner.hasNextInt()) {
	                    this.userFlightNo = scanner.nextInt();
	                    scanner.nextLine();
	                    break;
	                } else {
	                    System.out.println("Please enter a valid flight number.");
	                    scanner.next();
	                }
	            }
	        }

			public void modifyBooking() {
				Scanner scanner = new Scanner(System.in);
				System.out.println("Enter the booking ID to modify:");
				
				if (!scanner.hasNextInt()) {
					System.out.println("Invalid input. Please enter a valid booking ID.");
					return;
				}
			
				int bookingIdToModify = scanner.nextInt();
				scanner.nextLine(); // Consume newline
			
				List<String> bookingData = readFromFile();
				boolean bookingFound = false;
			
				for (int i = 0; i < bookingData.size(); i++) {
					String[] bookingDetails = bookingData.get(i).split("\t");
			
					int currentBookingId;
					try {
						currentBookingId = Integer.parseInt(bookingDetails[bookingDetails.length - 1].trim());
					} catch (NumberFormatException e) {
						// Skip if the booking ID is not a valid integer
						continue;
					}
			
					if (currentBookingId == bookingIdToModify) {
						bookingFound = true;
			
						System.out.println("Choose the attribute to modify:");
						System.out.println("1. Name");
						System.out.println("2. Email");
						System.out.println("3. Address");
			
						int choice;
						if (!scanner.hasNextInt()) {
							System.out.println("Invalid input. Please enter a valid attribute number.");
							return;
						} else {
							choice = scanner.nextInt();
							scanner.nextLine(); // Consume newline
						}
			
						if (choice >= 1 && choice <= 3) {
							System.out.println("Enter new value for the selected attribute:");
							String newValue = scanner.nextLine();
			
							// Update the corresponding attribute
							switch (choice) {
								case 1:
									bookingDetails[0] = newValue;
									break;
								case 2:
									bookingDetails[6] = newValue;
									break;
								case 3:
									bookingDetails[7] = newValue;
									break;
							}
			
							bookingData.set(i, String.join("\t", bookingDetails));
						} else {
							System.out.println("Invalid attribute number. No modifications were made.");
							return;
						}
					}
				}
			
				if (bookingFound) {
					try (BufferedWriter writer = new BufferedWriter(new FileWriter("booking_details.txt"))) {
						for (String updatedLine : bookingData) {
							writer.write(updatedLine);
							writer.newLine();
						}
						System.out.println("Booking details modified successfully.");
					} catch (IOException e) {
						System.out.println("An error occurred while updating the booking details file: " + e.getMessage());
					}
				} else {
					System.out.println("Booking ID not found. No modifications were made.");
				}
			}

			public void cancelBooking() {
				Scanner scanner = new Scanner(System.in);
				System.out.print("Enter the booking ID to cancel: ");
				int bookingIdToCancel = scanner.nextInt();
				scanner.nextLine(); // Consume newline character
			
				List<String> bookingData = readFromFile();
				boolean bookingFound = false;
			
				for (int i = 0; i < bookingData.size(); i++) {
					String[] bookingDetails = bookingData.get(i).split("\t");
			
					int currentBookingId;
					try {
						currentBookingId = Integer.parseInt(bookingDetails[bookingDetails.length - 1].trim());
					} catch (NumberFormatException e) {
						// Skip if the booking ID is not a valid integer
						continue;
					}
			
					if (currentBookingId == bookingIdToCancel) {
						bookingFound = true;
						bookingData.remove(i); // Remove the booking from the list
						break; // Exit loop after canceling the booking
					}
				}
			
				if (bookingFound) {
					try (BufferedWriter writer = new BufferedWriter(new FileWriter("booking_details.txt"))) {
						for (String updatedLine : bookingData) {
							writer.write(updatedLine);
							writer.newLine();
						}
						System.out.println("Booking canceled successfully.");
					} catch (IOException e) {
						System.out.println("An error occurred while updating the booking details file: " + e.getMessage());
					}
				} else {
					System.out.println("Booking ID not found. No bookings were canceled.");
				}
			}
			
			
			@Override
    public String toString() {
        return name +
               "    " + sex +
               " \t " + date +
               " \t " + departure +
			   " \t " + arrival +
               " \t " + userFlightNo +
			   " \t " + phone +
			   "\t " + email +
               "\t " + address +
			   "\t " + bookingId;
        
    }

	         public void displayAllValues() {
    try {
        File file = new File("booking_details.txt");
        Scanner scanner = new Scanner(file);

		System.out.print("\n");
        System.out.println("Displaying Booking Details:");

		System.out.println("\nName\tSex\tDate\t\tDeparture\tArrival\t\tFlight No.\tPhone No.\tEmail\t\tAddress\t\tBooking_Id");
        System.out.println("------\t---\t----\t\t---------\t-------\t\t----------\t---------\t-----\t\t-------\t\t----------");
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            System.out.println(line);
        }

        scanner.close();
    } catch (IOException e) {
        System.out.println("An error occurred while reading the booking details: " + e.getMessage());
    }
}

public static List<String> readFromFile() {
        List<String> flightData = new ArrayList<>();
        try {
            File file = new File("booking_details.txt");
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
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("booking_details.txt", true))) {
            writer.write(data + "\n");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }


	}

