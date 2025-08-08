/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/MessageDrivenBean.java to edit this template
 */
package mdb;

import DBConnect.DBConnect;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSDestinationDefinition;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 *
 * @author Acer
 */
@JMSDestinationDefinition(name = "java:app/jms/myQueue", interfaceName = "javax.jms.Queue", resourceAdapter = "jmsra", destinationName = "myQueue")
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:app/jms/myQueue"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class NewMessageBean implements MessageListener {
    public NewMessageBean() {
    }
    
    @Override
    public void onMessage(Message message) {
        try {
            String msg = message.getBody(String.class);
            String[] parts = msg.split("\\|");
            String loaiTacVu = parts[0];
            String tenSP = parts[1];
            DBConnect conn = new DBConnect();
            conn.addMessage(loaiTacVu, tenSP);
            
        } catch (JMSException ex) {
            Logger.getLogger(NewMessageBean.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
}
