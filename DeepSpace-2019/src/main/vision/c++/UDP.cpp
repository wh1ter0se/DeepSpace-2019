#define _DEFAULT_SOURCE

#include "Vision.h"
#include <sys/types.h>
#include <arpa/inet.h>
#include <assert.h>
#include <errno.h>
#include <string.h>
#include <sys/time.h>
#include <sys/select.h>
#include <sys/ioctl.h>
#include <net/if.h>
#include <unistd.h>
#include <sys/socket.h> 
#include <stdio.h>
#include <netdb.h>
// Define the port we will be sending to
#define port 22

/**
 * source file for the good ol UDP code
 */


UDP::UDP() {
    //if needed?
}

using namespace std;

/**
 * sends the given string to the RIO through UDP.
 * @param msg the message to send to the RIO.
 */
void UDP::Send(string msg) {
    // struct hostent *hp; /* host information */ 
    // struct sockaddr_in servaddr; /* server address */ 

    // int fd = 0;
    // const char *sendMSG = msg.c_str();
    // //char *my_messsage = "this is a test message"; 

    // /* fill in the server's address and data */ 
    // memset((char*)&servaddr, 0, sizeof(servaddr)); 
    // servaddr.sin_family = AF_INET; 
    // servaddr.sin_port = htons(port); 

    // /* look up the address of the server given its name */ 
    // /*hp = gethostbyname(host); 
    // if (!hp) { 
	//     fprintf(stderr, "could not obtain address of %s\n", host); 
	//     //return 0; 
    // } 
    // */
    // /* put the host's address into the server address structure */ 
    // memcpy((void *)&servaddr.sin_addr, hp->h_addr_list[0], hp->h_length); 

    // /* send a message to the server */ 
    // if (sendto(fd, sendMSG, strlen(sendMSG), 0, (struct sockaddr *)&servaddr, sizeof(servaddr)) < 0) { 
	// perror("sendto failed"); 
	// //return 0; 
    // }
    
}


/**
 * reads the UDP buffer and returns whats there. Also clears the buffer after it's done
 * @return the message in the buffer.
 */
string UDP::Receive() {
    // struct sockaddr_in src = { .sin_family=AF_INET, .sin_addr.s_addr=INADDR_ANY, .sin_port=htons(68) };

    // int fd = socket(AF_INET, SOCK_DGRAM, IPPROTO_IP);

    // const int on=1;
    // setsockopt(fd, SOL_SOCKET, SO_REUSEADDR, &on, sizeof(on));

    // struct ifreq ifr;
    // memset(&ifr, '\0', sizeof(ifr));
    // ioctl(fd, SIOCGIFINDEX, &ifr);
    // setsockopt(fd, SOL_SOCKET, SO_BINDTODEVICE, (void *)&ifr, sizeof(ifr));

    // bind(fd, (struct sockaddr *)&src, sizeof(src));
    // string s = "";
    // char buf[512];
    // ssize_t res = recvfrom(fd, buf, sizeof(buf), 0, NULL, 0);
    // //printf("res=%zi\n", res);
	// //Returns the recieved value
    // s += buf;
    // return s;
    return "";
}