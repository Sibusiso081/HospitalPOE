/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hospital;

/**
 *
 * @author Student
 */
public class Bed {
    
    private String bedNumber;
    private boolean occupied;
    private String patientId;
    
    public Bed(String bedNumber) {
        
        this.bedNumber = bedNumber;
        this.occupied  = false;
        this.patientId = "";
    }
    
    public String getBedNumber() {
        return bedNumber;
    }
    
    public boolean isOccupied() {
        return patientId;
    }
    
    public String getPatientId() {
        return patientId;
    }
    
    public boolean allocate(String patientId) {
        
        if (occupied) {
            return false;
        }
        
        this.occupied = true;
        this.patientId = patientId; 
        
        return true;
    }
    
    public boolean release() {
        
        if (!occupied) {
            return false;
        }
        
        this.occupied = false;
        this.patientId = "";
        
        return true;
    }
}
