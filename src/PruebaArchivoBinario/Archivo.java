/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PruebaArchivoBinario;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * @author samuelborjas
 */
public class Archivo {
   
    
    private RandomAccessFile players;
    public static final String ROOT_FOLDER = "jugadores";
    
    public Archivo(){
        try{
            new File(ROOT_FOLDER).mkdirs();
            players = new RandomAccessFile(ROOT_FOLDER+"/players.pl","rw");
            
        }
        catch(IOException e){
            System.out.println(e);
        }
    }
    
    public boolean AgregarPlayer(String name,String pass,int points,Boolean activado) throws IOException{  
       
        if(getName(name)){
           return false; 
        }
        players.seek(players.length()); 
        players.writeUTF(name); //escribir nombre
        players.writeUTF(pass); //escribir pass
        players.writeInt(points);   //escribir puntos
        players.writeBoolean(activado);
        return true;
         
    }
    
    public boolean Ingresar(String name, String pass) throws IOException{
        players.seek(0);        
        while(players.getFilePointer()<players.length()){
            String n = players.readUTF();
            String p = players.readUTF();
            players.readInt();
            boolean a = players.readBoolean();
            if(name.equals(n) && pass.equals(p)){
                if(a==true)
                    return true;
            }
        }
        return false;
    }
    
    public boolean cambiarPass(String n, String pV,String pN)throws IOException{
        players.seek(0);
        while(players.getFilePointer()<players.length()){
            String no = players.readUTF();
            String pa = players.readUTF();
            players.readInt();
            Boolean a = players.readBoolean();
            int pointer = (int)buscar(no);
            if((no.equals(no)&&pa.equals(pV))&&a==true){
                players.seek(players.getFilePointer()-pointer);
                players.readUTF();
                players.writeUTF(pN);
                players.readInt();
                players.readBoolean();
                return true;
            }
        }
        return false;
    }
    
    public boolean borrarUsuario(String name) throws IOException{
        players.seek(0);       
        while(players.getFilePointer() < players.length()){
            String no = players.readUTF();
            
            if(no.equals(name)){                                 
                players.readUTF();
                players.readInt();
                players.writeBoolean(false);
                return true;
            }else{
                players.readUTF();
                players.skipBytes(5);
            } 
        }
        return false;
        
    }
    
    public boolean getName(String name) throws IOException{
        players.seek(0);
        while(players.getFilePointer()<players.length()){
            String nom = players.readUTF();
            players.readUTF();
            players.readInt();
            players.readBoolean();
            if(nom.equals(name)){         
                return true;
            }
                                        
        }
        return false;
        
    }
    
    public boolean isActivado() throws IOException{
        players.seek(0);
        while(players.getFilePointer() < players.length()){
            players.readUTF();
            players.readUTF();
            players.readInt();
            Boolean act = players.readBoolean();
            if(act == true){
                return true;
            }
        }
        return false;
    }
    
    public long buscar(String name) throws IOException{
        players.seek(0);
        while(players.getFilePointer()<players.length()){
            if(players.readUTF().equals(name))
                return players.getFilePointer();
            players.readUTF();
            players.readInt();
            players.readBoolean();
            
        }
        return -1;
    }
    
    public long buscarPass(String pass) throws IOException{
        players.seek(0);
        while(players.getFilePointer() < players.length()){
            players.readUTF();
            String pas =players.readUTF();
            if(pas.equals(pass))
                return players.getFilePointer();
            players.readInt();
            players.readBoolean();
        }
        return -1;
    }
    
    public void imprimirDatos() throws IOException{
        players.seek(0);
        while(players.getFilePointer()<players.length()){
            
            String n = players.readUTF();
            String pass = players.readUTF();
            int p = players.readInt();
            boolean a = players.readBoolean();
            
            //if(a == true){
                System.out.println("Nombre: "+n+ " Contraseña: "+pass+ " Puntos: "+p + " Activado: "+a);
            //}
        }
    }
}
