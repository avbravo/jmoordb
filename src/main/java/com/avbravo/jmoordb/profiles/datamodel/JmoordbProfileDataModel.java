/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package com.avbravo.jmoordb.profiles.datamodel;


import com.avbravo.jmoordb.profiles.entity.JmoordbProfile;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author avbravo
 */
public class JmoordbProfileDataModel extends ListDataModel<JmoordbProfile> implements SelectableDataModel<JmoordbProfile>{

    public JmoordbProfileDataModel() {
    }
    public JmoordbProfileDataModel(List<JmoordbProfile>data) {
        super(data);
    }

    @Override
    public JmoordbProfile  getRowData(String rowKey) {
        List<JmoordbProfile> jmoordbProfileList = (List<JmoordbProfile>) getWrappedData();
        for (JmoordbProfile jmoordbProfile : jmoordbProfileList) {
             if (jmoordbProfile.getIdprofile().equals(rowKey)) {
                 return jmoordbProfile;
             }
        }
        return null;
     }
     @Override
     public Object getRowKey(JmoordbProfile jmoordbProfile) {
         return jmoordbProfile.getIdprofile();
     }


}
