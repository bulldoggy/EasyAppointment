/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easyappointmentclient;

import ejb.session.stateless.AdminEntitySessionBeanRemote;
import ejb.session.stateless.AppointmentEntitySessionBeanRemote;
import ejb.session.stateless.ServiceProviderEntitySessionBeanRemote;
import ejb.session.stateless.CustomerEntitySessionBeanRemote;
import ejb.session.stateless.EmailSessionBeanRemote;
import entity.AdminEntity;
import entity.AppointmentEntity;
import entity.CustomerEntity;
import entity.ServiceProviderEntity;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import util.exception.AdminNotFoundException;
import util.exception.AppointmentNotFoundException;
import util.exception.CustomerNotFoundException;
import util.exception.InvalidLoginCredentialException;
import util.exception.ServiceProviderNotFoundException;
import util.helper.DateUtil;

/**
 *
 * @author zhijun
 */
public class Startup {
    
    private CustomerEntitySessionBeanRemote customerEntitySessionBeanRemote;
    private ServiceProviderEntitySessionBeanRemote serviceProviderEntitySessionBeanRemote;
    private AdminEntitySessionBeanRemote adminEntitySessionBeanRemote;
    private AppointmentEntitySessionBeanRemote appointmentEntitySessionBeanRemote;
    private EmailSessionBeanRemote emailSessionBeanRemote;
    
    private ServiceProviderOperationMenu serviceProviderOperationMenu;
    private AdminOperationMenu adminOperationMenu;
    private CustomerOperationMenu customerOperationMenu;
    
    private AdminEntity adminEntity;
    private ServiceProviderEntity serviceProviderEntity;
    private CustomerEntity customerEntity;
    private AppointmentEntity appointmentEntity;

    public Startup() {
    }

    public Startup(CustomerEntitySessionBeanRemote customerEntitySessionBeanRemote, ServiceProviderEntitySessionBeanRemote serviceProviderEntitySessionBeanRemote, AdminEntitySessionBeanRemote adminEntitySessionBeanRemote, AppointmentEntitySessionBeanRemote appointmentEntitySessionBeanRemote, EmailSessionBeanRemote emailSessionBeanRemote) {
        this.customerEntitySessionBeanRemote = customerEntitySessionBeanRemote;
        this.serviceProviderEntitySessionBeanRemote = serviceProviderEntitySessionBeanRemote;
        this.adminEntitySessionBeanRemote = adminEntitySessionBeanRemote;
        this.appointmentEntitySessionBeanRemote = appointmentEntitySessionBeanRemote;
        this.emailSessionBeanRemote = emailSessionBeanRemote;
    }
    

    
    public void start() {
        initAppointment();
        Scanner sc = new Scanner(System.in);
        Integer response = 0;
        
        while (true) {
            System.out.println("*** Welcome to the Easy Appointment Portal! ***");
            System.out.println("Please choose your respective terminal!\n");
            System.out.println("1: Admin terminal");
            System.out.println("2: Service provider terminal");
            System.out.println("3: Custmomer terminal");
            System.out.println("4: Exit");
            response = 0;
            
            while (response < 1 || response > 4) {
                try {
                    System.out.print("> ");
                    response = sc.nextInt();
                } catch (InputMismatchException ex) {  
                    sc.nextLine();
                }
                if (response == 1) {
                    loginTerminal("Admin");
                } else if (response == 2) {
                    loginTerminal("Service Provider");
                } else if (response == 3) {
                    loginTerminal("Customer");
                } else if (response == 4) {
                    System.out.println("Goodbye!");
                } else {
                    System.out.println("Invalid option! Please try again!\n");
                    break;
                }
            } if (response == 4) {
                break;
            }
        }
    }
    
