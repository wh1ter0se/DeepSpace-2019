#include "UDP.h"

using namespace std;

int main() {
    
    UDP udp = UDP("127.0.0.1", 3695);

    cout << "Please enter an action. Actions are:\n1. read\n2. write\n3. exit" << endl;

    while(true) {
        string action;
        cout << "Enter an action: ";
        cin >> action;

        if(action == "1") {
            string read = udp.Recieve();
            cout << "read from udp: " << read << endl;
        }
        if(action == "2") {
            string write;
            cout << "write: ";
            cin >> write;
            udp.Send(write);
            cout << "sent " << write << endl;
        }
        if(action == "3") {
            break;
        }
    }

    udp.Close();
    cout << "Goodbye!" << endl;
}