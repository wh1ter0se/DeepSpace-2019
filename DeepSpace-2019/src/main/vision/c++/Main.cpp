#include <iostream>
#include "Vision.h"

using namespace std;
int main(int argc, char *argv[]) {
    cout << "Vision Man is here\n";
    cout.flush();
    PostProcessor worker = PostProcessor();
    worker.Loop();
    worker.CleanUp();
    return 0;
}