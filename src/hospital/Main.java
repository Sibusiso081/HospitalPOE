/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hospital;

import java.util.Scanner;

public class Main {
    
    private static Scanner scanner = new Scanner(System.in);

    private static HospitalSystem hospital =
            new HospitalSystem();

    public static void main(String[] args) {

        int choice;

        do {

            showMenu();

            choice = readInt("Enter your choice: ");

            System.out.println();

            switch (choice) {

                case 1:
                    registerPatient();
                    break;

                case 2:
                    searchPatient();
                    break;

                case 3:
                    updatePatient();
                    break;

                case 4:
                    deletePatient();
                    break;

                case 5:
                    hospital.displayAllPatients();
                    break;

                case 6:
                    allocateBed();
                    break;

                case 7:
                    releaseBed();
                    break;

                case 8:
                    hospital.displayWardLayout();
                    break;

                case 9:
                    hospital.displayAvailableBeds();
                    break;

                case 10:
                    hospital.displayOccupiedBeds();
                    break;

                case 11:
                    showReports();
                    break;

                case 12:
                    sortPatients();
                    break;

                case 0:
                    System.out.println(
                            "Program closed.");
                    break;

                default:
                    System.out.println(
                            "Invalid choice.");
            }

            System.out.println();

        } while (choice != 0);
    }

    public static void showMenu() {

        System.out.println(
                "====================================");

        System.out.println(
                " MEDICARE HOSPITAL PATIENT SYSTEM");

        System.out.println(
                "====================================");

        System.out.println("1. Register Patient");
        System.out.println("2. Search Patient");
        System.out.println("3. Update Patient");
        System.out.println("4. Delete Patient");
        System.out.println("5. Display All Patients");
        System.out.println("6. Allocate Bed");
        System.out.println("7. Release Bed");
        System.out.println("8. Display Ward Layout");
        System.out.println("9. Display Available Beds");
        System.out.println("10. Display Occupied Beds");
        System.out.println("11. Reports");
        System.out.println("12. Sort Patients");
        System.out.println("0. Exit");
    }

    public static void registerPatient() {

        String id =
                readString("Patient ID: ");

        String first =
                readString("First Name: ");

        String last =
                readString("Last Name: ");

        int age =
                readInt("Age: ");

        String gender =
                readString("Gender: ");

        String condition =
                readString("Medical Condition: ");

        PatientCategory category =
                readCategory();

        Patient patient;

        if (category == PatientCategory.INPATIENT) {

            patient = new Inpatient(
                    id,
                    first,
                    last,
                    age,
                    gender,
                    condition,
                    1);

        } else {

            patient = new Patient(
                    id,
                    first,
                    last,
                    age,
                    gender,
                    condition,
                    category);
        }

        if (hospital.registerPatient(patient)) {

            System.out.println(
                    "Patient registered successfully.");

        } else {

            System.out.println(
                    "Patient ID already exists.");
        }
    }

    public static PatientCategory readCategory() {

        while (true) {

            System.out.println("1. Inpatient");
            System.out.println("2. Outpatient");
            System.out.println("3. Emergency");

            int choice =
                    readInt("Choose category: ");

            if (choice == 1) {
                return PatientCategory.INPATIENT;
            }

            if (choice == 2) {
                return PatientCategory.OUTPATIENT;
            }

            if (choice == 3) {
                return PatientCategory.EMERGENCY;
            }

            System.out.println(
                    "Invalid category.");
        }
    }

    public static void searchPatient() {

        String id =
                readString("Enter Patient ID: ");

        hospital.displayPatient(id);
    }

    public static void updatePatient() {

        String id =
                readString(
                        "Enter Patient ID to update: ");

        Patient patient =
                hospital.searchPatient(id);

        if (patient == null) {

            System.out.println(
                    "Patient not found.");

            return;
        }

        String first =
                readString("New First Name: ");

        String last =
                readString("New Last Name: ");

        int age =
                readInt("New Age: ");

        String gender =
                readString("New Gender: ");

        String condition =
                readString(
                        "New Medical Condition: ");

        if (hospital.updatePatient(
                id,
                first,
                last,
                age,
                gender,
                condition)) {

            System.out.println(
                    "Patient updated successfully.");
        }
    }

    public static void deletePatient() {

        String id =
                readString(
                        "Enter Patient ID to delete: ");

        if (hospital.deletePatient(id)) {

            System.out.println(
                    "Patient deleted successfully.");

        } else {

            System.out.println(
                    "Patient not found.");
        }
    }

    public static void allocateBed() {

        String id =
                readString("Enter Inpatient ID: ");

        hospital.displayAvailableBeds();

        String bed =
                readString(
                        "Enter bed number, e.g. B01: ");

        if (hospital.allocateBed(id, bed)) {

            System.out.println(
                    "Bed allocated successfully.");

        } else {

            System.out.println(
                    "Bed allocation failed. Check that "
                    + "the patient is an inpatient and "
                    + "the bed is available.");
        }
    }

    public static void releaseBed() {

        String id =
                readString("Enter Patient ID: ");

        if (hospital.releaseBed(id)) {

            System.out.println(
                    "Bed released successfully.");

        } else {

            System.out.println(
                    "Bed could not be released.");
        }
    }

    public static void showReports() {

        System.out.println(
                "========== WARD REPORT ==========");

        System.out.println(
                "Total registered patients: "
                + hospital.getTotalPatients());

        System.out.println(
                "Total available beds: "
                + hospital.getAvailableBeds());

        System.out.println(
                "Total occupied beds: "
                + hospital.getOccupiedBeds());

        System.out.printf(
                "Ward occupancy: %.2f%%%n",
                hospital.getOccupancyPercentage());

        hospital.displayAvailableBeds();

        hospital.displayOccupiedBeds();
    }

    public static void sortPatients() {

        System.out.println(
                "1. Sort by surname");
        
        System.out.println(
                "2. Sort by Patient ID");

        int choice =
                readInt("Choose sorting option: ");

        if (choice == 1) {

            hospital.sortBySurname();

            System.out.println(
                    "Patients sorted by surname.");

            hospital.displayAllPatients();

        } else if (choice == 2) {

            hospital.sortByPatientId();

            System.out.println(
                    "Patients sorted by Patient ID.");

            hospital.displayAllPatients();

        } else {

            System.out.println(
                    "Invalid choice.");
        }
    }

    public static String readString(String message) {

        System.out.print(message);

        return scanner.nextLine();
    }

    public static int readInt(String message) {

        while (true) {

            try {

                System.out.print(message);

                return Integer.parseInt(
                        scanner.nextLine());

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid number.");
            }
        }
    }
}

