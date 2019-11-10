/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package com.avbravo.jmoordb.mongodb.history.datamodel;

import com.avbravo.jmoordb.mongodb.history.entity.ErrorInfo;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author avbravo
 */
public class ErrorinfoDataModel extends ListDataModel<ErrorInfo> implements SelectableDataModel<ErrorInfo>{

    public ErrorinfoDataModel() {
    }
    public ErrorinfoDataModel(List<ErrorInfo>data) {
        super(data);
    }

    @Override
    public ErrorInfo  getRowData(String rowKey) {
        List<ErrorInfo> errorInfoList = (List<ErrorInfo>) getWrappedData();
        for (ErrorInfo errorInfo : errorInfoList) {
             if (errorInfo.getIderror().equals(rowKey)) {
                 return errorInfo;
             }
        }
        return null;
     }
     @Override
     public Object getRowKey(ErrorInfo errorInfo) {
         return errorInfo.getIderror();
     }


}
