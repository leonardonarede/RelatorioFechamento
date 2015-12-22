/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.rf.bo;

import br.com.rf.factory.ConnectionFactory;
import java.sql.Connection;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

/**
 *
 * @author LEONARDO
 */
public class RelatorioBO {
    private Connection con;
    public void gerar(Date dtrel){
        
        try {
            Calendar dtcalend = Calendar.getInstance();
            
            SimpleDateFormat formato = new SimpleDateFormat("dd.MM.yyyy");
            System.out.println(dtrel);
            dtcalend.setTime(dtrel);
            dtcalend.add(Calendar.DATE,-1);
            Date dtant = new Date(dtcalend.getTimeInMillis());
            //criando a conexão com o banco
            con =  ConnectionFactory.getInstance().obterConexao();         
            //criando relatorio a partir do modelo
            JasperReport jr = JasperCompileManager.compileReport("C:\\Relatorio\\relatorio1.jrxml");
            Map parametros = new HashMap();
            parametros.put("DT_REL", dtrel);
            parametros.put("DT_ANT", dtant);
            JasperPrint print = JasperFillManager.fillReport(jr,parametros,con);
            JasperExportManager.exportReportToPdfFile(print, "C:\\Relatorio\\Saida\\Relatorio00.pdf");
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
