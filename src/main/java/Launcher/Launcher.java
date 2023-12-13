package Launcher;

import GUIs.Login;

/**
 * Corresponde a la clase que contiene el ejecutable.
 */
public class Launcher {
    /**
     * Corresponde al método ejecutable y al arrancar abre la ventana "Login".
     * @param args
     */
    public static void main(String[] args) {
        Login ventana = new Login();
        ventana.setVisible(true);
    }
}