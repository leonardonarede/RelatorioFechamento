package br.com.rf.factory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ConnectionFactory{
    private static ConnectionFactory connectionFactory;
    private Connection connection;

    public static ConnectionFactory getInstance()
     {
       if (connectionFactory == null) {
           connectionFactory = new ConnectionFactory();
       }
       return connectionFactory;
     }
 
      public Connection obterConexao()
       throws SQLException, ClassNotFoundException, Exception{

                    /*Inicio lendo configurações para o banco*/  
                      String datadir = "";
                      String host = "";
                      String user = "";
                      String pass = "";
                      try {
                          String local = new File("./bdconfig.txt").getCanonicalFile().toString();
                          File arq = new File(local);
                          boolean existe = arq.exists();        
                          if (existe){
                                  FileReader fr = new FileReader( arq );
                                  BufferedReader br = new BufferedReader( fr );
                                  while(br.ready()){
                                      String linha = br.readLine();
                                      if(linha.contains("DBdir:")){
                                          datadir = linha.replace("DBdir:", "").replace(" ", "");
                                      }
                                      if(linha.contains("DBUser:")){
                                          user = linha.replace("DBUser:", "").replace(" ", "");
                                      }
                                      if(linha.contains("DBPass:")){
                                          pass = linha.replace("DBPass:", "").replace(" ", "");
                                      }
                                      if(linha.contains("DBhost:")){
                                          host = linha.replace("DBhost:", "").replace(" ", "");
                                      }
                                  }

                           }
                      } catch (Exception e) {
                          StackTraceElement st[] = e.getStackTrace();
                          String erro = "";
                          for (int i = 0;i < st.length;i++){
                              erro += st[i].toString() + "\n";
                          } 
                          JOptionPane.showMessageDialog(null, erro, "Mensagem de Erro",JOptionPane.ERROR_MESSAGE);
                      }
                    /*Fim lendo configurações para o banco*/ 
            if ((this.connection == null) || (this.connection.isClosed())){
             Class.forName("org.firebirdsql.jdbc.FBDriver");
             String url = "jdbc:firebirdsql:"+host+"/3050:"+datadir+"?defaultResultSetHoldable=True";
             try{
                this.connection = DriverManager.getConnection(url, user, pass);
                if (this.connection != null) {
                return this.connection;
                }
            }catch (Exception ex){
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Conexao com o banco nao realizada", "Mensagem de Erro",JOptionPane.ERROR_MESSAGE);
                throw new Exception("Conexao com o banco nao realizada");
            }
            return this.connection;
        }
        return this.connection;
 }
}
