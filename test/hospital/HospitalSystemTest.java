/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hospital;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class HospitalSystemTest {
    
    @Test
    public void testRegisterPatient() {

        HospitalSystem hospital =
                new HospitalSystem();

        Patient patient =
                new Patient(
                        "P001",
                        "John",
                        "Smith",
                        25,
                        "Male",
                        "Flu",
                        PatientCategory.OUTPATIENT);

        assertTrue(
                hospital.registerPatient(patient));
    }

    @Test
    public void testSearchPatient() {

        HospitalSystem hospital =
                new HospitalSystem();

        Patient patient =
                new Patient(
                        "P002",
                        "Mary",
                        "Jones",
                        30,
                        "Female",
                        "Fever",
                        PatientCategory.OUTPATIENT);

        hospital.registerPatient(patient);

        Patient result =
                hospital.searchPatient("P002");

        assertNotNull(result);

        assertEquals(
                "Mary",
                result.getFirstName());
    }

    @Test
    public void testUpdatePatient() {

        HospitalSystem hospital =
                new HospitalSystem();

        Patient patient =
                new Patient(
                        "P003",
                        "John",
                        "Brown",
                        20,
                        "Male",
                        "Flu",
                        PatientCategory.OUTPATIENT);

        hospital.registerPatient(patient);

        boolean result =
                hospital.updatePatient(
                        "P003",
                        "James",
                        "Brown",
                        21,
                        "Male",
                        "Cold");

        assertTrue(result);

        assertEquals(
                "James",
                hospital.searchPatient("P003")
                        .getFirstName());
    }

    @Test
    public void testDeletePatient() {

        HospitalSystem hospital =
                new HospitalSystem();

        Patient patient =
                new Patient(
                        "P004",
                        "Peter",
                        "Mokoena",
                        40,
                        "Male",
                        "Flu",
                        PatientCategory.OUTPATIENT);

        hospital.registerPatient(patient);

        assertTrue(
                hospital.deletePatient("P004"));

        assertNull(
                hospital.searchPatient("P004"));
    }

    @Test
    public void testAllocateBed() {

        HospitalSystem hospital =
                new HospitalSystem();

        Inpatient patient =
                new Inpatient(
                        "P005",
                        "David",
                        "Smith",
                        45,
                        "Male",
                        "Pneumonia",
                        1);

        hospital.registerPatient(patient);

        assertTrue(
                hospital.allocateBed(
                        "P005",
                        "B01"));

        assertTrue(
                hospital.isBedOccupied("B01"));
    }

    @Test
    public void testReleaseBed() {

        HospitalSystem hospital =
                new HospitalSystem();

        Inpatient patient =
                new Inpatient(
                        "P006",
                        "Sarah",
                        "Jones",
                        35,
                        "Female",
                        "Flu",
                        1);

        hospital.registerPatient(patient);

        hospital.allocateBed("P006","B02");

        assertTrue(hospital.releaseBed("P006"));

        assertFalse(hospital.isBedOccupied("B02"));
    }

    @Test
    public void testPreventDuplicatePatientID() {

        HospitalSystem hospital =
                new HospitalSystem();

        Patient patient1 =
                new Patient(
                        "P007",
                        "John",
                        "Smith",
                        20,
                        "Male",
                        "Flu",
                        PatientCategory.OUTPATIENT);

        Patient patient2 =
                new Patient(
                        "P007",
                        "Peter",
                        "Jones",
                        30,
                        "Male",
                        "Cold",
                        PatientCategory.OUTPATIENT);

        assertTrue(
                hospital.registerPatient(patient1));

        assertFalse(
                hospital.registerPatient(patient2));
    }

    @Test
    public void testPreventOccupiedBed() {

        HospitalSystem hospital =
                new HospitalSystem();

        Inpatient patient1 =
                new Inpatient(
                        "P008",
                        "John",
                        "Smith",
                        30,
                        "Male",
                        "Flu",
                        1);

        Inpatient patient2 =
                new Inpatient(
                        "P009",
                        "Peter",
                        "Jones",
                        40,
                        "Male",
                        "Cold",
                        1);

        hospital.registerPatient(patient1);
        hospital.registerPatient(patient2);

        assertTrue(
                hospital.allocateBed(
                        "P008",
                        "B01"));

        assertFalse(
                hospital.allocateBed(
                        "P009",
                        "B01"));
    }

    @Test
    public void testPreventOutpatientBedAllocation() {

        HospitalSystem hospital =
                new HospitalSystem();

        Patient patient =
                new Patient(
                        "P010",
                        "James",
                        "Brown",
                        25,
                        "Male",
                        "Flu",
                        PatientCategory.OUTPATIENT);

        hospital.registerPatient(patient);

        assertFalse(
                hospital.allocateBed(
                        "P010",
                        "B01"));
    }

    @Test
    public void testAllBedsOccupied() {

        HospitalSystem hospital =
                new HospitalSystem();

        for (int i = 1; i <= 20; i++) {

            String id =
                    String.format("P%03d", i);

            Inpatient patient =
                    new Inpatient(
                            id,
                            "Patient",
                            "Test",
                            20,
                            "Male",
                            "Condition",
                            1);

            hospital.registerPatient(patient);

            String bed =
                    String.format("B%02d", i);

            assertTrue(
                    hospital.allocateBed(
                            id,
                            bed));
        }

        Inpatient extraPatient =
                new Inpatient(
                        "P021",
                        "Extra",
                        "Patient",
                        20,
                        "Male",
                        "Condition",
                        1);

        hospital.registerPatient(extraPatient);

        assertFalse(
                hospital.allocateBed(
                        "P021",
                        "B01"));
    }

    @Test
    public void testSortBySurname() {

        HospitalSystem hospital =
                new HospitalSystem();

        Patient patient1 =
                new Patient(
                        "P011",
                        "John",
                        "Zulu",
                        20,
                        "Male",
                        "Flu",
                        PatientCategory.OUTPATIENT);

        Patient patient2 =
                new Patient(
                        "P012",
                        "Peter",
                        "Adams",
                        25,
                        "Male",
                        "Cold",
                        PatientCategory.OUTPATIENT);

        hospital.registerPatient(patient1);
        hospital.registerPatient(patient2);

        hospital.sortBySurname();

        assertEquals(
                "Adams",
                hospital.searchPatient("P012")
                        .getLastName());
    }
}
    

