package modelo;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class modelo extends database{

    public modelo (){}
    
    public class ModeloTablaNoEditable extends DefaultTableModel {

        public boolean isCellEditable(int row, int column){  
            return false;  
        }
    }

    public DefaultTableModel getTablaComic(){
        DefaultTableModel tablemodel = new ModeloTablaNoEditable();
        int registros = 0;
        String[] columNames = {"ISBN","Titulo","Precio","Paginas"};
      
        try{
            PreparedStatement pstm = this.getConexion().prepareStatement("SELECT count(ISBN) as total FROM Comics");
            ResultSet res = pstm.executeQuery();
            res.next();
            registros = res.getInt("total");
            res.close();
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }
        Object[][] data = new String[registros][5];
        try{
          
            PreparedStatement pstm = this.getConexion().prepareStatement("SELECT * FROM Comics");
            ResultSet res = pstm.executeQuery();
            int i=0;
            while(res.next()){
                data[i][0] = res.getString("ISBN");
                data[i][1] = res.getString("Titulo");
                data[i][2] = res.getString("Precio")+"€";
                data[i][3] = res.getString("Paginas");
            i++;
            }
            res.close();
            tablemodel.setDataVector(data, columNames );
        }catch(SQLException e){
            System.err.println( e.getMessage() );
        }
        return tablemodel;
    }

    public boolean NuevoComic(String isbn, String titulo , String precio, String paginas)
    {
        if( valida_datos(isbn, titulo, precio, paginas))
        {
            precio = precio.replace(",", ".");
            String q="INSERT INTO Comics (ISBN, Titulo, Precio, Paginas)"
                    + "VALUES('" + isbn + "','" + titulo + "', '" + precio + "'," + paginas + ")";
            try {
                PreparedStatement pstm = this.getConexion().prepareStatement(q);
                pstm.execute();
                pstm.close();
                return true;
            }catch(SQLException e){
                System.err.println(e.getMessage());
            }
            return false;
        }
        else
        return false;
    }

    public boolean EliminarProducto(String isbn)
    {
        boolean res=false;
        String q = "DELETE FROM Comics WHERE  ISBN='" + isbn + "'";
        try {
            PreparedStatement pstm = this.getConexion().prepareStatement(q);
            pstm.execute();
            pstm.close();
            res=true;
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }
        return res;
    }

    private boolean valida_datos(String id, String nombre , String precio, String cantidad)
    {
        if( id.equals("  -   "))
            return false;
        else if( nombre.length() > 0 && precio.length()>0 && cantidad.length() >0)
        {
            return true;
        }
        else{
            return false;
        }
    }
    
    public int numeroComics(){
        int num = 0;
        String q = "SELECT count(*) as Comics FROM Comics";
        try {
            PreparedStatement pstm = this.getConexion().prepareStatement(q);
            ResultSet res = pstm.executeQuery();
            while(res.next()){
                num = res.getInt("Comics");
            }
            res.close();
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }
        return num;
    }
    
    public int numeroDinero(){
        int num = 0;
        String q = "SELECT sum(Precio) as Dinero FROM Comics";
        try {
            PreparedStatement pstm = this.getConexion().prepareStatement(q);
            ResultSet res = pstm.executeQuery();
            while(res.next()){
                num = res.getInt("Dinero");
            }
            res.close();
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }
        return num;
    }
    
    public int numeroPaginas(){
        int num = 0;
        String q = "SELECT sum(Paginas) as Paginas FROM Comics";
        try {
            PreparedStatement pstm = this.getConexion().prepareStatement(q);
            ResultSet res = pstm.executeQuery();
            while(res.next()){
                num = res.getInt("Paginas");
            }
            res.close();
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }
        return num;
    }
}