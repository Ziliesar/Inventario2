package mp;

import java.util.Scanner;

public class MP {

    public static void main(String[] args) {
        Scanner leer=new Scanner(System.in);
        int cont=0;
        int cont2=0;
        int cont3=0;
        int cont4=0;
        int alto;
        int alto2;
        System.out.println("Ingrese un numero mayor o igual a 4: ");
        alto=leer.nextInt();
        alto2=alto;
        
        
        
        if(alto>=4){
            while(alto2>0){
               for (int i = 0; i < alto2; i++) {
                System.out.print(" ");
            }
                while(cont4>=0) {
                    System.out.print("*");
                    cont4--;
                }
                cont4=cont4+2;
                cont3=cont4;
                System.out.println("");
            alto2--;
            }
            
            
            
            while(alto>0){
               for (int i = 0; i < alto; i++) {
                System.out.print(" ");
            }
                while(cont>=0) {
                    System.out.print("*");
                    cont--;
                }
                cont2=cont2+2;
                cont=cont2;
                System.out.println("");
            alto--;
            }
        }else{
            System.out.println("El número ingresado no es válido");
        }
        
    }
    
}
