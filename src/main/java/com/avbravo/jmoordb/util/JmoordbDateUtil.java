/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordb.util;
// <editor-fold defaultstate="collapsed" desc="import">  


import static com.avbravo.jmoordb.util.JmoordbUtil.errorDialog;
import static com.avbravo.jmoordb.util.JmoordbUtil.errorMessage;
import java.util.List;
import java.util.logging.Logger;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
// </editor-fold>

/**
 *
 * @authoravbravo
 */
public class JmoordbDateUtil implements Serializable { 

    private static final Logger LOG = Logger.getLogger(JmoordbDateUtil.class.getName());
    private static final String EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";

    // static Pattern object, since pattern is fixed
    private static Pattern pattern;

    // non-static Matcher object because it's created from the input String
    private static Matcher matcher;

// <editor-fold defaultstate="collapsed" desc="getFechaActual"> 
    public static java.util.Date getFechaActual() {
        LocalDateTime timePoint = LocalDateTime.now();
        LocalDate currentDate = LocalDate.now();
        java.util.Date date = java.sql.Date.valueOf(currentDate);
        return date;
    }    // </editor-fold>
// <editor-fold defaultstate="collapsed" desc="fechaActual"> 

    public static java.util.Date fechaActual() {
        LocalDateTime timePoint = LocalDateTime.now();
        LocalDate currentDate = LocalDate.now();
        java.util.Date date = java.sql.Date.valueOf(currentDate);
        return date;
    }    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="isBisiesto(Integer anio)">
    public static Boolean isBisiesto(Integer anio) {
        try {
            GregorianCalendar calendar = new GregorianCalendar();

            if (calendar.isLeapYear(anio)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            errorMessage("isBisiesto() " + e.getLocalizedMessage());
        }
        return true;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="numberDayOfMonth(Integer anio, String mes)"> 
    public static Integer numberDayOfMonth(Integer anio, String mes) {
        Integer dias = 0;
        try {
            JmoordbMonthUtils m = new JmoordbMonthUtils(); 

            dias = numberDayOfMonth(anio, m.numeroMes(mes));
        } catch (Exception e) {
            errorMessage("numberDayOfMonth() " + e.getLocalizedMessage());
        }
        return dias;
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="numberDayOfMonth(Integer anio, Integer mes)"> 
// devuelve el total de dias del mes

    public static Integer numberDayOfMonth(Integer anio, Integer mes) {
        Integer dias = 0;
        try {
            switch (mes) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    dias = 31;
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    dias = 30;
                    break;
                case 2:
                    if (isBisiesto(anio)) {
                        dias = 29;
                    } else {
                        dias = 28;
                    }
//                    if ((anio % 4 == 0 && dias % 100 != 0) || anio % 400 == 0) {
//                        dias = 29;
//                    } else {
//                        dias = 28;
//                    }
                    break;
                default:
                    System.out.println("\nEl mes " + mes + " es incorrecto.");
                    break;
            }

        } catch (Exception e) {
            errorMessage("numberDayOfMonth() " + e.getLocalizedMessage());
        }
        return dias;
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="totalDiaDelMes(Integer anio, Integer mes)"> 
// devuelve el total de dias del mes

    public static Integer totalDiaDelMes(Integer anio, Integer mes) {
        Integer dias = 0;
        try {
            switch (mes) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    dias = 31;
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    dias = 30;
                    break;
                case 2:
                    if (isBisiesto(anio)) {
                        dias = 29;
                    } else {
                        dias = 28;
                    }
//                    if ((anio % 4 == 0 && dias % 100 != 0) || anio % 400 == 0) {
//                        dias = 29;
//                    } else {
//                        dias = 28;
//                    }
                    break;
                default:
                    System.out.println("\nEl mes " + mes + " es incorrecto.");
                    break;
            }

        } catch (Exception e) {
            errorMessage("numberDayOfMonth() " + e.getLocalizedMessage());
        }
        return dias;
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="getAnioActual"> 

    public static Integer getAnioActual() {
        java.util.Calendar ca = java.util.Calendar.getInstance();
        java.sql.Date mydate = new java.sql.Date(ca.getTimeInMillis());
        return ca.get(Calendar.YEAR);
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="anioActual"> 

    public static Integer anioActual() {
        java.util.Calendar ca = java.util.Calendar.getInstance();
        java.sql.Date mydate = new java.sql.Date(ca.getTimeInMillis());
        return ca.get(Calendar.YEAR);
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="mesActual"> 
    public static Integer mesActual() {
        java.util.Calendar ca = java.util.Calendar.getInstance();
        java.sql.Date mydate = new java.sql.Date(ca.getTimeInMillis());
        return ca.get(Calendar.MONTH) + 1;
    }
// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="getFechaHoraActual()"> 

    public static Date getFechaHoraActual() {
        LocalDateTime ahora = LocalDateTime.now();
        Date date2 = Date.from(ahora.atZone(ZoneId.systemDefault()).toInstant());
        return date2;
    }
    // </editor-fold>
// <editor-fold defaultstate="collapsed" desc="fechaHoraActual()"> 

    public static Date fechaHoraActual() {
        LocalDateTime ahora = LocalDateTime.now();
        Date date2 = Date.from(ahora.atZone(ZoneId.systemDefault()).toInstant());
        return date2;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="numberOfMonth"> 
    public static Integer convertMonthNameToNumber(String mes) {
        Integer number = 0;
        try {
       JmoordbMonthUtils m = new JmoordbMonthUtils();

            number = m.numeroMes(mes);
        } catch (Exception e) {
        }
        return number;
    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="dateFormatToString"> 

    /**
     * formatea una fecha a "dd/MM/yyyy hh:mm a"
     *
     * @param fecha
     * @param format
     * @return
     */
    public static String dateFormatToString(Date fecha, String... format) {
        String dateformat = "";
        String f = "dd/MM/yyyy hh:mm a";
        try {
            if (format.length != 0) {
                f = format[0];

            }
            SimpleDateFormat sdf = new SimpleDateFormat(f);
            dateformat = sdf.format(fecha);
        } catch (Exception e) {
        }
        return dateformat;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="hourFromDateToString(Date fecha)"> 
    /**
     * Devuelve una hora en formato hh:mm a o se puede especificar el formato
     * deseado
     *
     * @param fecha
     * @param format
     * @return
     */
    public static String hourFromDateToString(Date fecha, String... format) {
        String h = "";
        try {
            String f = "hh:mm a";
            if (format.length != 0) {
                f = format[0];

            }

            h = dateFormatToString(fecha, f);
        } catch (Exception e) {
            JmoordbUtil.errorMessage("hourFromDateToString() " + e.getLocalizedMessage()); 
        }
        return h;

    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="dateBetween(Date fechaToSearch, Date fechainicio, Date fechafin)"> 
    /**
     * busca una fecha si esta entre fechas
     *
     * @param fechaToSearch
     * @param fechainicio
     * @param fechafin
     * @return
     */
    public static Boolean dateBetween(Date fechaToSearch, Date fechainicio, Date fechafin) {
        try {
//            Date fechainiciot = converterDate(fechainicio);
//                    Date fechafint = converterDate(fechafin);
            if (fechaToSearch.equals(fechainicio) || fechaToSearch.equals(fechafin) || (fechaToSearch.after(fechainicio) && fechaToSearch.before(fechafin))) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="getNombreMes"> 
    public static String getNombreMes(Integer numeromes) {
        try {
            String nombre = "";
            List<String> listMeses = new ArrayList<>();
            listMeses.add("Enero");
            listMeses.add("Febrero");
            listMeses.add("Marzo");
            listMeses.add("Abril");
            listMeses.add("Mayo");
            listMeses.add("Junio");
            listMeses.add("Julio");
            listMeses.add("Agosto");
            listMeses.add("Septiembre");
            listMeses.add("Octubre");
            listMeses.add("Noviembre");
            listMeses.add("Diciembre");
            return listMeses.get(numeromes);

        } catch (Exception e) {
        }
        return "";
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="String nameOfMonthStartWith1 "> 
    public static String nameOfMonthStartWith1(Integer numeromes) {
        String nombre = "";
        try {

            switch (numeromes) {
                case 1:
                    nombre = "Enero";
                    break;
                case 2:
                    nombre = "Febrero";
                    break;
                case 3:
                    nombre = "Marzo";
                    break;
                case 4:
                    nombre = "Abril";
                    break;
                case 5:
                    nombre = "Mayo";
                    break;
                case 6:
                    nombre = "Junio";
                    break;
                case 7:
                    nombre = "Julio";
                    break;
                case 8:
                    nombre = "Agosto";
                    break;
                case 9:
                    nombre = "Septiembre";
                    break;
                case 10:
                    nombre = "Octubre";
                    break;
                case 11:
                    nombre = "Noviembre";
                    break;
                case 12:
                    nombre = "Diciembre";
                    break;

            }

        } catch (Exception e) {
            System.out.println("nombreMesStartWith1 " + e.getLocalizedMessage());
        }
        return nombre;
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="String nameOfMonthStartWith1 "> 

    public static String nameOfMonthStartWith0(Integer numeromes) {
        String nombre = "";
        try {

            switch (numeromes) {
                case 0:
                    nombre = "Enero";
                    break;
                case 1:
                    nombre = "Febrero";
                    break;
                case 2:
                    nombre = "Marzo";
                    break;
                case 3:
                    nombre = "Abril";
                    break;
                case 4:
                    nombre = "Mayo";
                    break;
                case 5:
                    nombre = "Junio";
                    break;
                case 6:
                    nombre = "Julio";
                    break;
                case 7:
                    nombre = "Agosto";
                    break;
                case 8:
                    nombre = "Septiembre";
                    break;
                case 9:
                    nombre = "Octubre";
                    break;
                case 10:
                    nombre = "Noviembre";
                    break;
                case 11:
                    nombre = "Diciembre";
                    break;

            }

        } catch (Exception e) {
            System.out.println("nombreMesStartWith0 " + e.getLocalizedMessage());
        }
        return nombre;
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="nombreMes(Integer numeromes) "> 

    public static String nombreMesFirstMesZero(Integer numeromes) {
        try {
            String nombre = "";
            List<String> listMeses = new ArrayList<>();
            listMeses.add("Enero");
            listMeses.add("Febrero");
            listMeses.add("Marzo");
            listMeses.add("Abril");
            listMeses.add("Mayo");
            listMeses.add("Junio");
            listMeses.add("Julio");
            listMeses.add("Agosto");
            listMeses.add("Septiembre");
            listMeses.add("Octubre");
            listMeses.add("Noviembre");
            listMeses.add("Diciembre");
            return listMeses.get(numeromes);

        } catch (Exception e) {
        }
        return "";
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="mesToMonth"> 
    /**
     * Convierte un nombre de mes a un objeto Month Month month =
     * JmoordbUtil.mesToMonth("Febrero); Devuelve un month.FEBRARY;
     *
     * @param mes
     * @return
     */
    public static Month mesToMonth(String mes) {
        mes = mes.toLowerCase();
        Month month = Month.JANUARY;
        try {
            switch (mes) {
                case "enero":
                    month = Month.JANUARY;
                    break;
                case "febrero":
                    month = Month.FEBRUARY;
                    break;
                case "marzo":
                    month = Month.MARCH;
                    break;
                case "abril":
                    month = Month.APRIL;
                    break;
                case "mayo":
                    month = Month.MAY;
                    break;
                case "junio":
                    month = Month.JUNE;
                    break;
                case "julio":
                    month = Month.JULY;
                    break;
                case "agosto":
                    month = Month.AUGUST;
                    break;
                case "septiembre":
                    month = Month.SEPTEMBER;
                    break;
                case "octubre":
                    month = Month.OCTOBER;
                    break;
                case "noviembre":
                    month = Month.NOVEMBER;
                    break;
                case "diciembre":
                    month = Month.DECEMBER;
                    break;

            }

        } catch (Exception e) {
            errorMessage("mesToMonth() " + e.getLocalizedMessage());
        }
        return month;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="mesDeUnaFecha"> 
    public static Integer mesDeUnaFecha(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int anio = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH) + 1;
        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        return mes;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="mesDeUnaFechaStartEneroWith1(Date date)"> 
    public static Integer mesDeUnaFechaStartEneroWith1(Date date) {
        int mes = 0;
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            int anio = calendar.get(Calendar.YEAR);
            mes = calendar.get(Calendar.MONTH) + 1;
            int dia = calendar.get(Calendar.DAY_OF_MONTH);
            return mes;
        } catch (Exception e) {
            errorDialog("error", "getMesDeUnaFechaStartEneroWith1() " + e.getLocalizedMessage());
        }
        return 0;

    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="mesDeUnaFechaStartEneroWith0(Date date)"> 

    public static Integer mesDeUnaFechaStartEneroWith0(Date date) {
        int mes = 0;
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            int anio = calendar.get(Calendar.YEAR);
            mes = calendar.get(Calendar.MONTH);
            int dia = calendar.get(Calendar.DAY_OF_MONTH);
            return mes;
        } catch (Exception e) {
            errorDialog("error", "getMesDeUnaFechaStartEneroWith1() " + e.getLocalizedMessage());
        }
        return 0;

    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="anioDeUnaFecha"> 
    public static Integer anioDeUnaFecha(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int anio = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH) + 1;
        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        return anio;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="diaDeUnaFecha"> 
    public static Integer diaDeUnaFecha(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int anio = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH) + 1;
        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        return dia;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="horaDeUnaFecha(Date date)"> 
    public static Integer horaDeUnaFecha(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int hora = calendar.get(Calendar.HOUR_OF_DAY);
        return hora;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="horaMinutoAMPMDeUnaFecha(Date date)(Date date)"> 
    public static String horaMinutoAMPMDeUnaFecha(Date date) {

        int hora = horaDeUnaFecha(date);
        int minutos = minutosDeUnaFecha(date);
        String time12h = "AM";
        if (hora > 12) {
            hora = hora - 12;
            time12h = "PM";
        }
        return hora + ":" + minutos + time12h;
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="horaDeUnaFechaFormato12(Date date)"> 

    public static Integer horaDeUnaFechaFormato12H(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int hora = calendar.get(Calendar.HOUR_OF_DAY);
        if (hora > 12) {
            hora = hora - 12;
        }
        return hora;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="minutosDeUnaFecha"> 
    public static Integer minutosDeUnaFecha(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int minuto = calendar.get(Calendar.MINUTE);
        return minuto;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="diaActual"> 
    public static Integer diaActual() {
        java.util.Calendar ca = java.util.Calendar.getInstance();
        java.sql.Date mydate = new java.sql.Date(ca.getTimeInMillis());
        return ca.get(Calendar.DATE);
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="nameOfDay()"> 
    public static String nameOfDay(LocalDate date) {
        String nombre = "DOMINGO";
        try {
            DayOfWeek dia = date.getDayOfWeek();
            dia.name();
            switch (dia.name()) {
                case "SATURDAY":
                    nombre = "SABADO";
                    break;
                case "SUNDAY":
                    nombre = "DOMINGO";
                    break;
                case "MONDAY":
                    nombre = "LUNES";
                    break;
                case "TUESDAY":
                    nombre = "MARTES";
                    break;
                case "WEDNESDAY":
                    nombre = "MIERCOLES";
                    break;
                case "THURSDAY":
                    nombre = "JUEVES";
                    break;
                case "FRIDAY":
                    nombre = "VIERNES";
                    break;

            }

        } catch (Exception e) {
            errorMessage("nameOfDay() " + e.getLocalizedMessage());
        }
        return nombre;
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="nameOfDay()"> 

    public static String nameOfDayMinuscula(LocalDate date) {
        String nombre = "Domingo";
        try {
            DayOfWeek dia = date.getDayOfWeek();
            dia.name();
            switch (dia.name()) {
                case "SATURDAY":
                    nombre = "Sabado";
                    break;
                case "SUNDAY":
                    nombre = "Domingo";
                    break;
                case "MONDAY":
                    nombre = "Lunes";
                    break;
                case "TUESDAY":
                    nombre = "Martes";
                    break;
                case "WEDNESDAY":
                    nombre = "Miercoles";
                    break;
                case "THURSDAY":
                    nombre = "Jueves";
                    break;
                case "FRIDAY":
                    nombre = "Viernes";
                    break;

            }

        } catch (Exception e) {
            errorMessage("nameOfDayMinusculas() " + e.getLocalizedMessage());
        }
        return nombre;
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="nameOfDay(Date date)"> 

    public static String nameOfDay(Date date) {
        String nombre = "";
        try {
            LocalDate localDate = date.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            nombre = nameOfDay(localDate);
        } catch (Exception e) {
            errorMessage("nameOfDay() " + e.getLocalizedMessage());
        }
        return nombre;
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="nameOfDay(Date date)"> 

    public static String nameOfDayMinusculas(Date date) {
        String nombre = "";
        try {
            LocalDate localDate = date.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            nombre = nameOfDayMinuscula(localDate);
        } catch (Exception e) {
            errorMessage("nameOfDay() " + e.getLocalizedMessage());
        }
        return nombre;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="firstLeterOfDay"> 
    /**
     * devuelve la primera letra del dia
     *
     * @param date
     * @return
     */
    public static String firstLetterOfDay(LocalDate date) {
        String letra = "";
        try {
            letra = nameOfDay(date);
            if (letra.length() > 1) {
                letra = letra.substring(0, 1);
            }

        } catch (Exception e) {
            errorMessage("firsLetterOfDay() " + e.getLocalizedMessage());
        }
        return letra;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="nameDayOfMonth"> 
    /**
     * devuelve un List<String> correspondiente a las letras de cada dia del mes
     *
     * @param year
     * @param mes
     * @return
     */
    public static List<String> nameDayOfMonth(Integer year, String mes) {
        List<String> names = new ArrayList<>();
        try {
            LocalDate date;

            Month month = mesToMonth(mes);

            for (int i = 1; i <= month.maxLength(); i++) {

                date = LocalDate.of(year, month, i);
                String name = nameOfDay(date);
                names.add(name);

            }
        } catch (Exception e) {
            errorMessage("nameDayOfMonth() " + e.getLocalizedMessage());
        }
        return names;
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="nameDayOfMonth"> 

    /**
     * devuelve un List<String> correspondiente a las letras de cada dia del mes
     *
     * @param year
     * @param mes
     * @return
     */
    public static List<JmoordbFechaDiaUtils> nameOfDayOfDateOfMonth(Integer year, String mes) { 
        List<JmoordbFechaDiaUtils> fechaDiaUtilsList = new ArrayList<>(); 
        try {
            LocalDate date;

            Month month = mesToMonth(mes);
            Integer numeroDias = numberDayOfMonth(year, mes);
            //for (int i = 1; i <= month.maxLength(); i++) {
            for (int i = 1; i <= numeroDias; i++) {

                date = LocalDate.of(year, month, i);
                String name = nameOfDay(date);
                String letter = firstLetterOfDay(date);
                JmoordbFechaDiaUtils fechaDiaUtils = new JmoordbFechaDiaUtils(date, letter, name);
                fechaDiaUtilsList.add(fechaDiaUtils);

            }
        } catch (Exception e) {
            errorMessage("nameOfDayOfDateOfMonth() " + e.getLocalizedMessage());
        }
        return fechaDiaUtilsList;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="primeraFechaAnio"> 
    /**
     * devuelve la primera fecha del año
     *
     * @return
     */
    public static Date primeraFechaAnio() {
        LocalDate now = LocalDate.now();//# 2015-11-23
        Integer year = now.getYear();
        Integer month = 1;
        Integer day = 1;
        LocalDate firstDay = LocalDate.of(year, month, day);

        Date date = java.sql.Date.valueOf(firstDay);
        return date;

    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="ultimaFechaAnio"> 
    /**
     * devuelve la ultima fecha del año
     *
     * @return
     */
    public static Date ultimaFechaAnio() {
        LocalDate now = LocalDate.now();//# 2015-11-23
        Integer year = now.getYear();
        Integer month = 12;
        Integer day = 31;
        LocalDate firstDay = LocalDate.of(year, month, day);

        Date date = java.sql.Date.valueOf(firstDay);
        return date;

    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="dateFirtsOfMonth"> 
    /**
     *
     * @param month
     * @return devuelve una fecha correspondiente al primer dia de ese mes
     */
    public static Date dateFirtsOfMonth(Integer year, Integer month) {
        LocalDate now = LocalDate.now();//# 2015-11-23
        Integer day = 1;
        LocalDate firstDay = LocalDate.of(year, month, day);
        Date date = java.sql.Date.valueOf(firstDay);
        return date;
    }
    // </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Date primerDiaDelMesEnFecha(Integer year, Integer month)"> 

    /**
     *
     * @param month
     * @return devuelve una fecha correspondiente al primer dia de ese mes
     */
    public static Date primerDiaDelMesEnFecha(Integer year, Integer month) {
        LocalDate now = LocalDate.now();//# 2015-11-23
        Integer day = 1;
        LocalDate firstDay = LocalDate.of(year, month, day);
        Date date = java.sql.Date.valueOf(firstDay);
        return date;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="integerToDate"> 
    public static Date integerToDate(Integer year, Integer month, Integer day) {

        LocalDate firstDay = LocalDate.of(year, month, day);
        Date date = java.sql.Date.valueOf(firstDay);
        return date;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="dateLastOfMonth(Integer year, Integer month)"> 
    public static Date dateLastOfMonth(Integer year, Integer month) {
        LocalDate now = LocalDate.now();//# 2015-11-23
        Integer day = numberDayOfMonth(year, month);

        LocalDate firstDay = LocalDate.of(year, month, day);
        Date date = java.sql.Date.valueOf(firstDay);
        return date;
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Date ultimoDiaDelMesEnFecha(Integer year, Integer month)"> 

    public static Date ultimoDiaDelMesEnFecha(Integer year, Integer month) {
        LocalDate now = LocalDate.now();//# 2015-11-23
        Integer day = numberDayOfMonth(year, month);

        LocalDate firstDay = LocalDate.of(year, month, day);
        Date date = java.sql.Date.valueOf(firstDay);
        return date;
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Date ultimoDiaDelMesEnFecha(Integer year, Integer month)"> 

    public static Date fechaConHora0(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, 0);
        return calendar.getTime();
//        Calendar ahoraCal = Calendar.getInstance();
//System.out.println(ahoraCal.getClass());
//ahoraCal.set(2004,1,7);
//System.out.println(ahoraCal.getTime());
//ahoraCal.set(2004,1,7,7,0,0);
//System.out.println(ahoraCal.getTime());
//        LocalDate now = LocalDate.now();//# 2015-11-23
//        Integer day = numberDayOfMonth(year, month);
//
//        LocalDate firstDay = LocalDate.of(year, month, day);
//        Date date2 = java.sql.Date.valueOf(firstDay);
//        return date2;
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="iSODate"> 
    public static String iSODate(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.format(date);
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="tiempo"> 
    public static LocalTime tiempo() {
        LocalTime now = LocalTime.now();

        return now;

    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="printJmoordbTiempo"> 
    public static String printJmoordbTiempo() {
        LocalTime now = LocalTime.now();
        String tiempo = "";

        tiempo = "En este momento son las %d horas con %d minutos y %d segundos\n" + now.getHour()
                + now.getMinute() + now.getSecond();

        return tiempo;

    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="hour"> 
    public static LocalTime hour() {

        return LocalTime.now();
    }// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="diasEntreFechas"> 

    public static Integer diasEntreFechas(Date fechaMayor, Date fechaMenor) {
        int d = 0;
        try {
            long diferenciaEn_ms = fechaMayor.getTime() - fechaMenor.getTime();

            long dias = diferenciaEn_ms / (1000 * 60 * 60 * 24);
            d = (int) dias;
        } catch (Exception e) {
            System.out.println("diasEntreFechas() " + e.getLocalizedMessage());
        }

        return d;
    }// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Integer minutosEntreFechas(Date fechaMayor, Date fechaMenor)"> 
    /**
     * Devuelve el tiempo entre dos fechas (dias,horas, munutos)
     *
     * @param fechaMayor
     * @param fechaMenor
     * @return
     */
    public static JmoordbTiempo diferenciaEntreFechas(Date fechaMayor, Date fechaMenor) {
        int d = 0;
        JmoordbTiempo tiempo = new JmoordbTiempo(0, 0, 0); 
        try {
            int diferencia = (int) ((fechaMayor.getTime() - fechaMenor.getTime()) / 1000);

            int dias = 0;
            int horas = 0;
            int minutos = 0;
            if (diferencia > 86400) {
                dias = (int) Math.floor(diferencia / 86400);
                diferencia = diferencia - (dias * 86400);
            }
            if (diferencia > 3600) {
                horas = (int) Math.floor(diferencia / 3600);
                diferencia = diferencia - (horas * 3600);
            }
            if (diferencia > 60) {
                minutos = (int) Math.floor(diferencia / 60);
                diferencia = diferencia - (minutos * 60);
            }

            if (minutos >= 60) {
                minutos = 0;
                horas++;
            }
            if (horas >= 24) {
                horas = 0;
                dias++;
            }
            tiempo.setDias(dias);
            tiempo.setHoras(horas);
            tiempo.setMinutos(minutos);

        } catch (Exception e) {
            System.out.println("diasEntreFechas() " + e.getLocalizedMessage());
        }

        return tiempo;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="fechaMenor(Date date1, Date date2)">
    public static Boolean fechaMenor(Date date1, Date date2) {
        Boolean esmenor = false;
        try {
            if (date1.compareTo(date2) < 0) {
                esmenor = true;
            }
        } catch (Exception e) {
            errorDialog("error", "fechaMenor() " + e.getLocalizedMessage());
        }
        return esmenor;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="fechaMayor(Date date1, Date date2)">
    public static Boolean fechaMayor(Date date1, Date date2) {
        Boolean esmenor = false;
        try {
            if (date1.compareTo(date2) > 0) {
                esmenor = true;
            }
        } catch (Exception e) {
            errorDialog("error", "fechaMayor() " + e.getLocalizedMessage());
        }
        return esmenor;
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="fechaIgual(Date date1, Date date2)">

    public static Boolean fechaIgual(Date date1, Date date2) {
        Boolean esmenor = false;
        try {
            if (date1.compareTo(date2) == 0) {
                esmenor = true;
            }
        } catch (Exception e) {
            errorDialog("error", "fechaIgual() " + e.getLocalizedMessage());
        }
        return esmenor;
    }
    // </editor-fold>

// <editor-fold defaultstate="collapsed" desc="sumarMesaFechaActual"> 
    /*
    
     */
    public static Date sumarMesaFechaActual(Integer mes) {
        java.util.Date date = new Date();
        try {
            LocalDate localDate = LocalDate.now().plusMonths(mes);
            date = java.sql.Date.valueOf(localDate);

        } catch (Exception e) {
            System.out.println("diasEntreFechas() " + e.getLocalizedMessage());
        }

        return date;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="sumarMesaFecha(date,mes)"> 
    /**
     * suma a la fecha el numero de mes
     *
     * @param date
     * @param mes
     */
    public static Date sumarMesaFecha(Date date, Integer mes) {
        java.util.Date dateresult = new Date();
        try {

            ZoneId defaultZoneId = ZoneId.systemDefault();
            Instant instant = date.toInstant();
            LocalDate localDate = instant.atZone(defaultZoneId).toLocalDate();

            localDate = localDate.plusMonths(mes);
            dateresult = java.sql.Date.valueOf(localDate);

        } catch (Exception e) {
            errorMessage("sumarMesaFecha() " + e.getLocalizedMessage());
        }
        return dateresult;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="segundosToHoraString"> 
    public static String segundosToHoraString(Integer segundos) {
        String resultado = "";
        try {
            int hours = segundos / 3600;
            int minutes = (segundos % 3600) / 60;
            segundos = segundos % 60;
            resultado = twoDigitString(hours) + " : " + twoDigitString(minutes) + " : " + twoDigitString(segundos);
        } catch (Exception e) {
            errorMessage("segundosToHoraString() " + e.getLocalizedMessage());
        }
        return resultado;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="twoDigitString"> 
    private static String twoDigitString(int number) {

        if (number == 0) {
            return "00";
        }

        if (number / 10 == 0) {
            return "0" + number;
        }

        return String.valueOf(number);
    }// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="milisegundos"> 
/**
 * 
 * @return Devuelve los milisegundos como un long
 */
    public static long milisegundos() {
        long milisegundos = 0;
        try {
            milisegundos = System.nanoTime();

        } catch (Exception e) {
            System.out.println("getMilisegundos() " + e.getLocalizedMessage());
        }
        return milisegundos;
    }// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Integer milisegundosToInteger()"> 
/**
 * 
 * @return Devuelve los milisegundos actuales como un entero
 * 
 */
    public static Integer milisegundosToInteger() {
        long milisegundos = 0;
        try {
            milisegundos = System.nanoTime();

        } catch (Exception e) {
            System.out.println("getMilisegundos() " + e.getLocalizedMessage());
        }
        return Math.toIntExact(milisegundos);
    }// </editor-fold>

    
    
    
// <editor-fold defaultstate="collapsed" desc="milisegundosTranscurridos"> 
    public static long milisegundosTranscurridos(long t0, long t1) {
        long milisegundos = 0;
        try {
            milisegundos = TimeUnit.NANOSECONDS.toMillis(t1 - t0);

        } catch (Exception e) {
            errorMessage("getMilisegundos() " + e.getLocalizedMessage());
            System.out.println("getMilisegundos() " + e.getLocalizedMessage());
        }
        return milisegundos;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="milisegundosToSegundos"> 
    public static Integer milisegundosToSegundos(long milisegundos) {
        Integer seconds = 0;
        try {
            seconds = (int) (milisegundos / 1000) % 60;
        } catch (Exception e) {
            errorMessage("miliseguntosToSegundos() " + e.getLocalizedMessage());
        }
        return seconds;
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="milisegundosToMinutos"> 
    public static Integer milisegundosToMinutos(long milisegundos) {
        Integer minutes = 0;
        try {
            minutes = (int) ((milisegundos / (1000 * 60)) % 60);
        } catch (Exception e) {
            errorMessage("miliseguntosToMinutos() " + e.getLocalizedMessage());
        }
        return minutes;

    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="milisegundosToHoras"> 
    public static Integer milisegundosToHoras(long milisegundos) {
        Integer hours = 0;
        try {
            hours = (int) ((milisegundos / (1000 * 60 * 60)) % 24);
        } catch (Exception e) {
            errorMessage("miliseguntosToMinutos() " + e.getLocalizedMessage());
        }
        return hours;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="milisegundosToJmoordbTiempoString"> 
    /**
     * devuelve el tiempo de los milisegundos en el formato hh:mm:ss
     * milisegundos 1222 devuelve; 1:2:23
     *
     * @param milisegundos
     * @return
     */
    public static String milisegundosToJmoordbTiempoString(long milisegundos) {
        String tiempoString = "";

        try {

            tiempoString = milisegundosToHoras(milisegundos) + " : "
                    + milisegundosToMinutos(milisegundos) + " : " + milisegundosToSegundos(milisegundos);

        } catch (Exception e) {
            errorMessage("milisegundosToJmoordbTiempoString() " + e.getLocalizedMessage());
        }
        return tiempoString;

    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="mesAnterior(String mes) "> 
    /**
     * devuelve el nombre del mes anterior
     *
     * @param mes
     * @return
     */
    public static String mesAnterior(String mes) {
        String mesanterior = "";
        try {
            switch (mes.toLowerCase()) {
                case "enero":
                    mesanterior = "diciembre";
                    break;

                case "febrero":
                    mesanterior = "enero";
                    break;
                case "marzo":
                    mesanterior = "febrero";
                    break;
                case "abril":
                    mesanterior = "marzo";
                    break;
                case "mayo":
                    mesanterior = "abril";
                    break;
                case "junio":
                    mesanterior = "mayo";
                    break;
                case "julio":
                    mesanterior = "junio";
                    break;
                case "agosto":
                    mesanterior = "julio";
                    break;
                case "septiembre":
                    mesanterior = "agosto";
                    break;
                case "octubre":
                    mesanterior = "septiembre";
                    break;
                case "noviembre":
                    mesanterior = "octubre";
                    break;
                case "diciembre":
                    mesanterior = "noviembre";
                    break;
            }
        } catch (Exception e) {
            errorMessage("mesAnterior() " + e.getLocalizedMessage());
        }
        return mesanterior;
    }    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="fechaActualEnMilisegundos()"> 
    public static Long fechaActualEnMilisegundos() {
        return ZonedDateTime.now().toInstant().toEpochMilli();
    }

// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="letterDayOfMonth(Integer year, String mes)"> 
    /**
     * devuelve un List<String> correspondiente a las letras de cada dia del mes
     *
     * @param year
     * @param mes
     * @return
     */
    public static List<String> letterDayOfMonth(Integer year, String mes) {
        List<String> letters = new ArrayList<>();
        try {
            LocalDate date;

            Month month = mesToMonth(mes);

            for (int i = 1; i <= month.maxLength(); i++) {

                date = LocalDate.of(year, month, i);
                String letra = firstLetterOfDay(date);
                letters.add(letra);

            }
        } catch (Exception e) {
            errorMessage("letterDayOfMonth() " + e.getLocalizedMessage());
        }
        return letters;
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="numeroMes(String mes)">

    /**
     * Devuelve el numero de mes iniciando enero= 0 hasta diciembre= 11
     *
     * @param mes
     * @return
     */
    public static Integer numeroMes(String mes) {
        List<String> listMeses = Arrays.asList("enero", "febrero", "marzo", "abril", "mayo", "junio", "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre");
        Integer i = -1;
        for (String l : listMeses) {
            i++;
            if (l.toLowerCase().equals(mes.toLowerCase())) {
                return i;
            }
        }

        return -1;

    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="numeroMesStartEneroWith1(String mes)">

    /**
     * devuelve el numero de mes iniciando enero en 1 y diciembre en 12
     *
     * @param mes
     * @return
     */
    public static Integer numeroMesStartEneroWith1(String mes) {
        List<String> listMeses = Arrays.asList("enero", "febrero", "marzo", "abril", "mayo", "junio", "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre");
        Integer i = -1;
        for (String l : listMeses) {
            i++;
            if (l.toLowerCase().equals(mes.toLowerCase())) {
                return i + 1;
            }
        }

        return -1;

    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Boolean isValidCierreMensual(Integer anioselected, String messelected, Integer diaminimo,ResourceBundle rs)">
    /**
     *
     * @param mes
     * @return
     */
    public static Boolean isValidCierreMensual(Integer anioselected, String messelected, Integer diaminimo, ResourceBundle rs) {
        try {

            if (anioselected <= 0) {
                JmoordbUtil.warningMessage(rs.getString("warning.anionegativo"));
                return false;
            }
            if (anioselected >  getAnioActual()) {
                JmoordbUtil.warningMessage(rs.getString("warning.anomayorqueactual"));
                return false;
            }

            Integer anio =  getAnioActual() - anioselected;
            if (anio.intValue() > 1) {
                JmoordbUtil.warningMessage(rs.getString("warning.aniomuyantiguo"));
                return false;
            }
            if (anio.intValue() == 1 && !messelected.toLowerCase().equals("diciembre")) {
                JmoordbUtil.warningMessage(rs.getString("warning.debecerrardiciembredelañoanterior"));
                return false;
            }
            Integer diaactual =  diaActual();
            Integer mesactual =  mesActual();
            //Esto pasarlo a avbravoutils
            Integer numeromesseleccionado =  numeroMes(messelected);

            if (numeromesseleccionado > mesactual) {
                JmoordbUtil.warningMessage(rs.getString("warning.mesacerrarmayoractual"));
                return false;
            }
            if (numeromesseleccionado.equals(mesactual) && diaactual < diaminimo) {
                JmoordbUtil.warningMessage(rs.getString("warning.estacerrandoelmesmuypronto"));
                return false;
            }
            return true;
        } catch (Exception e) {
            JmoordbUtil.warningMessage("isValidCierreMensual" + e.getLocalizedMessage());
        }
        return false;
    }

// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="String isValidCierreMensual(Integer anioselected, String messelected, Integer diaminimo)">
    /**
     *
     * @param mes
     * @return
     */
    public static String isValidCierreMensual(Integer anioselected, String messelected, Integer diaminimo) {
        try {

            if (anioselected <= 0) {
                return "warning.anionegativo";

            }
            if (anioselected >  getAnioActual()) {
                return "warning.anomayorqueactual";

            }

            Integer anio =  getAnioActual() - anioselected;
            if (anio.intValue() > 1) {
                return "warning.aniomuyantiguo";
            }
            if (anio.intValue() == 1 && !messelected.toLowerCase().equals("diciembre")) {
                return "warning.debecerrardiciembredelañoanterior";
            }
            Integer diaactual =  diaActual();
            Integer mesactual =  mesActual();
            //Esto pasarlo a avbravoutils
            Integer numeromesseleccionado =  numeroMes(messelected);

            if (numeromesseleccionado > mesactual) {
                return "warning.mesacerrarmayoractual";

            }
            if (numeromesseleccionado.equals(mesactual) && diaactual < diaminimo) {
                return "warning.estacerrandoelmesmuypronto";

            }
            return "";
        } catch (Exception e) {
            JmoordbUtil.warningMessage("isValidCierreMensual" + e.getLocalizedMessage());
        }
        return "";
    }

// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Date setHourToDate(Date date,Integer hour)">
    /**
     * asigna la hora a la fecha que se le pase Hora minima: 0 Hora maxima: 23
     *
     * @param date
     * @param hour
     * @return
     */
    public static Date setHourToDate(Date date, Integer hour, Integer minutes) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hour);
        calendar.add(Calendar.MINUTE, minutes);
        return calendar.getTime();
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="LocalDate convertirJavaDateToLocalDate(Date dateToConvert)">
    public static LocalDate convertirJavaDateToLocalDate(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="java.sql.Date convertirJavaDateToJavaSQLDate(java.util.Date date)">
    public static java.sql.Date convertirJavaDateToJavaSQLDate(java.util.Date date) {
        return java.sql.Date.valueOf( convertirJavaDateToLocalDate(date));
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="LocalDate convertirJavaDateToLocalDate(Date dateToConvert)">
    public static Date convertirLocalDateToJavaDate(LocalDate localDate) {
        return java.sql.Date.valueOf(localDate);
    }    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="descomponerFecha(Date date)">
    /**
     * Descompone una fecha en año, mes, dia, hora , minutos y nombre de mes
     *
     * @param date
     * @return
     */
    public static JmoordbDecomposedDate descomponerFechaMonthStartWith0(Date date) {
        JmoordbDecomposedDate decomposedDate = new JmoordbDecomposedDate();
        try {
            Integer day =  diaDeUnaFecha(date);
            Integer month =  mesDeUnaFechaStartEneroWith0(date);
            String nameOfMonth =  nameOfMonthStartWith0(month);
            Integer year =  anioDeUnaFecha(date);
            Integer hour =  horaDeUnaFecha(date);
            Integer minute =  minutosDeUnaFecha(date);
            decomposedDate.setDay(day);
            decomposedDate.setHour(hour);
            decomposedDate.setMinute(minute);
            decomposedDate.setMonth(month);
            decomposedDate.setNameOfMonth(nameOfMonth);
            decomposedDate.setYear(year);

        } catch (Exception e) {
            errorMessage("descomponerFechaMonthStartWith0() " + e.getLocalizedMessage());
        }
        return decomposedDate;
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="descomponerFecha(Date date)">
    /**
     * Descompone una fecha en año, mes, dia, hora , minutos y nombre de mes
     *
     * @param date
     * @return
     */
    public static JmoordbDecomposedDate descomponerFechaMonthStartWith1(Date date) {
        JmoordbDecomposedDate decomposedDate = new JmoordbDecomposedDate();
        try {
            Integer day =  diaDeUnaFecha(date);
            Integer month =  mesDeUnaFechaStartEneroWith1(date);
            String nameOfMonth =  nameOfMonthStartWith1(month);
            Integer year =  anioDeUnaFecha(date);
            Integer hour =  horaDeUnaFecha(date);
            Integer minute =  minutosDeUnaFecha(date);
            decomposedDate.setDay(day);
            decomposedDate.setHour(hour);
            decomposedDate.setMinute(minute);
            decomposedDate.setMonth(month);
            decomposedDate.setNameOfMonth(nameOfMonth);
            decomposedDate.setYear(year);

        } catch (Exception e) {
            errorMessage("descomponerFechaMonthStartWith1() " + e.getLocalizedMessage());
        }
        return decomposedDate;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Integer numberOfMonthBetweenDecomposedDate(DecomposedDate start, DecomposedDate end)">
    /**
     * Devuelve el numero de meses entre dos fechas de tipo DescomposedDate
     *
     * @param start
     * @param end
     * @return
     */
    public static Integer numberOfMonthBetweenDecomposedDate(JmoordbDecomposedDate start, JmoordbDecomposedDate end) {
        Integer meses = 0;
        try {

            if (start.getMonth() > end.getMonth()) {
                meses = (end.getMonth() + 12) - start.getMonth();
            } else {
                if (start.getYear() < end.getYear()) {
                    meses = (end.getMonth() + 12) - start.getMonth();
                } else {
                    meses = end.getMonth() - start.getMonth();
                }

            }
        } catch (Exception e) {
            errorMessage("numeroMesesEntreFechas() " + e.getLocalizedMessage());
        }
        return meses;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="List<JmoordbFechaDiaUtils> validarRangoFechas(Integer anioPartida, String nombreMesPartida)">
    /**
     * valida el rango de las fechas validas
     *
     * @param anioPartida
     * @param nombreMesPartida
     * @return
     */
    public static List<JmoordbFechaDiaUtils> validarRangoFechas(Integer anioPartida, String nombreMesPartida, Date fechaHoraPartida, Date fechaHoraRegreso) {
        List<JmoordbFechaDiaUtils> fechaDiaUtilsSaveList = new ArrayList<>();
        try {
            List<JmoordbFechaDiaUtils> fechaDiaUtilsInicialList =  nameOfDayOfDateOfMonth(anioPartida, nombreMesPartida);

//convertir la fecha de solicitud a LocalDate
            LocalDate start =  convertirJavaDateToLocalDate(fechaHoraPartida);
            LocalDate end =  convertirJavaDateToLocalDate(fechaHoraRegreso);

            //Buscar si esta en el intervalo de dias entre las fechas
            fechaDiaUtilsInicialList.forEach((fdu) -> {
                LocalDate l = fdu.getDate();

                if (l.isEqual(start) || l.isEqual(end) || (l.isAfter(start) && l.isBefore(end))) {
                    fechaDiaUtilsSaveList.add(fdu);

                }
            });

        } catch (Exception e) {
            errorMessage("validarRangoFechas() " + e.getLocalizedMessage());
        }
        return fechaDiaUtilsSaveList;
    }  // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="String showDate(Date date)">
    public static String showDate(Date date) {
        String h = "";
        try {
            h =  dateFormatToString(date, "dd/MM/yyyy");
        } catch (Exception e) {
            JmoordbUtil.errorMessage("showDate() " + e.getLocalizedMessage());
        }
        return h;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="String showHour(Date date)">
    public static String showHour(Date date) {
        String h = "";
        try {
            h =  hourFromDateToString(date);
        } catch (Exception e) {
            JmoordbUtil.errorMessage("showHour() " + e.getLocalizedMessage());
        }
        return h;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="java.util.Date  localDateToDate(LocalDate localDate)"> 
    public static java.util.Date localDateToDate(LocalDate localDate) {
        java.util.Date date = java.sql.Date.valueOf(localDate);
        return date;
    } // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="java.util.Date insertHorasMinutosSegundosToDate(Date date, Integer hora,Integer minutos, Integer segundos) "> 
    /**
     * Inserta horas, minutos y segundos a una fecha
     *
     * @param date
     * @param hora
     * @param minutos
     * @param segundos
     * @return
     */
    public static java.util.Date insertHoursMinutesSecondsToDate(Date date, Integer hora, Integer minutos, Integer segundos) {
        Integer anio = anioDeUnaFecha(date);
        Integer mes = mesDeUnaFecha(date);
        Integer dia =  diaDeUnaFecha(date);
        LocalDateTime start = LocalDateTime.of(anio, mes, dia, hora, minutos, segundos);
        Date ldate = Date.from(start.atZone(ZoneId.systemDefault()).toInstant());
        return ldate;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Date primerDiaDelMesActual()"> 
    /**
     *
     * @param month
     * @return Date primerDiaDelActual()
     */
    public static Date primerDiaDelMesActual() {
        LocalDate now = LocalDate.now();//# 2015-11-23
        Integer day = 1;
        LocalDate firstDay = LocalDate.of(anioActual(), mesActual(), day);
        Date date = java.sql.Date.valueOf(firstDay);
        return date;
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" Date primerDiaDelMesActualConPrimeraHoraDelDia()"> 

    /**
     *
     * @param month
     * @return Date primerDiaDelActual()
     */
    public static Date primerDiaDelMesActualConPrimeraHoraDelDia() {
        LocalDate now = LocalDate.now();//# 2015-11-23
        Integer day = 1;
        LocalDate firstDay = LocalDate.of(anioActual(), mesActual(), day);
        Date date = java.sql.Date.valueOf(firstDay);
        date = insertHoursMinutesSecondsToDate(date, 0, 0, 0);
        return date;
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Date primerDiaDelMesActualConHoraMinutosSegundos(Integer hora, Integer minutos, Integer segundos)"> 

    /**
     *
     * @param month
     * @return Date primerDiaDelActual()
     */
    public static Date primerDiaDelMesActualConHoraMinutosSegundos(Integer hora, Integer minutos, Integer segundos) {
        LocalDate now = LocalDate.now();//# 2015-11-23
        Integer day = 1;
        LocalDate firstDay = LocalDate.of(anioActual(), mesActual(), day);
        Date date = java.sql.Date.valueOf(firstDay);
        date = insertHoursMinutesSecondsToDate(date, hora, minutos, segundos);
        return date;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Date ultimoDiaDelMesActual(Integer year, Integer month) "> 
    public static Date ultimoDiaDelMesActual() {
        LocalDate now = LocalDate.now();//# 2015-11-23
        Integer day = numberDayOfMonth(anioActual(), mesActual());

        LocalDate firstDay = LocalDate.of(anioActual(), mesActual(), day);
        Date date = java.sql.Date.valueOf(firstDay);
        return date;
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Date ultimoDiaDelMesActualConHoraFinal() "> 

    public static Date ultimoDiaDelMesActualConHoraFinal() {
        LocalDate now = LocalDate.now();//# 2015-11-23
        Integer day = numberDayOfMonth(anioActual(), mesActual());

        LocalDate firstDay = LocalDate.of(anioActual(), mesActual(), day);
        Date date = java.sql.Date.valueOf(firstDay);
        date = insertHoursMinutesSecondsToDate(date, 23, 59, 0);
        return date;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Date ultimoDiaDelMesActualConHoraMinutoSegundo(Integer hora, Integer minuto, Integer segundo) "> 
    public static Date ultimoDiaDelMesActualConHoraMinutoSegundo(Integer hora, Integer minutos, Integer segundos) {
        LocalDate now = LocalDate.now();//# 2015-11-23
        Integer day = numberDayOfMonth(anioActual(), mesActual());

        LocalDate firstDay = LocalDate.of(anioActual(), mesActual(), day);
        Date date = java.sql.Date.valueOf(firstDay);
        date = insertHoursMinutesSecondsToDate(date, hora, minutos, segundos);
        return date;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Date convertStringWithDateTtoDate(String dateString) "> 
    /**
     * Convierte una fecha String con valores con T String str =
     * "2009-12-23T00:00:00";
     *
     * @param
     * @return Date primerDiaDelActual()
     */
    public static Date convertStringWithDateTtoDate(String dateStringWithT) {
        Date date = new Date();
        try {

            //     String str = "2009-12-23T00:00:00";
            //   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss. SSS");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:SSS");
            date = format.parse(dateStringWithT);

        } catch (Exception e) {
            System.out.println(" stringToDateWithT() " + e.getLocalizedMessage());
        }
        return date;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="java.sql.Date javasqlDateToJavaUtilDate(java.util.Date date) "> 
    /**
     * Convierte una fecha String con valores con T String str =
     * "2009-12-23T00:00:00";
     *
     * @param
     * @return Date primerDiaDelActual()
     */
    public static java.sql.Date javasqlDateToJavaUtilDate(java.util.Date date) {

        java.sql.Date d = new java.sql.Date(date.getTime());

        return d;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="java.sql.Date stringToDate(String str)"> 
    public static java.sql.Date stringToDate(String str) {
        Date date = new Date();
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:SSS");
            date = format.parse(str);
        } catch (Exception e) {
            System.out.println("stringToDate()");
        }
        return new java.sql.Date(date.getTime());
        //  return date;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="String fechaEnLetras(Date fecha)"> 
    public static String fechaEnLetras(Date fecha) {
        String text = "";
        try {

            Integer anio = anioDeUnaFecha(fecha);
            Integer mes = mesDeUnaFecha(fecha);

            Integer dia = diaDeUnaFecha(fecha);

            String nombreDia = nameOfDay(fecha);

            String nombremes = nameOfMonthStartWith1(mes);

            text = nombreDia + " " + dia + " de " + nombremes + " de " + anio;

        } catch (Exception e) {
            System.out.println("fechaEnLetras()" + e.getLocalizedMessage());
        }
        return text;
        //  return date;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="String fechaEnLetrasMinusculas(Date fecha)"> 
    public static String fechaEnLetrasMinusculas(Date fecha) {
        String text = "";
        try {

            Integer anio = anioDeUnaFecha(fecha);
            Integer mes = mesDeUnaFecha(fecha);

            Integer dia = diaDeUnaFecha(fecha);

            String nombreDia = nameOfDayMinusculas(fecha);

            String nombremes = nameOfMonthStartWith1(mes);

            text = nombreDia + " " + dia + " de " + nombremes + " de " + anio;

        } catch (Exception e) {
            System.out.println("fechaEnLetras()" + e.getLocalizedMessage());
        }
        return text;
        //  return date;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="esMismoDia(Date fechaInicio, Date fechaFin)"> 
    public static Boolean esMismoDia(Date fechaInicio, Date fechaFin) {
        Boolean iguales = false;
        try {

            Integer anio =  anioDeUnaFecha(fechaInicio);
            Integer aniofin =  anioDeUnaFecha(fechaFin);

            Integer mes =  mesDeUnaFecha(fechaInicio);
            Integer mesfin =  mesDeUnaFecha(fechaFin);

            Integer dia =  diaDeUnaFecha(fechaInicio);
            Integer diafin =  diaDeUnaFecha(fechaFin);

            if (anio.equals(aniofin) && mes.equals(mesfin) && dia.equals(diafin)) {
                iguales = true;
            }

        } catch (Exception e) {
            System.out.println("esMismoDia()" + e.getLocalizedMessage());
        }
        return iguales;
        //  return date;
    }
    // </editor-fold>
}
