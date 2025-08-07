
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
        
            

            input.close();
        }
    }
}
