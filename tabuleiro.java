
import java.util.Scanner;
public class tabuleiro  {

    public static void main(String[] args) {



       Scanner input = new Scanner(System.in);
        
        ponto p1 = new ponto("x1", false);
        ponto p2 = new ponto("y1", false);
        ponto p3 = new ponto("vazio", true);
        ponto p4 = new ponto("x2", false);
        ponto p5 = new ponto("y2", false);


        while(true){

        
            System.out.println("sua vez, escolha qual peça quer mover, x1 ou x2?");
            String resposta1 = input.nextLine();

                
            if (resposta1 != "x2" || resposta1 != "x1") {
                System.out.println("jogada inválida, tente novamente");
            }
        
            if ((resposta1 == "x1" && p1.P == "x1") || resposta1 == "x2" && p1.P == "x2"){

        p1.disponibilidade = true;
        p1.P = "vazio";

        
        if (p2.disponibilidade == true){

            p2.disponibilidade = false;
            p2.P = resposta1;
        }

            if (p3.disponibilidade == true){
    
                p3.disponibilidade = false;
                p3.P = resposta1;
            }
                
                else{
                    
                    System.out.println("está peça está presa, escolha outra");
                }
    }

   
    if ((resposta1 == "x1" && p2.P == "x1") || resposta1 == "x2" && p2.P == "x2") {
        
        p2.disponibilidade = true;
        p2.P = "vazio";

        
        if (p1.disponibilidade == true) {

            p1.disponibilidade = false;
            p1.P = resposta1;
        }
            if (p3.disponibilidade == true) {
        
                p3.disponibilidade = false;
                p3.P = resposta1;
            }
                if (p5.disponibilidade == true) {

                    p5.disponibilidade = false;
                    p5.P = resposta1;
                }
                   
                    else{
                    
                    System.out.println("está peça está presa, escolha outra");
                }
    }

    
    if ((resposta1 == "x1" && p3.P == "x1") || resposta1 == "x2" && p3.P == "x2") {

        p3.disponibilidade = true;
        p3.P = "vazio";

        
        if (p1.disponibilidade == true) {

            p1.disponibilidade = false;
            p1.P = resposta1;
        }
            if (p2.disponibilidade == true){

                p2.disponibilidade = false;
                p2.P = resposta1;
            }
                if (p3.disponibilidade == true){
    
                    p3.disponibilidade = false;
                    p3.P = resposta1;
                }
                    if (p4.disponibilidade = true) {

                        p4.disponibilidade = false;
                        p4.P = resposta1;
                    }
                        if (p5.disponibilidade == true) {

                            p5.disponibilidade = false;
                            p5.P = resposta1;
                        }
                            
                            else{
                    
                                System.out.println("está peça está presa, escolha outra");
                            }
                        
    }

    
    if((resposta1 == "x1" && p4.P == "x1") || resposta1 == "x2" && p4.P == "x2" ){

        p4.disponibilidade = true;
        p4.P = "vazio";

       
        if(p3.disponibilidade = true){

            p3.disponibilidade = false;
            p3.P = resposta1;
        }
            if(p5.disponibilidade = true){

                p5.disponibilidade = false;
                p5.P = resposta1;
            }
               
                else{
                    
                    System.out.println("está peça está presa, escolha outra");
                } 
    }

  
   if ((resposta1 == "x1" && p5.P == "x1") || resposta1 == "x2" && p5.P == "x2") {

        p5.disponibilidade = true;
        p5.P = "vazio";

        
        if (p2.disponibilidade == true){

                p2.disponibilidade = false;
                p2.P = resposta1;
        }
                if(p3.disponibilidade = true){

                    p3.disponibilidade = false;
                    p3.P = resposta1;
                }
                    if (p4.disponibilidade = true) {

                        p4.disponibilidade = false;
                        p4.P = resposta1;
                    }
      
                        else{
                    
                            System.out.println("está peça está presa, escolha outra");
                        }
   }


            input.close();
        }
    }
}
