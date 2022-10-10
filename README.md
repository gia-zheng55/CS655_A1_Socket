## CS655 Assignment1

project: part1
Project2: part2

### Implementation
1. Enter the forder: project/src (Project2/src)
2. Run commend: javac EchoServer.java
3. Run commend: java EchoServer <port number>
4. Run commend: javac EchoClient.java (open another window to run client program)
5. Run commend: java EchoClient <hostname: csa1.bu.edu> <port number> 
(open another window to run client program)

### Output/Input
- Output
Connect successfully with the server csa1.bu.edu: 58456
Please enter the measure type(rtt/tput):
- Input
rtt
- Output
Please enter the number of probe messages to send(more than 10)
- Input
1000
- Output
Please enter the message size:
(rtt: 1, 100, 200, 400, 800, 1000)
(tput: 1000, 2000, 4000, 8000, 16000, 32000)
- Input
100
- Output
Please enter the expected server delay(ms)
- Input
0
- Output
Phase 1 send: s rtt 1000 100 10
Phase 1 received: 200 Ready
Phase 2 (1) receive: m 1 aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
Phase 2 (1) send: m 1 aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
Phase 2 (2) receive: m 2 aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
Phase 2 (2) send: m 2 aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
.
.
.
Phase 2 (999) send: m 999 aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
Phase 2 (999) receive: m 999 aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
Phase 2 (1000) send: m 1000 aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
Phase 2 (1000) receive: m 1000 aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
The RTT of this test is 7ms.
Phase 3 send: t

Phase 3 receive: 200 OK: Closing Connection
