#include "UDP.h"

using namespace std;

int main() {
    cout << "hello world!" << endl;
    UDP udp = UDP();

    cout << "Please enter an action. \n1. Read\n2. Write\n3. Quit" << endl;

    for(;;) {
        //do things
        string in;
        cin >> in;

        if(in == "1") {
            string r = udp.Receive();
            cout << r << endl;
        } else if(in == "2") {
            cout << "Write: ";
            string write;
            cin >> write;

            udp.Send(write);
            cout << "Sent " << write << endl;

        } else if(in == "3") {
            break;
        }
    }

    cout << "Closing UDP" << endl;
    udp.Close();

    cout << "Goodbye!" << endl;
}

