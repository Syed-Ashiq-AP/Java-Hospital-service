
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Hashtable;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

/**
 * Patient
 */
class Patient {
    int id;
    String name, address, phone, pass;

    Patient(int id, String name, String address, String phone, String pass) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.pass = pass;
    }

    public String Show() {
        return "\nID: " + id + "\nName: " + name + "\nAddress: " + address + "\nPhone: " + phone;
    }

}

/**
 * Doctor
 */
class Doctor {
    int id, number;
    String name, specialist;

    Doctor(int id, String name, String specialist, int number) {
        this.id = id;
        this.name = name;
        this.specialist = specialist;
        this.number = number;
    }

    public String Show() {
        return "\nID: " + id + "\nName: " + name + "\nSpecialist: " + specialist + "\nNumber: " + number;

    }

}

/**
 * Appointment
 */
class Appointment {

    int id, doctorId, patientId, number;
    String date, reason, status, price;

    Appointment(int id, String reason, int patientId, int doctorId, int number, String date, String status,
            String price) {
        this.id = id;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.number = number;
        this.date = date;
        this.reason = reason;
        this.status = status;
        this.price = price;
    }

    public String Show() {
        return "\nID: " + id + "\nDoctor ID: " + doctorId + "\nPatient ID: " + patientId + "\nNumber: " + number
                + "\nDate: " + date + "\nReason: " + reason + "\nStatus: " + status + "\nPrice: " + price + " Rs";
    }

}

public class App {
    static Vector<Patient> patients = new Vector<>();
    static Vector<Doctor> doctors = new Vector<>();
    static Vector<Appointment> appointments = new Vector<>();
    static Scanner inp = new Scanner(System.in);
    static String[] statusList = { "Open ", "Booked", "Cancelled", "Pending Payment" };

    static File fdata = new File("data.txt");

    static Scanner fscanner;

    static Hashtable<String, String> data = new Hashtable<String, String>();

    public static void main(String[] args) {
        LoadData();
        int option;
        System.out.println("Medical Service System");
        System.out.println("Are you admin(1) | patient(2) | doctor(3)?");
        option = inp.nextInt();
        if (option == 1) {
            displayOption(1);
        } else if (option == 2) {
            displayOption(2);
        } else if (option == 3) {
            displayOption(3);
        }
        Write();  
    }

