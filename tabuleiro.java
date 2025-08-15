import java.util.Scanner;
public class tabuleiro  {  

    public static void main(String[] args) {
   
   Scanner input = new Scanner(System.in);

    int jogador = 1;
    ponto p1 = new ponto("x1", false);
    ponto p2 = new ponto("y1", false);
    ponto p3 = new ponto("vazio", true);
    ponto p4 = new ponto("x2", false);
    ponto p5 = new ponto("y2", false);
    


while(true){

    if (jogador == 1) {


        // condição de derrota
    if(("x1".equals(p4.P) && "x2".equals(p5.P) || "x2".equals(p4.P) && "x1".equals(p5.P)) && p2.disponibilidade == false && p3.disponibilidade == false){

        System.out.println(" você perdeu!");
        break;
    }
        // condição de derrota
        if(("x1".equals(p1.P) && "x2".equals(p2.P) || "x2".equals(p1.P) && "x1".equals(p2.P)) && p3.disponibilidade == false && p5.disponibilidade == false){

            System.out.println(" você perdeu!");

            break;
        }
    
    // o jogador escolhe sua jogada
    System.out.println("sua vez, escolha qual peça quer mover, x1 ou x2?");
    String resposta1 = input.nextLine();

    // mensagem de erro em caso de jogada invalida     
    if (!"x1".equals(resposta1)  && !"x2".equals(resposta1)) {

        System.out.println("jogada invalida, tente novamente");
    }
 
    // o local da peça escolhida passa a ficar vazio
    if (("x1".equals(resposta1) && "x1".equals(p1.P)) || "x2".equals(resposta1) && "x2".equals(p1.P)){

        p1.disponibilidade = true;
        p1.P = "vazio";

        // possibilidades de movimento para o ponto livre, e ocupação do ponto livre
        if (p2.disponibilidade == true){

            p2.disponibilidade = false;
            p2.P = resposta1;
            System.out.println("a peça "+resposta1+" está no ponto 2 ");
            jogador = 2;
            continue;
        }

            else if (p3.disponibilidade == true){
    
                p3.disponibilidade = false;
                p3.P = resposta1;
                System.out.println("a peça "+resposta1+" está no ponto 3 ");
                jogador = 2;
                continue;
            }
                // a peça escolhida não pode se mover
                else{
                    
                    System.out.println("está peça está presa, escolha outra");
                    continue;
                }
    }

    // o local da peça escolhida passa a ficar vazio
    if (("x1".equals(resposta1) && "x1".equals(p2.P)) || "x2".equals(resposta1) && "x2".equals(p2.P)) {
        
        p2.disponibilidade = true;
        p2.P = "vazio";

        // possibilidades de movimento para o ponto livre, e ocupação do ponto livre
        if (p1.disponibilidade == true) {

            p1.disponibilidade = false;
            p1.P = resposta1;
            System.out.println("a peça "+resposta1+" está no ponto 1 ");
            jogador = 2;
            continue;
        }
            else if (p3.disponibilidade == true) {
        
                p3.disponibilidade = false;
                p3.P = resposta1;
                System.out.println("a peça "+resposta1+" está no ponto 3");
                jogador = 2;
                continue;
            }
                else if (p5.disponibilidade == true) {

                    p5.disponibilidade = false;
                    p5.P = resposta1;
                    System.out.println("a peça "+resposta1+" está no ponto 5");
                    jogador = 2;
                    continue;
                }
                    // a peça escolhida não pode se mover
                    else{
                    
                    System.out.println("está peça está presa, escolha outra");
                    continue;
                }
    }

    // o local da peça escolhida passa a ficar vazio
    if (("x1".equals(resposta1) && "x1".equals(p3.P)) || "x2".equals(resposta1) && "x2".equals(p3.P)) {

        p3.disponibilidade = true;
        p3.P = "vazio";

        // possibilidades de movimento para o ponto livre, e ocupação do ponto livre
        if (p1.disponibilidade == true) {

            p1.disponibilidade = false;
            p1.P = resposta1;
            System.out.println("a peça "+resposta1+" está no ponto 1 ");
            jogador = 2;
            continue;
        }
            else if (p2.disponibilidade == true){

                p2.disponibilidade = false;
                p2.P = resposta1;
                System.out.println("a peça "+resposta1+" está no ponto 2 ");
                jogador = 2;
                continue;
            }
                    else if (p4.disponibilidade == true) {

                        p4.disponibilidade = false;
                        p4.P = resposta1;
                        System.out.println("a peça "+resposta1+" está no ponto 4 ");
                        jogador = 2;
                        continue;
                    }
                        else if (p5.disponibilidade == true) {

                            p5.disponibilidade = false;
                            p5.P = resposta1;
                            System.out.println("a peça "+resposta1+" está no ponto 5 ");
                            jogador = 2;
                            continue;
                        }
                            // a peça escolhida não pode se mover
                            else{
                    
                                System.out.println("está peça está presa, escolha outra");
                                continue;
                            }
                        
    }

    // o local da peça escolhida passa a ficar vazio 
    if(("x1".equals(resposta1) && "x1".equals(p4.P)) || "x2".equals(resposta1) && "x2".equals(p4.P) ){

        p4.disponibilidade = true;
        p4.P = "vazio";

        // possibilidades de movimento para o ponto livre, e ocupação do ponto livre
        if(p3.disponibilidade == true){

            p3.disponibilidade = false;
            p3.P = resposta1;
            System.out.println("a peça "+resposta1+" está no ponto 3 ");
            jogador = 2;
            continue;
        }
            else if(p5.disponibilidade == true){

                p5.disponibilidade = false;
                p5.P = resposta1;
                System.out.println("a peça "+resposta1+" está no ponto 5 ");
                jogador = 2;
                continue;
            }
                // a peça escolhida não pode se mover
                else{
                    
                    System.out.println("está peça está presa, escolha outra");
                    continue;
                } 
    }

   // o local da peça escolhida passa a ficar vazio
   if (("x1".equals(resposta1) && "x1".equals(p5.P)) || "x2".equals(resposta1) && "x2".equals(p5.P)) {

        p5.disponibilidade = true;
        p5.P = "vazio";

        // possibilidades de movimento para o ponto livre, e ocupação do ponto livre
        if (p2.disponibilidade == true){

                p2.disponibilidade = false;
                p2.P = resposta1;
                System.out.println("a peça "+resposta1+" está no ponto 2 ");
                jogador = 2;
                continue;
        }
                else if(p3.disponibilidade == true){

                    p3.disponibilidade = false;
                    p3.P = resposta1;
                    System.out.println("a peça "+resposta1+" está no ponto 3 ");
                    jogador = 2;
                    continue;
                }
                    else if (p4.disponibilidade == true) {

                        p4.disponibilidade = false;
                        p4.P = resposta1;
                        System.out.println("a peça "+resposta1+" está no ponto 4 ");
                        jogador = 2;
                        continue;
                    }
                        // a peça escolhida não pode se mover
                        else{
                    
                            System.out.println("está peça está presa, escolha outra");
                            continue;
                        }
   }
    
}

else if (jogador == 2) {
    
    // o jogador escolhe sua jogada
    System.out.println("sua vez, escolha qual peça quer mover, y1 ou y2?");
    String resposta2 = input.nextLine();

    // mensagem de erro em caso de jogada invalida     
    if (!"y1".equals(resposta2)  && !"y2".equals(resposta2)) {

        System.out.println("jogada invalida, tente novamente");
    }
 
    // o local da peça escolhida passa a ficar vazio
    if (("y1".equals(resposta2) && "y1".equals(p1.P)) || "y2".equals(resposta2) && "y2".equals(p1.P)){

        p1.disponibilidade = true;
        p1.P = "vazio";

        // possibilidades de movimento para o ponto livre, e ocupação do ponto livre
        if (p2.disponibilidade == true){

            p2.disponibilidade = false;
            p2.P = resposta2;
            System.out.println("a peça "+resposta2+" está no ponto 2 ");
            jogador = 1;
            continue;
        }

            else if (p3.disponibilidade == true){
    
                p3.disponibilidade = false;
                p3.P = resposta2;
                System.out.println("a peça "+resposta2+" está no ponto 3 ");
                jogador = 1;
                continue;
            }
                // a peça escolhida não pode se mover
                else{
                    
                    System.out.println("está peça está presa, escolha outra");
                    continue;
                }
    }

    // o local da peça escolhida passa a ficar vazio
    if (("y1".equals(resposta2) && "y1".equals(p2.P)) || "y2".equals(resposta2) && "y2".equals(p2.P)) {
        
        p2.disponibilidade = true;
        p2.P = "vazio";

        // possibilidades de movimento para o ponto livre, e ocupação do ponto livre
        if (p1.disponibilidade == true) {

            p1.disponibilidade = false;
            p1.P = resposta2;
            System.out.println("a peça "+resposta2+" está no ponto 1 ");
            jogador = 1;
            continue;
        }
            else if (p3.disponibilidade == true) {
        
                p3.disponibilidade = false;
                p3.P = resposta2;
                System.out.println("a peça "+resposta2+" está no ponto 3");
                jogador = 1;
                continue;
            }
                else if (p5.disponibilidade == true) {

                    p5.disponibilidade = false;
                    p5.P = resposta2;
                    System.out.println("a peça "+resposta2+" está no ponto 5");
                    jogador = 1;
                    continue;
                }
                    // a peça escolhida não pode se mover
                    else{
                    
                    System.out.println("está peça está presa, escolha outra");
                    continue;
                }
    }

    // o local da peça escolhida passa a ficar vazio
    if (("y1".equals(resposta2) && "y1".equals(p3.P)) || "y2".equals(resposta2) && "y2".equals(p3.P)) {

        p3.disponibilidade = true;
        p3.P = "vazio";

        // possibilidades de movimento para o ponto livre, e ocupação do ponto livre
        if (p1.disponibilidade == true) {

            p1.disponibilidade = false;
            p1.P = resposta2;
            System.out.println("a peça "+resposta2+" está no ponto 1 ");
            jogador = 1;
            continue;
        }
            else if (p2.disponibilidade == true){

                p2.disponibilidade = false;
                p2.P = resposta2;
                System.out.println("a peça "+resposta2+" está no ponto 2 ");
                jogador = 1;
                continue;
            }
                    else if (p4.disponibilidade == true) {

                        p4.disponibilidade = false;
                        p4.P = resposta2;
                        System.out.println("a peça "+resposta2+" está no ponto 4 ");
                        jogador = 1;
                        continue;
                    }
                        else if (p5.disponibilidade == true) {

                            p5.disponibilidade = false;
                            p5.P = resposta2;
                            System.out.println("a peça "+resposta2+" está no ponto 5 ");
                            jogador = 1;
                            continue;
                        }
                            // a peça escolhida não pode se mover
                            else{
                    
                                System.out.println("está peça está presa, escolha outra");
                                continue;
                            }
                        
    }

    // o local da peça escolhida passa a ficar vazio 
    if(("x1".equals(resposta2) && "x1".equals(p4.P)) || "x2".equals(resposta2) && "x2".equals(p4.P) ){

        p4.disponibilidade = true;
        p4.P = "vazio";

        // possibilidades de movimento para o ponto livre, e ocupação do ponto livre
        if(p3.disponibilidade == true){

            p3.disponibilidade = false;
            p3.P = resposta2;
            System.out.println("a peça "+resposta2+" está no ponto 3 ");
            jogador = 1;
            continue;
        }
            else if(p5.disponibilidade == true){

                p5.disponibilidade = false;
                p5.P = resposta2;
                System.out.println("a peça "+resposta2+" está no ponto 5 ");
                jogador = 1;
                continue;
            }
                // a peça escolhida não pode se mover
                else{
                    
                    System.out.println("está peça está presa, escolha outra");
                    continue;
                } 
    }

   // o local da peça escolhida passa a ficar vazio
   if (("y1".equals(resposta2) && "y1".equals(p5.P)) || "y2".equals(resposta2) && "y2".equals(p5.P)) {

        p5.disponibilidade = true;
        p5.P = "vazio";

        // possibilidades de movimento para o ponto livre, e ocupação do ponto livre
        if (p2.disponibilidade == true){

                p2.disponibilidade = false;
                p2.P = resposta2;
                System.out.println("a peça "+resposta2+" está no ponto 2 ");
                jogador = 1;
                continue;
        }
                else if(p3.disponibilidade == true){

                    p3.disponibilidade = false;
                    p3.P = resposta2;
                    System.out.println("a peça "+resposta2+" está no ponto 3 ");
                    jogador = 1;
                    continue;
                }
                    else if (p4.disponibilidade == true) {

                        p4.disponibilidade = false;
                        p4.P = resposta2;
                        System.out.println("a peça "+resposta2+" está no ponto 4 ");
                        jogador = 1;
                        continue;
                    }
                        // a peça escolhida não pode se mover
                        else{
                    
                            System.out.println("está peça está presa, escolha outra");
                            continue;
                        }
   }
}

}
input.close();

}}
