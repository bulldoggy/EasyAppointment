package easyappointmentclient;

import ejb.session.stateless.AdminEntitySessionBeanRemote;
import ejb.session.stateless.AppointmentEntitySessionBeanRemote;
import ejb.session.stateless.CustomerEntitySessionBeanRemote;
import ejb.session.stateless.ServiceProviderEntitySessionBeanRemote;
import entity.AppointmentEntity;
import entity.CustomerEntity;
import entity.ServiceProviderEntity;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.exception.AppointmentNotFoundException;
import util.exception.CustomerNotFoundException;
import static util.helper.StringUtil.serviceProviderAppointmentTableFormat;

/**
 *
 * @author zhijun
 */
public class ServiceProviderOperationMenu {
    
    private ServiceProviderEntity serviceProviderEntity;
    private ServiceProviderEntitySessionBeanRemote serviceProviderEntitySessionBeanRemote;
    private CustomerEntitySessionBeanRemote customerEntitySessionBeanRemote;
    private AppointmentEntitySessionBeanRemote appointmentEntitySessionBeanRemote;
            

    public ServiceProviderOperationMenu(ServiceProviderEntity serviceProviderEntity, ServiceProviderEntitySessionBeanRemote serviceProviderEntitySessionBeanRemote, CustomerEntitySessionBeanRemote customerEntitySessionBeanRemote, AppointmentEntitySessionBeanRemote appointmentEntitySessionBeanRemote) {
        this.serviceProviderEntity = serviceProviderEntity;
        this.serviceProviderEntitySessionBeanRemote = serviceProviderEntitySessionBeanRemote;
        this.customerEntitySessionBeanRemote = customerEntitySessionBeanRemote;
        this.appointmentEntitySessionBeanRemote = appointmentEntitySessionBeanRemote;
    }
    
    public void serviceProviderMainMenu() {
        Scanner sc = new Scanner(System.in);
        Integer response = 0;
        
        while (true) {
            System.out.println("*** Service provider terminal :: Main ***\n");
            System.out.println("You are login as " + serviceProviderEntity.getName());
            System.out.println("1: View Profile");
            System.out.println("2: Edit Profile");
            System.out.println("3: View Appointments");
            System.out.println("4: Cancel Appointments");
            System.out.println("5: Logout\n");
            response = 0;

            while (response < 1 || response > 5) {
                try {
                    System.out.print("> ");
                    response = sc.nextInt();
                } catch (InputMismatchException ex) {
                    sc.nextLine();
                }
                if (response == 1) {
                    viewProfile();
                } else if (response == 2) {
                    editProfile();
                } else if (response == 3) {
                    viewAppointments();
                } else if (response == 4) {
                    cancelAppointments();
                } else if (response == 5) {
                    break;
                } else {
                System.out.println("Invalid option! Please try again!");
                }
            }
            if (response == 5) {
                break;
            }
        }
    }
    
    public void viewProfile() {
        System.out.println("*** Service provider terminal :: View Profile ***\n");
        System.out.println(String.format("%-2s", "ID") + "| " +
                           String.format("%-20s", "Name") + " | " + 
                           String.format("%-18s", "Business Category") + " | " + 
                           String.format("%-17s", "Registration No.") + " | " +
                           String.format("%-12s", "City") + " | " + 
                           String.format("%-25s", "Address") + " | " + 
                           String.format("%-18s", "Email") + " | " +
                           String.format("%-8s", "Phone"));
        System.out.println(serviceProviderEntity.toStringWithBusinessNo());
    }
    
    public void editProfile() {
        Scanner sc = new Scanner(System.in);
        String input = "";
                
        System.out.println("*** Service provider terminal :: Edit Profile ***\n");
        System.out.println("Enter your new city: (Enter '0' if no change)");
        System.out.print("> ");
        input = sc.nextLine().trim();
        if(!input.equals("0")) {
            serviceProviderEntity.setCity(input);
        }
        System.out.println("Enter your new business address: (Enter '0' if no change)");
        System.out.print("> ");
        input = sc.nextLine().trim();
        if(!input.equals("0")) {
            serviceProviderEntity.setAddress(input);
        }
        System.out.println("Enter your new email address: (Enter '0' if no change)");
        System.out.print("> ");
        input = sc.nextLine().trim();
        if(!input.equals("0")) {
            serviceProviderEntity.setEmail(input);
        }
        System.out.println("Enter your phone number: (Enter '0' if no change)");
        System.out.print("> ");
        input = sc.nextLine().trim();
        if(!input.equals("0")) {
            serviceProviderEntity.setPhone(input);
        }
        System.out.println("Enter your new password: (Enter '0' if no change)");
        System.out.print("> ");
        input = sc.nextLine().trim();
        if(!input.equals("0")) {
            serviceProviderEntity.setPassword(input);
        }
        
        serviceProviderEntitySessionBeanRemote.updateServiceProviderEntity(serviceProviderEntity);
        System.out.println("Your information has been successfully saved!");
    }
    
    public void cancelAppointments() {
        Scanner sc = new Scanner(System.in);
        System.out.println("*** Service provider terminal :: Cancel Appointments ***\n");
        System.out.println("Appointments");
        List<AppointmentEntity> appointments = serviceProviderEntity.getAppointmentEntity();
        if(appointments.size() <= 0) {
            System.out.println("There are no appointments to show.");
        } else {
            serviceProviderAppointmentTableFormat(appointments);
        }
        
        while(true) {
            System.out.println("Enter 0 to go back to the previous menu.");
            System.out.print("Enter Appointment Id>");
            String appointmentNo = sc.nextLine();
            if (appointmentNo.equals("0")) {
                break;
            }
            try {
                AppointmentEntity cancelledAppt = appointmentEntitySessionBeanRemote.retrieveAppointmentByAppointmentNo(appointmentNo);
                CustomerEntity customerWithAppt = customerEntitySessionBeanRemote.retrieveCustomerById(cancelledAppt.getCustomerEntity().getId());
                // Remove ServiceProvider --- Appointment relationship
                cancelledAppt.setServiceProviderEntity(null);
                serviceProviderEntity.getAppointmentEntity().remove(cancelledAppt);

                // Remove ServiceProvider --- Appointment relationship
                cancelledAppt.setCustomerEntity(null);
                customerWithAppt.getAppointmentEntity().remove(cancelledAppt);
                
                appointmentEntitySessionBeanRemote.deleteAppointment(cancelledAppt.getAppointmentId());

                System.out.println("Appointment " + appointmentNo + " has been cancelled successfully.");
            } catch(AppointmentNotFoundException | CustomerNotFoundException ex) {
                ex.getMessage();
            }
        }
    }
    
    public void viewAppointments() {
        Scanner sc = new Scanner(System.in);
        System.out.println("*** Service provider terminal :: View Appointments ***\n");
        System.out.println("Appointments");
        List<AppointmentEntity> appointments = serviceProviderEntity.getAppointmentEntity();
        if(appointments.size() <= 0) {
            System.out.println("There are no appointments to show.");
        } else {
            serviceProviderAppointmentTableFormat(appointments);
        }
        
        while(true) {
            System.out.println("Enter 0 to go back to the previous menu.");
            System.out.print(">");
            String exitStr = sc.nextLine();
            if (exitStr.equals("0")) {
                break;
            }
        }
    }
}
