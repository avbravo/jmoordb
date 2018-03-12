/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.ejbjmoordb.services;

import java.util.Date;
import javax.ejb.Stateless;

/**
 *
 * @author avbravo
 */
@Stateless
public class LookupServices {

    private String text;
    private String id;
    private String nombre;
    private String pais;
    private String descripcion;
    private String cedula;
    private String apellido;
    private String apellidomaterno;
    private String apellidopaterno;
    private String cliente;
    private String proveedor;
    private String usuario;
    private String rol;
    private String paciente;
    private String cargo;
    private String provincia;
    private String distrito;
    private String continente;
    private String planeta;
    private String marca;
    private String placa;
    private String vehiculo;
    private String agente;
    private String comprador;
    private String vendedor;
    private String banco;
    private String cuenta;
    private String sucursal;
    
   

    //fechas
    private Date fecha;
    private Date fechaDesde;
    private Date fechaHasta;
    private Date fechaincio;
    private Date fechafin;
    private Date fechanacimiento;
    private Date fechamuerte;
    private Date fechabaja;
    private Date fechaalta;
    private Date fecha1;
    private Date fecha2;
    private Date fecha3;
    private Date fecha4;
    private Date fecha5;
    private Date fecha6;
    private Date fecha7;
    private Date fecha8;
    private Date fecha9;
    private Date fecha10;
    private Date fecha11;
    private Date fecha12;
    private Date fecha13;
    private Date fecha14;
    private Date fecha15;
    
   /*
    numeros
    */
    private Integer key;
    private Integer primarykey;
    private Integer identero;
    private Integer numero;
    private Integer numeroDesde;
    private Integer numeroHasta;
    private Integer numero1;
    private Integer numero2;
    private Integer numero3;
    private Integer numero4;
    private Integer numero5;
    private Integer numero6;
    private Integer numero7;
    private Integer numero8;
    private Integer numero9;
    private Integer numero10;
    private Integer numero11;
    private Integer numero12;
    private Integer numero13;
    private Integer numero14;
    private Integer numero15;

 //Double
    private Double salario;
    private Double itbms;
    private Double ventas;
    private Double doubledesde;
    private Double doublehasta;
    private Double subtotal;
    private Double total;
    private Double saldo;
    private Double vuelto;
    private Double monto;
    private Double apagar;
    private Double cambio;
    private Double sueltobruto;
    private Double sueldoneto;
    private Double exoneracion;
    private Double totalexoneracion;
    private Double double1;
    private Double double2;
    private Double double3;
    private Double double4;
    private Double double5;
    private Double double6;
    private Double double7;
    private Double double8;
    private Double double9;
    private Double double10;
    private Double double11;
    private Double double12;
    private Double double13;
    private Double double14;
    private Double double15;

    public LookupServices() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getApellidomaterno() {
        return apellidomaterno;
    }

    public void setApellidomaterno(String apellidomaterno) {
        this.apellidomaterno = apellidomaterno;
    }

    public String getApellidopaterno() {
        return apellidopaterno;
    }

