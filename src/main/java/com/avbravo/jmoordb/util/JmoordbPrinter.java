/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordb.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.oasis.JROdtExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRPptxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;

/**
 *
 * @authoravbravo
 */
//@Named
//@RequestScoped
public class JmoordbPrinter {

    static JRBeanCollectionDataSource ds;
    static FacesContext facesContext;
    static ServletContext scontext;
    static JasperPrint jasperPrint;

    public JmoordbPrinter() {
    }

    public static String imprimir(List<?> t, String ruta, HashMap hashmap) {
        try {

            preparar(t, ruta, hashmap);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
            exporter.exportReport();
            byte[] bytes = baos.toByteArray();
            if (bytes != null && bytes.length > 0) {

                HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
                response.setContentType("application/pdf");
                response.setHeader("Contentdisposition", "inline; filename=\"relatorioPorData.pdf\"");
                response.setContentLength(bytes.length);
                ServletOutputStream outputStream = response.getOutputStream();
                outputStream.write(bytes, 0, bytes.length);
                outputStream.flush();
                outputStream.close();

            }
        } catch (JRException | IOException e) {
            JmoordbUtil.errorMessage(e.getMessage());
        }
        return null;
    }

    public static String pdf(List<?> t, String ruta, HashMap hashmap) {
        try {
            preparar(t, ruta, hashmap);
            HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            httpServletResponse.addHeader("Contentdisposition",
                    "attachment;filename=report.pdf");
            ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
            FacesContext.getCurrentInstance().responseComplete();
        } catch (JRException | IOException e) {
            JmoordbUtil.errorMessage(e.getMessage());
        }
        return null;
    }

    public static String docx(List<?> t, String ruta, HashMap hashmap) {
        try {
            preparar(t, ruta, hashmap);
            HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            httpServletResponse.addHeader("Contentdisposition",
                    "attachment;filename=report.docx");
            ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
            JRDocxExporter docxExporter = new JRDocxExporter();
            docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
                    servletOutputStream);
            docxExporter.exportReport();
            FacesContext.getCurrentInstance().responseComplete();
        } catch (JRException | IOException e) {
            JmoordbUtil.errorMessage(e.getMessage());
        }
        return null;
    }

    public static String xlsx(List<?> t, String ruta, HashMap hashmap) {
        try {
            preparar(t, ruta, hashmap);
            HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            httpServletResponse.addHeader("Contentdisposition",
                    "attachment;filename=report.xlsx");
            ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
            JRXlsxExporter docxExporter = new JRXlsxExporter();
            docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
                    servletOutputStream);
            docxExporter.exportReport();
            FacesContext.getCurrentInstance().responseComplete();
        } catch (JRException | IOException e) {
            JmoordbUtil.errorMessage(e.getMessage());
        }
        return null;
    }

    public static String odt(List<?> t, String ruta, HashMap hashmap) {
        try {
            preparar(t, ruta, hashmap);
            HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            httpServletResponse.addHeader("Contentdisposition",
                    "attachment;filename=report.odt");
            ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
            JROdtExporter docxExporter = new JROdtExporter();
            docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
                    servletOutputStream);
            docxExporter.exportReport();
            FacesContext.getCurrentInstance().responseComplete();
        } catch (JRException | IOException e) {
            JmoordbUtil.errorMessage(e.getMessage());
        }
        return null;
    }

    public static String ppt(List<?> t, String ruta, HashMap hashmap) {
        try {
            preparar(t, ruta, hashmap);
            HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            httpServletResponse.addHeader("Contentdisposition",
                    "attachment;filename=report.pptx");
            ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
            JRPptxExporter docxExporter = new JRPptxExporter();
            docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
                    servletOutputStream);
            docxExporter.exportReport();
            FacesContext.getCurrentInstance().responseComplete();
        } catch (JRException | IOException e) {
            JmoordbUtil.errorMessage(e.getMessage());
        }
        return null;
    }

    private static void preparar(List<?> t, String ruta, HashMap hashmap) {
        try {
            ds = new JRBeanCollectionDataSource(t);
            facesContext = FacesContext.getCurrentInstance();
            facesContext.responseComplete();
            scontext = (ServletContext) facesContext.getExternalContext().getContext();
            jasperPrint = JasperFillManager.fillReport(scontext.getRealPath(ruta), hashmap, ds);
        } catch (Exception e) {
            JmoordbUtil.errorMessage(e.getMessage());
        }
    }

    public static String printtoPDF(List<?> t, String ruta, HashMap hashmap) throws JRException, IOException {
        JasperPrint jasperPrint = JasperFillManager.fillReport(ruta, hashmap, new JREmptyDataSource());
//        JasperPrint jasperPrint = JasperFillManager.fillReport(reportPath, new HashMap<String,Object>(), new JRBeanArrayDataSource(new SaleOrder[]{saleOrder}));  
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.addHeader("Content-disposition", "attachment; filename=ordentrabjo" + ".pdf");
//        httpServletResponse.addHeader("Content-disposition", "attachment; filename=" + name + "_" + saleOrder.getName() + ".pdf");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
        FacesContext.getCurrentInstance().responseComplete();
        return "";
    }
}