    static void LoadData() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("./data.txt"));
            String construct = "";
            while (reader.ready()) {
                String line = reader.readLine();
                if (line.equals("<--Patients-->")) {
                    construct = "Patients";
                    continue;
                }
                if (line.equals("<--Doctors-->")) {
                    construct = "Doctors";
                    continue;
                }
                if (line.equals("<--Appointments-->")) {
                    construct = "Appointments";
                    continue;
                }
                System.out.println(line);
                if (construct.equals("Patients")) {
                    String[] data = line.split(",");
                    Patient p = new Patient(Integer.parseInt(data[0]), data[1], data[3], data[2], data[4]);
                    patients.add(p);
                } else if (construct.equals("Doctors")) {
                    String[] data = line.split(",");
                    Doctor d = new Doctor(Integer.parseInt(data[0]), data[1], data[2], Integer.parseInt(data[3]));
                    doctors.add(d);
                } else if (construct.equals("Appointments")) {
                    String[] data = line.split(",");
                    Appointment a = new Appointment(Integer.parseInt(data[0]), data[1], Integer.parseInt(data[2]),
                            Integer.parseInt(data[3]), Integer.parseInt(data[4]), data[5], data[6], data[7]);
                    appointments.add(a);
                }
            }

            reader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void Write() {
        try {
            FileWriter writer = new FileWriter("data.txt");
            writer.write("<--Patients-->\n");
            for (Patient p : patients) {
                writer.write(p.id + "," + p.name + "," + p.phone + "," + p.address + "," + p.pass + "\n");
            }
            writer.write("<--Doctors-->\n");
            for (Doctor d : doctors) {
                writer.write(d.id + "," + d.name + "," + d.specialist + "," + d.number + "\n");
            }
            writer.write("<--Appointments-->\n");
            for (Appointment a : appointments) {
                writer.write(a.id + "," + a.reason + "," + a.patientId + "," + a.doctorId + "," + a.number + ","
                        + a.date + "," + a.status + "," + a.price + "\n");
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void displayOption(int o) {
        inp.nextLine();
        Boolean l = true;
        while (l) {

            switch (o) {
                case 1:
                    System.out.println("Enter Admin Pin:");
                    int pass = inp.nextInt();
                    if (pass == 2024) {
                        while (l) {
                            System.out.println("\n1. Manage Doctors\n2. Manage Patients\n3. Exit");
                            int option = inp.nextInt();
                            switch (option) {
                                case 1:
                                    ShowDoctors();
                                    break;
                                case 2:
                                    ShowPatients();
                                    break;
                                case 3:
                                    System.out.println("Exit");
                                    l = false;
                                    break;
                                default:
                                    System.out.println("Invalid option!");
                                    break;
                            }
                        }

                    } else {
                        displayOption(o);
                    }
                    
                    break;

                case 2:
                    System.out.println("Enter Patient Name:");
                    String PatName = inp.nextLine();
                    System.out.println("Enter Password:");
                    String PatPass = inp.nextLine();
                    int ID = 0;
                    Boolean found = false;
                    for (Patient p : patients) {
                        if (p.name.equals(PatName) && p.pass.equals(PatPass)) {
                            found = true;
                            ID = p.id;
                            break;
                        }
                    }
                    if (found) {
                        l = false;
                        DisplayPatient(ID - 1);
                    } else {
                        System.out.println("Patient Name (or) Password is wrong!");
                    }
                    
                    break;

                case 3:
                    System.out.println("Enter Doctor Name:");
                    String DocName = inp.nextLine();
                    for (Doctor d : doctors) {
                        if (d.name.equals(DocName)) {
                            DisplayDoctor(d.id - 1);
                        }
                    }
                    break;

                default:
                    break;
            }
        }

    }

    static void ShowDoctors() {
        Boolean l = true;
        while (l) {

            System.out.println("Doctors:");
            for (Doctor a : doctors) {
                System.out.println(a.Show());
            }
            System.out.println("\n1. Add Doctor\n2. Update Doctor\n3. Back");
            int option = inp.nextInt();
            switch (option) {
                case 1:
                    AddDoctor();
                    break;
                case 2:
                    UpdateDoctor();
                    break;
                case 3:
                    System.out.println("Exit");
                    l = false;
                    break;
                default:
                    System.out.println("Invalid option!");
                    break;
            }

        }

    }

    private static void AddDoctor() {
        System.out.println("Enter the name of the Doctor:");
        inp.nextLine();
        String DocName = inp.nextLine();
        System.out.println("Enter the specialization of the Doctor:");
        String DocSpec = inp.nextLine();
        System.out.println("Enter the Number of the Doctor:");
        int DocNum = inp.nextInt();
        doctors.add(new Doctor(doctors.size() + 1, DocName, DocSpec, DocNum));
        // write();

    }

    static void UpdateDoctor() {

        System.out.println("Enter Index of Doctor to Update:");
        int ID = inp.nextInt();

        Boolean l = true;
        while (l) {

            System.out.println("Selected: \n" + doctors.get(ID - 1).Show());
            System.out.println("\nWant to update;");
            System.out.println("1. Name\n2. specialization\n3. Phone\n4. Back");
            int option = inp.nextInt();
            switch (option) {
                case 1:
                    String DocName;
                    System.out.println("Enter Name of the Doctor: ");
                    inp.nextLine();
                    DocName = inp.nextLine();
                    doctors.get(ID - 1).name = DocName;
                    break;
                case 2:
                    String DocSpe;
                    System.out.println("Enter specialization of the Doctor: ");
                    inp.nextLine();
                    DocSpe = inp.nextLine();
                    doctors.get(ID - 1).specialist = DocSpe;
                    break;
                case 3:
                    String DocNumber = "";
                    inp.nextLine();
                    while (DocNumber.length() != 10) {
                        System.out.println("Enter Phone of the Doctor: ");
                        DocNumber = inp.nextLine();
                    }
                    doctors.get(ID - 1).number = Integer.parseInt(DocNumber);

                    break;
                case 4:
                    l = false;
                    break;

                default:
                    break;
            }
        }
        // write();
    }

    static void ShowPatients() {
        Boolean l = true;
        while (l) {
            System.out.println("\nPatients:");
            for (Patient a : patients) {
                System.out.println(a.Show());
            }
            System.out.println("\n1. Add Patient\n2. Update Patient\n3. Back");
            int option = inp.nextInt();
            switch (option) {
                case 1:
                    AddPatient();
                    break;
                case 2:
                    UpdatePatient();
                    break;
                case 3:
                    System.out.println("Exit");
                    l = false;
                    break;
                default:
                    System.out.println("Invalid option!");
                    break;
            }

        }
    }

    static void AddPatient() {
        String PatName, PatAdd, PatPhone = "", PatPass;
        System.out.println("Enter Name of the Patient:");
        inp.nextLine();
        PatName = inp.nextLine();
        while (PatPhone.length() != 10) {
            System.out.println("Enter Phone of the Patient:");
            PatPhone = inp.nextLine();
        }
        System.out.println("Enter Address of the Patient:");
        PatAdd = inp.nextLine();

        System.out.println("Enter Password: ");
        PatPass = inp.nextLine();
        patients.add(new Patient(patients.size() + 1, PatName, PatAdd, PatPhone, PatPass));
    }

    static void UpdatePatient() {
        int ID;
        System.out.println("Enter ID of the Patient to Update:");
        ID = inp.nextInt();
        Boolean l = true;
        while (l) {

            System.out.println("Selected: \n" + patients.get(ID - 1).Show());
            System.out.println("\nWant to update;");
            System.out.println("1. Name\n2. Address\n3. Phone\n4. Back");
            int option = inp.nextInt();
            switch (option) {
                case 1:
                    String PatName;
                    System.out.println("Enter Name of the Patient: ");
                    inp.nextLine();
                    PatName = inp.nextLine();
                    patients.get(ID - 1).name = PatName;
                    break;
                case 2:
                    String PatAdd;
                    System.out.println("Enter Address of the Patient: ");
                    inp.nextLine();
                    PatAdd = inp.nextLine();
                    patients.get(ID - 1).address = PatAdd;
                    break;
                case 3:
                    String PatPhone = "";
                    inp.nextLine();
                    while (PatPhone.length() != 10) {
                        System.out.println("Enter Phone of the Patient: ");
                        PatPhone = inp.nextLine();
                    }
                    patients.get(ID - 1).phone = PatPhone;

                    break;
                case 4:
                    l = false;
                    break;

                default:
                    break;
            }
        }
    }

    static void DisplayPatient(int ind) {
        Patient p = patients.get(ind);
        System.out.println("\nWelcome " + p.name + "!");
        System.out.println(p.Show());
        Boolean l = true;
        while (l) {
            System.out.println("\n1. Apply for Appointment\n2. Check Appointment\n3. Exit");
            int o = inp.nextInt();

            switch (o) {
                case 1:
                    ApplyAppointment(ind);
                    // write();
                    break;
                case 2:
                    CheckAppointment(ind);
                    // write();
                    break;
                case 3:
                    l = false;
                    break;
                default:
                    System.out.println("Invalid option!");
                    break;
            }
        }
    }

    static void ApplyAppointment(int ind) {

        System.out.println("Doctors:");
        for (Doctor a : doctors) {
            System.out.println(a.Show());
        }
        System.out.println("\nEnter Index to Apply for: ");
        int Doci = inp.nextInt();
        try {
            doctors.get(Doci - 1);
            System.out.println("Enter Reason for Appointment:");
            inp.nextLine();
            String reason = inp.nextLine();
            System.out.println("Enter Date for Appointment:");
            String date = inp.nextLine();
            Appointment app = new Appointment(appointments.size() + 1, reason, ind, Doci, new Random().nextInt(1000),
                    date, "Pending", "-");
            appointments.add(app);
            System.out.println("\nAppointment Applied!");

            System.out.println("\nAppointment Details:\n" + app.Show());
        } catch (Exception e) {
            System.out.println("Invalid Doctor!");
        }
        // write();
    }

    static void CheckAppointment(int ind) {
        Boolean l = true;
        while (l) {

            System.out.println("Appointments:\n");
            for (Appointment a : appointments) {
                if (a.patientId == (ind + 1)) {
                    System.out.println(a.Show());
                }
            }
            System.out.println("\nEnter Index number:(-1 to go back)");
            int i = inp.nextInt();
            if (i < 0) {
                l = false;
            } else {
                System.out.println("Selected: " + appointments.get(i - 1).Show());
                System.out.println("\n1. Cancel Appointment\n2. Back");
                int option = inp.nextInt();
                switch (option) {
                    case 1:
                        appointments.get(i - 1).status = "Cancelled";
                        // write();
                        break;
                    case 2:
                        l = false;
                        break;
                    default:
                        System.out.println("Invalid option!");
                        break;
                }
            }
        }

    }

    static void DisplayDoctor(int ind) {
        Doctor d = doctors.get(ind);
        System.out.println("\nWelcome " + d.name + "!");
        System.out.println(d.Show());
        ManageAppointment(ind);

    }

    static void ManageAppointment(int ind) {
        Boolean l = true;
        while (l) {
            System.out.println("\nAppointments: \n");
            for (Appointment a : appointments) {
                if (a.doctorId == (ind + 1)) {
                    System.out.println(a.Show());
                }
            }
            System.out.println("Enter ID of Appointment to Manage (-1 to exit):");

            int i = inp.nextInt();
            if (i < 0) {
                l = false;
                return;
            }
            System.out.println(
                    "Selected: \n" + appointments.get(i - 1).Show());
            System.out.println(
                    "\nEnter Staus of Appointment:(1. Open , 2. Booked , 3. Cancelled, 4. Pending Payment, 5. Back )");
            int s = inp.nextInt();
            if (s == 5) {
                l = false;
                return;
            }
            appointments.get(i - 1).status = statusList[s - 1];
            if (s == 4) {
                System.out.println("Enter Price:");
                inp.nextLine();
                String price = inp.nextLine();
                System.out.println(price);
                appointments.get(i - 1).price = price;
            }

        }
        // write();
    }

}