    public void loginTerminal(String entity) {
        Scanner sc = new Scanner(System.in);
        Integer response = 0;
        
        if(entity.equals("Admin")) {
            while (true) {
                System.out.println("*** Welcome to " + entity + " terminal ***\n");
                System.out.println("1: Login");
                System.out.println("2: Exit\n");
                response = 0;

                while (response < 1 || response > 2) {
                    try {
                        System.out.print("> ");
                        response = sc.nextInt();
                    } catch (InputMismatchException ex) {  
                        sc.nextLine();
                    }
                    if (response == 1) {
                        try {
                            login(entity);
                            adminOperationMenu = new AdminOperationMenu(adminEntity, customerEntitySessionBeanRemote, serviceProviderEntitySessionBeanRemote, adminEntitySessionBeanRemote, emailSessionBeanRemote);
                            adminOperationMenu.adminOperationMainMenu();
                            System.out.println("Login successful!");
                        } catch (InvalidLoginCredentialException ex) {
                            System.out.println("Login error! " + ex.getMessage() + "\n");
                        }
                    } else if (response == 2) {
                        System.out.println("Returning to main page...\n");
                    } else {
                        System.out.println("Invalid option! Please try again!\n");
                        break;
                    }
                } if (response == 2) {
                    break;
                }
            }
        } 
        if(entity.equals("Service Provider")) {
            while (true) {
                System.out.println("*** Welcome to " + entity + " terminal ***\n");
                System.out.println("1: Registration");
                System.out.println("2: Login");
                System.out.println("3: Exit\n");
                
                while (response < 1 || response > 3) {
                    try {
                        System.out.print("> ");
                        response = sc.nextInt();
                    } catch (InputMismatchException ex) {  
                        sc.nextLine();
                    }
                    
                    if (response == 1) {
                        doRegistration(entity);
                    }
                    if (response == 2) {
                        try {
                            login(entity);
                            serviceProviderOperationMenu = new ServiceProviderOperationMenu(serviceProviderEntity, serviceProviderEntitySessionBeanRemote, customerEntitySessionBeanRemote, appointmentEntitySessionBeanRemote);
                            serviceProviderOperationMenu.serviceProviderMainMenu();
                            System.out.println("Login successful!");
                        } catch (InvalidLoginCredentialException ex) {
                            System.out.println("Login error! " + ex.getMessage() + "\n");
                        }
                    } else if (response == 3) {
                        System.out.println("Returning to main page...\n");
                    } else {
                        System.out.println("Invalid option! Please try again!\n");
                        break;
                    }
                }
                if (response == 3) {
                    break;
                }
                response = 0;
            }            
        } if(entity.equals("Customer")) {
            while (true) {
                System.out.println("*** Welcome to " + entity + " terminal ***\n");
                System.out.println("1: Registration");
                System.out.println("2: Login");
                System.out.println("3: Exit\n");
                response = 0;
                
                while (response < 1 || response > 3) {
                    try {
                        System.out.print("> ");
                        response = sc.nextInt();
                    } catch (InputMismatchException ex) {  
                        sc.nextLine();
                    }
                    
                    if (response == 1) {
                        doRegistration(entity);
                    }
                    if (response == 2) {
                        try {
                            login(entity);
                            customerOperationMenu = new CustomerOperationMenu(customerEntity, serviceProviderEntitySessionBeanRemote, customerEntitySessionBeanRemote,  appointmentEntitySessionBeanRemote, adminEntitySessionBeanRemote);
                            customerOperationMenu.customerOperationMainMenu();
                            System.out.println("Login successful!");
                        } catch (InvalidLoginCredentialException ex) {
                            System.out.println("Login error! " + ex.getMessage() + "\n");
                        }
                    } else if (response == 3) {
                        System.out.println("Returning to main page...\n");
                    } else {
                        System.out.println("Invalid option! Please try again!\n");
                        break;
                    }
                } 
                if (response == 3) {
                    break;
                }
            }            
        }
    }
    
    public void login(String entity) throws InvalidLoginCredentialException {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("*** " + entity + " terminal :: Login ***\n");
        System.out.print("Enter Email Address> ");
        String email = sc.nextLine().trim();
        System.out.print("Enter password> ");
        String password = sc.nextLine().trim();
        
        if (entity.equals("Admin")) {
            if (email.length() > 0 && password.length() > 0) {
                adminEntity = adminEntitySessionBeanRemote.adminLogin(email, password);
            } else {
                throw new InvalidLoginCredentialException("Missing login credentials");
            }
        } if (entity.equals("Service Provider")) {
            if (email.length() > 0 && password.length() > 0) {
                serviceProviderEntity = serviceProviderEntitySessionBeanRemote.serviceProviderLogin(email, password);
            } else {
                throw new InvalidLoginCredentialException("Missing login credentials");
            }
        } if (entity.equals("Customer")) {
            if (email.length() > 0 && password.length() > 0) {
                if (customerEntitySessionBeanRemote == null) {
                    System.out.println("SessionBean is null");
                }
                customerEntity = customerEntitySessionBeanRemote.customerLogin(email, password);
            } else {
                throw new InvalidLoginCredentialException("Missing login credentials");
            }
        }
    }
    
