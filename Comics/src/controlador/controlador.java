package controlador;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import modelo.modelo;
import vista.interfaz;

public class controlador implements ActionListener, MouseListener{

    interfaz vista ;
    modelo modelo = new modelo();

    public enum AccionMVC
    {
        btnVerComics,
        btnAgregarComic,
        btnEliminarComic
    }

    public controlador(interfaz vista)
    {
        this.vista = vista;
    }

    public void iniciar()
    {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            SwingUtilities.updateComponentTreeUI(vista);
            vista.setVisible(true);
        } catch (UnsupportedLookAndFeelException ex){}
          catch (ClassNotFoundException ex){}
          catch (InstantiationException ex){}
          catch (IllegalAccessException ex){}

        this.vista.btnVerComics.setActionCommand("btnVerComics");
        this.vista.btnVerComics.addActionListener(this);
        this.vista.btnAgregarComic.setActionCommand("btnAgregarComic");
        this.vista.btnAgregarComic.addActionListener(this);
        this.vista.btnEliminarComic.setActionCommand("btnEliminarComic");
        this.vista.btnEliminarComic.addActionListener(this);

        this.vista.tablaComics.addMouseListener(this);
        this.vista.tablaComics.setModel(new DefaultTableModel());
    }

    public void mouseClicked(MouseEvent e){
        if(e.getButton()== 1){
            int fila = this.vista.tablaComics.rowAtPoint(e.getPoint());
            if (fila > -1){                
                this.vista.isbn.setText(String.valueOf(this.vista.tablaComics.getValueAt(fila, 0)));
                String titulo = String.valueOf(this.vista.tablaComics.getValueAt(fila, 1));
                this.vista.titulo.setText(titulo);
                this.vista.precio.setText(String.valueOf( this.vista.tablaComics.getValueAt(fila, 2)));
                this.vista.paginas.setText(String.valueOf( this.vista.tablaComics.getValueAt(fila, 3)));
                try{
                    this.vista.foto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/"+titulo+".jpg")));
                }catch(Exception ex){
                    this.vista.foto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ImageNotFound.png")));
                }
            }
        }
    }

    public void mousePressed(MouseEvent e){}

    public void mouseReleased(MouseEvent e){}

    public void mouseEntered(MouseEvent e){}

    public void mouseExited(MouseEvent e){}
 
    public void actionPerformed(ActionEvent e){
        switch (AccionMVC.valueOf(e.getActionCommand())){
            case btnVerComics:
                this.vista.tablaComics.setModel(this.modelo.getTablaComic());
                break;
            case btnAgregarComic:
                if (this.modelo.NuevoProducto(
                        this.vista.isbn.getText(),
                        this.vista.titulo.getText(),
                        this.vista.precio.getText(),
                        this.vista.paginas.getText()))
                {
                    this.vista.tablaComics.setModel(this.modelo.getTablaComic());
                    JOptionPane.showMessageDialog(vista,"Exito: Nuevo registro agregado.");
                    this.vista.isbn.setText("");
                    this.vista.titulo.setText("");
                    this.vista.precio.setText("0");
                    this.vista.paginas.setText("0");
                }
                else{
                    JOptionPane.showMessageDialog(vista,"Error: Los datos son incorrectos.");
                }
                break;
            case btnEliminarComic:
                if (this.modelo.EliminarProducto(this.vista.isbn.getText()))
                {
                    this.vista.tablaComics.setModel(this.modelo.getTablaComic());
                    JOptionPane.showMessageDialog(vista,"Exito: Registro eliminado.");
                    this.vista.isbn.setText("");
                    this.vista.titulo.setText("");
                    this.vista.precio.setText("0");
                    this.vista.paginas.setText("0");
                }
                break;       
        }
    }
}