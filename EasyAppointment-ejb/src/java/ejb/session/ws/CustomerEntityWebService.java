/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.ws;

import ejb.session.stateless.CustomerEntitySessionBeanLocal;
import entity.CustomerEntity;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ejb.Stateless;

/**
 *
 * @author ryanl
 */
@WebService(serviceName = "CustomerEntityWebService")
@Stateless()
public class CustomerEntityWebService {

    @EJB
    private CustomerEntitySessionBeanLocal customerEntitySessionBeanLocal;
    
    @WebMethod
    public Long createCustomerEntity(@WebParam String identityNo,@WebParam String password,@WebParam String firstName,@WebParam String lastName,@WebParam String gender,@WebParam Integer age,@WebParam String phone,@WebParam String address, @WebParam String city, @WebParam String emailAddress) {
        CustomerEntity newCustomerEntity = new CustomerEntity();
        
        newCustomerEntity.setIndentityNo(identityNo);
        newCustomerEntity.setPassword(password);
        newCustomerEntity.setFirstName(firstName);
        newCustomerEntity.setLastName(lastName);
        newCustomerEntity.setGender(gender);
        newCustomerEntity.setAge(age);
        newCustomerEntity.setPhone(phone);
        newCustomerEntity.setAddress(address);
        newCustomerEntity.setCity(city);
        newCustomerEntity.setEmailAddress(emailAddress);
        
        return customerEntitySessionBeanLocal.createCustomerEntity(newCustomerEntity);
    }
}
