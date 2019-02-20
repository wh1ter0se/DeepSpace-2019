#include <iostream>
#include "netdb.h"
#include "string.h"
#include "unistd.h"
#include "sys/socket.h"
#include "sys/types.h"
#include "netinet/in.h"
#include "arpa/inet.h"

using namespace std;

class UDP {
    public:
    UDP(string dest_ip, int port);
    void Send(string msg);
    string Recieve();
    void Close();

    private:
    int sock;
    sockaddr_in server_address;
};