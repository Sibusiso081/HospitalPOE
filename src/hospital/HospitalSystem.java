/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hospital;

import java.util.ArrayList;
import java.util.Comparator;

public class HospitalSystem {

    private ArrayList<Patient> patients;
    private Bed[][] beds;

    public HospitalSystem() {

        patients = new ArrayList<>();

        beds = new Bed[4][5];

        int number = 1;

        for (int row = 0; row < 4; row++) {

            for (int col = 0; col < 5; col++) {

                beds[row][col] =
                        new Bed(String.format("B%02d", number));

                number++;
            }
        }
    }

    public boolean registerPatient(Patient patient) {

        if (searchPatient(patient.getPatientId()) != null) {
            return false;
        }

        patients.add(patient);

        return true;
    }

    public Patient searchPatient(String patientId) {

        for (Patient patient : patients) {

            if (patient.getPatientId()
                    .equalsIgnoreCase(patientId)) {

                return patient;
            }
        }

        return null;
    }

    public boolean updatePatient(String patientId,
                                 String firstName,
                                 String lastName,
                                 int age,
                                 String gender,
                                 String medicalCondition) {

        Patient patient = searchPatient(patientId);

        if (patient == null) {
            return false;
        }

        patient.setFirstName(firstName);
        patient.setLastName(lastName);
        patient.setAge(age);
        patient.setGender(gender);
        patient.setMedicalCondition(medicalCondition);

        return true;
    }

    public boolean deletePatient(String patientId) {

        Patient patient = searchPatient(patientId);

        if (patient == null) {
            return false;
        }

        if (patient instanceof Inpatient) {

            Inpatient inpatient = (Inpatient) patient;

            if (!inpatient.getBedNumber().equals("None")) {
                releaseBed(patientId);
            }
        }

        patients.remove(patient);

        return true;
    }

    private Bed findBed(String bedNumber) {

        for (int row = 0; row < 4; row++) {

            for (int col = 0; col < 5; col++) {

                if (beds[row][col]
                        .getBedNumber()
                        .equalsIgnoreCase(bedNumber)) {

                    return beds[row][col];
                }
            }
        }

        return null;
    }

    public boolean allocateBed(String patientId,
                               String bedNumber) {

        Patient patient = searchPatient(patientId);

        if (!(patient instanceof Inpatient)) {
            return false;
        }

        Inpatient inpatient = (Inpatient) patient;

        if (!inpatient.getBedNumber().equals("None")) {
            return false;
        }

        Bed bed = findBed(bedNumber);

        if (bed == null || bed.isOccupied()) {
            return false;
        }

        if (bed.allocate(patientId)) {

            inpatient.setBedNumber(
                    bedNumber.toUpperCase());

            return true;
        }

        return false;
    }

    public boolean releaseBed(String patientId) {

        Patient patient = searchPatient(patientId);

        if (!(patient instanceof Inpatient)) {
            return false;
        }

        Inpatient inpatient = (Inpatient) patient;

        String bedNumber = inpatient.getBedNumber();

        if (bedNumber.equals("None")) {
            return false;
        }

        Bed bed = findBed(bedNumber);

        if (bed == null) {
            return false;
        }

        bed.release();
        
        inpatient.setBedNumber("None");

        return true;
    }

    public void displayAllPatients() {

        if (patients.isEmpty()) {

            System.out.println("No patients registered.");
            return;
        }

        for (Patient patient : patients) {

            System.out.println("-----------------------------");

            patient.displayDetails();
        }
    }

    // DISPLAY ONE PATIENT
    public void displayPatient(String patientId) {

        Patient patient = searchPatient(patientId);

        if (patient == null) {

            System.out.println("Patient not found.");

        } else {

            patient.displayDetails();
        }
    }

    public void displayWardLayout() {

        System.out.println("\nWARD 1 - 4 x 5 LAYOUT");

        for (int row = 0; row < 4; row++) {

            for (int col = 0; col < 5; col++) {

                Bed bed = beds[row][col];

                if (bed.isOccupied()) {

                    System.out.print(
                            "[" + bed.getBedNumber()
                            + "-O] ");

                } else {

                    System.out.print(
                            "[" + bed.getBedNumber()
                            + "-A] ");
                }
            }

            System.out.println();
        }

        System.out.println("A = Available, O = Occupied");
    }

    public void displayAvailableBeds() {

        System.out.println("Available Beds:");

        boolean found = false;

        for (int row = 0; row < 4; row++) {

            for (int col = 0; col < 5; col++) {

                if (!beds[row][col].isOccupied()) {

                    System.out.print(
                            beds[row][col].getBedNumber()
                            + " ");

                    found = true;
                }
            }
        }

        if (!found) {

            System.out.println("No beds available.");

        } else {

            System.out.println();
        }
    }

    public void displayOccupiedBeds() {

        System.out.println("Occupied Beds:");

        boolean found = false;

        for (int row = 0; row < 4; row++) {

            for (int col = 0; col < 5; col++) {

                if (beds[row][col].isOccupied()) {

                    System.out.println(
                            beds[row][col].getBedNumber()
                            + " - Patient ID: "
                            + beds[row][col].getPatientId());

                    found = true;
                }
            }
        }

        if (!found) {
            System.out.println("No occupied beds.");
        }
    }

    public int getTotalPatients() {

        return patients.size();
    }

    public int getOccupiedBeds() {

        int count = 0;

        for (int row = 0; row < 4; row++) {

            for (int col = 0; col < 5; col++) {

                if (beds[row][col].isOccupied()) {
                    count++;
                }
            }
        }

        return count;
    }

    public int getAvailableBeds() {

        return 20 - getOccupiedBeds();
    }

    public double getOccupancyPercentage() {

        return (getOccupiedBeds() / 20.0) * 100;
    }

    public void sortBySurname() {

        patients.sort(
                Comparator.comparing(
                        Patient::getLastName,
                        String.CASE_INSENSITIVE_ORDER));
    }

    public void sortByPatientId() {

        patients.sort(
                Comparator.comparing(
                        Patient::getPatientId,
                        String.CASE_INSENSITIVE_ORDER));
    }
    
    public boolean isBedOccupied(String bedNumber) {

        Bed bed = findBed(bedNumber);

        return bed != null && bed.isOccupied();
    }
}
    
    
    

