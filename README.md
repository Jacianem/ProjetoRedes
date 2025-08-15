PONG HAU KI  
O projeto consiste na criação do jogo Pong Hau Ki em Java, com uma implementação de rede utilizando sockets e o formato de dados JSON para a comunicação entre os jogadores. O jogo é jogado em turnos, onde dois jogadores alternam movimentos de suas peças em um tabuleiro de 5 pontos. Cada jogador possui 2 peças e há um ponto livre no tabuleiro. O objetivo do jogo é mover as peças de forma estratégica até que um dos jogadores fique sem movimentos possíveis.

A comunicação entre os jogadores é realizada através de um sistema servidor-cliente, onde um jogador atua como servidor e o outro como cliente. Ambos os jogadores escolhem o protocolo de rede desejado para a comunicação, com suporte tanto para UDP quanto para TCP. A conexão é feita através de um endereço IP e uma porta fornecidos. Durante o jogo, os dados sobre os movimentos das peças são trocados utilizando o formato JSON, garantindo a simplicidade e a flexibilidade na troca de informações.

ALUNOS:
Jaciane Melo Dos Santos
Matrícula:
20231054010038

Jamylle Costa de Souza
Matrícula:
20231054010021

NICOLY LAYANE DE SOUZA SILVA
Matrícula:
20231054010032

Uriel de França Cunha
Matrícula:
20221054010020

INSTRUÇÕES
primeiramente é necessário ter os programas vscode, java e json, instalados na maquina com suas versões mais recentes, em seguida colocar todos os arquivos dentro de uma única pasta, abrir o vscode e rodar o codigo no terminal com o seguinte comando: javac -cp ".;json-20250517.jar" tabuleiro.java ; java -cp ".;json-20250517.jar" tabuleiro
lembrando que é de extrema importancia que ambos jogadores escolham o mesmo protocolo, endereço e porta

DESCRIÇÃO DO PROTOCOLO
O protocolo de camada de aplicação do jogo Pong Hau K'i tem como principal objetivo permitir a comunicação entre dois jogadores, garantindo que o tabuleiro fique sincronizado durante a partida toda. Ele é independente do protocolo de transporte, ou seja, não depende de um protocolo específico para funcionar, podendo rodar tanto sobre TCP quanto sobre UDP sem precisar mudar sua lógica do jogo, e foi feito para funcionar de forma simples e eficiente. A comunicação entre o cliente e o servidor ocorre por mensagens em formato JSON, que facilita a conversão e reconversão dos dados. No início da conexão, o cliente envia o status "iniciar" para indicar o começo da partida, enquanto o servidor aguarda essa confirmação. Cada jogada dos jogadores é transmitida como um pacote JSON que guarda o estado completo do tabuleiro, com cinco pares de informações para cada ponto (p1 a p5): o campo P indica qual peça está no ponto, e o campo disponibilidade indica se ele está disponível para jogada. No JSON, essas informações são representadas como "p1_peca", "p2_peca"e  para as peças, e "p1_disp", "p2_disp" para a disponibilidade. O jogador que faz a jogada envia os dados, e o adversário do jogador atualiza de forma real seu tabuleiro local ao receber essas informações. As ações são interpretadas a partir dos campos do JSON, e o gerenciamento dos turnos é controlado pela lógica do jogo. No TCP, a comunicação ocorre por fluxos de dados, e no UDP, as mensagens são enviadas em pacotes diretos, sempre com a mesma estrutura para garantir compatibilidade. O protocolo inclui mensagens simples para o fim da partida ou para tratar/lidar com falhas, permitindo que a lógica do jogo continue de forma correta e com a comunicação clara.