    public void setApellidopaterno(String apellidopaterno) {
        this.apellidopaterno = apellidopaterno;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getPaciente() {
        return paciente;
    }

    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getContinente() {
        return continente;
    }

    public void setContinente(String continente) {
        this.continente = continente;
    }

    public String getPlaneta() {
        return planeta;
    }

    public void setPlaneta(String planeta) {
        this.planeta = planeta;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(String vehiculo) {
        this.vehiculo = vehiculo;
    }

    public String getAgente() {
        return agente;
    }

    public void setAgente(String agente) {
        this.agente = agente;
    }

    public String getComprador() {
        return comprador;
    }

    public void setComprador(String comprador) {
        this.comprador = comprador;
    }

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(Date fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public Date getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(Date fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public Date getFechaincio() {
        return fechaincio;
    }

    public void setFechaincio(Date fechaincio) {
        this.fechaincio = fechaincio;
    }

    public Date getFechafin() {
        return fechafin;
    }

    public void setFechafin(Date fechafin) {
        this.fechafin = fechafin;
    }

    public Date getFechanacimiento() {
        return fechanacimiento;
    }

    public void setFechanacimiento(Date fechanacimiento) {
        this.fechanacimiento = fechanacimiento;
    }

    public Date getFechamuerte() {
        return fechamuerte;
    }

    public void setFechamuerte(Date fechamuerte) {
        this.fechamuerte = fechamuerte;
    }

    public Date getFechabaja() {
        return fechabaja;
    }

    public void setFechabaja(Date fechabaja) {
        this.fechabaja = fechabaja;
    }

    public Date getFechaalta() {
        return fechaalta;
    }

    public void setFechaalta(Date fechaalta) {
        this.fechaalta = fechaalta;
    }

    public Date getFecha1() {
        return fecha1;
    }

    public void setFecha1(Date fecha1) {
        this.fecha1 = fecha1;
    }

    public Date getFecha2() {
        return fecha2;
    }

    public void setFecha2(Date fecha2) {
        this.fecha2 = fecha2;
    }

    public Date getFecha3() {
        return fecha3;
    }

    public void setFecha3(Date fecha3) {
        this.fecha3 = fecha3;
    }

    public Date getFecha4() {
        return fecha4;
    }

    public void setFecha4(Date fecha4) {
        this.fecha4 = fecha4;
    }

    public Date getFecha5() {
        return fecha5;
    }

    public void setFecha5(Date fecha5) {
        this.fecha5 = fecha5;
    }

    public Date getFecha6() {
        return fecha6;
    }

    public void setFecha6(Date fecha6) {
        this.fecha6 = fecha6;
    }

    public Date getFecha7() {
        return fecha7;
    }

    public void setFecha7(Date fecha7) {
        this.fecha7 = fecha7;
    }

    public Date getFecha8() {
        return fecha8;
    }

    public void setFecha8(Date fecha8) {
        this.fecha8 = fecha8;
    }

    public Date getFecha9() {
        return fecha9;
    }

    public void setFecha9(Date fecha9) {
        this.fecha9 = fecha9;
    }

    public Date getFecha10() {
        return fecha10;
    }

    public void setFecha10(Date fecha10) {
        this.fecha10 = fecha10;
    }

    public Date getFecha11() {
        return fecha11;
    }

    public void setFecha11(Date fecha11) {
        this.fecha11 = fecha11;
    }

    public Date getFecha12() {
        return fecha12;
    }

    public void setFecha12(Date fecha12) {
        this.fecha12 = fecha12;
    }

    public Date getFecha13() {
        return fecha13;
    }

    public void setFecha13(Date fecha13) {
        this.fecha13 = fecha13;
    }

    public Date getFecha14() {
        return fecha14;
    }

    public void setFecha14(Date fecha14) {
        this.fecha14 = fecha14;
    }

    public Date getFecha15() {
        return fecha15;
    }

    public void setFecha15(Date fecha15) {
        this.fecha15 = fecha15;
    }

    public Integer getIdentero() {
        return identero;
    }

    public void setIdentero(Integer identero) {
        this.identero = identero;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Integer getNumeroDesde() {
        return numeroDesde;
    }

    public void setNumeroDesde(Integer numeroDesde) {
        this.numeroDesde = numeroDesde;
    }

    public Integer getNumeroHasta() {
        return numeroHasta;
    }

    public void setNumeroHasta(Integer numeroHasta) {
        this.numeroHasta = numeroHasta;
    }

    public Integer getNumero1() {
        return numero1;
    }

    public void setNumero1(Integer numero1) {
        this.numero1 = numero1;
    }

    public Integer getNumero2() {
        return numero2;
    }

    public void setNumero2(Integer numero2) {
        this.numero2 = numero2;
    }

    public Integer getNumero3() {
        return numero3;
    }

    public void setNumero3(Integer numero3) {
        this.numero3 = numero3;
    }

    public Integer getNumero4() {
        return numero4;
    }

    public void setNumero4(Integer numero4) {
        this.numero4 = numero4;
    }

    public Integer getNumero5() {
        return numero5;
    }

    public void setNumero5(Integer numero5) {
        this.numero5 = numero5;
    }

    public Integer getNumero6() {
        return numero6;
    }

    public void setNumero6(Integer numero6) {
        this.numero6 = numero6;
    }

    public Integer getNumero7() {
        return numero7;
    }

    public void setNumero7(Integer numero7) {
        this.numero7 = numero7;
    }

    public Integer getNumero8() {
        return numero8;
    }

    public void setNumero8(Integer numero8) {
        this.numero8 = numero8;
    }

    public Integer getNumero9() {
        return numero9;
    }

    public void setNumero9(Integer numero9) {
        this.numero9 = numero9;
    }

    public Integer getNumero10() {
        return numero10;
    }

    public void setNumero10(Integer numero10) {
        this.numero10 = numero10;
    }

    public Integer getNumero11() {
        return numero11;
    }

    public void setNumero11(Integer numero11) {
        this.numero11 = numero11;
    }

    public Integer getNumero12() {
        return numero12;
    }

    public void setNumero12(Integer numero12) {
        this.numero12 = numero12;
    }

    public Integer getNumero13() {
        return numero13;
    }

    public void setNumero13(Integer numero13) {
        this.numero13 = numero13;
    }

    public Integer getNumero14() {
        return numero14;
    }

    public void setNumero14(Integer numero14) {
        this.numero14 = numero14;
    }

    public Integer getNumero15() {
        return numero15;
    }

    public void setNumero15(Integer numero15) {
        this.numero15 = numero15;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public Double getItbms() {
        return itbms;
    }

    public void setItbms(Double itbms) {
        this.itbms = itbms;
    }

    public Double getVentas() {
        return ventas;
    }

    public void setVentas(Double ventas) {
        this.ventas = ventas;
    }

    public Double getDoubledesde() {
        return doubledesde;
    }

    public void setDoubledesde(Double doubledesde) {
        this.doubledesde = doubledesde;
    }

    public Double getDoublehasta() {
        return doublehasta;
    }

    public void setDoublehasta(Double doublehasta) {
        this.doublehasta = doublehasta;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public Double getVuelto() {
        return vuelto;
    }

    public void setVuelto(Double vuelto) {
        this.vuelto = vuelto;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public Double getApagar() {
        return apagar;
    }

    public void setApagar(Double apagar) {
        this.apagar = apagar;
    }

    public Double getCambio() {
        return cambio;
    }

    public void setCambio(Double cambio) {
        this.cambio = cambio;
    }

    public Double getSueltobruto() {
        return sueltobruto;
    }

    public void setSueltobruto(Double sueltobruto) {
        this.sueltobruto = sueltobruto;
    }

    public Double getSueldoneto() {
        return sueldoneto;
    }

    public void setSueldoneto(Double sueldoneto) {
        this.sueldoneto = sueldoneto;
    }

    public Double getExoneracion() {
        return exoneracion;
    }

    public void setExoneracion(Double exoneracion) {
        this.exoneracion = exoneracion;
    }

    public Double getTotalexoneracion() {
        return totalexoneracion;
    }

    public void setTotalexoneracion(Double totalexoneracion) {
        this.totalexoneracion = totalexoneracion;
    }

    public Double getDouble1() {
        return double1;
    }

    public void setDouble1(Double double1) {
        this.double1 = double1;
    }

    public Double getDouble2() {
        return double2;
    }

    public void setDouble2(Double double2) {
        this.double2 = double2;
    }

    public Double getDouble3() {
        return double3;
    }

    public void setDouble3(Double double3) {
        this.double3 = double3;
    }

    public Double getDouble4() {
        return double4;
    }

    public void setDouble4(Double double4) {
        this.double4 = double4;
    }

    public Double getDouble5() {
        return double5;
    }

    public void setDouble5(Double double5) {
        this.double5 = double5;
    }

    public Double getDouble6() {
        return double6;
    }

    public void setDouble6(Double double6) {
        this.double6 = double6;
    }

    public Double getDouble7() {
        return double7;
    }

    public void setDouble7(Double double7) {
        this.double7 = double7;
    }

    public Double getDouble8() {
        return double8;
    }

    public void setDouble8(Double double8) {
        this.double8 = double8;
    }

    public Double getDouble9() {
        return double9;
    }

    public void setDouble9(Double double9) {
        this.double9 = double9;
    }

    public Double getDouble10() {
        return double10;
    }

    public void setDouble10(Double double10) {
        this.double10 = double10;
    }

    public Double getDouble11() {
        return double11;
    }

    public void setDouble11(Double double11) {
        this.double11 = double11;
    }

    public Double getDouble12() {
        return double12;
    }

    public void setDouble12(Double double12) {
        this.double12 = double12;
    }

    public Double getDouble13() {
        return double13;
    }

    public void setDouble13(Double double13) {
        this.double13 = double13;
    }

    public Double getDouble14() {
        return double14;
    }

    public void setDouble14(Double double14) {
        this.double14 = double14;
    }

    public Double getDouble15() {
        return double15;
    }

    public void setDouble15(Double double15) {
        this.double15 = double15;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public Integer getPrimarykey() {
        return primarykey;
    }

    public void setPrimarykey(Integer primarykey) {
        this.primarykey = primarykey;
    }
   

    
    
}
