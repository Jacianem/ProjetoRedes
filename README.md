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