    public void doRegistration(String entity) {
        Scanner sc = new Scanner(System.in);
        
        if(entity.equals("Service Provider")) {
            System.out.println("*** " + entity + " terminal :: Registration Operation ***\n");

            System.out.print("Enter Name> ");
            String name = sc.nextLine().trim();
            try { 
                List<String> categoryList = adminEntitySessionBeanRemote.retrieveAdminById(Long.valueOf(1)).getBusinessCategory();
                 for(int i = 0; i < categoryList.size(); i++) {
                    System.out.print((i + 1) + " " + categoryList.get(i));
                    if(i < categoryList.size() - 1) {
                        System.out.print(" | ");
                    }
                }
                System.out.println();
                System.out.print("Enter Business Category> ");
                int choice = sc.nextInt();
                String businessCategory = categoryList.get(choice);
                sc.nextLine();
                System.out.print("Enter Business Registration Number> ");
                String businessRegNumber = sc.nextLine().trim();
                System.out.print("Enter City> ");
                String city = sc.nextLine().trim();
                System.out.print("Enter Phone> ");
                String phone = sc.nextLine().trim(); 
                System.out.print("Enter Business Address> ");
                String address = sc.nextLine().trim();    
                System.out.print("Enter Email> ");
                String email = sc.nextLine().trim();
                System.out.print("Enter Password> ");
                String password = sc.nextLine().trim();

                Long uniqueIdNumber = serviceProviderEntitySessionBeanRemote.createServiceProviderEntity(new ServiceProviderEntity(businessRegNumber, businessCategory, name, address, city, email, phone, password));
                System.out.print("You have been registered successfully! ID: " + uniqueIdNumber+"\n");
            } catch(AdminNotFoundException ex) {
                ex.getMessage();
            }

            System.out.print("Enter 0 to go back to the previous menu.\n");
            System.out.print(">");
            int exitNumber = -1;
            while(exitNumber != 0) {
                exitNumber = sc.nextInt();
                
                if(exitNumber == 0) {
                    loginTerminal(entity);
                }
            }
        }
        
        if(entity.equals("Customer")) {
            //customer registration code
            System.out.println("*** " + entity + " terminal :: Registration Operation ***\n");
            
            System.out.print("Enter first name> ");
            String firstName = sc.nextLine().trim();
            System.out.print("Enter last name> ");
            String lastName = sc.nextLine().trim();
            System.out.print("Enter identity number> ");
            String identityNo = sc.nextLine().trim();
            System.out.print("Enter phone number> ");
            String phone = sc.nextLine().trim();
            System.out.print("Enter age> ");
            Integer age = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter gender (M/F)> ");
            String gender = sc.nextLine().trim();
            System.out.print("Enter address> ");
            String address = sc.nextLine().trim();
            System.out.print("Enter city> ");
            String city = sc.nextLine().trim();
            System.out.print("Enter email address (to be used as login ID)> ");
            String email = sc.nextLine().trim();
            System.out.print("Enter password> ");
            String password = sc.nextLine().trim();
            
            Long uniqueIdNumber = customerEntitySessionBeanRemote.createCustomerEntity(new CustomerEntity(identityNo, password, firstName, lastName, gender, age, phone, address, city, email));
            System.out.print("You have been registered successfully! ID: " + uniqueIdNumber+"\n");
            System.out.print("Enter 0 to go back to the previous menu.\n");
            
            int exitNumber = -1;
            while(exitNumber != 0) {
                exitNumber = sc.nextInt();
                
                if(exitNumber == 0) {
                    sc.nextLine();
                    loginTerminal(entity);
                }
            }
        }
    }
    
    public void initAppointment(){
        
        Date myDate = new DateUtil().getDate(2022, 10, 15, 11, 30);
        
        try {
            ServiceProviderEntity SPEntity = serviceProviderEntitySessionBeanRemote.retrieveServiceProviderByUniqueIdNumber(Long.valueOf(1));
            CustomerEntity CustEntity = customerEntitySessionBeanRemote.retrieveCustomerById(Long.valueOf(1));
            Long appointmentId = appointmentEntitySessionBeanRemote.createAppointmentEntity(new AppointmentEntity("101011130", myDate, myDate));
            AppointmentEntity ApptEntity = appointmentEntitySessionBeanRemote.retrieveAppointmentById(appointmentId);

            //SPEntity.setAppointmentEntity(new ArrayList<AppointmentEntity>());
            //CustEntity.setAppointmentEntity(new ArrayList<AppointmentEntity>());

            //Lazy Fetching for ServiceProviderEntity --- AppointmentEntity
            ApptEntity.setServiceProviderEntity(SPEntity);
            SPEntity.getAppointmentEntity().add(ApptEntity);

            //Lazy Fetching for CustomerEntity --- AppointmentEntity
            ApptEntity.setCustomerEntity(CustEntity);
            CustEntity.getAppointmentEntity().add(ApptEntity);
            
            customerEntitySessionBeanRemote.updateCustomerEntity(CustEntity);
            serviceProviderEntitySessionBeanRemote.updateServiceProviderEntity(SPEntity);
            appointmentEntitySessionBeanRemote.updateAppointment(ApptEntity);

        } catch (ServiceProviderNotFoundException | AppointmentNotFoundException | CustomerNotFoundException ex) {
            ex.getMessage();
        }
    }
}