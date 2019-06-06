/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package com.avbravo.jmoordb.profiles.datamodel;



import com.avbravo.jmoordb.pojos.JmoordbNotifications;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author avbravo
 */
public class JmoordbNotificationsDataModel extends ListDataModel<JmoordbNotifications> implements SelectableDataModel<JmoordbNotifications>{

    public JmoordbNotificationsDataModel() {
    }
    public JmoordbNotificationsDataModel(List<JmoordbNotifications>data) {
        super(data);
    }

    @Override
    public JmoordbNotifications  getRowData(String rowKey) {
        List<JmoordbNotifications> jmoordbNotificationsList = (List<JmoordbNotifications>) getWrappedData();
        for (JmoordbNotifications jmoordbNotifications : jmoordbNotificationsList) {
             if (jmoordbNotifications.getIdjoordbnotifications().equals(rowKey)) {
                 return jmoordbNotifications;
             }
        }
        return null;
     }
     @Override
     public Object getRowKey(JmoordbNotifications jmoordbNotifications) {
         return jmoordbNotifications.getIdjoordbnotifications();
     }


}
