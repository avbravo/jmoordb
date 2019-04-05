/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package com.avbravo.jmoordb.profiles.datamodel;



import com.avbravo.jmoordb.profiles.entity.JmoordbForm;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author avbravo
 */
public class JmoordbFormDataModel extends ListDataModel<JmoordbForm> implements SelectableDataModel<JmoordbForm>{

    public JmoordbFormDataModel() {
    }
    public JmoordbFormDataModel(List<JmoordbForm>data) {
        super(data);
    }

    @Override
    public JmoordbForm  getRowData(String rowKey) {
        List<JmoordbForm> jmoordbFormList = (List<JmoordbForm>) getWrappedData();
        for (JmoordbForm jmoordbForm : jmoordbFormList) {
             if (jmoordbForm.getIdform().equals(rowKey)) {
                 return jmoordbForm;
             }
        }
        return null;
     }
     @Override
     public Object getRowKey(JmoordbForm jmoordbForm) {
         return jmoordbForm.getIdform();
     }


}
