#include <iostream>
#include "string.h"
#include "unistd.h"
#include "sys/socket.h"
#include "sys/types.h"
#include "netinet/in.h"
#include <netdb.h>


using namespace std;

class UDP {
    public:
    UDP();
    void Send(string msg);
    string Receive();
    void Close();

    static const int portno = 3695;
    static string ip;

    private:
    int sock; //a pointer to the socket
    char buffer[256]; //buffer we are to read from

    sockaddr_in *server_address, //our address! 
                *client_address; //the RIO's address
    
    socklen_t client_len;
    
    addrinfo our_info, rio_info;
};
