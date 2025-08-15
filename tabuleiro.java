import java.io.IOException;
import java.net.*;
import java.util.Map;
import java.util.Scanner;

public class tabuleiro {
    private static Scanner Terminal = new Scanner(System.in);

    private static String[] Configuracao() {
        String modo, protocolo, host, portaStr;

        while (true) {
            System.out.print("Você quer hospedar uma partida (selecione h) ou conectar-se a uma(selecione c)? ");
            modo = Terminal.nextLine().toLowerCase();
            if (modo.equals("h") || modo.equals("c")) break;
            System.out.println("Opção inválida. Escolha 'h' ou 'c'.");
        }

        while (true) {
            System.out.print("quer usar qual protocolo de transporte tcp ou udp? ");
            protocolo = Terminal.nextLine().toLowerCase();
            if (protocolo.equals("tcp") || protocolo.equals("udp")) break;
            System.out.println("Protocolo inválido. Escolha 'tcp' ou 'udp'.");
        }

        System.out.print("Deseja utilizar qual endereço IP? (IPv4 ou IPv6): ");
        host = Terminal.nextLine();

        while (true) {
            System.out.print("insira a porta (ex: 13690): ");
            portaStr = Terminal.nextLine();
            try {
                int porta = Integer.parseInt(portaStr);
                if (porta >= 1024 && porta <= 65535) break;
                System.out.println("Por favor, insira um número de porta entre 1024 e 65535.");
            } catch (NumberFormatException e) {
                System.out.println("Porta inválida. Digite um número.");
            }
        }
        return new String[]{modo, protocolo, host, portaStr};
    }


