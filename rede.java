import org.json.JSONObject;
import org.json.JSONException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Scanner;


public class rede{

    public enum Protocolo {
        TCP,
        UDP
    }
    public static class pacoteRecebido {
        public final Map<String, Object> dados;
        public final SocketAddress enderecoRemetente;

        public pacoteRecebido(Map<String, Object> dados, SocketAddress enderecoRemetente) {
            this.dados = dados;
            this.enderecoRemetente = enderecoRemetente;
        }
    }

//criação de sockets de acordo com a escolha dos jogadores
public AutoCloseable criarSocketComunicacao(Protocolo protocolo, String host, int porta) throws IOException { 

        InetAddress enderecoVinculado = InetAddress.getByName(host);

        //criação de socket TCP
        if (protocolo == Protocolo.TCP) {
            
            ServerSocket socketServidor = new ServerSocket(porta, 50, enderecoVinculado);
            socketServidor.setReuseAddress(true); 
            return socketServidor;
        }
        
        //criação de soket UDP
        else { 
            DatagramSocket socketDatagrama = new DatagramSocket(new InetSocketAddress(enderecoVinculado, porta));
            socketDatagrama.setReuseAddress(true);
            return socketDatagrama;
        }
    }
    // Envia dados (serializados em JSON) através de um socket TCP para um oponente específico.
    // Cria um JSONObject diretamente do Map e o converte para uma string.
    public boolean enviarDados(Socket socketTcp, Map<String, Object> dados) {
        
        JSONObject objetoJson = new JSONObject(dados);
        String cargaDeDados = objetoJson.toString();
        byte[] bytesCargaDeDados = cargaDeDados.getBytes(StandardCharsets.UTF_8);

        try {
            OutputStream fluxoSaida = socketTcp.getOutputStream();
            fluxoSaida.write(bytesCargaDeDados);
            fluxoSaida.flush(); 
            return true;
        } catch (IOException e) {
            System.err.println("Erro ao enviar dados TCP: " + e.getMessage());
            return false;
        }
    }

    // Envia dados (serializados em JSON) através de um socket UDP para um oponente específico.
    // Cria um JSONObject diretamente do Map e o converte para uma string.
    public boolean enviarDados(DatagramSocket socketUdp, Map<String, Object> dados, SocketAddress enderecoOponente) {
        JSONObject objetoJson = new JSONObject(dados);
        String cargaDeDados = objetoJson.toString();
        byte[] bytesCargaDeDados = cargaDeDados.getBytes(StandardCharsets.UTF_8);

        DatagramPacket pacote = new DatagramPacket(bytesCargaDeDados, bytesCargaDeDados.length, enderecoOponente);
        try {
            socketUdp.send(pacote);
            return true;
        } catch (IOException e) {
            System.err.println("Erro ao enviar dados UDP: " + e.getMessage());
            return false;
        }
    }

    public Map<String, Object> receberDados(Socket socketTcp, int tamanhoBuffer) {
        try {
            InputStream fluxoEntrada = socketTcp.getInputStream();
            byte[] buffer = new byte[tamanhoBuffer];
            int bytesLidos = fluxoEntrada.read(buffer);

            if (bytesLidos == -1) {
                // A conexão foi fechada pelo outro lado
                return null;
            }

            String cargaDeDados = new String(buffer, 0, bytesLidos, StandardCharsets.UTF_8);
            JSONObject objetoJson = new JSONObject(cargaDeDados);
            return objetoJson.toMap();
        } catch (IOException e) {
            // Um erro de IO geralmente significa que o socket foi fechado
            System.err.println("Erro ao receber dados TCP: " + e.getMessage());
            return null;
        } catch (JSONException e) {
            System.err.println("Erro ao analisar JSON recebido via TCP: " + e.getMessage());
            return null;
        }
    }

    public pacoteRecebido receberDados(DatagramSocket socketUdp, int tamanhoBuffer) {
        try {
            byte[] buffer = new byte[tamanhoBuffer];
            DatagramPacket pacote = new DatagramPacket(buffer, buffer.length);
            socketUdp.receive(pacote); // Espera bloqueante por um pacote

            String cargaDeDados = new String(pacote.getData(), 0, pacote.getLength(), StandardCharsets.UTF_8);
            JSONObject objetoJson = new JSONObject(cargaDeDados);
            Map<String, Object> dados = objetoJson.toMap();

            return new pacoteRecebido(dados, pacote.getSocketAddress());
        } catch (IOException e) {
            System.err.println("Erro ao receber dados UDP: " + e.getMessage());
            return null;
        } catch (JSONException e) {
            System.err.println("Erro ao analisar JSON recebido via UDP: " + e.getMessage());
            return null;
        }
    }

}
