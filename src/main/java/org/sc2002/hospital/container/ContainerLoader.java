package org.sc2002.hospital.container;
import org.sc2002.hospital.container.data.AppointmentContainer;
import org.sc2002.hospital.container.data.AppointmentOutcomeContainer;
import org.sc2002.hospital.container.data.InventoryContainer;
import org.sc2002.hospital.container.data.MedicalContainer;
import org.sc2002.hospital.container.data.PrescriptionContainer;
import org.sc2002.hospital.container.data.ReplenishContainer;
import org.sc2002.hospital.container.user.PatientContainer;
import org.sc2002.hospital.container.user.StaffContainer;
public class ContainerLoader {
    private RecordContainer[] containers={
        new AppointmentContainer(),
        new AppointmentOutcomeContainer(),
        new InventoryContainer(),
        new MedicalContainer(),
        new PrescriptionContainer(),
        new ReplenishContainer(),
        new PatientContainer(),
        new StaffContainer()
    };

    private String[] csvPaths;
    public ContainerLoader(String[] csvPaths) {
        this.csvPaths = csvPaths;
        readCSVs();
    }

    public final void readCSVs() {
        for (int i=0;i<csvPaths.length;i++) {
            containers[i].readCSV(csvPaths[i]);
        }
    }

    public void writeCSVs() {
        for (int i=0;i<csvPaths.length;i++) {
            containers[i].writeCSV(csvPaths[i]);
        }
    }

    public AppointmentContainer getAppointmentContainer() {
        return (AppointmentContainer) containers[0];
    }

    public AppointmentOutcomeContainer getAppointmentOutcomeContainer() {
        return (AppointmentOutcomeContainer) containers[1];
    }

    public InventoryContainer getInventoryContainer() {
        return (InventoryContainer) containers[2];
    }

    public MedicalContainer getMedicalContainer() {
        return (MedicalContainer) containers[3];
    }

    public PrescriptionContainer getPrescriptionContainer() {
        return (PrescriptionContainer) containers[4];
    }

    public ReplenishContainer getReplenishContainer() {
        return (ReplenishContainer) containers[5];
    }

    public PatientContainer getPatientContainer() {
        return (PatientContainer) containers[6];
    }

    public StaffContainer getStaffContainer() {
        return (StaffContainer) containers[7];
    }
}