    public static void main(String[] args) {
        rede Rede = new rede();
        String[] config = Configuracao();
        String modo = config[0];
        String protocoloStr = config[1];
        String host = config[2];
        int porta = Integer.parseInt(config[3]);
        rede.Protocolo protocolo;
        if (protocoloStr.equals("tcp")) {
            protocolo = rede.Protocolo.TCP;
        } else {
            protocolo = rede.Protocolo.UDP;
        }
        Scanner input = new Scanner(System.in);
        int turno = 1;
        int jogador;

        ponto p1 = new ponto("x1", false);
        ponto p2 = new ponto("y1", false);
        ponto p3 = new ponto("vazio", true);
        ponto p4 = new ponto("x2", false);
        ponto p5 = new ponto("y2", false);
        Socket socketTcpComunicacao = null;
        DatagramSocket socketUdp = null;
        SocketAddress enderecoOponente = null;
        AutoCloseable socketPrincipal = null;

        try {
            if (modo.equals("h")) {
                jogador = 1;
                System.out.printf("\n[INFO] Hospedando partida em %s:%d usando %s.\n", host, porta, protocolo.toString());
                socketPrincipal = Rede.criarSocketComunicacao(protocolo, host, porta);

                if (protocolo == rede.Protocolo.TCP) {
                    ServerSocket servidorTcp = (ServerSocket) socketPrincipal;
                    System.out.println("[INFO] Aguardando conexão do oponente");
                    socketTcpComunicacao = servidorTcp.accept();
                    enderecoOponente = socketTcpComunicacao.getRemoteSocketAddress();
                    System.out.println("[INFO] Oponente conectado de " + enderecoOponente + ". Partida iniciada!");
                } else { // UDP
                    socketUdp = (DatagramSocket) socketPrincipal;
                    System.out.println("[INFO] Aguardando a primeira mensagem do oponente para iniciar");
                    rede.pacoteRecebido pacoteInicial = Rede.receberDados(socketUdp, 1024);
                    if (pacoteInicial != null && "iniciar".equals(pacoteInicial.dados.get("status"))) {
                        enderecoOponente = pacoteInicial.enderecoRemetente;
                        System.out.println("[INFO] Oponente conectado de " + enderecoOponente + ". Partida iniciada!");
                    } else {
                        throw new IOException("Falha ao estabelecer conexão UDP.");
                    }
                }
            } else { // modo 'c'
                jogador = 2;
                System.out.printf("\n[INFO] Conectando-se a %s:%d usando %s...\n", host, porta, protocolo.toString());
                enderecoOponente = new InetSocketAddress(host, porta);

                if (protocolo == rede.Protocolo.TCP) {
                    socketTcpComunicacao = new Socket();
                    socketTcpComunicacao.connect(enderecoOponente);
                    System.out.println("[INFO] Conectado ao hospedeiro. Partida iniciada!");
                } else { // UDP
                    socketUdp = new DatagramSocket();
                    Rede.enviarDados(socketUdp, Map.of("status", "iniciar"), enderecoOponente);
                    System.out.println("[INFO] Mensagem de início enviada. Aguardando a primeira mensagem do hospedeiro.");
                }
            }

            System.out.println("\n--- Partida iniciada ---");
            while (true) {

                if (turno == jogador) {
                    // --- Turno do Jogador Local ---
                    String pecaEscolhida;
                    boolean jogadaValida = false;

                    if (turno == 1) {
                        // --- LÓGICA DO JOGADOR 1 REINTEGRADA ---

                        // condição de vitoria
                        if (("y1".equals(p4.P) && "y2".equals(p5.P) || "y2".equals(p4.P) && "y1".equals(p5.P)) && !p2.disponibilidade && !p3.disponibilidade) {
                            System.out.println(" você ganhou!");
                            break;
                        }
                        // condição de vitoria
                        if (("y1".equals(p1.P) && "y2".equals(p2.P) || "y2".equals(p1.P) && "y1".equals(p2.P)) && !p3.disponibilidade && !p5.disponibilidade) {
                            System.out.println(" você ganhou!");
                            break;
                        }
                        // condição de derrota
                        if (("x1".equals(p4.P) && "x2".equals(p5.P) || "x2".equals(p4.P) && "x1".equals(p5.P)) && !p2.disponibilidade && !p3.disponibilidade) {
                            System.out.println(" você perdeu!");
                            break;
                        }
                        // condição de derrota
                        if (("x1".equals(p1.P) && "x2".equals(p2.P) || "x2".equals(p1.P) && "x1".equals(p2.P)) && !p3.disponibilidade && !p5.disponibilidade) {
                            System.out.println(" você perdeu!");
                            break;
                        }

                        System.out.println("\nSua vez (JOGADOR 1). Escolha qual peça quer mover: x1 ou x2?");
                        pecaEscolhida = input.nextLine();

                        if (("x1".equals(pecaEscolhida) && "x1".equals(p1.P)) || "x2".equals(pecaEscolhida) && "x2".equals(p1.P)) {
                            p1.disponibilidade = true; p1.P = "vazio";
                            if (p2.disponibilidade) { p2.disponibilidade = false; p2.P = pecaEscolhida; System.out.println("a peça " + pecaEscolhida + " está no ponto 2 "); jogadaValida = true; }
                            else if (p3.disponibilidade) { p3.disponibilidade = false; p3.P = pecaEscolhida; System.out.println("a peça " + pecaEscolhida + " está no ponto 3 "); jogadaValida = true; }
                            else { System.out.println("está peça está presa, escolha outra"); p1.disponibilidade = false; p1.P = pecaEscolhida; }
                        }
                        else if (("x1".equals(pecaEscolhida) && "x1".equals(p2.P)) || "x2".equals(pecaEscolhida) && "x2".equals(p2.P)) {
                            p2.disponibilidade = true; p2.P = "vazio";
                            if (p1.disponibilidade) { p1.disponibilidade = false; p1.P = pecaEscolhida; System.out.println("a peça " + pecaEscolhida + " está no ponto 1 "); jogadaValida = true; }
                            else if (p3.disponibilidade) { p3.disponibilidade = false; p3.P = pecaEscolhida; System.out.println("a peça " + pecaEscolhida + " está no ponto 3"); jogadaValida = true; }
                            else if (p5.disponibilidade) { p5.disponibilidade = false; p5.P = pecaEscolhida; System.out.println("a peça " + pecaEscolhida + " está no ponto 5"); jogadaValida = true; }
                            else { System.out.println("está peça está presa, escolha outra"); p2.disponibilidade = false; p2.P = pecaEscolhida; }
                        }
                        else if (("x1".equals(pecaEscolhida) && "x1".equals(p3.P)) || "x2".equals(pecaEscolhida) && "x2".equals(p3.P)) {
                            p3.disponibilidade = true; p3.P = "vazio";
                            if (p1.disponibilidade) { p1.disponibilidade = false; p1.P = pecaEscolhida; System.out.println("a peça " + pecaEscolhida + " está no ponto 1 "); jogadaValida = true; }
                            else if (p2.disponibilidade) { p2.disponibilidade = false; p2.P = pecaEscolhida; System.out.println("a peça " + pecaEscolhida + " está no ponto 2 "); jogadaValida = true; }
                            else if (p4.disponibilidade) { p4.disponibilidade = false; p4.P = pecaEscolhida; System.out.println("a peça " + pecaEscolhida + " está no ponto 4 "); jogadaValida = true; }
                            else if (p5.disponibilidade) { p5.disponibilidade = false; p5.P = pecaEscolhida; System.out.println("a peça " + pecaEscolhida + " está no ponto 5 "); jogadaValida = true; }
                            else { System.out.println("está peça está presa, escolha outra"); p3.disponibilidade = false; p3.P = pecaEscolhida; }
                        }
                        else if (("x1".equals(pecaEscolhida) && "x1".equals(p4.P)) || "x2".equals(pecaEscolhida) && "x2".equals(p4.P)) {
                            p4.disponibilidade = true; p4.P = "vazio";
                            if (p3.disponibilidade) { p3.disponibilidade = false; p3.P = pecaEscolhida; System.out.println("a peça " + pecaEscolhida + " está no ponto 3 "); jogadaValida = true; }
                            else if (p5.disponibilidade) { p5.disponibilidade = false; p5.P = pecaEscolhida; System.out.println("a peça " + pecaEscolhida + " está no ponto 5 "); jogadaValida = true; }
                            else { System.out.println("está peça está presa, escolha outra"); p4.disponibilidade = false; p4.P = pecaEscolhida; }
                        }
                        else if (("x1".equals(pecaEscolhida) && "x1".equals(p5.P)) || "x2".equals(pecaEscolhida) && "x2".equals(p5.P)) {
                            p5.disponibilidade = true; p5.P = "vazio";
                            if (p2.disponibilidade) { p2.disponibilidade = false; p2.P = pecaEscolhida; System.out.println("a peça " + pecaEscolhida + " está no ponto 2 "); jogadaValida = true; }
                            else if (p3.disponibilidade) { p3.disponibilidade = false; p3.P = pecaEscolhida; System.out.println("a peça " + pecaEscolhida + " está no ponto 3 "); jogadaValida = true; }
                            else if (p4.disponibilidade) { p4.disponibilidade = false; p4.P = pecaEscolhida; System.out.println("a peça " + pecaEscolhida + " está no ponto 4 "); jogadaValida = true; }
                            else { System.out.println("está peça está presa, escolha outra"); p5.disponibilidade = false; p5.P = pecaEscolhida; }
                        }

                    } else { // turno == 2
                        // --- LÓGICA DO JOGADOR 2 REINTEGRADA ---

                        // condição de vitoria
                        if (("x1".equals(p4.P) && "x2".equals(p5.P) || "x2".equals(p4.P) && "x1".equals(p5.P)) && !p2.disponibilidade && !p3.disponibilidade) {
                            System.out.println(" você ganhou!");
                            break;
                        }
                        // condição de vitoria
                        if (("x1".equals(p1.P) && "x2".equals(p2.P) || "x2".equals(p1.P) && "x1".equals(p2.P)) && !p3.disponibilidade && !p5.disponibilidade) {
                            System.out.println(" você ganhou!");
                            break;
                        }
                        // condição de derrota
                        if (("y1".equals(p4.P) && "y2".equals(p5.P) || "y2".equals(p4.P) && "y1".equals(p5.P)) && !p2.disponibilidade && !p3.disponibilidade) {
                            System.out.println(" você perdeu!");
                            break;
                        }
                        // condição de derrota
                        if (("y1".equals(p1.P) && "y2".equals(p2.P) || "y2".equals(p1.P) && "y1".equals(p2.P)) && !p3.disponibilidade && !p5.disponibilidade) {
                            System.out.println(" você perdeu!");
                            break;
                        }
                        
                        System.out.println("\nSua vez (JOGADOR 2). Escolha qual peça quer mover: y1 ou y2?");
                        pecaEscolhida = input.nextLine();

                        if (("y1".equals(pecaEscolhida) && "y1".equals(p1.P)) || "y2".equals(pecaEscolhida) && "y2".equals(p1.P)) {
                            p1.disponibilidade = true; p1.P = "vazio";
                            if (p2.disponibilidade) { p2.disponibilidade = false; p2.P = pecaEscolhida; System.out.println("a peça " + pecaEscolhida + " está no ponto 2 "); jogadaValida = true; }
                            else if (p3.disponibilidade) { p3.disponibilidade = false; p3.P = pecaEscolhida; System.out.println("a peça " + pecaEscolhida + " está no ponto 3 "); jogadaValida = true; }
                            else { System.out.println("está peça está presa, escolha outra"); p1.disponibilidade = false; p1.P = pecaEscolhida; }
                        }
                        else if (("y1".equals(pecaEscolhida) && "y1".equals(p2.P)) || "y2".equals(pecaEscolhida) && "y2".equals(p2.P)) {
                            p2.disponibilidade = true; p2.P = "vazio";
                            if (p1.disponibilidade) { p1.disponibilidade = false; p1.P = pecaEscolhida; System.out.println("a peça " + pecaEscolhida + " está no ponto 1 "); jogadaValida = true; }
                            else if (p3.disponibilidade) { p3.disponibilidade = false; p3.P = pecaEscolhida; System.out.println("a peça " + pecaEscolhida + " está no ponto 3"); jogadaValida = true; }
                            else if (p5.disponibilidade) { p5.disponibilidade = false; p5.P = pecaEscolhida; System.out.println("a peça " + pecaEscolhida + " está no ponto 5"); jogadaValida = true; }
                            else { System.out.println("está peça está presa, escolha outra"); p2.disponibilidade = false; p2.P = pecaEscolhida; }
                        }
                        else if (("y1".equals(pecaEscolhida) && "y1".equals(p3.P)) || "y2".equals(pecaEscolhida) && "y2".equals(p3.P)) {
                            p3.disponibilidade = true; p3.P = "vazio";
                            if (p1.disponibilidade) { p1.disponibilidade = false; p1.P = pecaEscolhida; System.out.println("a peça " + pecaEscolhida + " está no ponto 1 "); jogadaValida = true; }
                            else if (p2.disponibilidade) { p2.disponibilidade = false; p2.P = pecaEscolhida; System.out.println("a peça " + pecaEscolhida + " está no ponto 2 "); jogadaValida = true; }
                            else if (p4.disponibilidade) { p4.disponibilidade = false; p4.P = pecaEscolhida; System.out.println("a peça " + pecaEscolhida + " está no ponto 4 "); jogadaValida = true; }
                            else if (p5.disponibilidade) { p5.disponibilidade = false; p5.P = pecaEscolhida; System.out.println("a peça " + pecaEscolhida + " está no ponto 5 "); jogadaValida = true; }
                            else { System.out.println("está peça está presa, escolha outra"); p3.disponibilidade = false; p3.P = pecaEscolhida; }
                        }
                        else if (("y1".equals(pecaEscolhida) && "y1".equals(p4.P)) || "y2".equals(pecaEscolhida) && "y2".equals(p4.P)) {
                            p4.disponibilidade = true; p4.P = "vazio";
                            if (p3.disponibilidade) { p3.disponibilidade = false; p3.P = pecaEscolhida; System.out.println("a peça " + pecaEscolhida + " está no ponto 3 "); jogadaValida = true; }
                            else if (p5.disponibilidade) { p5.disponibilidade = false; p5.P = pecaEscolhida; System.out.println("a peça " + pecaEscolhida + " está no ponto 5 "); jogadaValida = true; }
                            else { System.out.println("está peça está presa, escolha outra"); p4.disponibilidade = false; p4.P = pecaEscolhida; }
                        }
                        else if (("y1".equals(pecaEscolhida) && "y1".equals(p5.P)) || "y2".equals(pecaEscolhida) && "y2".equals(p5.P)) {
                            p5.disponibilidade = true; p5.P = "vazio";
                            if (p2.disponibilidade) { p2.disponibilidade = false; p2.P = pecaEscolhida; System.out.println("a peça " + pecaEscolhida + " está no ponto 2 "); jogadaValida = true; }
                            else if (p3.disponibilidade) { p3.disponibilidade = false; p3.P = pecaEscolhida; System.out.println("a peça " + pecaEscolhida + " está no ponto 3 "); jogadaValida = true; }
                            else if (p4.disponibilidade) { p4.disponibilidade = false; p4.P = pecaEscolhida; System.out.println("a peça " + pecaEscolhida + " está no ponto 4 "); jogadaValida = true; }
                            else { System.out.println("está peça está presa, escolha outra"); p5.disponibilidade = false; p5.P = pecaEscolhida; }
                        }
                    }

                    // Se uma jogada válida foi feita, envia os dados e troca o turno
                    if (jogadaValida) {
                        Map<String, Object> dadosParaEnviar = Map.of(
                                "p1_peca", p1.P, "p1_disp", p1.disponibilidade,
                                "p2_peca", p2.P, "p2_disp", p2.disponibilidade,
                                "p3_peca", p3.P, "p3_disp", p3.disponibilidade,
                                "p4_peca", p4.P, "p4_disp", p4.disponibilidade,
                                "p5_peca", p5.P, "p5_disp", p5.disponibilidade
                        );

                        boolean enviadoComSucesso;
                        if (protocolo == rede.Protocolo.TCP) {
                            enviadoComSucesso = Rede.enviarDados(socketTcpComunicacao, dadosParaEnviar);
                        } else {
                            enviadoComSucesso = Rede.enviarDados(socketUdp, dadosParaEnviar, enderecoOponente);
                        }

                        if (!enviadoComSucesso) {
                            System.out.println("[ERRO] Falha ao enviar a jogada!");
                            break;
                        }
                        turno = (turno == 1) ? 2 : 1;
                    } else {
                        System.out.println("Jogada inválida. Tente novamente.");
                    }

                } else {
                    // --- Turno do Oponente ---
                    System.out.println("\nAguardando jogada do oponente...");
                    Map<String, Object> dadosRecebidos;

                    if (protocolo == rede.Protocolo.TCP) {
                        dadosRecebidos = Rede.receberDados(socketTcpComunicacao, 1024);
                    } else { // UDP
                        rede.pacoteRecebido pacote = Rede.receberDados(socketUdp, 1024);
                        dadosRecebidos = (pacote != null) ? pacote.dados : null;
                        if (pacote != null) enderecoOponente = pacote.enderecoRemetente;
                    }

                    if (dadosRecebidos == null) {
                        System.out.println("\n[ERRO] Conexão com o oponente perdida!");
                        break;
                    }

                    // Atualiza o estado do tabuleiro local com base nos dados recebidos
                    p1.P = (String) dadosRecebidos.get("p1_peca");
                    p1.disponibilidade = (Boolean) dadosRecebidos.get("p1_disp");
                    p2.P = (String) dadosRecebidos.get("p2_peca");
                    p2.disponibilidade = (Boolean) dadosRecebidos.get("p2_disp");
                    p3.P = (String) dadosRecebidos.get("p3_peca");
                    p3.disponibilidade = (Boolean) dadosRecebidos.get("p3_disp");
                    p4.P = (String) dadosRecebidos.get("p4_peca");
                    p4.disponibilidade = (Boolean) dadosRecebidos.get("p4_disp");
                    p5.P = (String) dadosRecebidos.get("p5_peca");
                    p5.disponibilidade = (Boolean) dadosRecebidos.get("p5_disp");

                    System.out.println("Oponente jogou. O estado do tabuleiro foi atualizado.");
                    turno = (turno == 1) ? 2 : 1;
                }
            }

        } catch (IOException e) {
            System.err.println("\n[ERRO DE REDE] Ocorreu um erro: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (socketTcpComunicacao != null) socketTcpComunicacao.close();
                if (socketUdp != null) socketUdp.close();
                if (socketPrincipal != null) socketPrincipal.close();
            } catch (Exception e) {
                System.err.println("Erro ao fechar os sockets: " + e.getMessage());
            }
            Terminal.close();
            input.close();
            System.out.println("\n[INFO] Conexão encerrada.");
        }
    }
}
