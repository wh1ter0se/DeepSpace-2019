#include "UDP.h"

using namespace std;

void err(const char *msg) {
    perror(msg);
    exit(1);
}

string UDP::ip = "10.36.95.2";

UDP::UDP() {
    //define our socket, using ipv4 protocols (AF_INET), and TCP (SOCK_STREAM)
    cout << "creating socket" << endl;

    this->sock = socket(AF_INET, SOCK_DGRAM, 0);
    if(this->sock < 0) //that sock didn't do correctly
        err("IT AINT THERE CHEIF"); //sorry, "cheif" needed to be in here somewhere

    cout << "setting address" << endl;
    cout.flush();
    //family, addr, and port
    
    sockaddr_in cliaddr;
    
    cliaddr.sin_family       = AF_INET; //protcol
    cliaddr.sin_addr.s_addr  = INADDR_ANY; //we will take messages from any address, its the port that matters
    cliaddr.sin_port         = htons(portno); //set the port number
    
    this->client_address = (sockaddr_in*) &cliaddr;
    
    sockaddr_in servaddr;
    servaddr.sin_family       = AF_INET;
    servaddr.sin_port         = htons(portno);
    
    this->server_address = (sockaddr_in*) &servaddr;
    
    //discover the RIO
    //first set our address info
    memset(&this->our_info, 0, sizeof(this->our_info)); //clears the thing
    this->our_info.ai_family    = AF_INET;
    this->our_info.ai_socktype  = SOCK_DGRAM;
    this->our_info.ai_flags     = AI_PASSIVE;
    this->our_info.ai_protocol  = 0;
    this->our_info.ai_canonname = NULL;
    this->our_info.ai_next      = NULL;
    this->our_info.ai_next      = NULL;
    
    //std::to_string(this->portno).c_str()
    int addr_result = getaddrinfo(this->ip.c_str(), "3695", &this->our_info, (addrinfo**) &this->rio_info); //get the address
    //hostent *test = gethostbyname(this->ip.c_str());
    if(addr_result < 0)
        err("FAILED TO GET ADDRESS");
    
    cout << "1" << endl;
    cout.flush();
    
    this->server_address = (sockaddr_in*) rio_info.ai_addr;
    
    //bcopy(
    //    (char*) this->rio_info.ai_addr, 
    //    (char*) &server_address->sin_addr;
    //    this->rio_info.ai_addrlen
    //);
    
    cout << "2" << endl;
    cout.flush();

    //this->client_address.sin_family       = AF_INET;
    //this->client_address.sin_addr.s_addr  = INADDR_ANY;
    //this->client_address.sin_port         = htons(portno);

    bzero((char *) &server_address, sizeof(server_address));

    cout << "binding..." << endl;
    
    //now attempt to bind
    int bind_result = bind(sock, (const sockaddr*) &cliaddr, sizeof(cliaddr));
    if(bind_result < 0) //something went wrong while binding
        err("BINDING FAILED");
        
    cout << this->server_address << endl;
    int connect_result = connect(this->sock, (const sockaddr*) this->server_address, sizeof(this->server_address));
    if(connect_result < 0)
        err("CONNECT FAILED");

    this->client_len = sizeof(this->client_address);

}

void UDP::Send(string msg) {
    int result = sendto(this->sock, msg.c_str(), sizeof(msg.c_str()), MSG_DONTWAIT, (sockaddr*) &this->client_address, sizeof(this->client_address));
    if(result < 0)
        err("SEND FAILED");
    
    cout << "send result: " << result << endl;
    cout.flush();
}

string UDP::Receive() {
    bzero(buffer, sizeof(buffer));
    recvfrom(this->sock, this->buffer, 256, 0, (sockaddr*) &this->client_address, &this->client_len);
    return buffer;
}

void UDP::Close() {
    close(this->sock);
}
