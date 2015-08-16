package thomson;
/**
* Universidad Del Valle de Guatemala
* 12-ago-2015
* Pablo Díaz 13203
*/

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;


/**
 * Clase que construye el árbol sintáctico
 * @author Pablo
 * @param <T> Generic
 */
public class SyntaxTree<T> {

    private Nodo<T> root;
    private Nodo<T> actual;
    private Queue pila = new LinkedList();
    private ArrayList arrayNodos = new ArrayList();
    
    
    
   /**
    * Constructor 
    * inicializa la raiz del arbol
    */
    public SyntaxTree(){
        this.root= new Nodo("");
    }

   /**
    * Método que construye el árbol a partir de una cadena 
    * @param cadenaEnPrefix
    */
    public void buildTree(String cadenaEnPrefix){
        
        this.root = new Nodo(cadenaEnPrefix);
        buildPostFixTree((Nodo<T>) this.root);
        this.root=this.actual;
        

        
    }


    public Nodo<T> getRoot() {
        return root;
    }

    public void setRoot(Nodo<T> root) {
        this.root = root;
    }
    
    
    private void buildPostFixTree(Nodo<T> nodo){
       
        String texto_postfix = (String) nodo.getRegex();
        char letra_inicial = texto_postfix.charAt(0);
       
        //verificar si es un símbolo. Si lo es poner de una vez en la rama
        if(letra_inicial!='*'&&letra_inicial!='|'&&letra_inicial!='.'){
            
            String sub_cadena = texto_postfix.substring(1);
          
            Nodo nuevo = new Nodo((sub_cadena));
            nuevo.setId(""+letra_inicial);
            nuevo.setIsLeaf(true);
            arrayNodos.add(nuevo);
            
            //nuevo.setIsLeaf(true);
            if (pila.isEmpty())
                pila.add(nodo);
            pila.remove(this.root);
            pila.add(nuevo);
            buildPostFixTree(nuevo);
           
           
        }
         else//verificar que no sea terminal
            
        {
            //es un operador

            //si es un operador unario (como *)
            if(letra_inicial == '*'){
                //obtener un operador
                //se le asigna el nombre al nodo principal
                String sub_cadena = texto_postfix.substring(1);
                Nodo nuevo = new Nodo(sub_cadena);
                nuevo.setId((T) (""+letra_inicial));
                
                Nodo nodoPila = nodo;
                nuevo.setIzquierda(nodoPila);
                arrayNodos.add(nuevo);
                
                /*String sub_cadena = texto_prefix.substring(1);//falta validar...
                //print("subcadena: "+sub_cadena);
                nodo.setIzquierda(new Nodo(obtener_operando(sub_cadena)));
                //para generar recursivamente el nodo*/
                pila.add(nuevo);
               
           
            }

            //si es un operador unario (como |, concat)
            else if(letra_inicial=='|'||letra_inicial=='.'){
                //obtener dos operadores

                //se le asigna el nombre al nodo principal
               // nodo.setId((T) (""+letra_inicial));
               
               
                String sub_cadena = texto_postfix.substring(1);
               /* String primer_operando = this.obtener_operando(sub_cadena);
                String segundo_operando = this.obtener_operando(sub_cadena.substring(primer_operando.length()));*/
                Nodo nuevo = new Nodo(sub_cadena);
                nuevo.setId(""+letra_inicial);
              
                nuevo.setIzquierda((Nodo) pila.poll());
               
                //nodo.setIzquierda(new Nodo(primer_operando));
                //para generar recursivamente el nodo hijo izquierdo
               
                //buildPostFix(nuevo.getIzquierda());
                if (!pila.isEmpty())
                    nuevo.setDerecha((Nodo)pila.poll());
                else
                    nuevo.setDerecha(nodo);
                //el hijo izquierdo dejarlo vacío...
                //odo.setDerecha(new Nodo(segundo_operando));
                //para generar recursivamente el nodo hijo izquierdo
               // buildPostFix(nuevo.getDerecha());
                 pila.add(nuevo);
                 arrayNodos.add(nuevo);
                 this.actual = nuevo;
                
            }
        }//cierra else if leaf
        if (!pila.isEmpty()){
            Nodo siguiente = (Nodo) pila.poll();
            //verificar que no sea el ultimo a evaluar
            if (!siguiente.getRegex().equals("")){
                buildPostFixTree(siguiente);
                
            }
          
        }
        
        
        
        
    }

    public Nodo<T> getResultado() {
        return actual;
    }

    public void setResultado(Nodo<T> resultado) {
        this.actual = resultado;
    }

    public ArrayList getArrayNodos() {
        return arrayNodos;
    }

    public void setArrayNodos(ArrayList arrayNodos) {
        this.arrayNodos = arrayNodos;
    }
   
    
    
}